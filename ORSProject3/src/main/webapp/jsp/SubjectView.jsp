<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj3.dto.CourseDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj3.ctl.SubjectCtl"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.ctl.ORSView"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<title>Subject View</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--    favicon-->
<link rel="shortcut icon"
	href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-icon">


<style type="text/css">



.video-wrap {
  clip: rect(0, auto, auto, 0);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

#video {
  position: fixed;
  display: block;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center center;
  -webkit-transform: translateZ(0);
          transform: translateZ(0);
  will-change: transform;
  z-index: -1000;
}
video { 
  position: fixed;
  top: 50%;
  left: 50%;
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  z-index: -100;
  transform: translateX(-50%) translateY(-50%);
  background: url('') no-repeat;
  background-size: cover;
  transition: 1s opacity;
}

.row, .container-fluid {
margin-left: 0px!important;
margin-right: 0px!important;
}


label {
	position: absolute;
}
</style>
</head>

<body background="/ORSProject3/image/a108.jpg">
	<form action="<%=ORSView.SUBJECT_CTL%>" method="post">
	
	<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div></div> -->

		<jsp:useBean id="dto" class="in.co.rays.proj3.dto.SubjectDTO"
			scope="request"></jsp:useBean>

			<%
				List cList = (List) request.getAttribute("courseList");
			%>
	<div class="container-fluid">      
		<div class="row">
		<div class="col-xs-12 col-md-6 col-sm-12 col-lg-4 col-md-offset-4">
			<div class="panel panel-primary" style="margin-top:10px; background-color: #DCDCDC;">
				<div class="panel-heading" style="background-color:#e9967a00;" align="center">
		
	<%
 	if (dto.getId() > 0) {
 %> <b><h1>Update Subject</h1></b> <%
 	} else {
 %> <b><h1>Add Subject</h1></b> <%
 	}
 %>
		</div>
		
		<div class="panel-body">


               <div align="center">
			<div class="alert alert-success" role="alert"
				style="width: 90%; margin-left: 0%; font-size: 150%"
				<%=ServletUtility.getSuccessMessage(request).equals("") ? "hidden" : ""%>>
				<b> <%=ServletUtility.getSuccessMessage(request)%></b>
			</div>
			<div class="alert alert-danger" role="alert"
				style="width: 90%; margin-left: 0%; font-size: 150%;"
				<%=ServletUtility.getErrorMessage(request).equals("") ? "hidden" : ""%>>
				<b><%=ServletUtility.getErrorMessage(request)%></b>
			</div>
		</div>
		<br>
			

			<input type="hidden" name="id" value="<%=dto.getId()%>"> 
			<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>"> 
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

		<div class="col-xs-12 col-sm-12 col-md-10 col-lg-12">
			

		<div class="form-group" style="margin-left: 10%;">
		<label align="left" class="control-label col-md-6 text-info">
		Course Name<span style="color: red;">*</span></label>

		 <div class="col-md-12"  style="margin-bottom: 25px;">
		<div class="input-group">
		<span class="input-group-addon">
		<i class="glyphicon glyphicon-user"></i></span> 
		<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), cList)%>
		</div>
		<label class="control-label text-danger" for="inputError1">
		<%=ServletUtility.getErrorMessage("courseId", request)%></label>
		</div>
		</div>
		
		       
		
		 <div class="form-group" style="margin-left: 10%;">
	 <label align="left" class="control-label text-info col-md-6">
	 Subject Name<span style="color: red;">*</span></label>

	 <div class="col-md-12"  style="margin-bottom: 25px;">
	 <div class="input-group">
	 <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
	 <input type="text" class="form-control" name="subjectName" placeholder="Enter Subject Name"
	 value="<%=DataUtility.getStringData(dto.getSubjectName())%>">
	 </div>
	 <label class="control-label text-danger"  for="inputError1">
	 <%=ServletUtility.getErrorMessage("subjectName", request)%></label>
	 </div>
	 </div> 
					 
      
	 <div class="form-group" style="margin-left: 10%;">
	 <label align="left" class="control-label text-info col-md-6">
	 Description<span style="color: red;">*</span></label>

	  <div class="col-md-12"  style="margin-bottom: 25px;">
	 <div class="input-group">
	 <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
	 <input type="text" class="form-control" name="description" placeholder="Description of Subject"
	 value="<%=DataUtility.getStringData(dto.getDescription())%>">
	 </div>
	 <label class="control-label text-danger"  for="inputError1">
	 <%=ServletUtility.getErrorMessage("description", request)%></label>
	 </div>
	 </div>
				
				</div>
				  
				 
		
		<div class="form-group" align="center">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<%if (dto.getId() > 0) { %><br>
        <button type="submit" class="btn btn-success" name="operation"
		value="<%=SubjectCtl.OP_UPDATE%>"><span class="glyphicon glyphicon-check"></span> Update</button>
		<button type="submit" class="btn btn-primary" name="operation"
		value="<%=SubjectCtl.OP_CANCEL%>"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
						<%
						} else {
					%><br>
		<button type="submit" class="btn btn-primary" name="operation"
		value="<%=SubjectCtl.OP_SAVE%>"><span class="glyphicon glyphicon-check"></span> Save</button>&emsp;
		<button type="submit" class="btn btn-warning" name="operation"
		value="<%=SubjectCtl.OP_RESET%>"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
		<%}%>
					
					
					</div>
					</div>
					</div>
					</div>
				</div>
			</div>
		<br><br>
			</div>
				  
	
	</form>
	<br>
	
	<%@ include file="Footer.jsp"%>
</body>
</html>