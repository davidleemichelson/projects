<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="client.Tag" %>
<%@ page import = "java.util.List" %>
<% List<Tag> tags = (List<Tag>)request.getAttribute("tags"); %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			USCourseSpace - Compose Review
		</title>
		
		<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
		<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
		
		<link href="css/main.css" rel="stylesheet">
		<style>
			#main_container {
				width: 100%;
				height: 900px;
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
			}
			
			#title {
				width: 100%;
				font-size: 20px;
				text-align: center;
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
			
			input {
				margin-top: 5px;
			}
        	
        	#button_container {
        		margin-left: -15px;
        	}
        	
        	.form-control {
        		width: 100%;
        		margin-top: 15px;
        	}
        	input[type=checkbox] {
        		width: 20px;
        	}
			
			textarea {
				width: 100%;
			}
			
        	.buttons {
        		color: black; 
        		margin-top: 20px;
        		display: inline-block; 
        		width: 100%; 
        		padding: 5px; 
        		text-align: center;
        		
        		border: solid 1px black; 
        	}
        	
        	.buttons:hover {
        		cursor: pointer; 
        		background-color: #00ccff; 
        		color: white;
        	}
        	
		</style>
		
		<script>
			function toJson(){
				  var data = $('#content_container').serializeArray();

				  var tags= [];
				  var i = 0; 
				  var result = {}; 
				  for (i = 0; i < data.length; i++){
					if(data[i]['name']!='tags[]')
					  	result[data[i]['name']] = data[i]['value'];
				  }
				  
				  i = 0;
				  $("input[name='tags[]']:checked:enabled").each(function() {
				      tags[i] = $(this).val();
				      i++;
				  });
				  result.tags = tags;
				  
				  console.log(JSON.stringify(result));
				  
				  var param = '?info=' + JSON.stringify(result);
				  
				  $.ajax({
					 async: true,
				    url: 'ReviewServlet',
				    type: 'POST',
				    contentType: 'application/json',
				    data: JSON.stringify(result),
				    success: function(data){
				    	console.log("HEY!");
				    	window.location.href = "course?id=" + result.courseid; 
				    }
				  });
				  
				  return false; 
			}
		</script>
	</head>
	<body>
		<%@include file="navbar.jsp"%>
		<div id="main_container">
			<form id="content_container" method="GET" onsubmit="return toJson();">	
				<div id="left_half" class="half">
					<div id="form">
						<input name="courseid" style="display:none;" value='<%= request.getAttribute("courseid") %>' />
						<input name="professorid" style="display:none;" value='<%= request.getAttribute("courseid") %>' />
					
						<div id="title">
							<strong>Review to CSCI 103</strong>
						</div>
						
						<br> When did you take this course?<br>
						
						<input type = "text" id = "myText" placeholder="Semester and Year" name="semesterandyear" />
						
						Which of the following does this course include? <br>
						
						<!-- <form> -->
						<div style="width: 100%"><input type="checkbox" name="midterms" value= "midterms" /> Midterms</div>
						<div style="width: 100%"><input type="checkbox" name="finals" value= "finals" /> Final</div>
						<div style="width: 100%"><input type="checkbox" name="essays" value= "essays" /> Essays</div>
						<div style="width: 250px"><input type="checkbox" name="assignments" value= "assignments" /> Assignments (Reading responses, weekly submissions, etc.)</div>
						<div style="width: 250px"><input type="checkbox" name="projects" value= "projects" /> Projects</div>
						<div style="width: 250px"><input type="checkbox" name="quizzes" value= "quizzes" /> Popup Quizzes</div>
						<!-- </form> -->
						
						<br> How would you rate this course? <br>
						
						Grading: 
						<select name="grading" class="form-control">
						        <option value="1">1.0</option>
						        <option value="2">2.0</option>
						        <option value="3">3.0</option>
						        <option value="4">4.0</option>
						        <option value="5">5.0</option>
						 </select>
						
						Workload: 
						<select name="workload" class="form-control">
						        <option value="1">1.0</option>
						        <option value="2">2.0</option>
						        <option value="3">3.0</option>
						        <option value="4">4.0</option>
						        <option value="5">5.0</option>
						 </select>
						 
						 Content: 
						<select name="content" class="form-control">
						        <option value="1">1.0</option>
						        <option value="2">2.0</option>
						        <option value="3">3.0</option>
						        <option value="4">4.0</option>
						        <option value="5">5.0</option>
						 </select>
						 
						 Teaching: 
						<select name="teaching" class="form-control">
						        <option value="1">1.0</option>
						        <option value="2">2.0</option>
						        <option value="3">3.0</option>
						        <option value="4">4.0</option>
						        <option value="5">5.0</option>
						 </select>
						
						Which of the tags descibe the course most precisely?
							<% for (int i=0; i<tags.size(); i++){ %>
								<div style="width: 100%"><input type="checkbox" name="tags[]" value= "<%= tags.get(i).getID() %>"> <%= tags.get(i).getName() %> </div>
							<% } %>
					</div>
				</div>
				<div id="vertical_line"></div>
				<div id="right_half" class="half">
					<div id="content">
						What would you like to say about this course? (200 words) <br><br>
						
						<textarea name="comments" rows="20" cols="60"></textarea>
						
						<input type="submit" class="buttons" value="Submit"/>
					
					</div>
				</div>
			</form>
		</div>
	</body>
</html>