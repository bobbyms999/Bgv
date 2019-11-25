package com.accredilink.bgv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accredilink.bgv.entity.User;
import com.accredilink.bgv.service.RegistrationService;
import com.accredilink.bgv.util.ResponseObject;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	
	@PostMapping("/register")
	public ResponseObject registerUser(@RequestBody User user) throws Exception {
		return registrationService.registration(user);
	}

}