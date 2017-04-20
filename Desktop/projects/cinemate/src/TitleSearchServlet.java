

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
import data.Movie;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/TitleSearchServlet")
public class TitleSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TitleSearchServlet() {
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
//		System.out.println("Testing if data works Current logged in user is: " + data.getLoggedInUser().getUsername());
		String searchInput = request.getParameter("SearchInput");
		System.out.println("User searched: " + searchInput);
		
		Set<Movie> searchResultSet = new HashSet<Movie>();
		if(data.searchByTitle(searchInput.toLowerCase()) != null) {
			searchResultSet = data.searchByTitle(searchInput.toLowerCase());
		}
		Set<String> searchResultStringSet = new HashSet<String>();
		for(Movie movie : searchResultSet) {
			searchResultStringSet.add(movie.getTitle());
		}
		request.setAttribute("TitleSearchResults", searchResultStringSet);
		request.getRequestDispatcher("titleSearch.jsp").forward(request, response);
		
		/*
		 * Use these:
		protected Set<Movie> searchByGenre(String genre){
			return genreToMovies.get(genre.toLowerCase());
		}
		
		protected Set<Movie> searchByTitle(String title){
			return titleToMovies.get(title.toLowerCase());
		}
		
		protected Set<Movie> searchByActor(String actor){
			return actorToMovies.get(actor.toLowerCase());
		}
		
//		 */
//		// now let's actually perform the search
//		// flag that search was made (?)
//		Set<User> searchResultSet = data.searchForUser(searchInput);
//		Set<String> searchResultStringSet = new HashSet<String>();
//		for(User user : searchResultSet) {
//			searchResultStringSet.add(user.getUsername().toLowerCase());
//		}
//		request.setAttribute("SearchResults", searchResultStringSet);
//		request.getRequestDispatcher("searchUsersPage.jsp").forward(request, response);
//		
//
//		/* Example of putting shit to the screen after posting from Login errors: */
////		if(!loginCheck.equals("success")) {
////			// put message on page
////			request.setAttribute("LoginError", loginCheck);
////			
////			request.getRequestDispatcher("loginPage.jsp").forward(request, response);
////			System.out.println("getattribute: " + request.getAttribute("LoginError"));
////		}
////		else {
////			// go to next page
////			// make the user loggedinuser in data storage
////			data.logInUser(username);
////			request.getRequestDispatcher("mainMenuPage.jsp").forward(request, response);
////		}
//		
//		
//		
//		
//		
//		request.getRequestDispatcher("searchUsersPage.jsp").forward(request, response);
	}

}
