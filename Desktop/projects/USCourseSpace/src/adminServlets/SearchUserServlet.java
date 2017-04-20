package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class searchUserServlet
 */
@WebServlet("/searchUser")
public class SearchUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchUserServlet() {
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
    		User user = msql.getUser(name);
    		if (user==null){
    			res = "Error! No such user";
    		}else {
	    		res += "<div class='result' id='" + user.getId() + "'>";
	    			res += "<div class='coursename'>" + user.getFullName() + " (" + user.getUsername() + ")" + "</div>";
	    			res += "<div class='options'>";
	    				res += "<div class='edit' onclick='editUser(\"" + user.getUsername() + "\");'><i class='fa fa-pencil' aria-hidden='true'></i></div>";
	    				res += "<div class='delete'><i class='fa fa-trash' aria-hidden='true'></i></div>";
	    			res += "</div>";
	    		res += "</div>";
    		}
    	}
    	response.getWriter().write(res);
	}
}
