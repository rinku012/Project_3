<%@page import="in.co.rays.proj3.dto.TimeTableDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj3.ctl.TimeTableCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@include file="Header.jsp"%>

<html>
<head>
<title>Time Table View</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--    favicon-->
<link rel="shortcut icon"
	href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-icon">


<style type="text/css">


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


.error_msg{
margin-left: -110px; 
}

label {
	position: absolute;
}

</style>
</head>
<body background="/ORSProject3/image/a133.jpg">
	<form action="<%=ORSView.TIME_TABLE_CTL%>" method="post">
	
	<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div></div> -->
	
		<jsp:useBean id="dto" class="in.co.rays.proj3.dto.TimeTableDTO"
			scope="request"></jsp:useBean>
		
		<center  style="height: 360px;">
			<%
				List cList = (List) request.getAttribute("courseList");
				List sList = (List) request.getAttribute("subjectList");
			%>

<div class="container-fluid">      
		<div class="row">
		<div class="col-xs-12 col-md-6 col-sm-12 col-lg-4 col-md-offset-4">
			<div class="panel panel-primary" style="margin-top:10px; background-color:  #DCDCDC;">
				<div class="panel-heading" style="background-color: #e9967a00;" align="center">
		
	<%
 	if (dto.getId() > 0) {
 %> <b><h1>Update Time Table</h1></b> <%
 	} else {
 %> <b><h1>Add Time Table</h1></b> <%
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
		<label class="control-label text-danger error_msg" for="inputError1">
		<%=ServletUtility.getErrorMessage("courseId", request)%></label>
		</div>
		</div> 
  					
  					
		
		<div class="form-group" style="margin-left: 10%;">
		<label align="left" class="control-label col-md-6 text-info">
		Subject Name<span style="color: red;">*</span></label>

		 <div class="col-md-12"  style="margin-bottom: 25px;">
		<div class="input-group">
		<span class="input-group-addon">
		<i class="glyphicon glyphicon-user"></i></span> 
		<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), sList)%>
		</div>
		<label class="control-label text-danger  error_msg" for="inputError1">
		<%=ServletUtility.getErrorMessage("subjectId", request)%></label>
		</div>
		</div> 
		

									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Exam Time<span style="color: red;">*</span>
										</label>

										 <div class="col-md-12"  style="margin-bottom: 25px;">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-user" aria-hidden="true"></i></span>
													<%
							HashMap map = new HashMap();
							map.put("","--- Select Time ---");
							map.put("08:00 AM To 10:00 AM", "08:00 AM To 10:00 AM");
							map.put("12:00 PM To 02:00 PM", "12:00 PM To 02:00 PM");
							map.put("03:00 PM To 05:00 PM", "03:00 PM To 05:00 PM");
							String examTimeList = HTMLUtility.getList("examTime", dto.getExamTime(), map);
						%> <%=examTimeList%>
											</div>
											<label class="control-label text-danger  error_msg" for="inputError1">
												<%=ServletUtility.getErrorMessage("examTime", request)%></label>
										</div>
									</div>
									
  					
  					
			
		<div class="form-group" style="margin-left: 10%;">
		<label align="left" class="control-label col-md-6 text-info">
		Exam Date<span style="color: red;">*</span></label>

		 <div class="col-md-12"  style="margin-bottom: 25px;">
		<div class="input-group">
		<span class="input-group-addon">
		<i class="glyphicon glyphicon-calendar"></i></span> 
		<input type="text" class="form-control" name="examDate"  id="datesun" readonly="readonly"
		placeholder="Select Date of Exam" value="<%=DataUtility.getDateString(dto.getExamDate())%>">
		</div>
		<label class="control-label text-danger  error_msg" for="inputError1">
		<%=ServletUtility.getErrorMessage("examDate", request)%></label>
	    </div>
		</div>
				
				</div>

				
				
				
		
		<div class="form-group" align="center">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<%if (dto.getId() > 0) { %><br>
        <button type="submit" class="btn btn-success" name="operation"
		value="<%=TimeTableCtl.OP_UPDATE%>"><span class="glyphicon glyphicon-check"></span> Update</button>
		<button type="submit" class="btn btn-primary" name="operation"
		value="<%=TimeTableCtl.OP_CANCEL%>"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
						<%
						} else {
					%><br>
		<button type="submit" class="btn btn-primary" name="operation"
		value="<%=TimeTableCtl.OP_SAVE%>"><span class="glyphicon glyphicon-check"></span> Save</button>&emsp;
		<button type="submit" class="btn btn-warning" name="operation"
		value="<%=TimeTableCtl.OP_RESET%>"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
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

	<div style="min-height: 200px">
		<%@ include file="Footer.jsp"%>
</body>
</html>