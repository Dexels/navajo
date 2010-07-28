<%@page errorPage="tmlerror.jsp" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tags/navajo.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
	<%
	  NavajoContext xx = (NavajoContext) pageContext.findAttribute("navajoContext");
	  xx.reset();
	%>
	<html><head>
  <meta http-equiv="Refresh" content="0; url=../index.jsp">
</head></html>
	
