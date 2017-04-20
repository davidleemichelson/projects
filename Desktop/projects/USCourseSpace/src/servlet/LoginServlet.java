package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.User;
import server.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// need to see if the login credentials exist in the users database?
//		<input type="text" name="username" placeholder="Username" required />
//		<input type="password" name="password" placeholder="Password" required />
    	
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	
    	System.out.println("LoginServelet - Attempting login with username/password: " + username + "/" + password);
    	
    	// Verifying user's password here
    	HttpSession session = request.getSession(); 
    	String error_message = "";
    	MySQLDriver msql = new MySQLDriver();
    	msql.connect();
    	session.setAttribute("guest", false);
    	request.setAttribute("guest", false);
    	User user = msql.getUser(username);
    	
    	if (user == null || !user.getPassword().equals(password)){
    		// user doesn't exist or password isn't correct
    		error_message = "Incorrect credentials. Please try again. ";
    		response.getWriter().write("Incorrect credentials. Please try again.");
    	} else {
    		session.setAttribute("connection", msql);
    		session.setAttribute("user", user);
    	}
    	if (!error_message.equals("")){
    		request.setAttribute("error", error_message);
//			request.getRequestDispatcher("login.jsp").forward(request, response);
    	}
    	else {
	    	// If valid user, then forward to the search page
	    	System.out.println("LoginServelet - Valid user login! Forwarding to search.jsp");
			// request.getRequestDispatcher("search.jsp").forward(request, response);
    	}
    	
		// Otherwise, generate an incorrect credentials message via AJAX 
    }

}
