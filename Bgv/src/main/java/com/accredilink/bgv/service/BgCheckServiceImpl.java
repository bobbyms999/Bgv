package com.accredilink.bgv.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.component.DadsEMRCheck;
import com.accredilink.bgv.component.NationalOIGCheck;
import com.accredilink.bgv.component.TxOIGCheck;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeBgDetails;
import com.accredilink.bgv.repository.EmployeeBgDetailsRepository;
import com.accredilink.bgv.repository.EmployeeRepository;
import com.accredilink.bgv.util.ResponseObject;

@Service
public class BgCheckServiceImpl implements BgCheckService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private NationalOIGCheck nationalOIGCheck;

	@Autowired
	private TxOIGCheck txOIGCheck;

	@Autowired
	private DadsEMRCheck dadsEMRCheck;

	private EmployeeBgDetailsRepository employeeBgDetailsRepository;

	@Override
	@Transactional
	public ResponseObject submitBg(int employeeId) {
		EmployeeBgDetails bgDetails = null;
		String SSNSuccess = "SSN Number Matched";
		Optional<Employee> employee = employeeRepository.findById(employeeId);

		if (employee.isPresent()) {
			bgDetails = nationalOIGCheck.check(employee.get());
			if (!(bgDetails.getBgResult().trim().equals(SSNSuccess))) {
				//employeeBgDetailsRepository.save(bgDetails);
				bgDetails = txOIGCheck.check(employee.get());
				if (!(bgDetails.getBgResult().trim().equals(SSNSuccess))) {
					//employeeBgDetailsRepository.save(bgDetails);
					bgDetails = dadsEMRCheck.check(employee.get());
					if (!(bgDetails.getBgResult().trim().equals(SSNSuccess))) {
						//employeeBgDetailsRepository.save(bgDetails);
					} else {
						//employeeBgDetailsRepository.save(bgDetails);
					}
				} else {
					//employeeBgDetailsRepository.save(bgDetails);
				}
			} else {
				//employeeBgDetailsRepository.save(bgDetails);
			}
		}
		return ResponseObject.constructResponse("over",1);
	}
}
