package com.dexels.navajo.server;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
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

  /** Version information **/
  public static final String vendor = "Dexels";
  public static final String product = "Navajo Integrator";
  public static final String version = "Navajo Integrator Release 2004.11.10 - Production";
  public static String serverId = null;

  private Navajo inMessage = null;
  protected static boolean matchCN = false;
  public static HashSet accessSet = new HashSet();
  public static boolean useAuthorisation = true;
  private static final String defaultDispatcher = "com.dexels.navajo.server.GenericHandler";
  private static final String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";
  public static java.util.Date startTime = new java.util.Date();

  public static long requestCount = 0;
  private static double totalAuthorsationTime = 0.0;
  private static double totalRuleValidationTime = 0.0;
  private static double totalDispatchTime = 0.0;
  private static NavajoConfig navajoConfig = null;
  private static boolean initialized = false;

  private static NavajoLogger logger = null;

  private static boolean debugOn = false;

  private static String keyStore;
  private static String keyPassword;

  private String properDir(String in) {
    String result = in + (in.endsWith("/") ? "" : "/");
    return result;
  }

  /**
   * Initialize the Dispatcher.
   *
   * @param in inputstream containing the NavajoConfig information.
   * @param fileInputStreamReader specifies the reader to user.
   * @throws SystemException
   */
  private synchronized void init(InputStream in, InputStreamReader fileInputStreamReader) throws SystemException {
    if (!initialized) {
      try {
        // Read configuration file.

        navajoConfig = new NavajoConfig(in, fileInputStreamReader);
        debugOn = navajoConfig.isLogged();

        initialized = true;
        logger = NavajoConfig.getNavajoLogger(Dispatcher.class);

      }
      catch (Exception e) {
        e.printStackTrace();
        initialized = false;
      }
    }
  }

  /**
   * Constructor for file based configuration (server.xml).
   *
   * @param configurationPath specifies the file location of the configuration file (server.xml).
   *
   * @param fileInputStreamReader
   * @throws NavajoException
   */
  public Dispatcher(String configurationPath,
                    InputStreamReader fileInputStreamReader) throws
      NavajoException {
    try {
      if (!initialized) {
        FileInputStream fis = new FileInputStream(configurationPath);
        init(fis, fileInputStreamReader);
        fis.close();
      }
    }
    catch (Exception se) {
      se.printStackTrace(System.err);
      throw NavajoFactory.getInstance().createNavajoException(se);
    }
  }

  /**
   * Constructor for URL based configuration.
   *
   * @param configurationUrl
   * @param fileInputStreamReader
   * @throws NavajoException
   */
  public Dispatcher(URL configurationUrl,
                    InputStreamReader fileInputStreamReader) throws
      NavajoException {
    try {
      if (!initialized) {
        init(configurationUrl.openStream(), fileInputStreamReader);
      }
    }
    catch (Exception se) {
      throw NavajoFactory.getInstance().createNavajoException(se);
    }
  }

  /**
   * Set the location of the certificate keystore.
   *
   * @param s
   */
  public static final void setKeyStore(String s) {
    keyStore = s;
  }

  /**
   * Set the password for the certificate keystore.
   *
   * @param s
   */
  public static final void setKeyPassword(String s) {
    keyPassword = s;
  }

  /**
   * Get the location of the keystore.
   *
   * @return
   */
  public static final String getKeyStore() {
    return keyStore;
  }

  /**
   * Get the password of the keystore.
   *
   * @return
   */
  public static final String getKeyPassword() {
    return keyPassword;
  }

  /**
   * Clears all Navajo classloaders.
   *
   */
  public synchronized static final void doClearCache() {
    navajoConfig.doClearCache();
    GenericHandler.doClearCache();
    System.runFinalization();
    System.gc();
  }

  /**
   * Update the Navajo repository that is used for authentication/authorization.
   *
   * @param repositoryClass the fully specified classname of the repository.
   *
   * @throws java.lang.ClassNotFoundException
   */
  public synchronized static final void updateRepository(String repositoryClass) throws
      java.lang.ClassNotFoundException {
    doClearCache();
    Repository newRepository = RepositoryFactory.getRepository(navajoConfig.
        getClassloader(), repositoryClass, navajoConfig);
    System.err.println("New repository = " + newRepository);
    if (newRepository == null) {
      throw new ClassNotFoundException("Could not find repository class: " +
                                       repositoryClass);
    }
    else {
      navajoConfig.setRepository(newRepository);
    }
  }

  /**
   * Get the (singleton) NavajoConfig object reference.
   *
   * @return
   */
  public static final NavajoConfig getNavajoConfig() {
    return navajoConfig;
  }

  /**
   * Get the default NavajoClassLoader. Note that when hot-compile is enabled each webservice context uses
   * its own NavajoClassLoader instance.
   *
   * @return
   */
  public static final NavajoClassLoader getNavajoClassLoader() {
    if (navajoConfig == null) {
      return null;
    }
    else {
      return navajoConfig.getClassloader();
    }
  }

  /**
   * Get a reference to the Navajo repository object.
   *
   * @return
   */
  public static final Repository getRepository() {
    return navajoConfig.getRepository();
  }

  /**
   * Process a webservice using a special handler class.
   *
   * @param handler the class the will process the webservice.
   * @param in the request Navajo document.
   * @param access
   * @param parms
   * @return
   * @throws Exception
   */
  private final Navajo dispatch(String handler, Navajo in, Access access, Parameters parms) throws Exception {

    try {
      Navajo out = null;
      if (access == null) {
        System.err.println("Null access!!!");
      }

      Class c;
      if (access == null) {
        c = navajoConfig.getClassloader().getClass(handler);
      }
      else {
        if (access.betaUser) {
          c = navajoConfig.getBetaClassLoader().getClass(handler);
        }
        else {
          c = navajoConfig.getClassloader().getClass(handler);
        }
      }

      c = (access.betaUser)
          ? navajoConfig.getBetaClassLoader().getClass(handler)
          : navajoConfig.getClassloader().getClass(handler);
      ServiceHandler sh = (ServiceHandler) c.newInstance();

      if (access.betaUser) {
        sh.setInput(in, access, parms, navajoConfig);
      }
      else {
        sh.setInput(in, access, parms, navajoConfig);
      }
      long expirationInterval = in.getHeader().getExpirationInterval();

      // Remove password from in to create password independend persistenceKey.
      in.getHeader().setRPCPassword("");
      String key = null;
      if (expirationInterval == -1) {
        key = "";
      }
      else {
        key = in.persistenceKey();
      }

      out = (Navajo) navajoConfig.getPersistenceManager().get(sh,
          access.rpcName + "_" +
          access.rpcUser + "_" + key, expirationInterval,
          (expirationInterval != -1));
      return out;
    }
    catch (java.lang.ClassNotFoundException cnfe) {
      throw new SystemException( -1, cnfe.getMessage(), cnfe);
    }
    catch (java.lang.IllegalAccessException iae) {
      throw new SystemException( -1, iae.getMessage(), iae);
    }
    catch (java.lang.InstantiationException ie) {
      throw new SystemException( -1, ie.getMessage(), ie);
    }
  }

  /**
   * Somewhat deprecated method to log timings. Use navajo store for proper statistics.
   *
   * @param access
   * @param part
   * @param total
   * @throws SystemException
   */
  private final void timeSpent(Access access, int part, long total) throws
      SystemException {
    if (debugOn) {
      logger.log(NavajoPriority.DEBUG,
                 "Time spent in " + part + ": " + (total / 1000.0) + " seconds");
    }
    navajoConfig.getRepository().logTiming(access, part, total);
  }

  private final void addParameters(Navajo doc, Parameters parms) throws
      NavajoException {

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
        Property prop = NavajoFactory.getInstance().createProperty(doc, key,
            type, Util.toString(value, type), 0, "", Property.DIR_OUT);
        msg.addProperty(prop);
      }
    }
  }

  public static final boolean doMatchCN() {
    return matchCN;
  }

  /**
   * Handles errors.
   *
   * @param access
   * @param e
   * @param inMessage
   * @return the response error Navajo document.
   *
   * @throws FatalException
   */
  private final Navajo errorHandler(Access access, Throwable e,
                                    Navajo inMessage) throws FatalException {

    if (access != null) {
      try {
        String message = e.getClass().toString() + ": " + e.getMessage()
            + ", " + e.toString() + ", " + e.getLocalizedMessage();

        if (message.equalsIgnoreCase("")) {
          message = "Undefined Error";

        }
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

      }
      catch (NavajoException tbe) {
        throw new FatalException(tbe.getMessage());
      }
    }

    try {
      Navajo out = generateErrorMessage(access, "System error occured", -1, 1, e);

      return out;
    }
    catch (Exception ne) {
      ne.printStackTrace();
      throw new FatalException(ne.getMessage());
    }
  }

  /**
   * Generate a Navajo authorization error response.
   *
   * @param access
   * @param ae
   * @return
   * @throws FatalException
   */
  private final Navajo generateAuthorizationErrorMessage(Access access,
      AuthorizationException ae) throws FatalException {

    try {
      Navajo outMessage = NavajoFactory.getInstance().createNavajo();
      Message errorMessage = NavajoFactory.getInstance().createMessage(
          outMessage, (ae.isNotAuthorized() ?
                       AuthorizationException.AUTHORIZATION_ERROR_MESSAGE :
                       AuthorizationException.AUTHENTICATION_ERROR_MESSAGE));
      outMessage.addMessage(errorMessage);
      Property prop = NavajoFactory.getInstance().createProperty(outMessage,
          "Message", Property.STRING_PROPERTY, ae.getMessage(), 1, "Message",
          Property.DIR_OUT);
      errorMessage.addProperty(prop);
      prop = NavajoFactory.getInstance().createProperty(outMessage, "User",
          Property.STRING_PROPERTY, ae.getUser(), 1, "User", Property.DIR_OUT);
      errorMessage.addProperty(prop);

      access.setException(ae);
      return outMessage;
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      throw new FatalException(e.getMessage());
   }
  }

  /**
   * Generate a Navajo error message and log the error to the Database.
   */
  private final Navajo generateErrorMessage(Access access, String message,
                                            int code, int level, Throwable t) throws
      FatalException {

    if (debugOn) {

      if (access != null) {
        if (t != null) {
          logger.log(NavajoPriority.DEBUG,
                     "in generateErrorMessage(), rpc = " + access.rpcName +
                     " usr = " + access.rpcUser + ", message: " +
                     message, t);
        }
        else {
          logger.log(NavajoPriority.DEBUG,
                     "in generateErrorMessage(), rpc = " + access.rpcName +
                     " usr = " + access.rpcUser + ", message: " +
                     message);

        }
      }
      else {
        if (t != null) {
          logger.log(NavajoPriority.DEBUG,
                     "in generateErrorMessage(): " + message, t);
        }
        else {
          logger.log(NavajoPriority.DEBUG,
                     "in generateErrorMessage(): " + message);

        }
      }
    }

    if (message == null) {
      message = "Null pointer exception";

    }
    try {
      Navajo outMessage = NavajoFactory.getInstance().createNavajo();

      Message errorMessage = NavajoFactory.getInstance().createMessage(
          outMessage, Constants.ERROR_MESSAGE);

      outMessage.addMessage(errorMessage);

      Property prop = NavajoFactory.getInstance().createProperty(outMessage,
          "message", Property.STRING_PROPERTY,
          message, 200, "Message", Property.DIR_OUT);

      errorMessage.addProperty(prop);

      prop = NavajoFactory.getInstance().createProperty(outMessage, "code",
          Property.INTEGER_PROPERTY, code + "", 100, "Code", Property.DIR_OUT);
      errorMessage.addProperty(prop);

      prop = NavajoFactory.getInstance().createProperty(outMessage, "level",
          Property.INTEGER_PROPERTY, level + "", 100, "Level", Property.DIR_OUT);
      errorMessage.addProperty(prop);

      if (access != null) {
        prop = NavajoFactory.getInstance().createProperty(outMessage,
            "access_id", Property.INTEGER_PROPERTY, access.accessID + "", 100,
            "Access id", Property.DIR_OUT);
        errorMessage.addProperty(prop);
        access.setException(t);
      }

      return outMessage;
    }
    catch (Exception e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Evaluate user specific parameters.
   *
   * @param parameters
   * @param message
   * @return
   * @throws SystemException
   */
  private final Parameters evaluateParameters(Parameter[] parameters,
                                              Navajo message) throws SystemException {
    if (parameters == null) {
      return null;
    }

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

  /**
   * Deprecated method to check validation errors. Use <validations> block inside webservice script instead.
   *
   * @param conditions
   * @param inMessage
   * @param outMessage
   * @return
   * @throws NavajoException
   * @throws SystemException
   * @throws UserException
   */
  public static final Message[] checkConditions(ConditionData[] conditions,
                                                Navajo inMessage,
                                                Navajo outMessage) throws
      NavajoException, SystemException, UserException {

    if (conditions == null) {
      return null;
    }

    ArrayList messages = new ArrayList();

    boolean ok;
    int index = 0;

    for (int i = 0; i < conditions.length; i++) {
      ConditionData condition = conditions[i];
      boolean valid = false;

      try {
        valid = com.dexels.navajo.parser.Condition.evaluate(condition.condition,
            inMessage);
      }
      catch (com.dexels.navajo.parser.TMLExpressionException ee) {
        throw new UserException( -1, "Invalid condition: " + ee.getMessage());
        //valid = true;
      }
      if (!valid) {
        ok = false;
        String eval = com.dexels.navajo.parser.Expression.replacePropertyValues(
            condition.condition, inMessage);
        Message msg = NavajoFactory.getInstance().createMessage(outMessage,
            "failed" + (index++));
        Property prop0 = NavajoFactory.getInstance().createProperty(outMessage,
            "Id", Property.STRING_PROPERTY,
            condition.id + "", 0, "", Property.DIR_OUT);
        Property prop1 = NavajoFactory.getInstance().createProperty(outMessage,
            "Description", Property.STRING_PROPERTY,
            condition.comment, 0, "", Property.DIR_OUT);
        Property prop2 = NavajoFactory.getInstance().createProperty(outMessage,
            "FailedExpression", Property.STRING_PROPERTY,
            condition.condition, 0, "", Property.DIR_OUT);
        Property prop3 = NavajoFactory.getInstance().createProperty(outMessage,
            "EvaluatedExpression", Property.STRING_PROPERTY,
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
    }
    else {
      return null;
    }
  }

  /**
   * Handle a webservice (without ClientInfo object given).
   *
   * @param inMessage
   * @param userCertificate
   * @return
   * @throws FatalException
   */
  public final Navajo handle(Navajo inMessage, Object userCertificate) throws FatalException {
     return handle(inMessage, userCertificate, null);
  }

  /**
   * Handle a webservice (without ClientInfo and certificate).
   *
   * @param inMessage
   * @return
   * @throws FatalException
   */
  public final Navajo handle(Navajo inMessage) throws FatalException {
    return handle(inMessage, null);
  }

  /**
   * Handle a webservice.
   *
   * @param inMessage
   * @param userCertificate
   * @param clientInfo
   * @return
   * @throws FatalException
   */
  public final Navajo handle(Navajo inMessage, Object userCertificate, ClientInfo clientInfo) throws
      FatalException {

    Access access = null;
    Navajo outMessage = null;

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

      long startAuth = System.currentTimeMillis();

      if (useAuthorisation) {
        try {
          access = navajoConfig.getRepository().authorizeUser(rpcUser, rpcPassword, rpcName, inMessage, userCertificate);
          if (clientInfo != null && access != null) {
            access.ipAddress = clientInfo.getIP();
            access.hostName = clientInfo.getHost();
            access.parseTime = clientInfo.getParseTime();
            access.requestEncoding = clientInfo.getEncoding();
            access.compressedReceive  = clientInfo.isCompressedRecv();
            access.compressedSend = clientInfo.isCompressedSend();
            access.contentLength = clientInfo.getContentLength();
            access.created = clientInfo.getCreated();
          }
        }
        catch (AuthorizationException ex) {
          System.err.println("IN LINE 487 OF DISPATCHER, CAUGHT AUTHORIZATIONEXCEPTION");
          outMessage = generateAuthorizationErrorMessage(access, ex);
          outMessage.write(System.err);
          return outMessage;
        }
        catch (SystemException se) {
          outMessage = generateErrorMessage(access, se.getMessage(),
                                            SystemException.NOT_AUTHORISED, 1,
                                            new Exception("NOT AUTHORISED"));
          return outMessage;
        }
      }
      else {
        if (debugOn) {
          logger.log(NavajoPriority.WARN, "Switched off authorisation mode");
        }
        access = new Access(0, 0, 0, rpcUser, rpcName, "", "", "", null);
      }

      if (rpcUser.equalsIgnoreCase(navajoConfig.getBetaUser())) {
        access.betaUser = true;
        if (debugOn) {
          logger.log(NavajoPriority.INFO, "BETA USER ACCESS!");
        }
      }

      if ( (access.userID == -1) || (access.serviceID == -1)) { // ACCESS NOT GRANTED.

        String errorMessage = "";

        if (access.userID == -1) {
          errorMessage = "Cannot authenticate user: " + rpcUser;
        }
        else {
          errorMessage = "Cannot authorise use of: " + rpcName;
        }
        outMessage = generateErrorMessage(access, errorMessage, SystemException.NOT_AUTHORISED, 1, new Exception("NOT AUTHORISED"));
        return outMessage;

      }
      else { // ACCESS GRANTED.

        access.authorisationTime = (int) ( System.currentTimeMillis() - startAuth );
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
        if (conditions != null) {
          outMessage = NavajoFactory.getInstance().createNavajo();
          Message[] failed = checkConditions(conditions, inMessage, outMessage);

          if (failed != null) {
            Message msg = NavajoFactory.getInstance().createMessage(outMessage, "ConditionErrors");
            outMessage.addMessage(msg);
            msg.setType(Message.MSG_TYPE_ARRAY);
            for (int i = 0; i < failed.length; i++) {
              msg.addMessage( (Message) failed[i]);
            }
            return outMessage;
          }
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
        }
        else {
          if (rpcName.startsWith("navajo")) {
            outMessage = dispatch(defaultNavajoDispatcher, inMessage, access, parms);
          }
          else {
            outMessage = dispatch(defaultDispatcher, inMessage, access, parms);
          }
        }

        return outMessage;
      }
    }
    catch (AuthorizationException aee) {
      outMessage = generateAuthorizationErrorMessage(access, aee);
      return outMessage;
    }
    catch (UserException ue) {
      try {
        outMessage = generateErrorMessage(access, ue.getMessage(), ue.code, 1, (ue.t != null ? ue.t : ue));
        return outMessage;
      }
      catch (Exception ee) {
        ee.printStackTrace();
        logger.log(NavajoPriority.DEBUG, ee.getMessage(), ee);
        return errorHandler(access, ee, inMessage);
      }
    }
    catch (SystemException se) {
      se.printStackTrace(System.err);
      System.err.println("CAUGHT SYSTEMEXCEPTION IN DISPATCHER()!!");
      try {
        outMessage = generateErrorMessage(access, se.getMessage(), se.code, 1, (se.t != null ? se.t : se));
        return outMessage;
      }
      catch (Exception ee) {
        ee.printStackTrace();
        logger.log(NavajoPriority.DEBUG, ee.getMessage(), ee);
        return errorHandler(access, ee, inMessage);
      }
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
      logger.log(NavajoPriority.DEBUG, e.getMessage(), e);
      return errorHandler(access, e, inMessage);
    }
    finally {
      if (access != null) {
        access.setFinished();
        if (getNavajoConfig().getStatisticsRunner() != null) {
          // Give asynchronous statistics runner a new access object to persist.
          getNavajoConfig().getStatisticsRunner().addAccess(access);
        }
        accessSet.remove(access);
      }

    }
  }

  public void finalize() {
    System.err.println("In finalize() Dispatcher object");
  }
  public String getServerId() {
    return serverId;
  }
  public void setServerId(String serverId) {
    if (this.serverId == null) {
      this.serverId = serverId;
    }
  }
}