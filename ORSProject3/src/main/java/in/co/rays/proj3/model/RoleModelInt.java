package in.co.rays.proj3.model;

import java.util.List;

import in.co.rays.proj3.dto.RoleDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of Role
 *
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface RoleModelInt {

	/**
	 * Find Role by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	RoleDTO findByPk(long pk) throws ApplicationException;

	/**
	 * Find Role by Name
	 *
	 * @param name : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	RoleDTO findByRoleName(String name) throws ApplicationException;

	/**
	 * Add a Role
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when Role already exists
	 */
	long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Update a Role
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : if updated user record is already exist
	 */
	void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Delete a Role
	 *
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 */
	void delete(RoleDTO dto) throws ApplicationException;

	/**
	 * Search Role with pagination
	 *
	 * @return list : List of Role
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Search Role
	 *
	 * @return list : List of Role
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */
	List search(RoleDTO dto) throws ApplicationException;

	/**
	 * get List of Role with pagination
	 *
	 * @return list : List of Role
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List list(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Gets List of Role
	 *
	 * @return list : List of Role
	 * @throws DatabaseException
	 */
	List list() throws ApplicationException;

}
