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
import java.sql.*;
import java.net.InetAddress;

import org.dexels.grus.DbConnectionBroker;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.PersistenceManagerFactory;
import com.dexels.navajo.logger.*;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public final class Dispatcher {

//    private static Repository repository = null;

    private Navajo inMessage = null;
    protected static boolean matchCN = false;
//    private static HashMap properties = null;
    public static HashSet accessSet = new HashSet();
    public static boolean useAuthorisation = true;
    private static final String defaultDispatcher = "com.dexels.navajo.server.GenericHandler";
    private static final String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";
    public static java.util.Date startTime = new java.util.Date();

    public static long requestCount = 0;
    private static double totalAuthorsationTime = 0.0;
    private static double totalRuleValidationTime = 0.0;
    private static double totalDispatchTime = 0.0;


//    private static NavajoClassLoader loader = null;
//    private static NavajoClassLoader betaLoader = null;

//    private static String betaUser = "";

    private static NavajoConfig navajoConfig = null;

//    private static PersistenceManager persistenceManager = null;

    private static boolean initialized = false;

    private static NavajoLogger logger = null;

    private static boolean debugOn = false;

    private static String keyStore;
    private static String keyPassword;

    private String properDir(String in) {
        String result = in + (in.endsWith("/") ? "" : "/");
        //System.err.println(result);
        return result;
    }

    private synchronized void init(InputStream in, InputStreamReader fileInputStreamReader) throws SystemException {
        if (!initialized) {
            try {
                // Read configuration file.
                //System.err.println("Trying to read configuration file");
                navajoConfig = new NavajoConfig(in, fileInputStreamReader);
                debugOn = navajoConfig.isLogged();
                //System.err.println("in Dispatcher init(), debugOn = " + debugOn);
                initialized = true;
                logger = NavajoConfig.getNavajoLogger(Dispatcher.class);
                //System.err.println("logger = " + logger);
            } catch (Exception e) {
                e.printStackTrace();
                initialized = false;
            }
        }
    }

    public Dispatcher(String configurationPath, InputStreamReader fileInputStreamReader) throws NavajoException {
        try {
                init(new FileInputStream(configurationPath), fileInputStreamReader);
        } catch (Exception se) {
            throw NavajoFactory.getInstance().createNavajoException(se);
        }
    }

    public Dispatcher(URL configurationUrl, InputStreamReader fileInputStreamReader) throws NavajoException {
        try {
          init(configurationUrl.openStream(), fileInputStreamReader);
        } catch (Exception se) {
            throw NavajoFactory.getInstance().createNavajoException(se);
        }
    }

    public static final void setKeyStore(String s) {
      keyStore = s;
    }

    public static final void setKeyPassword(String s) {
      keyPassword = s;
    }

    public static final String getKeyStore() {
      return keyStore;
    }

    public static final String getKeyPassword() {
      return keyPassword;
    }

    /**
     * Create instance of new ClassLoader(s) to enforce class reloading.
     */
    public synchronized static final void doClearCache() {
      navajoConfig.doClearCache();
      GenericHandler.doClearCache();
      System.runFinalization();
      System.gc();
    }

    public synchronized static final void updateRepository(String repositoryClass) throws java.lang.ClassNotFoundException {
        doClearCache();
        Repository newRepository = RepositoryFactory.getRepository(navajoConfig.getClassloader(), repositoryClass, navajoConfig);
        System.err.println("New repository = " + newRepository);
        if (newRepository == null)
            throw new ClassNotFoundException("Could not find repository class: " + repositoryClass);
        else
            navajoConfig.setRepository(newRepository);
    }

    public static final NavajoConfig getNavajoConfig() {
      return navajoConfig;
    }

    public static final NavajoClassLoader getNavajoClassLoader() {
      if (navajoConfig == null)
        return null;
      else
        return navajoConfig.getClassloader();
    }

    public static final Repository getRepository() {
        return navajoConfig.getRepository();
    }

    private final Navajo dispatch(String handler, Navajo in, Access access, Parameters parms) throws  Exception {


        //System.err.println("Dispatcher.dispatch(), webservice = " + access.rpcName);

        try {
            Navajo out = null;
            if (access==null) {
              System.err.println("Null access!!!");
            }

            Class c;
            if (access==null) {
              c = navajoConfig.getClassloader().getClass(handler);
            } else {
              if (access.betaUser) {
                c = navajoConfig.getBetaClassLoader().getClass(handler);
              } else {
                c = navajoConfig.getClassloader().getClass(handler);
              }
            }

            c = (access.betaUser)
                    ? navajoConfig.getBetaClassLoader().getClass(handler)
                    : navajoConfig.getClassloader().getClass(handler);
            ServiceHandler sh = (ServiceHandler) c.newInstance();

            if (access.betaUser) {
                sh.setInput(in, access, parms, navajoConfig);
            } else {
                sh.setInput(in, access, parms, navajoConfig);
            }
            long expirationInterval = in.getHeader().getExpirationInterval();

            // Remove password from in to create password independend persistenceKey.
            in.getHeader().setRPCPassword("");
            String key = null;
            if (expirationInterval==-1) {
              key = "";
            } else {
              key = in.persistenceKey();
            }

            out = (Navajo) navajoConfig.getPersistenceManager().get(sh, access.rpcName + "_" +
                                  access.rpcUser + "_" + key, expirationInterval,
                                  (expirationInterval != -1));
            return out;
        } catch (java.lang.ClassNotFoundException cnfe) {
            throw new SystemException(-1, cnfe.getMessage(), cnfe);
        } catch (java.lang.IllegalAccessException iae) {
            throw new SystemException(-1, iae.getMessage(), iae);
        } catch (java.lang.InstantiationException ie) {
            throw new SystemException(-1, ie.getMessage(), ie);
        }
    }

    private final void timeSpent(Access access, int part, long total) throws SystemException {
        if (debugOn)
            logger.log(NavajoPriority.DEBUG, "Time spent in " + part + ": " + (total / 1000.0) + " seconds");
        navajoConfig.getRepository().logTiming(access, part, total);
    }

    private final void addParameters(Navajo doc, Parameters parms) throws NavajoException {

        Message msg = doc.getMessage("__parms__");
        if (msg == null) {
          msg = NavajoFactory.getInstance().createMessage(doc, "__parms__");
          doc.addMessage(msg);
        }

        if (parms != null) {
            Enumeration all = parms.keys();

            // "Enrich" document with paramater message block "__parms__"
            while (all.hasMoreElements()) {
                String key = (String) all.nextElement();
                Object value = parms.getValue(key);
                String type = parms.getType(key);
                Property prop = NavajoFactory.getInstance().createProperty(doc, key, type, Util.toString(value, type), 0, "", Property.DIR_OUT);
                msg.addProperty(prop);
            }
        }
    }


    public static final boolean doMatchCN() {
        return matchCN;
    }


    /**
     * Handle fatal errors. Log the error message to the Database.
     */
    private final Navajo errorHandler(Access access, Throwable e, Navajo inMessage) throws FatalException {

        if (access != null) {
            try {
                String message = e.getClass().toString() + ": " + e.getMessage()
                        + ", " + e.toString() + ", " + e.getLocalizedMessage();

                if (message.equalsIgnoreCase(""))
                    message = "Undefined Error";

                StringWriter swriter = new StringWriter();
                PrintWriter writer = new PrintWriter(swriter);

                e.printStackTrace(writer);

                message += swriter.getBuffer().toString();

                message += "\n";

                swriter = new StringWriter();
                writer = new PrintWriter(swriter);
                // inMessage.getMessageBuffer().write(writer);
                inMessage.write(writer);

                message += swriter.getBuffer().toString();

            } catch (NavajoException tbe) {
                throw new FatalException(tbe.getMessage());
            }
        }

        try {
            Navajo out = generateErrorMessage(access, "System error occured", -1, 1, e);

            return out;
        } catch (Exception ne) {
            ne.printStackTrace();
            throw new FatalException(ne.getMessage());
        }
    }

    /**
     * Generate a Navajo authorization message and log the error to the Database.
     */
    private final Navajo generateAuthorizationErrorMessage(Access access, AuthorizationException ae) throws FatalException {

        try {
            Navajo outMessage = NavajoFactory.getInstance().createNavajo();
            Message errorMessage = NavajoFactory.getInstance().createMessage(outMessage, (ae.isNotAuthorized() ?
                AuthorizationException.AUTHORIZATION_ERROR_MESSAGE : AuthorizationException.AUTHENTICATION_ERROR_MESSAGE));
            outMessage.addMessage(errorMessage);
            Property prop = NavajoFactory.getInstance().createProperty(outMessage, "Message", Property.STRING_PROPERTY, ae.getMessage(), 1, "Message", Property.DIR_OUT);
            errorMessage.addProperty(prop);
            prop = NavajoFactory.getInstance().createProperty(outMessage, "User", Property.STRING_PROPERTY, ae.getUser(), 1, "User", Property.DIR_OUT);
            errorMessage.addProperty(prop);

            return outMessage;
        } catch (Exception e) {
            throw new FatalException(e.getMessage());
        }
    }

    /**
     * Generate a Navajo error message and log the error to the Database.
     */
    private final Navajo generateErrorMessage(Access access, String message, int code, int level, Throwable t) throws FatalException {

      if (debugOn) {

        if (access != null) {
          if (t != null)
            logger.log(NavajoPriority.DEBUG,
                       "in generateErrorMessage(), rpc = " + access.rpcName +
                       " usr = " + access.rpcUser + ", message: " +
                     message, t);
          else
            logger.log(NavajoPriority.DEBUG,
                      "in generateErrorMessage(), rpc = " + access.rpcName +
                      " usr = " + access.rpcUser + ", message: " +
                    message);

        }
        else {
          if (t != null)
            logger.log(NavajoPriority.DEBUG, "in generateErrorMessage(): " + message, t);
          else
            logger.log(NavajoPriority.DEBUG, "in generateErrorMessage(): " + message);

        }
      }

        if (message == null)
            message = "Null pointer exception";

        try {
            Navajo outMessage = NavajoFactory.getInstance().createNavajo();

            Message errorMessage = NavajoFactory.getInstance().createMessage(outMessage, Constants.ERROR_MESSAGE);

            outMessage.addMessage(errorMessage);

            Property prop = NavajoFactory.getInstance().createProperty(outMessage, "message", Property.STRING_PROPERTY,
                    message, 200, "Message", Property.DIR_OUT);

            errorMessage.addProperty(prop);

            prop = NavajoFactory.getInstance().createProperty(outMessage, "code", Property.INTEGER_PROPERTY, code + "", 100, "Code", Property.DIR_OUT);
            errorMessage.addProperty(prop);

            prop = NavajoFactory.getInstance().createProperty(outMessage, "level", Property.INTEGER_PROPERTY, level + "", 100, "Level", Property.DIR_OUT);
            errorMessage.addProperty(prop);

            if (access != null) {
                prop = NavajoFactory.getInstance().createProperty(outMessage, "access_id", Property.INTEGER_PROPERTY, access.accessID + "", 100, "Access id", Property.DIR_OUT);
                errorMessage.addProperty(prop);
            }

            return outMessage;
        } catch (Exception e) {
            throw new FatalException(e.getMessage());
        }
    }

    private final Parameters evaluateParameters(Parameter[] parameters, Navajo message) throws SystemException {
        if (parameters == null)
            return null;

        Parameters params = new Parameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter p = (Parameter) parameters[i];
            params.store(p.name, p.expression, p.type, p.condition, message);
        }
        return params;
    }

    public final void setUseAuthorisation(boolean a) {
      useAuthorisation = a;
    }

    private final Message[] checkConditions(ConditionData[] conditions, Navajo message, Navajo outMessage) throws NavajoException, SystemException, UserException {

        if (conditions == null)
            return null;

        ArrayList messages = new ArrayList();

        boolean ok;
        int index = 0;

        for (int i = 0; i < conditions.length; i++) {
            ConditionData condition = conditions[i];
            boolean valid = false;

            try {
                valid = com.dexels.navajo.parser.Condition.evaluate(condition.condition, inMessage);
            } catch (com.dexels.navajo.parser.TMLExpressionException ee) {
                throw new UserException(-1, "Invalid condition: " + ee.getMessage());
                //valid = true;
            }
            if (!valid) {
                ok = false;
                String eval = com.dexels.navajo.parser.Expression.replacePropertyValues(condition.condition, inMessage);
                Message msg = NavajoFactory.getInstance().createMessage(outMessage, "failed" + (index++));
                Property prop0 = NavajoFactory.getInstance().createProperty(outMessage, "Id", Property.STRING_PROPERTY,
                        condition.id+"", 0, "", Property.DIR_OUT);
                Property prop1 = NavajoFactory.getInstance().createProperty(outMessage, "Description", Property.STRING_PROPERTY,
                        condition.comment, 0, "", Property.DIR_OUT);
                Property prop2 = NavajoFactory.getInstance().createProperty(outMessage, "FailedExpression", Property.STRING_PROPERTY,
                        condition.condition, 0, "", Property.DIR_OUT);
                Property prop3 = NavajoFactory.getInstance().createProperty(outMessage, "EvaluatedExpression", Property.STRING_PROPERTY,
                        eval, 0, "", Property.DIR_OUT);

                msg.addProperty(prop0);
                msg.addProperty(prop1);
                msg.addProperty(prop2);
                msg.addProperty(prop3);
                messages.add(msg);
            }
        }

        if (messages.size() > 0) {
            Message[] msgArray = new Message[messages.size()];

            messages.toArray(msgArray);
            return msgArray;
        } else
            return null;
    }

    public final Navajo handle(Navajo inMessage) throws FatalException {
      return handle(inMessage, null);
    }

    public final Navajo handle(Navajo inMessage, Object userCertificate) throws FatalException {

        Access access = null;
        Navajo outMessage = null;

        System.err.println("Access set size is " + accessSet.size() );

        try {
            this.inMessage = inMessage;
            String rpcName = "";
            String rpcUser = "";
            String rpcPassword = "";

            requestCount++;

            Header header = inMessage.getHeader();
            rpcName = header.getRPCName();
            rpcUser = header.getRPCUser();
            rpcPassword = header.getRPCPassword();

            String userAgent = header.getUserAgent();
            String address = header.getIPAddress();
            String host = header.getHostName();

            /**
             * Phase II: Authorisation/Authentication of the user. Is the user known and valid and may it use the
             * specified service?
             * Also log the access.
             */

            if (useAuthorisation) {
                try {
                  access = navajoConfig.getRepository().authorizeUser(rpcUser, rpcPassword, rpcName, inMessage, userCertificate);
                }
                catch (AuthorizationException ex) {
                  System.err.println("IN LINE 487 OF DISPATCHER, CAUGHT AUTHORIZATIONEXCEPTION");
                  outMessage = generateAuthorizationErrorMessage(access, ex);
                  System.err.println("RETURNING");
                  outMessage.write(System.err);
                  return outMessage;
                }
                catch (SystemException se) {
                  outMessage = generateErrorMessage(access, se.getMessage(), SystemException.NOT_AUTHORISED, 1, new Exception("NOT AUTHORISED"));
                  return outMessage;
                }
            } else {
                if (debugOn) logger.log(NavajoPriority.WARN, "Switched off authorisation mode");
                access = new Access(0, 0, 0, rpcUser, rpcName, "", "", "", null);
            }

            if (rpcUser.equalsIgnoreCase(navajoConfig.getBetaUser())) {
                access.betaUser = true;
                if (debugOn) logger.log(NavajoPriority.INFO, "BETA USER ACCESS!");
            }


            if ((access.userID == -1) || (access.serviceID == -1)) { // ACCESS NOT GRANTED.

                String errorMessage = "";

                if (access.userID == -1)
                    errorMessage = "Cannot authenticate user: " + rpcUser;
                else
                    errorMessage = "Cannot authorise use of: " + rpcName;
                outMessage = generateErrorMessage(access, errorMessage, SystemException.NOT_AUTHORISED, 1, new Exception("NOT AUTHORISED"));
                return outMessage;

            } else {   // ACCESS GRANTED.

                accessSet.add(access);
                access.setMyDispatcher(this);

                // Check for lazy message control.
                access.setLazyMessages(header.getLazyMessages());

                Parameters parms = null;

                /**
                 * Phase III: Check conditions for user/service combination using the 'condition' table in the database and
                 * the incoming Navajo document.
                 */
                ConditionData[] conditions = navajoConfig.getRepository().getConditions(access);

                outMessage = NavajoFactory.getInstance().createNavajo();
                Message[] failed = checkConditions(conditions, inMessage, outMessage);

                if (failed != null) {
                    Message msg = NavajoFactory.getInstance().createMessage(outMessage, "ConditionErrors");

                    outMessage.addMessage(msg);
                    msg.setType(Message.MSG_TYPE_ARRAY);

                    for (int i = 0; i < failed.length; i++) {
                        msg.addMessage((Message) failed[i]);
                    }
                    return outMessage;
                }

                /**
                 * Phase IV: Get application specific parameters for user.
                 */
                Parameter[] pl = navajoConfig.getRepository().getParameters(access);

                parms = evaluateParameters(pl, inMessage);

                // Add parameters to __parms__ message.
                addParameters(inMessage, parms);

                /**
                 * Phase VI: Dispatch to proper servlet.
                 */


                if (useAuthorisation) {
                    outMessage = dispatch(navajoConfig.getRepository().getServlet(access), inMessage, access, parms);
                } else {
                    if (rpcName.startsWith("navajo"))
                        outMessage = dispatch(defaultNavajoDispatcher, inMessage, access, parms);
                    else
                        outMessage = dispatch(defaultDispatcher, inMessage, access, parms);
                }

                return outMessage;
            }
        } catch (AuthorizationException aee) {
          outMessage = generateAuthorizationErrorMessage(access, aee);
          return outMessage;
        } catch (UserException ue) {
            try {
                outMessage = generateErrorMessage(access, ue.getMessage(), ue.code, 1, (ue.t != null ? ue.t : ue));
                return outMessage;
            } catch (Exception ee) {
                ee.printStackTrace();
                logger.log(NavajoPriority.DEBUG, ee.getMessage(), ee);
                return errorHandler(access, ee, inMessage);
            }
        } catch (SystemException se) {
            se.printStackTrace(System.err);
            System.err.println("CAUGHT SYSTEMEXCEPTION IN DISPATCHER()!!");
            try {
                outMessage = generateErrorMessage(access, se.getMessage(), se.code, 1, (se.t != null ? se.t : se));
                return outMessage;
            } catch (Exception ee) {
                ee.printStackTrace();
                logger.log(NavajoPriority.DEBUG, ee.getMessage(), ee);
                return errorHandler(access, ee, inMessage);
            }
        } catch (Exception e) {
                e.printStackTrace(System.err);
                logger.log(NavajoPriority.DEBUG, e.getMessage(), e);
            return errorHandler(access, e, inMessage);
        } finally {
          if (access != null) {
            accessSet.remove(access);
          }
          access = null;
        }
    }

    public void finalize() {
      System.err.println("In finalize() Dispatcher object");
    }
}
