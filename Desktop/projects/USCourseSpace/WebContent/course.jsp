<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List" %>
<%@ page import="client.Course" %>
<%@ page import="client.Review" %>
<%@ page import="client.Professor" %>
<%@ page import="client.Department" %>
<% 
	Course course = (Course)request.getAttribute("course");
	List<Review> reviews = (List<Review>)request.getAttribute("reviews");
	Professor professor = (Professor)request.getAttribute("professor");
	Department department = (Department)request.getAttribute("department");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		<script src="js/jquery.nice-select.js"></script>
		<link rel="stylesheet" href="css/nice-select.css">
		
		
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
		
		<title>USCourseSpace - Search</title>
		
		<link href="css/main.css" rel="stylesheet">
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		<style>
			#main_container {
				position: absolute; 
				top: 10vh; 
				width: 90%;
				left: 5%;
				height: 90vh;
			}
			#search_header {
				width: 100%;
			}
			#search_row {
				height: 8vh;
			}
			
			form {vertical-align: top;
				display: inline-block;
				width: 65%;
				height: 100%;
			}
			
			input {
				height: 100%;
				width: 100%;
				font-size: 20px;
				background-color: #eee;
				padding: 10px;
				border: none;
				border: solid 2px #c9c9c9;
				transition: border 0.3s;
			}
			input:focus,
			input.focus {
				outline: none;
				border: solid 2px #00ccff;
			}
        	
        	#horizontal_line {
        		position: relative; 
        		left: 0;
        		margin-top: 20px;
        		width: 100%;
        		height: 1px; 
        		background-color: #aaa;
        		margin-bottom: 0px;
        	}
 			#search_results {
 				overflow-y: scroll;
 				height: 400px;
 			}
 			/* course info */
 			.course {
				white-space: nowrap;
				width: 50%;
				margin-left: 25%;
			}
			.half_course {
				display: block;
				width: 100%;
			}
			
			.course h2 {
				color: inherit;
				margin:0;
				font-size: 60px;
			}
			.courseid, .coursename {
				display: inline-block;
				font-size: 40px;
			}
			.score {
				display: inline-block;
				width: 20%;
			}
			.info {
				margin-top: 5px;
				white-space: normal;
			}
			.info td {
				vertical-align: top;
				text-align: left;
			}
			.tag {
				color: white;
				border-radius: 5px;
				padding: 3px 5px;
				background-color: #00ccff;
				display: inline-block; 
			}
			
			/* comments */
			
			.vertical_line {
				display: inline-block;
				height: 100px;
				margin-left: 10px;
				margin-right: 10px;
				width: 1px; 
				background-color: #ccc;
			}
			
			#header {
				margin-top: 20px;
			}
			.comments {
				width: 100%;
			}
			.comment {
				width: 50%;
				margin-left: 25%;
			}
			.comment:after {
				content: "";
				width: 100%;
				height: 1px;
				background-color: #bbb;
				display:block;
				margin-top: 10px;
				margin-bottom: 10px;
			}
			.comment:last-child:after {
				background-color: #eee;
			}
			
			.general_grading {
				vertical-align: top;
				padding-top: 5px;
				display: inline-block;
			}
			.tasks {
				display: inline-block;
			}
			.text {
				vertical-align: top;
				display: inline-block;
				width: 60%;
			}
			
			.buttons {
	     		color: black; 
	     		margin-top: 20px;
	     		display: inline-block; 
	     		width: 30%; 
	     		padding: 5px; 
	     		text-align: center;
	     		
	     		border: solid 1px black; 
	     		
	     	}
	     	
	     	.buttons:hover {
	     		cursor: pointer; 
	     		background-color: #00ccff; 
	     		color: white;
	     	}
	     	.button_container {
	     		width: 100%;
	     		margin-bottom: 20px;
	     	}
		</style>
	</head>
	<body>
		<%
			boolean guest=false;
			if(request.getAttribute("guest") != null) {
				if((boolean) request.getAttribute("guest") == true) {
					guest = true;
				}
				else {
					guest = false;
				}
			}
			if(!guest) {
		%>
		<%@include file="navbar.jsp"%>
		<% } %>
			
		<div id="header">
			<!--  course template -->
			<div class="course">
				<div class="half_course">
					<div class="courseid"><strong><%= course.getCode() %> </strong></div>
					<div class="coursename">&nbsp;- <%= course.getName() %></div>
					<div><%= department.getName() %> - <%= department.getSchoolName() %> </div>
					<div>Current professor: <strong><a href="professor?id=<%= professor.getId() %>"><%= professor.getName() %></a></strong></div>
					<div><%= reviews.size() %> reviews</div>
					
					<div class="button_container">
						<%
							if(!guest) {
						%>
						<div class="buttons" onclick="window.location.href='composeReview?id=<%= course.getId() %>'">Write a review</div>
						<%
							if(request.getAttribute("follows") != null) {
								if((boolean) request.getAttribute("follows")) {
						%>
						<div class="buttons" onclick="window.location.href='UnfollowCourseServlet?id=<%= course.getId() %>'">Unfollow this course</div>
						<%		}
								else {
						%>
						<div class="buttons" onclick="window.location.href='FollowCourseServlet?id=<%= course.getId() %>'">Follow this course</div>
						<%		}
							}
							else {	
						%>
						<div class="buttons" onclick="window.location.href='FollowCourseServlet?id=<%= course.getId() %>'">Follow this course</div>
						<% 	}} %>
					</div>
					<div class="rating">
					
							<%	Course result = course;
								String good = "#2db52a";
								String ok = "#c9c021"; 
								String bad = "#D2000D";
								String grading="", workload = "", content = "", teaching = "";
								String grading_color = ok, workload_color = ok, content_color = ok, teaching_color = ok; 
								if(result.getGrading()!=0.0){ 
									grading = Double.toString(Math.round(result.getGrading() * 100.0) / 100.0).substring(0,3);
									if (result.getGrading()>3.7)grading_color =good; 
									else if(result.getGrading() >2.4) grading_color = ok; 
									else grading_color = bad;
								} else {
									grading = "--";
								}
								if(result.getWorkload()!=0.0){ 
									workload = Double.toString(Math.round(result.getWorkload() * 100.0) / 100.0).substring(0,3);
									if (result.getWorkload()>3.7)workload_color =good; 
									else if(result.getWorkload() >2.4) workload_color = ok; 
									else workload_color = bad;
								} else {
									workload = "--";
								}
								if(result.getContent()!=0.0){ 
									content = Double.toString(Math.round(result.getContent() * 100.0) / 100.0).substring(0,3);
									if (result.getContent()>3.7)content_color =good; 
									else if(result.getContent() >2.4) content_color = ok; 
									else content_color = bad;
								} else {
									content = "--";
								}
								if(result.getTeaching()!=0.0){ 
									teaching = Double.toString(Math.round(result.getTeaching() * 100.0) / 100.0).substring(0,3);
									if (result.getTeaching()>3.7)teaching_color =good; 
									else if(result.getTeaching() >2.4) teaching_color = ok; 
									else teaching_color = bad;
								} else {
									teaching = "--";
								}
								%>
						<div class="score" style="color:<%= grading_color %>">Grading<h2><strong><%= grading %></strong></h2></div>
						<div class="score" style="color:<%= workload_color %>">Workload<h2><strong><%= workload %></strong></h2></div>
						<div class="score" style="color:<%= content_color %>">Content<h2><strong><%= content %></strong></h2></div>
						<div class="score" style="color:<%= teaching_color %>">Teaching<h2><strong><%= teaching %></strong></h2></div>
					</div>
				</div>
				<div class="half_course">
					<table class="info">
						<tr>
							<td>
								<strong>Description:&nbsp;</strong>
							</td>
							<td> <%= course.getDescription() %> </td>
						<tr>
						<tr>
							<td>
								<strong>Tags:&nbsp;</strong>
							</td>
							<td>
								<% List<String> tagss = course.getTags(); if(tagss!=null) for (String t:tagss){ %>
									<div class="tag"><%= t %></div>
								<% } %>
							</td>
						<tr>
					</table>
				</div>
				<div id="horizontal_line"></div>
			</div>
			
		</div>
		<div id="comments">
			<!-- comment template -->
			<% if (reviews!=null){
				int index = 0;
				for (int i=reviews.size()-1; i>=0; i--){
					index++;
					if(guest) 
							if(index == 4) break;
				%>
			
			<div class="comment">
				<div class="general_grading">
					<table>
						<tr><td>Grading: </td><td><%= reviews.get(i).getGradingRating() %></td></tr>
						<tr><td>Workload: </td><td><%= reviews.get(i).getWorkloadRating() %></td></tr>
						<tr><td>Content: </td><td><%= reviews.get(i).getContentRating() %></td></tr>
						<tr><td>Teaching: </td><td><%= reviews.get(i).getTeachingRating() %></td></tr>
					</table>
				</div>
				<div class="vertical_line"></div>
				<div class="tasks">
					<% if (reviews.get(i).isHasMidterm()) { %><i class="fa fa-check-square" aria-hidden="true">
					<% } else { %><i class="fa fa-square" aria-hidden="true"><% } %></i> Midterms <br />
					<% if (reviews.get(i).isHasFinal()) { %><i class="fa fa-check-square" aria-hidden="true">
					<% } else { %><i class="fa fa-square" aria-hidden="true"><% } %></i> Finals <br />
					<% if (reviews.get(i).isHasAssignments()) { %><i class="fa fa-check-square" aria-hidden="true">
					<% } else { %><i class="fa fa-square" aria-hidden="true"><% } %></i> Assignments <br />
					<% if (reviews.get(i).isHasProjects()) { %><i class="fa fa-check-square" aria-hidden="true">
					<% } else { %><i class="fa fa-square" aria-hidden="true"><% } %></i> Projects <br />
					<% if (reviews.get(i).isHasQuiz()) { %><i class="fa fa-check-square" aria-hidden="true">
					<% } else { %><i class="fa fa-square" aria-hidden="true"><% } %></i> Pop quizzes <br />
					<% if (reviews.get(i).isHasEssay()) { %><i class="fa fa-check-square" aria-hidden="true">
					<% } else { %><i class="fa fa-square" aria-hidden="true"><% } %></i> Essays <br />
				</div>
				<div class="vertical_line"></div>
				<div class="text">
					<table class="info">
						<tr>
							<td>
								<strong>Comments:&nbsp;</strong>
							</td>
							<td> <%= reviews.get(i).getCourse_Review() %> </td>
						<tr>
						<tr>
							<td>
								<strong>Tags:&nbsp;</strong>
							</td>
							<td>
								<%  List<String> tags = reviews.get(i).getTags(); 
									for (String t:tags){
								%>
									<div class="tag"><%= t %></div>
								<% } %>
							</td>
						<tr>
					</table>
				</div>
			</div>
			<% }} %>
			
		</div>
		
		
	</body>
</html>