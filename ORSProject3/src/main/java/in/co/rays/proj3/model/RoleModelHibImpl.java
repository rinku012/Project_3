package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;

/**
 * Hibernate Implementation of Role Model
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class RoleModelHibImpl implements RoleModelInt {

	/**
	 * Find Role by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public RoleDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		RoleDTO dto = null;

		try {
			session = HibernateDataSource.getSession();
			dto = (RoleDTO) session.get(RoleDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Find User by Role Name
	 * 
	 * @param name
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */
	
	public RoleDTO findByRoleName(String name) throws ApplicationException {
		Session session = null;
		RoleDTO dto = null;
		List list = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(RoleDTO.class);
			crit.add(Restrictions.eq("name", name));
			list = crit.list();
			if (list.size() == 1) {
				dto = (RoleDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by role name");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Add a Role
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		RoleDTO existDto = null;
		long pk = 0L;
		
		existDto = findByRoleName(dto.getName());
		if (existDto != null) {
			throw new DuplicateRecordException("Role Name already exist");
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
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new ApplicationException("exception in add role");
		
		} finally {
			session.close();
		}
		 return pk;
	}

	/**
	 * Update a Role
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */	
	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException {

		RoleDTO existDto = null;
		long pk = 0L;
		
		existDto = findByRoleName(dto.getName());
		if (existDto != null  && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Role Name already exist");
		}
		
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
			throw new ApplicationException("exception in update user");
		} finally {
			session.close();
		}
	}

	/**
	 * Delete a Role
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(RoleDTO dto) throws ApplicationException {
		Session session = null;
		Transaction tx = null;

		try {
			session = HibernateDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}		
			e.printStackTrace();
			throw new ApplicationException("exception in delete role");
		}

	}

	/**
	 * Search Role with pagination
	 * 
	 * @return list : List of Roles
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */
	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(RoleDTO.class);
			
			if (dto.getName() != null && dto.getName().length() > 0) {
				crit.add(Restrictions.like("name", dto.getName() + "%"));
			}
			
			if (dto.getId() > 0L) {
				crit.add(Restrictions.eq("Id", dto.getId()));
			}

			if (dto.getDescription() != null && dto.getDescription().length() > 0) {
				crit.add(Restrictions.like("description", dto.getDescription() + "%"));
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			
			list = crit.list();
			 
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in search in role");
			
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Search Role
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(RoleDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Get List of Role with pagination
	 * 
	 * @return list : List of Role
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
			Criteria crit = session.createCriteria(RoleDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in role list");
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Get List of Role
	 * 
	 * @return list : List of Role
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
