package adminServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Department;
import client.Professor;
import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	if (session == null){
    		request.getRequestDispatcher("login.jsp").forward(request, response);
    		return; 
    	}
    	
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    			
    	User user = (User)session.getAttribute("user");
    	
    	if (user==null||msql==null){
    		request.getRequestDispatcher("login.jsp").forward(request, response);
    		return; 
    	}
    	if (!user.isAdmin()) {
    		request.getRequestDispatcher("search.jsp").forward(request, response);
    		return; 
    	}

    	// TODO 
    	// need to check if a user is admin
    	List<Professor> professors = msql.getProfessors();
    	List<Department> departments = msql.getDepartments();
    	
    	request.setAttribute("professors", professors);
    	request.setAttribute("departments", departments);

    	request.getRequestDispatcher("manager_admin.jsp").forward(request, response);
    }

}
