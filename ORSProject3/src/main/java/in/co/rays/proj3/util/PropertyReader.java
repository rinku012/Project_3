package in.co.rays.proj3.util;

import java.util.ResourceBundle;

/**
 * Read the property values from application properties file using Resource
 * Bundle
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class PropertyReader {
	
	/**
	 * Load Resource Bundle File
	 */
	public static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj3.bundle.System");
	
	/**
	 * 
	 * Return value of key
	 * 
	 * @param key
	 * @return
	 */
	
	public static String getValue(String key){
		String val = null;
        try {
            val = rb.getString(key);
        } catch (Exception e) {
            val = key;
        }
        return val;
	}
	
	/**
	 * 
	 * Gets String after placing param values
	 * 
	 * @param key
	 * @param param
	 * @return
	 */
	public static String getValue(String key, String param){
		String msg = getValue(key);
        msg = msg.replace("{0}", param);
		return msg;
	}

}
