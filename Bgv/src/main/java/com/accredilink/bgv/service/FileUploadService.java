package com.accredilink.bgv.service;

import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.util.ResponseObject;

public interface FileUploadService {

	public ResponseObject uploadEmployeeSheet(MultipartFile file);
	
	public ResponseObject uploadAliasNamesSheet(MultipartFile file);

}
