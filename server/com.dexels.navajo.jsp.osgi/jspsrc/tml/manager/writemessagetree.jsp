<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.ClientContext"  scope="application" />


<div class="messagetree">
<a href="#${navajoContext.messagePath}">${navajoContext.message.name}</a>
<c:forEach var="msg" items="${navajoContext.message.allMessages}">
	<nav:message message="${msg}">
		<c:import url="tml/manager/writemessagetree.jsp"/>
	</nav:message>
</c:forEach>
</div>