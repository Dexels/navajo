<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.client.context.NavajoContext" scope="session" />	  
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>

<c:if test="${param['service']!= null && param['service']!=''}">
	<h3>Run script</h3>
	<div class="info">
	<a href="index.jsp?view=editor&service=${ param['service']}&${ param['service']}=true">${ param['service']}</a>
	</div>
</c:if>
<c:choose>
	<c:when test="${param['view']!=null && param['view']!='home'}">
		<h3>Methods</h3>
		<div class="info">
		    <c:if test="${param['service']!= null && param['service']!='' && navajoContext.navajos[param['service']]!=null}">
         		  <nav:service service="${param['service']}">
				<c:import url="/tml/manager/writemethodlist.jsp" />
			  </nav:service>
			</c:if>
		</div>
	</c:when>
	<c:otherwise>
		<c:import url="tml/manager/configtree.jsp" />
	</c:otherwise>
</c:choose>
		<c:if test="${param['view']=='editor'}">
			<h3>Loaded scripts</h3>
				<c:import url="/tml/manager/writeloadedscripts.jsp"/>
				<h3>Message structure</h3>
		<div class="info">
			    <c:if test="${param['service']!= null && param['service']!='' && navajoContext.navajos[param['service']]!=null}">
          		  <nav:service service="${param['service']}">
					<div class="info"><c:import url="/tml/manager/writetmltree.jsp" /></div>
				  </nav:service>
				</c:if>
			</div>
		</c:if>
			
	<h3>NQL Query</h3>
		<c:import url="/tml/manager/nqlform.jsp" />

		<h3>Folders and Scripts</h3>
		<div class="info"><c:import url="/tml/manager/scripttree.jsp" />
		</div>


