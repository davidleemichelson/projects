<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="client.Course" %>
<%@ page import="client.Review" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<% List<Review> reviews = (List<Review>)request.getAttribute("reviews"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			USCourseSpace - Manager Admin
		</title>
		
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		
		<style>
			#main_container {
				width: 100%;
				height: 95vh;
			}
			#content_container {
				position: absolute; 
				width: 80%;
				left: 10%;
				white-space:  nowrap;
				
				height: 80%;
				top: 10%; 
			}
			
			.half {
				position: absolute;
				height: 80%; 
				width: 40%; 
				margin-left: 5%;
				white-space: normal;
			}
			
			.half:last-child {
				left: 50%;
			}
			
			
			.half h1 {
				margin: 0;
			}
			#form {
				width: 80%; 
				height: 100%; 
				margin-left: 10%;
			}
			
			#title {
	
				width: 100%;
				font-size: 20px;
			}
			#title:after {
				position: relative;
				top: 5px;
				left: 10%;
				content: "";
				display: block;
				height: 1px;
				width: 80%;
				background-color: #ccc;
			}
			
			#content {
				width: 80%;
				margin-left: 10%;
			}
			
			input {
				margin-top: 5px;
			}
        	
        	#button_container {
        		margin-left: -15px;
        	}
        	
        	.form-control {
        		width: 100%;
        		margin-top: 15px;
        	}
        	
        	#heading{
        		text-align:center;
        		margin-top:1px;
        	}
        	
        	.buttons {
        		color: black; 
        		margin-top: 20px;
        		display: inline-block; 
        		width: 100%; 
        		padding: 5px; 
        		text-align: center;
        		
        		border: solid 1px black; 
        	}
        	
        	.buttons:hover {
        		cursor: pointer; 
        		background-color: #00ccff; 
        		color: white;
        	}
        	.edit:hover {
        		cursor: pointer; 
        		color: grey;
        	}
        	.edit {
        		color: blue;
        	}
        	#search-course input {
        		border: 1px solid #555;
        		border-radius: 5px;
        		height: 20px;
        		background-color: white; 
        	}
        	
        	
        	
        	.result-panel {
        		margin-top: 5px;
        		width: 100%;
        		height: 400px;
        		border: 1px solid #555;
        		overflow-y: scroll;
        		background-color: white;
        		border-radius: 10px;
        	}
        	.result {
        		width: 100%; 
        		height: 20px;
        		border-bottom: 1px solid black;
        		padding-left: 8px; 
        		pardding-right: 8px;
        		white-space: nowrap;
        	}
        	.result:hover {
        		cursor: pointer;
        	}
        	.coursename {
        		display: inline-block;
        		width: 80%;
        	}
        	.options {
        		display: inline-block;
        		width: 15%;
        		text-align: right;
        	}
        	.edit, .delete {
        		margin-right: 5px;
        		display: inline-block;
        		width: 5px;
        	}
        	.edit:hover, .delete:hover {
        		cursor: pointer;
        	}
        	.date {
        		
        	}
        	#click_to_edit {
        		border-bottom: 1px solid #ccc;
        	}
        	#infodisplay {
        		text-align: left;
        	}
        	#picture_container {
        		display: inline-block;
        		text-align: right;
        		width: 49%;
        	}
        	#picture {
        		display: inline-block;
        		width: 120px;
        		height: 120px;
        		background-image: url("<%= request.getAttribute("image") %>");
        		background-color: white;
        		border-radius: 50%;
        	}
        	#userinfo {
        		padding-left: 20px;
        		display: inline-block;
        		text-align: left;
        		width: 50%;
        	}
		</style>
		
		<link href="css/main.css" rel="stylesheet">
		<!-- <script src="./js/deleteCourseFromProfile.js" type="text/javascript"></script> -->

	</head>
	<body>
		<%@include file="navbar.jsp"%>
		<div id="main_container">
			<div id="content_container">
				<div id="heading">
				
					<div id="picture_container">
						<div id="picture">
						
						</div>
					</div>
					<div id="userinfo">
						<strong><%= request.getAttribute("fullname") %></strong><br />
						<table id="infodisplay">
							<tr>
								<td><strong>Email: &nbsp;&nbsp;&nbsp;</strong></td>
								<td id="user_email"><%= request.getAttribute("email") %></td>
							<tr>
							<tr>
								<td><strong>Major: </strong></td>
								<td id="user_major"><%= request.getAttribute("major") %></td>
							<tr>
							<tr>
								<td><strong>Year: </strong></td>
								<td id="user_year"><%= request.getAttribute("year") %></td>
							<tr>
						</table>
						<div class="edit" onclick="window.location.href='BeginEditProfileServlet'">Click to edit</div>
						<br /><br />
					</div>
				
				</div>	
				<div id="left_half" class="half">
					<strong>Courses</strong>
					<div id="search-course-results" class="result-panel">
						<!-- display results -->
						<%
							List<Course> coursesFollowing = new ArrayList();
							if(request.getAttribute("coursesFollowing") != null) {
								coursesFollowing = (List<Course>) request.getAttribute("coursesFollowing");
							}
							if(coursesFollowing != null) {
								for(Course course : coursesFollowing) {
									if(course != null) {
						%>
						<div class="result">
							<div class="coursename" onclick="window.location.href='course?id=<%= course.getId() %>'"><%if(course != null) %><%= course.getName() %></div> 
							<div class="options">
								<div class="delete" onclick="window.location.href='ProfileRemoveCourseServlet?id=<%= course.getId() %>'"><i class="fa fa-trash" aria-hidden="true"></i></div>
							</div>
						</div>
						<%
								}
								}
							}
						%>
					</div>
					<br><br>
				</div>
				<div id="right_half" class="half">
					<strong>Notifications</strong>
					<div id="search-course-results" class="result-panel">
						<!-- display results -->
						<% if (reviews!=null&&reviews.size()>0) { %>
							<% for (Review r:reviews){ %>
							<div class="result" onclick="window.location.href='course?id=<%= r.getCourse_id() %>'">
								<div class="coursename">New post in <%= r.getCourseNameLimited() %></div> 
								<div class="options">
									<div class="date"><%= r.getDate_Created() %></div>
								</div>
							</div>
						<% } } %>
					</div>
					<br><br>
				</div>
			</div>
		</div>
	</body>
</html>