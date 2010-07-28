<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />

<div class="messagetree">
<a href="#${navajoContext.messagePath}">${navajoContext.message.name}</a>
<c:forEach var="msg" items="${navajoContext.message.allMessages}">
	<nav:message message="${msg}">
		<c:import url="tml/manager/writemessagetree.jsp"/>
	</nav:message>
</c:forEach>
</div>