package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.User;
import client.Course;
import client.Review;
import server.MySQLDriver;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession(); 
    	if (session == null){
    		request.getRequestDispatcher("signup.jsp").forward(request, response);
    		return;
    	}

    	User user = (User)session.getAttribute("user");
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	msql.clearUnreadByUserID(user.getId());
    	session.setAttribute("user", msql.getUserByID(user.getId()));
    	user = (User)session.getAttribute("user");
    
    	List<Review> reviews = msql.getReviewsByFollowerID(user.getId());
    	
    		
    	request.setAttribute("fullname", user.getFullName());
    	request.setAttribute("email", user.getEmail());
    	request.setAttribute("major", user.getMajor());
    	
    	request.setAttribute("year", user.getCurrentYear());
    	request.setAttribute("image", user.getPhotoURL());
    	request.setAttribute("reviews", reviews);
    	
    	List<Course> coursesFollowing = msql.getCoursesFollowingByUserId(user.getId());
    	
    	request.setAttribute("coursesFollowing", coursesFollowing);
    	
    	request.getRequestDispatcher("profile.jsp").forward(request, response);
    	
    	// Get current logged in user 
    }
}
