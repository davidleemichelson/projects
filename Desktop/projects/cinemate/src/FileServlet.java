import main.Main;
import main.ApplicationInterface;
import data.DataStorage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		String filename = request.getParameter("filename");
		HttpSession session = request.getSession(true);
		session.setAttribute("filename", filename); // acts as a map, key "name" hs value name
		System.out.println("Successfully got filename: " + session.getAttribute("filename"));
		// now we have to give this filename to the java application
		// check for exceptions add if shitty, throw parsexception("which error") then print to frontend
		// filename has to be this good /Users/davidmichelson/Desktop/hw_dmichels/Assignment2/samplexml
		DataStorage data = new DataStorage (filename);
		String errorMessage = data.getErrorMessage();
		System.out.println("ERROR MESSAGE: " + errorMessage);
		if(!errorMessage.isEmpty()) { // if there is an error, show error
			System.out.println("2 ERROR MESSAGE: " + errorMessage);
			// errorMessage
			request.setAttribute("FileError", errorMessage);
			request.getRequestDispatcher("entry_dmichels.jsp").forward(request, response);
			System.out.println("getattribute: " + request.getAttribute("FileError"));
//			response.sendRedirect("entryPage.jsp"); // now we have name, serve up factory.jsp
			// for the future, if u need another string you would be screwed since getAttribute
			// wouldn't know which string to use. so maybe make a new class for like errorString
		}
		else {
			session.setAttribute("Data", data);
			request.getRequestDispatcher("loginPage.jsp").forward(request, response);
//			response.sendRedirect("loginPage.jsp"); // now we have name, serve up factory.jsp
		}

	}

}
