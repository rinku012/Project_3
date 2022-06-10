package in.co.rays.proj3.dto;

/**
 * Course JavaBean encapsulates Course attributes
 * 
 * @author Builder
 * @version 1.0
 * @copyright (c) SunilOS
 * 
 */

public class CourseDTO extends BaseDTO {

	/**
	 * Name of Course
	 */
	private String courseName;
	/**
	 * Description of Course
	 */
	private String description;

	/**
	 * Acceser
	 */
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @Override public String getKey()
	 */
	public String getKey() {
		return id + "";
	}

	/**
	 * @Override public String getValue()
	 */
	public String getValue() {
		return courseName;
	}

}
