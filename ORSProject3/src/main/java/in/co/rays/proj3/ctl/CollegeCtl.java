package in.co.rays.proj3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.CollegeDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 */
/**
 * @author Rinku
 *
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CollegeCtl.class);

	/**
	 * Validates input data entered by User
	 * 
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {

		log.debug("CollegeCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isNameNumber(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.pass", "College Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		} else if (!DataValidator.isNameNumber(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.pass", "State Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.pass", "City Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.pass", "Phone No."));
			pass = false;
		}

		log.debug("CollegeCtl Method validate Ended");

		return pass;
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("CollegeCtl Method populatedto Started");

		CollegeDTO dto = new CollegeDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("name")));

		dto.setAddress(DataUtility.getString(request.getParameter("address")));

		dto.setState(DataUtility.getString(request.getParameter("state")));

		dto.setCity(DataUtility.getString(request.getParameter("city")));

		dto.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

		populate(dto, request);

		log.debug("CollegeCtl Method populatedto Ended");

		return dto;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			CollegeDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDTO(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("CollegeCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CollegeDTO dto = (CollegeDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);

					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
					return;
				} else {
					long pk = model.add(dto);
					dto.setId(pk);
					ServletUtility.setSuccessMessage("Data successfully Saved", request);
					ServletUtility.forward(getView(), request, response);
					return;
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CollegeDTO dto = (CollegeDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op))

		{
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("CollegeCtl Method doGet Ended");
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
