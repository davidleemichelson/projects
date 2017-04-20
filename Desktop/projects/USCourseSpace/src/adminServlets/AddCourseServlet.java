package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Course;
import server.MySQLDriver;

/**
 * Servlet implementation class AddCourseServlet
 */
@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Course Name: <input type="text" name="coursename"/> <br>
//		Course Prefix: <input type="text" name="courseprefix"/> <br>
//		Course Number: <input type="text" name="coursenumber"/> <br>
//		Professor: <input type="text" name="professor"/> <br>
    	
    	int courseid = Integer.parseInt(request.getParameter("courseid"));
    	String courseName = request.getParameter("coursename");
    	int coursePrefixID = Integer.parseInt(request.getParameter("courseprefix"));
    	String courseNumber = request.getParameter("coursenumber");
    	int courseProfessorID = Integer.parseInt(request.getParameter("courseprofessor"));
    	String courseDescription = request.getParameter("coursedescription");
    	
    	HttpSession session = request.getSession(); 
    	if (session==null){
        	request.getRequestDispatcher("index.jsp").forward(request, response);
        	return;
    	}
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	if (msql==null){
        	request.getRequestDispatcher("login.jsp").forward(request, response);
        	return;
    	}
    	System.out.println(courseid);
    	
    	Course course = msql.getCourseByID(courseid);
    	if(course!=null){
    		msql.updateCourse(courseid, coursePrefixID, courseNumber, courseName, courseDescription, courseProfessorID);
    	}
    	else
    		msql.addNewCourse(coursePrefixID, courseNumber, courseName, courseDescription, courseProfessorID);
    	
    	request.getRequestDispatcher("admin").forward(request, response);
    }

}
