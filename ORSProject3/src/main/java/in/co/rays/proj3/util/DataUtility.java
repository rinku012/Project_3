package in.co.rays.proj3.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Data Utility class to format data from one format to another
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class DataUtility {
	
	/**
	 * Application Date Format
	 */	
	public static final String APP_DATE_FORMAT = "MM/dd/yyyy";
	public static final String APP_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final String SEARCH_DATE_FORMAT="yyyy-MM-dd";
	
	/**
	 * Date formatter
	 */
	private static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);
	private static final SimpleDateFormat searchFormatter=new SimpleDateFormat(SEARCH_DATE_FORMAT);
	
	/**
	 * Trims and trailing and leading spaces of a String
	 * 
	 * @param val
	 * @return
	 */
	public static String getString(String value) {
		return DataValidator.isNotNull(value) ? value.trim() : value;
	}
	
	/**
	 * Converts and Object to String
	 * 
	 * @param val
	 * @return
	 */
	public static String getStringData(Object value) {
		return value != null ? value.toString() : "";
	}

	/**
	 * Converts String into Integer
	 * 
	 * @param val
	 * @return
	 */
	public static int getInt(String value) {
		return DataValidator.isInteger(value) ? Integer.parseInt(value) : 0;
	}
	
	/**
	 * Converts String into Long
	 * 
	 * @param val
	 * @return
	 */
	public static long getLong(String value) {
		return DataValidator.isLong(value) ? Long.parseLong(value) : 0L;
	}
	
	/**
	 * 
	 * Get time stamp in return timestamp format
	 * 
	 * @param l
	 * @return
	 */
	public static Timestamp getTimestamp(long l) {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(l);
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/**
	 * 
	 * Get Current Time Stamp
	 * 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeStamp;
	}
	
	/**
	 * Converts String into Date
	 * 
	 * @param val
	 * @return
	 */

	public static Date getDate(String val) {
		Date date = null;
		try {
			date = formatter.parse(val);
		} catch (Exception e) {

		}
		return date;
	}
	
	/**
	 * Converts Date into String
	 * 
	 * @param date
	 * @return
	 */

	public static String getDateString(Date date) {
		try {
			return formatter.format(date);
		} catch (Exception e) {
		}
		return "";
	}
	
	/**
	 * 
	 * get Current time Stamp return in long
	 * 
	 * @param tm
	 * @return
	 */

	public static long getTimestamp(Timestamp tm) {
		try {
			return tm.getTime();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * Converts String into TimeStamp
	 * 
	 * @param val
	 * @return
	 */

	public static Timestamp getTimestamp(String val) {

		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp((timeFormatter.parse(val)).getTime());
		} catch (Exception e) {
			return null;
		}
		return timeStamp;
	}

	/////////// time table list search////////////
	public static String getSearchDate(Date date) {
		try {
			return searchFormatter.format(date);
		} catch (Exception e) {
			return "";
		}
	}

}
