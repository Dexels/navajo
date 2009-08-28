<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="../WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />	  
<ul>
<li>
<a href="navajotester.jsp?service=InitLocation">InitLocation</a>
</li>
<li>
<a href="navajotester.jsp?service=InitNavajoDemo">InitNavajoDemo</a>
</li>
<li>
<a href="navajotester.jsp?service=InitNavajoLogon">InitNavajoLogon</a>
</li>
<li>
<a href="navajotester.jsp?service=InitNavajoStatus">InitNavajoStatus</a>
</li>
<li>
<a href="navajotester.jsp?service=InitTestPropertyTypes">InitTestPropertyTypes</a>
</li>
<li>
<a href="navajotester.jsp?service=InitCreateDatabase">InitCreateDatabase</a>
</li>
</ul>