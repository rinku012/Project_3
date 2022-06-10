package in.co.rays.proj3.ctl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj3.util.ServletUtility;

/**
 * Main Controller performs session checking and logging operations before
 * calling any application controller. It prevents any user to access
 * application without login.
 */


/**
 * @author Rinku
 *
 */
@WebFilter(urlPatterns={"/ctl/*","/doc/*"})
public class FrontController implements Filter 
{

	/** 
	 * Front Controller init Method
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		
		System.out.println("init in front controller");
	}

	/** 
	 * Do Filter Mehtod in Front Controller
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException 
	{
		System.out.println("doFilter Method in FrontController()");
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(true);

        if (session.getAttribute("user") == null) 
        {   
        	String uri= request.getRequestURI();
        	System.out.println("URI in Front Controller : "+uri);
        	session.setAttribute("URI", uri);
            request.setAttribute("message","Your session has been expired...!! Please Re-Login...!!");
            ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
            return;
        } 
        else 
        {
            chain.doFilter(req, resp);               
        }
 			
	}

	/** 
	 * Destroy Mehtod in Front Controller
	 */
	public void destroy() {	
		System.out.println("Destroy in front controller");
	}

}
