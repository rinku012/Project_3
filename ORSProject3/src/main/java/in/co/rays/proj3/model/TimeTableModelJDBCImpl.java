package in.co.rays.proj3.model;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.dto.TimeTableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of TimeTable Model
 * 
 * @author Builder
 * @version 1.0
 */

public class TimeTableModelJDBCImpl implements TimeTableModelInt {

	public static Logger log = Logger.getLogger(TimeTableModelJDBCImpl.class);

	/**
	 * Find next PK of TimeTable
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPk() throws DatabaseException {
		log.debug("nextpk debug started");
		int pk = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			rs = st.executeQuery("select max(id) from st_timetable");
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
     * Finds TimeTable by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws ApplicationException
     */
	
	public TimeTableDTO findByPk(long pk) throws ApplicationException {
		log.debug("findByPk debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_timetable where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setExamDate(rs.getDate(6));
				dto.setExamTime(rs.getString(7));
				dto.setSemester(rs.getInt(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByPk debug completed");
		return dto;
	}

	/**
	 * 
	 * Find TimeTable by Course Name, Subject Name
	 * 
	 * @param courseName, subjectName : get parameter
	 * 
	 * @return dto
	 * @throws ApplicationException
	 * 
	 */
	public TimeTableDTO findByCourseNameSubjectName(String courseName, String subjectName) throws ApplicationException {
		log.debug("findByCourseNameSubjectName debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_timetable where course_name=? and subject_name=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setString(2, subjectName);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setExamDate(rs.getDate(6));
				dto.setExamTime(rs.getString(7));
				dto.setSemester(rs.getInt(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RecordNotFoundException("no such record exist");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByCourseNameSubjectName debug completed");
		return dto;
	}

	/**
	 * Find TimeTable by courseId, ExamDate
	 * 
	 * @param courseName
	 * @param examDate
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByCourseNameExamDate(String courseName, Date examDate) throws ApplicationException {
		log.debug("findByCourseNameDate debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_timetable where course_name=? and exam_date=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setDate(2, new java.sql.Date(examDate.getTime())); // doubt
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setExamDate(rs.getDate(6));
				dto.setExamTime(rs.getString(7));
				dto.setSemester(rs.getInt(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("findByCourseNameDate debug completed");
		return dto;
	}

	/**
	 * Find TimeTable by SubjectName, courseName, ExamTime, ExamDate
	 * 
	 * @param subjectName
	 * @param courseName
	 * @param examTime
	 * @param examDate
	 * @return
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByCourseNameSubjectNameExamDateExamTime(String courseName, String subjectName,
			Date exameDate, String examTime) throws ApplicationException {
		log.debug("findByCourseNameSubjectNameDateTime debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"select * from st_timetable where course_name=? and subject_name=? and exam_date=? and exam_time=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setString(2, subjectName);
			pstm.setDate(3, new java.sql.Date(exameDate.getTime()));
			pstm.setString(4, examTime);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setExamDate(rs.getDate(6));
				dto.setExamTime(rs.getString(7));
				dto.setSemester(rs.getInt(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByCourseNameSubjectNameDateTime debug completed");
		// System.out.println(dto==null);
		return dto;
	}

	/**
     * Add a TimeTable
     *
     * @param dto
     * @throws DatabaseException
     * @throws ApplicationException
     */
	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("add debug started");
		int pk = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;

		// Add course name from course id for preload//
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couBean = new CourseDTO();
		couBean = couModel.findByPK(dto.getCourseId());
		String couName = couBean.getCourseName();

		// Add Subject Name by Subject id for preload//
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subBean = new SubjectDTO();
		subBean = subModel.findByPk(dto.getSubjectId());
		String subName = subBean.getSubjectName();

		dto = findByCourseNameSubjectName(couName, subName);
		if (dto != null) {
			throw new DuplicateRecordException("record already existed");
		}
		dto = findByCourseNameExamDate(couName, dto.getExamDate());
		if (dto != null) {
			throw new DuplicateRecordException("record already existed");
		}

		dto = findByCourseNameSubjectNameExamDateExamTime(couName, subName, dto.getExamDate(), dto.getExamTime());
		if (dto != null) {
			throw new DuplicateRecordException("Record already existed");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk = nextPk(); // Get auto-generated next primary key
			StringBuffer sql = new StringBuffer("insert into st_timetable values (?,?,?,?,?,?,?,?,?,?,?,?)");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setInt(1, pk);
			pstm.setString(2, couName);
			pstm.setLong(3, dto.getCourseId());
			pstm.setString(4, subName);
			pstm.setLong(5, dto.getSubjectId());
			pstm.setDate(6, new java.sql.Date(dto.getExamDate().getTime()));
			pstm.setString(7, dto.getExamTime());
			pstm.setInt(8, dto.getSemester());
			pstm.setString(9, dto.getCreatedBy());
			pstm.setString(10, dto.getModifiedBy());
			pstm.setTimestamp(11, dto.getCreatedDatetime());
			pstm.setTimestamp(12, dto.getModifiedDatetime());
			pstm.executeUpdate();
			conn.commit();
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
	 * Update a TimeTable
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : if updated TimeTable record is already
	 *                                  exist
	 */
	public void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("add debug started");
		int pk = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;

		// Add course name from course id for preload//
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couBean = new CourseDTO();
		couBean = couModel.findByPK(dto.getCourseId());
		String couName = couBean.getCourseName();

		// Add Subject Name by Subject id for preload//
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subBean = new SubjectDTO();
		subBean = subModel.findByPk(dto.getSubjectId());
		String subName = subBean.getSubjectName();

		dto = findByCourseNameSubjectName(couName, subName);
		if (dto != null && dto.getId() != dto.getId()) {
			throw new DuplicateRecordException("record already existed");
		}
		dto = findByCourseNameExamDate(couName, dto.getExamDate());
		if (dto != null && dto.getId() != dto.getId()) {
			throw new DuplicateRecordException("record already existed");
		}

		dto = findByCourseNameSubjectNameExamDateExamTime(couName, subName, dto.getExamDate(), dto.getExamTime());
		if (dto != null && dto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Record already existed");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"update st_timetable set course_name=?,course_id=?,subject_name=?,subject_id=?,exam_date=?,exam_time=?,semester=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, couName);
			pstm.setLong(2, dto.getCourseId());
			pstm.setString(3, subName);
			pstm.setLong(4, dto.getSubjectId());
			pstm.setDate(5, new java.sql.Date(dto.getExamDate().getTime()));
			pstm.setString(6, dto.getExamTime());
			pstm.setInt(7, dto.getSemester());
			pstm.setString(8, dto.getCreatedBy());
			pstm.setString(9, dto.getModifiedBy());
			pstm.setTimestamp(10, dto.getCreatedDatetime());
			pstm.setTimestamp(11, dto.getModifiedDatetime());
			pstm.setLong(12, dto.getId());
			pstm.executeUpdate();
			conn.commit();
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
	}
	
	/**
     * Deletes a TimeTable
     * 
     * @param dto
     * @throws ApplicationException
     */
	public void delete(TimeTableDTO dto) throws ApplicationException {
		log.debug("delete debug started");
		PreparedStatement pstm = null;
		Connection conn = null;
		ResultSet rs = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("delete from st_timetable where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, dto.getId());
			i = pstm.executeUpdate();
			conn.commit();
			if (i > 0) {
				System.out.println("data deleted successfully");
			} else {
				throw new DatabaseException("data not deleted");
			}

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
	 * Search TimeTable with pagination
	 *
	 * @return list : List of TimeTable
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("search debug started");
		ArrayList list = new ArrayList();
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");
		// dynamic injection return true value and at run time it will provide attribute
		// to append with the query

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" and ID=" + dto.getId());
			}

			// System.out.println(dto.getCourseName());

			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + dto.getCourseName() + "%'");
			}

			// System.out.println(dto.getSubjectName());

			if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
				sql.append(" and subject_name like'" + dto.getSubjectName() + "%'");
			}

			System.out.println("exam date" + dto.getExamDate());

			if (dto.getExamDate() != null && dto.getExamDate().getDate() > 0) {
				sql.append(" and exam_date = '" + DataUtility.getSearchDate(dto.getExamDate()) + "'");
			}

			if (dto.getExamTime() != null && dto.getExamTime().length() > 0) {
				sql.append(" and exam_time like'" + dto.getExamTime() + "%'");
			}
			if (dto.getSemester() > 0) {
				sql.append(" and semester=" + dto.getSemester());
			}
			if (dto.getCourseId() > 0) {
				sql.append(" and course_id=" + dto.getCourseId());
			}
			// System.out.println(dto.getSubjectId());
			if (dto.getSubjectId() > 0) {
				sql.append(" and subject_id=" + dto.getSubjectId());
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			PreparedStatement pstm = null;
			ResultSet rs = null;
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setExamDate(rs.getDate(6));
				dto.setExamTime(rs.getString(7));
				dto.setSemester(rs.getInt(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search debug completed");
		return list;
	}

	/**
	 * Search TimeTable
	 *
	 * @return list : List of TimeTable
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */
	public List search(TimeTableDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}


	/**
	 * get List of TimeTable with pagination
	 *
	 * @return list : List of TimeTable
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("list debug started");
		Connection conn = null;
		TimeTableDTO dto = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_timetable ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseId(rs.getLong(3));
				dto.setSubjectName(rs.getString(4));
				dto.setSubjectId(rs.getLong(5));
				dto.setExamDate(rs.getDate(6));
				dto.setExamTime(rs.getString(7));
				dto.setSemester(rs.getInt(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				list.add(dto);
			}
		} catch (Exception e) {
			// throw new RecordNotFoundException("exception in list search");
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("list debug completed");
		return list;
	}

	/**
	 * Gets List of TimeTable
	 *
	 * @return list : List of TimeTable
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
