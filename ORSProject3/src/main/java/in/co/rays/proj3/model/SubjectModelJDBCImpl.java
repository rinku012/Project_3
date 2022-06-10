package in.co.rays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.JDBCDataSource;


/**
 * @author Rinku
 *
 */
public class SubjectModelJDBCImpl implements SubjectModelInt {

	private static Logger log = Logger.getLogger(StudentModelJDBCImpl.class);

	/**
	 * Find next PK of Subject model (JDBC)
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		System.out.println("NextPk(JDBC) Method in Subject Model()");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_subject");
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
	 * Find Subject by PK
	 * 
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public SubjectDTO findByPk(long pk) throws ApplicationException {
		log.debug("findByPk debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		SubjectDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_subject where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setDescription(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("find by pk completed");
		return dto;
	}

	/**
	 * Find User by Subject Name
	 * 
	 * @param collage : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public SubjectDTO findBySubjectName(String subjectName) throws ApplicationException {
		log.debug("findBySubjectName debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		SubjectDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_subject where subject_name=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, subjectName);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setDescription(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findBySubjectName debug completed");
		return dto;
	}

	/**
	 * Add a Subject
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("add debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int pk = 0;

		// get Course Name
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couDto = couModel.findByPK(dto.getCourseId());
		String courseName = couDto.getCourseName();

		SubjectDTO existDto = findBySubjectName(dto.getSubjectName());
		if (existDto != null) {
			throw new DuplicateRecordException("Subject name already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("insert into st_subject values (?,?,?,?,?,?,?,?,?,?)");
			pstm.setLong(1, pk);
			pstm.setString(2, courseName);
			pstm.setLong(3, dto.getCourseId());
			pstm.setString(4, dto.getSubjectName());
			pstm.setLong(5, dto.getSubjectId());
			pstm.setString(6, dto.getDescription());
			pstm.setString(7, dto.getCreatedBy());
			pstm.setString(8, dto.getModifiedBy());
			pstm.setTimestamp(9, dto.getCreatedDatetime());
			pstm.setTimestamp(10, dto.getModifiedDatetime());
			pstm.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Update a Subject
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("add debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int pk = 0;

		// get Course Name
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couDto = couModel.findByPK(dto.getCourseId());
		String courseName = couDto.getCourseName();

		SubjectDTO existDto = findBySubjectName(dto.getSubjectName());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Subject name already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"update st_subject set course_name=?,course_id=?,subject_name=?,subject_id=?,description=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setLong(2, dto.getCourseId());
			pstm.setString(3, dto.getSubjectName());
			pstm.setLong(4, dto.getSubjectId());
			pstm.setString(5, dto.getDescription());
			pstm.setString(6, dto.getCreatedBy());
			pstm.setString(7, dto.getModifiedBy());
			pstm.setTimestamp(8, dto.getCreatedDatetime());
			pstm.setTimestamp(9, dto.getModifiedDatetime());
			pstm.setLong(10, dto.getId());
			long i = pstm.executeUpdate();
			conn.commit(); // End transaction
			pstm.close();
			if (i > 0) {
				System.out.println("record updated:" + i);
			} else {
				System.out.println("record not updated:" + i);
			}
		
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End");
	}

	/**
	 * Detete a Subject
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	public void delete(SubjectDTO dto) throws ApplicationException {
		log.debug("model delete started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("delete from st_subject where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, dto.getId());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("deleted:" + i);
			} else {
				System.out.println("data not deleted:" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete completed");
	}

	/**
	 * Searches Subjects with pagination
	 * 
	 * @return list : List of Subjects
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * 
	 * @throws DatabaseException
	 */

	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("search started");
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstm = null;

		StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" and ID=" + dto.getId());
			}
			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + dto.getCourseName() + "%'");
			}
			if (dto.getCourseId() > 0) {
				sql.append(" and course_id=" + dto.getCourseId());
			}
			if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
				sql.append(" and subject_name like '" + dto.getSubjectName() + "%'");
			}
			if (dto.getSubjectId() > 0) {
				sql.append(" and subject_id=" + dto.getSubjectId());
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
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setDescription(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
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
	 * Search Subject
	 * 
	 * @param dto : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(SubjectDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Get List of Subject with pagination
	 * 
	 * @return list : List of College
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("list debug started");
		Connection conn = null;
		SubjectDTO dto = null;
		PreparedStatement pstm = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_subject ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new SubjectDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setDescription(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
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
	 * Get List of Subject
	 * 
	 * @return list : List of College
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
