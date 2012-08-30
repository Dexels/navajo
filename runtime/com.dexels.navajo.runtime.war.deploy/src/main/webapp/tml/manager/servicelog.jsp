<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../../WEB-INF/tld/navajo.tld"%>
<%@ taglib prefix="navserver" uri="../../WEB-INF/tld/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>

<nav:call service="navajo/InitGetAccessLog">

<%--<nav:set property="AccessDetails/AccessId" value='<%=request.getParameter("AccessId")%>  1285594883162-105' />
 --%>	
<nav:set property="AccessDetails/AccessId" value='1285594883162-105' />
<c:import url="tml/writedefaulttml.jsp"/>
	<nav:call service="navajo/ProcessGetAccessLog" navajo="navajo/InitGetAccessLog">
		<%--<c:import url="tml/writedefaulttml.jsp"/> --%>
		Entering logdetails	
		<nav:property propertyName="LogDetails/Exception">
			<div class="consoleScroll">
			<navserver:formattedFile serverContext="${serverContext}" content="${navajoContext.property.typedValue}" name="Exception.txt" />
			</div>
			<c:if test="${!empty navajoContext.property.typedValue} ">
			</c:if>
		</nav:property>
		<nav:property propertyName="LogDetails/Console">
			<div class="consoleScroll">
				<navserver:formattedFile serverContext="${serverContext}" content="${navajoContext.property.typedValue}" name="Console.txt" />
			</div>
			<c:if test="${navajoContext.property.typedValue != null} ">
			</c:if>
		</nav:property>
		
		Navajo In
		<nav:property propertyName="LogDetails/NavajoIn">
			<nav:tml content="${navajoContext.property.typedValue}">
				<c:import url="tml/writedefaulttml.jsp"/>
			</nav:tml>
		</nav:property>
		
		Navajo Out
		<nav:property propertyName="LogDetails/NavajoOut">
			<nav:tml content="${navajoContext.property.typedValue}"></nav:tml>
		</nav:property>

	</nav:call>
</nav:call>