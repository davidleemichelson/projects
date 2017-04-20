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
import client.Professor;
import server.MySQLDriver;

/**
 * Servlet implementation class ProfessorServlet
 */
@WebServlet("/professor")
public class ProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfessorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(); 
    	
    	int professor_id = Integer.parseInt(request.getParameter("id"));
    	
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
    	Professor p = msql.getProfessorByID(professor_id);
    	
    	List<Course> results = msql.getCoursesByProfessorID(professor_id);

    	for(Course result : results) {
			String courseDept = msql.getDepartmentById(result.getDeptId()).getName();
			String courseDeptAndNumber = courseDept + " " + result.getCode();
			result.setDeptAndNumber(courseDeptAndNumber);
			List<String> tags = msql.getTopThreeTagsForProfessor(professor_id);
			result.setTags(tags);
			
			msql.getRatings(result);	
    	}
    	
    	if(session.getAttribute("guest") != null) {
    		if((boolean) session.getAttribute("guest") == true) {
    			request.setAttribute("guest", true);
    		}
    		else {
    			request.setAttribute("guest", false);
    		}
    	}
    	
    	request.setAttribute("results", results);
    	request.setAttribute("professor", p); 
    	
    	request.getRequestDispatcher("professor.jsp").forward(request, response);
    	// Get current logged in user 
    }

}
