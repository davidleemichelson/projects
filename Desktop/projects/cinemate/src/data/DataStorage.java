package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.ParseException;

public class DataStorage {
	//maps a username to a user object
	public Map<String, User> usersMap;
	//maps a movie title to a movie object
	protected Map<String, Movie> moviesMap;
	//maps a case insensitive username to a list of case sensitive matches
	private Map<String, Set<User>> usernameToUsers;
	//maps a case insensitive fname to a list of case sensitive matches
	private Map<String, Set<User>> fnameToUsers;
	//maps a case insensitive lname to a list of case sensitive matches
	private Map<String, Set<User>> lnameToUsers;
	//maps a username to a list of followers
	private Map<String, Set<String>> usernameToFollowers;
	//maps an actor to a list of movies
	private Map<String, Set<Movie>> actorToMovies;
	//maps a genre to a list of movies
	private Map<String, Set<Movie>> genreToMovies;
	//maps a case insensitive movie title to a list of case sensitive matches
	private Map<String, Set<Movie>> titleToMovies;
	//genres
	private List<String> genresList;
	//actions
	private List<String> actionsList;
	//errors
	private String errorMessage;
	
	private User loggedInUser;

	private Document doc;
	
	//for strings used more than once, a constant is used instead
	protected static final String genre = "genre";
	protected static final String title = "title";
	protected static final String actor = "actor";
	
	private static final String actors = "actors";
	private static final String writers = "writers";
	private static final String rating = "rating";
	private static final String action = "action";
	private static final String username = "username";
	private static final String feed = "feed";
	private static final String following = "following";
	private static final String image = "image";
	
	public DataStorage(String filepath) {
		usersMap = new HashMap<>();
		moviesMap = new HashMap<>();
		genresList = new ArrayList<>();
		actionsList = new ArrayList<>();
		usernameToUsers = new HashMap<>();
		fnameToUsers = new HashMap<>();
		lnameToUsers = new HashMap<>();
		usernameToFollowers = new HashMap<>();
		actorToMovies = new HashMap<>();
		genreToMovies = new HashMap<>();
		titleToMovies = new HashMap<>();
		errorMessage = "";
		
		try{
			//create the document object and parse it
			System.out.println("filepath: " + filepath);
			DocumentBuilder dBuilder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
			System.out.println("ccc");
			File file = new File(filepath);
			System.out.println("file getAbsolutePath(): " + file.getAbsolutePath());
			doc = dBuilder.parse(new File(filepath));
			System.out.println("bbbb");
			parse();
			System.out.println("aaa");
			for (User user : usersMap.values()) user.setFollowers(usernameToFollowers.get(user.getUsername()));
		}
		catch (SAXException | IOException | ParserConfigurationException e){
			errorMessage = e.getMessage();
			// This sees if there is an invalid filename; but we also have to check if the file is valid while we parse
			System.out.println(e.getClass() + " " + e.getCause() + " " + e.getMessage());
		}
		
	}
	public User getLoggedInUser() {
		return loggedInUser;
	}
	public void logInUser(String username) {
		User user = usersMap.get(username);
		this.loggedInUser = user;
		System.out.println("Logged in user! [" + loggedInUser.getUsername() + "]");
	}
	public String checkLogin(String username, String password) {
		if(usernameToUsers.containsKey(username) && usersMap.get(username).getPassword().equals(password)) {
			return "success";
		}
		else if(usernameToUsers.containsKey(username) && !usersMap.get(username).getPassword().equals(password)) {
			return "Invalid password.";
		}
		else if(!usernameToUsers.containsKey(username)) {
			return "Invalid username.";
		}
		return "";
	}
	//get error
	public String getErrorMessage() {
		return errorMessage;
	}
	//search methods
	public Set<Movie> searchByGenre(String genre){
		return genreToMovies.get(genre.toLowerCase());
	}
	
	public Set<Movie> searchByTitle(String title){
		return titleToMovies.get(title.toLowerCase());
	}
	
	public Set<Movie> searchByActor(String actor){
		return actorToMovies.get(actor.toLowerCase());
	}
	
