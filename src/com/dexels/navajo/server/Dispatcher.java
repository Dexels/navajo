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

import com.dexels.navajo.broadcast.BroadcastMessage;
import com.dexels.navajo.document.*;
import com.dexels.navajo.scheduler.WebserviceListener;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.integrity.Worker;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.Lock;
import com.dexels.navajo.lockguard.LockDefinition;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.lockguard.LocksExceeded;

import com.dexels.navajo.logger.*;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public final class Dispatcher implements Mappable, DispatcherMXBean {

  public static int instances = 0;
  
  /**
   * Fields accessable by webservices
   */
  public Access [] users;
  public static final String VERSION = "$Id$";
  public static final String vendor = "Dexels BV";
  public static final String product = "Navajo Integrator";
  public static final String version = "Navajo Integrator Release 2005.11.10";
  public String serverId = null;
  public String applicationId = null;

  /**
   * Unique dispatcher instance.
   */
  private static Dispatcher instance = null;
  
  //private Navajo inMessage = null;
  protected  boolean matchCN = false;
//  public  Set accessSet = Collections.synchronizedSet(new HashSet());
  public final Set accessSet = new HashSet();

  public  boolean useAuthorisation = true;
  private  final String defaultDispatcher =
      "com.dexels.navajo.server.GenericHandler";
  private static final String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";
  public static java.util.Date startTime = new java.util.Date();

  public  long requestCount = 0;
  private final NavajoConfig navajoConfig;
 
  private  NavajoLogger logger = null;

  private  boolean debugOn = false;

  private  String keyStore;
  private  String keyPassword;

  public static final int rateWindowSize = 20;
  public static double requestRate = 0.0;
  private  long[] rateWindow = new long[rateWindowSize];
  
  private static Object semaphore = new Object();
  private int peakAccessSetSize = 0;
  
  private static final Set broadcastMessage = Collections.synchronizedSet(new HashSet());
  
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
	  
	  instances++;
	  InputStream is = null; 
	  try {
		  // Read configuration file.
		  is = configurationUrl.openStream();
		  navajoConfig = new NavajoConfig(fileInputStreamReader, cl); 
		  navajoConfig.loadConfig(is);
		  debugOn = navajoConfig.isLogged();
		  logger = NavajoConfig.getNavajoLogger(Dispatcher.class);
	  }
	  
	  catch (Exception se) {
		  throw NavajoFactory.getInstance().createNavajoException(se);
	  } finally {
		  if ( is != null ) {
			  try {
				is.close();
			} catch (IOException e) {
				// NOT INTERESTED.
			}
		  }
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
		  InputStreamReader fileInputStreamReader, String serverIdentification) throws
		  NavajoException  {

	  if ( instance != null ) {
		  return instance;
	  }

	  synchronized (semaphore) {
		  if ( instance == null ) {
			  instance = new Dispatcher(configurationUrl, fileInputStreamReader);
			  JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, "Dispatcher");
			  instance.setServerIdentifier(serverIdentification);
			  NavajoFactory.getInstance().setTempDir(instance.getTempDir());
			  // Startup task runner.
			  instance.navajoConfig.getTaskRunner();
		  }
	  }
	  return instance;
  }
   
  public Access [] getUsers() {
	  Set all = new HashSet(com.dexels.navajo.server.Dispatcher.getInstance().accessSet);
	  Iterator iter = all.iterator();
	  ArrayList d = new ArrayList();
	  while (iter.hasNext()) {
		  Access a = (Access) iter.next();
		  d.add(a);
	  }
	  Access [] ams = new Access[d.size()];
	  return (Access []) d.toArray(ams);
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
	  
	  Worker integ = null;
	  Navajo out = null;
	  if (access == null) {
		  System.err.println("Null access!!!");
	  }
	  
	  access.setInDoc(in);
	  
	  if (in!=null) {
		Header h = in.getHeader();
		if (h!=null) {
			// Process client token:
			String clientToken = h.getAttribute("clientToken");
			if (clientToken!=null) {
				access.setClientToken(clientToken);
			}
			
			// Process piggyback data:
			Set s = h.getPiggybackData();
			if (s!=null ) {
				for (Iterator iter = s.iterator(); iter.hasNext();) {
					Map element = (Map) iter.next();
					access.addPiggybackData(element);
				}
			}
		}
	}
	  
	  
	  // Check for webservice transaction integrity.
	  boolean integrityViolation = false;
	  integ = navajoConfig.getIntegrityWorker();
	  if ( integ != null ) {
		  // Check for stored response or webservice that is still running.
		  out = integ.getResponse(access, in);
		  if ( out != null ) {
			  integrityViolation = true;
			  return out;
		  }
	  }
	  
	  // Check for locks.
	  LockManager lm = navajoConfig.getLockManager( );
	  Lock [] locks = null;
	  if ( lm != null ) {
		  try {
			  locks = lm.grantAccess(access);
		  } catch (LocksExceeded le) {
			  LockDefinition ld = le.getLockDefinition();
			  return generateErrorMessage(access, "Could not get neccessary lock(s) for service: " + access.rpcName + " (wspattern="+ ld.webservice+",matchuser=" + ld.matchUsername + ",matchrequest="+ld.matchRequest+",maxinstancecount="+ld.maxInstanceCount+")", SystemException.LOCKS_EXCEEDED, -1, le);
		  }
	  }
  
	  try {
		  
		  Class c;
//		  if (access == null) {
//			  c = navajoConfig.getClassloader().getClass(handler);
//		  }
//		  else {
////			  if (access.betaUser) {
////				  c = navajoConfig.getBetaClassLoader().getClass(handler);
////			  }
////			  else {
//				  c = navajoConfig.getClassloader().getClass(handler);
//			//  }
//		  }
		  
		  if ( access.betaUser ) {
			  c = navajoConfig.getBetaClassLoader().getClass(handler);
		  } else {
			  c = navajoConfig.getClassloader().getClass(handler);
		  }
		  ServiceHandler sh = (ServiceHandler) c.newInstance();
		  
//		  if (access.betaUser) {
//			  sh.setInput(in, access, parms, navajoConfig);
//		  }
//		  else {
			  sh.setInput(in, access, parms, navajoConfig);
		  //}
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
		  
		  // Store response for integrity checking.
		  if ( integ != null && out != null && !integrityViolation ) {
			  integ.setResponse(in, out);
		  }
		  
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
		  // Release locks.
		  if ( lm != null && locks != null ) {
			  lm.removeLocks(access, locks);
		  }  
		  // Remove stored access from worker request list.
		  if ( integ != null ) {
			  integ.removeFromRunningRequestsList(in);
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
      AuthorizationException ae, String rpcName) throws FatalException {

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
      
      prop = NavajoFactory.getInstance().createProperty(outMessage, "Webservice",
              Property.STRING_PROPERTY, rpcName, 0, "User", Property.DIR_OUT);

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

      if ( t != null && t instanceof LocksExceeded ) {
    	  
    	  LocksExceeded ld = (LocksExceeded) t;
    	  
    	  Message locksExceeded = NavajoFactory.getInstance().createMessage(
    	          outMessage, "LocksExceeded");
    	  outMessage.addMessage(locksExceeded);
    	  Property w = NavajoFactory.getInstance().createProperty(outMessage,
    	          "Webservice", Property.STRING_PROPERTY,
    	          access.rpcName, 200, "Webservice", Property.DIR_OUT);
    	  locksExceeded.addProperty(w);
    	  Property u = NavajoFactory.getInstance().createProperty(outMessage,
    	          "Username", Property.STRING_PROPERTY,
    	          access.rpcUser, 200, "Username", Property.DIR_OUT);
    	  locksExceeded.addProperty(u);
    	  Property ws = NavajoFactory.getInstance().createProperty(outMessage,
    	          "WebservicePattern", Property.STRING_PROPERTY,
    	          ld.getLockDefinition().getWebservice(), 200, "Webservice pattern", Property.DIR_OUT);
    	  locksExceeded.addProperty(ws);
    	  Property mu = NavajoFactory.getInstance().createProperty(outMessage,
    	          "MatchUsername", Property.STRING_PROPERTY,
    	          ld.getLockDefinition().getMatchUsername() + "", 200, "Match username", Property.DIR_OUT);
    	  locksExceeded.addProperty(mu);
    	  Property mr = NavajoFactory.getInstance().createProperty(outMessage,
    	          "MatchRequest", Property.STRING_PROPERTY,
    	          ld.getLockDefinition().getMatchRequest() + "", 200, "Match request", Property.DIR_OUT);
    	  locksExceeded.addProperty(mr);
    	  Property mi = NavajoFactory.getInstance().createProperty(outMessage,
    	          "MaxInstanceCount", Property.STRING_PROPERTY,
    	          ld.getLockDefinition().getMaxInstanceCount() + "", 200, "Maximum number of instances", Property.DIR_OUT);
    	  locksExceeded.addProperty(mi);
    	  
    	  
    	  outMessage.addMessage(locksExceeded);
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

  public String getThreadName(Access a) {
	  return applicationId + "/" + a.accessID;
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
    String origThreadName = null;
    
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
      
      if ( clientInfo != null ) {
    	  rateWindow[rateWindowSize - 1] = clientInfo.created.getTime();
      } else {
    	  rateWindow[rateWindowSize - 1] = System.currentTimeMillis();
      }

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

      if ( useAuthorisation && !(isSpecialwebservice(rpcName) && rpcUser.equals("NAVAJO") ) ) {
        try {
          if ( navajoConfig == null ) {
        	  System.err.println("EMPTY NAVAJOCONFIG, INVALID STATE OF DISPATCHER!");
        	  throw new FatalException("EMPTY NAVAJOCONFIG, INVALID STATE OF DISPATCHER!");
          }
          if ( navajoConfig.getRepository() == null ) {
        	  System.err.println("EMPTY REPOSITORY, INVALID STATE OF DISPATCHER!");
        	  throw new FatalException("EMPTY REPOSITORY, INVALID STATE OF DISPATCHER!");
          }
          
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
            // Set the name of this thread.
            origThreadName = Thread.currentThread().getName();
            Thread.currentThread().setName(getThreadName(access));
          }
        }
        catch (AuthorizationException ex) {
          //System.err.println("IN LINE 487 OF DISPATCHER, CAUGHT AUTHORIZATIONEXCEPTION");
          outMessage = generateAuthorizationErrorMessage(access, ex, rpcName);
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

      if ( rpcUser.endsWith(navajoConfig.getBetaUser()) ) {
        access.betaUser = true;
        System.err.println("We have a beta user: " + rpcUser );
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

        access.authorisationTime = (int) (System.currentTimeMillis() - startAuth);
        synchronized ( accessSet ) {
        	accessSet.add(access);
        }
        
        access.setMyDispatcher(this);
        int accessSetSize = accessSet.size();
        if ( accessSetSize > peakAccessSetSize ) {
        	peakAccessSetSize = accessSetSize;
        }
        access.setThreadCount(accessSetSize);
        
        // Check for lazy message control.
        access.setLazyMessages(header.getLazyMessages());

        Parameters parms = null;

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
        updatePropertyDescriptions(inMessage,outMessage);
        return outMessage;
      }
    }
    catch (AuthorizationException aee) {
     outMessage = generateAuthorizationErrorMessage(access, aee, rpcName);
      updatePropertyDescriptions(inMessage,outMessage);
      return outMessage;
    }
    catch (UserException ue) {
      try {
        outMessage = generateErrorMessage(access, ue.getMessage(), ue.code, 1,
                                          (ue.t != null ? ue.t : ue));
        myException = ue;
        updatePropertyDescriptions(inMessage,outMessage);
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
        updatePropertyDescriptions(inMessage,outMessage);
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
    		synchronized ( accessSet ) {
    			accessSet.remove(access);
			}
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
    		if (    getNavajoConfig().getStatisticsRunner() != null &&  
    				getNavajoConfig().getStatisticsRunner().isEnabled() && 
    				!isSpecialwebservice(access.rpcName)) {
    			// Give asynchronous statistics runner a new access object to persist.
    			getNavajoConfig().getStatisticsRunner().addAccess(access, myException, null);
    		}
    		
    		appendServerBroadCast(access, inMessage,h);
    	}
    	else if (getNavajoConfig().monitorOn) { // Also monitor requests without access objects if monitor is on.
    		Access dummy = new Access( -1, -1, -1, rpcUser, rpcName, null,
    				(clientInfo != null ? clientInfo.getIP() :
    				"Internal request"),
    				(clientInfo != null ? clientInfo.getHost() :
    				"via NavajoMap"),
    				false, userCertificate);
    		if (    getNavajoConfig().getStatisticsRunner() != null &&
    				getNavajoConfig().getStatisticsRunner().isEnabled() &&
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
    			getNavajoConfig().getStatisticsRunner().addAccess(dummy, myException, null);
    		}
    	}
    	if ( origThreadName != null ) {
    		Thread.currentThread().setName(origThreadName);
    	}
    }
  }

  private void updatePropertyDescriptions(Navajo inMessage, Navajo outMessage) {
		if (navajoConfig.getDescriptionProvider() == null) {
			System.err.println("No description provider");
			return;
		}
		try {
			navajoConfig.getDescriptionProvider().updatePropertyDescriptions(inMessage, outMessage);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

private void appendServerBroadCast(Access a, Navajo in, Header h) {
	  Set toBeRemoved = null;
	  for (Iterator iter = broadcastMessage.iterator(); iter.hasNext();) {
		  
		  Object element = (Object) iter.next();
		if (element instanceof BroadcastMessage) {
			BroadcastMessage bm = (BroadcastMessage)element;
			if (bm.isExpired()) {
				if (toBeRemoved==null) {
					toBeRemoved = new HashSet();
				}
				toBeRemoved.add(element);
			}
			if (!bm.validRecipient(a)) {
				continue;
			}
			if (bm.hasBeenSent(a)) {
				continue;
			}
			h.addPiggyBackData(bm.createMap());
			bm.addSentToClientId(a);
		} else {
			if (element==null) {
				System.err.println("Strange object found: NULL!");
			} else {
				System.err.println("Strange object found: "+element.getClass());
			}
		}
	  }
	  if (toBeRemoved!=null) {
		broadcastMessage.removeAll(toBeRemoved);
    	}
	  HashMap m = new HashMap();
	  m.put("requestRate", ""+getRequestRate());
	  m.put("serverId", ""+serverId);
	  h.addPiggyBackData(m);
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
	  if ( name.startsWith("navajo") || name.equals("InitNavajoStatus") || name.equals("navajo_logon") ) {
		  return true;
	  }
	  else {
		  return false;
	  }
  }

  public void finalize() {
	  //System.err.println("In finalize() Dispatcher object");
	  instances--;
  }
  
  public static void killMe() {
	  if ( instance != null ) {
		  
		  // Kill all supporting threads.
		  GenericThread.killAllThreads();
		  
		  // Clear all classloaders.
		  GenericHandler.doClearCache();
		  
		  try {
			  JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, "Dispatcher");
		  } catch (Throwable e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  // Finally kill myself
		  instance = null;
		  AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Navajo Dispatcher terminated.");
			 
	  }  
  }

  public String getServerId() {
	if ( serverId == null ) {
		return "unknown_server_id";
	}
	String serverPart = serverId.substring(0, serverId.indexOf("/"));
	serverPart = serverPart.replace('/', '_');
    return serverPart;
  }

  public String getApplicationId() {
	  if ( serverId == null ) {
		  return "unknown_application_id";
	  }
	  applicationId = serverId.substring(serverId.indexOf("/")+1, serverId.length());
	  applicationId = applicationId.replace('/', '_');
	  return applicationId;
  }
  
  public File getTempDir() {
	  File tempDir = new File(System.getProperty("java.io.tmpdir") + "/" + getApplicationId());
	  tempDir.mkdirs();
	  return tempDir;
  }
  
  public File createTempFile(String prefix, String suffix) throws IOException {  
	  File f = File.createTempFile(prefix, suffix, getTempDir());
	  // Don't use deleteOnExit until Java 1.6, lower version contain memory leak (approx. 1K per call!).
	  //f.deleteOnExit();
	  return f;
  }
  
  public void setServerIdentifier(String x) {
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

public void setBroadcast(String message, int timeToLive, String recipientExpression) {
	BroadcastMessage bm = new BroadcastMessage(message,timeToLive,recipientExpression);
	broadcastMessage.add(bm);
	
}

public Access getAccessObject(String id) {

	Iterator iter = accessSet.iterator();
	boolean found = false;
	Access a = null;
	while (iter.hasNext() && !found) {
		a = (Access) iter.next();
		if (a.accessID.equals(id)) {
			System.err.println("FOUND ACCESS OBJECT!!!");
			found = true;
		}
	}
	return a;
}

public int getAccessSetSize() {
	return Dispatcher.getInstance().accessSet.size();
}

public void kill() {
	// TODO Auto-generated method stub
	
}

public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	// TODO Auto-generated method stub
	
}

public void store() throws MappableException, UserException {
	// TODO Auto-generated method stub
	
}

public int getPeakAccessSetSize() {
	return peakAccessSetSize;
}
}