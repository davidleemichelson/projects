package client;

public class Tag {

	private int id;
	private String name;
	
	public Tag(int id, String tag) {
		this.id = id;
		this.name = tag;
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
}
