package client;

public class Department {

	private int id;
	private String name, schoolName;
	
	public Department(int id, String name, String schoolName) {
		this.id = id;
		this.name = name;
		this.schoolName = schoolName;
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}
