<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.proj3.ctl.*" %>

<%-- <%@page import="in.co.rays.proj3.ctl.*"%>
 --%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Online Result System</title>
<!--    favicon-->
<link rel="shortcut icon" href="theam_wel/image/fav-icon.png"
	type="image/x-icon">
<!-- Bootstrap -->
<!--   <link href="theam_wel/css/bootstrap.min.css" rel="stylesheet">

    <link href="theam_wel/css/bootstrap-theme.min.css" rel="stylesheet">

    <link href="theam_wel/css/font-awesome.min.css" rel="stylesheet">
 -->
<style type="text/css">
body {
	background-image: url("/ORSProject3/image/"),
		url("/ORSProject3/image/bg_forgetpass.jpg");
	background-size: cover;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-position: center;
}

.video-wrap {
	clip: rect(0, auto, auto, 0);
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
}

/*#video {
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
 */ /* video { 
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
} */

/* body{
background-color: blue

} */
</style>

<!--css-->
<link rel="stylesheet" href="theam_wel/css/style.css">
<!--  HTmL5 shim and Respond.js for IE8 support of HTmL5 elements and media queries
    WARNING: Respond.js doesn't work if you view the page via file://
    [if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script> -->
<!--   <![endif] -->
</head>

<body>

	<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div> -->

	<section class="hero-area-fix">
	<div class="hero-area" id="water">
		<div class="container">
			<div class="row">
				<div class="hero-text">

					<h1 align="CENTER">
						<img src="theam_wel/image/customLogo.png" width="368" height="157"
							border="0">
					</h1>

					<!--  <div class="video-background"></div>-->


					<div>
						<h3>Rays Technologies, Think IT Think Us</h3>
					</div>

					<!--    <div id="typed-strings">
                            <h3>Rays Technologies, Think IT Think Us</h3>
                            <h3>Online Web Application</h3>
                        </div>
                        <h3><span id="typed"></span></h3> 
                        <br> -->

					<a href="<%=ORSView.WELCOME_CTL%>" class="btn"><strong>Online
							Result System</strong></a>

				</div>
			</div>
		</div>
	</div>
	</section>


	<!--End Footer area-->
	<!--  <script src="theam_wel/js/jquery-2.2.4.min.js"></script> -->
	<!-- <script src="theam_wel/js/bootstrap.min.js"></script> -->


	<!--counter js-->

	<!-- <script src="theam_wel/vendores/typedjs/typed.min.js"></script>
  	<script src="theam_wel/vendores/ripples/jquery.ripples-min.js"></script>


    <script src="theam_wel/js/custom.js"></script>
 -->

</body>

</html>
