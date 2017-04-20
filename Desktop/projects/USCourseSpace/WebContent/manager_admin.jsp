<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List" %>
<%@ page import="client.Professor" %>
<%@ page import="client.Department" %>
<%
	List<Professor> professors = (List<Professor>) request.getAttribute("professors");
	List<Department> departments = (List<Department>) request.getAttribute("departments");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			USCourseSpace - Manager Admin
		</title>
		
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
		
		<link href="css/main.css" rel="stylesheet">
		<link href="css/manager_admin.css" rel="stylesheet">
		
		
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		<script type="text/javascript" src="js/manager_admin.js"></script>
		
		<script>
		
		</script>
		
	</head>
	<body>
		<%@include file="navbar.jsp"%>
		<div id="main_container">
			<div id="content_container">
				<div id="heading">
				Manager Admin Page <br><br>
				
				</div>	
				<div id="left_half" class="half">
					<div id="form">
					
						
						<h3><strong>Add/Edit a Course</strong></h3>
						
						<form method="POST" action="AddCourseServlet">
						
							Course Name: <input type="text" name="coursename"/> <br>
							Course Department: 
							<select class="form-control" id="department" name="courseprefix">
								<% for(int i=0; i<departments.size(); i++){ %>
									<option value="<%= departments.get(i).getID() %>"><%= departments.get(i).getName() %> - <%= departments.get(i).getSchoolName() %></option>
								<% } %>
						 	</select>
							Course Number: <input type="text" name="coursenumber"/> <br>
							Professor:
							<select class="form-control" id="professors" name="courseprofessor">
								<% for (int i=0; i<professors.size(); i++){ %>
						        	<option value="<%= professors.get(i).getId() %>"><%= professors.get(i).getName() %></option>
								<% } %>
						 	</select> <br>
							Description: <textarea name="coursedescription"></textarea>
							<input type="text" style="display:none;" name="courseid" value='-1' />
							<input type="submit" class="buttons" value="Submit"/>
							<% if(request.getAttribute("addCourseMessage")!=null) { %>
								<%= request.getAttribute("addCourseMessage") %>
							<% } %>
						</form>
						
						<br>
						<h3><strong>Add a Professor/Major/Tag</strong></h3>
						
						
						<form method="POST" action="AddProfessorServlet">
							Professor Name: <input type="text" name="professorname" size="5"/> <br>
							Professor Email: <input type="text" name="professoremail" size="5"/> <br>
							Professor Avatar: <input type="text" name="professorimage" size="5"/> <br>
							<input type="submit" class='buttons' value="Submit" />
							<% if(request.getAttribute("addProfessorMessage")!=null) { %>
								<%= request.getAttribute("addProfessorMessage") %>
							<% } %>
						</form>
						<br>
						
						<form method="POST" action="AddMajorServlet">
							Department Prefix: <input type="text" name="department_prefix" size="5"/> <br>
							School: <input type="text" name="school_name" size="5"/> <br>
							<input type="submit" class="buttons" value="Submit" />
						</form>
						<br>
						
						<form method="POST" action="AddTagServlet">
							Tag: <input type="text" name="tag" size="5"/> <br>
							<input type="submit" class="buttons" value="Submit" />
						</form>
						<br>
						
						<h3><strong>Add/Edit a User</strong></h3>
						
						<form method="POST" action="AddUserServlet">
							User Name: <input type="text" name="user_name" required /> <br>
							Full name: <input type="text" name="user_fullname" required /> <br>
							User Email: <input type="text" name="user_email" required /> <br>
							User Password: <input type="text" name="user_password" required /> <br>
							User Image: <input type="text" name="user_image" /> <br>
							
							User Year: 
								<select class="form-control" name="year">
							        <option value="Freshman">Freshman</option>
							        <option value="Sophomore">Sophomore</option>
							        <option value="Junior">Junior</option>
							        <option value="Senior">Senior</option>
							 	</select>

							User Major: <input type="text" name="user_major"/> <br>
							<input type="submit" class="buttons" value="Submit"/>
							<% if(request.getAttribute("addUserMessage")!=null) { %>
								<%= request.getAttribute("addUserMessage") %>
							<% } %>
						</form>
						
						<br>
		
					</div>
				</div>
				<div id="vertical_line"></div>
				<div id="right_half" class="half">
					<div id="content">
						Search Course: 
						<form id="searchCourse" onsubmit="return searchCourse();" class="search">
							<input id="searchThisCourse" type="text" name="user"  />
						</form>
						<div id="search-course-results" class="result-panel"  class="search">
							<!-- display results -->
						</div>
						<br><br>
						Search User: 
						<form id="searchUser" onsubmit="return searchUser();"  class="search">
							<input id="searchThisName" type="text" name="user"  />
						</form>
						<div id="search-user-results" class="result-panel">
							<!-- display results -->
						</div>
						<br><br>
						<br><br>
						
						Search Professor:  
						<form id="search-professor" class="search" onsubmit="return searchProfessor();">
							<input type="text" name="course" id="searchThisProfessor" />
						</form>
						<div id="search-professor-results" class="result-panel">
							<!-- display results -->
							
						</div>
						<br><br>
						
						Search major:  
						<form id="search-major" class="search" onsubmit="return searchMajor();">
							<input type="text" name="course" id="searchThisMajor" />
						</form>
						<div id="search-major-results" class="result-panel">
							<!-- display results -->
						</div>
						<br><br>
						
						Search tag:  
						<form id="search-tag" class="search" onsubmit="return searchTag();">
							<input type="text" name="tag" id="searchThisTag" />
						</form>
						<div id="search-tag-results" class="result-panel">
							<!-- display results -->
						</div>
						<br><br>
						
					</div>
				</div>
			</div>
		</div>
		
	</body>
</html>