package in.co.rays.proj3.model;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.dto.TimeTableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;

public class TimeTableModelHibImpl implements TimeTableModelInt {

	/**
	 * Finds TimeTable by PK
	 * 
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (TimeTableDTO) session.get(TimeTableDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in add timetable");
		} finally {
			session.close();
		}
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

		Session session = null;
		TimeTableDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("courseName", courseName));
			criteria.add(Restrictions.eq("subjectName", subjectName));
			List list = criteria.list();
			if (list.size() > 0) {
				dto = (TimeTableDTO) list.get(0);
			}

			if (list.size() > 0) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by subject & course name");
		} finally {
			session.close();
		}
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

		Session session = null;
		TimeTableDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(TimeTableDTO.class);
			crit.add(Restrictions.eq("courseName", courseName));
			crit.add(Restrictions.eq("examDate", examDate));
			List list = crit.list();
			if (list.size() > 0 && list != null) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find By Subject Name Course Name Exam Time");
		} finally {
			session.close();
		}

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

		Session session = null;
		TimeTableDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(TimeTableDTO.class);
			crit.add(Restrictions.eq("courseName", courseName));
			crit.add(Restrictions.eq("subjectName", subjectName));
			crit.add(Restrictions.eq("examDate", exameDate));
			crit.add(Restrictions.eq("examTime", examTime));

			List list = crit.list();
			if (list.size() > 0) {
				dto = (TimeTableDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find By CourseName Subject Name ExamDate ExamTime ");
		} finally {
			session.close();
		}
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
		// Set Course Name
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couDto = couModel.findByPK(dto.getCourseId());
		dto.setCourseName(couDto.getCourseName());
		String couName = couDto.getCourseName();
		System.out.println("Coruse Name : " + couName);

		// Set Subject Name
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subDto = subModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(subDto.getSubjectName());
		String subName = subDto.getSubjectName();
		System.out.println("Subject Name : " + subName);

		// Check for Duplicacy
		TimeTableDTO existDto_cs = findByCourseNameSubjectName(dto.getCourseName(), dto.getSubjectName());
		if (existDto_cs != null) {
			throw new DuplicateRecordException("record already existed");
		}

		TimeTableDTO existDto_cd = findByCourseNameExamDate(couName, dto.getExamDate());
		if (existDto_cd != null) {
			throw new DuplicateRecordException("record already existed");
		}

		TimeTableDTO existDto_csee = findByCourseNameSubjectNameExamDateExamTime(couName, subName, dto.getExamDate(),
				dto.getExamTime());
		if (existDto_csee != null) {
			throw new DuplicateRecordException("Record already existed");
		}

		long pk = 0l;
		Session sesssion = null;
		Transaction tx = null;

		try {
			sesssion = HibernateDataSource.getSession();
			tx = sesssion.beginTransaction();
			sesssion.save(dto);
			pk = dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("exception in add timetable");
		} finally {
			sesssion.close();
		}
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

		// Set Course Name
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couDto = couModel.findByPK(dto.getCourseId());
		dto.setCourseName(couDto.getCourseName());
		String couName = couDto.getCourseName();

		// Set Subject Name
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subDto = subModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(subDto.getSubjectName());
		String subName = subDto.getSubjectName();

		// Check for Duplicacy
		TimeTableDTO existDto_cs = findByCourseNameSubjectName(dto.getCourseName(), dto.getSubjectName());
		if (existDto_cs != null && existDto_cs.getId() != dto.getId()) {
			throw new DuplicateRecordException("record already existed");
		}

		TimeTableDTO existDto_cd = findByCourseNameExamDate(couName, dto.getExamDate());
		if (existDto_cd != null && existDto_cd.getId() != dto.getId()) {
			throw new DuplicateRecordException("record already existed");
		}

		TimeTableDTO existDto_csee = findByCourseNameSubjectNameExamDateExamTime(couName, subName, dto.getExamDate(),
				dto.getExamTime());
		if (existDto_csee != null && existDto_csee.getId() != dto.getId()) {
			throw new DuplicateRecordException("Record already existed");
		}

		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("exception in add timetable");
		} finally {
			session.close();
		}
	}

	/**
	 * Deletes a TimeTable
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(TimeTableDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("exception in delete timetable");
		}
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

		Session session = null;
		List list = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(TimeTableDTO.class);

			if (dto.getId() > 0L) {
				crit.add(Restrictions.eq("Id", dto.getId()));
			}

			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				crit.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
			}

			if (dto.getCourseId() > 0L) {
				crit.add(Restrictions.eq("courseId", dto.getCourseId()));
			}

			if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
				crit.add(Restrictions.like("subjectName", dto.getSubjectName() + "%"));
			}

			if (dto.getSubjectId() > 0L) {
				crit.add(Restrictions.eq("SubjectId", dto.getSubjectId()));
			}

			if (dto.getExamTime() != null && dto.getExamTime().length() > 0) {
				crit.add(Restrictions.like("examTime", dto.getExamTime() + "%"));
			}

			if (dto.getExamDate() != null && dto.getExamDate().getDate() > 0) {
				crit.add(Restrictions.eq("examDate", dto.getExamDate()));
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in search time table");
		} finally {
			session.close();
		}
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
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(TimeTableDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in list");
		}
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
