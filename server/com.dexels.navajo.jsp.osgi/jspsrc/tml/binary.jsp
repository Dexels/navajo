<%@ page language="java" contentType="image/svg+xml; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.document.*"%>
<%@page import="com.dexels.navajo.document.types.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.ClientContext"  scope="application" />

<%
Property p =navajoContext.parsePropertyPath((String)request.getParameter("path"));
	if(p!=null) {
		Object value = p.getTypedValue();
		if(value instanceof Binary) {
			Binary b = (Binary)value;
			response.setContentType(b.getMimeType());
			response.setContentLength((int)b.getLength());
			b.write(response.getOutputStream());

		}
	}
%>