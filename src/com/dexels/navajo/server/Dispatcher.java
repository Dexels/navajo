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

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.dexels.grus.DbConnectionBroker;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.PersistenceManagerFactory;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public final class Dispatcher {

//    private static Repository repository = null;

    private Navajo inMessage = null;
    private static boolean matchCN = false;
//    private static HashMap properties = null;
    private static boolean useAuthorisation = true;
    private static final String defaultDispatcher = "com.dexels.navajo.server.GenericHandler";
    private static final String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";

    private static int requestCount = 0;
    private static double totalAuthorsationTime = 0.0;
    private static double totalRuleValidationTime = 0.0;
    private static double totalDispatchTime = 0.0;

//    private static NavajoClassLoader loader = null;
//    private static NavajoClassLoader betaLoader = null;

//    private static String betaUser = "";

    private static NavajoConfig navajoConfig = null;

//    private static PersistenceManager persistenceManager = null;

    private static boolean initialized = false;

    private static Logger logger = Logger.getLogger( Dispatcher.class );

    private static boolean debugOn = false;

    private String properDir(String in) {
        String result = in + (in.endsWith("/") ? "" : "/");

        System.out.println(result);
        return result;
    }

    private synchronized void init(InputStream in, InputStreamReader fileInputStreamReader) throws SystemException {
        if (!initialized) {
            try {
                // Read configuration file.
                System.out.println("Trying to read configuration file");
                navajoConfig = new NavajoConfig(in, fileInputStreamReader);
                initialized = true;
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


    /**
     * Create instance of new ClassLoader to enforce class reloading.
     */
    public synchronized static void doClearCache() {
      navajoConfig.doClearCache();
    }

    public synchronized static void updateRepository(String repositoryClass) throws java.lang.ClassNotFoundException {
        doClearCache();
        Repository newRepository = RepositoryFactory.getRepository(navajoConfig.getClassloader(), repositoryClass, navajoConfig);

        System.out.println("New repository = " + newRepository);
        if (newRepository == null)
            throw new ClassNotFoundException("Could not find repository class: " + repositoryClass);
        else
            navajoConfig.setRepository(newRepository);
    }

    public static final NavajoConfig getNavajoConfig() {
      return navajoConfig;
    }

    public static final NavajoClassLoader getNavajoClassLoader() {
        return navajoConfig.getClassloader();
    }

    public static final Repository getRepository() {
        return navajoConfig.getRepository();
    }

    private Navajo dispatch(String handler, Navajo in, Access access, Parameters parms) throws  Exception {

        try {
            Navajo out = null;
            if (access==null) {
              System.err.println("Null access!!!");
            }

            if (debugOn)
              logger.log(Priority.DEBUG, "Dispatching request to " + handler + "...");
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
            out = (Navajo) navajoConfig.getPersistenceManager().get(sh, access.rpcName + "_" +
                                  access.rpcUser + "_" + in.persistenceKey(), expirationInterval,
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
        if (debugOn)
            logger.log(Priority.DEBUG, "Time spent in " + part + ": " + (total / 1000.0) + " seconds");
        navajoConfig.getRepository().logTiming(access, part, total);
    }

    private void addParameters(Navajo doc, Parameters parms) throws NavajoException {

        Message msg = NavajoFactory.getInstance().createMessage(doc, "__parms__");

        doc.addMessage(msg);

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


    public static boolean doMatchCN() {
        return matchCN;
    }


    /**
     * Handle fatal errors. Log the error message to the Database.
     */
    private Navajo errorHandler(Access access, Throwable e, Navajo inMessage) throws FatalException {

        if (debugOn) {
          logger.log(Priority.DEBUG, e.toString());
          logger.log(Priority.DEBUG, e.getMessage());
        }

        e.printStackTrace(System.out);

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
    private Navajo generateErrorMessage(Access access, String message, int code, int level) throws FatalException {

        if (debugOn)
          logger.log(Priority.DEBUG, "in generateErrorMessage(): message = " + message + ", code = " + code + ", level = " + level);

        if (message == null)
            message = "Null pointer exception";

        try {
            Navajo outMessage = NavajoFactory.getInstance().createNavajo();

            Message errorMessage = NavajoFactory.getInstance().createMessage(outMessage, "error");

            outMessage.addMessage(errorMessage);

            Property prop = NavajoFactory.getInstance().createProperty(outMessage, "message", Property.STRING_PROPERTY,
                    message, 1, "Message", Property.DIR_OUT);

            errorMessage.addProperty(prop);

            prop = NavajoFactory.getInstance().createProperty(outMessage, "code", Property.INTEGER_PROPERTY, code + "", 1, "Code", Property.DIR_OUT);
            errorMessage.addProperty(prop);

            prop = NavajoFactory.getInstance().createProperty(outMessage, "level", Property.INTEGER_PROPERTY, level + "", 1, "Level", Property.DIR_OUT);
            errorMessage.addProperty(prop);

            if (access != null) {
                prop = NavajoFactory.getInstance().createProperty(outMessage, "access_id", Property.INTEGER_PROPERTY, access.accessID + "", 1, "Access id", Property.DIR_OUT);
                errorMessage.addProperty(prop);
            }

            return outMessage;
        } catch (Exception e) {
            throw new FatalException(e.getMessage());
        }
    }

    private Parameters evaluateParameters(Parameter[] parameters, Navajo message) throws SystemException {
        if (parameters == null)
            return null;

        Parameters params = new Parameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter p = (Parameter) parameters[i];
            params.store(p.name, p.expression, p.type, p.condition, message);
        }
        return params;
    }

    public void setUseAuthorisation(boolean a) {
      useAuthorisation = a;
    }

    private Message[] checkConditions(ConditionData[] conditions, Navajo message, Navajo outMessage) throws NavajoException, SystemException, UserException {

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

    public Navajo handle(Navajo inMessage) throws FatalException {

        Access access = null;
        Navajo outMessage = null;

        try {
            this.inMessage = inMessage;
            String rpcName = "";
            String rpcUser = "";
            String rpcPassword = "";

            // long end, start;
            // double total;
            // double authorisationTime = 0.0;
            // double validationTime = 0.0;
            // double dispatchTime = 0.0;

            requestCount++;

            // start = System.currentTimeMillis();

            // inMessage.getMessageBuffer().write(System.out);

            Header header = inMessage.getHeader();
            if (debugOn) logger.log(Priority.DEBUG, "Parsed request: " + inMessage);
            rpcName = header.getRPCName();
            if (debugOn) logger.log(Priority.DEBUG, "Got RPC name: " + rpcName);
            rpcUser = header.getRPCUser();
            if (debugOn) logger.log(Priority.DEBUG, "Got RPC user: " + rpcUser);
            rpcPassword = header.getRPCPassword();
            if (debugOn) logger.log(Priority.DEBUG, "Got RPC password: " + rpcPassword);

            System.err.println("IN DISPATCHER().handle() FOR NAVASERVICE = " + rpcName);

            String userAgent = header.getUserAgent();

            logger.log(Priority.DEBUG, "Got user_agent: " + userAgent);
            String address = header.getIPAddress();

            if (debugOn) System.out.println("GOT ADDRESS: " + address);

            if (debugOn) logger.log(Priority.DEBUG, "Got address: " + address);
            String host = header.getHostName();

            if (debugOn) logger.log(Priority.DEBUG, "Got host: " + host);

            /**
             * Phase II: Authorisation/Authentication of the user. Is the user known and valid and may it use the
             * specified service?
             * Also log the access.
             */

            if (useAuthorisation) {
                // access = repository.authorizeUser(myBroker, rpcUser, rpcPassword, rpcName, userAgent, address, host, true);
                access = navajoConfig.getRepository().authorizeUser(rpcUser, rpcPassword, rpcName, inMessage);
            } else {
                if (debugOn) logger.log(Priority.WARN, "Switched off authorisation mode");
                access = new Access(0, 0, 0, rpcUser, rpcName, "", "", "");
            }

            if (rpcUser.equalsIgnoreCase(navajoConfig.getBetaUser())) {
                access.betaUser = true;
                if (debugOn) logger.log(Priority.INFO, "BETA USER ACCESS!");
            }

            if (debugOn) {
              logger.log(Priority.DEBUG, "USER_ID = " + access.userID);
              logger.log(Priority.DEBUG, "SERVICE_ID = " + access.serviceID);
            }

            if ((access.userID == -1) || (access.serviceID == -1)) { // ACCESS NOT GRANTED.

                String errorMessage = "";

                if (access.userID == -1)
                    errorMessage = "Cannot authenticate user: " + rpcUser;
                else
                    errorMessage = "Cannot authorise use of: " + rpcName;
                outMessage = generateErrorMessage(access, errorMessage, SystemException.NOT_AUTHORISED, 1);

                // end = System.currentTimeMillis();
                // authorisationTime = (end - start)/1000.0;
                return outMessage;

            } else {   // ACCESS GRANTED.

                access.setMyDispatcher(this);

                // Check for lazy message control.
                access.setLazyMessages(header.getLazyMessages());

                if (debugOn) logger.log(Priority.DEBUG, "Received TML document.");
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

                if (debugOn) logger.log(Priority.DEBUG, "Got local parameters : " + parms);

                /**
                 end = System.currentTimeMillis();
                 validationTime = (end - start)/1000.0;

                 start = System.currentTimeMillis();
                 */

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

                System.err.println("LEAVING DISPATCHER().handle() FOR NAVASERVICE = " + rpcName);

                return outMessage;
            }
        } catch (UserException ue) {
            try {
                outMessage = generateErrorMessage(access, ue.getMessage(), ue.code, 1);
                return outMessage;
            } catch (Exception ee) {
                return errorHandler(access, ee, inMessage);
            }
        } catch (SystemException se) {
            try {
                outMessage = generateErrorMessage(access, se.getMessage(), se.code, 1);
                return outMessage;
            } catch (Exception ee) {
                return errorHandler(access, ee, inMessage);
            }
        } catch (Exception e) {
            return errorHandler(access, e, inMessage);
        }
    }

}
