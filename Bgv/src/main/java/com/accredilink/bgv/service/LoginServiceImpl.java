package com.accredilink.bgv.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.entity.Agency;
import com.accredilink.bgv.entity.User;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.pojo.Login;
import com.accredilink.bgv.repository.UserRepository;
import com.accredilink.bgv.util.Constants;
import com.accredilink.bgv.util.PasswordEncrAndDecrUtil;
import com.accredilink.bgv.util.ResponseObject;
import com.accredilink.bgv.util.TokenGeneratorUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private Constants constants;

	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	@Override
	public ResponseObject login(Login login) {
		Optional<User> optionalRegistration = userRepository.findByEmailIdOrUserName(login.getEmailId(),
				login.getEmailId());
		Agency agency=null;
		if (optionalRegistration.isPresent()) {
			if (PasswordEncrAndDecrUtil.check(login.getPassword(), optionalRegistration.get().getPassword())) {
				agency=optionalRegistration.get().getAgency();
				Blob image = agency.getAgencyImage();
				String agencyImage = null;
				if (image!=null) {
					try {
						byte[] imageArray = image.getBytes(1, (int) image.length());
						agencyImage = new String(imageArray);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return ResponseObject.constructResponse(constants.LOGIN_SUCESS, 1,
						TokenGeneratorUtil.generateNewToken(),agency.getAgencyId(),agency.getAgencyName(),agencyImage);
			} else {
				throw new AccredLinkAppException(constants.LOGIN_FAILED);
			}
		} else {
			throw new AccredLinkAppException(constants.LOGIN_FAILED_REGISTER);
		}
	}

	@Override
	public ResponseObject forgot(Login login) {
		User user = null;
		String token = null;
		boolean flag = false;
		ResponseObject responseObject = null;
		StringBuffer emailBody = new StringBuffer(constants.EMAIL_RESETPASSWORD_BODY.toString());
		Optional<User> optionalRegistration = userRepository.findByEmailId(login.getEmailId());
		if (optionalRegistration.isPresent()) {
			user = optionalRegistration.get();
			token = TokenGeneratorUtil.generateNewToken();
			user.setTokenNumer(token);
			userRepository.save(user);
			emailBody = emailBody.append(token).append("&").append("email=" + login.getEmailId());
			flag = notificationService.sendEmail(login.getEmailId(), constants.EMAIL_RESETPASSWORD_SUBJECT,
					emailBody.toString());
			return responseObject = (flag) ? ResponseObject.constructResponse(constants.EMAIL_SUCESS, 1)
					: ResponseObject.constructResponse(constants.EMAIL_FAILED, 0);
		} else {
			throw new AccredLinkAppException(constants.EMAIL_FAILED);
		}
	}

	@Override
	public ResponseObject reset(Login login) {
		User user = null;
		Optional<User> optionalRegistration = userRepository.findByEmailIdAndTokenNumer(login.getEmailId(),
				login.getToken());
		if (optionalRegistration.isPresent()) {
			user = optionalRegistration.get();
			user.setPassword(PasswordEncrAndDecrUtil.encrypt(login.getPassword()));
			userRepository.save(user);
			return ResponseObject.constructResponse(constants.PASSWORD_SUCESS, 1);
		} else {
			logger.error("Email id/token not found");
			throw new AccredLinkAppException(constants.PASSWORD_FAILED);
		}
	}
}
