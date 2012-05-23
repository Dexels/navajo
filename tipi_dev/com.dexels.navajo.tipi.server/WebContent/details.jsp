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
<title><c:import url="parts/apptitle.jsp"></c:import> - <%= getServletContext().getInitParameter("serverTitle") %>  ( <%= getServletContext().getContextPath() %>) </title>
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
			<c:set var="app" scope="page" value="${manager.application}"/>

        </div>
        <h2><c:import url="parts/apptitle.jsp"></c:import></h2>
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
			<h3>Actual path</h3>
			<div class="info">
			${manager.application.realPath}
			</div>
			<h3>XSD File</h3>
			<div class="info">
				If your editor supports it, you can download an XSD file to aid you.
				<p><a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=xsd">Download XSD</a></p>
				<p>Put it in the 'tipi' folder of your application and make sure it is called 'tipi.xsd'</p>
				<p>If you add extensions or update them, you will need to download this file again.</p>
			</div>
			<h3>XSD File</h3>
			<div class="info">
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
					<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=downloaddeploy">Download deploy</a>
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
				<a href="${app.getManagerUrl(app.currentDeploy)}">Manage application</a> (Auth. may be required)

			<div class="important">
			<h4>Deployments (active: ${app.currentDeploy })</h4>
					<table>
						<thead>
							<tr>
							<th>
								Deployment
							</th>
							<th>
								Edit
							</th>
			
							</tr>
						</thead>
						<c:forEach var="deploy" items="${app.deployments}">
							<tr>
							<td>
								${deploy}
							</td>
							<td>
								<a href="editor.jsp?application=${app.applicationName}&amp;filePath=settings/deploy/${deploy}.properties">Edit deployment</a>
							</td>
							</tr>
						</c:forEach>
					</table>
			</div>

			
			<div class="important">
			<h4>Settings</h4>
					<a href="editor.jsp?application=${app.applicationName}&amp;filePath=settings/tipi.properties">Edit tipi.properties</a>
					<a href="editor.jsp?application=${app.applicationName}&amp;filePath=settings/arguments.properties">Edit arguments.properties</a>
			
			</div>
			
			<div class="important">
			<c:if test="${app.buildType=='web'}">
				<h4>Open Echo Application</h4>
					<table>
						<thead>
							<tr>
							<th>
							</th>
							<c:forEach var="deploy" items="${app.deployments}">
								<c:choose>
									<c:when test="${deploy == app.currentDeploy}">
										<th>
											<b>${deploy}</b>
										</th>
									</c:when>
									<c:otherwise>
										<th>
											${deploy}
										</th>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</tr>
						</thead>
						<c:forEach var="profile" items="${app.profiles}">
							<tr>
							<td>
								${profile}
							</td>

								<c:forEach var="deploy" items="${app.deployments}">
									<td>
									<c:choose>
										<c:when test="${deploy == app.currentDeploy}">
											<a href="${app.propertyEntry(deploy,profile,'liveUrl') }"><b>${deploy}/${profile}</b></a>								
										</c:when>
										<c:otherwise>
											<a href="${app.propertyEntry(deploy,profile,'liveUrl') }">${deploy}/${profile}</a>								
										</c:otherwise>
									</c:choose>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
			</c:if>
			</div>



			<div class="important">
			<c:if test="${app.buildType=='web'}">
				<h4>Deploy Echo Application</h4>
					<table>
						<thead>
							<tr>
							<th>
							</th>
							<c:forEach var="deploy" items="${app.deployments}">
								<c:choose>
									<c:when test="${deploy == app.currentDeploy}">
										<th>
											<b>${deploy}</b>
										</th>
									</c:when>
									<c:otherwise>
										<th>
											${deploy}
										</th>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							</tr>
						</thead>
						<c:forEach var="profile" items="${app.profiles}">
							<tr>
							<td>
								${profile}
							</td>

								<c:forEach var="deploy" items="${app.deployments}">
									<td>
									<c:choose>
										<c:when test="${deploy == app.currentDeploy}">
											<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=build&amp;deploy=${deploy}&amp;profile=${profile }&amp;destination=/details.jsp"><b>${deploy}/${profile}</b></a>								
										</c:when>
										<c:otherwise>
											<a href="${app.propertyEntry(deploy,profile,'liveUrl') }">${deploy}/${profile}</a>								
										</c:otherwise>
									</c:choose>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
			</c:if>
			</div>



			<div class="important">
				<h4>Extensions</h4>
				<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Documentation</th>
					</tr>
				</thead>
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
