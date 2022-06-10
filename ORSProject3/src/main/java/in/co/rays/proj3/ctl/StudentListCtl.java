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
import in.co.rays.proj3.dto.StudentDTO;
import in.co.rays.proj3.exception.ApplicationException;
import in.co.rays.proj3.exception.RecordNotFoundException;
import in.co.rays.proj3.model.ModelFactory;
import in.co.rays.proj3.model.StudentModelInt;
import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.PropertyReader;
import in.co.rays.proj3.util.ServletUtility;

/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 * 
 */

/**
 * @author Rinku
 *
 */
@ WebServlet(name="StudentListCtl",urlPatterns={"/ctl/StudentListCtl"})
public class StudentListCtl extends BaseCtl 
{

    private static Logger log = Logger.getLogger(StudentListCtl.class);

	/**
	 * Populates dto object from request parameters
	 * 
	 * @param request
	 * @return
	 */
    protected void preload(HttpServletRequest request) {
    	
    	StudentModelInt model = ModelFactory.getInstance().getStudentModel();
    	try {
			List list=model.list();
			request.setAttribute("stdnt", list);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    protected BaseDTO populateDTO(HttpServletRequest request) {

        StudentDTO dto = new StudentDTO();

        dto.setFirstName(DataUtility.getString(request
                .getParameter("firstName")));
        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
        dto.setEmail(DataUtility.getString(request.getParameter("email")));

        return dto;
    }

    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("StudentListCtl doGet Start");
        List list = null;

        int pageNo = 1;

        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentDTO dto = (StudentDTO) populateDTO(request);

        String op = DataUtility.getString(request.getParameter("operation"));
        String ids[]=request.getParameterValues("ids");
        StudentModelInt model = ModelFactory.getInstance().getStudentModel();
        
        try {
            list = model.search(dto, pageNo, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No Record found....!!!!", request);
            }
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("StudentListCtl doGet End");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("StudentListCtl doPost Start");
        List list = null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;

        StudentDTO dto = (StudentDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");
        StudentModelInt model = ModelFactory.getInstance().getStudentModel();

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if(OP_NEW.equalsIgnoreCase(op))
            {
             ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
             return;
            }else if(OP_RESET.equalsIgnoreCase(op))
            {
            ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
            return;
            } 
            else if(OP_BACK.equalsIgnoreCase(op))
            {
            	ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
            	return;
            }
            
            else if(OP_DELETE.equalsIgnoreCase(op))
            {
            	 pageNo = 1;
                 if (ids != null && ids.length > 0) {
                     StudentDTO deletedto = new StudentDTO();
                     //System.out.println("id from select box in dopost of userlistctl in dlt "+ids[0]);
                     for (String id : ids) {
                         deletedto.setId(DataUtility.getInt(id));
                         try {
							model.delete(deletedto);
						 } 
                         catch (Exception e) 
                         {
							e.printStackTrace();
						 }
                         ServletUtility.setSuccessMessage( "Data successfully deleted", request);
                     }
                 } else {
                     ServletUtility.setErrorMessage( "Select at least one record", request);
                 }	
            }
            list = model.search(dto, pageNo, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No Record found...!!! ", request);
            }
            ServletUtility.setList(list, request);

            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("StudentListCtl doGet End");
    }

    /**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
    protected String getView() {
        return ORSView.STUDENT_LIST_VIEW;
    }
}
