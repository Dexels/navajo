<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.ClientContext"  scope="application" />


<div class="messagetree">
	<ul>
		<c:forEach var="msg" items="${navajoContext.navajos}">
			<li>
			<c:choose>
				<c:when test="${param['service']==msg.key}">
					${msg.key}
				</c:when>
				<c:otherwise>
				<a href="index.jsp?view=editor&service=${msg.key}">${msg.key}</a>
				</c:otherwise>		
			</c:choose>
			</li>
		</c:forEach>
	</ul>
</div>