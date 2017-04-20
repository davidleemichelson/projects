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

import server.MySQLDriver;
import client.Course;
import client.Professor;
/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// need to see if the login credentials exist in the users database?
//		<input type="text" name="username" placeholder="Username" required />
//		<input type="password" name="password" placeholder="Password" required />
    	HttpSession session = request.getSession(); 
    	
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	
    	if(session.getAttribute("guest") != null) {
    		if((boolean) session.getAttribute("guest") == true) {
    			request.setAttribute("guest", true);
    		}
    	}
    	
    	String searchValue = request.getParameter("search-value");
    	String searchType = request.getParameter("search-type");
    	
    	System.out.println("Searching for value: " + searchValue);
    	System.out.println("Search type: " + searchType);
    	
    	List<Course> results = new ArrayList<>();
    	switch(searchType) {
    	case "1":
    		System.out.println("Searching by professor.");
    		// This doesn't work
    		List<Professor> professors = msql.getProfessorsByName(searchValue);
    		// This works
    		System.out.println("Professors size: " + professors.size());
    		
    		for(Professor professor : professors) {
    			System.out.println("PROFESSOR: " + professor.getName() + " " + professor.getEmail() + " " + professor.getId());
    			results.addAll(msql.getCoursesByProfessorID(professor.getId()));
    		}
    		for(Course result : results) {
    			System.out.println("RESULT: " + result.getName());
    		}
    		break;
    	case "2":
    		System.out.println("Searching by course Id.");
    		results = msql.getCoursesByName(searchValue);
    		System.out.println("Course name results size: " + results.size());
    		
    		for(Course result : results){
    			System.out.println("Course found from course name: " + result.getName());
    		}    		
    		break;
    	case "3":
    		System.out.println("Searching by tag.");
    		results = msql.getCoursesByTagName(searchValue);
    		System.out.println("Course name results size: " + results.size());
    		
    		for(Course result : results){
    			System.out.println("Course found from course name: " + result.getName());
    		}    	
    		break;
    	case "4":
    		results = msql.getCoursesByDeptName(searchValue);
    		System.out.println("Dept results size: " + results.size());
    		for(Course result : results){
    			System.out.println("Course found from dept name: " + result.getName());
    		}    		
    		break;
    	}
    	
    	for(Course result : results) {
			String courseDept = msql.getDepartmentById(result.getDeptId()).getName();
			String courseDeptAndNumber = courseDept + " " + result.getCode();
			result.setDeptAndNumber(courseDeptAndNumber);
			List<String> tags = msql.getTopThreeTagsForCourse(result.getId());
			result.setTags(tags);
			
			msql.getRatings(result);
			
    	}
    	
    	request.setAttribute("results", results);
    	
		request.getRequestDispatcher("search.jsp").forward(request, response);
		
		// Present results to page
    }
    

}
