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
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * subject functionality Controller. Performs operation for add, update, delete
 * and get subject
 * 
 */

/**
 * @author Rinku
 *
 */
@WebServlet(urlPatterns = "/ctl/SubjectCtl")
public class SubjectCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SubjectCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
		List list = new ArrayList();
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		System.out.println("hi preload of subject ctl ");
		try {
			list = model.list();
			request.setAttribute("courseList", list);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Validates input data entered by User
	 * 
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("subjectctl valiadte debug started");
		boolean pass = true;
		// System.out.println("im in validate of userctl");

		if (DataValidator.isNull(request.getParameter("subjectName"))) {
			request.setAttribute("subjectName", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		} else if (!DataValidator.isNameNumber(request.getParameter("subjectName"))) {
			request.setAttribute("subjectName", PropertyReader.getValue("error.pass", "Subject Name"));
			pass = false;
		}

		System.out.println("subject ctl validate" + request.getParameter("courseId"));

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		log.debug("subjectctl validate debug completed");
		return pass;
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("subject ctl populate dto debug started");

		SubjectDTO dto = new SubjectDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));

		dto.setDescription(DataUtility.getString(request.getParameter("description")));

		populate(dto, request);

		log.debug("subject ctl populate dto debug completed");

		return dto;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("subject ctl doget started");
		String op = DataUtility.getString(request.getParameter("operation"));
		SubjectDTO dto = null;
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
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
		log.debug("subject ctl doget completed");
	}

	/**
	 * Contains submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("subject ctl dopost debug started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		SubjectModelInt submodel = ModelFactory.getInstance().getSubjectModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SubjectDTO dto = (SubjectDTO) populateDTO(request);
			try {
				if (id > 0) {

					try {
						submodel.update(dto);
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
						pk = submodel.add(dto);
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

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("subject ctl dopost debug completed");
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}
