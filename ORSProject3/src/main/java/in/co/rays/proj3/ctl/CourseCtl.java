package in.co.rays.proj3.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CourseDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * Course functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 */

/**
 * @author Rinku
 *
 */
@WebServlet(name = "CourseCtl", urlPatterns = "/ctl/CourseCtl")
public class CourseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CourseCtl.class);

	/**
	 * Validates input data entered by User
	 * 
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("Course ctl debug started in validate");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("courseName"))) {
			request.setAttribute("courseName", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		} else if (!DataValidator.isNameNumber(request.getParameter("courseName"))) {
			request.setAttribute("courseName", PropertyReader.getValue("error.pass", "Course Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		log.debug("course ctl debug ended in validate");
		return pass;
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("populate bean debug started");
		CourseDTO dto = new CourseDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));

		dto.setDescription(DataUtility.getString(request.getParameter("description")));

		populate(dto, request);

		log.debug("populate dto debug completed");

		return dto;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doget debug started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			CourseDTO dto;

			try {
				dto = model.findByPK(id);
				ServletUtility.setDTO(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("doget debug completed");
	}

	/**
	 * Contains submit logic
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("dopost debug started");

		String op = DataUtility.getString(request.getParameter("operation"));
		
		// get model
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			
			CourseDTO dto = (CourseDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
					ServletUtility.forward(getView(), request, response);
					return;
					
				}

				else {
					long pk = 0;
					try {
						pk = model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
						ServletUtility.forward(getView(), request, response);
						return;
					} catch (Exception e) {
						e.printStackTrace();
						
					}
				}

				dto.setId(0);
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("login id already exist", request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
				return;
			}
			// ServletUtility.forward(ORSView.USER_CTL, request, response);
		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {

			CourseDTO dto = (CourseDTO) populateDTO(request);
			
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}


		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("dopost debug completed");

	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

}
