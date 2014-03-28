<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.ClientContext"  scope="application" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<% pageContext.setAttribute("engineInstance", System.getProperty("com.dexels.navajo.server.EngineInstance")); %>
  <script type="text/javascript">
    function getText() {
      return document.getElementById('nqlQuery').value;
   }
  </script>
<form method=get action="index.jsp">
<div class="info">
	<div><div style="width: 60px">Username:</div><input type="text" size="30" name="setupUsername" id="setupUsername"/></div>
	<div><div style="width: 60px">Password:</div><input type="password" size="30" name="setupPassword" id="setupPassword"/></div>
	<input type="hidden" name="updateUser" value="true">
	<input type="submit" name="submit" value="Update">
</div>
</form>
