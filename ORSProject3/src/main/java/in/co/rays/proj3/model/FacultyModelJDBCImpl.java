package in.co.rays.proj3.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.JDBCDataSource;


/**
 * @author Rinku
 *
 */
public class FacultyModelJDBCImpl implements FacultyModelInt {

	public static Logger log = Logger.getLogger(FacultyModelJDBCImpl.class);

	/**
	 * Find next PK of Faculty model (JDBC)
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		System.out.println("NextPk(JDBC) Method in College Model()");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_faculty");
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
	 * Find Faculty by PK
	 *
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * @throws DatabaseException 
	 */
	public FacultyDTO findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FacultyDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_faculty where ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLoginId(rs.getString(4));
				dto.setDoj(rs.getDate(5));
				dto.setMobileNo(rs.getString(6));
				dto.setCollegeName(rs.getString(7));
				dto.setCollegeId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSubjectId(rs.getLong(10));
				dto.setCreatedBy(rs.getString(11));
				dto.setModifiedBy(rs.getString(12));
				dto.setCreatedDatetime(rs.getTimestamp(13));
				dto.setModifiedDatetime(rs.getTimestamp(14));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	/**
	 * Find Faculty by Login
	 *
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public FacultyDTO findByLogin(String login) throws ApplicationException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FacultyDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_faculty where LOGIN_ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, login);
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLoginId(rs.getString(4));
				dto.setDoj(rs.getDate(5));
				dto.setMobileNo(rs.getString(6));
				dto.setCollegeName(rs.getString(7));
				dto.setCollegeId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSubjectId(rs.getLong(10));
				dto.setCreatedBy(rs.getString(11));
				dto.setModifiedBy(rs.getString(12));
				dto.setCreatedDatetime(rs.getTimestamp(13));
				dto.setModifiedDatetime(rs.getTimestamp(14));
				return dto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return dto;
	}

	/**
	 * Add a Faculty
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when Faculty already exists
	 */
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("add debug started");

		int pk = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;

		/////////// Add college name by college id in data base///////////
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeBean = new CollegeDTO();
		collegeBean = collegeModel.findByPK(dto.getCollegeId());
		String collegeName = collegeBean.getName();

		//////////////// Update subject Name by subject id in data base//////
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subBean = new SubjectDTO();
		subBean = subModel.findByPk(dto.getSubjectId());
		String subName = subBean.getSubjectName();

