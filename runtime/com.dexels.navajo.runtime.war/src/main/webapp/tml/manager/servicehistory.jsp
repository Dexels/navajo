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
	<nav:set value="${param['service']}" property="AccessDetails/Service"/>
	<nav:call service="navajo/ProcessGetServiceLogOverview" navajo="navajo/InitGetAccessLog">
		<nav:message messageName="LogOverview">
			<div class="messagetree">
			<table>
			<c:forEach var="msg" items="${navajoContext.message.allMessages}">
				<nav:message message="${msg}">
					<tr>			
							<td>
								<nav:property propertyName="CreatedTime">
									${navajoContext.property.value}
								</nav:property>
							</td>
							<td>
								<nav:property propertyName="UserName">
									${navajoContext.property.value}
								</nav:property>
							</td>
							<td>
								<nav:property propertyName="IpAddress">
									${navajoContext.property.value}
								</nav:property>
							</td>
							<td>
								<nav:property propertyName="AccessId">
									<a href="index.jsp?view=accessdetail&service=${param['service']}&accessid=${navajoContext.property.value}">Details</a>
								</nav:property>
							</td>
						<td>
						</td>
					</tr>
				</nav:message>
			</c:forEach>
			</table>
			</div>
		</nav:message>
	</nav:call>
</nav:call>
