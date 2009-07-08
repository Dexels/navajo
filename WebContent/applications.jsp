<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.dexels.navajo.tipi.appmanager.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<jsp:useBean id="manager"
	class="com.dexels.navajo.tipi.appmanager.ApplicationManager"
	scope="page" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tipi applications deployed here:</title>
</head>
<body>
<%
String res = request.getParameter("result");
if(res!=null) {
	out.write("<div class='servermessage'>Result: "+URLDecoder.decode(res,"UTF-8")+"</div>");
}

%>
<h3>Tipi App Store</h3>
<%

	manager.setContext(getServletContext());
	String appFolder = getServletContext().getInitParameter("appFolder"); 
	if(appFolder==null) {
		appFolder = config.getServletContext().getRealPath(".");
	}
	manager.setAppsFolder(new File(appFolder));
%>

<ul>
	<c:forEach var="app" items="${manager.applications}">
		<li bgcolor="#faa">
			${app.applicationName}  ${app.profiles}
				<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=clean">Clean</a>
				<a href="TipiAdminServlet?app=${app.applicationName}&amp;cmd=build">Build</a>
				<c:forEach var="profile" items="${app.profiles}">
					<a href="${app.applicationName}/${profile}.jnlp">${profile}</a>
				</c:forEach>
		</li>
	</c:forEach>
<ul>
</body>

<%@page import="java.net.URLDecoder"%></html>