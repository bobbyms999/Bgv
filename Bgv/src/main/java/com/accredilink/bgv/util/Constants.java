package com.accredilink.bgv.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:constants.properties")
public class Constants {

	@Value("${registration.sucess}")
	public String REGISTRATION_SUCESS;

	@Value("${registration.failed}")
	public String REGISTRATION_FAILED;

	@Value("${login.sucess}")
	public String LOGIN_SUCESS;

	@Value("${login.failed}")
	public String LOGIN_FAILED;

	@Value("${login.failed.register}")
	public String LOGIN_FAILED_REGISTER;
	
	@Value("${employee.create.success}")
	public String EMPLOYEE_CREATE_SUCCESS;
	
	@Value("${employee.create.failed}")
	public String EMPLOYEE_CREATE_FAILED;
	
	@Value("${email.sucess}")
	public String EMAIL_SUCESS;

	@Value("${email.fail}")
	public String EMAIL_FAILED;

	@Value("${password.sucess}")
	public String PASSWORD_SUCESS;

	@Value("${password.failed}")
	public String PASSWORD_FAILED;

	@Value("${invalid.email}")
	public String INVALID_EMAIL;

	@Value("${individual}")
	public String INDIVIDUAL;

	@Value("${agency}")
	public String AGENCY;

	@Value("${email.id.exists}")
	public String EMAIL_ID_ALREADY_EXISTS;

	@Value("${email.registration.subject}")
	public String EMAIL_REGISTRATION_SUBJECT;

	@Value("${email.registration.body}")
	public String EMAIL_REGISTRATION_BODY;

	@Value("${email.resetpassword.subject}")
	public String EMAIL_RESETPASSWORD_SUBJECT;

	@Value("${email.resetpassword.body}")
	public String EMAIL_RESETPASSWORD_BODY;
}
