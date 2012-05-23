<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.tipi.appmanager.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@page import="com.dexels.navajo.tipi.projectbuilder.*"%>

<%@ taglib prefix="c" uri="WEB-INF/tags/c.tld"%>
<jsp:useBean id="manager" class="com.dexels.navajo.tipi.appmanager.ApplicationManager" scope="page" />
<jsp:useBean id="versionResolver" class="com.dexels.navajo.tipi.projectbuilder.VersionResolver" scope="page" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Tipi Application Store</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- add your meta tags here -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->
</head>
<body>
  <div class="page_margins">
    <div class="page">
      <div id="header">
        <div id="topnav">
          <!-- start: skip link navigation -->
<%

String res = request.getParameter("result");
if(res!=null) {
	out.write("<div class='warning'>"+URLDecoder.decode(res,"UTF-8")+"</div>");
}
%>

<%

	manager.setContext(getServletContext());
	String appFolder = getServletContext().getInitParameter("appFolder"); 
	if(appFolder==null) {
		appFolder = config.getServletContext().getRealPath(".");
	}
	manager.setAppsFolder(new File(appFolder));
	manager.setCurrentApplication(request.getParameter("application"));
	
	versionResolver.load(manager.getApplication().getRepository());
%>
        </div>
        <h2>Tipi App Store</h2>
      </div>
      <div id="nav">
        <!-- skiplink anchor: navigation -->
        <a id="navigation" name="navigation"></a>
        <div class="hlist">
          <!-- main navigation: horizontal list -->
          <ul>
            <li ><a href="details.jsp?application=${manager.currentApplication}">${manager.currentApplication}</a></li>
 			<li class="active"><strong>Repository Overview</strong></li>

          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
          <div id="col1_content" class="clearfix">
            <h3><a href=".">Back to overview</a></h3>
			<div class="info">
				Details of ${manager.currentApplication}
			</div>

          </div>
        </div>
        <div id="col3">
          <div id="col3_content" class="clearfix">
			<c:set var="app" scope="page" value="${manager.application}"/>
			<div class="important">
				<h4>Extensions</h4>
			</div>
			
	
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
</body>
</html>
