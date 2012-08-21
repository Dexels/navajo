<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ taglib prefix="navserver" uri="/WEB-INF/tld/navajoserver.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>

<%@page import="com.dexels.navajo.installer.NavajoInstaller"%><jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />
<jsp:useBean id="serverContext" class="com.dexels.navajo.jsp.server.NavajoServerContext" scope="session" />
<jsp:useBean id="installerContext" class="com.dexels.navajo.jsp.server.InstallerContext" scope="session" />
<jsp:setProperty property="pageContext" name="serverContext" value="${pageContext}"/>
<jsp:setProperty property="pageContext" name="installerContext" value="${pageContext}"/>

<%

	System.err.println("In doinstall...");
	String path = application.getRealPath("");
	String selectedPath = request.getParameter("selectedPath");
	String cvsRoot = request.getParameter("cvsRoot");
	String cvsPassword = request.getParameter("cvsPassword");
	String cvsModule = request.getParameter("cvsModule");
	String cvsRevision = request.getParameter("cvsRevision");
	
	Map<String,String> params = new HashMap<String,String>();
	params.put("selectedPath",selectedPath);
	params.put("cvsRoot",cvsRoot);
	params.put("cvsPassword",cvsPassword);
	params.put("cvsModule",cvsModule);
	params.put("cvsRevision",cvsRevision);

	System.err.println("Result:"+params);
	
	String result = NavajoInstaller.callAnt(new File(application.getRealPath( "WEB-INF/ant/checkout.xml")),new File(path),params);
	System.err.println("Result:"+result);

	
	File home = new File(System.getProperty("user.home"));
	File navajo = new File(home,"navajo.properties");
	FileWriter fw = new FileWriter(navajo,true);
	fw.write(application.getContextPath().substring(1) +"="+selectedPath+"\n");
	fw.flush();
	fw.close();
	
%>
<html><head>
  <meta http-equiv="Refresh" content="0; url=../../index.jsp">
</head></html>
