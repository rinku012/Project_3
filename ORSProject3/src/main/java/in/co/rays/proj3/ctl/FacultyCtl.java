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
import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.FacultyModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * faculty functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 */

/**
 * @author Rinku
 *
 */
@WebServlet(urlPatterns = "/ctl/FacultyCtl")
public class FacultyCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
		log.debug("faculty ctl preload debug started");
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		try {
			List subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

			List collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);

		} catch (Exception e) {
			log.error(e);
		}
		log.debug("faculty ctl preload debug completed");
	}

	/**
	 * Validates input data entered by User
	 * 
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("faculty ctl validate debug started");
		String login = request.getParameter("loginId");
		String doj = request.getParameter("doj");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.name", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(login)) {
			request.setAttribute("loginId", PropertyReader.getValue("error.require", "Login Id "));
			pass = false;

		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("loginId", PropertyReader.getValue("error.email", "LoginID"));
			pass = false;

		}

		if (DataValidator.isNull(doj)) {
			request.setAttribute("doj", PropertyReader.getValue("error.require", "Date of joining"));
			pass = false;

		} else if (!DataValidator.isDate(doj)) {
			request.setAttribute("doj", PropertyReader.getValue("error.date", "Date of joining"));
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no"));
			pass = false;

		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mobile", " "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}

		log.debug("faculty ctl validate debug completed");
		return pass;
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("faculty ctl populate dto debug started");

		FacultyDTO dto = new FacultyDTO();

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setLoginId(DataUtility.getString(request.getParameter("loginId")));

		dto.setDoj(DataUtility.getDate(request.getParameter("doj")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		populate(dto, request);

		log.debug("faculty ctl populate dto debug completed");

		return dto;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("faculty ctl doget debug started");

		String op = DataUtility.getString(request.getParameter("operation"));
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			FacultyDTO dto = new FacultyDTO();
			try {
				dto = model.findByPk(id);
				ServletUtility.setDTO(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("faculty ctl doget debug completed");
	}

	/**
	 * Contains submit logic
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("faculty ctl doget debug started");
		String op = DataUtility.getString(request.getParameter("operation"));

		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyDTO dto = (FacultyDTO) populateDTO(request);
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
						return;
					}

				}
			} catch (Exception e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("record already exist", request);
				e.printStackTrace();
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			FacultyDTO dto = (FacultyDTO) populateDTO(request);
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
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		log.debug("faculty ctl doget debug completed");
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {

		return ORSView.FACULTY_VIEW;
	}

}
