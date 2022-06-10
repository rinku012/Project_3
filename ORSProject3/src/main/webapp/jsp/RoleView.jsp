<%@page import="in.co.rays.proj3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj3.ctl.RoleCtl"%>
<%@page import="in.co.rays.proj3.ctl.BaseCtl"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <!--    favicon-->
    <link rel="shortcut icon" href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-icon">
<title>Role View</title>

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

</style>
</head>

<body background="/ORSProject3/image/bg_viewlist.jpg">
	<form action="<%=ORSView.ROLE_CTL%>" method="post">
	
	<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div> -->
	
	<%@ include file="Header.jsp"%>
	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.RoleDTO" scope="request"></jsp:useBean>

	<div class="container-fluid">      
		<div class="row">
			<div class="col-xs-12 col-md-6 col-sm-12 col-lg-4 col-md-offset-4">
				<div class="panel panel-primary" style="margin-top:10px; background-color: #DCDCDC;">
					<div class="panel-heading" style="background-color:#e9967a00" align="center">
		
	<%if (dto.getId() > 0) {%> 
	
	<b><h1>Update Role</h1></b> 
	
	<%}else{%> 
	
	<b><h1>Add Role</h1></b> 
	
	<%}%>
		
	</div>
		<div class="panel-body">
			<div align="center">
			
			<div class="alert alert-success" role="alert" style="width: 90%; margin-left: 0%; font-size: 150%"
				<%=ServletUtility.getSuccessMessage(request).equals("") ? "hidden" : ""%>>
				<b> <%=ServletUtility.getSuccessMessage(request)%></b>
			</div>
			
			<div class="alert alert-danger" role="alert"style="width: 90%; margin-left: 0%; font-size: 150%;"
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
					<label align="left" class="control-label text-info col-md-6">Name<span style="color: red;">*</span></label>

					<div class="col-md-12" style="margin-bottom: 10px;">
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
				<input type="text" class="form-control" name="name" placeholder="Enter Role Name" value="<%=DataUtility.getStringData(dto.getName())%>">
				
					</div>
						<label class="control-label text-danger"  for="inputError1"><%=ServletUtility.getErrorMessage("name", request)%></label>
					</div>
			</div> 

			
				<div class="form-group" style="margin-left: 10%;">
					<label align="left" class="control-label text-info col-md-6">Description<span style="color: red;">*</span></label>

					<div class="col-md-12" style="margin-bottom: 10px;">
						<div class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-align-justify"></i></span> 
				<input type="text" class="form-control" name="description" placeholder="Description Of Role" value="<%=DataUtility.getStringData(dto.getName())%>">
					
					</div>
					
				<label class="control-label text-danger"  for="inputError1"><%=ServletUtility.getErrorMessage("description", request)%></label>
				
				</div>
			</div> 

		</div>
                
             
		
		
		<div class="form-group" align="center">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<%if (dto.getId() > 0) { %>
					<br>
					<button type="submit" class="btn btn-success" name="operation"value="<%=RoleCtl.OP_UPDATE%>">
					<span class="glyphicon glyphicon-check"></span> Update</button>
		
					<button type="submit" class="btn btn-primary" name="operation"
					value="<%=RoleCtl.OP_CANCEL%>"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
				
				<%}else{%>
					<br>
					<button type="submit" class="btn btn-primary" name="operation" value="<%=RoleCtl.OP_SAVE%>">
					<span class="glyphicon glyphicon-check"></span> Save</button>&emsp;
		
					<button type="submit" class="btn btn-warning" name="operation" value="<%=RoleCtl.OP_RESET%>">
					<span class="glyphicon glyphicon-refresh"></span> Reset</button>
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
    <br><br>
    <%@ include file="Footer.jsp"%>
</body>
</html>
