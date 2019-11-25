package com.accredilink.bgv.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.repository.EmployeeRepository;
import com.accredilink.bgv.util.Constants;
import com.accredilink.bgv.util.EmailValidator;
import com.accredilink.bgv.util.ResponseObject;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private Constants constants;

	@Override
	@Transactional
	public ResponseObject create(Employee employee) {

		/*
		 * Checking email id is valid or not, if it is invalid then throwing exception.
		 */
		boolean isValid = isEmailValid(employee.getEmailId());
		if (!isValid) {
			throw new AccredLinkAppException(constants.INVALID_EMAIL);
		}
		try {
			employee.setCreatedBy(employee.getEmailId());
			employee.setActive("active");
			employee = employeeRepository.save(employee);
			if (employee != null) {
				return ResponseObject.constructResponse(constants.EMPLOYEE_CREATE_SUCCESS, 1);
			}
		} catch (Exception e) {
			logger.error("Exception raised while creating employee ", e);
			e.printStackTrace();
			throw new AccredLinkAppException(constants.EMPLOYEE_CREATE_FAILED);
		}
		return ResponseObject.constructResponse(constants.EMPLOYEE_CREATE_FAILED, 0);
	}

	@Override
	public ResponseObject delete(int employeeId) {
		Optional<Employee> savedEmployee;
		try {
			savedEmployee=employeeRepository.findById(employeeId);
			if(savedEmployee!=null) {
				savedEmployee.get().setActive("deactive");
				employeeRepository.save(savedEmployee.get());
				return ResponseObject.constructResponse("Employee Deleted Successfully", 1);
			}
			else {
				return ResponseObject.constructResponse("Unable to delete the Employee", 0);
			}
		} catch (Exception e) {
			logger.error("Exception raised while deleting employee ", e);
			e.printStackTrace();
			throw new AccredLinkAppException("Exception raised while deleting the employee ");
		}
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	private boolean isEmailValid(String emailId) {
		EmailValidator emailValidator = new EmailValidator();
		return emailValidator.validate(emailId);
	}

}
