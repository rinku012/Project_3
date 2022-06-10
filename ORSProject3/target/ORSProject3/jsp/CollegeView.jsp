<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj3.ctl.CollegeCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>


<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College View</title>
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

label {
	position: absolute;
}
</style>
</head>

<body background="/ORSProject3/image/bg_viewlist.jpg">

	<form action="<%=ORSView.COLLEGE_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>
		
		<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div> -->

		<jsp:useBean id="dto" class="in.co.rays.proj3.dto.CollegeDTO"
			scope="request"></jsp:useBean>

<div class="container-fluid">      
		<div class="row">
		<div class="col-xs-12 col-md-6 col-sm-12 col-lg-4 col-md-offset-4">
			<div class="panel panel-primary" style="margin-top:10px; background-color: #DCDCDC;">
				<div class="panel-heading" style="background-color:#e9967a00;" align="center">
		
	<%
 	if (dto.getId() > 0) {
 %> <b><h1>Update College</h1></b> <%
 	} else {
 %> <b><h1>Add College</h1></b> <%
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
				<label align="left" class="control-label text-info col-md-6">
				College Name<span style="color: red;">*</span></label>

				 <div class="col-md-12" style="margin-bottom: 25px;">
				<div class="input-group">
				<span class="input-group-addon">
				<i class="glyphicon glyphicon-copyright-mark"></i></span> 
				<input type="text" class="form-control" name="name"
				placeholder="Enter College Name" value="<%=DataUtility.getStringData(dto.getName())%>">
				</div>
				<label class="control-label text-danger"  for="inputError1">
				<%=ServletUtility.getErrorMessage("name", request)%></label>
				</div>
				</div> 

				
					
          		<div class="form-group" style="margin-left: 10%;">
				<label align="left" class="control-label text-info col-md-6">
				Address<span style="color: red;">*</span></label>

				 <div class="col-md-12" style="margin-bottom: 25px;">
				<div class="input-group">
				<span class="input-group-addon">
				<i class="glyphicon glyphicon-tasks"></i></span> 
				<input type="text" class="form-control" name="address"
				placeholder="Enter College Address" value="<%=DataUtility.getStringData(dto.getAddress())%>">
				</div>
				<label class="control-label text-danger"  for="inputError1">
				<%=ServletUtility.getErrorMessage("address", request)%></label>
				</div>
				</div> 

				
                        
                <div class="form-group" style="margin-left: 10%;">
				<label align="left" class="control-label text-info col-md-6">
				City<span style="color: red;">*</span></label>

				 <div class="col-md-12" style="margin-bottom: 25px;">
				<div class="input-group">
				<span class="input-group-addon">
				<i class="glyphicon glyphicon-tasks"></i></span> 
				<input type="text" class="form-control" name="city"
				placeholder="Enter City" value="<%=DataUtility.getStringData(dto.getCity())%>">
				</div>
				<label class="control-label text-danger"  for="inputError1">
				<%=ServletUtility.getErrorMessage("city", request)%></label>
				</div>
				</div> 

				
					
				<div class="form-group" style="margin-left: 10%;">
				<label align="left" class="control-label text-info col-md-6">
				State<span style="color: red;">*</span></label>

				 <div class="col-md-12" style="margin-bottom: 25px;">
				<div class="input-group">
				<span class="input-group-addon">
				<i class="glyphicon glyphicon-tasks"></i></span> 
				<input type="text" class="form-control" name="state"
				placeholder="Enter State" value="<%=DataUtility.getStringData(dto.getState())%>">
				</div>
				<label class="control-label text-danger"  for="inputError1">
				<%=ServletUtility.getErrorMessage("state", request)%></label>
				</div>
				</div> 

				               
                
                <div class="form-group" style="margin-left: 10%;">
				<label align="left" class="control-label col-md-6 text-info">
				Phone No.<span style="color: red;">*</span></label>

				 <div class="col-md-12" style="margin-bottom: 25px;">
				<div class="input-group">
				<span class="input-group-addon">
				<i class="glyphicon glyphicon-earphone"></i></span> 
				<input type="text" class="form-control" name="phoneNo" maxlength="10"
				placeholder="Enter Phone Number" value="<%=DataUtility.getStringData(dto.getPhoneNo())%>">
				</div>
				<label class="control-label text-danger" for="inputError1">
				<%=ServletUtility.getErrorMessage("phoneNo", request)%></label>
				</div>
	    		</div>
                
                </div>
                    
                    
	
		
		<div class="form-group" align="center">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<%if (dto.getId() > 0) { %><br>
        <button type="submit" class="btn btn-success" name="operation"
		value="<%=CollegeCtl.OP_UPDATE%>"><span class="glyphicon glyphicon-check"></span> Update</button>
		<button type="submit" class="btn btn-primary" name="operation"
		value="<%=CollegeCtl.OP_CANCEL%>"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
						<%
						} else {
					%><br>
		<button type="submit" class="btn btn-primary" name="operation"
		value="<%=CollegeCtl.OP_SAVE%>"><span class="glyphicon glyphicon-check"></span> Save</button>&emsp;
		<button type="submit" class="btn btn-warning" name="operation"
		value="<%=CollegeCtl.OP_RESET%>"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
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