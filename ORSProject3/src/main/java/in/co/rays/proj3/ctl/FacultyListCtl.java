package in.co.rays.proj3.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.Logger;

import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.FacultyModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.FacultyDTO;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * faculty List functionality Controller. Performs operation for list, search
 * and delete operations of faculty
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet("/ctl/FacultyListCtl")
public class FacultyListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(FacultyListCtl.class);

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("faculty list ctl debug started");

		FacultyDTO dto = new FacultyDTO();

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setLoginId(DataUtility.getString(request.getParameter("loginId")));

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		log.debug("faculty list ctl debug completed");

		return dto;

	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("FacultyListCtl doGet Start");

		List list = null;
		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		FacultyDTO dto = (FacultyDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("faculty list ctl do get" + op);

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("id"); // to get muliple ids through check box //like movie tkt
															// booking seats are check box
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		
		try {
			list = model.search(dto, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record found...!!!! ", request);
				request.setAttribute("next", list);
			}
			System.out.println("faculty list ctl do get" + list.size());

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("FacultyListCtl doPOst End");
	}

	/**
	 * Contains submit logic
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("FacultyListCtl doPost Start");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));// coming from view

		// System.out.println("hi doget of fac ctl"+pageNo);

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		FacultyDTO dto = (FacultyDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		// System.out.println("hi dopost of fac
		// ctl"+dto.getFirstName()+dto.getLastName());

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");

		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;
					// System.out.println("hi doget of fac ctl inside search"+pageNo);
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;
				if (ids != null && ids.length > 0) {
					FacultyDTO deletedto = new FacultyDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));
						model.delete(deletedto);
						dto.setId(0); //// it is solution to avoid delete bug

					}
					ServletUtility.setSuccessMessage("Data successfully deleted", request);

				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			}
			list = model.search(dto, pageNo, pageSize);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("Record not found ...!!!!", request);
				request.setAttribute("list", list);

			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("facultyListCtl doGet End");
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {

		return ORSView.FACULTY_LIST_VIEW;
	}

}
