package in.co.rays.proj3.model;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;

/**
 * Hibernate Implementation of CollegeModel
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public class CollegeModelHibImpl implements CollegeModelInt {

	private static Logger log = Logger.getLogger(CollegeModelHibImpl.class);

	/**
	 * Find College by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public CollegeDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		Session session = null;
		CollegeDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (CollegeDTO) session.get(CollegeDTO.class, pk);
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
	 * Find College by Name
	 *
	 * @param login : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public CollegeDTO findByName(String name) throws ApplicationException {

		log.debug("Model findByName Started");
		Session session = null;
		CollegeDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(CollegeDTO.class);
			crit.add(Restrictions.eq("name", name));
			List list = crit.list();
			if (list.size() > 0) {
				dto = (CollegeDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("exception in find college");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Add a College
	 *
	 * @param dto
	 * @throws DatabaseException
	 *
	 */
	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");
		CollegeDTO existDto = null;
		long pk = 0L;

		existDto = findByName(dto.getName());
		if (existDto != null) {
			throw new DuplicateRecordException("college name already exist");
		}

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
			throw new ApplicationException("exception in add college");
		} finally {
			session.close();
		}
		return pk;
	}

	/**
	 * Update a Collage
	 *
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Session session = null;
		Transaction transaction = null;

		CollegeDTO dtoExist = findByName(dto.getName());

		// Check if updated College already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("College is already exist");
		}

		try {
			session = HibernateDataSource.getSession();
			transaction = session.beginTransaction();
			session.update(dto);
			transaction.commit();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			if (transaction != null) {
				transaction.rollback();
				throw new ApplicationException("Exception in College Update" + e.getMessage());
			}
		} finally {
			session.close();
		}
		log.debug("Model update End");
	}

	/**
	 * Delete a College
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void delete(CollegeDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in colloge");

		} finally {
			session.close();
		}

	}

	/**
	 * Searches Colleges with pagination
	 *
	 * @return list : List of Colleges
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws DatabaseException
	 */
	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(CollegeDTO.class);
			if (dto.getId() > 0L) {
				crit.add(Restrictions.eq("Id", dto.getId()));
			}

			if (dto.getName() != null && dto.getName().length() > 0) {
				crit.add(Restrictions.like("name", dto.getName() + "%"));
			}

			if (dto.getAddress() != null && dto.getAddress().length() > 0) {
				crit.add(Restrictions.like("address", dto.getAddress() + "%"));
			}

			if (dto.getState() != null && dto.getState().length() > 0) {
				crit.add(Restrictions.like("state", dto.getState() + "%"));
			}

			if (dto.getCity() != null && dto.getCity().length() > 0) {
				crit.add(Restrictions.like("city", dto.getCity() + "%"));
			}

			if (dto.getPhoneNo() != null && dto.getPhoneNo().length() > 0) {
				crit.add(Restrictions.like("phoneNo", dto.getPhoneNo() + "%"));
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in college search");
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Search College
	 *
	 * @param dto : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(CollegeDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * get List of College with pagination
	 *
	 * @return list : List of College
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(CollegeDTO.class);
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
	 * Gets List of College
	 *
	 * @return list : List of College
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
