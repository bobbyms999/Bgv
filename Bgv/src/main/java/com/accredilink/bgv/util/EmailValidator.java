package com.accredilink.bgv.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	
	private static Pattern pattern;
	private static Matcher matcher;

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Validate hex with regular expression
	 * @param hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public  static boolean isValid(final String hex) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(hex);
		return matcher.matches();

	}
}
