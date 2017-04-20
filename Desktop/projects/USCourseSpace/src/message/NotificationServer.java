package message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.*;
import java.util.List;
import java.util.Scanner;

import client.User;
import server.MySQLDriver;

@ServerEndpoint(value="/notifications")
public class NotificationServer {
	public static final int port = 6789;
	
	public static MySQLDriver msql;
	public static HashMap<String, Session> sessionMap = new HashMap<String, Session>();
	public static HashMap<String, String> reverseSessionMap = new HashMap<String, String>();

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private static ServerSocket ss = null; 
	
	private static boolean isRunning = false; 
	
	public static void listener(){

		try {
			isRunning = true; 
			msql = new MySQLDriver();
			msql.connect();
			if (ss == null || !ss.isClosed()){
					System.out.println("Starting server now!");
					ss = new ServerSocket(NotificationServer.port);
				
			}
			while(true) {
					System.out.println("Connected!");
					Socket s = ss.accept();
					NotificationThread nt = new NotificationThread(s);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public NotificationServer(){
		if(!isRunning){
			System.out.println("Calling this method");
			listener();
		}
	}
	
	@OnOpen
	public void open(Session session){
		
	}

	@OnMessage
	public void onMessage(String message, Session session){
		//System.out.println("message: " + message);
		sessionMap.put(message, session);
		reverseSessionMap.put(session.getId(), message);
	}

	@OnClose
	public void close(Session session){
		//System.out.println("closing");
		String temp = reverseSessionMap.get(session.getId());
		reverseSessionMap.remove(session.getId());
		sessionMap.remove(temp);
	}
	
	@OnError
	public void onError(Throwable error){
		System.out.println("error");
	}
	
	public static void sendMessageToFollower (User user){
		try {
			Session s = sessionMap.get(user.getUsername());
			s.getBasicRemote().sendText("newpost");
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	
}