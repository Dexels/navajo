package com.dexels.navajo.server;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import javax.servlet.ServletRequest;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.xml.XMLutils;
import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.xml.*;
import utils.FileUtils;
import java.sql.*;
import java.net.InetAddress;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public class Dispatcher {

  private Authorisation authorisation = null;
  private static DbConnectionBroker myBroker = null;
  private Navajo inMessage = null;
  private static boolean matchCN = false;
  private static ResourceBundle properties = null;
  private static boolean useAuthorisation = true;
  private static String defaultDispatcher = "com.dexels.navajo.server.GenericHandler";
  private static String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";

  private static int requestCount = 0;
  private static double totalAuthorsationTime = 0.0;
  private static double totalRuleValidationTime = 0.0;
  private static double totalDispatchTime = 0.0;

  static {
    try {
        properties = ResourceBundle.getBundle("navajoserver");
        if (properties == null) {
          Util.debugLog("MAKE REFERENCE TO RESOURCE FIRST");
        }

        Util.debugLog("Trying to open connection to database");
        Util.debugLog(properties.getString("authorisation_driver"));
        Util.debugLog(properties.getString("authorisation_url"));
        Util.debugLog(properties.getString("authorisation_user"));
        Util.debugLog(properties.getString("authorisation_pwd")+"");
        Util.debugLog(properties.getString("navajo_logon"));

        myBroker = new DbConnectionBroker(properties.getString("authorisation_driver"),
                                          properties.getString("authorisation_url"),
                                          properties.getString("authorisation_user"),
                                          properties.getString("authorisation_pwd"),
                                          2, 25, "/tmp/log.db", 0.1);

        matchCN = properties.getString("security.matchCN").equals("true");
        try {
          String model = properties.getString("authorisation_model");
          if (model != null) {
            if (model.equals("off")) {
              useAuthorisation = false;
              System.out.println("authorisation switched off");
            }
          }
        } catch (Exception e) {

        }

        Connection con = myBroker.getConnection();
        if (con == null) {
          myBroker = null;
        } else {
          Util.debugLog("Got test connection: " + con);
          myBroker.freeConnection(con);
          Util.debugLog("Opened connection to AUTHORISATION DB");
        }

      } catch (Exception e) {
        Util.debugLog(e.getMessage());
        myBroker = null;
      }
  }

  public Dispatcher() throws NavajoException {
    if (properties == null)
      throw new NavajoException("Could not find navajoserver.properties file");
    if (myBroker == null)
      throw new NavajoException("Could not open database [driver = " + properties.getString("authorisation_driver") + ", url = " +
                                  properties.getString("authorisation_url") + "]");

    String dbms = properties.getString("authorisation_dbms");
    if (dbms.equals("mysql"))
      this.authorisation = new Authorisation(Authorisation.DBMS_MYSQL);
    else if (dbms.equals("mssql"))
      this.authorisation = new Authorisation(Authorisation.DBMS_MSSQL);
  }

  private Navajo dispatch(String handler, Navajo in, Access access, Parameters parms) throws  UserException,
                                                                                                SystemException,
                                                                                                NavajoException
  {
    try {
      Navajo out = null;
      Util.debugLog(this, "Dispatching request to " + handler + "...");
      Class c = Class.forName(handler);
      ServiceHandler sh = (ServiceHandler) c.newInstance();
      out = sh.doService(in, access, parms, properties);
      return out;
    } catch (java.lang.ClassNotFoundException cnfe) {
      throw new SystemException(-1, cnfe.getMessage());
    } catch (java.lang.IllegalAccessException iae) {
      throw new SystemException(-1, iae.getMessage());
    } catch (java.lang.InstantiationException ie) {
      throw new SystemException(-1, ie.getMessage());
    }
  }

  private void timeSpent(Access access, int part, long total) throws SystemException {
    Util.debugLog(this, "Time spent in " + part + ": " + (total/1000.0) + " seconds");
    authorisation.logTiming(access, part, total);
  }

  private void addParameters(Navajo doc, Parameters parms) throws NavajoException {
    Enumeration all = parms.keys();
    Message msg = Message.create(doc, "__parms__");
    doc.addMessage(msg);
    // "Enrich" document with paramater message block "__parms__"
    while (all.hasMoreElements()) {
      String key = (String) all.nextElement();
      String value = parms.getValue(key);
      String type = parms.getType(key);
      Property prop = Property.create(doc, key, type, value, 0, "", Property.DIR_OUT);
      msg.addProperty(prop);
    }
    //XMLDocumentUtils.toXML( doc.getMessageBuffer(),null,null,new StreamResult(System.out));
  }

   /**
   * Get the name of the user_agent from a Navajo message.
   */
  private String getUserAgent(Navajo message) {

    String value = "";

    Element n = (Element)
	   XMLutils.findNode(message.getMessageBuffer(), "http");
    if (n != null)
      value = n.getAttribute("user_agent");

    return value;
  }

   /**
   * Get the ip address from a Navajo message.
   */
  private String getIPAddress(Navajo message) {

    String value = "";

    Element n = (Element)
	   XMLutils.findNode(message.getMessageBuffer(), "client");
    if (n != null)
      value = n.getAttribute("address");

    return value;
  }

   /**
   * Get the hostname from a Navajo message.
   */
  private String getHostName(Navajo message) {

    String value = "";

    Element n = (Element)
	   XMLutils.findNode(message.getMessageBuffer(), "client");
    if (n != null)
      value = n.getAttribute("host");

    return value;
  }

  /**
   * Get the name of the service (RPC name) from a Navajo message.
   */
  public static String getRPCName(Navajo message) {

    String rpcName = "";
    Element n = (Element)
	   XMLutils.findNode(message.getMessageBuffer(), "transaction");
    rpcName = n.getAttribute("rpc_name");

    return rpcName;
  }

  public static boolean doMatchCN() {
    return matchCN;
  }

  /**
   * Get the name of the user (RPC user) from a Navajo message.
   */
  public static String getRPCUser(Navajo message) {

    String rpcUser = "";

    Element n = (Element)
	   XMLutils.findNode(message.getMessageBuffer(), "transaction");
    rpcUser = n.getAttribute("rpc_usr");

    return rpcUser;
  }

  /**
   * Get the password of the user (RPC password) from a Navajo message.
   */
   public static String getRPCPassword(Navajo message) {

    String rpcPwd = "";

    Element n = (Element)
	   XMLutils.findNode(message.getMessageBuffer(), "transaction");
    rpcPwd = n.getAttribute("rpc_pwd");

    return rpcPwd;
  }

   /**
   * Handle fatal errors. Log the error message to the Database.
   */
  private Navajo errorHandler(Access access, Throwable e, Navajo inMessage) throws FatalException
  {
    Util.debugLog(this, e.toString());
    Util.debugLog(this, e.getMessage());

    e.printStackTrace(System.out);

    if (access != null) {
      try {
        String message = e.getClass().toString() + ": " + e.getMessage()+ ", " +
                         e.toString() + ", " + e.getLocalizedMessage();
        if (message.equalsIgnoreCase(""))
          message = "Undefined Error";

        StringWriter swriter = new StringWriter();
        PrintWriter writer = new PrintWriter(swriter);
        e.printStackTrace(writer);

        message += swriter.getBuffer().toString();

        message += "\n";

        swriter = new StringWriter();
        writer = new PrintWriter(swriter);
//        inMessage.getMessageBuffer().write(writer);
        XMLDocumentUtils.toXML(inMessage.getMessageBuffer(),null,null,new StreamResult(writer));

        message += swriter.getBuffer().toString();

        authorisation.logAction(access, Authorisation.LOG_SYSTEM_ERROR, message);

      } catch (SystemException sqle) {
        throw new FatalException(sqle.getMessage());
      } catch (NavajoException tbe){
        throw new FatalException(tbe.getMessage());
      }
    }

    try {
      Navajo out = generateErrorMessage(access, "System error occured", -1, 1);
      return out;
    } catch (Exception ne) {
      ne.printStackTrace();
      throw new FatalException(ne.getMessage());
    }
  }


  /**
   * Generate a Navajo error message and log the error to the Database.
   */
  private Navajo generateErrorMessage(Access access, String message, int code, int level) throws FatalException
  {

    Util.debugLog("in generateErrorMessage(): message = " + message + ", code = " + code + ", level = " + level);

    if (message == null)
      message = "Null pointer exception";

    try {
      Navajo outMessage = new Navajo();

      Message errorMessage = Message.create(outMessage, "error");
      outMessage.addMessage(errorMessage);

      Property prop = Property.create(outMessage, "message", Property.STRING_PROPERTY,
                                       message, 1, "Message", Property.DIR_OUT);
      errorMessage.addProperty(prop);

      prop = Property.create(outMessage, "code", Property.INTEGER_PROPERTY, code+"", 1, "Code", Property.DIR_OUT);
      errorMessage.addProperty(prop);

      prop = Property.create(outMessage, "level", Property.INTEGER_PROPERTY, level+"", 1, "Level", Property.DIR_OUT);
      errorMessage.addProperty(prop);

      if (access != null) {
        prop = Property.create(outMessage, "access_id", Property.INTEGER_PROPERTY, access.accessID+"", 1, "Access id", Property.DIR_OUT);
        errorMessage.addProperty(prop);
      }

      if (access != null)
        authorisation.logAction(access, level, message);

      return outMessage;
    } catch (Exception e) {
      throw new FatalException(e.getMessage());
    }
  }

  public Navajo handle(Navajo inMessage) throws FatalException
  {

    Access access = null;
    Navajo outMessage = null;
    try {
      this.inMessage = inMessage;
      String rpcName = "";
      String rpcUser = "";
      String rpcPassword = "";
      long end, start;
      double total;
      double authorisationTime = 0.0;
      double validationTime = 0.0;
      double dispatchTime = 0.0;

      requestCount++;

      start = System.currentTimeMillis();

      //inMessage.getMessageBuffer().write(System.out);

      Util.debugLog(this, "Parsed request: " + inMessage);
      rpcName = getRPCName(inMessage);
      Util.debugLog(this, "Got RPC name: " + rpcName);
      rpcUser = getRPCUser(inMessage);
      Util.debugLog(this, "Got RPC user: " + rpcUser);
      rpcPassword = getRPCPassword(inMessage);
      Util.debugLog(this, "Got RPC password: " + rpcPassword);

      String userAgent = getUserAgent(inMessage);
      Util.debugLog(this, "Got user_agent: " + userAgent);
      String address = getIPAddress(inMessage);
      Util.debugLog(this, "Got address: " + address);
      String host = getHostName(inMessage);
      Util.debugLog(this, "Got host: " + host);

    /**
     * Phase II: Authorisation/Authentication of the user. Is the user known and valid and may it use the
     * specified service?
     * Also log the access.
     */

    if (useAuthorisation)
      access = authorisation.authorizeUser(myBroker, rpcUser, rpcPassword, rpcName, userAgent, address, host, true);
    else
      access = new Access(0, 0, 0, rpcUser, rpcName, "", "", "", myBroker, authorisation.currentDBMS);

    Util.debugLog(this, "USER_ID = " + access.userID);
    Util.debugLog(this, "SERVICE_ID = " + access.serviceID);

    if ((access.userID == -1) || (access.serviceID == -1)) { // ACCESS NOT GRANTED.

         String errorMessage = "";
         if (access.userID == -1)
            errorMessage = "Cannot authenticate user: " + rpcUser;
         else
            errorMessage = "Cannot authorise use of: " +rpcName;
         outMessage = generateErrorMessage(access, errorMessage, SystemException.NOT_AUTHORISED, Authorisation.LOG_SYSTEM_ERROR);

         end = System.currentTimeMillis();
         authorisationTime = (end - start)/1000.0;
         return outMessage;

    } else {   // ACCESS GRANTED.

      authorisation.logAction(access, Authorisation.LOG_ACCESS, "Access granted");
      Util.debugLog(this, "Received TML document.");
      Parameters parms = null;

      end = System.currentTimeMillis();
      authorisationTime = (end - start)/1000.0;

      start = System.currentTimeMillis();


        /**
          * Phase III: Check conditions for user/service combination using the 'condition' table in the database and
          * the incoming Navajo document.
          */
        String [] failed = authorisation.checkConditions(access, inMessage);
        if (failed != null) {
           outMessage = new Navajo();
           Message msg = Message.create(outMessage, "conditionerrors");
           outMessage.addMessage(msg);
           for (int i = 0; i < failed.length; i++) {
              Property prop = Property.create(outMessage, "failed"+i, Property.STRING_PROPERTY, failed[i], 0, "", Property.DIR_OUT);
              msg.addProperty(prop);
           }
           return outMessage;
        }

        /**
          * Phase IV: Get application specific parameters for user.
          */
        parms = authorisation.getParameters(access, inMessage);
        // Add parameters to __parms__ message.
        addParameters(inMessage, parms);

        Util.debugLog(this, "Got local parameters : " + parms);


      end = System.currentTimeMillis();
      validationTime = (end - start)/1000.0;

      start = System.currentTimeMillis();

      /**
       * Phase VI: Dispatch to proper servlet.
       */

      if (useAuthorisation) {
        outMessage = dispatch(authorisation.getServlet(access, rpcName), inMessage, access, parms);
      }
      else {
        if (rpcName.startsWith("navajo"))
          outMessage = dispatch(defaultNavajoDispatcher, inMessage, access, parms);
        else
          outMessage = dispatch(defaultDispatcher, inMessage, access, parms);
      }

      end = System.currentTimeMillis();

      dispatchTime = (end - start)/1000.0;

      this.totalAuthorsationTime += authorisationTime;
      this.totalRuleValidationTime += validationTime;
      this.totalDispatchTime += dispatchTime;

      System.out.println("\nTIMING SUMMARY (service/user=" + rpcName + "/" + rpcUser+"):\n");
      System.out.println("Authorisation/authentication phase: " + authorisationTime + " (avg=" +
                                                                (this.totalAuthorsationTime/(double) this.requestCount) + ")");
      System.out.println("Business rule phase               : " + validationTime + " (avg=" +
                                                                (this.totalRuleValidationTime/(double) this.requestCount) + ")");
      System.out.println("Dispatch phase                    : " + dispatchTime + " (avg=" +
                                                                (this.totalDispatchTime/(double) this.requestCount) + ")");
      double gt = authorisationTime + validationTime + dispatchTime;
      System.out.println("Total                             : " + gt + " (avg=" +
                                                                (this.totalAuthorsationTime+this.totalDispatchTime+this.totalDispatchTime)/(double) this.requestCount +
                                                                ")");
      System.out.println("-----------------------------------------------------------------------------");

      return outMessage;
     }
    } catch (UserException ue) {
      try {
        outMessage = generateErrorMessage(access, ue.getMessage(), ue.code, Authorisation.LOG_USER_ERROR);
        return outMessage;
      } catch (Exception ee) {
        return errorHandler(access, ee, inMessage);
      }
    } catch (SystemException se) {
      try {
        outMessage = generateErrorMessage(access, se.getMessage(), se.code, Authorisation.LOG_SYSTEM_ERROR);
        return outMessage;
      } catch (Exception ee) {
        return errorHandler(access, ee, inMessage);
      }
    } catch (Exception e) {
      return errorHandler(access, e, inMessage);
    }
  }
}
