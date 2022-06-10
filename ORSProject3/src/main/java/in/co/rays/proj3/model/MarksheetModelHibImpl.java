package in.co.rays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;


/**
 * @author Rinku
 *
 */
public class MarksheetModelHibImpl implements MarksheetModelInt {
	
	private static Logger log = Logger.getLogger(MarksheetModelHibImpl.class);

	/**
	 * Finds Marksheet by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public MarksheetDTO findByPk(long pk) throws ApplicationException {
		Session session = null;
		MarksheetDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (MarksheetDTO) session.get(MarksheetDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Marksheet by pk");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Finds Marksheet by Roll No
	 *
	 * @param rollNo : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
		MarksheetDTO dto = null;
		Session session = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			criteria.add(Restrictions.eq("rollNo", rollNo));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (MarksheetDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Marksheet by Rollno ");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Adds a Marksheet
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when Roll No already exists
	 *
	 */
	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		
		// Set Student Name
		StudentModelInt studentFactory = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDto = studentFactory.findByPk(dto.getStudentId());
		dto.setName(studentDto.getFirstName() + " " + studentDto.getLastName());
		
		// Check for Duplicacy
		MarksheetDTO existDto = findByRollNo(dto.getRollNo());
		if (existDto != null) {
			throw new DuplicateRecordException("roll number already exist");
		}

		long pk = 0L;
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
			throw new ApplicationException("exception in add marksheet");
		} finally {
			session.close();
		}
		return pk;
	}
		
	/**
	 * Updates a Marksheet
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		
		// Set Student Name
		StudentModelInt studentFactory = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDto = studentFactory.findByPk(dto.getStudentId());
		dto.setName(studentDto.getFirstName() + " " + studentDto.getLastName());
		
		// Check for Duplicacy
		MarksheetDTO existDto = findByRollNo(dto.getRollNo());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("roll number already exist");
		}

		long pk = 0L;
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
			throw new ApplicationException("Exception in Marksheet Update");
		} finally {
			session.close();
		}
	}

	/**
	 * Deletes a Marksheet
	 *
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 */
	public void delete(MarksheetDTO dto) throws ApplicationException {
		
		log.debug("Model delete Started");
		MarksheetDTO dtoExist = findByPk(dto.getId());
		if (dtoExist == null) {
			throw new ApplicationException("Marksheet does not exist");
		}
		
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateDataSource.getSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();

			if (transaction != null) {
				transaction.rollback();
			}

			throw new ApplicationException("Exception in Marksheet Delete");
		} finally {
			session.close();
		}
	}

	/**
	 * Searches Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 *
	 * @throws ApplicationException
	 */
	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (dto.getId() > 0L) {
				criteria.add(Restrictions.eq("Id", dto.getId()));
			}

			if (dto.getStudentId() > 0L) {
				criteria.add(Restrictions.eq("studentId", dto.getStudentId()));
			}

			if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
				criteria.add(Restrictions.like("rollNo", dto.getRollNo() + "%"));
			}

			if (dto.getName() != null && dto.getName().length() > 0) {
				criteria.add(Restrictions.like("name", dto.getName() + "%"));
			}

			if (dto.getPhysics() != null) {
				criteria.add(Restrictions.eq("physics", dto.getPhysics()));
			}

			if (dto.getChemistry() != null) {
				criteria.add(Restrictions.eq("chemistry", dto.getChemistry()));
			}

			if (dto.getMaths() != null) {
				criteria.add(Restrictions.eq("maths", dto.getMaths()));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Marksheet Search ");
		} finally {
			session.close();
		}

		return list;
	}

	/**
	 * Searches Marksheet
	 *
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */
	public List search(MarksheetDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in  Marksheet List");
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Gets List of Marksheet
	 *
	 * @return list : List of Marksheets
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get Merit List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;
		try {
			session = HibernateDataSource.getSession();
			StringBuffer hql = new StringBuffer("from MarksheetDTO order by (physics + chemistry + maths) desc");
			Query query = session.createQuery(hql.toString());
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				query.setFirstResult(pageNo);
				query.setMaxResults(pageSize);
			}
			list = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in  marksheet merit list");
		} finally {
			session.close();
		}
		return list;
	}

}
