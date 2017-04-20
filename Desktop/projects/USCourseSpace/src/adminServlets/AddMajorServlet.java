package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.MySQLDriver;

/**
 * Servlet implementation class AddMajorServlet
 */
@WebServlet("/AddMajorServlet")
public class AddMajorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMajorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(); 
    	String prefix = request.getParameter("department_prefix");
    	String school = request.getParameter("school_name");
    	
    	if (session==null){
        	request.getRequestDispatcher("index.jsp").forward(request, response);
        	return;
    	}
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	if (msql==null){
        	request.getRequestDispatcher("login.jsp").forward(request, response);
        	return;
    	}
    	if(prefix==null)
    		return; 
    	
    	msql.addDepartment(prefix, school); 
    	
    	request.getRequestDispatcher("admin").forward(request, response);
    }

}
