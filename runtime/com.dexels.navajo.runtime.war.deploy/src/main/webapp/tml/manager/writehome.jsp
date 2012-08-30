<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tld/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />	  
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<h3>Navajo Configuration</h3>
<c:choose>
	<c:when test="${param['configfile']!=null}">
		<c:import url="tml/manager/showconfig.jsp"></c:import>
	</c:when>
	<c:otherwise>
		<h2>Welcome to Navajo</h2>
		Check documentation, tips, and cool gadgets at <a href="http://www.navajo.nl">www.navajo.nl</a> 
	</c:otherwise>
</c:choose>