package in.co.rays.proj3.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;

/**
 * 
 * Subject Model Test classes
 * 
 * @author PAWAN_TECHNIQUES
 *
 */

public class SubjectModelTest {
	
	/**
	 * Model object to test	
	 */
	public static SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
	
	/**
     * Main method to call test methods.
     * 
     * @param args
	 * @throws ApplicationException 
	 * @throws DuplicateRecordException 
     * @throws ParseException
     */
	public static void main(String[] args) throws ApplicationException, DuplicateRecordException {
	
		 testAdd();
		// testDelete();
		// testUpdate();
		// testFindBySubjectName();
		// testFindByPk();
		// testSearch();
		// testList();
	}

	/**
	 * Tests Add a Subject
	 * 
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	
	public static void testAdd() throws DuplicateRecordException, ApplicationException {

		SubjectDTO bean = new SubjectDTO();
		
		bean.setCourseName("M.E");
		bean.setCourseId(5);
		bean.setSubjectName("humane science");
		bean.setSubjectId(104);
		bean.setDescription("masters in mangment");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.add(bean);
	}

	/**
	 * Tests delete a Subject
	 * 
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	
	public static void testDelete() throws ApplicationException {

		SubjectDTO bean = new SubjectDTO();
		
		bean.setId(25);
		model.delete(bean);
		System.out.println("Runing Delete Method");
	}

	/**
	 * Tests Update a Subject
	 * 
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	
	public static void testUpdate() throws DuplicateRecordException, ApplicationException {

		SubjectDTO bean = new SubjectDTO();
		
		bean.setModifiedBy("self");
		bean.setCreatedBy("self");
		bean.setSubjectName("economic");
		model.update(bean);
	}

	/**
	 * Tests find a Subject by Name.
	 */
	
	public static void testFindBySubjectName() throws ApplicationException {

		SubjectDTO bean = new SubjectDTO();
		
		bean = model.findBySubjectName("Basic Computer");
		System.out.println(bean.getId());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getModifiedDatetime());
	}

	/**
	 * Tests find a Subject by PK
	 */
	
	public static void testFindByPk() throws ApplicationException {

		SubjectDTO bean = new SubjectDTO();
		
		bean = model.findByPk(13);
		System.out.println(bean.getId());
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getModifiedDatetime());
	}

	/**
	 * Tests search a Subject by Name
	 */
	public static void testSearch() throws ApplicationException {

		SubjectDTO bean = new SubjectDTO();
		
		List list = new ArrayList();
		// bean.setCourseId(103);
		bean.setCourseName("B.E");
		// bean.setSubjectName("economic");
		// bean.setSubjectId(104);
		// bean.setId(1);
		list = model.search(bean, 1, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			SubjectDTO Bean = (SubjectDTO) it.next();
			System.out.print(" " + Bean.getCourseId());
			System.out.print(" " + Bean.getCourseName());
			System.out.print(" " + Bean.getSubjectId());
			System.out.print(" " + Bean.getSubjectName());
			System.out.print(" " + Bean.getDescription());
			System.out.print(" " + Bean.getId());
			System.out.println(" " + Bean.getCreatedBy());
		}
	}

	
	/**
	 * Tests get List a Course.
	 */
	
	public static void testList() throws ApplicationException  {
		List list = new ArrayList();
		SubjectDTO bean = new SubjectDTO();
		list = model.list();
		if (list.size() < 0) {
			System.out.println("Test list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (SubjectDTO) it.next();
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getId());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		}
	}
}
