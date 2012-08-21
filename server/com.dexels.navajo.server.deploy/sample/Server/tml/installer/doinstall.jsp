<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tld/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.dexels.navajo.installer.NavajoInstaller"%>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:useBean id="installerContext" class="com.dexels.navajo.jsp.server.InstallerContext" scope="session" />






<%
	serverContext.setPageContext(pageContext);
	installerContext.setPageContext(pageContext);
	System.err.println("In doinstall...");
	String path = application.getRealPath("");
	String selectedPath = request.getParameter("selectedPath");
	Map<String,String> params = new HashMap<String,String>();
	params.put("selectedPath",selectedPath);
	System.err.println("Result:"+params);
	
	String result = NavajoInstaller.callAnt(new File(application.getRealPath( "WEB-INF/ant/install.xml")),new File(path),params);
	System.err.println("Result:"+result);

	String deleteLocal = request.getParameter("deleteLocal");

	if(deleteLocal!=null) {
		//String result2 = NavajoInstaller.callAnt(new File(application.getRealPath( "WEB-INF/ant/deleteLocal.xml")),new File(path),params);
		//System.err.println("Result:"+result2);
	}
	
	String contextPath = application.getContextPath().substring(1) ;
	String engineInstance = System.getProperty("com.dexels.navajo.server.EngineInstance");
	String key = contextPath;
	if(engineInstance!=null) {
		key = contextPath +"@"+engineInstance;
	}

	// TODO Check if it already contains the value. This construction is incorrect if
	// the installation directory has simply been deleted.
	System.err.println("Using user home: "+System.getProperty("user.home"));
	File home = new File(System.getProperty("user.home"));
	File navajo = new File(home,"navajo.properties");
	FileWriter fw = new FileWriter(navajo,true);
	fw.write(key+"="+selectedPath+"\n");
	fw.flush();
	fw.close();

	// ----
	installerContext.initialize();

%>
<html><head>
  <meta http-equiv="Refresh" content="0; url=../../index.jsp">
</head></html>