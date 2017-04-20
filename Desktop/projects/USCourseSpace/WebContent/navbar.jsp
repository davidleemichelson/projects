<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="client.User" %>
    
<script src="js/notifications.jsp" type="text/javascript"></script>
<div id="navbar" onload="connectToServer();">
    <div class="subnav">
        <a href="index.jsp"><i class="fa fa-star" aria-hidden="true"></i> USCourseSpace</a>
    </div>
    <div class="subnav" style="text-align:right;">
       	<div class="navbtn">
      			<a href="search.jsp" class="middleOut">
       			<i class="fa fa-search" aria-hidden="true"></i> Search
       		</a>
       	</div>
       	<div class="navbtn" id="profile_btn">	
      			 <a href="profile"  class="middleOut">
      			 <i class="fa fa-user" aria-hidden="true"></i> Profile 
      			 </a>
      			 <div id="notification" <% if(((User)session.getAttribute("user")).getHasUnread()==1) {  %>style="display:block;"<% } %>></div>
       	</div>
       	<div class="navbtn">
       		<a href="logout" class="middleOut">
       		<i class="fa fa-sign-out" aria-hidden="true"></i> Logout
       		</a>
       	</div>
    </div>
</div>