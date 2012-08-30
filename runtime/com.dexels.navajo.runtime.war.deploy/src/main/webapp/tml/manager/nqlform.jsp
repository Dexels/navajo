<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<% pageContext.setAttribute("engineInstance", System.getProperty("com.dexels.navajo.server.EngineInstance")); %>
  <script type="text/javascript">
    function getText() {
      return document.getElementById('nqlQuery').value;
   }
  </script>
<div class="info">
	Query:<br/>
	<input type="text" size="30" name="query" id="nqlQuery"/>
	<input type="button" name="back" value="Send" class="input" onClick="window.open('Nql?username=${serverContext.clientSettings['username']}&password=${serverContext.clientSettings['password']}&query='+getText());">
</div>
