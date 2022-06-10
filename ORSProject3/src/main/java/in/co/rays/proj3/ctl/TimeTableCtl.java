package in.co.rays.proj3.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.TimeTableDTO;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.model.TimeTableModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * timetable functionality Controller. Performs operation for add, update,
 * delete and get time table
 * 
 */

/**
 * @author Rinku
 *
 */
@WebServlet(urlPatterns = "/ctl/TimeTableCtl")
public class TimeTableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
		log.debug("time table ctl debug started");
		List courseList = null;
		List subjectList = null;
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();

		try {
			courseList = courseModel.list();
			subjectList = subjectModel.list();

			request.setAttribute("courseList", courseList);
			request.setAttribute("subjectList", subjectList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("time table ctl debug completed");
	}

	/**
	 * Validates input data entered by User
	 * 
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("time table ctl validate debug started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject name"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examTime"))) {
			request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));

			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));

			pass = false;
		}

		log.debug("time table ctl validate debug completed");

		return pass;
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("time table ctl populatedto debug completed");

		TimeTableDTO dto = new TimeTableDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));

		dto.setExamTime(DataUtility.getString(request.getParameter("examTime")));

		populate(dto, request);

		log.debug("time table ctl populatedto debug completed");

		return dto;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("time table ctl doget debug completed");

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("hi timetable doget" + op);

		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("hi timetable doget" + id);
		if (id > 0) {
			TimeTableDTO dto = null;
			try {
				dto = model.findByPk(id);
				// System.out.println("id in time table do get"+dto.getId());
				ServletUtility.setDTO(dto, request);

			} catch (Exception e) {

				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("time table ctl doget debug completed");
	}

	/**
	 * Contains submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("time table ctl dopost debug completed");

		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("hi dopost in timetable ctl" + op);

		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("hi dopost in timetable ctl" + id);
		

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
			System.out.println("ID : "+dto.getId());
			System.out.println("Course id : "+dto.getCourseId());
			System.out.println("Subj id : "+dto.getSubjectId());
			dto.getExamTime();
			
			try {
				if (id > 0) {

					try {
						model.update(dto);
						ServletUtility.setDTO(dto, request);
						ServletUtility.setSuccessMessage("Updated Successfully...!!!!", request);
						ServletUtility.forward(getView(), request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDTO(dto, request);
						ServletUtility.setErrorMessage("Records already exists", request);
						ServletUtility.forward(getView(), request, response);
						return;
					}
				} else {
					long pk = 0;
					try {
						pk = model.add(dto);
						ServletUtility.setSuccessMessage("Saved Successfully", request);
						ServletUtility.forward(getView(), request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDTO(dto, request);
						ServletUtility.setErrorMessage("Records already exists", request);
						e.printStackTrace();
					}
					
					dto.setId(pk);
					ServletUtility.setErrorMessage("Record already exist....!!!!", request);
					ServletUtility.setDTO(dto, request);
					ServletUtility.forward(getView(), request, response);
					return;

				}
			} catch (Exception e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("record already exist", request);
				e.printStackTrace();
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.setSuccessMessage("Record Deleted Successfully...!!!", request);
				ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
				return;
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
			return;
		}

		log.debug("time table ctl dopost debug completed");

	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {

		return ORSView.TIME_TABLE_VIEW;
	}

}
