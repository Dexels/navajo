<%@page errorPage="tml/tmlerror.jsp" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
 		
<nav:postHandler/>
<html>
<head>
<title>Navajo Tester 2.0</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- add your meta tags here -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->
<c:import url="tml/manager/extrastylesheets.jsp"></c:import>

</head>
	<nav:client username="ROOT" password="R20T"  />
<!--server="penelope1.dexels.com/sportlink/knvb/servlet/Postman"	-->


		<c:if test="${param['service']!= null }">
			<c:if test="${param[param['service']]==true}">
				<c:choose>
					<c:when test="${param.inputNavajo!=null}">
							<nav:call service="${param['service']}" navajo="${param['inputNavajo']}"/>
					</c:when>
					<c:otherwise>
						<nav:call service="${param.service}"/>
					</c:otherwise>
				</c:choose>
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
        <h2><a href="index.jsp">Navajo Tester 2.0</a></h2>
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
			 <c:import url="tml/manager/content.jsp"/>
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

