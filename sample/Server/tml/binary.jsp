<?xml version="1.0" standalone="no"?>
<%@ page language="java" contentType="image/svg+xml; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.document.*"%>
<%@page import="com.dexels.navajo.document.types.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tags/navajo.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />

<%
	
//	request
//

Property p =navajoContext.parsePropertyPath((String)request.getParameter("path"));
	if(p!=null) {
		Object value = p.getTypedValue();
		if(value instanceof Binary) {
			Binary b = (Binary)value;
	//		out.write("Length: "+b.getLength()+" mime: "+b.getMimeType());
			response.setContentType(b.getMimeType());
			response.setContentLength((int)b.getLength());
			b.write(response.getOutputStream());

		}
	}
%>