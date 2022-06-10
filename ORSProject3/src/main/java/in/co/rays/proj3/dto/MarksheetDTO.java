package in.co.rays.proj3.dto;

/**
 * Marksheet JavaBean encapsulates Marksheet attributes
 * 
 * @author Builder
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class MarksheetDTO extends BaseDTO {

	/**
	 * Rollno of Student
	 */
	private String rollNo;
	/**
	 * ID of Student
	 */
	private long studentId;
	/**
	 * Name of Student
	 */
	private String name;
	/**
	 * Physics marks of Student
	 */
	private String physics;
	/**
	 * Chemistry marks of Student
	 */
	private String chemistry;
	/**
	 * Maths marks of Student
	 */
	private String maths;

	/**
	 * accessor
	 */

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhysics() {
		return physics;
	}

	public void setPhysics(String physics) {
		this.physics = physics;
	}

	public String getChemistry() {
		return chemistry;
	}

	public void setChemistry(String chemistry) {
		this.chemistry = chemistry;
	}

	public String getMaths() {
		return maths;
	}

	public void setMaths(String maths) {
		this.maths = maths;
	}

	/**
	 * @Override public String getKey()
	 */
	public String getKey() {
		return id + " ";
	}

	/**
	 * @Override public String getValue()
	 */
	public String getValue() {
		return rollNo;
	}

}
