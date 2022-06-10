package in.co.rays.proj3.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
 
/**
 * 
 * Student Model Test classes
 * 
 * @author PAWAN_TECHNIQUES
 *
 */
public class StudentModelTest {

	/**
	 * Model object to test	
	 */
	public static StudentModelInt model = ModelFactory.getInstance().getStudentModel();
	
	/**
     * Main method to call test methods.
     * 
     * @param args
     * @throws ParseException
     */
	
    public static void main(String[] args) throws ParseException {
        // testAdd();
         testDelete();
        // testUpdate();
        // testFindByPK();
        // testFindByEmailId();
        // testSearch();
        // testList();
    }

    /**
     * Tests add a student
     */
    public static void testAdd() throws ParseException {

        try {
            StudentDTO bean = new StudentDTO();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            // bean.setId(1L);
            bean.setFirstName("ram");
            bean.setLastName("kumawat");
            bean.setDob(sdf.parse("31/12/1990"));
            bean.setMobileNo("9165254357");
            bean.setEmail("vipin.chandore@nenosystems.com");
            bean.setCollegeId(2L);
            bean.setCreatedBy("Admin");
            bean.setModifiedBy("Admin");
            bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
            bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
            long pk = model.add(bean);
            StudentDTO addedbean = model.findByPk(pk);
            if (addedbean == null) {
                System.out.println("Test add fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests delete a Student
     */
    public static void testDelete() {

        try {
            StudentDTO bean = new StudentDTO();
            long pk = 14L;
            bean.setId(pk);
            model.delete(bean);
            StudentDTO deletedbean = model.findByPk(pk);
            if (deletedbean != null) {
                System.out.println("Test Delete fail");
            }
            System.out.println("Deleted");
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests update a student
     */
    public static void testUpdate() {

        try {
            StudentDTO bean = model.findByPk(14L);
            bean.setCollegeId(14L);
            bean.setFirstName("ankit");
            bean.setLastName("sharma");
            model.update(bean);

            StudentDTO updatedbean = model.findByPk(3L);
            if (!"rr".equals(updatedbean.getFirstName())) {
                System.out.println("Test Update fail");
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests find a Student by PK. 
     */
    public static void testFindByPK() {
        try {
            StudentDTO bean = new StudentDTO();
            long pk = 11L;
            bean = model.findByPk(pk);
            if (bean == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getFirstName());
            System.out.println(bean.getLastName());
            System.out.println(bean.getDob());
            System.out.println(bean.getMobileNo());
            System.out.println(bean.getEmail());
            System.out.println(bean.getCollegeId());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests find a Student by Emailid
     */
    public static void testFindByEmailId() {
        try {
            StudentDTO bean = new StudentDTO();
            bean = model.findByEmailId("bhavesh@gmail.com");
            if (bean != null) {
                System.out.println("Test Find By EmailId fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getFirstName());
            System.out.println(bean.getLastName());
            System.out.println(bean.getDob());
            System.out.println(bean.getMobileNo());
            System.out.println(bean.getEmail());
            System.out.println(bean.getCollegeId());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests get Search
     */
    public static void testSearch() {

        try {
            StudentDTO bean = new StudentDTO();
            List list = new ArrayList();
            bean.setFirstName("Aditya");
            list = model.search(bean, 0, 0);
            if (list.size() < 0) {
                System.out.println("Test Serach fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (StudentDTO) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getFirstName());
                System.out.println(bean.getLastName());
                System.out.println(bean.getDob());
                System.out.println(bean.getMobileNo());
                System.out.println(bean.getEmail());
                System.out.println(bean.getCollegeId());
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests get List.
     */
    public static void testList() {

        try {
            StudentDTO bean = new StudentDTO();
            List list = new ArrayList();
            list = model.list(1, 10);
            if (list.size() < 0) {
                System.out.println("Test list fail");
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                bean = (StudentDTO) it.next();
                System.out.println(bean.getId());
                System.out.println(bean.getFirstName());
                System.out.println(bean.getLastName());
                System.out.println(bean.getDob());
                System.out.println(bean.getMobileNo());
                System.out.println(bean.getEmail());
                System.out.println(bean.getCollegeId());
                System.out.println(bean.getCreatedBy());
                System.out.println(bean.getCreatedDatetime());
                System.out.println(bean.getModifiedBy());
                System.out.println(bean.getModifiedDatetime());
            }

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
    
}
	
