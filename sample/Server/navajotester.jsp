<%@page errorPage="tml/tmlerror.jsp" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page import="com.dexels.navajo.jsp.server.NavajoServerContext"%>

<jsp:useBean id="navajoContext" class="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:setProperty property="navajoContext" name="serverContext" value="${navajoContext}"/>
<navserver:localclient/>
<nav:postHandler/>
<html>
<head>
<style type="text/css">
	.consoleScroll {
	height:300px;
	width:700px;
	overflow:scroll;
	}
</style>

<c:if test="${param['service']!= null }">
	<jsp:setProperty property="script" name="serverContext" value="${param['service']}"/>
</c:if>	
<title>
<c:import url="tml/writeversion.jsp" />
</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- add your meta tags here -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->
<c:import url="tml/manager/extrastylesheets.jsp"></c:import>

</head>


	<c:if test="${param['service']!= null }">
		<c:if test="${param[param['service']]==true}">
				<nav:call service="${param['service']}" navajo="${param['inputNavajo']}"/>
		</c:if>
	</c:if>
 	<c:if test="${param['cmd'] != null}">
 		<c:set target="${serverContext}" property="command" value="${param['cmd']}"/>
	</c:if> 
		
<body>

<form action="index.jsp" method="post" >


  <input type="hidden" name="inputNavajo" value="${param['service']}"/>
  <input type="hidden" name="view" value="editor"/>
  <div class="page_margins">
    <div class="page">
      <div id="header">
        <div id="topnav">
          <!-- start: skip link navigation -->

        </div>
        <h2><a href="index.jsp"><c:import url="tml/writeversion.jsp" /></a></h2>
        <div style="float: right; position: absolute;  right: 30px; top: 10px;"><a href="http://www.dexels.com"><img src="images/logo_dexels.png"/></a></div>
        <%
       	try {
				Class.forName("com.sun.tools.javac.Main");
			} catch (ClassNotFoundException e) {
				System.err.println("No sun compiler.");
				out.write("<p class=\"warning\">No Sun compiler found! Add a tools.jar (from a JDK) to the webapp, or to the Tomcat/lib folder (recommended)</p>");

			}

        %>
      </div>
      <div id="nav">
        <!-- skiplink anchor: navigation -->
        <a id="navigation" name="navigation"></a>
        <div class="hlist">
  
          <!-- main navigation: horizontal list -->
          <ul>
          	<c:import url="tml/manager/topmenu.jsp"/>
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
			<c:import url="tml/manager/leftmenu.jsp"/>
       </div>
        <div id="col3">
          <div id="col3_content" class="clearfix" style="overflow:auto">
			<c:catch var="signal">
				<c:import url="tml/manager/content.jsp"/>
			</c:catch>
			<c:if test="${signal}">
				${signal}
			</c:if>		
		    </div>
          <!-- IE Column Clearing -->
          <div id="ie_clearing"> &#160; </div>
        </div>
      </div>
      <!-- begin: #footer -->
      <div id="footer">Powered by <a href="http://www.navajo.nl/">Navajo</a>
      	<c:import url="tml/manager/installwarning.jsp"></c:import>
      </div>
    </div>
  </div>
  </form>
</body>
</html>

