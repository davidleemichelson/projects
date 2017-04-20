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
 * Servlet implementation class DeleteDepartmentServlet
 */
@WebServlet("/deleteDepartment")
public class DeleteDepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDepartmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String pid = request.getParameter("id");
    	String res = "";
    	HttpSession session = request.getSession(); 
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection"); 
    	
    	if (session==null||msql==null){
    		res = "Error! No connection!"; 
    	} else {
    		msql.deleteDepartmentByID(Integer.parseInt(pid));
    		res = "Success!";
    		/*
			Professor professor = msql.getProfessorByID(Integer.parseInt(pid));
    		if (professor==null){
    			res = "Error! No such user";
    		}else {
	    		msql.deleteProfessorByID(Integer.parseInt(pid));
	    		res = "Success!";
    		}
    		*/
    	}
    	response.getWriter().write(res);
    	
    }

}
