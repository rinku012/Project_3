package in.co.rays.proj3.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.UserModelInt;

/**
 * 
 * User Model Test classes
 * 
 * @author PAWAN_TECHNIQUES
 *
 */
public class UserModelTest {
	
	/**
	 * Model object to test
	 */
	static UserModelInt model = ModelFactory.getInstance().getUserModel();

	// Main method to call test methods.
	public static void main(String[] args) throws DatabaseException, ParseException, DuplicateRecordException {
		// model.nextPK();
		// testFindByPK();
		// testFindByLogin();
		//testAdd();
		// testUpdate();
		// testDelete();
		 //testSearch();
		// testList();
		 //testAuthenticate();
		// testRegisterUser();
		//testforgetPassword();
		// testchangePassword();
	}

	/**
	 * Tests find a User by PK.
	 */
	public static void testFindByPK() {
		try {
			UserDTO bean = new UserDTO();
			long pk = 5L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests find a User by Login.
	 */
	public static void testFindByLogin() {
		try {
			UserDTO bean = new UserDTO();
			bean = model.findByLogin("er.pa1.sharma@gmail.com");
			if (bean == null) {
				System.out.println("JDBC_Data Not Exist in Login (Bean is Null)");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests add a User
	 */
	public static void testAdd() throws ParseException, DuplicateRecordException {

		try {
			UserDTO bean = new UserDTO();
			SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
			// Date date =sdf.parse("05/24/1990");
			bean.setId(65L);
			bean.setFirstName("nishank");
			bean.setLastName("shrivashtav");
			bean.setLogin("binku@gmail.com");
			bean.setPassword("pass1234");
			bean.setDob(sdf.parse("06-23-1991"));
			bean.setRoleId(1);
			bean.setUnSuccessfulLogin(2);
			bean.setGender("Male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setConfirmPassword("pass1234");
			bean.setRegisteredIP("5154565");
			bean.setLastLoginIP("kjsahf");
			bean.setMobileNo("7415781251");
			bean.setCreatedBy("kfaklafjl");
			bean.setModifiedBy("khdfahjfdh");

			long pk = model.add(bean);
			/*
			 * UserDTO addedbean = model.findByPK(pk); System.out.println("Test add succ");
			 * if (addedbean != null) { System.out.println("Test add successful"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests update a User
	 */
	public static void testUpdate() {

		try {
			UserDTO bean = model.findByPK(60L);
			bean.setFirstName("test");
			bean.setLastName("gupta");
			bean.setPassword("6969695656");

			model.update(bean);
			System.out.println("Test Update succ");

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests delete a User
	 */
	public static void testDelete() {

		try {
			UserDTO bean = new UserDTO();
			long pk = 60L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests get Search
	 */
	public static void testSearch() {

		try {
			UserDTO bean = new UserDTO();
			List list = new ArrayList();
			bean.setFirstName("siddharth");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserDTO) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
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
			UserDTO bean = new UserDTO();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserDTO) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests authenticate User.
	 */
	public static void testAuthenticate() {

		try {
			UserDTO bean = new UserDTO();
			bean.setLogin("er.pa1.sharma@gmail.com");
			bean.setPassword("admin@123");
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
			if (bean != null) {
				System.out.println("Successfully login");
				System.out.println("Welcome : " + bean.getFirstName());

			} else {
				System.out.println("Invalied login Id & password");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests add a User register
	 * 
	 * @throws ParseException
	 */
	public static void testRegisterUser() throws ParseException {
		try {
			UserDTO bean = new UserDTO();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			bean.setId(111L);
			bean.setFirstName("vipin");
			// bean.setLastName("kumawat");
			bean.setLogin("vishu545@gmail.com");
			bean.setPassword("rr");
			bean.setConfirmPassword("4444");
			bean.setDob(sdf.parse("11/20/2015"));
			bean.setGender("Male");
			bean.setRoleId(2);
			bean.setCreatedBy("werkljsaak");
			long pk = model.registerUser(bean);
			System.out.println("Successfully register");
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			UserDTO registerbean = model.findByPK(pk);
			if (registerbean != null) {
				System.out.println("Test registation fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests change Password
	 */
	public static void testchangePassword() {

		try {
			UserDTO bean = model.findByLogin("pawan0881@gmail.com");
			String oldPassword = bean.getPassword();
			bean.setId(15);
			bean.setPassword("admin@123");
			bean.setConfirmPassword("admin@1234");
			String newPassword = bean.getPassword();
			try {
				model.changePassword(46L, oldPassword, newPassword);
				System.out.println("password has been change successfully");
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Tests fogetPassword
	 */
	public static void testforgetPassword() {
		try {
			boolean b = model.forgetPassword("neerajmalviya.217@gmail.com");
			System.out.println("Suucess : Test Forget Password Success");
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
