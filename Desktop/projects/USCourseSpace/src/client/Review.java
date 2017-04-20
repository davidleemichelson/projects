package client;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Review {
	private int id;
	private int User_id;
	private int Professor_id;
	private int Course_id;
	private String Course_Review;
	private String Date_Attended;
	private boolean hasMidterm;
	private boolean hasFinal;
	private boolean hasEssay;
	private boolean hasAssignments;
	private boolean hasProjects;
	private boolean hasQuiz;
	private double gradingRating;
	private double workloadRating;
	private double contentRating;
	private double teachingRating;
	private List<String> tags = new ArrayList<String>();
	
	private Date date = null;
	private String coursename = "";
	
	public Review(int id, int user_id, int professor_id, int course_id, String course_Review, String date_Attended,
			boolean hasMidterm, boolean hasFinal, boolean hasEssay, boolean hasAssignments, boolean hasProjects,
			boolean hasQuiz, double gradingRating, double workloadRating, double contentRating, double teachingRating) {
		this.id = id;
		User_id = user_id;
		Professor_id = professor_id;
		Course_id = course_id;
		Course_Review = course_Review;
		Date_Attended = date_Attended;
		this.hasMidterm = hasMidterm;
		this.hasFinal = hasFinal;
		this.hasEssay = hasEssay;
		this.hasAssignments = hasAssignments;
		this.hasProjects = hasProjects;
		this.hasQuiz = hasQuiz;
		this.gradingRating = gradingRating;
		this.workloadRating = workloadRating;
		this.contentRating = contentRating;
		this.teachingRating = teachingRating;
	}
	
	public int getId() {
		return id;
	}

	public int getUser_id() {
		return User_id;
	}

	public void setUser_id(int user_id) {
		User_id = user_id;
	}

	public int getProfessor_id() {
		return Professor_id;
	}

	public void setProfessor_id(int professor_id) {
		Professor_id = professor_id;
	}

	public int getCourse_id() {
		return Course_id;
	}

	public void setCourse_id(int course_id) {
		Course_id = course_id;
	}

	public String getCourse_Review() {
		return Course_Review;
	}

	public void setCourse_Review(String course_Review) {
		Course_Review = course_Review;
	}

	public String getDate_Attended() {
		return Date_Attended;
	}

	public void setDate_Attended(String date_Attended) {
		Date_Attended = date_Attended;
	}

	public boolean isHasMidterm() {
		return hasMidterm;
	}

	public void setHasMidterm(boolean hasMidterm) {
		this.hasMidterm = hasMidterm;
	}

	public boolean isHasFinal() {
		return hasFinal;
	}

	public void setHasFinal(boolean hasFinal) {
		this.hasFinal = hasFinal;
	}

	public boolean isHasEssay() {
		return hasEssay;
	}

	public void setHasEssay(boolean hasEssay) {
		this.hasEssay = hasEssay;
	}

	public boolean isHasAssignments() {
		return hasAssignments;
	}

	public void setHasAssignments(boolean hasAssignments) {
		this.hasAssignments = hasAssignments;
	}

	public boolean isHasProjects() {
		return hasProjects;
	}

	public void setHasProjects(boolean hasProjects) {
		this.hasProjects = hasProjects;
	}

	public boolean isHasQuiz() {
		return hasQuiz;
	}

	public void setHasQuiz(boolean hasQuiz) {
		this.hasQuiz = hasQuiz;
	}

	public double getGradingRating() {
		return gradingRating;
	}

	public void setGradingRating(double gradingRating) {
		this.gradingRating = gradingRating;
	}

	public double getWorkloadRating() {
		return workloadRating;
	}

	public void setWorkloadRating(double workloadRating) {
		this.workloadRating = workloadRating;
	}

	public double getContentRating() {
		return contentRating;
	}

	public void setContentRating(double contentRating) {
		this.contentRating = contentRating;
	}

	public double getTeachingRating() {
		return teachingRating;
	}

	public void setTeachingRating(double teachingRating) {
		this.teachingRating = teachingRating;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
		
	}
	public List<String> getTags(){
		return tags;
	}

	public void setDate_Created(Date date) {
		this.date = date; 
	}
	public Date getDate_Created(){
		return this.date;
	}
	
	public void setCourseName(String name){
		this.coursename = name; 
	}
	public String getCourseName () {
		return this.coursename;
	}
	public String getCourseNameLimited (){
		if (this.coursename.length()>25){ 
			return (this.coursename.substring(0, 26) + "...");
		}
		return getCourseName();
	}
	
}
