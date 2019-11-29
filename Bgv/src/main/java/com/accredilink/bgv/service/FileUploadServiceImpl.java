package com.accredilink.bgv.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.component.ExcelMapper;
import com.accredilink.bgv.entity.Employee;
import com.accredilink.bgv.repository.EmployeeRepository;
import com.accredilink.bgv.util.ResponseObject;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ExcelMapper excelMapper;

	@Override
	@Transactional
	public ResponseObject uploadEmployeeSheet(MultipartFile file) {

		if (file.isEmpty()) {
			return ResponseObject.constructResponse("File is Empty", 0);
		}
		List<Employee> employees = (List<Employee>) excelMapper.mapToObject(file, Employee.class);
		if(employees!=null) {
		employees.forEach(employee -> {
			System.out.println(employee);
		});
		}

		/*
		 * employees.forEach(saveEmployee->{employeeRepository.save(saveEmployee);}); if
		 * (!(employees.isEmpty())) { return
		 * ResponseObject.constructResponse("File stored in db", 1); }
		 */ return ResponseObject.constructResponse("unable to store in db", 0);
	}

	@Override
	public ResponseObject uploadAliasNamesSheet(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}

}