	public Set<User> searchForUser(String username){
		Set<User> searchSet = new HashSet<User>();
		try {
			searchSet.addAll(usernameToUsers.get(username.toLowerCase()));
		}
		catch (Exception e) {
			System.out.println("no username: " + e.getMessage());
		}
		try {
			searchSet.addAll(fnameToUsers.get(username.toLowerCase()));
		}
		catch (Exception e) {
			System.out.println("no first name: " + e.getMessage());
		}
		try {
			searchSet.addAll(lnameToUsers.get(username.toLowerCase()));
		}
		catch (Exception e) {
			System.out.println("no last name: " + e.getMessage());
		}
		// take all 3 sets and stick into big set and return that
		
		return searchSet;
	}
	
	//takes a NodeList and either the list of actions or list of genres 
	private void addToGenresOrActions(List<String> toAddTo, NodeList startNode){
		//children of the parent tag (either <actions> or <genres>)
		NodeList children = startNode.item(0).getChildNodes();
		//iterate through the children and get their text content, add it to the list
		for (int i = 0; i<children.getLength(); i++){

			if (children.item(i).hasChildNodes()){

				toAddTo.add(children.item(i).getFirstChild().getTextContent());
			}
		}
	}
	
	private void parse(){
		
		NodeList genresNodeList = doc.getElementsByTagName("genres");
		NodeList actionsNodeList = doc.getElementsByTagName("actions");
		NodeList moviesNodeList = doc.getElementsByTagName("movies");
		
		System.out.println("moviesNodeList size: " + moviesNodeList.getLength());
		System.out.println("moviesNodeList children size: " + moviesNodeList.item(0).getChildNodes().getLength());
		System.out.println("moviesNodeList children name: " + moviesNodeList.item(0).getFirstChild().getNodeName());
		
		boolean goodMovies = false;
		NodeList movieChildren = moviesNodeList.item(0).getChildNodes();
		for(int i = 0; i < movieChildren.getLength(); i++) {
			Node node = movieChildren.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				System.out.println(node.getNodeName());
				if(node.getNodeName().equals("movie")) {
					goodMovies = true;
				}
			}
		}
		if(!goodMovies) {
			System.out.println("There are no movie objects found under the movies tag.");
			errorMessage = "There are no movie objects found under the movies tag.";
		}
		
