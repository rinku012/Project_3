package in.co.rays.proj3.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;

/**
 * 
 * Course Model Test classes
 * 
 * @author PAWAN_TECHNIQUES
 *
 */
public class CourseModelTest {
	
	// Model object to test	
	public static CourseModelInt model = ModelFactory.getInstance().getCourseModel();

	/**
	 * Main method to call test methods.
	 * 
	 * @param args
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	public static void main(String[] args) throws SQLException, DuplicateRecordException, ApplicationException 
	{	
		//testAdd();
		//testUpdate();
		//testDelete();
		//testFindByCourseName();
		//testFindByPk();
		//testSearch();
		// testList();
	}
	
	/**
	 * Tests add a Course
	 * 
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	public static void testAdd() throws SQLException, DuplicateRecordException, ApplicationException
	{
		CourseDTO bean=new CourseDTO();
		long i=0;
		bean.setCourseName("FASHION DESIGNING");
		bean.setDescription("provides specialization in FASHION DESIGN");
		bean.setCreatedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedBy("root");
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		i=model.add(bean);
		System.out.println("record added"+i);
	}
	
	/**
	 * Tests update a Course
	 * 
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	public static void testUpdate() throws ApplicationException, DuplicateRecordException
	{
		CourseDTO bean=new CourseDTO();
	    bean.setId(6);
		bean.setCourseName("TEST");
		bean.setDescription("TEST");
		bean.setCreatedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedBy("root");
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	    model.update(bean);
		System.out.println("update completed");
	}
	
	/**
	 * Tests delete a Course
	 * 
	 * @throws DuplicateRecordException
	 * @throws DatabaseException
	 */
	public static void testDelete() throws SQLException, ApplicationException
	{
		CourseDTO bean=new CourseDTO();
		bean.setId(6);
		model.delete(bean);	
		System.out.println("DELETED");
	}
	
	/**
	 * Tests find a Course by Name.
	 */
	public static void testFindByCourseName() throws SQLException, ApplicationException
	{
		CourseDTO bean=new CourseDTO();
		//bean.setCourseName("mba");
		bean=model.findByCourseName("M.Tec");
		System.out.println(bean.getCourseName());
		System.out.println(bean.getId());
		System.out.println(bean.getCreatedDatetime());
	    System.out.println(bean.getDescription());	
	}

	/**
	 * Tests find a Course by PK.
	 */
	public static void testFindByPk() throws SQLException, ApplicationException
	{
		CourseDTO bean=new CourseDTO();
		bean=model.findByPK(3);
		System.out.println(bean!=null);
		System.out.println(bean.getCourseName());
		System.out.println(bean.getId());
		System.out.println(bean.getDescription());
	}
	

	/**
	 * Tests search a Course by Name
	 */
	public static void testSearch() throws SQLException, ApplicationException
	{
        List list =new ArrayList();
        CourseDTO Bean=null;
		CourseDTO bean=new CourseDTO();
		//bean.setId(6);
		bean.setCourseName("M.E");
		list =model.search(bean, 1, 10);
		Iterator it = list.iterator();
		while(it.hasNext())
		{
		     Bean=(CourseDTO) it.next();
		     System.out.println(Bean.getCourseName());
		     System.out.println(Bean.getId());
		     System.out.println(Bean.getDescription());
		     System.out.println(Bean.getCreatedBy());
		     System.out.println(Bean.getModifiedBy());
		}
		System.out.println("search completed");
	}
	
	/**
	 * Tests get List a Course.
	 */
	public static void testList() {
		
		try {
			CourseDTO Bean = new CourseDTO();
			List list = new ArrayList();
			list =model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while(it.hasNext())
			{
			     Bean=(CourseDTO) it.next();
			     System.out.println(Bean.getCourseName());
			     System.out.println(Bean.getId());
			     System.out.println(Bean.getDescription());
			     System.out.println(Bean.getCreatedBy());
			     System.out.println(Bean.getModifiedBy());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
		
 
