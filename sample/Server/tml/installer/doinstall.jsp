<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tags/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tags/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>

<%@page import="com.dexels.navajo.installer.NavajoInstaller"%><jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:useBean id="installerContext" class="com.dexels.navajo.jsp.server.InstallerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<jsp:setProperty property="pageContext" name="installerContext" value="${pageContext}"/>
<%

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
	
	
	File home = new File(System.getProperty("user.home"));
	File navajo = new File(home,"navajo.properties");
	FileWriter fw = new FileWriter(navajo,true);
	fw.write(application.getContextPath().substring(1) +"="+selectedPath+"\n");
	fw.flush();
	fw.close();
	
%>
<html><head>
  <meta http-equiv="Refresh" content="0; url=/StandardFrank/">
</head></html>
