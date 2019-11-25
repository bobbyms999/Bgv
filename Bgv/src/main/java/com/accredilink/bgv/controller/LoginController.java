package com.accredilink.bgv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accredilink.bgv.pojo.Login;
import com.accredilink.bgv.service.LoginService;
import com.accredilink.bgv.util.ResponseObject;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseObject loginUser(@RequestBody Login login) {
		return loginService.login(login);
	}

	@PostMapping("/forgotpassword")
	public ResponseObject forgotPassword(@RequestBody Login login) {
		return loginService.forgot(login);
	}

	@PostMapping("/resetpassword")
	public ResponseObject resetPassword(@RequestBody Login login) {
		return loginService.reset(login);
	}
}
