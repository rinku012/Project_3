package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.MarksheetDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Service of Marksheet
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface MarksheetModelInt {

	/**
	 * Finds Marksheet by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	MarksheetDTO findByPk(long pk) throws ApplicationException;

	/**
	 * Finds Marksheet by Roll No
	 *
	 * @param rollNo : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	MarksheetDTO findByRollNo(String rollNo) throws ApplicationException;

	/**
	 * Adds a Marksheet
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when Roll No already exists
	 *
	 */
	long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Updates a Marksheet
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Deletes a Marksheet
	 *
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 */
	void delete(MarksheetDTO dto) throws ApplicationException;

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
	List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Searches Marksheet
	 *
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */
	List search(MarksheetDTO dto) throws ApplicationException;

	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List list(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Gets List of Marksheet
	 *
	 * @return list : List of Marksheets
	 * @throws DatabaseException
	 */
	List list() throws ApplicationException;

	/**
	 * get Merit List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List getMeritList(int pageNo, int pageSize) throws ApplicationException;

}
