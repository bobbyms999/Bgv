package com.accredilink.bgv.service;

import com.accredilink.bgv.pojo.Login;
import com.accredilink.bgv.util.ResponseObject;

public interface LoginService {

	public ResponseObject login(Login login);
	
	public ResponseObject forgot(Login login);
	
	public ResponseObject reset(Login login);
	
}
