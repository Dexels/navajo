<%@page import="com.dexels.navajo.osgi.JspComponent"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true"%>
<jsp:useBean id="navajoContext"
	type="com.dexels.navajo.client.context.ClientContext"
	scope="application" />
	<c:choose>
		<c:when test="${header['X-Navajo-Instance'] == null }">
			<c:forEach items="${applicationScope['localClients']}" var="entry">
				<a href="index.jsp?instance=${entry.key}">${entry.key}</a>
			</c:forEach>
		</c:when>
		<c:otherwise>
		${header['X-Navajo-Instance']}
		</c:otherwise>
	</c:choose>


