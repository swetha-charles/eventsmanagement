package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataValidation {
	public static boolean checkLessThanFifty(String... str) {
		for (String s : str) {
			if (s.length() > 51) {
				return false;
			}
		}

		return true;
	}

	private static String escape(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace("_", "\\_");
		str = str.replace("\n", "\\n");
		return str;
	}

	private static String unescape(String str) {
		str = str.replace("\\\\", "\\");
		str = str.replace("\\_", "_");
		str = str.replace("\\n", "\n");
		return str;
	}

	public static boolean isThisDateValid(String dateToValidate) {
		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static boolean checkLessThanTwenty(String username) {
		return (username.length()<= 20);
	}

	public static boolean checkLessThanThirty(String username) {
		return (username.length() <= 30);
	}
}
