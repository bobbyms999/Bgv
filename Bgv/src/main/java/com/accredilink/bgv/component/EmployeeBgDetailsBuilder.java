package com.accredilink.bgv.component;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.accredilink.bgv.entity.BgDataBase;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.key.EmployeeBgDetailsKey;
import com.accredilink.bgv.repository.BgDataBaseRepository;
import com.accredilink.bgv.repository.EmployeeBgDetailsRepository;

@Component
public class EmployeeBgDetailsBuilder {

	@Autowired
	private BgDataBaseRepository bgDataBaseRepository;


	@Autowired
	private EmployeeBgDetailsRepository employeeBgDetailsRepository;

	public EmployeeBgDetails build(File image, EmployeeAgency employeeAgency, String message, int bgDbId,String description) {

		EmployeeBgDetails saveEmplBgDetails = new EmployeeBgDetails();
		EmployeeBgDetailsKey employeeBgDetailsKey = new EmployeeBgDetailsKey();

		// Adding composite key combination with emp,agency,bgdatabase
		employeeBgDetailsKey.setEmployee(employeeAgency.getEmployee());
		employeeBgDetailsKey.setAgency(employeeAgency.getAgency());
		BgDataBase bgdatabase = bgDataBaseRepository.findAllByBgDbId(bgDbId);
		employeeBgDetailsKey.setBgDateBase(bgdatabase);
		employeeBgDetailsKey.setBg_date_time(new Date());

		// Adding employeeBgDetails object
		saveEmplBgDetails.setActive(true);
		saveEmplBgDetails.setBgresult(message);
		saveEmplBgDetails.setBgresultdesc(description);

		try {
			saveEmplBgDetails.setBgresultproof(new SerialBlob(Files.readAllBytes(image.toPath())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveEmplBgDetails.setEmployeeBgDetailsPk(employeeBgDetailsKey);
		employeeBgDetailsRepository.save(saveEmplBgDetails);
		return saveEmplBgDetails;
	}

}
