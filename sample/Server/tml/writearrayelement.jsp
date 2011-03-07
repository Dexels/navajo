<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<tr>			<c:forEach var="prop" items="${navajoContext.message.allProperties}">
				<td>
				<nav:property property="${prop}">
					<c:import url="tml/writepropertyvalue.jsp"/>
				</nav:property>
				</td>
			</c:forEach>
</tr>
