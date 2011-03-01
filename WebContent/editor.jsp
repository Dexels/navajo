<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.tipi.appmanager.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<jsp:useBean id="manager" class="com.dexels.navajo.tipi.appmanager.ApplicationManager" scope="page" />
<jsp:useBean id="versionResolver" class="com.dexels.navajo.tipi.projectbuilder.VersionResolver" scope="page" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Settings</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- add your meta tags here -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->
</head>
<body>
<%
		manager.setContext(getServletContext());
		manager.setCurrentApplication(request.getParameter("application"));
		ApplicationStatus aa = manager.getApplication();
		versionResolver.load(aa.getRepository());

%>
  <div class="page_margins">
    <div class="page">
      <div id="header">
        <div id="topnav">
          <!-- start: skip link navigation -->

        </div>
        <h2>Tipi App Store</h2>
      </div>
      <div id="nav">
        <!-- skiplink anchor: navigation -->
        <a id="navigation" name="navigation"></a>
        <div class="hlist">
          <!-- main navigation: horizontal list -->
          <ul>
            <li class="active"><strong>Application Overview</strong></li>
           
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
          <div id="col1_content" class="clearfix">
            <h3>Usage</h3>
			<div class="info">
				Edit with care, file may be under CVS control
			</div>

        </div>
        </div>
        <div id="col3">

		  <c:set var="app" scope="page" value="${manager.application}"/>
			
		  <form  action="TipiAdminServlet" method="post">
		  		  
		  <textarea rows="10" cols="80" name="content"><% 
				    String path2 = request.getParameter("filePath");
		  			if(path2.indexOf("..")!=-1) {
		  				throw new  Exception("Illegal path: "+path2);
		  			}
					manager.getApplication().writeFile(path2,out);
		%></textarea>
			<input type="hidden" name="cmd" value="saveConfig" /> 
			<input type="hidden" name="app" value="${app.applicationName}" /> 
			<input type="hidden" name="filePath" value="${param.filePath}" /> 
			<input type="hidden" name="destination" value="/details.jsp?app=${app.applicationName}" /> 
			<br/>
			
			<input type="submit" value="Save &amp; Build" /><input type=button value="Cancel" onclick="history.go(-1)"/>
			
		  </form>
     </div>
          <!-- IE Column Clearing -->
          <div id="ie_clearing"> &#160; </div>
        </div>
      </div>
      <!-- begin: #footer -->
      <div id="footer">Powered by <a href="http://www.navajo.nl/">Navajo</a>
      </div>
    </div>
</body>
</html>
