<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<form name="input" action="TipiAdminServlet?cmd=upload&amp;app=unknown" method="post" enctype="multipart/form-data">Upload binary: 
	Choose file:<br/>
	<input type="file" name="binaryData" /> 
	<input type="hidden" name="cmd" value="upload" /> 
	<input type="hidden" name="destination" value="/message.jsp" /> 
	<input type="submit" value="Upload" />
</form>