<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>
            USCourseSpace - Homepage
        </title>
        
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/main.css" rel="stylesheet">
        
        <style>
        	#home_main {
        		width: 100%; 
        		height: 95vh;
        	}
        	
        	#background_container {
        		height: 100%;
        		width: 100%; 
        		-webkit-filter: brightness(0.5);
        		-moz-filter: brightness(0.5);
    			filter: brightness(0.5);
        		box-shadow:inset 0 0 200px 10px;
        		background-repeat: no-repeat;
        		background-position: center center;
        		background-size: 100% auto;
        		background-image: url("http://marshallmashup.usc.edu/wp-content/uploads/2013/10/DSC01214.jpg");
        	}
        	
        	#center_container {
        		position: absolute; 
        		width: 100%; 
        		height: 60%; 
        		top: 20%; 
        		text-align: center; 
        		color: #00ccff; 
        		
        	}
        	
        	#center_container h1 {
        		font-size: 80px;
        		text-shadow: 0 0 10px #000;
        	}
        	#center_container p {
        		font-size: 20px;
        		text-shadow: 0 0 5px #000;
        	}
        	#button_container {
        		
        	}
        	
        	.buttons {
        		color: white; 
        		margin-top: 20px;
        		display: inline-block; 
        		width: 10%; 
        		padding: 5px; 
        		
        		border: solid 1px white; 
        		transition: 0.5s;
        	}
        	
        	.buttons:hover {
        		cursor: pointer; 
        		border: solid 1px white; 
        		background-color: #00ccff; 
        		color: white;
        	}
        	
        </style>
    </head>
    <body>
    	<%@include file="navbar_nobtn.html"%>
        
        <div id="home_main">
        	<div id="background_container"></div>
        	<div id="center_container">
        		<h1>
        			USCourseSpace
        		</h1>
        		<p>
        			Best evaluations of USC courses
        		</p>
        		<div id="button_container">
        			<div class="buttons" onclick="window.href='login.jsp'">
        				<a href="login.jsp">Log in</a>
        			</div>
        			<div class="buttons" onclick="window.href='signup.jsp'">
        				<a href="signup.jsp">Sign up</a>
        			</div>
        			<div class="buttons" onclick="window.location.href='ContinueAsGuestServlet'">
        				Continue As Guest
        			</div>
        		</div>
        	</div>
        </div>
        
    </body>
</html>