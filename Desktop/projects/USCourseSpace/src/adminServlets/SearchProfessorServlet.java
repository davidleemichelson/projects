package adminServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import client.Professor;
import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class SearchProfessorServlet
 */
@WebServlet("/searchProfessor")
public class SearchProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchProfessorServlet() {
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

			List<Professor> professors = msql.getProfessorsByName(name);
    		if (professors==null){
    			res = "Error! No such user";
    		}else {
	    		for (int i=0; i<professors.size(); i++){
	    			res += "<div class='result' id='professor" + professors.get(i).getId() + "'>";
	    			res += "<div class='coursename'>" + professors.get(i).getName() + " (" + professors.get(i).getEmail() + ")" + "</div>";
	    			res += "<div class='options'>";
	    				res += "<div class='delete' onclick='deleteProfessor(\"" + professors.get(i).getId() + "\");'><i class='fa fa-trash' aria-hidden='true'></i></div>";
	    			res += "</div>";
	    			res += "</div>";
	    		}
    		}
    	}
    	response.getWriter().write(res);
	}

}
