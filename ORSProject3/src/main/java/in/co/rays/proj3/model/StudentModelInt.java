package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of Student
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface StudentModelInt {

	/**
	 * Find Student by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	StudentDTO findByPk(long pk) throws ApplicationException;

	/**
	 * Find Student by EmailId
	 *
	 * @param name : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	StudentDTO findByEmailId(String login) throws ApplicationException;

	/**
	 * Add a Student
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when Student already exists
	 */
	long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Update a Student
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : if updated user record is already exist
	 */
	void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Delete a Student
	 *
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 */
	void delete(StudentDTO dto) throws ApplicationException;

	/**
	 * Search Student with pagination
	 *
	 * @return list : List of Student
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Search Student
	 *
	 * @return list : List of Student
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */
	List search(StudentDTO dto) throws ApplicationException;

	/**
	 * get List of College with pagination
	 *
	 * @return list : List of College
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List list(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Gets List of College
	 *
	 * @return list : List of College
	 * @throws DatabaseException
	 */
	List list() throws ApplicationException;

}
