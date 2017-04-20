<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Main Menu</title>
		<link rel="stylesheet" href = "main.css">
		<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
	</head>
	<body>
		<div id="header">
			<h1 class="title" style="text-align: center">Cinelate</h1>
			<h2 style="text-align: center">Logged in! Main Menu: What would you like to do?</h2>
		</div>
		<div id="content">
			<div id="xml-entry">
				<form name="xml-file" method="POST" action="LoginServlet">
					<table id="main-menu-table">
						<tr>
							<td><a class="main-menu-table-item" class="main-menu-table-item" href="searchUsersPage.jsp">1. Search Users</a></td>
							<!--  need div with scrollable list -->
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="movieSubmenuPage.jsp">2. Search Movies</a></td>
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="feedPage.jsp">3. View Feed</a></td>
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="profilePage.jsp">4. View Profile</a></td>
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="loginPage.jsp">5. Logout</a></td>
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="entry_dmichels.jsp">6. Exit</a></td>
						</tr>
					</table>
				</form>
				<h3 class="errorMessage">
				<% 	
					String loginError = "";
					if(request.getAttribute("LoginError") != null) {
						if(loginError != "success") {
							loginError = "ERROR:" + (String)request.getAttribute("LoginError");
						}
					}
				%>
					<%=loginError%>
				</h3>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>