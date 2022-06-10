package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of Course
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public interface CourseModelInt {
	
	/**
	 * Find Course by PK
	 *
	 * @param pk
	 *            : get parameter
	 * @return courseDTO 
	 * @throws ApplicationException
	 */
	public CourseDTO findByPK(long pk) throws ApplicationException;
	
	/**
	 * Find Course by Course Name
	 *
	 * @param pk
	 *            : get parameter
	 * @return courseDTO 
	 * @throws ApplicationException
	 */
	CourseDTO findByCourseName(String courseName) throws ApplicationException;
	
	/**
	 * Add a Course
	 *
	 * @param courseDTO 
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when course already exists 
	 */
	long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Update a Course
	 *
	 * @param userDTO 
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : if updated user record is already exist
	 * @throws DatabaseException 
	 */
	void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Delete a Course
	 *
	 * @param dto
	 * @return 
	 * @throws ApplicationException
	 */
	void delete(CourseDTO dto) throws ApplicationException;

	/**
	 * Search Course with pagination
	 *
	 * @return list : List of Course
	 * @param CourseDTO 
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	List search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException;
	
	/**
	 * Search Course
	 *
	 * @return list : List of Course
	 * @param courseDTO 
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	List search(CourseDTO dto) throws ApplicationException;

	/**
	 * get List of Course with pagination
	 *
	 * @return list : List of Course
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws ApplicationException
	 */
	List list(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Gets List of Course
	 *
	 * @return list : List of Course
	 * @throws DatabaseException
	 */
	List list() throws ApplicationException;

}
