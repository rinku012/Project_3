package in.co.rays.proj3.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj3.dto.BaseDTO;
import in.co.rays.proj3.dto.TimeTableDTO;
import in.co.rays.proj3.model.CollegeModelInt;
import in.co.rays.proj3.model.CourseModelInt;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.SubjectModelInt;
import in.co.rays.proj3.model.TimeTableModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * timetable List functionality Controller. Performs operation for list, search
 * and delete operations of timetable
 * 
 */

/**
 * @author Rinku
 *
 */
@WebServlet(urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(TimeTableListCtl.class);

	/**
	 * Loads list and other data required to display at HTML form
	 * 
	 * @param request
	 */
	protected void preload(HttpServletRequest request) {
		log.debug("TimeTableList ctl preload debug started");

		SubjectModelInt subModel = ModelFactory.getInstance().getSubjectModel();
		CourseModelInt couModel = ModelFactory.getInstance().getCourseModel();
		try {
			List subList = subModel.list();
			List couList = couModel.list();

			request.setAttribute("subjectList", subList);
			request.setAttribute("courseList", couList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("TimeTableList ctl preload debug completed");
	}

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("timetable list ctl debug started");

		TimeTableDTO dto = new TimeTableDTO();

		dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));

		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));

		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		log.debug("timetable list ctl debug completed");

		return dto;

	}

	/**
	 * Contains Display logics
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("timetable list doGet Started");

		List list = null;
		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		// System.out.println("heyyy doget "+op);

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids"); // to get muliple ids through check box //like movie tkt
															// booking seats are check box
		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();
		
		try {
			list = model.search(dto, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record found...!!!! ", request);
				request.setAttribute("next", list);
				return;
			}
			ServletUtility.setDTO(dto, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("timetablelist doPOst End");
	}

	/**
	 * Contains submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("TimetableListCtl doPost Started");

		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));// coming from view

		// System.out.println("hi doget of fac ctl"+pageNo);

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println("time table list ctl dopost" + op);

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");

		// System.out.println("time table list ctl dopost"+ids[0]);

		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();
		
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
				ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;
				if (ids != null && ids.length > 0) {
					TimeTableDTO deletedto = new TimeTableDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));

						model.delete(deletedto);

						ServletUtility.setSuccessMessage("Data successfully deleted", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
				return;
			}
			list = model.search(dto, pageNo, pageSize);

			ServletUtility.setDTO(dto, request); //// to set value in preload after search

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record found...!!!! ", request);
				request.setAttribute("next", list);

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
		log.debug("timetable  dopost End");
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	protected String getView() {
		return ORSView.TIME_TABLE_LIST_VIEW;
	}

}
