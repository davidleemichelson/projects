package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	if (msql == null){
        	request.getRequestDispatcher("index.jsp").forward(request, response);
    		return;
    	}
    	
    	String username = request.getParameter("user_name");
    	String password = request.getParameter("user_password");
    	String email = request.getParameter("user_email");
    	String image = request.getParameter("user_image");
    	String major = request.getParameter("user_major");
    	String year = request.getParameter("year");
    	String fullname = request.getParameter("user_fullname");
    	

    	User user = msql.getUser(username);
    	if(user==null) {
        	System.out.println(username + " " + email + " " + image + " " + major + " " + year + " " + fullname);
        	msql.addUser(username, password, email, fullname, major, year, image);
        	request.setAttribute("addUserMessage", "success");
        	request.getRequestDispatcher("manager_admin.jsp").forward(request, response);
    	}
    	else {
    		System.out.println("Updating: " + username + " " + email + " " + image + " " + major + " " + year + " " + fullname + " " + password);
    		msql.updateUser(username, password, email, fullname, major, year, image);
        	request.setAttribute("addUserMessage", "success");
        	request.getRequestDispatcher("manager_admin.jsp").forward(request, response);
    	}
    	
    }
}
