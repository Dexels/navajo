<%@page errorPage="tml/tmlerror.jsp" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tags/navajoserver.tld"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<jsp:useBean id="installerContext" class="com.dexels.navajo.jsp.server.InstallerContext" scope="page" />
<c:if test="${installerContext.hasRedundantFiles}">
	Warning: Redundant files found!
</c:if>