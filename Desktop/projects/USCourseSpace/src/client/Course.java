package client;

import java.util.List;

public class Course {
	int id, dept_id, professor;
	String code, name, description, deptAndNumber;
	List<String> tags; 
	double grading, workload, content, teaching; 
	
	public Course(int id, int dept_id, String code, String name, String description, int professorid) {
		this.id = id; 
		this.dept_id = dept_id; 
		this.code = code; 
		this.name = name; 
		this.description = description; 
		this.professor = professorid; 
		grading = workload = content = teaching = 0.0; 
	}
	
	public void setDeptAndNumber(String deptAndNumber) {
		this.deptAndNumber = deptAndNumber;
	}
	
	public String getDeptAndNumber() {
		return deptAndNumber;
	}
	
	public int getId() {
		return id;
	}
	public int getProfessorId(){
		return professor;
	}
	
	public int getDeptId() {
		return dept_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setTags(List<String> tags){
		this.tags = tags;
	}
	public List<String> getTags(){
		return this.tags;
	}
	
	public void setGrading(double r){
		grading = r; 
	}
	public void setWorkload(double r){
		workload = r; 
	}
	public void setContent(double r){
		content = r; 
	}
	public void setTeaching(double r){
		teaching = r; 
	}
	public double getGrading (){
		return grading; 
	}
	public double getWorkload (){
		return workload; 
	}
	public double getContent (){
		return content; 
	}
	public double getTeaching (){
		return teaching; 
	}
}
