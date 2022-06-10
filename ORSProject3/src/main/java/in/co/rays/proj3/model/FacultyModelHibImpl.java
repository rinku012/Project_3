package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;

public class FacultyModelHibImpl implements FacultyModelInt {

	/**
	 * Find Faculty by PK
	 *
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * @throws DatabaseException 
	 */
	
	/**
	 *author Rinku
	 */
	public FacultyDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		FacultyDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (FacultyDTO) session.get(FacultyDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by pk");
		} finally {
			session.close();
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
		Session session = null;
		List list = null;
		FacultyDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(FacultyDTO.class);
			crit.add(Restrictions.eq("loginId", login));
			list = crit.list();
			if (list.size() > 0) {
				dto = (FacultyDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by login");
		} finally {
			session.close();
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
		
		// Set College Name
		CollegeModelInt collModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collDto = collModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collDto.getName());
		
		// Set College Name
		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subDto = subModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(subDto.getSubjectName());
		
		FacultyDTO existDto = findByLogin(dto.getLoginId());
		if (existDto != null) {
			throw new DuplicateRecordException("login id already exist");
		} 
		
		Session session = null;
		Transaction tx = null;
		long pk = 0L;
		
		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("exception in add faculty");

		} finally {
			session.close();
		}
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
		
		// Set College Name
		/*
		 * CollegeModelInt collModel = ModelFactory.getInstance().getCollegeModel();
		 * CollegeDTO collDto = collModel.findByPK(dto.getCollegeId());
		 * dto.setCollegeName(collDto.getName());
		 */
		
		// Set College Name
		/*
		 * SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		 * SubjectDTO subDto = subModel.findByPk(dto.getSubjectId());
		 * dto.setSubjectName(subDto.getSubjectName());
		 */
		
		FacultyDTO existDto = findByLogin(dto.getLoginId());
		if (existDto != null  && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("login id already exist");
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
			throw new ApplicationException("exception in update faculty");
		} finally {
			session.close();
		}
	  
	}

	/**
	 * Delete a Faculty
	 *
	 * @param dto
	 * @return 
	 * @throws ApplicationException
	 */
	public void delete(FacultyDTO dto) throws ApplicationException {
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
			throw new ApplicationException("exception in delete faculty");
		} finally {
			session.close();
		}
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
		Session session = null;
		List list = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(FacultyDTO.class);
			if (dto.getId() > 0L) {
				crit.add(Restrictions.eq("Id", dto.getId()));
			}

			if (dto.getCollegeId() > 0L) {
				crit.add(Restrictions.eq("collegeId", dto.getCollegeId()));
			}

			if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
				crit.add(Restrictions.like("collegeName", dto.getCollegeName() + "%"));
			}

			if (dto.getCourseId() > 0L) {
				crit.add(Restrictions.eq("courseId", dto.getCourseId()));
			}

			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				crit.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
			}

			if (dto.getDoj() != null) {
				crit.add(Restrictions.eq("doj", dto.getDoj()));
			}

			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				crit.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
			}

			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				crit.add(Restrictions.like("lastName", dto.getLastName() + "%"));
			}

			if (dto.getLoginId() != null && dto.getLoginId().length() > 0) {
				crit.add(Restrictions.like("loginId", dto.getLoginId() + "%"));
			}

			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				crit.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
			}

			if (dto.getSubjectId() > 0L) {
				crit.add(Restrictions.eq("subjectId", dto.getSubjectId()));
			}

			if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
				crit.add(Restrictions.like("subjectName", dto.getSubjectName() + "%"));
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in search faculty");
		} finally {
			session.close();
		}
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
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(FacultyDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in  Faculty List");
		} finally {
			session.close();
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
