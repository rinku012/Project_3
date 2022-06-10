<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj3.ctl.UserCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj3.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@ include file="Header.jsp"%>

<html>
<head>
<title>User View</title>
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



 .table-hover tbody tr:hover td
    {
        background-color: #0064ff36;
    }   

.error_msg{ 
 margin-left: -62px;
}


</style>
</head>

<body background="/ORSProject3/image/bg1.jpg">

	<form action="<%=ORSView.USER_CTL%>" method="post">
	
	<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div></div> -->

		<div align="center" style="height: 420px;">

			<jsp:useBean id="dto" class="in.co.rays.proj3.dto.UserDTO"
				scope="request"></jsp:useBean>

			<%
				List l = (List) request.getAttribute("roleList");
			%>

			<div class="container-fluid">
				<div class="row">
				  <div class="col-xs-12 col-md-6 col-sm-12 col-lg-4 col-md-offset-4">
					
						<div class="panel panel-primary"
							style="margin-top: 10px; background-color: #DCDCDC;">
							<div class="panel-heading" style="background-color: #e9967a00;"
								align="center">

								<%
									if (dto.getId() > 0) {
								%>
								<b><h1>Update User</h1></b>
								<%
									} else {
								%>
								<b><h1>Add User</h1></b>
								<%
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


								<br> <input type="hidden" name="id"
									value="<%=dto.getId()%>"> <input type="hidden"
									name="createdBy" value="<%=dto.getCreatedBy()%>"> <input
									type="hidden" name="modifiedBy"
									value="<%=dto.getModifiedBy()%>"> <input type="hidden"
									name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">


								<div class="col-xs-12 col-sm-12 col-md-10 col-lg-12">

									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label text-info col-md-6">
											First Name<span style="color: red;">*</span>
										</label>

										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input type="text"
													class="form-control" name="firstName"
													placeholder="Enter First Name"
													value="<%=DataUtility.getStringData(dto.getFirstName())%>">
											</div>
											<label class="control-label text-danger  error_msg" for="inputError1">
											<%=ServletUtility.getErrorMessage("firstName", request)%></label>
										</div>
									</div>

						

									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Last Name<span style="color: red;">*</span>
										</label>
										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input type="text"
													class="form-control" name="lastName"
													placeholder="Enter Last Name"
													value="<%=DataUtility.getStringData(dto.getLastName())%>">
											</div>
											<label class="control-label text-danger error_msg" for="inputError1">
												<%=ServletUtility.getErrorMessage("lastName", request)%></label>
										</div>
									</div>

								

									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Login Id<span style="color: red;">*</span>
										</label>

										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-log-in"></i></span> <input type="text"
													class="form-control" name="login"
													placeholder="Enter Login ID"
													value="<%=DataUtility.getStringData(dto.getLogin())%>"
													<%=(dto.getId() > 0) ? "readonly" : ""%>>
											</div>
											<label class="control-label text-danger error_msg" for="inputError1">
												<%=ServletUtility.getErrorMessage("login", request)%></label>
										</div>
									</div>



									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Password<span style="color: red;">*</span>
										</label>

										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-lock" aria-hidden="true"></i></span> <input
													type="password" class="form-control" name="password"
													placeholder="Enter Password"
													value="<%=DataUtility.getStringData(dto.getPassword())%>">
											</div>
											<label class="control-label text-danger error_msg" for="inputError1">
												<%=ServletUtility.getErrorMessage("password", request)%></label>
										</div>
									</div>

						

									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Confirm Password<span style="color: red;">*</span>
										</label>

										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-lock" aria-hidden="true"></i></span> <input
													type="password" class="form-control" name="confirmPassword"
													placeholder="Enter Confirm Password"
													value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
											</div>
											<label class="control-label text-danger" for="inputError1" style="margin-left: -10px;">
												<%=ServletUtility.getErrorMessage("confirmPassword", request)%></label>
										</div>
									</div>

									
									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Gender<span style="color: red;">*</span>
										</label>

										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group"  placeholder="Enter Gender">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-user" aria-hidden="true"></i></span>
												<%
													HashMap map = new HashMap();
												  
												   
												    map.put("---Select---", "---Select---");
													map.put("Female", "Female");
													map.put("Male", "Male");
													String htmlList = HTMLUtility.getList("gender", dto.getGender(), map);
												%>
												<%=htmlList%>
											</div>
											<label class="control-label text-danger error_msg" for="inputError1">
												<%=ServletUtility.getErrorMessage("gender", request)%></label>
										</div>
									</div>

									
									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Role<span style="color: red;">*</span>
										</label>

										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-user"></i></span>
												<%=HTMLUtility.getList("roleId", String.valueOf(dto.getRoleId()), l)%>
											</div>
											<label class="control-label text-danger error_msg" for="inputError1">
												<%=ServletUtility.getErrorMessage("roleId", request)%></label>
										</div>
									</div>

							

									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Date of Birth<span style="color: red;">*</span>
										</label>

									<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-calendar"></i></span> <input
													type="text" class="form-control" name="dob" id="datepicker"
													readonly="readonly" placeholder="Enter Date of Birth"
													value="<%=DataUtility.getDateString(dto.getDob())%>">
											</div>
											<label class="control-label text-danger" for="inputError1"  style="margin-left: -49px;">
												<%=ServletUtility.getErrorMessage("dob", request)%></label>
										</div>
									</div>

								

									<div class="form-group" style="margin-left: 10%;">
										<label align="left" class="control-label col-md-6 text-info">
											Mobile No.<span style="color: red;">*</span>
										</label>

										<div class="col-md-12" style="margin-bottom: 20px;">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-earphone"></i></span> <input
													type="text" class="form-control" name="mobileNo"
													maxlength="10" placeholder="Enter Mobile Number"
													value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
											</div>
											<label class="control-label text-danger error_msg" for="inputError1">
												<%=ServletUtility.getErrorMessage("mobileNo", request)%></label>
										</div>
									</div>


								</div>



								<div class="form-group" align="center">
									<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
										<%
											if (dto.getId() > 0) {
										%><br>
										<button type="submit" class="btn btn-success" name="operation"
											value="<%=UserCtl.OP_UPDATE%>">
											<span class="glyphicon glyphicon-check"></span> Update
										</button>
										<button type="submit" class="btn btn-primary" name="operation"
											value="<%=UserCtl.OP_CANCEL%>">
											<span class="glyphicon glyphicon-remove"></span> Cancel
										</button>
										<%
											} else {
										%><br>
										<button type="submit" class="btn btn-primary" name="operation"
											value="<%=UserCtl.OP_SAVE%>">
											<span class="glyphicon glyphicon-check"></span> Save
										</button>
										&emsp;
										<button type="submit" class="btn btn-warning" name="operation"
											value="<%=UserCtl.OP_RESET%>">
											<span class="glyphicon glyphicon-refresh"></span> Reset
										</button>
										<%
											}
										%>


									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<br>
				<br>
			</div>
			
	</form>

	<div style="min-height: 200px">
		<%@ include file="Footer.jsp"%>
</body>
</html>