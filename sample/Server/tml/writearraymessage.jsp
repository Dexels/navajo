<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.client.context.NavajoContext" scope="session" />

<div class="messagetree">
<table>
<tr>
<nav:message message="${msg}" messageIndex="0">
		<c:import url="tml/writearrayheader.jsp"/>
</nav:message>
</tr>
<c:forEach var="msg" items="${navajoContext.message.allMessages}">
	<nav:message message="${msg}">
		<c:import url="tml/writearrayelement.jsp"/>
	</nav:message>
</c:forEach>
</table>
</div>