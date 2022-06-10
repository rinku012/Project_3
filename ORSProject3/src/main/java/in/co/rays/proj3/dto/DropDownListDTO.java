package in.co.rays.proj3.dto;

/**
 * DropdownList interface is implemented by Beans those are used to create drop
 * down list on HTML pages
 * 
 * @author Builder
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */
public interface DropDownListDTO {

	/**
	 * Returns key of list element
	 * 
	 * @return key
	 */

	public String getKey();

	/**
	 * Returns display text of list element
	 * 
	 * @return value
	 */
	public String getValue();

}
