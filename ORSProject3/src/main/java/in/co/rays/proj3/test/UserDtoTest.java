package in.co.rays.proj3.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.util.HibernateDataSource;

public class UserDtoTest {

	static UserModelInt model = ModelFactory.getInstance().getUserModel();

	public static void main(String[] args)
			throws DatabaseException, ParseException, DuplicateRecordException, ApplicationException {

		// testFindByPK();
		// testFindByLogin();
		 testadd();
//		 testupdate();
		// testDelete();
		// testSearch();
		// testList();
		// testAuthenticate();
		// testRegisterUser();
		// testforgetPassword();
		// testchangePassword();

	}

	public static void testchangePassword() throws ApplicationException {
		try {
			UserDTO dto = model.findByLogin("RJ@gmail.com");
			String oldPassword = dto.getPassword();
			dto.setId(1);                               
			dto.setPassword("Rinku@1234");
			dto.setConfirmPassword("Rinku@1234");
			String newPassword = dto.getPassword();
			try {
				model.changePassword(1L, oldPassword, newPassword);
				System.out.println("Password has been chang successfully");
			} catch (RecordNotFoundException e) {
				e.printStackTrace();

			} catch (ApplicationException e) {
				e.printStackTrace();

			}
		} finally {

		}
	}

	public static void testforgetPassword() {
		try {
			boolean b = model.forgetPassword("rinkuyadav03972@gmail.com");
			System.out.println("Suucess : Test Forget Password Success");
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testRegisterUser() throws ParseException {
		try {
			UserDTO bean = new UserDTO();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			// bean.setId();
			bean.setFirstName("vipin");
			bean.setLastName("kumawat");
			bean.setLogin("vishu5@gmail.com");
			bean.setPassword("Vipin");
			// bean.setConfirmPassword("4444");
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

	public static void testAuthenticate() {
		try {
			UserDTO bean = new UserDTO();
			bean.setLogin("RJ@gmail.com");
			bean.setPassword("RAHUL1234");
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

	public static void testSearch() {
		try {
			UserDTO bean = new UserDTO();
			List list = new ArrayList();
			bean.setFirstName("Ragav");
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

	public static void testDelete() {
		try {
			UserDTO bean = new UserDTO();
			long pk = 1L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByLogin() {
		try {
			UserDTO bean = new UserDTO();
			bean = model.findByLogin("vishu545@gmail.com");
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

	public static void testFindByPK() throws ApplicationException {
		try {
			UserDTO dto = new UserDTO();
			long pk = 1L;
			dto = model.findByPK(pk);
			if (dto == null) {
				System.out.println("find by pk fail");
			}
			System.out.println(dto.getId());
			System.out.println(dto.getFirstName());
			System.out.println(dto.getLastName());
			System.out.println(dto.getLogin());
			System.out.println(dto.getPassword());
			System.out.println(dto.getDob());
			System.out.println(dto.getRoleId());
			System.out.println(dto.getUnSuccessfulLogin());
			System.out.println(dto.getGender());
			System.out.println(dto.getLastLogin());
			System.out.println(dto.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testupdate() throws ApplicationException, ParseException, DuplicateRecordException {
		try {
			UserDTO dto = new UserDTO();
			SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
			dto = model.findByPK(1L);
			dto.setFirstName("Rinku");
			dto.setLastName("Yadav");
			dto.setDob(sdf.parse("9-7-1998"));
			dto.setPassword("Rinku@1234");
			dto.setLogin("RYA@gmail.com");

			model.update(dto);
			System.out.println("Update");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testadd() throws ParseException, DuplicateRecordException {

		try {

			UserDTO bean = new UserDTO();
			SimpleDateFormat sdf = new SimpleDateFormat("mm-DD-yyyy");
			// Date date =sdf.parse("05/24/1990");
			// bean.setId(1L);
			bean.setFirstName("Tarun");
			bean.setLastName("Jaiswal");
			bean.setLogin("TY@gmail.com");
			bean.setPassword("Tarun1234");
			bean.setDob(sdf.parse("04-5-1994"));
			bean.setRoleId(2);
			bean.setUnSuccessfulLogin(2);
			bean.setGender("FeMale");
			// bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setConfirmPassword("pass1234");
			bean.setRegisteredIP("5154565");
			bean.setLastLoginIP("kjsahf");
			bean.setMobileNo("7415781251");
			bean.setCreatedBy("kfaklafjl");
			bean.setModifiedBy("khdfahjfdh");

			long pk = model.add(bean);
			System.out.println("inserted");
			/*
			 * UserDTO addedbean = model.findByPK(pk); System.out.println("Test add succ");
			 * if (addedbean != null) { System.out.println("Test add successful"); }
			 */
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	/*
	 * SessionFactory sf = HibernateDataSource.getSessionFactory(); Session s =
	 * sf.openSession(); Transaction tx =null;
	 * 
	 * tx = s.beginTransaction();
	 * 
	 * UserDTO u = new UserDTO();
	 * 
	 * u.setFirstName("niraj"); u.setLastName("vyas"); u.setGender("Male");
	 * u.setPassword("5066"); u.setLogin("Niraj@gmail.com"); u.setDob();
	 * 
	 * s.save(u); tx.commit(); System.out.println("inserted"); s.close();
	 */

}
