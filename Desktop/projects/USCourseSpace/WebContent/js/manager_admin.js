/**
 *  User management
 */
function searchUser(){
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", "searchUser?name=" + $('#searchThisName').val(), true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if (this.responseText!=""){
			document.getElementById("search-user-results").innerHTML = this.responseText;
		}
	}
	
	return false; 
}

function editUser(username) {
	var xhttp = new XMLHttpRequest();
	
	xhttp.open("GET", "editUser?username=" + username, true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if (this.responseText!=""){
			var json = JSON.parse(this.responseText);
			console.log(json);
			console.log($('input[name=user_name]').val());
			$('input[name=user_name]').val(json["username"]);
			$('input[name=user_fullname]').val(json["fullName"]);
			$('input[name=user_email]').val(json["email"]);
			$('input[name=user_password]').val(json["password"]);
			$('input[name=user_image]').val(json["photoURL"]);
			$('input[name=year]').val(json["year"]);
			$('input[name=user_major]').val(json["major"]);
		}
	}
	
	return false; 
}
/**
 *  User management ends
 */
/**
 *  Professor management
 */
function searchProfessor(){
	var xhttp = new XMLHttpRequest();
	console.log("searchProfessor?name=" + $('#searchThisProfessor').val());
	xhttp.open("GET", "searchProfessor?name=" + $('#searchThisProfessor').val(), true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if (this.responseText!=""){
			document.getElementById("search-professor-results").innerHTML = this.responseText;
		}
	}
	
	return false; 
}

function deleteProfessor(pid){
	var id = pid;
	console.log(id);
	var xhttp = new XMLHttpRequest();
	console.log("deleteProfessor?id=" + id);
	xhttp.open("GET", "deleteProfessor?id=" + id, true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState === XMLHttpRequest.DONE && xhttp.status === 200) {
			if (this.responseText!=""){
				alert(this.responseText);
				$('#professor'+pid).remove();
			}
		}
	}
	
	return false; 
}

/**
 *  Professor management ends
 */
/**
 *  Major management
 */
function searchMajor(){
	var xhttp = new XMLHttpRequest();
	console.log("searchMajor?prefix=" + $('#searchThisMajor').val());
	xhttp.open("GET", "searchMajor?prefix=" + $('#searchThisMajor').val(), true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if (this.responseText!=""){
			document.getElementById("search-major-results").innerHTML = this.responseText;
		}
	}
	
	return false; 
}

function deleteDepartment(pid){
	var id = pid;
	console.log(id);
	var xhttp = new XMLHttpRequest();
	console.log("deleteDepartment?id=" + id);
	xhttp.open("GET", "deleteDepartment?id=" + id, true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState === XMLHttpRequest.DONE && xhttp.status === 200) {
			if (this.responseText!=""){
				alert(this.responseText);
				$('#department'+pid).remove();
			}
		}
	}
	
	return false; 
}

/**
 *  Major management ends
 */
/**
 *  Tag management
 */
function searchTag(){
	var xhttp = new XMLHttpRequest();
	console.log("searchTag?name=" + $('#searchThisTag').val());
	xhttp.open("GET", "searchTag?name=" + $('#searchThisTag').val(), true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if (this.responseText!=""){
			document.getElementById("search-tag-results").innerHTML = this.responseText;
		}
	}
	
	return false; 
}

function deleteTag(pid){
	var id = pid;
	console.log(id);
	var xhttp = new XMLHttpRequest();
	console.log("deleteTag?id=" + id);
	xhttp.open("GET", "deleteTag?id=" + id, true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if(xhttp.readyState === XMLHttpRequest.DONE && xhttp.status === 200) {
			if (this.responseText!=""){
				alert(this.responseText);
				$('#tag'+pid).remove();
			}
		}
	}
	
	return false; 
}

/**
 *  Tag management ends
 */
/**
 *  Course management
 */
function searchCourse(){
	var xhttp = new XMLHttpRequest();
	console.log("searchCourse?name=" + $('#searchThisCourse').val());
	xhttp.open("GET", "searchCourse?name=" + $('#searchThisCourse').val(), true);
	xhttp.send();
	
	xhttp.onreadystatechange = function(){
		if (this.responseText!=""){
			document.getElementById("search-course-results").innerHTML = this.responseText;
		}
	}
	
	return false; 
}

function editCourse(id) {
	var xhttp = new XMLHttpRequest();
	
	console.log("editCourse?courseid=" + id);
	xhttp.open("GET", "editCourse?courseid=" + id, true);
	xhttp.send();	
	
	xhttp.onreadystatechange = function(){
		if (this.responseText!=""){
			var json = JSON.parse(this.responseText);
			console.log(json);
			$('input[name=coursename]').val(json["name"]);
			$('input[name=courseid]').val(json["id"]);
			
			$('textarea').val(json["description"]);
			$('input[name=coursenumber]').val(json["code"]);
			
			$('#department option[value=\''+json["dept_id"] +'\'').prop('selected',true);
			$('#professors option[value=\''+json["professor"] +'\'').prop('selected',true);
			
		}
	}
	
	return false; 
}