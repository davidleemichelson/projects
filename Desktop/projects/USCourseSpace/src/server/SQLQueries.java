package server;

public class SQLQueries {
	// get all related users
	public static final String GetAllFollowersByCourseID = 
			"SELECT * FROM Following WHERE courseid = ?;";
	
	// review queries 
	public static final String GetReviewByCourseAndUserID = 
			"SELECT * FROM Reviews r" +
			" WHERE r.user_id = ? AND r.course_id = ? ;";
	
	//User Queries
	public static final String UserExists = 
			"SELECT Username "
			+ "FROM Users "
			+ "WHERE Username = ?;";
	
	public static final String EmailTaken = 
			"SELECT Username "
			+ "FROM Users "
			+ "WHERE Email = ?;";
	
	public static final String ValidLoginCredentials = 
			"SELECT Username "
			+ "FROM Users "
			+ "WHERE Username = ? "
			+ "AND Password = ?;";
	
	public static final String GetUserInfo = 
			"SELECT * "
			+ "FROM Users "
			+ "WHERE Username = ?";
	
	public static final String GetUserByID = 
			"SELECT * "
			+ "FROM Users "
			+ "WHERE id = ?";
	
	public static final String AddNewUser = 
			"INSERT INTO Users (Username, Password, Email, Fullname, Major, Graduating_Year, Photo) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?); ";
	
	public static final String AddNewAdminUser = 
			"INSERT INTO Users (Username, Password, Email, Fullname, Major, Graduating_Year, Photo, isAdmin) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?); ";
	
	public static final String UpdateUser = 
			"UPDATE Users "
			+ "SET Fullname = ?, Major = ?, Graduating_Year = ?, Email = ?, Photo = ? "
			+ "WHERE Username = ?;";
	
	//Professor Queries
	public static final String GetProfessors = 
			"SELECT * FROM Professor "
			+ "ORDER BY Professor.Name;";
	
	public static final String GetProfessorByID = "SELECT * FROM Professor WHERE id = ?;";
	
	public static final String GetProfessorsByName = 
			"SELECT * FROM Professor "
			+ "WHERE Name Like ? "
			+ "ORDER BY Name;";
	
	public static final String AddNewProfessor = 
			"INSERT INTO Professor (Name, Email, Photo) "
			+ "VALUES (?, ?, ?);";
	
	public static final String DeleteProfessor = 
			"DELETE FROM Professor "
			+ "WHERE id = ?;";
	
	public static final String GetProfessorsThatTeachCourse = 
			"SELECT p.id, p.Name, p.Email, p.Photo "
			+ "FROM Professor p, Course c "
			+ "WHERE c.Professor_id = ? "
			+ "AND p.id = c.Professor_id "
			+ "ORDER BY p.Name;";
	
	public static final String GetTopThreeTagsForProfessor = 
			"SELECT t.Tag, COUNT(Tag) count "
			+ "FROM Tags t, Review_Tags rt "
			+ "WHERE rt.Tag_id = t.id "
			+ "AND rt.Course_id = (SELECT id FROM Course WHERE Professor_id = ? ) "
			+ "GROUP BY t.Tag "
			+ "ORDER BY count "
			+ "LIMIT 3;";
	
	//Departments
	public static final String GetDepartments =
			"SELECT * FROM Department "
			+ "ORDER BY Name;";
	
	public static final String AddNewDepartment = 
			"INSERT INTO Department (Name, School_Name) "
			+ "VALUES (?, ?);";
	
	public static final String DeleteDepartmentByID = 
			"DELETE FROM Department WHERE id = ?";
	

	public static final String GetDepartmentById = 
			"SELECT * FROM Department WHERE id = ?"; 
	
	//Courses
	public static final String GetCourses = 
			"SELECT * FROM Course "
			+ "ORDER BY Name";
	
	public static final String AddNewCourse = 
			"INSERT INTO Course (Dept_id, Code, Name, Description, Professor_id) "
			+ "VALUES (?, ?, ?, ?, ?);";
	
	public static final String GetCoursesByDeptID = 
			"SELECT c.id, c.Dept_id, c.Code, c.Name, c.Description "
			+ "FROM Course c, Department d "
			+ "WHERE c.Dept_id = ? "
			+ "AND c.Dept_id = d.id;";
	
