package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.MySQLDriver;

/**
 * Servlet implementation class SignupSerlvet
 */
@WebServlet("/SignupServlet")
public class SignupSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupSerlvet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// need to see if the login credentials exist in the users database?
//		<input type="text" name="username" placeholder="Username" required />
//		<input type="password" name="password" placeholder="Password" required />
    	
 
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String email = request.getParameter("email");
    	String fullname = request.getParameter("fullname");
    	String avatarURL = request.getParameter("avatar");
    	String major = request.getParameter("major");
    	String year = request.getParameter("year");
    	
    	// mysql driver
    	// add that user to database
    	HttpSession session = request.getSession();
    	MySQLDriver msql = new MySQLDriver();
    	msql.connect();
    	session.setAttribute("connection", msql);
    	session.setAttribute("guest", false);
    	request.setAttribute("guest", false);
    	// If user already exists, then give an error
    	if(msql.userExists(username)) {
    		// Spit out error to frontend
    		response.getWriter().write("Error! User already exists.");
    	} 
    	else {
        	msql.addUser(username, password, email, fullname, major, year, avatarURL);
        	session.setAttribute("user", msql.getUser(username));
        	// request.getRequestDispatcher("search.jsp").forward(request, response);
    	}
    	
    	
    	System.out.println("Singing up user with the following credentials: ");
    	System.out.println("username: " + username);
    	System.out.println("password: " + password);
    	System.out.println("email: " + email);
    	System.out.println("avatarURL: " + avatarURL);
    	System.out.println("major: " + major);
    	System.out.println("year: " + year);
    	
    }
	

}
