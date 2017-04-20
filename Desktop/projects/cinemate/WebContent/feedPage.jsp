<%@page import="data.DataStorage"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="data.Event"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Feed</title>
		<link rel="stylesheet" href = "main.css">
		<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
	</head>
	<body id = "profile-page">
		<div id="header">
			<h1 class="title" style="text-align: center">Cinewait</h1>
		</div>
		<div id="profile-header">
			<img class="prof-pic" src="
			<%
				String profileImage = "";
				System.out.println("Profile image 1: " + profileImage);
				if(((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getImage() != null) {
					profileImage = ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getImage();
					System.out.println("PROFILE IMAGE: " + profileImage);
				}
				else {
					System.out.println("session is null!");
				}
			%>
			<%=profileImage%>"></img>
			<h2 id="prof-full-name"">
			<%
				String fullName = "";
				fullName = ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getFName() + " " + ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getLName();

			%>
			<%=fullName%></h2>
			<h3 id="prof-username">
			<%
				String atName = "";
				atName = "@" + ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getUsername();
			%>
			<%=atName%></h3>
		</div>
		<div id="feed">
			<h2 class="follow-title" style="text-align: center">Feed</h2>
			<%
				/* 	print("Feed:", loggedInUser.toStringFeed());
					//get the list of usernames, and for each, get the user object from the usersMap and print their feed
					for (String following : loggedInUser.getFollowing()) System.out.print(usersMap.get(following).toStringFeed());
					System.out.println();
					//print the 'menu' which only allows the '0' input
					printSmallMenu(); */

				List<Event> feed = ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getFeed();
				Set<String> followings = ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getFollowing();
				List<String> otherfeed = new ArrayList<String>();
				if(followings != null) {
					for(String following : followings) {
						List<Event> tempFeed = ((DataStorage)request.getSession().getAttribute("Data")).usersMap.get(following).getFeed();
						for(Event event : tempFeed) {
							if(event.getRating() != -1) {
								otherfeed.add(event.getUsername() + " " + event.getAction() + " " + event.getMovieTitle() + " " + event.getRating());
							}
							else {
								otherfeed.add(event.getUsername() + " " + event.getAction() + " " + event.getMovieTitle());
							}
						}
					}
				}
				System.out.println(feed.size());
				// Note: also have to show people we follow's stuff!!!
				for(Event feeditem : feed) {		
					String action = feeditem.getAction().toLowerCase();
					String movie = feeditem.getMovieTitle();
					String event = "";
					if(feeditem.getRating() != -1) {
						event = action + " " + movie + " " + feeditem.getRating();
					}
					else {
						event = action + " " + movie;
					}
					System.out.println("EVENT: " + event);
			%>
					<h3 class="follow-item" style="text-align: center"><%=event%></h3><br />
			<%
				}
				for(String otherfeeditem : otherfeed) {
			%>
					<h3 class="follow-item" style="text-align: center"><%=otherfeeditem%></h3><br />
			<%
				}
			%>
		</div>
	</body>
</html>