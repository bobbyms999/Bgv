package com.accredilink.bgv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.entity.Agency;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.entity.User;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.repository.AgencyRepository;
import com.accredilink.bgv.repository.EmployeeRepository;
import com.accredilink.bgv.repository.UserRepository;
import com.accredilink.bgv.util.Constants;
import com.accredilink.bgv.util.EmailValidator;
import com.accredilink.bgv.util.ResponseObject;
import com.accredilink.bgv.util.Validator;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private Constants constants;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public ResponseObject create(EmployeeAgency employeeAgency, String loggedInUser) {

		try {
			Employee employee = employeeAgency.getEmployee();

			Employee existingEmployee = employeeRepository.findByFirstNameAndLastNameAndSsnNumber(
					employee.getFirstName(), employee.getLastName(), Validator.validateSSN(employee.getSsnNumber()));

			if (existingEmployee == null) {
				Agency agency;

				/*
				 * Checking email id is valid or not, if it is invalid then throwing exception.
				 */
				if (employee.getEmailId() != null) {
					boolean isValid = EmailValidator.isValid(employee.getEmailId());
					if (!isValid) {
						throw new AccredLinkAppException(constants.INVALID_EMAIL);
					}
				}
				employee.setCreatedBy(loggedInUser);
				employee.setSsnNumber(Validator.validateSSN(employee.getSsnNumber()));
				employee.setActive("S");
				EmployeeAgency newEmployeeAgency = new EmployeeAgency();
				if (employeeAgency.getAgency() != null) {
					agency = employeeAgency.getAgency();
					Optional<Agency> existingAgency = agencyRepository.findByAgencyName(agency.getAgencyName());
					if (existingAgency.isPresent()) {
						newEmployeeAgency.setAgency(existingAgency.get());
					} else {
						throw new AccredLinkAppException("Agency Information Not Found");
					}
					newEmployeeAgency.setEmployee(employee);
					newEmployeeAgency.setBgStatus(employeeAgency.getBgStatus());
					employee.addEmployeeAgency(newEmployeeAgency);
				} else {
					employee.addEmployeeAgency(null);
				}
				employee = employeeRepository.save(employee);
				if (employee != null) {
					return ResponseObject.constructResponse(constants.EMPLOYEE_CREATE_SUCCESS, 1);
				}
			} else {
				return ResponseObject.constructResponse("Employee Already Exists", 0);
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
			savedEmployee = employeeRepository.findById(employeeId);
			if (savedEmployee != null) {
				savedEmployee.get().setActive("N");
				employeeRepository.save(savedEmployee.get());
				return ResponseObject.constructResponse("Employee Deleted Successfully", 1);
			} else {
				return ResponseObject.constructResponse("Unable to delete the Employee", 0);
			}
		} catch (Exception e) {
			logger.error("Exception raised while deleting employee ", e);
			e.printStackTrace();
			throw new AccredLinkAppException("Exception raised while deleting the employee ");
		}
	}

	@Override
	public List<Employee> getAllEmployees(String loggedInUser) {
		List<Employee> newEmployees = new ArrayList<Employee>();

		Optional<User> existingUser = userRepository.findByEmailIdOrUserName(loggedInUser, loggedInUser);
		if (existingUser.isPresent()) {
			String userName = existingUser.get().getUserName();
			String emailId = existingUser.get().getEmailId();
			List<Employee> employees = employeeRepository.findAll();

			for (Employee employee : employees) {
				if (employee.getCreatedBy().equals(userName) || employee.getCreatedBy().equals(emailId)) {
					newEmployees.add(employee);
				}
			}
		}
		return newEmployees;
	}

	@Override
	public ResponseObject update(Employee employee) {
		Optional<Employee> savedEmployee;
		try {
			savedEmployee = employeeRepository.findById(employee.getEmployeeId());
			if (savedEmployee.isPresent()) {
				employee.setActive("S");
				employeeRepository.save(employee);
				return ResponseObject.constructResponse("Employee Updated Successfully", 1);
			} else {
				return ResponseObject.constructResponse("Unable to update the Employee", 0);
			}
		} catch (Exception e) {
			logger.error("Employee details not available ", e);
			e.printStackTrace();
			throw new AccredLinkAppException("Employee details not available");
		}

	}

}
