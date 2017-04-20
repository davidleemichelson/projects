

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.DataStorage;
import data.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/SearchUsersServlet")
public class SearchUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchUsersServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataStorage data = (DataStorage) request.getSession().getAttribute("Data");
		System.out.println("Testing if data works Current logged in user is: " + data.getLoggedInUser().getUsername());
		String searchInput = request.getParameter("SearchInput");
		System.out.println("User searched: " + searchInput);
		
		// now let's actually perform the search
		// flag that search was made (?)
		Set<User> searchResultSet = data.searchForUser(searchInput);
		Set<String> searchResultStringSet = new HashSet<String>();
		for(User user : searchResultSet) {
			searchResultStringSet.add(user.getUsername().toLowerCase());
		}
		request.setAttribute("SearchResults", searchResultStringSet);
		request.getRequestDispatcher("searchUsersPage.jsp").forward(request, response);

		/* Example of putting shit to the screen after posting from Login errors: */
//		if(!loginCheck.equals("success")) {
//			// put message on page
//			request.setAttribute("LoginError", loginCheck);
//			
//			request.getRequestDispatcher("loginPage.jsp").forward(request, response);
//			System.out.println("getattribute: " + request.getAttribute("LoginError"));
//		}
//		else {
//			// go to next page
//			// make the user loggedinuser in data storage
//			data.logInUser(username);
//			request.getRequestDispatcher("mainMenuPage.jsp").forward(request, response);
//		}
		
		
		
		
		
		request.getRequestDispatcher("searchUsersPage.jsp").forward(request, response);
	}

}
