<%@page import="data.DataStorage"%>
<%@page import="java.util.Set"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Profile</title>
		<link rel="stylesheet" href = "main.css">
		<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
	</head>
	<body id = "profile-page">
		<div id="header">
			<h1 class="title" style="text-align: center">CineKate</h1>
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
		<div id="followers-following">
			<div id="followers">
				<h2 class="follow-title">Followers</h2>
				<%
					Set<String> followers = ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getFollowers();
					for(String follower : followers) {				
				%>
						<h3 class="follow-item"><%=follower%></h3><br />
				<%
					}
				%>
			</div>
			<div id="following">
				<h2 class="follow-title">Following</h2>
				<%
					Set<String> followings = ((DataStorage)request.getSession().getAttribute("Data")).getLoggedInUser().getFollowing();
					for(String following : followings) {				
				%>
						<h3 class="follow-item"><%=following%></h3><br />
				<%
					}
				%>
			</div>
		</div>
	</body>
</html>