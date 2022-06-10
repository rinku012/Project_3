package in.co.rays.proj3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.util.HibernateDataSource;

/**
 * Hibernate Implementation of SubjectModel
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public class SubjectModelHibImpl implements SubjectModelInt {

	/**
	 * Find Subject by PK
	 * 
	 * @param pk : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */

	public SubjectDTO findByPk(long pk) throws ApplicationException {
		SubjectDTO dto = null;
		Session session = null;
		try {
			session = HibernateDataSource.getSession();
			dto = (SubjectDTO) session.get(SubjectDTO.class, pk);
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find by pk");
		} finally {
			session.close();
		}
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
		Session session = null;
		SubjectDTO dto = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(SubjectDTO.class);
			crit.add(Restrictions.eq("subjectName", subjectName));
			List list = crit.list();
			if (list.size() != 1) {
				SubjectDTO var6 = dto;
				return var6;
			}
			dto = (SubjectDTO) list.get(0);
			System.out.println("find by sub name" + dto.getSubjectName());
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in find Subject");
		} finally {
			session.close();
		}
		return dto;
	}

	/**
	 * Add a Subject
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0L;

		SubjectDTO existDto = findBySubjectName(dto.getSubjectName());
		if (existDto != null) {
			throw new DuplicateRecordException("Subject name already exist");
		}

		// get Course Name
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couDto = couModel.findByPK(dto.getCourseId());
		dto.setCourseName(couDto.getCourseName());

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
			throw new ApplicationException("exception in Add Subject");
		} finally {
			session.close();
		}
		return pk;
	}

	/**
	 * Update a Subject
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0L;

		SubjectDTO existDto = findBySubjectName(dto.getSubjectName());
		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Subject name already exist");
		}

		// get Course Name
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO couDto = couModel.findByPK(dto.getCourseId());
		dto.setCourseName(couDto.getCourseName());

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
			throw new ApplicationException("exception in update subject");
		} finally {
			session.close();
		}

	}

	/**
	 * Delete a Subject
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */

	public void delete(SubjectDTO dto) throws ApplicationException {
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
			throw new ApplicationException("exception in delete subject");
		} finally {
			session.clear();
		}
	}

	/**
     * Searches Subjects with pagination
     * 
     * @return list : List of Subjects
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */
	
	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {

		List list = null;
		Session session = null;

		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(SubjectDTO.class);
			if (dto.getId() > 0L) {
				crit.add(Restrictions.eq("Id", dto.getId()));
			}

			if (dto.getCourseId() > 0L) {
				crit.add(Restrictions.eq("courseId", dto.getCourseId()));
			}

			if (dto.getSubjectId() > 0L) {
				crit.add(Restrictions.eq("subjectId", dto.getSubjectId()));
			}

			if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
				crit.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
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
			throw new ApplicationException("exception in search Subject");
		} finally {
			session.close();
		}
		return list;
	}

	/**
     * Search Subject
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	
	public List search(SubjectDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * get List of Subject with pagination
     * 
     * @return list : List of Subject
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		List list = null;
		Session session = null;
		try {
			session = HibernateDataSource.getSession();
			Criteria crit = session.createCriteria(SubjectDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				crit.setFirstResult(pageNo);
				crit.setMaxResults(pageSize);
			}
			list = crit.list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("exception in model list");
		} finally {
			session.close();
		}
		return list;
	}


    /**
     * Gets List of Subject
     * 
     * @return list : List of Subject
     * @throws DatabaseException
     */
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

}
