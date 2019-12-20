package com.accredilink.bgv.service;

import java.util.List;

import com.accredilink.bgv.entity.DataFeedEmployee;

public interface DataFeedEmployeeService {

	List<DataFeedEmployee> getAllDataFeedEmployee(); 

	DataFeedEmployee findAllByFirstName(String firstName,String lastName); 


}
