package com.accredilink.bgv.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class GobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(AccredLinkAppException.class)
	public ResponseEntity<ErrorResponse> handleAppException(AccredLinkAppException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getExceptionMessge());
		errorResponse.setTimeStamp(LocalDateTime.now());
		errorResponse.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Internal Server Error Unable To Process The Request");
		errorResponse.setTimeStamp(LocalDateTime.now());
		errorResponse.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.EXPECTATION_FAILED);
	}


}
