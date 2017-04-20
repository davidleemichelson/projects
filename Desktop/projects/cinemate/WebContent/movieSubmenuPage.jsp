<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Movie Search Submenu</title>
		<link rel="stylesheet" href = "main.css">
		<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
	</head>
	<body>
		<div id="header">
			<h1 class="title" style="text-align: center">Cinerate</h1>
			<h2 style="text-align: center">How would you like to search for a movie?</h2>
		</div>
		<div id="content">
			<div id="xml-entry">
				<form name="xml-file" method="POST" action="LoginServlet">
					<table id="main-menu-table">
						<tr>
							<td><a class="main-menu-table-item" class="main-menu-table-item" href="actorSearch.jsp">1. Search By Actor</a></td>
							<!--  need div with scrollable list -->
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="titleSearch.jsp">2. Search By Title</a></td>
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="genreSearch.jsp">3. Search By Genre</a></td>
						</tr>
						<tr>
							<td><a class="main-menu-table-item" href="mainMenuPage.jsp">4. Back to Login Menu</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>