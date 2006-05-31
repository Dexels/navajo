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

import java.util.*;
import java.net.*;

//import org.dexels.grus.DbConnectionBroker;

import com.dexels.navajo.document.*;
import com.dexels.navajo.scheduler.WebserviceListener;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.integrity.Worker;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.Lock;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.lockguard.LocksExceeded;

import com.dexels.navajo.logger.*;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public final class Dispatcher {

  /** Version information **/
  public static final String VERSION = "$Id$";
  public static final String vendor = "Dexels BV";
  public static final String product = "Navajo Integrator";
  public static final String version = "Navajo Integrator Release 2005.11.10";
  public static String serverId = null;

  /**
   * Unique dispatcher instance.
   */
  private static Dispatcher instance = null;
  
  //private Navajo inMessage = null;
  protected  boolean matchCN = false;
  public  Set accessSet = Collections.synchronizedSet(new HashSet());
  public  boolean useAuthorisation = true;
  private  final String defaultDispatcher =
      "com.dexels.navajo.server.GenericHandler";
  private static final String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";
  public static java.util.Date startTime = new java.util.Date();

  public  long requestCount = 0;
  private NavajoConfig navajoConfig = null;
  private  boolean initialized = false;

  private  NavajoLogger logger = null;

  private  boolean debugOn = false;

  private  String keyStore;
  private  String keyPassword;

  public static int rateWindowSize = 20;
  public static double requestRate = 0.0;
  private  long[] rateWindow = new long[rateWindowSize];

  /**
   * Initialize the Dispatcher.
   *
   * @param in inputstream containing the NavajoConfig information.
   * @param fileInputStreamReader specifies the reader to user.
   * @throws SystemException
   */
  private synchronized void init(InputStream in,
                                 InputStreamReader fileInputStreamReader,
                                 NavajoClassSupplier ncs) throws
      SystemException {
    if (!initialized) {
      try {
        // Read configuration file.
        navajoConfig = new NavajoConfig(in, fileInputStreamReader, ncs);
        // Startup task runner.
        navajoConfig.getTaskRunner(this);
        
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
//  private Dispatcher(String configurationPath, InputStreamReader fileInputStreamReader) throws
//      NavajoException {
//    try {
//      if (!initialized) {
//        FileInputStream fis = new FileInputStream(configurationPath);
//        init(fis, fileInputStreamReader);
//        fis.close();
//      }
//    }
//    catch (Exception se) {
//      se.printStackTrace(System.err);
//      throw NavajoFactory.getInstance().createNavajoException(se);
//    }
//  }

  /**
   * Constructor for URL based configuration.
   *
   * @param configurationUrl
   * @param fileInputStreamReader
   * @throws NavajoException
   */
  private Dispatcher(URL configurationUrl,
                    InputStreamReader fileInputStreamReader,
                    NavajoClassSupplier cl) throws
      NavajoException {
    try {
      if (!initialized) {
        init(configurationUrl.openStream(), fileInputStreamReader, cl);
//        if (cl!=null) {
//            getNavajoConfig().setClassloader(cl);
//		}
      }
    }
    catch (Exception se) {
      throw NavajoFactory.getInstance().createNavajoException(se);
    }
  }

  private Dispatcher(URL configurationUrl,
                    InputStreamReader fileInputStreamReader) throws
      NavajoException {
    this(configurationUrl, fileInputStreamReader, null);
  }

  public static Dispatcher getInstance() {
	  return instance;
  }
  
  public static Dispatcher getInstance(URL configurationUrl,
          InputStreamReader fileInputStreamReader) throws
          NavajoException  {
	  
	  if ( instance == null ) {
		  instance = new Dispatcher(configurationUrl, fileInputStreamReader);
	  }
	  
	  return instance;
	  
	  
  }
  
  /**
   * Set the location of the certificate keystore.
   *
   * @param s
   */
  public  final void setKeyStore(String s) {
    keyStore = s;
  }

  /**
   * Set the password for the certificate keystore.
   *
   * @param s
   */
  public  final void setKeyPassword(String s) {
    keyPassword = s;
  }

  /**
   * Get the location of the keystore.
   *
   * @return
   */
  public  final String getKeyStore() {
    return keyStore;
  }

  /**
   * Get the password of the keystore.
   *
   * @return
   */
  public  final String getKeyPassword() {
    return keyPassword;
  }

  public  float getRequestRate() {
    if(rateWindow[0] > 0){
      float time = (float)(rateWindow[rateWindowSize - 1] - rateWindow[0]) / (float)1000.0;
      float avg = (float) rateWindowSize/time;
      return avg;
    }
    return 0.0f;
  }

  /**
   * Clears all Navajo classloaders.
   *
   */
  public synchronized  final void doClearCache() {
    navajoConfig.doClearCache();
    GenericHandler.doClearCache();
    System.runFinalization();
    System.gc();
  }

  /**
   * Clears only the script Navajo classloaders, and leaves the jar cache alone
   *
   */

  public synchronized  final void doClearScriptCache() {
      navajoConfig.doClearScriptCache();
      GenericHandler.doClearCache();
//      System.runFinalization();
//      System.gc();
    }
  
  
  /**
   * Update the Navajo repository that is used for authentication/authorization.
   *
   * @param repositoryClass the fully specified classname of the repository.
   *
   * @throws java.lang.ClassNotFoundException
   */
  public synchronized  final void updateRepository(String repositoryClass) throws
      java.lang.ClassNotFoundException {
    doClearCache();
    Repository newRepository = RepositoryFactory.getRepository( (
        NavajoClassLoader) navajoConfig.
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
  public  final NavajoConfig getNavajoConfig() {
    return navajoConfig;
  }

  /**
   * Get the default NavajoClassLoader. Note that when hot-compile is enabled each webservice context uses
   * its own NavajoClassLoader instance.
   *
   * @return
   */
  public  final NavajoClassSupplier getNavajoClassLoader() {
    if (navajoConfig == null) {
      return null;
    }
    else {
      return (NavajoClassSupplier) navajoConfig.getClassloader();
    }
  }

  /**
   * Get a reference to the Navajo repository object.
   *
   * @return
   */
  public  final Repository getRepository() {
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
	  
	  
	  Navajo out = null;
	  if (access == null) {
		  System.err.println("Null access!!!");
	  }
	  
	  // Check for webservice transaction integrity.
	  out = navajoConfig.getIntegrityWorker( this ).getResponse(in);
	  if ( out != null ) {
		  return out;
	  }
	  
	  // Check for locks.
	  LockManager lm = navajoConfig.getLockManager( );
	  Lock [] locks = null;
	  try {
		  locks = lm.grantAccess(access);
	  } catch (LocksExceeded le) {
		  return generateErrorMessage(access, "Could not get neccessary lock(s)", SystemException.LOCKS_EXCEEDED, -1, le);
	  }
	  
	  try {
		  
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
				  access.rpcUser + "_" + key, 
				  expirationInterval,
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
	  } finally {
		  // Store response for integrity checking.
		  if ( out != null ) {
			  Worker.getInstance( this ).setResponse(in, out);
		  }
		  // Release locks.
		  if ( locks != null ) {
			  lm.removeLocks(access, locks);
		  }  
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
//  private final void timeSpent(Access access, int part, long total) throws
//      SystemException {
//    if (debugOn) {
//      logger.log(NavajoPriority.DEBUG,
//                 "Time spent in " + part + ": " + (total / 1000.0) + " seconds");
//    }
//    navajoConfig.getRepository().logTiming(access, part, total);
//  }

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

  public  final boolean doMatchCN() {
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

	String message = "";
    
	if (access != null) {
      try {
        message = e.getClass().toString() + ": " + e.getMessage()
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
      Navajo out = generateErrorMessage(access, message, SystemException.SYSTEM_ERROR, 1, e);

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
          "Message", Property.STRING_PROPERTY, ae.getMessage(), 0, "Message",
          Property.DIR_OUT);

      errorMessage.addProperty(prop);
      prop = NavajoFactory.getInstance().createProperty(outMessage, "User",
          Property.STRING_PROPERTY, ae.getUser(), 0, "User", Property.DIR_OUT);

      errorMessage.addProperty(prop);
      if (access != null) {
        access.setException(ae);
      }
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
  public  final Navajo generateErrorMessage(Access access, String message,
                                                  int code, int level,
                                                  Throwable t) throws
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
                                              Navajo message) throws
      SystemException {
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
  public  final Message[] checkConditions(ConditionData[] conditions,
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
  public final Navajo handle(Navajo inMessage, Object userCertificate) throws
      FatalException {
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
    String rpcName = "";
    String rpcUser = "";
    String rpcPassword = "";
    //String requestId = "";
    Exception myException = null;
    
    try {
//      this.inMessage = inMessage;

      requestCount++;

      for (int i = 0; i < rateWindowSize - 1; i++) {
        try{
          rateWindow[i] = rateWindow[i + 1];
        }catch(ArrayIndexOutOfBoundsException e){
          rateWindow = new long[rateWindowSize];
        }
      }
      rateWindow[rateWindowSize - 1] = System.currentTimeMillis();

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
          access = navajoConfig.getRepository().authorizeUser(rpcUser,
              rpcPassword, rpcName, inMessage, userCertificate);
          if (clientInfo != null && access != null) {
            access.ipAddress = clientInfo.getIP();
            access.hostName = clientInfo.getHost();
            access.parseTime = clientInfo.getParseTime();
            access.requestEncoding = clientInfo.getEncoding();
            access.compressedReceive = clientInfo.isCompressedRecv();
            access.compressedSend = clientInfo.isCompressedSend();
            access.contentLength = clientInfo.getContentLength();
            access.created = clientInfo.getCreated();
          }
        }
        catch (AuthorizationException ex) {
          //System.err.println("IN LINE 487 OF DISPATCHER, CAUGHT AUTHORIZATIONEXCEPTION");
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
        outMessage = generateErrorMessage(access, errorMessage,
                                          SystemException.NOT_AUTHORISED, 1,
                                          new Exception("NOT AUTHORISED"));
        return outMessage;

      }
      else { // ACCESS GRANTED.

        access.authorisationTime = (int) (System.currentTimeMillis() -
                                          startAuth);
        accessSet.add(access);
        access.setMyDispatcher(this);
        access.setThreadCount(accessSet.size());
        
        // Check for lazy message control.
        access.setLazyMessages(header.getLazyMessages());

        Parameters parms = null;

        /**
         * Phase III: Check conditions for user/service combination using the 'condition' table in the database and
         * the incoming Navajo document.
         */
        ConditionData[] conditions = navajoConfig.getRepository().getConditions(
            access);
        if (conditions != null) {
          outMessage = NavajoFactory.getInstance().createNavajo();
          Message[] failed = checkConditions(conditions, inMessage, outMessage);

          if (failed != null) {
            Message msg = NavajoFactory.getInstance().createMessage(outMessage,
                "ConditionErrors");
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
        //System.err.println("REPOSITORY IS: " + navajoConfig.getRepository());
        
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
          if (rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON) || 
        	  rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON_SEND) ||
        	  rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_PING)  
             ) {
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
        outMessage = generateErrorMessage(access, ue.getMessage(), ue.code, 1,
                                          (ue.t != null ? ue.t : ue));
        myException = ue;
        return outMessage;
      }
      catch (Exception ee) {
        ee.printStackTrace();
        logger.log(NavajoPriority.DEBUG, ee.getMessage(), ee);
        myException = ee;
        return errorHandler(access, ee, inMessage);
      }
    }
    catch (SystemException se) {
      se.printStackTrace(System.err);
      myException = se;
      //System.err.println("CAUGHT SYSTEMEXCEPTION IN DISPATCHER()!!");
      try {
        outMessage = generateErrorMessage(access, se.getMessage(), se.code, 1,
                                          (se.t != null ? se.t : se));
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
      myException = e;
      return errorHandler(access, e, inMessage);
    }
    finally {
    	
      // Register webservice call to WebserviceListener.
      if ( access != null ) {
    	  WebserviceListener listener = WebserviceListener.getInstance();
    	  access.setInDoc(inMessage);
    	  listener.invocation(rpcName, access);
      }
        
      if (access != null) {
        // Remove access object from set of active webservices first.
        accessSet.remove(access);
        //System.err.println("AccessSet size: " + accessSet.size());
        // Set access to finished state.
        access.setFinished();
        Header h = outMessage.getHeader();
        if (h==null) {
            h = NavajoFactory.getInstance().createHeader(outMessage,rpcName,rpcUser,rpcPassword,-1);
            outMessage.addHeader(h);
        }
         access.storeStatistics(h);
        // Store access if navajostore is enabled and if webservice is not in list of special webservices.
        if (getNavajoConfig().getStatisticsRunner() != null && !isSpecialwebservice(access.rpcName)) {
          // Give asynchronous statistics runner a new access object to persist.
          getNavajoConfig().getStatisticsRunner().addAccess(access, myException);
        }
      }
      else if (getNavajoConfig().monitorOn) { // Also monitor requests without access objects if monitor is on.
        Access dummy = new Access( -1, -1, -1, rpcUser, rpcName, null,
                                  (clientInfo != null ? clientInfo.getIP() :
                                   "Internal request"),
                                  (clientInfo != null ? clientInfo.getHost() :
                                   "via NavajoMap"),
                                  false, userCertificate);
        if (getNavajoConfig().getStatisticsRunner() != null &&
            !isSpecialwebservice(rpcName)) {
          // Give asynchronous statistics runner a new access object to persist.
          dummy.setInDoc(inMessage);
          dummy.parseTime = (clientInfo != null ? clientInfo.getParseTime() :
                             -1);
          dummy.requestEncoding = (clientInfo != null ? clientInfo.getEncoding() :
                                   "");
          dummy.compressedReceive = (clientInfo != null ?
                                     clientInfo.isCompressedRecv() : false);
          dummy.compressedSend = (clientInfo != null ?
                                  clientInfo.isCompressedSend() : false);
          dummy.contentLength = (clientInfo != null ?
                                 clientInfo.getContentLength() : 0);
          dummy.threadCount = (clientInfo != null ? accessSet.size() : 0);
          getNavajoConfig().getStatisticsRunner().addAccess(dummy, myException);
        }
      }
    }
  }

  /**
   * Determine if WS is reserved Navajo webservice.
   *
   * @param name
   * @return
   */
  private final boolean isSpecialwebservice(String name) {

    if (name == null) {
      return false;
    }
    if (
        name.equals("InitGetAccessLogOverview") ||
        name.equals("ProcessGetAccessDetail") ||
        name.equals("InitGetAccessLog") ||
        name.equals("ProcessGetAccessLog") ||
        name.equals("InitGetAccessStatistics") ||
        name.equals("ProcessGetAccessStatistics") ||
        name.equals("InitNavajoStatus") ||
        name.equals("ProcessNavajoStatus") ||
        name.equals("ProcessQueryDatasource")) {
      return true;
    }
    else {
      return false;
    }
  }

  public void finalize() {
    //System.err.println("In finalize() Dispatcher object");
  }
  
  public static void killMe() {
	  if ( instance != null ) {
		  // Kill supporting threads.
		  if ( instance.getNavajoConfig().getLockManager() != null ) {
			  instance.getNavajoConfig().getLockManager().kill();
		  }
		  if ( instance.getNavajoConfig().getStatisticsRunner() != null ) {
			  instance.getNavajoConfig().getStatisticsRunner().kill();
		  }
		  if ( instance.getNavajoConfig().getTaskRunner(instance) != null ) {
			  instance.getNavajoConfig().getTaskRunner(instance).kill();
		  }
		  if ( instance.getNavajoConfig().getAsyncStore() != null ) {
			  instance.getNavajoConfig().getAsyncStore().kill();
		  }
		  if ( instance.getNavajoConfig().getIntegrityWorker(instance)!= null ) {
			  instance.getNavajoConfig().getIntegrityWorker(instance).kill();
		  }
		  
		  // Clear all classloaders.
		  GenericHandler.doClearCache();
		  
		  // Clear NavajoConfig.
		  instance.navajoConfig = null;
		  
		  // Finally kill myself
		  instance = null;
		  AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Navajo Dispatcher terminated.");
			 
	  }  
  }

  public String getServerId() {
    return serverId;
  }

  public void setServerId(String x) {
    if (serverId == null) {
      serverId = x;
    }
  }

  public static void main(String[] args) {
    int rateWindowSize = 20;
    long[] rateWindow = new long[rateWindowSize];
    for(int j=0;j<200;j++){
      for (int i = 0; i < rateWindowSize - 1; i++) {
        rateWindow[i] = rateWindow[i + 1];
        System.err.println("Shifting: [" + i + "] -> " + rateWindow[i]);
      }
      rateWindow[rateWindowSize - 1] = System.currentTimeMillis();
      if(rateWindow[0] > 0){
        float time = (float)(rateWindow[rateWindowSize - 1] - rateWindow[0]) / (float)1000.0;
        float avg = (float) rateWindowSize/time;
      }
    }

  }
}
