package in.co.rays.proj3.dto;

import java.util.Date;

/**
 * Time Table JavaBean encapsulates Time Table attributes
 * 
 * @author Builder
 * @version 1.0
 * @copyright (c) SunilOS
 * 
 */

public class TimeTableDTO extends BaseDTO {
	
	
	/**
	 * Name of Course Name
	 */
	private String courseName;
	/**
	 * Course Id of Course
	 */
	private long courseId;
	/**
	 * Name of Subject
	 */
	private String subjectName;
	/**
	 * ID of Subject
	 */
	private long SubjectId;
	/**
	 * Name of Subject
	 */
	private Date examDate;
	/**
	 * Exam Time of Subject
	 */
	private String examTime;
	/**
	 * Name of Semester
	 */
	private int semester;
	
	/**
	 * Accessor
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
		return SubjectId;
	}

	public void setSubjectId(long subjectId) {
		SubjectId = subjectId;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
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
		return courseName + " " + subjectName;
	}

}
