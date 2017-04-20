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
import client.Review;
import client.User;
import server.MySQLDriver;

/**
 * Servlet implementation class ContinueAsGuestServlet
 */
@WebServlet("/ContinueAsGuestServlet")
public class ContinueAsGuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContinueAsGuestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession(); 
    	MySQLDriver msql = new MySQLDriver();
    	msql.connect();
    	
    	session.setAttribute("guest", true);
    	session.setAttribute("connection", msql);
    	request.setAttribute("guest", true);
    	
    	
    	request.getRequestDispatcher("search.jsp").forward(request, response);
    	
    	// Get current logged in user 
    }

}
