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
<h3>Tipi App Store</h3>
<%
	manager.setContext(getServletContext());
	String appFolder = getServletContext().getInitParameter("appFolder"); 
	if(appFolder==null) {
		appFolder = config.getServletContext().getRealPath(".");
	}
	manager.setAppsFolder(new File(appFolder));
%>

<table>
	<c:forEach var="app" items="${manager.applications}">
		<tr>
			<td>${app.applicationName}</td>
				<td><a href="${app.applicationLink}">Start</a></td>
				<td><a href="${app.applicationLink}?build=true&amp;clean=true">Clean &amp; Start</a></td>
		</tr>
	</c:forEach>
</table>

</body>
</html>