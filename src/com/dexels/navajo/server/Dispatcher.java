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
import com.dexels.navajo.loader.NavajoClassLoader;

import utils.FileUtils;
import java.sql.*;
import java.net.InetAddress;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.PersistenceManagerFactory;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public class Dispatcher {

  private static Repository repository = null;

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

  private static NavajoClassLoader loader = null;
  private static NavajoClassLoader betaLoader = null;

  private static String adapterPath = "";

  private static String betaUser = "";

  private static PersistenceManager persistenceManager = null;

  static {
    try {

        properties = ResourceBundle.getBundle("navajoserver");
        if (properties == null) {
          Util.debugLog("MAKE REFERENCE TO RESOURCE FIRST");
        }

        String persistenceConfigurationFile = "";
        try {
          persistenceConfigurationFile = properties.getString("persistent_configuration");
        } catch (Exception e) {
          System.out.println("Disabled document persistence");
        }
        persistenceManager = PersistenceManagerFactory.getInstance(persistenceConfigurationFile);

        adapterPath = properties.getString("adapter_path");
        loader = new NavajoClassLoader(adapterPath);
        betaLoader = new NavajoClassLoader(adapterPath, true);
        System.out.println("loader = " + loader);
        System.out.println("betaLoader = " + betaLoader);

        repository = RepositoryFactory.getRepository(properties);
        try {
          betaUser = properties.getString("beta_user");
        } catch (Exception e) {
          System.out.println("No beta user specified");
        }
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
      } catch (Exception e) {
        e.printStackTrace();
        Util.debugLog(e.getMessage());
      }
  }

  public Dispatcher() throws NavajoException {
    if (properties == null)
      throw new NavajoException("Could not find navajoserver.properties file");
    if (repository == null)
      throw new NavajoException("Could not find repository");
  }


  /**
   * Create instance of new ClassLoader to enforce class reloading.
   */
  public synchronized static void doClearCache() {
    loader = new NavajoClassLoader(adapterPath);
    betaLoader = new NavajoClassLoader(adapterPath, true);
    System.runFinalization();
    System.gc();
    System.out.println("Cleared cache");
  }

  public synchronized static void updateRepository(String repositoryClass) throws java.lang.ClassNotFoundException {
    doClearCache();
    Repository newRepository = RepositoryFactory.getRepository(properties, loader, repositoryClass);
    System.out.println("New repository = " + newRepository);
    if (newRepository == null)
      throw new ClassNotFoundException("Could not find repository class: " + repositoryClass);
    else
      repository = newRepository;
  }

  public static NavajoClassLoader getNavajoClassLoader() {
    return loader;
  }

  public static Repository getRepository() {
    return repository;
  }

  private Navajo dispatch(String handler, Navajo in, Access access, Parameters parms) throws  Exception
  {
    System.out.println("current directory: " + System.getProperty("user.dir"));
    try {
      Navajo out = null;
      Util.debugLog(this, "Dispatching request to " + handler + "...");
      Class c = (access.betaUser) ? betaLoader.getClass(handler) : loader.getClass(handler);
      ServiceHandler sh = (ServiceHandler) c.newInstance();
      if (access.betaUser) {
        sh.setInput(in, access, parms, properties, repository, betaLoader);
      }
      else {
        sh.setInput(in, access, parms, properties, repository, loader);
      }
      long expirationInterval = getExpirationInterval(in);
      System.out.println("expirationInterval = " + expirationInterval);
      out = (Navajo) persistenceManager.get(sh, access.rpcName + "_" + access.rpcUser + "_" + in.persistenceKey(), expirationInterval,
                                            (expirationInterval != -1));
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
    repository.logTiming(access, part, total);
  }

  private void addParameters(Navajo doc, Parameters parms) throws NavajoException {

    Message msg = Message.create(doc, "__parms__");
    doc.addMessage(msg);

    if (parms != null) {
      Enumeration all = parms.keys();
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
   * Get the expiration interval.
   */

  public static long getExpirationInterval(Navajo message) {
    String s = "";
    Element n = (Element)
	   XMLutils.findNode(message.getMessageBuffer(), "transaction");
    s = n.getAttribute("expiration_interval");
    if ((s == null) || (s.equals("")))
      return -1;
    return Long.parseLong(s);
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

        repository.logAction(access, Authorisation.LOG_SYSTEM_ERROR, message);

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
        repository.logAction(access, level, message);

      return outMessage;
    } catch (Exception e) {
      throw new FatalException(e.getMessage());
    }
  }

  private Parameters evaluateParameters(Parameter [] parameters, Navajo message) throws SystemException {
    if (parameters == null)
      return null;

    Parameters params = new Parameters();
    for (int i = 0; i < parameters.length; i++) {
        Parameter p = (Parameter) parameters[i];
        params.store(p.name, p.value, p.type, p.condition, message);
    }
    return params;
  }

  private String [] checkConditions(ConditionData [] conditions, Navajo message) throws SystemException {

      if (conditions == null)
        return null;

      ArrayList messages = new ArrayList();

      boolean ok;
      for (int i = 0; i < conditions.length; i++) {
          ConditionData condition = conditions[i];
          boolean valid = false;
          try {
            valid = com.dexels.navajo.parser.Condition.evaluate(condition.condition, inMessage);
          } catch (com.dexels.navajo.parser.TMLExpressionException ee) {
            valid = true;
          }
          if (!valid) {
            ok = false;
            messages.add(condition.comment);
          }
      }

      if (messages.size() > 0) {
        String [] msgArray = new String[messages.size()];
        messages.toArray(msgArray);
        return msgArray;
      } else
        return null;
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
      //long end, start;
      //double total;
      //double authorisationTime = 0.0;
      //double validationTime = 0.0;
      //double dispatchTime = 0.0;

      requestCount++;

      //start = System.currentTimeMillis();

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

    if (useAuthorisation) {
      //access = repository.authorizeUser(myBroker, rpcUser, rpcPassword, rpcName, userAgent, address, host, true);
      access = repository.authorizeUser(rpcUser, rpcPassword, rpcName);
    }
    else {
      access = new Access(0, 0, 0, rpcUser, rpcName, "", "", "");
    }

    if (rpcUser.equalsIgnoreCase(betaUser)) {
        access.betaUser = true;
        System.out.println("BETA USER ACCESS!");
    }

    Util.debugLog(this, "USER_ID = " + access.userID);
    Util.debugLog(this, "SERVICE_ID = " + access.serviceID);

    if ((access.userID == -1) || (access.serviceID == -1)) { // ACCESS NOT GRANTED.

         String errorMessage = "";
         if (access.userID == -1)
            errorMessage = "Cannot authenticate user: " + rpcUser;
         else
            errorMessage = "Cannot authorise use of: " +rpcName;
         outMessage = generateErrorMessage(access, errorMessage, SystemException.NOT_AUTHORISED, Authorisation.LOG_SYSTEM_ERROR);

         //end = System.currentTimeMillis();
         //authorisationTime = (end - start)/1000.0;
         return outMessage;

    } else {   // ACCESS GRANTED.

      repository.logAction(access, Authorisation.LOG_ACCESS, "Access granted");
      Util.debugLog(this, "Received TML document.");
      Parameters parms = null;

      /**
      end = System.currentTimeMillis();
      authorisationTime = (end - start)/1000.0;

      start = System.currentTimeMillis();
      */

        /**
          * Phase III: Check conditions for user/service combination using the 'condition' table in the database and
          * the incoming Navajo document.
          */
        ConditionData [] conditions = repository.getConditions(access);
        String [] failed = checkConditions(conditions, inMessage);
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
        Parameter [] pl = repository.getParameters(access);
        parms = evaluateParameters(pl, inMessage);

        // Add parameters to __parms__ message.
        addParameters(inMessage, parms);

        Util.debugLog(this, "Got local parameters : " + parms);

      /**
      end = System.currentTimeMillis();
      validationTime = (end - start)/1000.0;

      start = System.currentTimeMillis();
      */

      /**
       * Phase VI: Dispatch to proper servlet.
       */

      if (useAuthorisation) {
        outMessage = dispatch(repository.getServlet(access), inMessage, access, parms);
      }
      else {
        if (rpcName.startsWith("navajo"))
          outMessage = dispatch(defaultNavajoDispatcher, inMessage, access, parms);
        else
          outMessage = dispatch(defaultDispatcher, inMessage, access, parms);
      }

      /**
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
      */

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
