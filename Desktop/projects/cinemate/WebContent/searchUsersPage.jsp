<%@page import="data.DataStorage"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>User Search</title>
		<link rel="stylesheet" href = "main.css">
		<link href="https://fonts.googleapis.com/css?family=VT323" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Quicksand" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
	</head>
	<body>
		<div id="header">
			<h1 class="title" style="text-align: center">CineNate</h1>
			<h2 style="text-align: center">Please enter a USER you'd like to search.</h2>
		</div>
		<div id="content">
			<div id="xml-entry">
				<form name="xml-file" method="POST" action="SearchUsersServlet">
					<table>
						<tr>
							<td colspan="2">
								<input type="text" name="SearchInput" placeholder="Search..." />
								<input type="submit" name="search" value="Search!" />
							</td>
						</tr>
					</table>
				</form>
				<%
					Set<String> searchResultStringSet = new HashSet<String>();
					if(request.getAttribute("SearchResults") != null) {
						searchResultStringSet = (HashSet<String>)request.getAttribute("SearchResults");
					}	
				%>
				<div id="results">
					<table id="search-results-table">
						<th id="search-results-header"><h2>Search Results</h2></th>
						<%
							String result = "";
							for(String stringResult : searchResultStringSet) {
								result = stringResult;
						%>	
							<tr class="search-result-table-row">
								<td><h2 id="result-h2"><%=result%></h2></td>
							</tr>
						<%
							}
						%>
					</table>
				</div>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>