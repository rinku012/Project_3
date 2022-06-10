package in.co.rays.proj3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj3.util.DataUtility;
import in.co.rays.proj3.util.ServletUtility;

/**
 * performs error handling
 * 
 * @author Rinku
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(urlPatterns = "/ErrorCtl")
public class ErrorCtl extends BaseCtl {
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ErrorCtl.class);

	/**
	 * Contains display logic
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Method doGet Started");
		ServletUtility.forward(getView(), request, response);
		log.debug("Method doGet completed");

	}

	/**
	 * Contains Submit logic
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistrationCtl Method doGet Started");
		String op = DataUtility.getStringData(request.getParameter("operation"));
		System.out.println("operation" + op);
		if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
		}
		log.debug("UserRegistrationCtl Method doGet completed");

	}

	@Override
	protected String getView() {

		return ORSView.ERROR_VIEW;
	}
}
