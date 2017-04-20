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

import com.google.gson.Gson;

import client.Course;
import client.Department;
import client.Professor;
import client.Review;
import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class CourseServlet
 */
@WebServlet("/course")
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseServlet() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	int courseid = Integer.parseInt(request.getParameter("id"));
    	if (session == null){
    		request.getRequestDispatcher("login.jsp").forward(request, response);
    		return;
    	}
    	
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	if (msql == null){
    		request.getRequestDispatcher("login.jsp").forward(request, response);
    		return;
    	}
    	
    	Course course = msql.getCourseByID(courseid);
    	List<Review> reviews = new ArrayList<Review>(); // TODO // msql.getReviewsByCourseId(courseid);

    	Professor prof = msql.getProfessorByID(course.getProfessorId());
    	Department department = msql.getDepartmentById(course.getDeptId());
    	reviews = msql.getReviewsByCourseId(course.getId());
    	for (Review r:reviews){
    		List<String> tags = msql.getReviewTags(r.getId());
			r.setTags(tags);
    	}
    	
    	msql.getRatings(course);
    	
    	request.setAttribute("course", course);
    	request.setAttribute("reviews", reviews);
    	request.setAttribute("professor", prof);
    	request.setAttribute("department", department);
    	request.setAttribute("reviews", reviews);
    	
    	// check if the user follows this course, set an attribute to tell the course.jsp 
    	if(session.getAttribute("guest") != null) {
    		if((boolean) session.getAttribute("guest") == true) {
    			request.setAttribute("guest", true);
    		}
    	}
    	else {
	    	int userid = ((User) session.getAttribute("user")).getId();
	    	
	    	if(msql.userFollowsCourse(userid, courseid)) {
	    		request.setAttribute("follows", true);
	    	}
	    	else {
	    		request.setAttribute("follow", false);
	    	}
    	}
    	
		request.getRequestDispatcher("course.jsp").forward(request, response);	
	}
}
