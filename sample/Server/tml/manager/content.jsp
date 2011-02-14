<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
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
<jsp:setProperty property="navajoContext" name="serverContext"
	value="${navajoContext}" />
	
<c:choose>
	<c:when test="${param['view']=='editor'}">
		<c:if test="${param['service'] != null && param['service'] != '' && navajoContext.navajos[param['service']]!=null}">
			<nav:service service="${param['service']}">
				<c:import url="tml/writedefaulttml.jsp" />
			</nav:service>
		</c:if>
	</c:when>
	<c:when test="${param['view']=='tsl'}">
		<c:if test="${param['service']!= null && param['service']!='' }">
			<navserver:formattedFile serverContext="${serverContext}" absoluteFilePath="${serverContext.scriptStatus.source}"></navserver:formattedFile>
		</c:if>
	</c:when>
	<c:when test="${param['view']=='tml'}">
		<c:if test="${param['service'] != null && param['service']!='' && navajoContext.navajos[param['service']]!=null}">
			<nav:service service="${param['service']}">
				<navserver:formattml service="${param['service']}" />
			</nav:service>
		</c:if>
	</c:when>
	<c:when test="${param['view']=='javasource'}">
		<c:choose>
			<c:when test="${param['service'] != null && param['service']!='' && serverContext.scriptStatus.compiledUnit!=null }">
				<navserver:formattedFile serverContext="${serverContext}" absoluteFilePath="${serverContext.scriptStatus.compiledUnit}"></navserver:formattedFile>
			</c:when>
			<c:otherwise>
				Uncompiled script.
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param['view']=='navadoc'}">
		<c:if test="${param['service'] != null && param['service']!=''}">
			<c:import url="NavaDoc?sname=${param['service']}"/>
		</c:if>
	</c:when>

	<c:otherwise>
		<c:import url="tml/manager/writehome.jsp" />
	</c:otherwise>
</c:choose>
