package com.dexels.navajo.server;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AdminServlet extends HttpServlet {

  private static ResourceBundle properties = null;
  private static final String CONTENT_TYPE = "text/html; charset=iso-8859-1";

  /**Initialize global variables*/
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    properties = ResourceBundle.getBundle("navajoserver");
  }

  /**Process the HTTP Get request*/
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head><title>AdminServlet</title></head>");
    out.println("<body>");
    out.println("<H1> Upload adapter jar </H1>");
    out.println("<form method='post' action='Admin' enctype='multipart/form-data'>");
    out.println("<input type='file' name='jar'>");
    out.println("<input type='submit' name='command' value='upload_jar'>");
    out.println("</form>");
    out.println("<H1> Reload all adapters </H1>");
    out.println("<form method='post' action='Admin' enctype='multipart/form-data'>");
    out.println("<input type='submit' name='command' value='reload'>");
    out.println("</form>");
    out.println("<H1> Upload script </H1>");
    out.println("<form method='post' action='Admin' enctype='multipart/form-data'>");
    out.println("<input type='file' name='script'>");
    out.println("<input type='submit' name='command' value='upload_script'>");
    out.println("</form>");
    out.println("<H1> Update repository class </H1>");
    out.println("<form method='post' action='Admin' enctype='multipart/form-data'>");
    out.println("<input type='text' name='repository' size=50><BR>");
    out.println("<input type='submit' name='command' value='update_repository'>");
    out.println("</form>");
    out.println("<H1> Add Navajo webservice </H1>");
    out.println("<form method='post' action='Admin' enctype='multipart/form-data'>");
    out.println("Name: <input type='text' name='name' size=50><BR>");
    out.println("Groupid: <input type='text' name='group' size=50><BR>");
    out.println("<input type='submit' name='command' value='add_service'>");
    out.println("</form>");
     out.println("<H1> Add Navajo webservice group </H1>");
    out.println("<form method='post' action='Admin' enctype='multipart/form-data'>");
    out.println("Name: <input type='text' name='name' size=50><BR>");
    out.println("Handler (leave empty to use default handler): <input type='text' name='handler' size=50><BR>");
    out.println("<input type='submit' name='command' value='add_group'>");
    out.println("</form>");
    out.println("<A HREF='NavajoClient'>goto Navajo Client</A>");
    out.println("</body></html>");
  }

  private void copyFile(File file, String destination) throws FileNotFoundException, IOException {
     BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination+"/"+file.getName()));
     int x;
     while ((x = bis.read()) != -1) {
        bos.write(x);
     }
     bis.close();
     bos.close();
  }

  /**Process the HTTP Post request*/
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

     String adapterPath = properties.getString("adapter_path");
     String scriptPath = properties.getString("script_path");
     org.dexels.grus.MultipartRequest newrequest = new org.dexels.grus.MultipartRequest(request,"/tmp");
     org.dexels.grus.MultipartRequest mpr = (org.dexels.grus.MultipartRequest) newrequest;
     System.out.println("adapterPath = " + adapterPath);
     String command = newrequest.getParameter("command");
     System.out.println("command = " + command);
     if (command.equals("upload_jar")) {
       File file = mpr.getFile("jar");
       System.out.println("Jar Filename = " + file.getName());
       copyFile(file, adapterPath);
       Dispatcher.doClearCache();
       response.sendRedirect("Admin");
     } else if (command.equals("reload")) {
        Dispatcher.doClearCache();
        response.sendRedirect("Admin");
     } else if (command.equals("upload_script")) {
       File file = mpr.getFile("script");
       System.out.println("Script Filename = " + file.getName());
       copyFile(file, scriptPath);
       response.sendRedirect("Admin");
     } else if (command.equals("update_repository")) {
       String className = newrequest.getParameter("repository");
       Dispatcher.updateRepository(className);
       response.sendRedirect("Admin");
     } else if (command.equals("add_service")) {
        Repository r = Dispatcher.getRepository();
        // TODO: Extend functionality of repository interface:
        // addService(service_name, group)
        // addGroup(group_name, handler)
        // addUser(username, password)
        // addAuthorisation(username, group_name)
        if (r instanceof SQLRepository) {
          SQLRepository sqlr = (SQLRepository) r;
          Authorisation a = sqlr.getAuthorisation();
          String name = newrequest.getParameter("name");
          String group = newrequest.getParameter("group");
          try {
            a.addService(new Access(-1, -1, -1, "", "", "", "", ""), name, Integer.parseInt(group));
          } catch (SystemException se) {
            se.printStackTrace();
          }
        }
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("Webservice added<BR>");
        out.println("<A HREF='Admin'>goto Admin</A>");
        out.println("</html>");
     } else if (command.equals("add_group")) {
        Repository r = Dispatcher.getRepository();
        int id = -1;
        if (r instanceof SQLRepository) {
          SQLRepository sqlr = (SQLRepository) r;
          Authorisation a = sqlr.getAuthorisation();
          String name = newrequest.getParameter("name");
          String handler = newrequest.getParameter("handler");
          if (handler.equals(""))
            handler = "com.dexels.navajo.server.GenericHandler";
          try {
            id = a.addServiceGroup(new Access(-1, -1, -1, "", "", "", "", ""), name, handler);
          } catch (SystemException se) {
            se.printStackTrace();
          }
        }
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("Webservice group added, id = " + id + "<BR>");
        out.println("<A HREF='Admin'>goto Admin</A>");
        out.println("</html>");
     }

  }
  /**Clean up resources*/
  public void destroy() {
  }
}