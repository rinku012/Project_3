<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.rays.proj3.dto.*" %>
<%@page import="in.co.rays.proj3.ctl.ORSView" %>
<html>

<head>

 <!--    favicon-->
    <link rel="shortcut icon" href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-icon">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to ORS</title>

<style type="text/css">
/* body {
	background-image: url("/ORSProject3/image/overlay.png"), url("/ORSProject3/image/banner.jpg");
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
} */

/* .video-wrap {
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
} */

.row, .container-fluid {
margin-left: 0px!important;
margin-right: 0px!important;
}

</style>

</head>
  
<body class="img-responsive" background="/ORSProject3/image/a113.jpg" style ="background-size:100%100%;>
<%@ include file="Header.jsp"%>
<form action="<%=ORSView.WELCOME_CTL%>">

<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div> -->
 
         <br><br><br><br><br><br>
         <br><br><br>
          <br><br><br><br><br><br>
         
		<div class="container">
		<div class="col-md-12" align="center">
		<h1 align="center">
		<font style="font-size:150%;color:;" color="">
		<p class="text-white"><b><i><strong>Welcome To ORS</strong></i></b></p></font>
		</h1>
		</div>
		</div>
		      <%
                        UserDTO user1Dto = (UserDTO) session.getAttribute("user");
                        if (user1Dto != null) {
                            if (user1Dto.getRoleId() == 2) {
                    %>
           <h2 align="Center" style="margin-top: 1%">
            <a href="<%=ORSView.GET_MARKSHEET_CTL%>"><H2 style="color: brown "><b><i>Click here to see your Marksheet</i></b></H2> </a>
        </h2>

        <%
                            }
                        }
                     %>


    </form>

</body>
     <%@ include file="Footer.jsp"%> 
</html>
