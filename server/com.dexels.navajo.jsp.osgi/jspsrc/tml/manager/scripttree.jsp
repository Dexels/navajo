<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tld/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.ClientContext"  scope="application" />

<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<ul>

	<c:forEach var="folder" items="${serverContext.folders}">
		<c:if test="${folder.name != 'CVS'}">
			<li>
				<a href="index.jsp?view=editor&cmd=setFolder&path=${folder.name}/&service=${param.service}">${folder.name }</a> <a href="compile?script=${folder.name}">[recompile]</a>
			</li>
		</c:if>
	</c:forEach>
	<c:forEach var="script" items="${serverContext.scripts}">
		<li><c:set var="fileName" value="${script}.xml"/>
		<c:choose>
			<c:when test="${navajoContext.navajos[script]!=null}">
				<a href="index.jsp?view=editor&service=${serverContext.path}${script}">${script} ${serverContext.cvsInfo[fileName].revision }</a>
				<a href="index.jsp?view=editor&cmd=cvsUpdate&path=scripts/${serverContext.path}${script}.xml">[[Update]]</a>
				<a href="index.jsp?view=editor&service=${serverContext.path}${script}&${serverContext.path}${script}=true">[[Reload]]</a>
			</c:when>
			<c:otherwise>
				<a href="index.jsp?view=tsl&service=${serverContext.path}${script}">${script} ${serverContext.cvsInfo[fileName].revision }</a>
				<c:if test="${serverContext.cvsInfo[fileName].revision !=null}">
					<a href="index.jsp?view=tsl&cmd=cvsUpdate&path=scripts/${serverContext.path}${script}.xml">[[Update]]</a>
				</c:if>
			</c:otherwise>
		</c:choose></li>
	</c:forEach>

</ul>