	public static final String GetCoursesByDeptName = 
			"SELECT * "
			+ "FROM Course c, Department d "
			+ "WHERE d.Name = ? "
			+ "AND c.Dept_id = d.id;";
	
	public static final String GetCoursesTaughtByProfessor = 
			"SELECT * "
			+ "FROM Course "
			+ "WHERE Professor_id = ? "
			+ "ORDER BY Dept_id, Code;";

	public static String updateCourse = "UPDATE Course "
					+ " SET Dept_id = ?, Code = ?, Name = ?,"
					+ " Description = ?, Professor_id = ? "
					+ "WHERE id = ?;";
	
	public static final String GetDepartmentByPrefix = 
			"SELECT * FROM Department WHERE Name = ?;";

	public static final String GetCourseByName = 
			"SELECT * FROM Course WHERE Name LIKE ?";

	public static final String GetCourseById = 
			"SELECT * FROM Course WHERE id = ?";
	
	public static final String AddCourseTagFromReview = 
			"INSERT INTO Review_Tags VALUES (?, ?, ?);";
	
	public static final String GetTopThreeTagsForCourse = 
			"SELECT t.Tag, COUNT(Tag) count "
			+ "FROM Tags t, Review_Tags rt "
			+ "WHERE rt.Tag_id = t.id "
			+ "AND rt.Course_id = ? "
			+ "GROUP BY t.Tag "
			+ "ORDER BY count "
			+ "LIMIT 3;";
	
	public static final String GetCoursesByTagName = 
			"SELECT c.id, c.Dept_id, c.Code, c.Name, c.Description, c.Professor_id "
			+ "FROM Course c, Tags t, Review_Tags rt "
			+ "WHERE t.Tag LIKE ? "
			+ "AND rt.Tag_id = t.id "
			+ "AND c.id = rt.Course_id "
			+ "GROUP BY rt.Review_id "
			+ "ORDER BY c.Code, c.Name;";
			
	//Reviews
	public static final String AddNewReview = 
			"INSERT INTO Reviews "
			+ "(User_id, Course_id, Professor_id, Course_Review, Date_Attended, "
			+ "Has_Midterm, Has_Final, Has_Essay, Has_Assignments, Has_Projects, Has_Quiz, "
			+ "Grading_Rating, Workload_Rating, Content_Rating, Teaching_Rating)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	 
	public static final String GetReviewsByCourseID = 
			"SELECT * FROM Reviews "
			+ "WHERE Course_id = ? "
			+ "ORDER BY Created_Date;";
	
	public static final String GetReviewsByProfessorID = 
			"SELECT * FROM Reviews "
			+ "WHERE Professor_id = ?"
			+ "ORDER BY Created_Date;";
	
	public static final String DeleteReviewByID = 
			"DELETE FROM Reviews WHERE id = ?;";
	
	public static final String GetTagsForReview =
			"SELECT t.Tag "
			+ "FROM Tags t, Review_Tags rt "
			+ "WHERE rt.Review_id = ? "
			+ "AND rt.Tag_id = t.id;";
	
	//Tags
	public static final String AddNewTag = 
			"INSERT INTO Tags (Tag) "
			+ "VALUES (?);";
	public static final String GetTagByName = 
			"SELECT * FROM Tags WHERE Tag = ?;";
	
	public static final String DeleteTagById = 
			"DELETE FROM Tags WHERE id = ?";
	
	public static final String GetTags = 
			"SELECT * FROM Tags;";

	// Followings
	public static final String AddFollowingUserToCourse =
			"INSERT INTO Following (userId, courseId) VALUES (?,?)";

	public static final String DeleteFollowingUserToCourse =
			"DELETE FROM Following WHERE userId=? AND courseId=?";

	public static final String SelectUserFollowingCourse =
			"SELECT * FROM Following WHERE userId=? AND courseId=?";

	public static final String GetCourseIdsFollowingByUserId =
			"SELECT * FROM Following WHERE userId=?";

	public static final String UpdateUneradByUserID = 
			"UPDATE Users "
			+ "SET hasUnread = ? "
			+ "WHERE id = ?;";


	public static final String GetReviewsByFollowerID = 
			"SELECT r.* FROM Reviews r, Users u, "
			+ "Following f WHERE u.id = f.userid "
			+ " AND f.courseid = r.course_id AND "
			+ " f.userid = ? ORDER BY r.Created_Date DESC";
}
