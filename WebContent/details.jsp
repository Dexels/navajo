<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.tipi.appmanager.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@page import="com.dexels.navajo.tipi.projectbuilder.*"%>

<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<jsp:useBean id="manager" class="com.dexels.navajo.tipi.appmanager.ApplicationManager" scope="page" />
<jsp:useBean id="versionResolver" class="com.dexels.navajo.tipi.projectbuilder.VersionResolver" scope="page" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Tipi Application Store - <%= getServletContext().getInitParameter("serverTitle") %>  ( <%= getServletContext().getContextPath() %>) </title>
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
		manager.setCurrentApplication(request.getParameter("application"));
		ApplicationStatus aa = manager.getApplication();
		versionResolver.load(aa.getRepository());

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
            <li class="active"><strong>${manager.currentApplication}</strong></li>
 			<li><a href="repository.jsp?application=${manager.currentApplication}">Repository Overview</a></li>
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
          <div id="col1_content" class="clearfix">
            <h3><a href=".">Back to overview</a></h3>
			<div class="info">
				Details of ${manager.currentApplication}
			</div>

          </div>
        </div>
        <div id="col3">
          <div id="col3_content" class="clearfix">
			<c:set var="app" scope="page" value="${manager.application}"/>
			<div class="important">
			<h4>Profiles</h4>
				
				<div class="float_right">
					<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=clean&amp;destination=/details.jsp">Clean</a>
					<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=build&amp;destination=/details.jsp">Build</a>
					<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=download&amp;destination=/details.jsp">Download</a>
					<a href="index.jsp" onclick="if(confirm('Delete application: ${app.applicationName}?')) location.href = 'TipiAdminServlet?app=${app.applicationName}&amp;cmd=delete&amp;destination=/details.jsp'; ">Delete</a>
				</div>
				<table>
				<c:if test="${app.valid}">
					<c:forEach var="profile" items="${app.profiles}">
						<tr><td><a href="Apps/${app.applicationName}/${profile}.jnlp">${profile}</a></td><td>	<a href="editor.jsp?application=${app.applicationName}&amp;filePath=settings/profiles/${profile}.properties">Edit profile</a></td></tr>
					</c:forEach>
				</c:if>
				<c:if test="${!app.valid}">
					<c:forEach var="profile" items="${app.profiles}">
						<tr><td>${profile} (needs rebuild)</td><td>	<a href="editor.jsp?application=${app.applicationName}&amp;filePath=settings/profiles/${profile}.properties">Edit profile</a></td></tr>
					</c:forEach>
				</c:if>
				</table>
			</div>
			<div class="important">
			<h4>Settings</h4>
					<a href="editor.jsp?application=${app.applicationName}&amp;filePath=settings/tipi.properties">Edit tipi.properties</a>
					<a href="editor.jsp?application=${app.applicationName}&amp;filePath=settings/arguments.properties">Edit arguments.properties</a>
			
			</div>
			
			<div class="important">
			
								<c:if test="${app.buildType=='web'}">
			<h4>Run Echo App</h4>
						Echo app: <a href="${app.liveUrl}${app.applicationName}">Start application</a><br/>
			</div>

					</c:if>
			<div class="important">
				<h4>Extensions</h4>
				<table>
				
				<c:forEach var="ext" items="${app.extensions}">
					<tr>
					<c:set target="${versionResolver}" property="versionToken" value="${ext.name}"/>
						<td>					
							<a href="${app.repository}${versionResolver.versionToken}">${ext.name} (${ext.version})</a>
						</td>
						<td>					
							<a href="${manager.documentationRepository}?id=tipidoc:${ext.name}:details">Documentation</a><br/>
						</td>
					</tr>
				</c:forEach>
				</table>
		</div>
		<div class="important">
				<h4>Application source / resources</h4>
			<iframe name="" src="${manager.documentationRepository}?id=apps:${manager.currentApplication}:info" frameborder="0" scrolling="auto" width="100%" height="280" marginwidth="5" marginheight="5" ></iframe>
		</div>
	
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
