package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.MySQLDriver;
import client.User;

/**
 * Servlet implementation class FinishEditProfileServlet
 */
@WebServlet("/FinishEditProfileServlet")
public class FinishEditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinishEditProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// need to see if the login credentials exist in the users database?
//		<input type="text" name="username" placeholder="Username" required />
//		<input type="password" name="password" placeholder="Password" required />
    	HttpSession session = request.getSession();
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	User user = (User) session.getAttribute("user");

    	String fullname, email, avatar, major, year; 

        if(request.getParameter("fullname") != null) {
    		fullname = request.getParameter("fullname");
    	}
        else {
        	fullname = user.getFullName();
        }
        if(request.getParameter("email") != null) {
    		email = request.getParameter("email");
    	}
        else {
        	email = user.getEmail();
        }
        if(request.getParameter("avatar") != null) {
    		avatar = request.getParameter("avatar");
    	}
        else {
        	avatar = user.getPhotoURL();
        }
        if(request.getParameter("major") != null) {
    		major = request.getParameter("major");
    	}
        else {
        	major = user.getMajor();
        }
        if(request.getParameter("year") != null) {
    		year = request.getParameter("year");
    	}
        else {
        	year = user.getCurrentYear();
        }
    	
    	
        msql.updateUser(user.getUsername(), user.getPassword(), email, fullname, major, year, avatar);
        
        user = msql.getUser(user.getUsername());
        
        session.setAttribute("user", user);
        
    	request.setAttribute("fullname", user.getFullName());
    	request.setAttribute("email", user.getEmail());
    	request.setAttribute("major", user.getMajor());
    	
    	request.setAttribute("year", user.getCurrentYear());
    	request.setAttribute("image", user.getPhotoURL());

        
        request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
    	
    	
    }

}
