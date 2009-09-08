<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:useBean id="installerContext" class="com.dexels.navajo.jsp.server.InstallerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<jsp:setProperty property="pageContext" name="installerContext" value="${pageContext}"/>

<nav:postHandler/>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Navajo Installer 2.0</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<!-- add your meta tags here -->

<link href="css/my_layout.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 7]>
<link href="css/patches/patch_my_layout.css" rel="stylesheet" type="text/css" />
<![endif]-->

</head>

<body>


  <input type="hidden" name="inputNavajo" value="${param['service']}"/>
  <div class="page_margins">
    <div class="page">
      <div id="header">
        <div id="topnav">
          <!-- start: skip link navigation -->

        </div>
        <h2><a href="index.jsp">Navajo Installer 2.0</a></h2>
      </div>
      <div id="nav">
        <!-- skiplink anchor: navigation -->
        <a id="navigation" name="navigation"></a>
        <div class="hlist">
  
          <!-- main navigation: horizontal list -->
          <ul>
		            <li class="active"><strong>Home</strong></li>
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
       </div>
        <div id="col3">
          <div id="col3_content" class="clearfix" style="overflow:auto">
				<h3>No installation found.</h3>
				<p>While it is completely possible to run a navajo installation in-place, I strongly recommend
				installing the actual files elsewhere.
				</p>
				<p>
				Current server context: ${serverContext.contextName}
				</p>
				<p>
				
				Choose an installation path for context: ${serverContext.contextName}:
				</p>
				<a href="tml/installer/doinstall.jsp?selectedPath=${installerContext.suggestedPath}">install</a>
				<form action="tml/installer/doinstall.jsp" method="post">
					<input type="text" name="selectedPath" value="${installerContext.suggestedPath}"/><br/>
					Delete files in web-app? <input type="checkbox" name="deleteLocal" value="true"/><br/>
					<input type="submit" value="Create default installation">

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
  </div>
</body>
</html>

