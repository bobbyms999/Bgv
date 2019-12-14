package com.accredilink.bgv.util;

public class SSNValidator {

	public static String validate(String ssnNumber) {
		String value = ssnNumber.replace("-", "");
		return value;
	}

}
