
package in.co.rays.proj3.ctl;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.internal.SessionImpl;

import in.co.rays.proj3.dto.UserDTO;
import in.co.rays.proj3.util.HibernateDataSource;
import in.co.rays.proj3.util.JDBCDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Servlet implementation class Jasper
 * 
 * 
 * @author Rinku
 * 
 * 
 */

@WebServlet(name = "JasperCtl", urlPatterns = { "/ctl/JasperCtl" })
public class JasperCtl extends BaseCtl {

	/**
	*
	*/

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new jasper ctl.
	 */
	public JasperCtl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DoGet Jasper Report");
		try {

			JasperReport jasperReport = JasperCompileManager
					.compileReport("G:\\ORSProject3\\src\\main\\webapp\\report\\MyjasperFile.jrxml");

			HttpSession session = request.getSession(true);
			UserDTO dto = (UserDTO) session.getAttribute("user");
			dto.getFirstName();
			dto.getLastName();

			Map<String, Object> map = new HashMap();
			map.put("user", dto.getFirstName() + " " + dto.getLastName());
			Connection conn = null;

			ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj3.bundle.System");

			String Database = rb.getString("DATABASE");

			if ("Hibernate".equalsIgnoreCase(Database)) {
				conn = ((SessionImpl) HibernateDataSource.getSession()).connection();
			}

			if ("JDBC".equalsIgnoreCase(Database)) {
				conn = JDBCDataSource.getConnection();
			}

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);

			byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

			response.setContentType("application/pdf");
			response.getOutputStream().write(pdf);
			response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	*
	*/
	protected String getView() {
		return null;
	}
}
