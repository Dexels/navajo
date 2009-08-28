<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page errorPage="tml/tmlerror.jsp" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />

<nav:postHandler/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Navajo Tester</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- add your meta tags here -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->
<c:import url="tml/extrastylesheets.jsp"></c:import>

</head>

<body>
<form action="navajotester.jsp" method="post" >
	<nav:client username="ROOT" password="R20T"  />
<!--server="penelope1.dexels.com/sportlink/knvb/servlet/Postman"	-->

	<c:choose>
		<c:when test="${param.inputNavajo!=null}">
			<nav:service service="${param.inputNavajo}">
				<nav:dump></nav:dump>
			</nav:service>
			<nav:call service="${param['service']}" navajo="${param['inputNavajo']}"/>
		</c:when>
		<c:otherwise>
			<nav:call service="${param.service}"/>
		</c:otherwise>
	</c:choose>
	
  <input type="hidden" name="inputNavajo" value="${param['service']}"/>
  <div class="page_margins">
    <div class="page">
      <div id="header">
        <div id="topnav">
          <!-- start: skip link navigation -->

        </div>
        <h2>Navajo Tester</h2>
      </div>
      <div id="nav">
        <!-- skiplink anchor: navigation -->
        <a id="navigation" name="navigation"></a>
        <div class="hlist">
  
          <!-- main navigation: horizontal list -->
          <ul>
          			<c:import url="tml/topmenu.jsp"/>
			
          
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
			<c:import url="tml/leftmenu.jsp"/>
       </div>
        <div id="col3">
          <div id="col3_content" class="clearfix">
           <c:choose>
          		<c:when test="${param['view']==editor}">
					<nav:service service="${param['service']}">
						<c:import url="tml/writedefaulttml.jsp"/>
					</nav:service>
			     </c:when>
			      <c:when test="${param['view']=='tsl'}">
						<navserver:formattedFile filePath="scripts/${param['service']}.xml"></navserver:formattedFile>
				  </c:when>
			      <c:when test="${param['view']=='tml'}">
					Service: ${param['service']}
					<nav:service service="${param['service']}">
						<nav:formattml service="${param['service']}"/>
					</nav:service>
			     </c:when>
          		<c:when test="${param['view']=='javasource'}">
		      			<navserver:formattedFile filePath="classes/${param['service']}.java"></navserver:formattedFile>
				</c:when>
           
          		<c:otherwise>
					<nav:service service="${param['service']}">
						<c:import url="tml/writedefaulttml.jsp"/>
					</nav:service>
          		</c:otherwise>
          	</c:choose>
  
    </div>
          <!-- IE Column Clearing -->
          <div id="ie_clearing"> &#160; </div>
        </div>
      </div>
      <!-- begin: #footer -->
      <div id="footer">Powered by <a href="http://www.navajo.nl/">Navajo</a>
      </div>
    </div>
  </div>
  </form>
</body>
</html>

