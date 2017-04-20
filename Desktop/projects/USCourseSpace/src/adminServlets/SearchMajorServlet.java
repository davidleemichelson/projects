package adminServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Department;
import client.Professor;
import server.MySQLDriver;

/**
 * Servlet implementation class SearchMajorServlet
 */
@WebServlet("/searchMajor")
public class SearchMajorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMajorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String prefix = request.getParameter("prefix");
    	String res = "";
    	HttpSession session = request.getSession(); 
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection"); 
    	
    	if (session==null||msql==null){
    		res = "Error! No connection!"; 
    	} else {
    		List<Department> departments = msql.getDepartmentsByPrefix(prefix);
    		if (departments==null||departments.size()==0){
    			res = "Error! No such user";
    		}else {
	    		for (int i=0; i<departments.size(); i++){
	    			res += "<div class='result' id='department" + departments.get(i).getID() + "'>";
	    			res += "<div class='coursename'>" + departments.get(i).getName() + " (" + departments.get(i).getSchoolName()+ ")" + "</div>";
	    			res += "<div class='options'>";
	    				res += "<div class='delete' onclick='deleteDepartment(\"" + departments.get(i).getID() + "\");'><i class='fa fa-trash' aria-hidden='true'></i></div>";
	    			res += "</div>";
	    			res += "</div>";
	    		}
    		}
    	}
    	response.getWriter().write(res);
	}

}
