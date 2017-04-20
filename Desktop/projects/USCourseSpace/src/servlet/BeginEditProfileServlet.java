package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Course;
import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class BeginEditProfileServlet
 */
@WebServlet("/BeginEditProfileServlet")
public class BeginEditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BeginEditProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession(); 
    	if (session == null){
    		request.getRequestDispatcher("signup.jsp").forward(request, response);
    		return;
    	}
    	
    	User user = (User)session.getAttribute("user");
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    		
    	request.setAttribute("fullname", user.getFullName());
    	request.setAttribute("email", user.getEmail());
    	request.setAttribute("major", user.getMajor());
    	
    	request.setAttribute("year", user.getCurrentYear());
    	request.setAttribute("image", user.getPhotoURL());
    	
    	request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
    	
    	// Get current logged in user 
    }
}