		NodeList usersNodeList = doc.getElementsByTagName("users");
		//parsing actions and genres
		addToGenresOrActions(genresList, genresNodeList);
		addToGenresOrActions(actionsList, actionsNodeList);
		//parsing movies and users (parsing movies first so we will have the movie objects ready when parsing a user's feed)
		parseObjects(moviesNodeList, true);
		parseObjects(usersNodeList, false);
		// ok now we have users, can check if all of their followings are valid?
	}
	
	//the same logic is used for iterating through movie and user nodes. So, we pass in a boolean to determine the helper method to call
	private void parseObjects(NodeList rootNode, Boolean isMovie){
		NodeList children = rootNode.item(0).getChildNodes();
		
		for (int i = 0; i<children.getLength(); i++){
			Node node = children.item(i);
			//some of the elements show up as text elements, so we need this check before we choose to parse
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				if (isMovie) parseMovie(node);
				else parseUser(node);
			}
		}
		// ok now we have all the Users
		if(!isMovie) { // if we just parsed users, now we can check their following list
			for(User value : usersMap.values()) { // now looking at all User objects
				// looking at current User, value
				for(String username : value.getFollowing()){
					if(!usersMap.containsKey(username)) { 
						System.out.println("A USER HAS A BAD NAME in their following list: [" + username + "].");
						errorMessage = "A user has a non-existing username in their following list.";
					}
				}
			}	
		}
	}
	
	//PARSE MOVIES
	//parsing one movie object
	private void parseMovie(Node rootNode){
		//get the fields of the movie
		NodeList movieFields = rootNode.getChildNodes();
		Movie movie = new Movie();
		
		for (int i = 0; i<movieFields.getLength(); i++){
			//get the current field of the movie
			Node movieField = movieFields.item(i);
			String nodeName = movieField.getNodeName();
			
			switch (nodeName){
			
				case "director":
					movie.setDirector(movieField.getFirstChild().getTextContent());
					break;
				case writers:
					//parse the writers in a helper method
					parseMovieSubList(writers, movieField, movie);
					break;
				case "year":
					try {
						movie.setYear(Integer.parseInt(movieField.getFirstChild().getTextContent()));
						System.out.println("Successfully parsed an int for movie year: [" +movieField.getFirstChild().getTextContent() + "].");
					}
					catch (Exception e) {
						System.out.println("***Could not successfully parse an int for movie year: [" +movieField.getFirstChild().getTextContent() + "].");
						errorMessage = "Non-string field year has a string value";
					}
					break;
				case actors:
					//parse the actors in a helper method
					parseMovieSubList(actors, movieField, movie);
					break;
				case genre:
					String genreName = movieField.getFirstChild().getTextContent();
					if(!genresList.contains(genreName)) {
						System.out.println("Movie contains non-existing genre: [" + genreName + "].");
						errorMessage = "A movie has a non-existing genre listed";
					}
					movie.setGenre(movieField.getFirstChild().getTextContent());
					break;
				case title:
					movie.setTitle(movieField.getFirstChild().getTextContent());
					break;
				case "description":
					movie.setDescription(movieField.getFirstChild().getTextContent());
					break;
				case rating:
					//check to see if there is a non-empty rating value
					if (movieField.getFirstChild() != null) {
						if(movieField.getFirstChild().getNodeValue() instanceof String) {
							try {
								double value = Double.parseDouble(movieField.getFirstChild().getNodeValue());
								System.out.println("Successfully changed string into double: " + value);
							} catch (Exception e) {
								// if we catch this, then we actually got a bad rating string up
								System.out.println("Couldn't change string to a double: [" + movieField.getFirstChild().getNodeValue() +"].");
								errorMessage = "Non-string field rating for movie has a string value";
								break;
							}
							System.out.println("Non-string field rating has a string value: [" + movieField.getFirstChild().getNodeValue() + "].");
						}
						movie.setRating(Double.parseDouble(movieField.getFirstChild().getTextContent()));
					}
					break;
			}
		}
		//add the movie to the movies map
		moviesMap.put(movie.getTitle(), movie);
		//add the movie to the correct genre's set
		addObjectToMap(genreToMovies, movie.getGenre().toLowerCase(), movie);
		System.out.println("movie.getGenre added " + movie.getGenre());
		//add the movie to the correct title's set
		addObjectToMap(titleToMovies, movie.getTitle().toLowerCase(), movie);
	}
	
	//used to parse actors and writers
	private void parseMovieSubList(String key, Node movieNode, Movie movie){
		NodeList children = movieNode.getChildNodes();
		
		for (int j = 0; j<children.getLength(); j++) {
			
			Node child = children.item(j);
			//if the current child is of the appropriate Node type
			if (child.getNodeType() == Node.ELEMENT_NODE) {
		       
		        String value = children.item(j).getFirstChild().getTextContent();
		        //if we are parsing the actors
				if (key.equals(DataStorage.actors)){
					//add the actor to the movie, then add the movie to the map from actors to movies
					movie.addActor(value);
					addObjectToMap(actorToMovies, value.toLowerCase(), movie);
				}
				//if we are parsing the writers, add the value to the movie's list of writers
				else movie.addWriter(children.item(j).getFirstChild().getTextContent());
		    }
		}
		
	}
	
	//PARSE USERS
	//parse a user object
	private void parseUser(Node rootNode){
		NodeList children = rootNode.getChildNodes();
		User user = new User();
		
		for (int i = 0; i<children.getLength(); i++){
			Node child = children.item(i);
			String nodeName = child.getNodeName();
			
			switch(nodeName){
				case username:
					user.setUsername(child.getFirstChild().getTextContent());
					break;
				case "fname":
					user.setFName(child.getFirstChild().getTextContent());
					break;
				case "lname":
					user.setLName(child.getFirstChild().getTextContent());
					break;
				case "password":
					user.setPassword(child.getFirstChild().getTextContent());
					break;
				case "image":
					user.setImage(child.getFirstChild().getTextContent());
					System.out.println("Successfully set user's image link to: [" + user.getImage() + "]" );
				case following:
					parseUserSubList(following, child, user);
					break;
				//parse the feed
				case feed:
					parseUserSubList(feed, child, user);
					break;
			}
		}
		//add the user to the users map
		System.out.println("Successfully set user [" + user.getUsername() + "]'s image link to: [" + user.getImage() + "]" );
		usersMap.put(user.getUsername(), user);
		//add the user with their lowercased username to the usernameToUsers map
		addObjectToMap(usernameToUsers, user.getUsername().toLowerCase(), user);
		
		// now, let's do first names
		addToFnameMap(user);
		addToLnameMap(user);
		
		
	}
	
	private void addToFnameMap(User user) {
		System.out.println("User first name: " + user.getFName());	
		if(fnameToUsers.containsKey(user.getFName())) { // if this first name is already in the set, add the new user to the set value
			fnameToUsers.get(user.getFName()).add(user);
		}
		else { // otherwise, we need to add this key value pair to the fnameToUsers map
			addObjectToMap(fnameToUsers, user.getFName().toLowerCase(), user);
		}
	}
	private void addToLnameMap(User user) {
		if(lnameToUsers.containsKey(user.getLName())) { // if this last name is already in the set, add this new user to that item in the set's value set of user
			lnameToUsers.get(user.getLName()).add(user);
		}
		else { // otherwise, we need to add this key value pair to the lnameToUsers map
			addObjectToMap(lnameToUsers, user.getLName().toLowerCase(), user);
		}
	}
	
	//used to parse following and feed
	private void parseUserSubList(String key, Node userNode, User user){
		NodeList following = userNode.getChildNodes();
		
		for (int j = 0; j<following.getLength(); j++) {
			
			Node node = following.item(j);
			//if the current child is of the appropriate Node type
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				//if we are parsing the feed, parse this event
		        if (key.equals(feed)) parseEvent(user, node);
		        //else add the username to the user's following set, then add the relationship to the usernameToFollowers map
		        else{
		        	String username = following.item(j).getFirstChild().getTextContent();
		        	user.addFollowing(username);
		        	addObjectToMap(usernameToFollowers, username, user.getUsername());
		        }
				
		    }
			
		}
	}
	
	//parse an event object
	private void parseEvent(User user, Node eventNode) {
		NodeList eventFields = eventNode.getChildNodes();
		Event event = new Event();
		event.setUsername(user.getUsername());
		
		for (int i = 0; i<eventFields.getLength(); i++){
			Node eventField = eventFields.item(i);
			String nodeName = eventField.getNodeName();
			
			switch(nodeName){
				//set the movie object of the event.
				//this is why in parse() we made sure to parse the movies node list before the users node list: so this movie value wouldn't be null
				case "movie":
					event.setMovie(moviesMap.get(eventField.getFirstChild().getTextContent()));
					break;
				case action: // 'Rated', 'Liked'
					String actionName = eventField.getFirstChild().getTextContent();
					if(!actionsList.contains(actionName)) {
						System.out.println("An event in a user's feed has a non-existing action listed: [" + actionName +"].");
						errorMessage = "An event in a user's feed has a non-existing action listed: [" + actionName +"].";
					}
					if(eventField.hasChildNodes()) {
						event.setAction(eventField.getFirstChild().getTextContent());
					}
					break;
				case rating: // '8'
					if (eventField.getFirstChild() != null) {
						try { 
							event.setRating(Double.parseDouble(eventField.getFirstChild().getTextContent()));
							System.out.println("Success! Parsed double from event rating: [" + eventField.getFirstChild().getTextContent() +"].");
						}
						catch (Exception e) {
							System.out.println("Could not successfully parse double from event rating: [" + eventField.getFirstChild().getTextContent() +"].");
							errorMessage = "Rating for event is a non-String field and has a String value: [" + eventField.getFirstChild().getTextContent() +"].";
						}
					}
					break;
			}
		}
		if(event.getAction() != null) {
			if(event.getRating() == -1 && event.getAction().equals("Rated")) {
				System.out.println("Event has 'Rated' action but no rating value.");
				errorMessage = "There is an event in a user's feed that has the action 'Rated' yet the rating field has been omitted";
			}
			else if(event.getRating() != -1 && !event.getAction().equals("Rated")) {
				System.out.println("Event has rating value action but no 'Rated' action.");
				errorMessage = "There is an event in a user's feed that does not have the action 'Rated' yet has a value in the rating field";
			}
		}
		else {
			if(event.getRating() != -1) {
				System.out.println("Event has rating value action but no 'Rated' action.");
				errorMessage = "There is an event in a user's feed that does not have the action 'Rated' yet has a value in the rating field";
			}
		}
		//add the event to the user
		user.addEvent(event);
	}
	
	//SHARED HELPER METHODS
	
	//more generics!! Yay!!!! They are so helpful in times like these!!!
	//adds a new object to the appropriate set
	private <T> void addObjectToMap(Map<String, Set<T>> map, String key, T object){
		//if the map already contains the provided key, retrieve the value (which is a set) and add the new object
		if (map.containsKey(key)) map.get(key).add(object);
		//else create a new set with the object, and add the provided key and created set to the map
		else{
			Set<T> objects = new HashSet<>();
			objects.add(object);
			map.put(key, objects);
		}
	}
}