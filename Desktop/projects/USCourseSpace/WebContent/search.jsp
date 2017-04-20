<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="server.MySQLDriver" %>
<%@ page import="server.SQLQueries" %>
<%@ page import="client.Course" %>
<%@ page import="client.User" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
			
			.buttons {
				height: 100%;
        		color: black;  
        		
        		font-size: 20px;
        		
        		vertical-align: top;
        		display: inline-block; 
        		width: 15%; 
				text-align: center;
        		
        		border: solid 1px #00ccff; 
        	}
        	
        	
        	.buttons:hover {
        		cursor: pointer; 
        		border: solid 1px #00ccff; 
        		background-color: #00ccff; 
        		color: white;
        	}
        	#horizontal_line {
        		position: relative; 
        		left: 0;
        		margin-top: 20px;
        		width: 100%;
        		height: 1px; 
        		background-color: #ccc;
        		margin-bottom: 20px;
        	}
        	
			.nice-select {
				width: 15%;
				height: 105%;
				margin-top: -1px;
			}
        	
			.course:hover {
				cursor: pointer;	
				background-color: #ccc;
				box-shadow: 0 0 5px 5px #ccc;
			}
        	
		</style>
	</head>
	<body>
	<%
		boolean guest = false;
		if(request.getAttribute("guest") != null) {
			if((boolean) request.getAttribute("guest") == true) {
				guest = true;
			}
		}
		if(guest) {
	%>
		<blockquote><h1>Guest Search Page</h1></blockquote>
		<%
		} else {
		%>
		<%@include file="navbar.jsp"%>
		<% } %>
		<div id="main_container">
			<div id="search_header">
				<strong>Find your course: </strong><br /><br />
				<div id="search_row">	
					<form id="search" method="GET" action="SearchServlet">
						<input type="text" name="search-value" />
					<!--  this has to be inside of the form I believe? not sure.
					but, when it is in the form it jacks up the format.
					The "Search" button does not need to be inside the form because
					of the js onClick function -->		
					</form>
					<select form="search" name="search-type">
						<option data-display="Search By" value="0">Select By:</option>
						<option value="1">Professor</option>
						<option value="2">Course Name</option>
						<option value="3">Tag</option>
						<option value="4">Department</option>
					</select>
					<div class="buttons">
						<div onClick="javascript:document.forms[0].submit()" style="line-height: 7.5vh">Search</div>
					</div>
				</div>
			</div>
			<div id="horizontal_line"></div>
			<div id="search_results">
				<%
					List<Course> results = new ArrayList<>();
					results = (ArrayList<Course>) request.getAttribute("results");
										
					if(results != null) {
				%>
						<div style="color:#888">Found: <%= results.size() %> results <%if (guest) {%> (Guests may only see 3 results) <%} %> </div>
				<%
						int index = 0;
						for(Course result : results) {
							index++;
							if(request.getAttribute("guest") != null) {
								if((boolean) request.getAttribute("guest") == true) {
									if(index == 4) break;
								}
							}
							// Get WRIT-150 style name
				%>				
				<!--  course template -->
				<div class="course" onclick="window.location.href='course?id=<%= result.getId() %>'">
					<div class="half_course">
						<div class="courseid"><strong><%= result.getDeptAndNumber() %></strong></div>
						-
						<div class="coursename"><%= result.getName() %></div>
						<div class="rating">
							<%
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
								<td> <%= result.getDescription() %></td>
							<tr>
							<tr>
								<td>
									<strong>Tags:&nbsp;</strong>
								</td>
								<td>
									<%  List<String> tags = result.getTags();
										if(tags!=null){ 
											for (int i=0; i<tags.size(); i++){ %>
										<div class="tag"><%= tags.get(i) %></div>
									<% }} %>
								</td>
							<tr>
						</table>
					</div>
				</div>
				<% }} %>
				
				
				
			</div>
		</div>
		
		<script type="text/javascript">
		$(document).ready(function() {
		  $('select').niceSelect();
		  
		});
		</script>
	</body>
</html>