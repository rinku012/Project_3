package in.co.rays.proj3.model;

import java.util.Date;
import java.util.List;

import in.co.rays.proj3.dto.TimeTableDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;

/**
 * Data Access Object of TimeTable
 *
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */

public interface TimeTableModelInt {

	/**
	 * Find TimeTable by PK
	 *
	 * @param pk : get parameter
	 * @return dto
	 * @throws ApplicationException
	 */
	TimeTableDTO findByPk(long pk) throws ApplicationException;

	/**
	 * 
	 * Find TimeTable by Course Name, Subject Name
	 * 
	 * @param courseName, subjectName : get parameter
	 * 
	 * @return dto
	 * @throws ApplicationException
	 * 
	 */
	TimeTableDTO findByCourseNameSubjectName(String courseName, String subjectName) throws ApplicationException;

	/**
	 * Find TimeTable by courseId, ExamDate
	 * 
	 * @param courseName
	 * @param examDate
	 * @return
	 * @throws ApplicationException
	 */
	TimeTableDTO findByCourseNameExamDate(String courseName, Date examDate) throws ApplicationException;

	/**
	 * Find TimeTable by SubjectName, courseName, ExamTime, ExamDate
	 * 
	 * @param subjectName
	 * @param courseName
	 * @param examTime
	 * @param examDate
	 * @return
	 * @throws ApplicationException
	 */
	TimeTableDTO findByCourseNameSubjectNameExamDateExamTime(String courseName, String subjectName,
			Date exameDate, String examTime) throws ApplicationException;

	/**
	 * Add a TimeTable
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : throws when TimeTable already exists
	 */
	long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Update a TimeTable
	 *
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException : if updated TimeTable record is already
	 *                                  exist
	 */
	void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException;

	/**
	 * Delete a TimeTable
	 *
	 * @param dto
	 * @return
	 * @throws ApplicationException
	 */
	void delete(TimeTableDTO dto) throws ApplicationException;

	/**
	 * Search TimeTable with pagination
	 *
	 * @return list : List of TimeTable
	 * @param dto      : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Search TimeTable
	 *
	 * @return list : List of TimeTable
	 * @param dto : Search Parameters
	 * @throws ApplicationException
	 */
	List search(TimeTableDTO dto) throws ApplicationException;

	/**
	 * get List of TimeTable with pagination
	 *
	 * @return list : List of TimeTable
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws ApplicationException
	 */
	List list(int pageNo, int pageSize) throws ApplicationException;

	/**
	 * Gets List of TimeTable
	 *
	 * @return list : List of TimeTable
	 * @throws DatabaseException
	 */
	List list() throws ApplicationException;

}
