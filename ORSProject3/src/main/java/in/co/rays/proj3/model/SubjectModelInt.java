package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of Subject
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public interface SubjectModelInt {

	/**
	 * Find Subject by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	SubjectDTO findByPk(long pk) throws ApplicationException;

	/**
	 * Find Subject by Name
	 *
	 * @param name : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	SubjectDTO findBySubjectName(String subjectName) throws ApplicationException;

	/**
	 * Add a Subject
	 * 
	 * @param SubjectDTO
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when User already exists
	 * @throws DatabaseException
	 */
	long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Update Subject
	 * 
	 * @param SubjectDTO
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when User already exists
	 * @throws DatabaseException
	 */
	void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Delete Subject
	 * 
	 * @param SubjectDTO
	 * @return
	 * @throws ApplicationException
	 */
	void delete(SubjectDTO dto) throws ApplicationException;

	/**
	 * Search Subject with pagination
	 *
	 * @return list : List of Subject
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Search Subject
	 *
	 * @return list : List of Subject
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */
	List search(SubjectDTO dto) throws ApplicationException;

	/**
	 * get List of Subject with pagination
	 *
	 * @return list : List of Subject
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List list(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Gets List of Subject
	 *
	 * @return list : List of Subject
	 * @throws DatabaseException
	 */
	List list() throws ApplicationException;

}
