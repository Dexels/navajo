package com.dexels.navajo.server;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.XMLutils;
import com.dexels.navajo.util.NavajoUtils;


public class AdminServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html; charset=iso-8859-1";

    private String configurationPath = "";

    /** Initialize global variables*/
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        configurationPath = config.getInitParameter("configuration");
        System.out.println("configurationPath = " + configurationPath);
    }

    /** Process the HTTP Get request*/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        out.println("NOT SUPPORTED");
    }

    private String properDir(String in) {
        String result = in + (in.endsWith("/") ? "" : "/");

        System.out.println(result);
        return result;
    }

    private void copyFile(File file, String destination, boolean beta) throws FileNotFoundException, IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destination + "/" + file.getName() + ((beta) ? "_beta" : "")));
        int x;

        while ((x = bis.read()) != -1) {
            bos.write(x);
        }
        bis.close();
        bos.close();
    }

    /** Process the HTTP Post request*/
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Navajo config = XMLutils.createNavajoInstance(configurationPath);
            Message body = config.getMessage("server-configuration");

            String rootPath = properDir(NavajoUtils.getPropertyValue(body, "paths/root", true));
            String adapterPath = properDir(rootPath + NavajoUtils.getPropertyValue(body, "paths/adapters", true));
            String scriptPath = properDir(rootPath + NavajoUtils.getPropertyValue(body, "paths/scripts", true));
            String tempPath = properDir(rootPath + NavajoUtils.getPropertyValue(body, "paths/tmp", true));

            org.dexels.grus.MultipartRequest newrequest = new org.dexels.grus.MultipartRequest(request, tempPath);
            org.dexels.grus.MultipartRequest mpr = (org.dexels.grus.MultipartRequest) newrequest;

            System.out.println("adapterPath = " + adapterPath);
            String command = newrequest.getParameter("command");
            String forward = newrequest.getParameter("forward");

            System.out.println("command = " + command);
            if (command.equals("upload_jar")) {
                String error = "";
                File file = mpr.getFile("jar");

                if (file != null) {
                    System.out.println("Jar Filename = " + file.getName());
                    boolean beta = (newrequest.getParameter("beta") != null);

                    System.out.println("Using beta file");
                    error = "Upload complete";
                    try {
                        copyFile(file, adapterPath, beta);
                    } catch (Exception e) {
                        error = e.getMessage();
                    }
                    Dispatcher.doClearCache();
                } else {
                    error = "Select a file first";
                }
                response.sendRedirect(forward + "?error=" + error);
            } else if (command.equals("beta_jar")) {
                String fileName = newrequest.getParameter("jar");

                System.out.println("Beta Jar Filename = " + fileName);
                File file = new File(adapterPath + "/" + fileName + "_beta");
                boolean foundJar = file.canRead();

                System.out.println("Opened file: " + fileName);
                file.renameTo(new File(adapterPath + "/" + fileName));
                System.out.println("File renamed");
                Dispatcher.doClearCache();
                String error = "";

                if (!foundJar)
                    error = "Could not publish jar. File not found: " + fileName
                            + "_beta";
                else
                    error = "Published jar";
                response.sendRedirect(forward + "?error=" + error);
            } else if (command.equals("beta_script")) {

                boolean foundScript;

                String fileName = newrequest.getParameter("script");

                System.out.println("Beta Script Filename = " + fileName);

                File file = new File(scriptPath + "/" + fileName + ".tml_beta");

                System.out.println("Opened file: " + fileName + ", file = " + file);
                System.out.println("readable: " + file.canRead());
                foundScript = file.canRead();

                file.renameTo(new File(scriptPath + "/" + fileName + ".tml"));

                file = new File(scriptPath + "/" + fileName + ".xsl_beta");

                System.out.println("Opened file: " + fileName + ", file = " + file);
                System.out.println("readable: " + file.canRead());
                if (!foundScript)
                    foundScript = file.canRead();

                file.renameTo(new File(scriptPath + "/" + fileName + ".xsl"));

                System.out.println("Script renamed");
                String error;

                if (!foundScript)
                    error = "Could not publish script. Script '" + fileName
                            + ".xsl_beta', or '" + fileName
                            + ".tml_beta' not found";
                else
                    error = "Published script";

                response.sendRedirect(forward + "?error=" + error);

            } else if (command.equals("reload")) {
                Dispatcher.doClearCache();
                String error = "Reload succeeded";

                response.sendRedirect(forward + "?error=" + error);
            } else if (command.equals("upload_script")) {
                File file = mpr.getFile("script");
                String error = "";

                if (file != null) {
                    System.out.println("Script Filename = " + file.getName());
                    boolean beta = (newrequest.getParameter("beta") != null);

                    System.out.println("Using beta file");
                    error = "Upload complete";
                    try {
                        copyFile(file, scriptPath, beta);
                    } catch (Exception e) {
                        error = e.getMessage();
                    }
                } else {
                    error = "Select a file first";
                }
                response.sendRedirect(forward + "?error=" + error);
            } else if (command.equals("update_repository")) {
                String className = newrequest.getParameter("repository");
                String error = "Repository update completed";

                try {
                    Dispatcher.updateRepository(className);
                } catch (Exception e) {
                    error = e.getMessage();
                }
                response.sendRedirect(forward + "?error=" + error);
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

                    if ((name == null) || (group == null) || (name.equals(""))
                            || group.equals(""))
                        response.sendRedirect(forward + "?error=Wrong input");
                    try {
                        a.addService(new Access(-1, -1, -1, "", "", "", "", ""), name, Integer.parseInt(group));
                    } catch (Exception se) {
                        response.sendRedirect(forward + "?error=" + se.getMessage());
                    }
                }
                PrintWriter out = response.getWriter();

                response.setContentType("text/html");
                out.println("<html>");
                out.println("Webservice added<BR>");
                out.println("<A HREF='admin.jsp?>goto Admin</A>");
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
                out.println("<A HREF='admin.jsp?>goto Admin</A>");
                out.println("</html>");
            }
        } catch (NavajoException ne) {
            throw new ServletException(ne);
        }
    }

    /** Clean up resources*/
    public void destroy() {}
}
