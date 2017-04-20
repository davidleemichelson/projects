package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Professor;
import client.Tag;
import server.MySQLDriver;

/**
 * Servlet implementation class ComposeReviewServlet
 */
@WebServlet("/composeReview")
public class ComposeReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComposeReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(); 
    	if (request.getParameter("id")==null){
        	request.getRequestDispatcher("search.jsp").forward(request, response);
    		return;
    	}
    	int id = Integer.parseInt(request.getParameter("id"));
    	
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
    	List<Tag> tags = msql.getTags();
    	
    	request.setAttribute("courseid", id);
    	request.setAttribute("tags", tags);
    	
    	request.getRequestDispatcher("compose_review.jsp").forward(request, response);
    	// Get current logged in user 
    }


}
