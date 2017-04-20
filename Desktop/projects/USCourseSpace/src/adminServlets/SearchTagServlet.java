package adminServlets;

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
 * Servlet implementation class SearchTagServlet
 */
@WebServlet("/searchTag")
public class SearchTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchTagServlet() {
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
			List<Tag> tags = msql.getTagsByName(name);
    		if (tags==null){
    			res = "Error! No such user";
    		}else {
	    		for (int i=0; i<tags.size(); i++){
	    			res += "<div class='result' id='tag" + tags.get(i).getID() + "'>";
	    			res += "<div class='coursename'>" + tags.get(i).getName() + "</div>";
	    			res += "<div class='options'>";
	    				res += "<div class='delete' onclick='deleteTag(\"" + tags.get(i).getID() + "\");'><i class='fa fa-trash' aria-hidden='true'></i></div>";
	    			res += "</div>";
	    			res += "</div>";
	    		}
    		}
    	}
    	response.getWriter().write(res);
	}

}
