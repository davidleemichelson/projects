package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Professor;
import server.MySQLDriver;

/**
 * Servlet implementation class AddProfessorServlet
 */
@WebServlet("/AddProfessorServlet")
public class AddProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProfessorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(); 
    	
    	String professorName = request.getParameter("professorname");
    	String professorEmail = request.getParameter("professoremail");
    	String professorImage = request.getParameter("professorimage");
    	
    	if (session==null){
        	request.getRequestDispatcher("index.jsp").forward(request, response);
        	return;
    	}
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	if (msql==null){
        	request.getRequestDispatcher("login.jsp").forward(request, response);
        	return;
    	}
    	msql.addProfessor(professorName, professorEmail, professorImage);
    	
    	request.getRequestDispatcher("admin").forward(request, response);
    
    }
}
