package model;

import java.sql.Time;
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

	public static boolean isThisDateValid(String day, String month, String year) {
		if (day.length() == 0 || month.length() == 0 || year.length() != 4) {
			//user did not input day, month or year correctly
			return false;
		} else {
			try {
				Integer.parseInt(day);
				Integer.parseInt(month);
				Integer.parseInt(year);
			} catch (NumberFormatException e2) {
				//user input letters
				return false;
			}
		}
		if (day.length() == 1) {
			//in case of unexpected formatting
			day = "0" + day;
		}
		if (month.length() == 1) {
			//in case of unexpected formatting
			month = "0" + month;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fullDate = day + "/" + month + "/" + year;
		sdf.setLenient(false);
		try {
			// if fullDate is not valid, this code will throw ParseException
			Date date = sdf.parse(fullDate);
			System.out.println(date);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	/**
	 * returns null if date is not valid
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public static Date sanitizeDate(String day, String month, String year){
		Date date = null;
		if(isThisDateValid(day, month, year)){
			if (day.length() == 1) {
				//in case of unexpected formatting
				day = "0" + day;
			}
			if (month.length() == 1) {
				//in case of unexpected formatting
				month = "0" + month;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String fullDate = day + "/" + month + "/" + year;
			try {
				date = sdf.parse(fullDate);
			} catch (ParseException e) {
				return date;
			}
		}
		return date;
	}

	public static boolean isThisTimeValid(String hours, String minutes) {
		try {
			// hours and minutes are both numbers
			int hoursInt = Integer.parseInt(hours);
			int minutesInt = Integer.parseInt(minutes);
			// check hours:
			if (hoursInt >= 0 && hoursInt <= 24) {
				if (minutesInt >= 0 && minutesInt <= 60) {
					return true;
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Data validation, isThisTimeValid(): could not parse input received");
		}
		// in all other cases, return false
		return false;
	}

	public static boolean checkLessThanTwenty(String username) {
		return (username.length() <= 20);
	}

	public static boolean checkLessThanThirty(String username) {
		return (username.length() <= 30);
	}
}
