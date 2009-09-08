<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<c:choose>
	<c:when test="${param['view']!=null && param['view']!='home'}">
		<c:choose>
			<c:when test="${param['service']!=null && param['service']!=''}">
				<c:if test="${param['view']=='editor'}">
					<nav:service service="${param['service']}">
						<h3>Message structure</h3>
						<div class="info"><c:import url="tml/manager/writetmltree.jsp" /></div>
					</nav:service>
				</c:if>
				<h3>Methods</h3>
				<div class="info">
					<c:import url="tml/manager/writemethodlist.jsp" />
				</div>
			</c:when>
			<c:otherwise>
			
			</c:otherwise>
		</c:choose>
		<h3>Folders</h3>
		<div class="info"><c:import url="tml/manager/scripttree.jsp" />
		</div>
	</c:when>
	<c:otherwise>
		<c:import url="tml/manager/configtree.jsp" />
	</c:otherwise>
</c:choose>

