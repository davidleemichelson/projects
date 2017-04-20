package client;

import java.io.Serializable;

public class User implements Serializable {
	public static final long serialVersionUID = 1;
	
	private int id;
	private String username, password;
	private String fullName, email;
	private String photoURL;
	private String year;
	private boolean isAdmin;
	private String major;
	private int hasUnread = 0; 
	
	public User(int id, String username, String password, String email, String fullname, String major, String year, String avatarURL, boolean isAdmin, int hu) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullname;
		this.photoURL = avatarURL;
		this.year = year;
		this.isAdmin = isAdmin;
		this.major = major;
		hasUnread = hu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public String getCurrentYear() {
		return year;
	}

	public void setCurrentYear(String year) {
		this.year = year;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	public void setHasUnread(int i){
		this.hasUnread = i; 
	}
	public int getHasUnread(){
		return this.hasUnread;
	}
	
	public void printUser (){
		System.out.println(username + " " + id);
	}
}
