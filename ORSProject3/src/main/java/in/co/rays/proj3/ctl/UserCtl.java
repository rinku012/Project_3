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
import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.DatabaseException;
import in.co.rays.proj3.exception.DuplicateRecordException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.RoleModelInt;
import in.co.rays.proj3.model.UserModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.DataValidator;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * User functionality Controller. Performs operation for add, update and get
 * User
 * 
 */

/**
 * @author Rinku
 *
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })

public class UserCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(UserCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {

		// get model
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();

		try {
			List l = model.list();

			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	/**
	 * Validates input data entered by User
	 * 
	 * @param request
	 * @return
	 */
	protected boolean validate(HttpServletRequest request) {

		log.debug("UserCtl Method validate Started");

		boolean pass = true;
		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		
		  if (DataValidator.isNull(request.getParameter("firstName"))) {
		  request.setAttribute("firstName", PropertyReader.getValue("error.require",
		  "First Name")); pass = false; } else if
		  (!DataValidator.isName(request.getParameter("firstName"))) {
		  request.setAttribute("firstName", PropertyReader.getValue("error.name",
		  "First Name")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("lastName"))) {
		  request.setAttribute("lastName", PropertyReader.getValue("error.require",
		  "Last Name")); pass = false; } else if
		  (!DataValidator.isName(request.getParameter("lastName"))) {
		  request.setAttribute("lastName", PropertyReader.getValue("error.name",
		  "Last Name")); pass = false; }
		  
		  if (DataValidator.isNull(login)) { request.setAttribute("login",
		  PropertyReader.getValue("error.require", "Login Id")); pass = false; } else
		  if (!DataValidator.isEmail(login)) { request.setAttribute("login",
		  PropertyReader.getValue("error.email", "Login ")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("password"))) {
		  request.setAttribute("password", PropertyReader.getValue("error.require",
		  "Password")); pass = false; } else if
		  (!DataValidator.isPassword(request.getParameter("password"))) {
		  request.setAttribute("password", PropertyReader.getValue("error.password",
		  "Password")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
		  request.setAttribute("confirmPassword",
		  PropertyReader.getValue("error.require", "Confirm Password")); pass = false;
		  }
		  
		  if (!request.getParameter("password").equals(request.getParameter(
		  "confirmPassword")) && !"".equals(request.getParameter("confirmPassword"))) {
		  request.setAttribute("confirmPassword",
		  PropertyReader.getValue("error.confirmpassword", " ")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("gender"))) {
		  request.setAttribute("gender", PropertyReader.getValue("error.require",
		  "Gender")); pass = false; } if (DataValidator.isNull(dob)) {
		  request.setAttribute("dob", PropertyReader.getValue("error.require",
		  "Date Of Birth")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("gender"))) {
		  request.setAttribute("gender", PropertyReader.getValue("error.require",
		  "Gender")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("mobileNo"))) {
		  request.setAttribute("mobileNo", PropertyReader.getValue("error.require",
		  "MobileNo")); pass = false; } else if
		  (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
		  request.setAttribute("mobileNo", PropertyReader.getValue("error.mobile",
		  " ")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("roleId"))) {
		  request.setAttribute("roleId", PropertyReader.getValue("error.require",
		  "Role Name")); pass = false; } log.debug("UserCtl Method validate Ended");
		  
		 		return pass;
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("UserCtl Method populatedto Started");

		UserDTO dto = new UserDTO();

		// System.out.println("im in populate dto of userctl");

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		// System.out.println("im in populate dto of userctl with id coming
		// from jsp in hidden
		// field"+(DataUtility.getLong(request.getParameter("id"))));

		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		populate(dto, request);

		log.debug("UserCtl Method populatedto Ended");

		return dto;
	}

	/**
	 * Contains DIsplay logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserCtl Method doGet Started");

		// System.out.println("hi i m doget of userctl");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			// System.out.println(" hi im in userctl doget in id > 0
			// condition");
			UserDTO dto;
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
		log.debug("UserCtl Method doGet Ended");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		// System.out.println("hi do post from jsp"+id);

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			// System.out.println(" hi im dopost in userctl inside if codition
			// for save or update operation");
			UserDTO dto = (UserDTO) populateDTO(request);

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
						// System.out.println(" hi im dopost in userctl inside
						// if codition for model.add to perform add");
						pk = model.add(dto);

						ServletUtility.setSuccessMessage("Data is successfully saved", request);
						ServletUtility.forward(getView(), request, response);
						return;
					} catch (Exception e) {
						e.printStackTrace();
						// throw new DuplicateRecordException("login id already
						// exist try another one");
					}
					// dto.setId(pk);
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
			// System.out.println(" hi im dopost in userctl inside if codition
			// for model.delete in delete operation");

			UserDTO dto = (UserDTO) populateDTO(request);
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
			// System.out.println(" hi im dopost in userctl inside if codition
			// for reset operation");

			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			// System.out.println(" hi im dopost in userctl inside if codition
			// for cancel operation");

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPostEnded");
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {
		return ORSView.USER_VIEW;
	}

}
