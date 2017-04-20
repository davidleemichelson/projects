<%@ page contentType="text/javascript" %>
<%@ page import="client.User" %>
$( document ).ready(function() {
    connectToServer();
});
var socket; 
function connectToServer(){
	socket = new WebSocket("ws://127.0.0.1:8080/USCourseSpace/notifications");
	socket.onopen = function(event){
		socket.send('<%= ((User)session.getAttribute("user")).getUsername() %>');
		console.log("Connecting...");
	}
	socket.onmessage = function(event){
		if(event.data=="newpost"){
			$('#notification').css('display', 'block');
		}
	}
	socket.onclose = function(event){
		console.log("Notifications closing...");
	}
}
function sendMessage() {
	socket.send("Aha - check this out" ); 
	return false; 
}