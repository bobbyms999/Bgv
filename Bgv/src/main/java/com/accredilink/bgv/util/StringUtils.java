package com.accredilink.bgv.util;

import java.util.Date;

public class StringUtils {

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean isDateNull(Date date) {
		if (date != null) {
			return true;
		}
		return false;
	}
}
