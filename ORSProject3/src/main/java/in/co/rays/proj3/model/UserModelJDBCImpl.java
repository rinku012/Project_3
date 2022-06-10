package in.co.rays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.util.EmailBuilder;
import in.co.rays.proj3.util.EmailMessage;
import in.co.rays.proj3.util.EmailUtility;
import in.co.rays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of UserModel
 * 
 * @author Builder
 * @version 1.0
 * @Copyright (c)SunilOS
 */

public class UserModelJDBCImpl implements UserModelInt {

	private static Logger log = Logger.getLogger(UserModelJDBCImpl.class);

	/**
	 * Role ID
	 */

	private long roleid;

	/**
	 * accessor
	 */
	

	public long getRoleid() {
		return roleid;
	}

	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}

	/**
	 * Find next PK of user model (JDBC)
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		System.out.println("NextPk(JDBC) Method in UserModel()");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("JDBC_PK : " + (pk + 1));
		return pk + 1;
	}

	/**
	 * Find User by PK
	 * 
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public UserDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");

		System.out.println("findByPK(JDBC) Method in UserModel()");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		UserDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setUnSuccessfulLogin(rs.getInt(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
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

		log.debug("Model findByLogin Started");

		System.out.println("JDBC_findByLogin Method in UserModel()");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		UserDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setUnSuccessfulLogin(rs.getInt(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return dto;
	}

	/**
	 * Add a User
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * 
	 */
	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		System.out.println("Add(JDBC) Method in UserModel()");

		Connection conn = null;
		int pk = 0;

		UserDTO existbean = findByLogin(dto.getLogin());

		if (existbean != null) {
			throw new DuplicateRecordException("Login Id already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK(); // Get auto-generated next primary key
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, dto.getFirstName());
			pstmt.setString(3, dto.getLastName());
			pstmt.setString(4, dto.getLogin());
			pstmt.setString(5, dto.getPassword());
			pstmt.setDate(6, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(7, dto.getMobileNo());
			pstmt.setLong(8, dto.getRoleId());
			pstmt.setInt(9, dto.getUnSuccessfulLogin());
			pstmt.setString(10, dto.getGender());
			pstmt.setTimestamp(11, dto.getLastLogin());
			pstmt.setString(12, dto.getLock());
			pstmt.setString(13, dto.getRegisteredIP());
			pstmt.setString(14, dto.getLastLoginIP());
			pstmt.setString(15, dto.getCreatedBy());
			pstmt.setString(16, dto.getModifiedBy());
			pstmt.setTimestamp(17, dto.getCreatedDatetime());
			pstmt.setTimestamp(18, dto.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Update a user
	 * 
	 * @param dto
	 * @throws DuplicateRecordException
	 * @throws Application              Exception
	 */

	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		System.out.println("Update (JDBC)  Method in UserModel()");
		UserDTO beanExist = findByLogin(dto.getLogin());

		if (beanExist != null && !(beanExist.getId() == dto.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			String sql = "UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getFirstName());
			pstmt.setString(2, dto.getLastName());
			pstmt.setString(3, dto.getLogin());
			pstmt.setString(4, dto.getPassword());
			pstmt.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(6, dto.getMobileNo());
			pstmt.setLong(7, dto.getRoleId());
			pstmt.setInt(8, dto.getUnSuccessfulLogin());
			pstmt.setString(9, dto.getGender());
			pstmt.setTimestamp(10, dto.getLastLogin());
			pstmt.setString(11, dto.getLock());
			pstmt.setString(12, dto.getRegisteredIP());
			pstmt.setString(13, dto.getLastLoginIP());
			pstmt.setString(14, dto.getCreatedBy());
			pstmt.setString(15, dto.getModifiedBy());
			pstmt.setTimestamp(16, dto.getCreatedDatetime());
			pstmt.setTimestamp(17, dto.getModifiedDatetime());
			pstmt.setLong(18, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Delete a User
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */

	public void delete(UserDTO dto) throws ApplicationException {
		log.debug("Model delete Started");

		System.out.println("delete (JDBC) Method in UserModel()");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from ST_USER where ID=?");
			ps.setLong(1, dto.getId());
			System.out.println("rows :" + ps.executeUpdate());
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Search User with pagination
	 * 
	 * @return list : List of Users
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * 
	 * @throws Application Exception
	 */

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");

		System.out.println("Search Method in UserModel()");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + dto.getLastName() + "%'");
			}
			if (dto.getLogin() != null && dto.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + dto.getLogin() + "%'");
			}
			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + dto.getPassword() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getGender());
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + dto.getMobileNo());
			}
			if (dto.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + dto.getRoleId());
			}
			if (dto.getUnSuccessfulLogin() > 0) {
				sql.append(" AND UNSUCCESSFUL_LOGIN = " + dto.getUnSuccessfulLogin());
			}
			if (dto.getGender() != null && dto.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + dto.getGender() + "%'");
			}
			if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
				sql.append(" AND LAST_LOGIN = " + dto.getLastLogin());
			}
			if (dto.getRegisteredIP() != null && dto.getRegisteredIP().length() > 0) {
				sql.append(" AND REGISTERED_IP like '" + dto.getRegisteredIP() + "%'");
			}
			if (dto.getLastLoginIP() != null && dto.getLastLoginIP().length() > 0) {
				sql.append(" AND LAST_LOGIN_IP like '" + dto.getLastLoginIP() + "%'");
			}
		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setUnSuccessfulLogin(rs.getInt(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
	}

	/**
	 * Search User
	 * 
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(UserDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Get List of User with Pagination
	 * 
	 * @return list : List of Users
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * 
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list Started");

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from ST_USER");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				UserDTO dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setUnSuccessfulLogin(rs.getInt(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

	/**
	 * Get List of User
	 * 
	 * @param bean : Search Parameters
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
	 * @param     new password : String newPassword
	 * @throws DatabaseException
	 */

	public UserDTO authenticate(String login, String password) throws ApplicationException {
		log.debug("Model authenticate Started");

		System.out.println("authenticate Method in UserModel()");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
		UserDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLogin(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setUnSuccessfulLogin(rs.getInt(9));
				dto.setGender(rs.getString(10));
				dto.setLastLogin(rs.getTimestamp(11));
				dto.setLock(rs.getString(12));
				dto.setRegisteredIP(rs.getString(13));
				dto.setLastLoginIP(rs.getString(14));
				dto.setCreatedBy(rs.getString(15));
				dto.setModifiedBy(rs.getString(16));
				dto.setCreatedDatetime(rs.getTimestamp(17));
				dto.setModifiedDatetime(rs.getTimestamp(18));
			}
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model authenticate End");
		return dto;
	}
	

	/**
	 * Register a user
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when user already exists
	 */

	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");

		System.out.println("Register User Method in UserModel()");

		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("Model registerUser End");
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
		log.debug("Model forgetPassword start");
		System.out.println("forgetPassword Method in UserModel()");

		UserDTO userData = findByLogin(login);
		boolean flag = false;

		if (userData == null) {
			throw new RecordNotFoundException("Email ID does not exists !");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstName());
		map.put("lastName", userData.getLastName());
		
		String message = EmailBuilder.getForgetPasswordMessage(map);
		
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(msg);
		
		flag = true;
		log.debug("Model forgetpassword End");
		return flag;
	}

	/**
	 * 
	 * Change Password User
	 * 
	 * @param id          : long id
	 * @param old         password : String oldPassword
	 * @param newpassword : String newPassword
	 * @throws DatabaseException
	 */

	public boolean changePassword(Long pk, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {
		
	log.debug("model changePassword Started");
		
		System.out.println("changepassword Method in UserModel()");

		boolean flag = false;
		UserDTO beanExist = null;

		beanExist = findByPK(pk);
		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (DuplicateRecordException e) {
				log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("Model changePassword End");
		return flag;
	}

	public boolean lock(String login) throws RecordNotFoundException, ApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

	public List getRoles(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDTO updateAccess(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean resetPassword(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return false;
	}

}
