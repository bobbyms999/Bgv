package com.accredilink.bgv.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.component.DataFeedCheck;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.pojo.BgDetails;
import com.accredilink.bgv.pojo.Image;
import com.accredilink.bgv.repository.EmployeeAgencyRepo;
import com.accredilink.bgv.repository.EmployeeBgDetailsRepository;
import com.accredilink.bgv.util.ResponseObject;

@Service
public class BgCheckServiceImpl implements BgCheckService {

	@Autowired
	private DataFeedCheck dataFeedCheck;

	@Autowired
	private EmployeeAgencyRepo employeeAgencyRepo;

	@Autowired
	private EmployeeBgDetailsRepository employeeBgDetailsRepository;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss aa");


	@Override
	@Transactional
	public ResponseObject submitEmployeeBg() {
		try {
			List<EmployeeAgency> verifyBgvStatus = employeeAgencyRepo.findAllByBgStatus("NOT_STARTED");
			if (verifyBgvStatus != null && !verifyBgvStatus.isEmpty()) {
				List<EmployeeBgDetails> bgDetails = null;
				for (EmployeeAgency employeeAgency : verifyBgvStatus) {
					bgDetails = dataFeedCheck.verifyEmployeeData(employeeAgency);
					saveOrUpdateEmployeeAgency(bgDetails);
				}
			} else {
				return ResponseObject.constructResponse("Employees Are Not Avilable To Process", 2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseObject.constructResponse("SubmitBG Processed Successfully", 1);
	}

	public void saveOrUpdateEmployeeAgency(List<EmployeeBgDetails> bgDetails) {
		boolean store = false;
		if (bgDetails != null && !bgDetails.isEmpty()) {
			for (EmployeeBgDetails employeeBgDetails : bgDetails) {
				if (employeeBgDetails.getBgresult().trim().startsWith("No Records Found")) {
					store = true;
				} else {
					store = false;
					break;
				}
			}
			EmployeeAgency agency = new EmployeeAgency();
			try {
			if (store) {
				agency.setBgStatus("PASS");
				agency.setBgvDate(dateFormat.parse(dateFormat.format(new Date())));

			} else {
				agency.setBgStatus("Failed");
				agency.setBgvDate(dateFormat.parse(dateFormat.format(new Date())));

			}
			agency.setEmployee(bgDetails.get(0).getEmployeeBgDetailsPk().getEmployee());
			agency.setAgency(bgDetails.get(0).getEmployeeBgDetailsPk().getAgency());
			employeeAgencyRepo.save(agency);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<BgDetails> verifyEmployeeBg() {
		List<EmployeeAgency> employeeAgencies = employeeAgencyRepo.findAll();
		List<BgDetails> bgDetails = new ArrayList<BgDetails>();
		BgDetails bg;
		for (EmployeeAgency employeeAgency : employeeAgencies) {

			if (!(employeeAgency.getBgStatus().trim().equals("NOT_STARTED"))) {

				bg = new BgDetails();
				bg.setEmployeeId(employeeAgency.getEmployeeAgencyPk().getEmployee().getEmployeeId());
				bg.setFirstName(employeeAgency.getEmployeeAgencyPk().getEmployee().getFirstName());
				bg.setMiddleName(employeeAgency.getEmployeeAgencyPk().getEmployee().getMiddleName());
				bg.setLastName(employeeAgency.getEmployeeAgencyPk().getEmployee().getLastName());
				bg.setEmailId(employeeAgency.getEmployeeAgencyPk().getEmployee().getEmailId());
				bg.setSsnNumber(employeeAgency.getEmployeeAgencyPk().getEmployee().getSsnNumber());
				bg.setAgencyName(employeeAgency.getEmployeeAgencyPk().getAgency().getAgencyName());
				bg.setBgvDate(employeeAgency.getBgvDate());
				bg.setBgvResult(employeeAgency.getBgStatus());
				bgDetails.add(bg);

			}
		}
		return bgDetails;

	}

	@Override
	public List<Image> bgProofImages(int employeeId) {
		List<Image> images = new ArrayList<Image>();
		List<EmployeeBgDetails> bgDetails = employeeBgDetailsRepository.findAll();
		Image image;
		for (EmployeeBgDetails employeeBgDetails : bgDetails) {
			if (employeeBgDetails.getEmployeeBgDetailsPk().getEmployee().getEmployeeId() == employeeId) {
				Blob blob = employeeBgDetails.getBgresultproof();
				image=new Image();
				try {
					image.setBgDateBaseId(employeeBgDetails.getEmployeeBgDetailsPk().getBgDateBase().getBgDataBaseId());
					image.setImageData(Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length())));
					image.setBgvDescription(employeeBgDetails.getBgresultdesc());
					images.add(image);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new AccredLinkAppException("Unable to show the images");
				}
			}
		}
		return images;
	}
}
