package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Driver;

import client.Course;
import client.Department;
import client.Professor;
import client.Review;
import client.Tag;
import client.User;

public class MySQLDriver {
	
	private Connection connection;
	
	public MySQLDriver() {
		try {
			new Driver();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://uscitp.com/paledoux_rot_db?user=paledoux&password=pal152325&useSSL=false");
			System.out.println("Connected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Find all users who follow this course
	 */
	public List<Integer> getAllFollowersByCourseID(int courseid){
		List<Integer> results = new ArrayList<Integer>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetAllFollowersByCourseID);
			pStatement.setInt(1, courseid);
			
			ResultSet rSet = pStatement.executeQuery(); 
			if(rSet.next()){
				results.add(rSet.getInt(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results; 
	}
	
	/*
	 * Set unread users
	 */
	public void setUnreadByUserID(int id){
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.UpdateUneradByUserID);
			pStatement.setInt(1, 1);
			pStatement.setInt(2, id);
			
			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * clear unread users
	 */
	public void clearUnreadByUserID(int id){
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.UpdateUneradByUserID);
			pStatement.setInt(1, 0);
			pStatement.setInt(2, id);
			
			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Check if a user exists in the database
	 */
	public boolean userExists(String username) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.UserExists);
			pStatement.setString(1, username.toLowerCase());
			ResultSet rSet = pStatement.executeQuery();
			
			if (rSet.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Check if an email address has already been registered
	 */
	public boolean emailTaken(String email) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.EmailTaken);
			pStatement.setString(1, email.toLowerCase());
			ResultSet rSet = pStatement.executeQuery();
			
			if (rSet.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Check the user entered a valid username and password combo
	 */
	public boolean validLoginCredentials(String username, String password) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.ValidLoginCredentials);
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			ResultSet rSet = pStatement.executeQuery();
			
			if (rSet.next()) {
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Get all info about a user by user id
	 * Should first check that the username exists before running this function
	 */
	public User getUserByID(int id) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetUserByID);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				return new User(
						rSet.getInt(1), 
						rSet.getString(2), 
						rSet.getString(3),
						rSet.getString(4),  
						rSet.getString(5), 
						rSet.getString(6), 
						rSet.getString(7), 
						rSet.getString(8), 
						rSet.getBoolean(9),
						rSet.getInt(10));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * Get all info about a user
	 * Should first check that the username exists before running this function
	 */
	public User getUser(String username) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetUserInfo);
			pStatement.setString(1, username);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				return new User(
						rSet.getInt(1), 
						rSet.getString(2), 
						rSet.getString(3),
						rSet.getString(4),  
						rSet.getString(5), 
						rSet.getString(6), 
						rSet.getString(7), 
						rSet.getString(8), 
						rSet.getBoolean(9), 
						rSet.getInt(10));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * Add a new user
	 * Should first check that the username and email do not exist before running this function
	 */
	public void addUser(String username, String password, String email, String firstname, String major, String year, String photoURL) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddNewUser);
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			pStatement.setString(3, email);
			pStatement.setString(4, firstname);
			pStatement.setString(5, major);
			pStatement.setString(6, year);
			pStatement.setString(7, photoURL);
			
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Add a new admin possible user
	 * Should first check that the username and email do not exist before running this function
	 */
	public void addAdminUser(String username, String password, String email, String firstname, String major, String year, String photoURL, boolean isAdmin) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddNewAdminUser);
			pStatement.setString(1, username);
			pStatement.setString(2, password);
			pStatement.setString(3, email);
			pStatement.setString(4, firstname);
			pStatement.setString(5, major);
			pStatement.setString(6, year);
			pStatement.setString(7, photoURL);
			pStatement.setBoolean(8, isAdmin);
			
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Add a new user
	 * Should first check that the username and email do not exist before running this function
	 */
	public void updateUser(String username, String password, String email, String firstname, String major, String year, String photoURL) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.UpdateUser);
			pStatement.setString(1, firstname);
			pStatement.setString(2, major);
			pStatement.setString(3, year);
			pStatement.setString(4, email);
			pStatement.setString(5, photoURL);
			pStatement.setString(6, username);
			
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Add a new professor
	 */
	public void addProfessor(String name, String email, String avatarURL) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddNewProfessor);
			pStatement.setString(1, name);
			pStatement.setString(2, email);
			pStatement.setString(3, avatarURL);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get list of all professors
	 */
	public List<Professor> getProfessors() {
		List<Professor> professors = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetProfessors);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				professors.add(new Professor(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professors;
	}
	
	/*
	 * Get list of professors by name
	 */
	public List<Professor> getProfessorsByName(String name) {
		List<Professor> professors = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetProfessorsByName);
			pStatement.setString(1, "%" + name + "%");
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				professors.add(new Professor(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professors;
	}
	
	/*
	 * Get list of professors by a course they teach
	 */
	public List<Professor> getProfessorsByCourseID(int id) {
		List<Professor> professors = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetProfessorsThatTeachCourse);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				professors.add(new Professor(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professors;
	}
	
	/*
	 * Get a specific professor by id
	 */
	public Professor getProfessorByID(int id) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetProfessorByID);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
			
			if (rSet.next()) {
				return new Professor(rSet.getInt(1), rSet.getString(2), rSet.getString(3), rSet.getString(4));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * Delete a professor by id
	 */
	public void deleteProfessorByID(int id) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.DeleteProfessor);
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get top three tags for a professor
	 */
	public List<String> getTopThreeTagsForProfessor(int professor_id) {
		List<String> tags = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetTopThreeTagsForProfessor);
			pStatement.setInt(1, professor_id);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				tags.add(rSet.getString(1));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}
	
	/*
	 * Get all departments
	 * Note department has Name (CSCI) and School_Name (Viterbi School of Engineering)
	 * School_Name can be null
	 */
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetDepartments);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				departments.add(new Department(rSet.getInt(1), rSet.getString(2), rSet.getString(3)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return departments;
	}
	
	/*
	 * Get all courses
	 * NOTE: Courses can have a description but it may be null as well
	 */
	public List<Course> getCourses() {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCourses);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				courses.add(new Course(rSet.getInt(1), rSet.getInt(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	

	public Course getCourseByID(int id) {
		Course course = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCourseById);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
			while(rSet.next())
			course = new Course(rSet.getInt(1), rSet.getInt(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return course;
	}
	
	/*
	 * Create a new course without a description
	 */
	public void addNewCourse(int dept_id, String code, String name, int prof_id) {
		addNewCourse(dept_id, code, name, null, prof_id);
	}
	
	/*
	 * Create a new course with a description
	 */
	public void addNewCourse(int dept_id, String code, String name, String description, int prof_id) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddNewCourse);
			pStatement.setInt(1, dept_id);
			pStatement.setString(2, code);
			pStatement.setString(3, name);
			pStatement.setString(4, description);
			pStatement.setInt(5, prof_id);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get all courses in a specific department
	 * NOTE: Courses can have a description but it may be null as well
	 */
	public List<Course> getCoursesByDeptID(int id) {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCoursesByDeptID);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				courses.add(new Course(rSet.getInt(1), rSet.getInt(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	
	/*
	 * Get all courses by name
	 * NOTE: Courses can have a description but it may be null as well
	 */
	public List<Course> getCoursesByName(String name) {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCourseByName);
			pStatement.setString(1, "%" + name + "%");
			// '%intro%'
			ResultSet rSet = pStatement.executeQuery(); 
			while (rSet.next()) {
				courses.add(new Course(rSet.getInt(1), rSet.getInt(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	
	/*
	 * Get all courses in a specific department by entering the three or four letter code for the department (CSCI)
	 * NOTE: Courses can have a description but it may be null as well
	 */
	public List<Course> getCoursesByDeptName(String name) {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCoursesByDeptName);
			pStatement.setString(1, name);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				courses.add(new Course(rSet.getInt(1), rSet.getInt(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	
	/*
	 * Get courses by tag name
	 * NOTE: Courses can have a description but it may be null as well
	 */
	public List<Course> getCoursesByTagName(String name) {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCoursesByTagName);
			pStatement.setString(1, "%" + name + "%");
			ResultSet rSet = pStatement.executeQuery(); 
			while (rSet.next()) {
				courses.add(new Course(rSet.getInt(1), rSet.getInt(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}

	public void updateCourse(int courseid, int coursePrefixID, String courseNumber, String courseName,
			String courseDescription, int courseProfessorID) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.updateCourse);
			
			pStatement.setInt(1, coursePrefixID);
			pStatement.setString(2, courseNumber);
			pStatement.setString(3, courseName);
			pStatement.setString(4, courseDescription);
			pStatement.setInt(5, courseProfessorID);
			pStatement.setInt(6, courseid);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get all courses taught by a professor
	 */
	public List<Course> getCoursesByProfessorID(int id) {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCoursesTaughtByProfessor);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				courses.add(new Course(rSet.getInt(1), rSet.getInt(2), rSet.getString(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return courses;
	}
	
	/*
	 * Add a tag for a course
	 */
	public void addCourseTag(int Review_id, int Course_id, int Tag_id) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddCourseTagFromReview);
			pStatement.setInt(1, Review_id);
			pStatement.setInt(2, Course_id);
			pStatement.setInt(3, Tag_id);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get top three tags for a course
	 */
	public List<String> getTopThreeTagsForCourse(int Course_id) {
		List<String> tags = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetTopThreeTagsForCourse);
			pStatement.setInt(1, Course_id);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				tags.add(rSet.getString(1));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}

	/*
	 * Add departments
	 */
	public void addDepartment(String prefix, String school) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddNewDepartment);
			pStatement.setString(1, prefix);
			pStatement.setString(2, school);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get all departments with the same name
	 */
	public List<Department> getDepartmentsByPrefix(String prefix) {
		List<Department> departments = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetDepartmentByPrefix);
			pStatement.setString(1, prefix);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				departments.add(new Department(rSet.getInt(1), rSet.getString(2), rSet.getString(3)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return departments;
	}
	
	public Department getDepartmentById(int id) {
		Department department = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetDepartmentById);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
			rSet.next();
				department = new Department(rSet.getInt(1), rSet.getString(2), rSet.getString(3));
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return department;
	}
	
	
	/* 
	 * Delete a department object by id
	 */
	public void deleteDepartmentByID(int id){
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.DeleteDepartmentByID);
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Add a tag object to database
	 */
	public void addTag(String tag) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddNewTag);
			pStatement.setString(1, tag);
			pStatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get all tags with the same name
	 */
	public List<Tag> getTagsByName(String name) {
		List<Tag> tags = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetTagByName);
			pStatement.setString(1, name);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				tags.add(new Tag(rSet.getInt(1), rSet.getString(2)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}
	
	/*
	 * Get all available tags
	 */
	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetTags);
			ResultSet rSet = pStatement.executeQuery();
		
			
			
			while (rSet.next()) {
				tags.add(new Tag(rSet.getInt(1), rSet.getString(2)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}
	
	/*
	 * Delete a tag by id
	 */
	public void deleteTagByID(int id){
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.DeleteTagById);
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Create a review
	 */
	public void createReview(int User_id, int Course_id, int Professor_id, String Course_Review, String Date_Attended,
			boolean Has_Midterm, boolean Has_Final, boolean Has_Essay, boolean Has_Assignment, boolean Has_Projects, boolean Has_Quiz,
			double Grading_Rating, double Workload_Rating, double Content_Rating, double Teaching_Rating, List<Integer> tags) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddNewReview);
			pStatement.setInt(1, User_id);
			pStatement.setInt(2, Course_id);
			pStatement.setInt(3, Professor_id);
			pStatement.setString(4, Course_Review);
			pStatement.setString(5, Date_Attended);
			pStatement.setBoolean(6, Has_Midterm);
			pStatement.setBoolean(7, Has_Final);
			pStatement.setBoolean(8, Has_Essay);
			pStatement.setBoolean(9, Has_Assignment);
			pStatement.setBoolean(10, Has_Projects);
			pStatement.setBoolean(11, Has_Quiz);
			pStatement.setDouble(12, Grading_Rating);
			pStatement.setDouble(13, Workload_Rating);
			pStatement.setDouble(14, Content_Rating);
			pStatement.setDouble(15, Teaching_Rating);
			
			pStatement.executeUpdate();
			
			Review review = this.getReviewByCourseAndUserId(User_id, Course_id);
			
			for (int i=0; i<tags.size(); i++){
				this.addCourseTag(review.getId(), Course_id, tags.get(i));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get all of the reviews for a Course
	 */
	public List<Review> getReviewsByCourseId(int id) {
		List<Review> reviews = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetReviewsByCourseID);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
		
			while (rSet.next()) {
				reviews.add(new Review(
									rSet.getInt(1), 
									rSet.getInt(2),
									rSet.getInt(3),
									rSet.getInt(4),
									rSet.getString(5),
									rSet.getString(6),
									rSet.getBoolean(7),
									rSet.getBoolean(8),
									rSet.getBoolean(9),
									rSet.getBoolean(10),
									rSet.getBoolean(11),
									rSet.getBoolean(12),
									rSet.getDouble(13),
									rSet.getDouble(14),
									rSet.getDouble(15),
									rSet.getDouble(16)
									)
						);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	public List<Review> getReviewsByFollowerID(int id) {
		List<Review> reviews = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetReviewsByFollowerID);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
		
			while (rSet.next()) {
				Review r =new Review(
							rSet.getInt(1), 
							rSet.getInt(2),
							rSet.getInt(3),
							rSet.getInt(4),
							rSet.getString(5),
							rSet.getString(6),
							rSet.getBoolean(7),
							rSet.getBoolean(8),
							rSet.getBoolean(9),
							rSet.getBoolean(10),
							rSet.getBoolean(11),
							rSet.getBoolean(12),
							rSet.getDouble(13),
							rSet.getDouble(14),
							rSet.getDouble(15),
							rSet.getDouble(16)
						);
				r.setDate_Created(rSet.getDate(17));
				Course course = this.getCourseByID(r.getCourse_id());
				r.setCourseName(course.getName());
				
				reviews.add(r);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}
	
	public Review getReviewByCourseAndUserId(int uid, int cid) {
		Review review = null;
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetReviewByCourseAndUserID);
			pStatement.setInt(1, uid);
			pStatement.setInt(2, cid);
			ResultSet rSet = pStatement.executeQuery();
		
			while (rSet.next()) {
				review = new Review(rSet.getInt(1), 
									rSet.getInt(2),
									rSet.getInt(3),
									rSet.getInt(4),
									rSet.getString(5),
									rSet.getString(6),
									rSet.getBoolean(7),
									rSet.getBoolean(8),
									rSet.getBoolean(9),
									rSet.getBoolean(10),
									rSet.getBoolean(11),
									rSet.getBoolean(12),
									rSet.getDouble(13),
									rSet.getDouble(14),
									rSet.getDouble(15),
									rSet.getDouble(16));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return review;
	}
	
	/*
	 * Get all of the reviews for a Course
	 */
	public List<Review> getReviewsByProfessorId(int id) {
		List<Review> reviews = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetReviewsByProfessorID);
			pStatement.setInt(1, id);
			ResultSet rSet = pStatement.executeQuery();
		
			while (rSet.next()) {
				reviews.add(new Review(
									rSet.getInt(1), 
									rSet.getInt(2),
									rSet.getInt(3),
									rSet.getInt(4),
									rSet.getString(5),
									rSet.getString(6),
									rSet.getBoolean(7),
									rSet.getBoolean(8),
									rSet.getBoolean(9),
									rSet.getBoolean(10),
									rSet.getBoolean(11),
									rSet.getBoolean(12),
									rSet.getDouble(13),
									rSet.getDouble(14),
									rSet.getDouble(15),
									rSet.getDouble(16)
									)
						);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reviews;
	}
	
	/*
	 * Delete a review by id
	 */
	public void deleteReviewById(int id){
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.DeleteReviewByID);
			pStatement.setInt(1, id);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Get all tags for a review
	 */
	public List<String> getReviewTags(int Review_id) {
		List<String> tags = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetTagsForReview);
			pStatement.setInt(1, Review_id);
			ResultSet rSet = pStatement.executeQuery();
			
			while (rSet.next()) {
				tags.add(rSet.getString(1));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}

	/*
	 *  Add a new following relationship
	 */
	public void addFollowingUserToCourse(int userid, int courseid) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.AddFollowingUserToCourse);
			pStatement.setInt(1, userid);
			pStatement.setInt(2, courseid);
			pStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean userFollowsCourse(int userid, int courseid) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.SelectUserFollowingCourse);
			pStatement.setInt(1, userid);
			pStatement.setInt(2, courseid);
			ResultSet rSet = pStatement.executeQuery();

			while(rSet.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void deleteFollowingUserToCourse(int userid, int courseid) {
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.DeleteFollowingUserToCourse);
			pStatement.setInt(1, userid);
			pStatement.setInt(2, courseid);
			pStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Course> getCoursesFollowingByUserId(int userid) {
		List<Course> courses = new ArrayList<>();
		try {
			PreparedStatement pStatement = connection.prepareStatement(SQLQueries.GetCourseIdsFollowingByUserId);
			pStatement.setInt(1, userid);
			ResultSet rSet = pStatement.executeQuery();
			
			while(rSet.next()) {
				courses.add(this.getCourseByID(rSet.getInt(3)));
				System.out.println("adding course: " + rSet.getInt(3));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	public void getRatings(Course course) {
		List<Review> reviews = this.getReviewsByCourseId(course.getId());
		double grading, workload, content, teaching; 
		grading = workload = content = teaching = 0.0; 
		int count = 0; 
		for (Review r:reviews){
			count ++; 
			grading += r.getGradingRating(); 
			workload+= r.getWorkloadRating();
			content += r.getContentRating(); 
			teaching += r.getTeachingRating();
		}
		if (count == 0) {
			return; 
		}
		course.setGrading(grading/count);
		course.setWorkload(workload/count);
		course.setContent(content/count);
		course.setTeaching(teaching/count);
	}
}
