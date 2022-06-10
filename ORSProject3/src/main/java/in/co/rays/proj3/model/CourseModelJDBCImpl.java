package in.co.rays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of Course Model
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class CourseModelJDBCImpl implements CourseModelInt {

	private static Logger log = Logger.getLogger(CourseModelJDBCImpl.class);

	/**
	 * Find next PK of Course model (JDBC)
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		System.out.println("NextPk(JDBC) Method in Course Model()");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_course");
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
	 * Find Course by ID
	 * 
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public CourseDTO findByPK(long pk) throws ApplicationException {
		log.debug("findbypk started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CourseDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_course where ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();

			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findbypk completed");
		return dto;
	}

	/**
	 * Find Course by Name
	 * 
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public CourseDTO findByCourseName(String courseName) throws ApplicationException {
		log.debug("findByCourseName debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CourseDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_course where coursename=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			rs = pstm.executeQuery();

			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByCourseName debug completed");
		return dto;
	}
	
	/**
	 * Add a Course
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		
		log.debug("add debug started");
		Connection conn = null;
		long pk = 0;
		
		// Check if create Role already exist
		CourseDTO duplicataCourse = findByCourseName(dto.getCourseName());
		if (duplicataCourse != null) {
			throw new DuplicateRecordException("Course already exist...!!!");
		}
		
		try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK(); // Get auto-generated next primary key
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement("insert into st_course values (?,?,?,?,?,?,?)");
				pstmt.setLong(1, pk);
				pstmt.setString(2, dto.getCourseName());
				pstmt.setString(3, dto.getDescription());
				pstmt.setString(4, dto.getCreatedBy());
				pstmt.setString(5, dto.getModifiedBy());
				pstmt.setTimestamp(6, dto.getCreatedDatetime());
				pstmt.setTimestamp(7, dto.getModifiedDatetime());
				pstmt.executeUpdate();
				conn.commit(); // End transaction
				pstmt.close();
				
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Update a Course
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("next pk debug started");
		Connection conn = null;
		
		// Check if updated College already exist
		CourseDTO dtoExist = findByCourseName(dto.getCourseName());
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Course is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_course set coursename=?,description=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstmt.setString(1, dto.getCourseName());
			pstmt.setString(2, dto.getDescription());
			pstmt.setString(3, dto.getCreatedBy());
			pstmt.setString(4, dto.getModifiedBy());
			pstmt.setTimestamp(5, dto.getCreatedDatetime());
			pstmt.setTimestamp(6, dto.getModifiedDatetime());
			pstmt.setLong(7, dto.getId());
			int i = pstmt.executeUpdate();
			System.out.println("update ho gaya"+i);
			if (i > 0) {
				System.out.println("record updated:" + i);
			} else {
				System.out.println("record not updated");
			}
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
	}

	/**
	 * Delete a Course
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(CourseDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("delete from st_course where id=?");
			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Search Course with pagination
	 * 
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */
	public List search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("search started");
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer("select * from st_course where 1=1"); 
		// dynamic injection return true value and at run time it will provide attribute to append with the query 
		if (dto != null) {
			if (dto.getId() > 0) {
				// System.out.println("hi model in if id");
				sql.append(" and ID=" + dto.getId());
			}
			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				sql.append(" and coursename like '" + dto.getCourseName() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			CourseDTO bean = null;
			while (rs.next()) {
				bean = new CourseDTO();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search completed");
		return list;
	}

	/**
	 * Search Course
	 * 
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(CourseDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Get List of College with pagination
	 * 
	 * @return list : List of College
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("list debug started");
		Connection conn = null;
		CourseDTO dto = null;
		PreparedStatement pstm = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_course ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));		
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("list debug completed");
		return list;
	}

	/**
	 * Get List of College
	 * 
	 * @return list : List of College
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
