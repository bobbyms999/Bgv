package com.accredilink.bgv.service;

import com.accredilink.bgv.entity.User;
import com.accredilink.bgv.pojo.Login;
import com.accredilink.bgv.util.ResponseObject;

public interface RegistrationService {

	/**
	 * @param registrationDTO
	 * @return
	 * @throws Exception
	 */
	public ResponseObject registration(User user) throws Exception;

}
