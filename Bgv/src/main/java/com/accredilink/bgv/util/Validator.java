package com.accredilink.bgv.util;

public class Validator {

	public static String validateSSN(String ssnNumber) {
		String value = ssnNumber.replace("-", "");
		return value;
	}

	public static String validatePhoneNumber(String phoneNumber) {
		phoneNumber = phoneNumber.replaceAll("\\D", "");
		return phoneNumber;
	}

}
