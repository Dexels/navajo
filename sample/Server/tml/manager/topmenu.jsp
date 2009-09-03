<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />	        

			<c:choose>
          		<c:when test="${param['view']=='home' || param['view']==null}">
		            <li class="active"><strong>Home</strong></li>
          		</c:when>
          		<c:otherwise>
		            <li><a href="${pageContext.request.requestURI}?view=home&amp;service=${param['service']}">Home</a></li>
          		</c:otherwise>
          	</c:choose>			<c:choose>
          		<c:when test="${param['view']=='editor' }">
		            <li class="active"><strong>Editor</strong></li>
          		</c:when>
          		<c:otherwise>
		            <li><a href="${pageContext.request.requestURI}?view=editor&amp;service=${param['service']}">Editor</a></li>
          		</c:otherwise>
          	</c:choose>
          	<c:choose>
          		<c:when test="${param['view']=='tml'}">
		            <li class="active"><strong>Tml</strong></li>
          		</c:when>
          		<c:otherwise>
		            <li><a href="${pageContext.request.requestURI}?view=tml&amp;service=${param['service']}">Tml</a></li>
          		</c:otherwise>
          	</c:choose>
          	<c:choose>
          		<c:when test="${param['view']=='tsl'}">
		            <li class="active"><strong>Tsl</strong></li>
          		</c:when>
          		<c:otherwise>
		            <li><a href="${pageContext.request.requestURI}?view=tsl&amp;service=${param['service']}">Tsl</a></li>
          		</c:otherwise>
          	</c:choose>
          	<c:choose>
          		<c:when test="${param['view']=='javasource'}">
		            <li class="active"><strong>Compiled</strong></li>
          		</c:when>
          		<c:otherwise>
		            <li><a href="${pageContext.request.requestURI}?view=javasource&amp;service=${param['service']}">Compiled</a></li>
          		</c:otherwise>
          	</c:choose>
           