<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.tipi.appmanager.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<jsp:useBean id="manager" class="com.dexels.navajo.tipi.appmanager.ApplicationManager" scope="page" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Tipi Application Store</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- add your meta tags here -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->
</head>
<body>
  <div class="page_margins">
    <div class="page">
      <div id="header">
        <div id="topnav">
          <!-- start: skip link navigation -->
<%
String res = request.getParameter("result");
if(res!=null) {
	out.write("<div class='warning'>"+URLDecoder.decode(res,"UTF-8")+"</div>");
}
%>

<%

	manager.setContext(getServletContext());
	String appFolder = getServletContext().getInitParameter("appFolder"); 
	if(appFolder==null) {
		appFolder = config.getServletContext().getRealPath(".");
	}
	manager.setAppsFolder(new File(appFolder));
%>
        </div>
        <h2>Tipi App Store</h2>
      </div>
      <div id="nav">
        <!-- skiplink anchor: navigation -->
        <a id="navigation" name="navigation"></a>
        <div class="hlist">
          <!-- main navigation: horizontal list -->
          <ul>
            <li class="active"><strong>Application Overview</strong></li>
           
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
          <div id="col1_content" class="clearfix">
            <h3>Usage</h3>
			<div class="info">
				This is an overview of all deployed Tipi applications on this server. It is a view on the file system on the server. 
				Feel free to edit the files on the server, or upload zipped applications, either created manually or by the TipiPlugin in Eclipse
				
			</div>
            <h3>Create a blank application</h3>
			<div class="info">
				<form name="input" action="TipiAdminServlet" method="get">Create new application: 
					<input type="text" name="app" /> 
					<input type="hidden" name="cmd" value="create" /> 
					<input type="submit" value="Create" />
				</form>
			</div>
            <h3>Upload a zipped application</h3>
			<div class="info">
				<form name="input" action="TipiAdminServlet?cmd=upload&amp;app=unknown" method="post" enctype="multipart/form-data">Application name: 
					<input type="text" name="appName" /><br/>
					Choose file:<br/>
					<input type="file" name="zippedFile" /> 
					<input type="hidden" name="cmd" value="upload" /> 
					<input type="submit" value="Upload" />
				</form>
			</div>

</div>
        </div>
        <div id="col3">
          <div id="col3_content" class="clearfix">
	<c:forEach var="app" items="${manager.applications}">
			<div class="important">
			<h4><a href="details.jsp?application=${app.applicationName}">${app.applicationName}</a></h4>
				
				<div class="float_right">
					<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=clean">Clean</a>
					<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=build">Build</a>
					<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=download">Download</a>
					<a href="index.jsp" onclick="if(confirm('Delete application: ${app.applicationName}?')) location.href = 'TipiAdminServlet?app=${app.applicationName}&amp;cmd=delete'; ">Delete</a>
				</div>
				<c:if test="${app.valid}">
				<c:forEach var="profile" items="${app.profiles}">
					<a href="${app.applicationName}/${profile}.jnlp">${profile}</a><br/>
				</c:forEach>
				</c:if>
				<c:if test="${!app.valid}">
				<c:forEach var="profile" items="${app.profiles}">
					<div>
					${profile}<br/>
					</div>
				</c:forEach>
				</c:if>
			
			</div>

	</c:forEach>

     </div>
          <!-- IE Column Clearing -->
          <div id="ie_clearing"> &#160; </div>
        </div>
      </div>
      <!-- begin: #footer -->
      <div id="footer">Powered by <a href="http://www.navajo.nl/">Navajo</a>
      </div>
    </div>
  </div>
</body>
</html>
