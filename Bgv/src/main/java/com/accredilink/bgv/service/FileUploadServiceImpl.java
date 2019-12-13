package com.accredilink.bgv.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.component.ExcelMapper;
import com.accredilink.bgv.entity.Alias;
import com.accredilink.bgv.entity.DataFeedEmployee;
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
			return ResponseObject.constructResponse("File is Empty Please upload the file again", 0);
		}
		List<Employee> employees = (List<Employee>) excelMapper.mapToObject(file, Employee.class);

		List<Employee> savedEmployees = employeeRepository.saveAll(employees);
		if (!(savedEmployees.isEmpty())) {
			return ResponseObject.constructResponse("File stored successfully", 1);
		}
		return ResponseObject.constructResponse("Unable to store the File", 0);
	}

	@Override
	public ResponseObject uploadAliasNamesSheet(MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseObject.constructResponse("uploadAliasNamesSheet", 0);
		}
		List<String> success = (List<String>) excelMapper.mapToObject(file, Alias.class);

		if (!(success.isEmpty())) {
			return ResponseObject.constructResponse("Successfully alias names are updated", 1);
		} else {
			return ResponseObject.constructResponse("Error Occured while saving the alias names ", 0);
		}
	}

	@Override
	public ResponseObject uploadFeedData(MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseObject.constructResponse("uploadFeedData", 0);
		}
		List<String> success = (List<String>) excelMapper.mapToObject(file, DataFeedEmployee.class);

		if (!(success.isEmpty())) {
			return ResponseObject.constructResponse("Successfully Data Feed names are Stored", 1);
		} else {
			return ResponseObject.constructResponse("Error Occured while saving the feed ", 0);
		}

	}
}
