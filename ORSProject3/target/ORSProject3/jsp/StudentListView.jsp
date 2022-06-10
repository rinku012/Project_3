<%@page import="in.co.rays.proj3.ctl.StudentListCtl"%>
<%@page import="in.co.rays.proj3.util.ServletUtility"%>
<%@page import="in.co.rays.proj3.dto.StudentDTO"%>
<%@page import="in.co.rays.proj3.model.ModelFactory"%>
<%@page import="in.co.rays.proj3.model.StudentModelInt"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

	<jsp:useBean id="dto" class="in.co.rays.proj3.dto.StudentDTO" scope="request"></jsp:useBean>
	<jsp:useBean id="model" class="in.co.rays.proj3.model.StudentModelHibImpl" scope="request"></jsp:useBean>
	

<html>
<head>
<title>Student List</title>
<!--    favicon-->
    <link rel="shortcut icon" href="/ORSProject3/theam_wel/image/fav-icon.png" type="image/x-i">

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
</style>
</head>

<body background="/ORSProject3/image/bg_theam.jpg">

    <%@include file="Header.jsp"%>
	
	<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
	
	<% 
     List studentlist= (List)request.getAttribute("stdnt");
	%>
	
	<!-- <div class="video-wrap">
		<div id="video">
		<video id="bgvid" autoplay loop muted>
		<source src="/ORSProject3/stars.mp4"" type="video/mp4">
		</video></div>
     -->
    <br>
        <div class="container">
        <div class="row">
        <div class="panel" style="background-color: rgba(248, 222, 210, 0.85); margin-bottom: 150px;" >
        <div class="panel-body">
        <div align="center">
         
         <H2>  <span class="glyphicon glyphicon-list"></span><b> Student List</b> </H2>
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
					<label class="control-label"  for="firstName">First Name :</label>
					<input type="text"  class="form-control" name="firstName" size=15
					placeholder="Enter First Name" value="<%=ServletUtility.getParameter("firstName", request)%>">
				</div>&emsp;&emsp;
				
				<div class="form-group  text-center">
					<label class="control-label"  for="lastName">Last Name :</label>
					<input type="text"  class="form-control" name="lastName" size=15
					placeholder="Enter Last Name" value="<%=ServletUtility.getParameter("lastName", request)%>">
				</div>&emsp;&emsp;
				
				
				<div class="form-group text-center">
					<label class="control-label" for="email">Email Id :</label>
				
					<input type="text" name="email" class="form-control" 
					placeholder="Enter Login Id" size=15 value="<%=ServletUtility.getParameter("email", request)%>">
				</div>
			
			&emsp;&emsp;
			
				<div class="form-group">
				<button type="submit" name="operation" class=" form-control btn btn-primary" 
				 value="<%=StudentListCtl.OP_SEARCH%>">
                <span class="glyphicon glyphicon-search"></span> Search </button>
       
			     <button type="submit" name="operation" class=" form-control btn btn-warning" 
			     value="<%=StudentListCtl.OP_RESET%>" >
                 <span class="	glyphicon glyphicon-refresh"></span> Reset </button>
        
        </div>
        </div><hr>
        
        <% if (userdto.getRoleId() == RoleDTO.ADMIN || userdto.getRoleId() == RoleDTO.FACULTY) { %>
        			<div style="float:right">
				 <div class="col-sm-3" style="margin-left: 4%;">
      <button type="submit" name="operation" value="<%=StudentListCtl.OP_DELETE%>"
	  class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span> 
	  <%=StudentListCtl.OP_DELETE%> </button></div>
			</div>
			<div style="float:left">
				<div class="col-sm-3" >
    <button type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>"
	class="btn btn-primary"> <span class="glyphicon glyphicon-plus"></span> 
	<%=StudentListCtl.OP_NEW%> </button></div>
			</div>
			
        </div>			
	<%}%>		
				<br>
				
				            		
		<%List list=ServletUtility.getList(request);
			if(list==null || list.size()==0){%>
				<table align="center">
				<tr>
					<td>
					 
					  <button type="submit" name="operation" class=" form-control btn btn-warning" 
			     value="<%=StudentListCtl.OP_BACK%>" style=" width: 150px; height: 47px; font-size: 16px; background-color: gray;">
                 <span style="margin-right: 7px;" class="	glyphicon glyphicon-folder-open"></span>  Back </button>
                 
					</td>
			
				</tr>
			</table>
			
			<%}else{ %>  
			
			
			<div class="box-body table-responsive">
					
               <table  class="table  table-bordered table-striped table-hover">
              <thead>
                   <tr>
					 <th style="text-align:center;">
					 <input type="checkbox" id="mainbox"
						onchange="selectAll(this)"> Select All</th>
                     <th style="text-align: center;">S.No.</th>
                     <th style="text-align: center;">First Name</th>
                     <th style="text-align: center;">Last Name</th>
                     <th style="text-align: center;">Date of Birth</th>
                     <th style="text-align: center;">Mobile No</th>
                     <th style="text-align: center;">Email Id</th>
                     <th style="text-align: center;">Edit</th>
                              
                   </tr>
                   </thead>
			        
                

                <%
                StudentModelInt s = ModelFactory.getInstance().getStudentModel();
				List l = s.list();
				int count = l.size();
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize);

                     list = ServletUtility.getList(request);
                    Iterator<StudentDTO> it = list.iterator();
                    while (it.hasNext()) {
                        dto = it.next();
            
                 %>
                
                <tbody>
                <tr>
					<td align="center"><input type="checkbox" name="ids" 
					 value="<%=dto.getId()%>"></td>
					
					<td align="center"><%=++index%></td>
					<td align="center"><%=dto.getFirstName()%></td>
					<td align="center"><%=dto.getLastName()%></td>
					<td align="center"><%=dto.getDob()%></td>
					<td align="center"><%=dto.getMobileNo()%></td>
					<td align="center"><%=dto.getEmail()%></td>

					
					<td style="size: 20%; text-align: center;">
						
					<a href="StudentCtl?id=<%=dto.getId()%>">
					<span class="glyphicon glyphicon-edit"></span></a>
					
					</td>
					
	
										
					
					
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
			<input type="hidden" name="pageSize" value="<%=pageSize%>">
				
				</tr>
				</tbody>
				<%
                    }
                %>
            	
			
			</table>
			</div>
		
		<div>
			
		<div style="float:right">
				
        <div class="col-sm-4"">
        <button type="submit" name="operation" value="<%=StudentListCtl.OP_NEXT%>"<%=(index == count || dto.getId()==0 || list.size() < pageSize) ? "disabled" : ""%>  class="btn btn-primary" 
        ><%=StudentListCtl.OP_NEXT%> <span class="glyphicon glyphicon-chevron-right"></span> 
        </button></div>

			</div>
			
			<div style="float:left">
				 
  <div class="col-sm-4"">
  <button type="submit" name="operation"
	value="<%=StudentListCtl.OP_PREVIOUS%>"<%=(pageNo == 1) ? "disabled" : ""%>   class="btn btn-primary">
	<span class="glyphicon glyphicon-chevron-left"></span> <%=StudentListCtl.OP_PREVIOUS%> </button> 
	 </div>

			</div>
		</div><hr>	
			
			<%
                    }
                %>
             </form>
    
    <br>
    <%@include file="Footer.jsp"%>
</body>
</html>
