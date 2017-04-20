package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import client.Course;
import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class EditCourseServlet
 */
@WebServlet("/editCourse")
public class EditCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int id = Integer.parseInt(request.getParameter("courseid"));
    	
    	String res = "";
    	
    	HttpSession session = request.getSession(); 
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection"); 
    	
    	if (session==null||msql==null){
    		res = "Error! No connection!"; 
    	} else {
    		Course course = msql.getCourseByID(id);
    		if (course==null){
    			res = "Error! No such course";
    		}else {
    			Gson gson = new Gson();
    			res = gson.toJson(course);
    		}
    	}
    	response.getWriter().write(res);
	}

}
