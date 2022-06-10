package in.co.rays.proj3.dto;

/**
 * Subject JavaBean encapsulates Subject attributes
 * 
 * @author Builder
 * @version 1.0
 * @copyright (c) SunilOS
 * 
 */

public class SubjectDTO extends BaseDTO {

	/**
	 *  Name of Course
	 */
	private String courseName;
	/**
	 *  ID of Course
	 */
	private long courseId;
	/**
	 *  Name of Subject
	 */
	private String subjectName;
	/**
	 *  ID of Subject
	 */
	private long subjectId;
	/**
	 * Description
	 */
	private String description;

	/**
	 * accessor
	 */

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
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
		return subjectName;
	}

}
