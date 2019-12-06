package com.accredilink.bgv.service;

import java.util.List;

import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.entity.EmployeeAgency;
import com.accredilink.bgv.util.ResponseObject;

public interface EmployeeService {

	public ResponseObject create(EmployeeAgency employeeAgency);

	public ResponseObject delete(int employeeId);

	public List<Employee> getAllEmployees();

}
