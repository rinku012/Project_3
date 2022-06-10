<%@page import="in.co.rays.proj3.ctl.GetMarksheetCtl"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.util.DataUtility"%>
<%@page import="in.co.rays.proj3.model.ModelFactory"%>
<%@page import="in.co.rays.proj3.model.CourseModelInt"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.MarksheetDTO"
			scope="request"></jsp:useBean>
	

<html>
<head>
<title>Get Maksheet</title>
<!--    favicon-->
    <link rel="shortcut icon" href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-i">

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


td,tr,th{
	background-color:white;
	
}
</style>
</head>

<body background="/ORSProject3/image/a135.jpg">

    <%@include file="Header.jsp"%>
	
	<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
	
	<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div> -->
    
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    
        <div class="container">
        <div class="row">
        <div class="panel" style="background-color: rgba(248, 222, 210, 0.85); margin-bottom: 150px;" >
        <div class="panel-body">
        <div align="center">
         
         <H2>  <span class="glyphicon glyphicon-list"></span><b> Get Marksheet</b> </H2>
         <hr style="height:2px; color:#000000;">
       </div>
       
        <div class="text-center" >
            
              
               <%if(ServletUtility.getSuccessMessage(request).length()>0){ %>
			
			<div class="text-center col-md-offset-4">
			<h2 style="position: absolute; ;margin-top: 10px;top: 240px;"">
				<font color="green"><i style="margin-left: 25px;"><%=ServletUtility.getSuccessMessage(request)%></i></font>
			</h2></div>
			
					
          <%}else{ %>
			
			  <div class="text-center col-md-offset-4" >
			<h2 style="position: absolute ;margin-top: 10px;top: 240px;" >
			<font color="brown"><i  style="margin-left: 25px;"><%=ServletUtility.getErrorMessage(request)%></i>
			</font></h2></div>
			<%} %>
                 </div>
	
	<br><br><br>
	
		  <div class="container-fluid text-center">
           <div class="form-inline" >
				<div class="form-group  text-center">
					<label class="control-label"  for="rollNo"> Roll No. :</label>
					<input type="text"  class="form-control" name="rollNo" size=15
					placeholder="Enter Role Name" value="<%=ServletUtility.getParameter("rollNo", request)%>">
				</div>
				
				&emsp;&emsp;
		
										
				<div class="form-group">
				<button type="submit" name="operation" class=" form-control btn btn-primary" 
				 value="<%=GetMarksheetCtl.OP_GO%>">
                <span class="glyphicon glyphicon-search"></span> Go </button>
       
			     <button type="submit" name="operation" class=" form-control btn btn-warning" 
			     value="<%=GetMarksheetCtl.OP_RESET%>" >
                 <span class="	glyphicon glyphicon-refresh"></span> Reset </button>
        
        </div>
        </div><hr><br><br>
        
        <% 		
        if (dto.getRollNo() != null && dto.getRollNo().trim().length() > 0) {

        	String grade = null;
			String result = null;
			long phyMarks = DataUtility.getLong(dto.getPhysics());
			long chemMarks = DataUtility.getLong(dto.getChemistry());
			long MathMarks = DataUtility.getLong(dto.getMaths());
			long totalMarks = (phyMarks + chemMarks + MathMarks);
		
			float percentage=(float)totalMarks/3;
        	percentage=percentage*100;
        	percentage=Math.round(percentage);
        	percentage=percentage/100; 
        	
        	if (phyMarks >= 35 && chemMarks >= 35 && MathMarks >= 35) {
				if (totalMarks >= 195) {
					grade = "A";
					result = "Pass";
				} else if (totalMarks < 195 && totalMarks >= 150) {
					grade = "B";
					result = "Pass";
				} else {
					grade = "c";
					result = "Pass";
				}

			} else {
				grade = "D";
				result = "Fail";
			}
        	
        	String div = null;
			if (percentage >= 60) {
				div = "First";
			} else if (percentage >= 45 && percentage < 60) {
				div = "Second";
			} else if (percentage >= 33 && percentage < 45) {
				div = "Third";
			}else if (percentage <33){
				div="Fail";
			}
		    	
        %>
							
		<div class="box-body table-responsive">
			<table  class="table  table-bordered table-striped">
			 <tbody>
				<tr>
					<td colspan="4" bgcolor="#BFC9CA" align="center"><b><i style="font-size: x-large;">Provisional Marksheet</i></b></td>
				</tr>
				<tr>
					<td style="text-align: center;">Roll No. :</td>
					<td colspan="3"><%=DataUtility.getStringData(dto.getRollNo())%></td>
				</tr>
				<tr>
					<td style="text-align: center;" >Name :</td>
					<td colspan="3" class="space"><%=DataUtility.getStringData(dto.getName())%></td>
				</tr>
				<tr>
					<td style="text-align: center;"><b>Subject:</b></td>
					<td style="text-align: center;"><b>Maximum Marks:</b></td>
					<td style="text-align: center;"><b>Minimum Marks:</b></td>
					<td style="text-align: center;"><b>Obtained Marks:</b></td>
				</tr>
				<tr>
					<td style="text-align: center;">Physics</td>
					<td style="text-align: center;">100</td>
					<td style="text-align: center;">35</td>
					<td style="text-align: center;"><%=DataUtility.getStringData(dto.getPhysics())%></td>
				</tr>
				<tr>
					<td style="text-align: center;">Chemistry</td>
					<td style="text-align: center;">100</td>
					<td style="text-align: center;">35</td>
					<td style="text-align: center;"><%=DataUtility.getStringData(dto.getChemistry())%></td>
				</tr>
				<tr>
					<td style="text-align: center;">Math</td>
					<td style="text-align: center;">100</td>
					<td style="text-align: center;">35</td>
					<td style="text-align: center;"><%=DataUtility.getStringData(dto.getMaths())%></td>
				</tr>
				<tr>
					<td style="text-align: center;">Total</td>
					<td style="text-align: center;">300</td>
					<td style="text-align: center;"></td>
					<td style="text-align: center;"><%=totalMarks%></td>
				</tr>
				
				
			  </tbody>
			</table>
			</div>
			
				<div class="box-body table-responsive">
					<table  class="table  table-bordered table-striped">
			
				<tr >
					<td style="text-align: center;">Result</th>
					<%
						if ("PASS".equalsIgnoreCase(result)) {
					%>
					<td align="center"><font color="green"><%=result%></font></td>
					<td style="text-align: center;">Grade:</th>
					<td align="center"><font color="green"><%=grade%></font></td>
					<%
						} else {
					%>

					<td align="center"><font color="red"><%=result%></font></td>
					<td style="text-align: center;">Grade:</th>
					<td align="center"><font color="green"><%=grade%></font></td>
					<%
						}
					%>
					
					<td style="text-align: center;">Division:</th>
					<td style="text-align: center;"><%=div%></td>
					
					<td style="text-align: center;">Percentage:</th>
					<td align="center"><%=percentage%>%</td>
					
				</tr>
			</table>
			<%
				}
			%>
		</div>
		
		<hr>
		
   
             </form>
    
    <br>
    <%@include file="Footer.jsp"%>
</body>
</html>
