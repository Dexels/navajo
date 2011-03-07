<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<c:if test="${param['service'] != null && param['service']!=''}">
	<h2> ${navajoContext.navajoName}</h2>
					
	<c:forEach var="msg" items="${navajoContext.navajo.allMessages}">
		<nav:message message="${msg}">
			<c:import url="tml/writemessage.jsp"/>
		</nav:message>
	</c:forEach>
</c:if>	