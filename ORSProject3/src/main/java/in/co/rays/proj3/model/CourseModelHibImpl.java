package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;

/**
 * Hibernate Implementation of Course Model
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class CourseModelHibImpl implements CourseModelInt {

	/**
	 * Find Course by ID
	 * 
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public CourseDTO findByPK(long pk) throws ApplicationException {
		CourseDTO dto = null;
		Session session = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (CourseDTO) session.get(CourseDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by pk");
		} finally {
			session.close();
		}
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
		Session session = null;
		CourseDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(CourseDTO.class);
			crit.add(Restrictions.eq("courseName", courseName));
			List list = crit.list();
			if (list.size() == 1) {
				dto = (CourseDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by Course name");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Add a Course
	 * 
	 * @param dto
	 * @throws DatabaseException
	 * 
	 */
	public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		CourseDTO existDto = null;
		
		existDto = findByCourseName(dto.getCourseName());
		if (existDto != null) {
			throw new DuplicateRecordException("course name already exist");
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
			throw new ApplicationException("exception in add course");
		} finally {
			session.close();
		}
		return pk;
	}

	/**
	 * Update a Course
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		CourseDTO duplicataCourse = findByCourseName(dto.getCourseName());
		if (duplicataCourse != null && duplicataCourse.getId() != dto.getId()) {
			throw new DuplicateRecordException("Course already exists");
		}

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
			throw new ApplicationException("Exception in Course Update ");
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Delete a Course
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(CourseDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			System.out.println("after delete");
			session.delete(dto);
			System.out.println("Delete ");
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("exception in delete subject");
		} finally {
			session.close();
		}
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
		List list = null;
		Session session = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(CourseDTO.class);
			if (dto.getId() > 0L) {
				crit.add(Restrictions.eq("Id", dto.getId()));
			}
			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				crit.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in search user");
		} finally {
			session.close();
		}
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
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(CourseDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in college list");
		} finally {
			session.close();
		}
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
