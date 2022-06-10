package in.co.rays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;

/**
 * Hibernate Implementation of StudentModel
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public class StudentModelHibImpl implements StudentModelInt {

	private static Logger log = Logger.getLogger(StudentModelHibImpl.class);

	/**
	 * Find Student by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public StudentDTO findByPk(long pk) throws ApplicationException {
		StudentDTO dto = null;
		Session session = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (StudentDTO) session.get(StudentDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Find Student by Email
	 *
	 * @param login : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public StudentDTO findByEmailId(String login) throws ApplicationException {
		Session session = null;
		StudentDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(StudentDTO.class);
			crit.add(Restrictions.eq("email", login));
			List list = crit.list();
			if (list.size() > 0) {
				dto = (StudentDTO) list.get(0);
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
	 * Add a Student
	 *
	 * @param dto
	 * @throws DatabaseException
	 *
	 */
	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException {

		long pk = 0L;

		StudentDTO existDto = findByEmailId(dto.getEmail());
		if (existDto != null) {
			throw new DuplicateRecordException("email id alredy exist");
		}

		// get College Name
		CollegeModelInt collModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collDto = collModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collDto.getName());

		Session session = null;
		Transaction tx = null;

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
			throw new ApplicationException("exception in add student");
		} finally {
			session.close();
		}
		return pk;
	}

	/**
	 * Update a Student
	 *
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException {
		StudentDTO existDto = null;
		StudentDTO existDtoPk = null;

		existDto = findByEmailId(dto.getEmail());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("email id alredy exist");
		}

		// get College Name
		CollegeModelInt collModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collDto = collModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collDto.getName());

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
			throw new ApplicationException("exception in update student");
		} finally {
			session.close();
		}
	}

	/**
	 * Delete a Student
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void delete(StudentDTO dto) throws ApplicationException {
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
			throw new ApplicationException("exception in delete student");
		} finally {
			session.close();
		}
	}

	/**
	 * Searches Student with pagination
	 *
	 * @return list : List of Student
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */
	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(StudentDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				criteria.add(Restrictions.eq("dob", dto.getDob()));
			}
			if (dto.getEmail() != null && dto.getEmail().length() > 0) {
				criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				criteria.add(Restrictions.like("mobileNo", dto.getMobileNo() + "%"));
			}

			// if page size is greater than zero the apply pagination
			if (pageSize > 0) {
				criteria.setFirstResult(((pageNo - 1) * pageSize));
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception in Student search");
		} finally {
			session.close();
		}

		log.debug("Model search End");
		return list;
	}

	/**
	 * Searches Student
	 *
	 * @param dto : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(StudentDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * get List of Student with pagination
	 *
	 * @return list : List of Student
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(StudentDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in student list");
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Gets List of Student
	 *
	 * @return list : List of Student
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
