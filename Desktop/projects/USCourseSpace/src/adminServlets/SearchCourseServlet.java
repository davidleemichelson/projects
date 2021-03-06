package adminServlets;

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
 * Servlet implementation class SearchCourseServlet
 */
@WebServlet("/searchCourse")
public class SearchCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
    	String res = "";
    	HttpSession session = request.getSession(); 
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection"); 
    	
    	if (session==null||msql==null){
    		res = "Error! No connection!"; 
    	} else {
    		List<Course> courses = msql.getCoursesByName(name);

        	System.out.println(courses.size());
    		if (courses==null){
    			res = "Error! No such user";
    		}else {
    			for(int i=0; i<courses.size(); i++){
		    		res += "<div class='result' id='course" + courses.get(i).getId() + "'>";
		    			res += "<div class='coursename'>" + courses.get(i).getCode() + " - " + courses.get(i).getName() + "</div>";
		    			res += "<div class='options'>";
		    				res += "<div class='edit' onclick='editCourse(\"" + courses.get(i).getId() + "\");'><i class='fa fa-pencil' aria-hidden='true'></i></div>";
		    				res += "<div class='delete' onclick='deleteCourse(\"" + courses.get(i).getId() + "\");'><i class='fa fa-trash' aria-hidden='true'></i></div>";
		    			res += "</div>";
		    		res += "</div>";
    			}
    		}
    	}
    	response.getWriter().write(res);
	}

}
