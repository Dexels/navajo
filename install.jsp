<jsp:page import="java.io.*"/>
<html>
<head>
<title>
index
</title>
</head>
<body>
<h1>
Navajo Installer
</h1>

<%
  // BIG TODO:
  // CLEAN UP AND RE-FACTOR SUCH THAT THIS WILL BE A MINIMALISTIC JSP PAGE THAT CAN BE EASILY TRANSFORMED TO SUPPORT
  // OTHER APPLICATIONS
  String installDir = request.getParameter("install_dir");

  // Create installation directory
  if (installDir != null) {

    String port = request.getParameter("port_number");
    String serverName = request.getParameter("server_name");
    String dbServer = request.getParameter("db_server");
    String dbUsername = request.getParameter("db_username");
    String dbPassword = request.getParameter("db_password");
    String dbPort = request.getParameter("db_port");
    String dbInstance = request.getParameter("db_instance");

    // Create installation directory
    com.dexels.navajo.install.Tools.mkDir(installDir);

    // Extract installation jar in installation directory.
    com.dexels.navajo.install.Tools.extractJar("install/evaluation.jar", installDir);

    // Install Navajo configuration files.
    java.util.HashMap tokens = null;
    tokens = new java.util.HashMap();
    String installDir2 = installDir.replace('\\', '/');
    tokens.put("INSTALL_DIR", installDir2);
    tokens.put("PORT", port);
    tokens.put("SERVER_NAME", serverName);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/web.xml", installDir+"/demo/WEB-INF/web.xml", tokens);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/server.xml", installDir+"/demo/auxilary/config/server.xml", tokens);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/persistence-manager.xml", installDir+"/demo/auxilary/config/persistence-manager.xml", tokens);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/index.jsp", installDir+"/crude/index.jsp", tokens);
    tokens.put("DB_SERVER", dbServer);
    tokens.put("DB_USERNAME", dbUsername);
    tokens.put("DB_PASSWORD", dbPassword);
    tokens.put("DB_PORT", dbPort);
    tokens.put("DB_INSTANCE", dbInstance);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/sqlmap.xml", installDir+"/demo/auxilary/config/sqlmap.xml", tokens);

    // Install Stocks and Demand servlets
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/df_web.xml", installDir+"/df/WEB-INF/web.xml", tokens);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/stocks_web.xml", installDir+"/stocks/WEB-INF/web.xml", tokens);

    // Install Orion configuration files.
    tokens = new java.util.HashMap();

    tokens.put("INSTALL_DIR", installDir);
    tokens.put("PORT", port);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/orion_server.xml", "config/server.xml", tokens);
    com.dexels.navajo.install.Tools.copyAndReplaceTokens("install/default-web-site.xml", "config/default-web-site.xml", tokens);

    System.out.print("Waiting...");
    Thread.sleep(5000);
    System.out.println("Done!");

    response.sendRedirect("http://" + serverName + ":" + port + "/demo/servlet/NavajoClient");
  }
%>

<form method="post" action="install.jsp">
<br><br>
<table>
<tr><td colspan="2"><I>Navajo specific</I></td></tr>
<tr><td>Installation folder (e.g. d:\apps)</td><td> <input type="text" name="install_dir"></td></tr>
<tr><td colspan="2"><I>Orion specific</I></td></tr>
<tr><td>Port number for Orion </td><td><input type="text" name="port_number"></td></tr>
<tr><td>Fully qualified name of the Orion server </td><td><input type="text" name="server_name"></td></tr>
<tr><td colspan="2"><I>Database specific</I></td></tr>
<tr><td>Fully qualified name of the database server </td><td><input type="text" name="db_server"></td></tr>
<tr><td>Username </td><td><input type="text" name="db_username"></td></tr>
<tr><td>Password </td><td><input type="text" name="db_password"></td></tr>
<tr><td>DB Port number </td><td><input type="text" name="db_port"></td></tr>
<tr><td>Instance name </td><td><input type="text" name="db_instance"></td></tr>
<tr><td><input type="submit" name="Submit" value="Submit"> </td><td></td></tr>
</table>
</form>
</body>
</html>
