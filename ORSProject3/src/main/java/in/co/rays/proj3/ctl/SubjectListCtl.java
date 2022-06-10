package in.co.rays.proj3.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.SubjectDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * subject List functionality Controller. Performs operation for list, search
 * and delete operations of subject
 * 
 */

/**
 * @author Rinku
 *
 */
@WebServlet(urlPatterns = "/ctl/SubjectListCtl")
public class SubjectListCtl extends BaseCtl

{

	private static Logger log = Logger.getLogger(SubjectListCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
		log.debug("prload debug started");
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();

		try {
			List cList = cModel.list();

			request.setAttribute("courseList", cList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("preload debug completed");
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("SubjectListCtl populatebean debug started");

		SubjectDTO dto = new SubjectDTO();

		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		dto.setSubjectName(DataUtility.getStringData(request.getParameter("subjectName")));

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		log.debug("SubjectListCtl populate dto debug completed");

		return dto;
	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("SubjectListCtl doget debug started");
		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubjectDTO dto = (SubjectDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		// System.out.println("Heeeee"+ids[0]);
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();

		try {
			list = model.search(dto, pageNo, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("record not found", request);
				request.setAttribute("next", list);
				return;
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
		log.debug("SubjectListCtl doget debug completed");
	}

	/**
	 * Contains submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("courseListctl debug started");
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		String ids[] = request.getParameterValues("ids");

		SubjectDTO dto = (SubjectDTO) populateDTO(request);
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					SubjectDTO deletedto = new SubjectDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));
						model.delete(deletedto);
						dto.setId(0);
					}
					ServletUtility.setSuccessMessage("Data successfully deleted", request);
				} else {
					ServletUtility.setErrorMessage("select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			}

				list = model.search(dto, pageNo, pageSize);
				ServletUtility.setDTO(dto, request); //// to set value in preload

				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No Record Found...!!!", request);
					request.setAttribute("next", list);
				}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Subject Listctl debug completed");

	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
