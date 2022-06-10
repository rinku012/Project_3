package in.co.rays.proj3.util;

import java.util.Date;

import in.co.rays.proj3.util.DataUtility;

/**
 * 
 * This class validates input data
 * 
 * @author Rinku
 *
 */
public class DataValidator {

	/**
	 * 
	 * Checks if value is Null
	 * 
	 * @param val
	 * @return
	 */
	public static Boolean isNull(String value) {
		return value != null && value.trim().length() != 0 ? false : true;
	}

	/**
	 * 
	 * Checks if value is NOT Null
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isNotNull(String value) {
		return !isNull(value);
	}

	/**
	 * 
	 * Checks if value is an Integer
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isInteger(String val) {
		if (isNotNull(val)) {
			try {
				int i = Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Checks if value is Long
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				long i = Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Checks if value is valid Email ID
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEmail(String val) {
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is Password
	 * 
	 * This regex will enforce these rules: At least one upper case English letter,
	 * (?=.*?[A-Z]) At least one lower case English letter, (?=.*?[a-z]) At least
	 * one digit, (?=.*?[0-9]) At least one special character, (?=.*?[#?!@$%^&*-])
	 * Minimum eight in length .{4,} (with the anchors)
	 * 
	 * @param val
	 * @return boolean
	 */

	public static boolean isPassword(String val) {
		// String passregex =
		// "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,}$";
		String passregex = "^[a-zA-Z0-9](?=.*?[#?!@$%^&*-]).{4,}$";
		return val.matches(passregex) ? true : false;
	}

	/**
	 * 
	 * Checks if value is valid Date
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isDate(String val) {
		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

	/**
	 * Checks if value is name
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isName(String val) {
		String name = "^[A-Za-z]{2,25}$";
		return val.matches(name) ? true : false;
	}

	/**
	 * Checks if value is name
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isNameNumber(String val) {

		String name = "^[a-zA-Z0-9._ ]{2,50}+$";
		return val.matches(name) ? true : false;

	}

	/**
	 * Checks if value is Mobile Number
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isMobileNo(String val) {
		String mobreg = "(0/91)?[6-9][0-9]{9}";
		return val.matches(mobreg) ? true : false;
	}

	public static boolean isPhoneNo(String val) {
    	String phonereg = "(0/+91)?[0-9-][0-9-]{9,12}";
    	return val.matches(phonereg) ? true : false;
    }
}
