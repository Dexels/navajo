<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page errorPage="tml/tmlerror.jsp" language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.tipi.appmanager.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="WEB-INF/tags/navajo.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<jsp:useBean id="manager" class="com.dexels.navajo.tipi.appmanager.ApplicationManager" scope="page" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Navajo Tester </title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>
	<nav:client server="penelope1.dexels.com/sportlink/knvb/servlet/Postman" username="" password="" />
	<nav:call service="club/InitUpdateClub">
		<nav:set value="BBKY84H" property="Club/ClubIdentifier"/>
		<nav:call service="club/ProcessQueryClub" navajo="club/InitUpdateClub">
		</nav:call>
	</nav:call>
	
<body>
  <div class="page_margins">
    <div class="page">
      <div id="header">
        
        <h2>Een voorbeeld van een club:  ${navajoContext.propertyElement['club/ProcessQueryClub:ClubData/ClubName']} </h2>
      </div>
      <div id="nav">
      		${navajoContext.propertyElement['club/ProcessQueryClub:VisitorAddress/StreetName']} ${navajoContext.propertyElement['club/ProcessQueryClub:VisitorAddress/AddressNumber']}
			
      	<nav:service service="club/ProcessQueryClub">
			<nav:property propertyName="ClubData/Logo">
				<c:import url="tml/writepropertyvalue.jsp"/>
			</nav:property>
			
		
				
		</nav:service>
      </div>

      <div id="footer">Powered by <a href="http://www.navajo.nl/">Navajo</a>
      </div>
    </div>
  </div>
</body>
</html>

