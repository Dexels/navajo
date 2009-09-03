<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext"
	class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<jsp:useBean id="serverContext"
	class="com.dexels.navajo.jsp.server.NavajoServerContext"
	scope="session" />
<jsp:setProperty property="pageContext" name="serverContext"
	value="${pageContext}" />
<c:choose>
	<c:when test="${param['view']=='editor'}">
		<c:if test="${param['service'] != null && param['service'] != ''}">
			<nav:service service="${param['service']}">
				<c:import url="tml/writedefaulttml.jsp" />
			</nav:service>
		</c:if>
	</c:when>
	<c:when test="${param['view']=='tsl'}">
		<navserver:formattedFile filePath="scripts/${param['service']}.xml"></navserver:formattedFile>
	</c:when>
	<c:when test="${param['view']=='tml'}">
		<c:if test="${param['service'] != null && param['service']!=''}">
			<nav:service service="${param['service']}">
				<nav:formattml service="${param['service']}" />
			</nav:service>
		</c:if>
	</c:when>
	<c:when test="${param['view']=='javasource'}">
		<c:if test="${param['service'] != null && param['service']!=''}">
			<navserver:formattedFile filePath="classes/${param['service']}.java"></navserver:formattedFile>
		</c:if>
	</c:when>

	<c:otherwise>
		<c:import url="tml/manager/writehome.jsp" />
	</c:otherwise>
</c:choose>
