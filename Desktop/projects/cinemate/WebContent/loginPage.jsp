<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Cinemate Login</title>
		<link rel="stylesheet" href = "main.css">
		<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
	</head>
	<body>
		<div id="header">
			<h1 class="title" style="text-align: center">Cinefate</h1>
			<h2 style="text-align: center">File parsed! Please log in.</h2>
		</div>
		<div id="content">
			<div id="xml-entry">
				<form name="xml-file" method="POST" action="LoginServlet">
					<table>
						<tr>
							<td colspan="2">
								<input type="text" name="username" placeholder="Username..." />
								<input type="password" name="password" placeholder="Password..." />
								<input type="submit" name="submit" value="Submit" />
							</td>
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