<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../../WEB-INF/tld/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tld/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<nav:call service="navajo/InitGetAccessLog">
	<nav:set value="${param['accessid']}" property="AccessDetails/AccessId"/>
	<nav:call service="navajo/ProcessGetAccessLog" navajo="navajo/InitGetAccessLog">
		<c:if test="${navajoContext.navajo.messages['FullLog'] != null}">
			<h3>Input Navajo</h3>
			<nav:property propertyName="FullLog/NavajoIn">
				<nav:service serviceString="${navajoContext.property.value}">
					<c:import url="tml/writedefaulttml.jsp"></c:import>
				</nav:service>
			</nav:property>
			<h3>Output Navajo</h3>
			<nav:property propertyName="FullLog/NavajoOut">
				${navajoContext.property.value}
				<nav:service serviceString="${navajoContext.property.value}">
					<c:import url="tml/writedefaulttml.jsp"></c:import>
				</nav:service>
			</nav:property>
			<nav:property propertyName="FullLog/Console">
				<h3>Console</h3>
				<div class="info">
					${navajoContext.property.value}
				</div>
			</nav:property>		
			<nav:property propertyName="FullLog/Exception">
				<h3>Exception</h3>
				<div class="info">
					${navajoContext.property.value}
				</div>
	
			</nav:property>		
			</c:if>
	</nav:call>
</nav:call>
