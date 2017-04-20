package message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.User;

public class NotificationThread extends Thread {
	private NotificationServer ns = null;
	private Socket s = null; 

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public NotificationThread(Socket s){
		this.s = s;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream()); 
		} catch (IOException ioe) {
			System.out.println("ioe in ChatThread: " + ioe.getMessage());
		}
	}
	public void run(){
		try {
			System.out.println("Running!");
			while (true){
				User user = (User)ois.readObject();
				user.printUser();
				NotificationServer.sendMessageToFollower(user);
				
				break;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			
		}
	}
}
