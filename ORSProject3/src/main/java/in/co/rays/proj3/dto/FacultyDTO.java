package in.co.rays.proj3.dto;

import java.util.Date;

/**
 * Faculty JavaBean encapsulates Faculty attributes
 * 
 * @author Builder
 * @version 1.0
 * @copyright (c) SunilOS
 * 
 */


public class FacultyDTO extends BaseDTO{
	
	
	/**
	 *  First Name of Faculty
	 */
	private String firstName;
	/**
	 * Last Name of Faculty
	 */
	private String lastName;
	/**
	 * login Id of Faculty
	 */
	private String loginId;
	/**
	 * Date of Joining for Faculty
	 */
	private Date doj;
	/**
	 * Mobile Number of Faculty
	 */
	private String mobileNo;
	/**
	 * College Name 
	 */
	private String collegeName;
	/**
	 * College ID
	 */
	private long collegeId;
	/**
	 * Subject Name
	 */
	private String subjectName;
	/**
	 * Subject ID
	 */
	private long subjectId;
	/**
	 * Course ID
	 */
	private long courseId;
	/**
	 * Course Name
	 */
	private String courseName;
	
	/**
	 * accessor
	 */

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public long getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
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
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
		return firstName + " " + lastName;
	}	

}
