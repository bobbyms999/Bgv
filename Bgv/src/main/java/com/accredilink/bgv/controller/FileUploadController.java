package com.accredilink.bgv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.accredilink.bgv.service.FileUploadService;
import com.accredilink.bgv.util.ResponseObject;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	@PostMapping("/{loggedInUser}/{agencyName}/uploademployee")
	public ResponseObject uploadEmployee(@PathVariable String loggedInUser,@PathVariable String agencyName,@RequestParam("file") MultipartFile file) {
		return fileUploadService.uploadEmployeeSheet(file,loggedInUser,agencyName);
	}
	
	@PostMapping("/uploadaliasnames")
	public ResponseObject uploadAliasNames(@RequestParam("file") MultipartFile file) {
		return fileUploadService.uploadAliasNamesSheet(file);
	}
	
	@PostMapping("/uploadfeeddata")
	public ResponseObject uploadFeedData(@RequestParam("file") MultipartFile file) {
		return fileUploadService.uploadFeedData(file);
	}
}
