package com.accredilink.bgv.service;

import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.util.ResponseObject;

public interface FileUploadService {

	public ResponseObject uploadEmployeeSheet(MultipartFile file,String loggedInUser,String agencyName);
	
	public ResponseObject uploadAliasNamesSheet(MultipartFile file);
	public ResponseObject uploadFeedData(MultipartFile file);

}
