package client;

import java.util.List;

public class Professor {
	private int id;
	private String name, email, avatarURL;
	private List<String> tags;
	
	public Professor(int id, String name, String email, String avatarURL) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.avatarURL = avatarURL;
	}

	public String getName() {
		return name;
	}
	
	public int getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}
	
	public void setTags(List<String> tags){
		this.tags = tags;
	}
	public List<String> getTags(){
		return this.tags;
	}
}
