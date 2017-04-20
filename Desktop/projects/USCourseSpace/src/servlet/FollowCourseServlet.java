package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Course;
import client.Department;
import client.Professor;
import client.Review;
import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class FollowCourseServlet
 */
@WebServlet("/FollowCourseServlet")
public class FollowCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowCourseServlet() {
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
    	if (msql == null){
    		request.getRequestDispatcher("login.jsp").forward(request, response);
    		return;
    	}
    	
    	int userId = ((User) session.getAttribute("user")).getId();
    	int courseId = Integer.parseInt(request.getParameter("id"));
    	
    	msql.addFollowingUserToCourse(userId, courseId);
    	
    	// Change "Follow" to "Unfollow"
    	
    	String jspString = "course?id=" + courseId;
    	
    	request.getRequestDispatcher(jspString).forward(request, response);
    	
    	
	}

}
