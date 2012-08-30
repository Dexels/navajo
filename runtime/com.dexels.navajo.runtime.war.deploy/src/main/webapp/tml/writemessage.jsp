<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<div class="important">
	
	<h4><a name="${navajoContext.messagePath}">${navajoContext.message.name}</a></h4>
	
	<c:choose>
		<c:when test="${navajoContext.message.type == 'array'}">
				<c:import url="tml/writearraymessage.jsp"/>
		</c:when>
		<c:otherwise>
			<c:forEach var="msg" items="${navajoContext.message.allMessages}">
				<nav:message message="${msg}">
					<c:import url="tml/writemessage.jsp"/>
				</nav:message>
			</c:forEach>
			<c:forEach var="prop" items="${navajoContext.message.allProperties}">
				<nav:property property="${prop}">
					<c:import url="tml/writeproperty.jsp"/>
				</nav:property>
			</c:forEach>
		</c:otherwise>		
	</c:choose>
</div>