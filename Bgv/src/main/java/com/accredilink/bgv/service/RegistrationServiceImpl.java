package com.accredilink.bgv.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accredilink.bgv.entity.Agency;
import com.accredilink.bgv.entity.User;
import com.accredilink.bgv.exception.AccredLinkAppException;
import com.accredilink.bgv.repository.AgencyRepository;
import com.accredilink.bgv.repository.UserRepository;
import com.accredilink.bgv.util.Constants;
import com.accredilink.bgv.util.EmailValidator;
import com.accredilink.bgv.util.PasswordEncrAndDecrUtil;
import com.accredilink.bgv.util.ResponseObject;
import com.accredilink.bgv.util.Validator;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	@Autowired
	NotificationService notificationService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Constants constants;

	@Autowired
	AgencyRepository agencyRepository;

	/**
	 * @param User
	 * @return ResponseObject
	 * @throws Exception
	 */

	@Override
	@Transactional
	public ResponseObject registration(User user) throws Exception {
		User savedUser = null;
		ResponseObject responseObject = null;
		/*
		 * Checking email id is valid or not, if it is invalid then throwing exception.
		 */
		boolean isValid = EmailValidator.isValid(user.getEmailId());
		if (!isValid) {
			throw new AccredLinkAppException(constants.INVALID_EMAIL);
		}
		try {
			checkUserIdAndEmail(user);
			/*
			 * Checking the User, If the User is Employee then inserting records into only
			 * User table
			 */
			if (user.getType().equalsIgnoreCase(constants.INDIVIDUAL)) {
				user.setAgency(null);
				savedUser = userRepository.save(user);
				/*
				 * Checking the User, If the User is Company then inserting records into User,
				 * Company, Address and UserType tables.
				 */
			} else if (user.getType().equalsIgnoreCase(constants.AGENCY)) {
				savedUser = userRepository.save(user);
			}
			if (savedUser != null) {
				boolean status = notificationService.sendEmail(user.getEmailId(), constants.EMAIL_REGISTRATION_SUBJECT,
						constants.EMAIL_REGISTRATION_BODY);
				if (status) {
					logger.info("Successfully sent email notification after registration.");
					responseObject = ResponseObject.constructResponse(constants.REGISTRATION_SUCESS, 1);
				} else {
					logger.error("ERROR : while sending notification at registration");
				}
			} else {
				responseObject = ResponseObject.constructResponse(constants.REGISTRATION_FAILED, 0);
			}
		} catch (AccredLinkAppException ale) {
			ale.printStackTrace();
			throw new AccredLinkAppException(ale.getExceptionMessge());
		} catch (Exception e) {
			logger.error("Exception raised while registration ", e);
			e.printStackTrace();
			throw new Exception(constants.REGISTRATION_FAILED);
		}
		return responseObject;
	}

	private void auditUser(User user) {
		user.getAddress().setCreatedBy(user.getUserName());
		user.setPassword(PasswordEncrAndDecrUtil.encrypt(user.getPassword()));
		user.setPhoneNumber(Validator.validatePhoneNumber(user.getPhoneNumber()));

		if (user.getType().equalsIgnoreCase(constants.AGENCY)) {

			Optional<Agency> existingAgency = agencyRepository.findByAgencyName(user.getAgency().getAgencyName());
			if (existingAgency.isPresent()) {
				user.setAgency(existingAgency.get());
			} else {
				String agencyLogo = user.getAgency().getAgencyLogo();
				if (agencyLogo != null) {
					try {
						Blob agencyImage = new SerialBlob(agencyLogo.getBytes());
						user.getAgency().setAgencyImage(agencyImage);
						user.getAgency().setCreatedBy(user.getUserName());
					} catch (SQLException e) {
						e.printStackTrace();
						throw new AccredLinkAppException("Unbale to store Agency Logo");
					}
				}
			}
		}
	}

	/*
	 * Checking email id already exist or not, if it is exists then throwing
	 * exception.
	 */
	private void checkUserIdAndEmail(User user) {
		Optional<User> optionalRegistration = userRepository.findByEmailIdOrUserName(user.getEmailId(),
				user.getUserName());
		if (optionalRegistration.isPresent()) {
			throw new AccredLinkAppException(constants.EMAIL_ID_ALREADY_EXISTS);
		} else {
			auditUser(user);
		}
	}

}