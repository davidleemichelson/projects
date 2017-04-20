<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			USCourseSpace - Edit Profile
		</title>
		
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
		<script src="errorCheck.js" type="text/javascript"></script>
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
				top: 15%; 
			}
			
			.half {
				position: absolute;
				height: 80%; 
				width: 50%; 
				white-space: normal;
			}
			
			.half:last-child {
				left: 50%;
			}
			
			#vertical_line {
				position: absolute;
				left: 50%;
				content: "";
				width: 1px; 
				height: 80%; 
				background-color: #bbb;
			}
			
			.half h1 {
				margin: 0;
			}
			#form {
				width: 80%; 
				height: 100%; 
				margin-left: 10%;
				text-align: center;
			}
			
			#title {
				text-align: center;
				width: 100%;
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
			form {
				margin-left: 10%;
				margin-top: 2%;
				width: 75%;
				
				text-align: center;
			}
			input {
				margin-top: 5px;
			}
        	.mybuttons {
        		color: black; 
        		margin-top: 20px;
        		display: inline-block; 
        		width: 37%; 
        		height: 40px;
        		padding: 5px; 
        		
        		border: solid 1px #ccc; 
        		transition: 0.5s;
        		font-size: 15px;
        	}
        	
        	.mybuttons:hover {
        		cursor: pointer; 
        		border: solid 1px black; 
        		background-color: #00ccff; 
        		color: black;
        	}
        	#button_container {
        		margin-left: -15px;
        	}
        	#error {
        		color: red; 
        		margin-top: 20px;
        	}
        	.form-control {
        		width: 100%;
        		margin-top: 15px;
        	}
        	#login-button {
				width: 75%;
			}
		</style>
		<script src="./js/errorCheck.js" type="text/javascript"></script>
		<link href="css/main.css" rel="stylesheet">
	</head>
	<body>
		<%@include file="navbar_nobtn.html"%>
		<div id="main_container">
			<div id="content_container">	
				<div id="left_half" class="half">
					<div id="form">
						<div id="title">
							Edit profile
						</div>
						<form method="GET" action="FinishEditProfileServlet">    	
							<input type="text" name="fullname" placeholder="Edit full name" required />
							<input type="email" name="email" placeholder="Edit email" required />
							<input type="text" name="avatar" placeholder="Edit image url" required />
						    
						    <select name="major" class="form-control" id="major">
						        <option>CSCI</option>
						        <option>EE</option>
						        <option>CSBA</option>
						        <option>CTIN</option>
						    </select>
							<select name="year" class="form-control" id="year">
						        <option>Freshman</option>
						        <option>Sophomore</option>
						        <option>Junior</option>
						        <option>Senior</option>
						     </select>
						     <div id="button_container">
								<input type="submit" name="login" value="Update"/>
								<div class="mybuttons">
									<a href="profile">Return to profile</a>
								</div>
						</div>
						</form>
						<div class="error" id="error"> </div>
					</div>
				</div>
				<div id="vertical_line"></div>
				<div id="right_half" class="half">
					<div id="content">
						<h1>Update your profile here! </h1>
					<p>
						Change your major? Finish a year? New e-mail? New name?!
						Change it here!
						<br/>
						<strong>
						Note: May take up to 5 minutes to update information!
						</strong>
					</p>
						<h1>Current info:</h1>
					<div id="userinfo">
						<strong><%= request.getAttribute("fullname") %></strong><br />
						<table id="infodisplay">
							<tr>
								<td><strong>Email: &nbsp;&nbsp;&nbsp;</strong></td>
								<td id="user_email"><%= request.getAttribute("email") %></td>
							<tr>
							<tr>
								<td><strong>Image: </strong></td>
								<td id="user_year"><%= request.getAttribute("image") %></td>
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
						<br /><br />
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>