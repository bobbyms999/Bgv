package com.accredilink.bgv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.entity.DataFeedEmployee;
import com.accredilink.bgv.repository.DataFeedEmployeeRepo;

@Service
public class DataFeedEmployeeServiceImpl implements DataFeedEmployeeService {
	
	@Autowired
	DataFeedEmployeeRepo dataFeedEmployeeRepo;

	@Override
	public List<DataFeedEmployee> getAllDataFeedEmployee() {
		
		return dataFeedEmployeeRepo.findAll();
	}

	@Override
	public DataFeedEmployee findAllByFirstName(String firstName,String lastName) {
		
		return dataFeedEmployeeRepo.findAllByFirstNameAndLastName(firstName,lastName);
	}

	

}
