<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			USCourseSpace - Login
		</title>
		
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
		<script src="errorCheck.js" type="text/javascript"></script>
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
							Registration
						</div>
						<form method="GET" action="SignupServlet">
							<input id="username" type="text" name="username" placeholder="Username" required />
							<input type="password" name="password" placeholder="Password" required />
							<input type="email" name="email" placeholder="Email" required />
							<input type="text" name="fullname" placeholder="Full Name" required />
							<input type="text" name="avatar" placeholder="URL link to avatar" required />
						    
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
								<input onclick="return errorCheck('<%="/SignupServlet"%>', '<%="search.jsp"%>', ['<%="username"%>'], 1, 'error')"id="login-button" class="mybuttons" type="submit" name="login" value="Log in"/>
								<div class="mybuttons">
									<a href="login.jsp">Registered? Log in</a>
								</div>
    					<div class="mybuttons">Continue as guest</div>
						</div>
						</form>
						<div class="error" id="error"> </div>
					</div>
				</div>
				<div id="vertical_line"></div>
				<div id="right_half" class="half">
					<div id="content">
						<h1>What is USCourseSpace? </h1>
					<p>
						Lorem ipsum dolor sit amet, consectetur adipiscing 
						elit. Nunc ex lorem, imperdiet ac ultricies id, tempus 
						ac mi. Sed eu est enim. Donec a augue et orci rhoncus 
						pretium. Donec tincidunt enim ut libero facilisis feugiat. 
						Vivamus scelerisque, enim non molestie congue, ligula 
						massa volutpat sapien, ut gravida nunc erat vel diam. 
						Nullam in tincidunt enim. Praesent malesuada ligula ut 
						massa bibendum fringilla. Nam fermentum velit sem, sit 
						amet dapibus velit vulputate eget.
					</p>
					<h1>Website updates </h1>
					<p>
						Lorem ipsum dolor sit amet, consectetur adipiscing 
						elit. Nunc ex lorem, imperdiet ac ultricies id, tempus 
						ac mi. Sed eu est enim. Donec a augue et orci rhoncus 
						pretium. Donec tincidunt enim ut libero facilisis feugiat. 
						Vivamus scelerisque, enim non molestie congue, ligula 
						massa volutpat sapien, ut gravida nunc erat vel diam. 
						Nullam in tincidunt enim. Praesent malesuada ligula ut 
						massa bibendum fringilla. Nam fermentum velit sem, sit 
						amet dapibus velit vulputate eget.
					</p>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>