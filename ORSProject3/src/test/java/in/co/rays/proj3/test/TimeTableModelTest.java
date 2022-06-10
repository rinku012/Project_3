package in.co.rays.proj3.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.TimeTableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.model.TimeTableModelInt;

/**
 * 
 * TimeTable Model Test classes
 * 
 * @author PAWAN_TECHNIQUES
 *
 */

public class TimeTableModelTest {
	/**
	 * Model object to test
	 */
	public static TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();

	/**
	 * Main method to call test methods.
	 * 
	 * @param args
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	
	public static void main(String[] args) throws Exception {
	
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindBySubNameCouName();
		// testFindcouNameExDate();
		// testCouNameSubNameExDateExTime();
		// testFindByPk();
		// testSearch();
		testList();
	}

	/**
	 * Tests add TimeTable
	 */

	public static void testAdd() throws ParseException {
		TimeTableDTO bean = new TimeTableDTO();
		long i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date = sdf.parse("05/11/2018");

		try {
			bean.setCourseName("MCA");
			bean.setCourseId(104);
			bean.setSubjectName("computer application");
			bean.setSubjectId(103);
			bean.setExamDate(date);
			bean.setExamTime("12:00"); // doubt
			bean.setSemester(1);
			bean.setCreatedBy("self");
			bean.setModifiedBy("self");
			bean.setCreatedDatetime(new Timestamp(new java.util.Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new java.util.Date().getTime()));
			model.add(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(i + ":record added");
	}

	public static void testDelete() throws Exception {
		TimeTableDTO bean = new TimeTableDTO();

		bean.setId(4);
		model.delete(bean);
		System.out.println("delete completed");
	}

	/**
	 * Tests Update TimeTable
	 */

	public static void testUpdate() throws ParseException, SQLException, DuplicateRecordException,
			RecordNotFoundException, ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		Date d1 = sdf.parse("08/05/2018");
		TimeTableDTO bean = new TimeTableDTO();

		bean.setId(4);
		bean.setCourseId(102);
		bean.setCourseName("B-Tech");
		bean.setSubjectId(103);
		bean.setSubjectName("Automobiles");
		bean.setExamDate(d1);
		bean.setExamTime("09:00");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setSemester(4);
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.update(bean);
		System.out.println("updated");
	}

	/**
	 * Tests TimeTable Find by Course and SubName
	 */

	public static void testFindBySubNameCouName() throws SQLException, RecordNotFoundException, ApplicationException {

		TimeTableDTO bean = new TimeTableDTO();

		bean = model.findByCourseNameSubjectName("MBA", "ECONOMICS");
		if (bean == null) {
			System.out.println("no such record found");
		} else {

			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getId());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		}
	}

	/**
	 * Tests TimeTable Find by Course and ExamDate
	 */
	
	public static void testFindcouNameExDate()
			throws ParseException, RecordNotFoundException, SQLException, ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		date = sdf.parse("05/01/1990");

		TimeTableDTO bean = new TimeTableDTO();

		bean = model.findByCourseNameExamDate("MB", date);
		System.out.println(bean == null);
		if (bean == null) {
			throw new RecordNotFoundException("such record not found in database");

		} else {
			System.out.println(bean.getId());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getSemester());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		}
	}

	/**
	 * Tests TimeTable Find by Course and SubName Exam date Exam Time
	 */
	
	public static void testCouNameSubNameExDateExTime()
			throws ParseException, SQLException, RecordNotFoundException, ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		date = sdf.parse("05/01/1990");
		TimeTableDTO bean = new TimeTableDTO();
		bean = model.findByCourseNameSubjectNameExamDateExamTime("MBA", "ECONOMICS", date, "15:00");
		// System.out.println(bean==null);
		if (bean == null) {
			throw new RecordNotFoundException("no such record found");
		} else {
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getId());
			System.out.println(bean.getCreatedBy());
		}
	}

	/**
	 * Tests TimeTable Find by PK
	 */
	
	public static void testFindByPk() throws SQLException, ApplicationException {

		TimeTableDTO bean = new TimeTableDTO();

		bean = model.findByPk(2);
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getExamDate());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getSemester());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		System.out.println(bean.getId());

		System.out.println("find by completed");

	}

	/**
	 * Tests TimeTable Search
	 */
	public static void testSearch() throws ParseException, SQLException, ApplicationException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse("09/27/2018");
		List list = new ArrayList();
		TimeTableDTO bean = new TimeTableDTO();

		// bean.setCourseId(102);
		// bean.setCourseName("BCA");
		// bean.setSubjectId(103);
		// bean.setSubjectName("Economics");
		// bean.setExamDate(date); //doubt
		// bean.setId(2);
		list = model.search(bean, 1, 1);
		Iterator it = list.iterator();
		TimeTableDTO Bean = null;
		while (it.hasNext()) {
			Bean = (TimeTableDTO) it.next();
			System.out.println(Bean.getCourseId());
			System.out.println(Bean.getCourseName());
			System.out.println(Bean.getSubjectId());
			System.out.println(Bean.getSubjectName());
			System.out.println(Bean.getExamTime());
			System.out.println(Bean.getExamDate());
			System.out.println(Bean.getSemester());
			System.out.println(Bean.getCreatedBy());

		}
		System.out.println("test search completed");
	}

	/**
	 * Tests TimeTable List
	 */
	
	public static void testList() throws SQLException, ApplicationException {
		List list = new ArrayList();
		TimeTableDTO bean = new TimeTableDTO();

		list = model.list(1, 4);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (TimeTableDTO) it.next();
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getId());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		}
	}

}
