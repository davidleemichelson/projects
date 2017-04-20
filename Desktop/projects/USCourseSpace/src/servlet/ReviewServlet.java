package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import client.Course;
import client.User;
import message.NotificationServer;
import server.MySQLDriver;

import org.json.JSONArray;
import org.json.JSONException;



/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	if (session == null){
    		response.getWriter().write("No connection!");
    		return;
    	}
    	
    	MySQLDriver msql = (MySQLDriver) session.getAttribute("connection");
    	if (msql == null){
    		response.getWriter().write("No connection!");
    		return;
    	}
        try {
	    	// get the course json object
	        BufferedReader reader = request.getReader();
	        String line;
	        
	        line = reader.readLine();
	        System.out.println(line);
	        
	        reader.close();
	        
			JSONObject json = new JSONObject(line);
			boolean midterms, finals, essays, assignments, projects, quizzes;
	    	midterms = finals = essays = assignments = projects = quizzes = false;
	    			
	    	if(json.has("midterms")) {
	    		midterms = true;
	    	}
	    	if(json.has("finals")) {
	    		finals = true;
	    	}
	    	if(json.has("essays")) {
	    		essays = true;
	    	}
	    	if(json.has("assignments")) {
	    		assignments = true;
	    	}
	    	if(json.has("projects")) {
	    		projects = true;
	    	}
	    	if(json.has("quizzes")) {
	    		quizzes = true;
	    	}
	    	
	    	JSONArray tagsarray = json.getJSONArray("tags");
	    	List<Integer> tags = new ArrayList<Integer>();
	    	for (int i=0; i<tagsarray.length(); i++){
	    		tags.add(tagsarray.getInt(i));
	    	}
	    	
	    	
	    	String comments = json.getString("comments");
	    	User user = (User) session.getAttribute("user");
	    	Course course = msql.getCourseByID(json.getInt("courseid"));
	    	
	    	// create the review
	    	msql.createReview(user.getId(), json.getInt("courseid"), course.getProfessorId(), comments, json.getString("semesterandyear"),
	    			midterms, finals, essays, assignments, projects, quizzes, json.getDouble("grading"), json.getDouble("workload"),
	    			json.getDouble("content"), json.getDouble("teaching"), tags);

	    	// send message to all followers
	    		// serializable 
	    	Socket s = new Socket("localhost", NotificationServer.port);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
	    	
	    	List<Integer> fs = msql.getAllFollowersByCourseID(json.getInt("courseid"));
	    	
	    	List<User> followers = new ArrayList<User>();
	    	for (int f:fs){
	    		msql.setUnreadByUserID(f);
	    			// send user the notification server
	    		System.out.println("Hello it's me! ");
	    		oos.writeObject(msql.getUserByID(f));
	    	}
	    	
	    	
	    	// return response
	    	response.setCharacterEncoding("UTF-8");
    		response.getWriter().write("Success!");
    		
    		// close all messagees
    		if(s!=null) {
    			s.close();
    		}
    		if (oos!=null){
    			oos.close();
    		} 
    		if (ois!=null) {
    			ois.close();
    		}
		}  catch (JSONException e) {
			e.printStackTrace();
		}
    }


}
