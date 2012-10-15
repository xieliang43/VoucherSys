package com.voucher.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NumberUtil {

	private static final String DATE_REGEX = "EEE MMM d HH:mm:ss z yyyy";
	
	private static NumberUtil instance = new NumberUtil();
	
	private NumberUtil(){}
	
	public static NumberUtil getInstance() {
		return instance;
	}

	public String getKeyString(Date date, Integer id) {
		DateFormat df = new SimpleDateFormat(DATE_REGEX);
		String currenttime = df.format(date);

		return getDateline(currenttime) + String.format("%04d", id);
	}

	public Date toDate(String date, String pattern) {
		if (("" + date).equals("")) {
			return null;
		}
		if (pattern == null) {
			pattern = DATE_REGEX;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
		Date newDate = new Date();
		try {
			newDate = sdf.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return newDate;
	}

	public int getDateline(String date) {
		return (int) (toDate(date, DATE_REGEX).getTime() / 1000);
	}
}
