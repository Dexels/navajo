<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />	  
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<ul>

	<c:forEach var="folder" items="${serverContext.folders}">
		<c:if test="${folder.name != 'CVS'}">
			<li>
				<a href="index.jsp?view=editor&command=setFolder&folder=${folder.name}&service=${param.service}">${folder.name }</a>
			</li>
		</c:if>
	</c:forEach>
	<c:forEach var="script" items="${serverContext.scripts}">
		<li>
		 
		<c:set var="fileName" value="${script}.xml"/>
		<c:choose>
			<c:when test="${navajoContext.navajos[script]!=null}">
				<a href="index.jsp?view=editor&service=${serverContext.path}${script}">${script} ${serverContext.cvsInfo[fileName].revision }</a>
				<a href="index.jsp?view=editor&cmd=cvsUpdate&path=script/${script}">[[Update]]</a>
				<a href="index.jsp?view=editor&service=${serverContext.path}${script}&${serverContext.path}${script}=true">[[Reload]]</a>
			</c:when>
			<c:otherwise>
				<a href="index.jsp?view=editor&service=${serverContext.path}${script}&${serverContext.path}${script}=true">${script} ${serverContext.cvsInfo[fileName].revision }</a>
			</c:otherwise>
		</c:choose>
		<!-- Note the weird variable name/value reversing trick -->
		</li>
	</c:forEach>

</ul>