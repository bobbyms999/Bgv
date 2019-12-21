package com.accredilink.bgv.service;

import java.util.List;

import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.util.ResponseObject;

public interface EmployeeService {

	public ResponseObject create(EmployeeAgency employeeAgency,String loggedInUser);

	public ResponseObject delete(int employeeId);

	public List<Employee> getAllEmployees(String loggedInUser);
	
	public ResponseObject update(Employee employee);

}
