package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of Faculty
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public interface FacultyModelInt {

	/**
	 * Find Faculty by PK
	 *
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * @throws DatabaseException 
	 */
	FacultyDTO findByPk(long pk) throws ApplicationException;

	/**
	 * Find Faculty by Login
	 *
	 * @param name
	 *            : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	FacultyDTO findByLogin(String login) throws ApplicationException;

	/**
	 * Add a Faculty
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when Faculty already exists
	 */
	long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Update a Faculty
	 *
	 * @param dto
	 * @return 
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : if updated Faculty record is already exist
	 */
	void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Delete a Faculty
	 *
	 * @param dto
	 * @return 
	 * @throws ApplicationException
	 */
	void delete(FacultyDTO dto) throws ApplicationException;

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
	List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException;
	
	/**
	 * Search Faculty
	 *
	 * @return list : List of Faculty
	 * @param dto
	 *            : Search Parameters
	 * @throws ApplicationException
	 */
	List search(FacultyDTO dto) throws ApplicationException;

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
	List list(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Gets List of Faculty
	 *
	 * @return list : List of Faculty
	 * @throws DatabaseException
	 */
	List list() throws ApplicationException;

}
