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
	public ResponseObject uploadEmployeeSheet(MultipartFile file,String loggedInUser,String agencyName) {
		int uniqueRecords = excelMapper.uniqueRecords;
		if (file.isEmpty()) {
			return ResponseObject.constructResponse("File is Empty Please upload the file again", 0);
		}
		List<Employee> employees = (List<Employee>) excelMapper.mapToObject(file,loggedInUser,agencyName, Employee.class);
		if (!(employees.isEmpty()) && excelMapper.uniqueRecords >= 1) {
			excelMapper.uniqueRecords = 0;
			return ResponseObject.constructResponse("Duplicates Are Found Only Unique Records Uploaded", 1);
		}
		if (employees.isEmpty() && excelMapper.uniqueRecords >= 1) {
			excelMapper.uniqueRecords = 0;
			return ResponseObject.constructResponse("No New Records Found To Upload", 1);
		}
		if (!(employees.isEmpty())) {
			return ResponseObject.constructResponse("Records Uploaded Successfully", 1);
		}
		return ResponseObject.constructResponse("Unable to store the File", 0);

	}

	@Override
	public ResponseObject uploadAliasNamesSheet(MultipartFile file) {
		int uniqueRecords = 0;
		if (file.isEmpty()) {
			return ResponseObject.constructResponse("uploadAliasNamesSheet", 0);
		}
		List<String> success = (List<String>) excelMapper.mapToObject(file,"","", Alias.class);

		if (!(success.isEmpty())) {
			return ResponseObject.constructResponse("Successfully alias names are updated", 1);
		} else {
			return ResponseObject.constructResponse("Error Occured while saving the alias names ", 0);
		}
	}

	@Override
	public ResponseObject uploadFeedData(MultipartFile file) {
		int uniqueRecords = 0;
		if (file.isEmpty()) {
			return ResponseObject.constructResponse("uploadFeedData", 0);
		}
		List<String> success = (List<String>) excelMapper.mapToObject(file,"","", DataFeedEmployee.class);

		return ResponseObject.constructResponse("Successfully Data Feed names are Stored", 1);

	}
}
