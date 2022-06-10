
package in.co.rays.proj3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Factory of Model classes
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 *
 */

public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj3.bundle.System");
	/**
	 * DataBse of Model classes
	 */
	private static final String DATABASE;
	private static ModelFactory mFactory;
	
	
	
	
	/**
	 * Cache of Model classes
	 */
	private static HashMap modelCache;

	static {
		DATABASE = rb.getString("DATABASE");
		mFactory = null;
		modelCache = new HashMap();
	}

	/**
	 * Constructor is private so no other class can create instance of Model Locator
	 */
	private ModelFactory() {

	}

	/**
	 * Get the instance of Model Locator
	 *
	 * @return mFactory
	 */
	public static ModelFactory getInstance() {
		
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	/**
	 * Get the instance of User Model
	 * 
	 * @return userModel
	 * 
	 */
	public UserModelInt getUserModel() {
		UserModelInt userModel = (UserModelInt) modelCache.get("UserModel");
		if (userModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				userModel = new UserModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("UserModel", userModel);
		}
		return userModel;
	}

	/**
	 * Get instance of Role Model
	 *
	 * @return RoleModel
	 */
	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("RoleModel");
		if (roleModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				roleModel = new RoleModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("RoleModel", roleModel);
		}
		return (RoleModelInt) roleModel;
	}

	/**
	 * Get instance of College Model
	 *
	 * @return courseModel
	 */
	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("CollegeModel");
		if (collegeModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("CollegeModel", collegeModel);
		}

		return (CollegeModelInt) collegeModel;
	}

	/**
	 * Get instance of Course Model
	 *
	 * @return courseModel
	 */
	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("CourseModel");
		if (courseModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				courseModel = new CourseModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("CourseModel", courseModel);
		}
		return (CourseModelInt) courseModel;
	}

	/**
	 * Get instance of Student Model
	 *
	 * @return courseModel
	 */
	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("StudentModel");
		if (studentModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				studentModel = new StudentModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("StudentModel", studentModel);
		}
		return (StudentModelInt) studentModel;
	}

	/**
	 * Get instance of Subject Model
	 *
	 * @return courseModel
	 */
	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}
		return (SubjectModelInt) subjectModel;
	}

	/**
	 * Get instance of Faculty Model
	 *
	 * @return courseModel
	 */
	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}
		return (FacultyModelInt) facultyModel;
	}

	/**
	 * Get instance of Marksheet Model
	 *
	 * @return courseModel
	 */
	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("MarksheetModel");
		if (marksheetModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("MarksheetModel", marksheetModel);
		}
		return (MarksheetModelInt) marksheetModel;
	}

	/**
	 * Get instance of Marksheet Model
	 *
	 * @return courseModel
	 */
	public TimeTableModelInt getTimeTableModel() {
		TimeTableModelInt timeTableModel = (TimeTableModelInt) modelCache.get("TimeTableModel");
		if (timeTableModel == null) {
			if ("HIBERNATE".equals(DATABASE)) {
				timeTableModel = new TimeTableModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				timeTableModel = new TimeTableModelJDBCImpl();
			}
			modelCache.put("TimeTableModel", timeTableModel);
		}
		return (TimeTableModelInt) timeTableModel;
	}
}