		dto = findByLogin(dto.getLoginId());
		if (dto != null) {
			throw new DuplicateRecordException("Faculty Login already exist..!!! ");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK(); // Get auto-generated next primary key
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("insert into st_faculty values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			pstm.setString(2, dto.getFirstName());
			pstm.setString(3, dto.getLastName());
			pstm.setString(4, dto.getLoginId());
			pstm.setDate(5, new java.sql.Date(dto.getDoj().getTime()));
			pstm.setString(6, dto.getMobileNo());
			pstm.setString(7, collegeName);
			pstm.setLong(8, dto.getCollegeId());
			pstm.setString(9, subName);
			pstm.setLong(10, dto.getSubjectId());
			pstm.setString(11, dto.getCreatedBy());
			pstm.setString(12, dto.getModifiedBy());
			pstm.setTimestamp(13, dto.getCreatedDatetime());
			pstm.setTimestamp(14, dto.getModifiedDatetime());
			pstm.executeUpdate();
			conn.commit(); // End transaction
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Update a Faculty
	 *
	 * @param dto
	 * @return 
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : if updated Faculty record is already exist
	 */
	public void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {

		int pk = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;

		/////////// Add college name by college id in data base///////////
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeBean = new CollegeDTO();
		collegeBean = collegeModel.findByPK(dto.getCollegeId());
		String collegeName = collegeBean.getName();

		//////////////// Update subject Name by subject id in data base//////
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subBean = new SubjectDTO();
		subBean = subModel.findByPk(dto.getSubjectId());
		String subName = subBean.getSubjectName();

		dto = findByLogin(dto.getLoginId());
		if (dto != null && dto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Faculty Login already exist..!!! ");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"update st_faculty set First_Name=?,Last_Name=?,Login_Id=?,Date_Of_Joining=?,Mobile_No=?,College_Name=?,COllege_Id=?,Subject_Name=?,Subject_Id=?,Created_By=?,Modified_By=?, Created_Datetime=?,Modified_Datetime=? where ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, dto.getFirstName());
			pstm.setString(2, dto.getLastName());
			pstm.setString(3, dto.getLoginId());
			pstm.setDate(4, new java.sql.Date(dto.getDoj().getTime()));
			pstm.setString(5, dto.getMobileNo());
			pstm.setString(6, collegeName);
			pstm.setLong(7, dto.getCollegeId());
			pstm.setString(8, subName);
			pstm.setLong(9, dto.getSubjectId());
			pstm.setString(10, dto.getCreatedBy());
			pstm.setString(11, dto.getModifiedBy());
			pstm.setTimestamp(12, dto.getCreatedDatetime());
			pstm.setTimestamp(13, dto.getModifiedDatetime());
			pstm.setLong(14, dto.getId());
			pstm.executeUpdate();
			conn.commit(); // End transaction
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
	}
	
	/**
	 * Delete a Faculty
	 *
	 * @param dto
	 * @return 
	 * @throws ApplicationException
	 */
	public void delete(FacultyDTO dto) throws ApplicationException {
		log.debug("delete debug started");
		PreparedStatement pstm = null;
		Connection conn = null;
		ResultSet rs = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("delete from st_faculty where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, dto.getId());
			i = pstm.executeUpdate();
			conn.commit(); // End transaction
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
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
	}

	/**
	 * Search Faculty with pagination
	 *
	 * @return list : List of Faculty
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("search debug started");
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstm = null;

		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");
		///// dynamic injection return true value and at run time it will provide
		///// attribute to append with the query
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" and ID=" + dto.getId());
			}
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				// System.out.println("first name of fac in
				// model"+dto.getFirstName());
				sql.append(" and first_name like '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				sql.append(" and last_name like '" + dto.getLastName() + "%'");
			}
			if (dto.getLoginId() != null && dto.getLoginId().length() > 0) {
				sql.append(" and login_id like '" + dto.getLoginId() + "%'");
			}
			if (dto.getDoj() != null && dto.getDoj().getDate() > 0) {
				sql.append(" and date_of_joining = '" + dto.getDoj());
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				sql.append(" and mobile_no like '" + dto.getMobileNo() + "%'");
			}
			if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
				sql.append(" and college_name like '" + dto.getCollegeName() + "%'");
			}
			if (dto.getCollegeId() > 0) {
				sql.append(" and college_id=" + dto.getCollegeId());
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
			ResultSet rs = null;
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLoginId(rs.getString(4));
				dto.setDoj(rs.getDate(5));
				dto.setMobileNo(rs.getString(6));
				dto.setCollegeName(rs.getString(7));
				dto.setCollegeId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSubjectId(rs.getLong(10));
				dto.setCreatedBy(rs.getString(11));
				dto.setModifiedBy(rs.getString(12));
				dto.setCreatedDatetime(rs.getTimestamp(13));
				dto.setModifiedDatetime(rs.getTimestamp(14));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search debug completed");
		// System.out.println("hi fac model "+list.size());
		return list;
	}

	/**
	 * Search Faculty
	 *
	 * @return list : List of Faculty
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	public List search(FacultyDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * get List of Faculty with pagination
	 *
	 * @return list : List of Faculty
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		PreparedStatement pstm = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_faculty ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			FacultyDTO dto = null;
			while (rs.next()) {
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setLoginId(rs.getString(4));
				dto.setDoj(rs.getDate(5));
				dto.setMobileNo(rs.getString(6));
				dto.setCollegeName(rs.getString(7));
				dto.setCollegeId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSubjectId(rs.getLong(10));
				dto.setCreatedBy(rs.getString(11));
				dto.setModifiedBy(rs.getString(12));
				dto.setCreatedDatetime(rs.getTimestamp(13));
				dto.setModifiedDatetime(rs.getTimestamp(14));
				list.add(dto);
			}
		} catch (Exception e) {
			// throw new RecordNotFoundException("exception in list search");
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	/**
	 * Gets List of Faculty
	 *
	 * @return list : List of Faculty
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
