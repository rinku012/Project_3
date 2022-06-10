package in.co.rays.proj3.model;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.util.EmailBuilder;
import in.co.rays.proj3.util.EmailMessage;
import in.co.rays.proj3.util.EmailUtility;
import in.co.rays.proj3.util.HibernateDataSource;
import in.co.rays.proj3.util.JDBCDataSource;

/**
 * @author Rinku
 *
 */
public class UserModelHibImpl implements UserModelInt {

	private static Logger log = Logger.getLogger(UserModelHibImpl.class);

	/**
	 * Find User by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public UserDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (UserDTO) session.get(UserDTO.class, pk);
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}

		log.debug("Model findByPK End");
		return dto;
	}

	/**
	 * Find User by Login
	 *
	 * @param login : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	

	public UserDTO findByLogin(String login) throws ApplicationException {
		log.debug("find by login debug started");
		Session session = null;
		UserDTO dto = null;

		try {
			
			session = HibernateDataSource.getSession();
			
			Criteria crit = session.createCriteria(UserDTO.class);
			
			crit.add(Restrictions.eq("login", login));
			
			List list = crit.list();
			
			Iterator it = list.iterator();
			System.out.println("itrate");

			if (list.size() == 1) {
				dto = (UserDTO) list.get(0);
			}

			/*
			 * if (list.size() > 0) {dto = (UserDTO) list.get(0);} ---OR--- while
			 * (it.hasNext()) { UserDTO object = (UserDTO) it.next();} if (list.size() <= 0)
			 * {return dto;} dto = (UserDTO) list.get(0);
			 */

		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in getting User by Login");
		} finally {
			session.close();
		}

		log.debug("find by login completed");
		return dto;

	}

	/**
	 * Add a User
	 *
	 * @param dto
	 * @throws DatabaseException
	 *
	 */
	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {

		long pk = 0L;
		Session session = null;
		Transaction tx = null;

		// Check if updated LoginId already exist
		
		
		  UserDTO existLoginDto = findByLogin(dto.getLogin()); if (existLoginDto !=
		  null) { throw new DuplicateRecordException("login id already exist"); }
		 

		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			
			  session.save(dto);
			  pk = dto.getId();		 
			tx.commit();

		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add End");
		return dto.getId();
	}

	/**
	 * Update a User
	 *
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model update Started");
		Session session = null;
		Transaction tx = null;

		// Check if updated LoginId already exist
		/*
		 * UserDTO existLoginDto = findByLogin(dto.getLogin()); if (existLoginDto !=
		 * null && existLoginDto.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("login id already exist"); }
		 */

		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Update " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model Update End");

	}

	/**
	 * Delete a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void delete(UserDTO dto) throws ApplicationException {

		log.debug("Model update Started");
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete " + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model Delete End");

	}

	/**
	 * Searches Users with pagination
	 *
	 * @return list : List of Users
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");
		Session session = null;
		List list = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(UserDTO.class);

			if (dto.getId() > 0L) {
				crit.add(Restrictions.eq("Id", dto.getId()));
			}

			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				crit.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
			}

			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				crit.add(Restrictions.like("lastName", dto.getLastName() + "%"));
			}

			if (dto
					.getLogin() != null && dto.getLogin().length() > 0) {
				crit.add(Restrictions.like("login", dto.getLogin() + "%"));
			}

			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				crit.add(Restrictions.like("password", dto.getPassword() + "%"));
			}

			if (dto.getRoleId() > 0L) {
				crit.add(Restrictions.eq("roleId", dto.getRoleId()));
			}

			if (dto.getRoleName() != null && dto.getRoleName().length() > 0) {
				crit.add(Restrictions.like("roleName", dto.getRoleName() + "%"));
			}

			if (dto.getGender() != null && dto.getGender().length() > 0) {
				crit.add(Restrictions.like("gender", dto.getGender() + "%"));
			}

			if (dto.getDob() != null) {
				crit.add(Restrictions.eq("dob", dto.getDob()));
			}

			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				crit.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
			}

			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}

			list = crit.list();

		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Searches Userli
	 *
	 * @param dto : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(UserDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * get List of User with pagination
	 *
	 * @return list : List of Users
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model search Started");
		Session session = null;
		List list = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(UserDTO.class);

			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}

			list = crit.list();

		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * get List of User
	 *
	 * @return list : List of Users
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * 
	 * authenticate user
	 * 
	 * @param id  : long id
	 * @param old password : String oldPassword
	 * @param new password : String newPassword
	 * @throws DatabaseException
	 */

	public UserDTO authenticate(String login, String password) throws ApplicationException {
		log.debug("Model authenticate Started");
		System.out.println("Model Authenticate ");
		Session session = null;
		UserDTO dto = null;

		session = HibernateDataSource.getSession();
		Query q = session.createQuery("from UserDTO where login=? and password=? ");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (UserDTO) list.get(0);
		} else {
			dto = null;
		}
		log.debug("Model authenticate End");
		return dto;
	}

	/*
	 * By Using Criteria List list = null; try { session =
	 * HibernateDataSource.getSession(); Criteria crit =
	 * session.createCriteria(UserDTO.class); crit.add(Restrictions.eq("login",
	 * login)); crit.add(Restrictions.eq("password", password)); list = crit.list();
	 * Iterator it = list.iterator();
	 * 
	 * while (true) { if (!it.hasNext()) { if (list.size() > 0) {dto = (UserDTO)
	 * list.get(0);} else { dto = null; } break; } UserDTO var8 = (UserDTO)
	 * it.next(); } } catch (HibernateException var12) { throw new
	 * ApplicationExcepton("exception in user authenticaton" + var12.getMessage());
	 * } finally { session.close();} return dto; }
	 */

	/**
	 * Register a user
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when user already exists
	 */

	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("register debug started");
		long pk = 0L;

		try {
			URL url = new URL("https://www.google.com/");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			throw new ApplicationException("internet connection not available");
		}

		try {
			pk = add(dto);
			HashMap<String, String> map = new HashMap();
			map.put("login", dto.getLogin());
			map.put("password", dto.getPassword());
			map.put("firstName", dto.getFirstName());
			map.put("lastName", dto.getLastName());
			String message = EmailBuilder.getUserRegistrationMessage(map);
			EmailMessage msg = new EmailMessage();
			msg.setTo(dto.getLogin());
			msg.setSubject("Congratulations you have successfully registered with Rays Tech");
			msg.setMessage(message);
			msg.setMessageType(2);
			EmailUtility.sendMail(msg);

		} catch (ApplicationException var8) {
			throw new ApplicationException("Exception in regjister user" + var8.getMessage());
		}

		log.debug("Reg User debug completed");
		return pk;
	}

	/**
	 * Send the password of User to his Email
	 *
	 * @return boolean : true if success otherwise false
	 * @param login : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException : if user not found
	 */
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {

		boolean flag = false;

		try {
			URL url = new URL("https://www.google.com/");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			throw new ApplicationException("internet connection not available");
		}

		UserDTO existDto = findByLogin(login);
		if (existDto == null) {
			throw new RecordNotFoundException("Email Id Does not matched.");

		}

		HashMap<String, String> map = new HashMap();
		map.put("login", existDto.getLogin());
		map.put("password", existDto.getPassword());
		map.put("firstName", existDto.getFirstName());
		map.put("lastName", existDto.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(2);
		EmailUtility.sendMail(msg);
		flag = true;
		return flag;
	}

	/**
	 * Change Password By pk
	 *
	 * @param pk ,oldPassword,newPassword : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 */

	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		log.debug("change password debug started");
		System.out.println("User model changePassword Started");
		boolean flag = false;
		UserDTO existDto = null;

		try {
			URL url = new URL("https://www.google.com/");
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (Exception e) {
			throw new ApplicationException("internet connection not available");
		}

		existDto = findByPK(id);
		if (existDto != null && existDto.getPassword().equals(oldPassword)) {
			existDto.setPassword(newPassword);
			try {
				update(existDto);
			} catch (DuplicateRecordException e) {
				log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", existDto.getLogin());
		map.put("password", existDto.getPassword());
		map.put("firstName", existDto.getFirstName());
		map.put("lastName", existDto.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(existDto.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("Model changePassword End");
		return flag;

	}

	/**
	 * Lock User for certain time duration
	 *
	 * @return boolean : true if success otherwise false
	 * @param login : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException : if user not found
	 */

	public boolean lock(String login) throws RecordNotFoundException, ApplicationException {
		log.debug("Service lock Started");
		boolean flag = false;
		UserDTO dtoExist = null;
		try {
			dtoExist = findByLogin(login);
			if (dtoExist != null) {
				// dtoExist.setLock(UserDTO.ACTIVE);
				update(dtoExist);
				flag = true;
			} else {
				throw new RecordNotFoundException("LoginId not exist");
			}
		} catch (DuplicateRecordException e) {
			log.error("Application Exception..", e);
			throw new ApplicationException("Database Exception");
		}
		log.debug("Service lock End");
		return flag;
	}

	/**
	 * Get User Roles
	 *
	 * @return List : User Role List
	 * @param dto
	 * @throws ApplicationException
	 */

	public List getRoles(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDTO updateAccess(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Reset Password of User with auto generated Password
	 *
	 * @return boolean : true if success otherwise false
	 * @param login : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException : if user not found
	 */

	public boolean resetPassword(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

}
