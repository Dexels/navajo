<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page trimDirectiveWhitespaces="true" %>
<%@page errorPage="tml/tmlerror.jsp" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page import="com.dexels.navajo.jsp.server.NavajoServerContext"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tld/navajoserver.tld"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<jsp:useBean id="installerContext" class="com.dexels.navajo.jsp.server.InstallerContext" scope="page" />
<jsp:setProperty property="installerContext" name="serverContext" value="${installerContext}"/>

<jsp:setProperty property="pageContext" name="installerContext" value="${pageContext}"/>
<c:choose>
	<c:when test="${installerContext.validInstallation}">
		<c:import url="navajotester.jsp"/>
	</c:when>
	<c:otherwise>
		<c:import url="tml/installer/main.jsp"/>
	</c:otherwise>
</c:choose>
