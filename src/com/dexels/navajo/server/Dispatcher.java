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

import com.dexels.navajo.server.enterprise.monitoring.AgentFactory;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;
import com.dexels.navajo.server.enterprise.scheduler.TaskInterface;
import com.dexels.navajo.server.enterprise.scheduler.TaskRunnerFactory;
import com.dexels.navajo.server.enterprise.scheduler.TaskRunnerInterface;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;

import com.dexels.navajo.broadcast.BroadcastMessage;
import com.dexels.navajo.document.*;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.events.types.ServerTooBusyEvent;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.jmx.SNMPManager;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.util.Util;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.Lock;
import com.dexels.navajo.lockguard.LockDefinition;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.lockguard.LocksExceeded;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.compiler.TslCompiler;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public final class Dispatcher implements Mappable, DispatcherMXBean {

  protected static int instances = 0;
  
  /**
   * Fields accessable by webservices
   */
  public Access [] users;
  public static final String VERSION = "$Id$";
  public static final String vendor  = "Dexels BV";
  public static final String product = "Navajo Service Delivery Platform";
  public static final String version = "Release 2008.01.01 $Tag$";
 
  /**
   * Unique dispatcher instance.
   */
  private static volatile Dispatcher instance = null;
  private static boolean servicesBeingStarted = false;
  private static boolean servicesStarted = false;
  
  protected  boolean matchCN = false;
  public final Set<Access> accessSet = new HashSet<Access>();

  public  boolean useAuthorisation = true;
  private  final String defaultDispatcher = "com.dexels.navajo.server.GenericHandler";
  private static final String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";
  public static java.util.Date startTime = new java.util.Date();

  public  long requestCount = 0;
  private final NavajoConfig navajoConfig;
 
  private  String keyStore;
  private  String keyPassword;

  public static final int rateWindowSize = 20;
  public static final double requestRate = 0.0;
  private  long[] rateWindow = new long[rateWindowSize];
  
  private static Object semaphore = new Object();
  private int peakAccessSetSize = 0;
  
  private static final Set<BroadcastMessage> broadcastMessage = Collections.synchronizedSet(new HashSet<BroadcastMessage>());
  
  /**
   * Registered SNMP managers.
   */
  private ArrayList<SNMPManager> snmpManagers = new ArrayList<SNMPManager>(); 
   
  /**
   * Constructor for URL based configuration.
   *
   * @param configurationUrl
   * @param fileInputStreamReader
   * @throws NavajoException
   */
  private Dispatcher(URL configurationUrl,
		  InputStreamReader fileInputStreamReader,
		  String rootPath,
		  NavajoClassSupplier cl) throws
		  NavajoException {
	  
	  instances++;
	  InputStream is = null; 
	  try {
		  // Read configuration file.
		  is = configurationUrl.openStream();
		  navajoConfig = new NavajoConfig(fileInputStreamReader, cl); 
		  navajoConfig.loadConfig(is,rootPath);
		  JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, "Dispatcher");
		  
		  // Monitor AccessSetSize
		  // JMXHelper.addGaugeMonitor( NavajoEventRegistry.getInstance(), JMXHelper.NAVAJO_DOMAIN, "Dispatcher", "AccessSetSize", null, new Integer(50), 10000L);
		  
		  NavajoFactory.getInstance().setTempDir(getTempDir());
		  
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
	  this(configurationUrl, fileInputStreamReader, null, cl);
  }

  public static Dispatcher getInstance() {
	  return instance;
  }
  
  public final void startUpServices() {

	  // Startup task runner.
	  instance.navajoConfig.getTaskRunner();
	  // Startup queued adapter.
	  RequestResponseQueueFactory.getInstance();
	  // Startup tribal status collector.
	  TribeManagerFactory.startStatusCollector();

  }
  
  public static Dispatcher getInstance(URL configurationUrl, InputStreamReader fileInputStreamReader) throws NavajoException {
	  if ( instance != null ) {
		  return instance;
	  }

	  synchronized (semaphore) {
		  if (instance == null) {
			  instance = new Dispatcher(configurationUrl, fileInputStreamReader, null);
			  instance.init();
		  }
	  }
	  return instance;
  }
  
  public static Dispatcher getInstance(String rootPath, String serverXmlPath, InputStreamReader fileInputStreamReader) throws NavajoException {

		if (instance != null) {
			return instance;
		}
		if(!rootPath.endsWith("/")) {
			rootPath = rootPath+"/";
		}

		URL configurationUrl;
		if(serverXmlPath==null) {
			System.err.println("Old skool configuration detected.");
			// old skool, the passed url is the configurationUrl (from web.xml):
				try {
					configurationUrl = new URL(rootPath);
					rootPath = null;
				} catch (MalformedURLException e) {
					throw NavajoFactory.getInstance().createNavajoException(e);
				}
		} else {
			// new-style: rootUrl is the root of the installation, serverXmlPath is the relative path to the server.xml file
			System.err.println("Newskool configuration detected.");
			try {
				File f = new File(rootPath);
				URL rootUrl = f.toURI().toURL();
				configurationUrl = new URL(rootUrl, serverXmlPath);
			} catch (MalformedURLException e) {
				throw NavajoFactory.getInstance().createNavajoException(e);
			}
			
		}
		
		synchronized (semaphore) {
			if (instance == null) {
				instance = new Dispatcher(configurationUrl, fileInputStreamReader,rootPath,null);
				instance.init();
			}
		}
		
		return instance;
	}
   
  private final void init() {
	  
	  // Init tribe, if initializing it returns null, should not matter, let it do its thing.
	  if ( !servicesBeingStarted && !servicesStarted ) {
		  servicesBeingStarted = true;
		  if (TribeManagerFactory.getInstance() != null) {
			  // After tribe exists, start other service (there are dependencies on existence of tribe!).
			  Dispatcher.getInstance().startUpServices();
		  }
		  servicesStarted = true;
		  servicesBeingStarted = false;
	  }

  }

public Access [] getUsers() {
	  Set<Access> all = new HashSet<Access>(com.dexels.navajo.server.Dispatcher.getInstance().accessSet);
	  Iterator<Access> iter = all.iterator();
	  ArrayList<Access> d = new ArrayList<Access>();
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
    //System.gc();
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
  @SuppressWarnings("unchecked")
  private final Navajo dispatch(String handler, Navajo in, Access access, Parameters parms) throws Exception {
	  
	  WorkerInterface integ = null;
	  Navajo out = null;
	  if (access == null) {
		  System.err.println("Null access!!!");
		  return null;
	  }
	  
	  access.setInDoc(in);
	  
	  if (in!=null) {
		Header h = in.getHeader();
		if (h!=null) {
			// Process client token:
			String clientToken = h.getHeaderAttribute("clientToken");
			if (clientToken!=null) {
				access.setClientToken(clientToken);
			}
			
			// Process piggyback data:
			Set<Map<String,String>> s = h.getPiggybackData();
			if (s!=null ) {
				for (Iterator<Map<String,String>> iter = s.iterator(); iter.hasNext();) {
					Map<String,String> element = iter.next();
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
		  
		  Class<? extends ServiceHandler> c;
		  
		  if ( access.betaUser ) {
			  c = navajoConfig.getBetaClassLoader().getClass(handler);
		  } else {
			  c = navajoConfig.getClassloader().getClass(handler);
		  }
		  
		  ServiceHandler sh = c.newInstance();
		  
		  sh.setInput(in, access, parms, navajoConfig);
		
		  long expirationInterval = CacheController.getInstance().getExpirationInterval(access.rpcName);
		  
		  // Remove password from in to create password independend persistenceKey.
		  in.getHeader().setRPCPassword("");
		
		  out = (Navajo) navajoConfig.getPersistenceManager().get(sh,  
				  CacheController.getInstance().getCacheKey( access.rpcUser, access.rpcName, in), access.rpcName,
				  expirationInterval, (expirationInterval != -1) );
		  
		  access.setOutputDoc(out);
		  
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

  @SuppressWarnings("unchecked")
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

	  if ( t != null ) {
		  t.printStackTrace(System.err);
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

  private final Navajo generateScheduledMessage(Header h, String taskId, boolean rejected) {
	  try {
		  Navajo outMessage = NavajoFactory.getInstance().createNavajo();
		  Header hnew = NavajoFactory.getInstance().createHeader(outMessage, h.getRPCName(), h.getRPCUser(), "", -1);

		  if ( ! rejected ) {
			  hnew.setSchedule(taskId);
		  }
		  else {
			  Message msg = NavajoFactory.getInstance().createMessage( outMessage, "Warning" );
			  outMessage.addMessage( msg );
			  
			  Property prop = NavajoFactory.getInstance().createProperty(outMessage, "Status", Property.STRING_PROPERTY, "TimeExpired", 32, "Created by generateScheduledMessage", Property.DIR_OUT );
			  msg.addProperty( prop );
		  }
		  outMessage.addHeader(hnew);

		  return outMessage;
	  } catch (Exception e) {
		  e.printStackTrace(System.err);
		  return null;
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

    ArrayList<Message> messages = new ArrayList<Message>();
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
      }
      
      if (!valid) {

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
    return handle(inMessage, userCertificate, null, false);
  }

  /**
   * Handle a webservice (without ClientInfo and certificate).
   *
   * @param inMessage
   * @return
   * @throws FatalException
   */
  public final Navajo handle(Navajo inMessage, boolean skipAuth) throws FatalException {
    return handle(inMessage, null, null, true);
  }
  
  public final Navajo handle(Navajo inMessage) throws FatalException {
	  return handle(inMessage, null, null, false);
  }

  public String getThreadName(Access a) {
	  return getApplicationId() + "/" + a.accessID;
  }
  
  public final boolean isBusy() {
	  return  ( accessSet.size() > navajoConfig.maxAccessSetSize );
	  //return ( !TribeManager.getInstance().getIsChief() );
  }

  private void setRequestRate(ClientInfo clientInfo, int accessSetSize) {
	  if ( accessSetSize > peakAccessSetSize ) {
		  peakAccessSetSize = accessSetSize;
	  }

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

  }
  
  public final Navajo handle(Navajo inMessage, Object userCertificate, ClientInfo clientInfo) throws FatalException {
	  return handle(inMessage, userCertificate, clientInfo, false);
  }
  
  /**
   * Handle a webservice.
   *
   * @param inMessage
   * @param userCertificate
   * @param clientInfo
   * @param skipAuth, always skip authorization part.
   * @return
   * @throws FatalException
   */
  @SuppressWarnings("deprecation")
public final Navajo handle(Navajo inMessage, Object userCertificate, ClientInfo clientInfo, boolean skipAuth) throws
      FatalException {

    Access access = null;
    Navajo outMessage = null;
    String rpcName = "";
    String rpcUser = "";
    String rpcPassword = "";
   
    Exception myException = null;
    String origThreadName = null;
    boolean scheduledWebservice = false;
    
    int accessSetSize = accessSet.size();
    setRequestRate(clientInfo, accessSetSize);
     
    // Check whether server is too busy...
    if ( isBusy() && !inMessage.getHeader().hasCallBackPointers() ) {
    	try {
    		Navajo result = TribeManagerFactory.getInstance().forward(inMessage);
    		return result;
    	} catch (Exception e) {
    		// Server too busy!
    		NavajoEventRegistry.getInstance().publishEvent(new ServerTooBusyEvent());
    		e.printStackTrace(System.err);
    		System.err.println(">> SERVER TOO BUSY!!!!!");
    		throw new FatalException("500.13");
    	}
    }
    
      Header header = inMessage.getHeader();
      rpcName = header.getRPCName();
      rpcUser = header.getRPCUser();
      rpcPassword = header.getRPCPassword();
      
      try {
      /**
       * Phase II: Authorisation/Authentication of the user. Is the user known and valid and may it use the
       * specified service?
       * Also log the access.
       */

      long startAuth = System.currentTimeMillis();

      if ( ( useAuthorisation && !skipAuth) && !(isSpecialwebservice(rpcName) && rpcUser.equals("NAVAJO") ) ) {
        try {
        	
          if ( navajoConfig == null ) {
        	  System.err.println("EMPTY NAVAJOCONFIG, INVALID STATE OF DISPATCHER!");
        	  throw new FatalException("EMPTY NAVAJOCONFIG, INVALID STATE OF DISPATCHER!");
          }
          
          if ( navajoConfig.getRepository() == null ) {
        	  System.err.println("EMPTY REPOSITORY, INVALID STATE OF DISPATCHER!");
        	  throw new FatalException("EMPTY REPOSITORY, INVALID STATE OF DISPATCHER!");
          }
          
          access = navajoConfig.getRepository().authorizeUser(rpcUser, rpcPassword, rpcName, inMessage, userCertificate);
          
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
        access = new Access(0, 0, 0, rpcUser, rpcName, "" , ( clientInfo != null ? clientInfo.getIP() : ""), ( clientInfo != null ?  clientInfo.getHost() : ""), null);
      }

      if ( rpcUser.endsWith(navajoConfig.getBetaUser()) ) {
        access.betaUser = true;
        System.err.println("We have a beta user: " + rpcUser );
       
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
         
        access.setThreadCount(accessSetSize);
        access.setCpuload(NavajoConfig.getInstance().getCurrentCPUload());
        
        // Check for lazy message control.
        access.setLazyMessages(header.getLazyMessages());

        Parameters parms = null;

        /**
         * Phase IV: Get application specific parameters for user.
         */
       
        Parameter[] pl = navajoConfig.getRepository().getParameters(access);

        parms = evaluateParameters(pl, inMessage);
        // Add parameters to __parms__ message.
        addParameters(inMessage, parms);

        /**
         * Phase VIa: Check if scheduled webservice
         */
        if ( inMessage.getHeader().getSchedule() != null && !inMessage.getHeader().getSchedule().equals("") ) {

        	if ( validTimeSpecification( inMessage.getHeader().getSchedule()) ) {

        		scheduledWebservice = true;
        		System.err.println("Scheduling webservice: " + inMessage.getHeader().getRPCName() + " on " + inMessage.getHeader().getSchedule());
        		TaskRunnerInterface trf = TaskRunnerFactory.getInstance();
        		TaskInterface ti = TaskRunnerFactory.getTaskInstance();
        		try {
        			ti.setTrigger(inMessage.getHeader().getSchedule());
        			ti.setNavajo(inMessage);
        			ti.setPersisted(true); // Make sure task gets persisted in tasks.xml
        			if ( inMessage.getHeader().getHeaderAttribute("keeprequestresponse") != null && 
        					inMessage.getHeader().getHeaderAttribute("keeprequestresponse").equals("true")
        			) {
        				ti.setKeepRequestResponse(true);
        			}
        			trf.addTask(ti);
        			outMessage = generateScheduledMessage(inMessage.getHeader(), ti.getId(), false);
        		} catch (UserException e) {
        			System.err.println("WARNING: Invalid trigger specified for task " + ti.getId()  + ": " + inMessage.getHeader().getSchedule());
        			trf.removeTask(ti);
        			outMessage = generateErrorMessage(access, "Could not schedule task:" + e.getMessage(), -1, -1, e);
        		}
        	}
        	else {	// obsolete time specification
        		outMessage = generateScheduledMessage(inMessage.getHeader(), null, true);
        	}

        } else {

        	/**
        	 * Phase VI: Dispatch to proper servlet.
        	 */

            // Create beforeWebservice event.
        	access.setInDoc(inMessage);
			Navajo useProxy = WebserviceListenerFactory.getInstance().beforeWebservice(rpcName, access);
			
			if (useAuthorisation) {
    			if ( useProxy == null ) {
    				outMessage = dispatch(navajoConfig.getRepository().getServlet(access), inMessage, access, parms);
    			} else {
    				rpcName = access.rpcName;
    				outMessage = useProxy;
    			}
        	}
        	else {
        		if (rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON) || 
        				rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_LOGON_SEND) ||
        				rpcName.equals(MaintainanceRequest.METHOD_NAVAJO_PING)  
        		) {
        			outMessage = dispatch(defaultNavajoDispatcher, inMessage, access, parms);
        		}
        		else {
        			if ( useProxy == null ) {
        				// Actually do something.
        				outMessage = dispatch(defaultDispatcher, inMessage, access, parms);
        			} else {
        				rpcName = access.rpcName;
        				outMessage = useProxy;
        			}
        		}
        	}
        }
       
        // Call after web service event...
        if ( access != null && !scheduledWebservice ) {
    		access.setInDoc(inMessage);
    		if (!isSpecialwebservice(rpcName)  ) {
    			// Register webservice call to WebserviceListener if it was not a scheduled webservice.
    			WebserviceListenerFactory.getInstance().afterWebservice(rpcName, access);
    		}
        }
        
        return outMessage;
      }
    }
    catch (AuthorizationException aee) {
    	outMessage = generateAuthorizationErrorMessage(access, aee, rpcName);
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
    		myException = ee;
    		return errorHandler(access, ee, inMessage);
    	}
    }
    catch (SystemException se) {
    	se.printStackTrace(System.err);
    	myException = se;
    	try {
    		outMessage = generateErrorMessage(access, se.getMessage(), se.code, 1,
    				(se.t != null ? se.t : se));
    		return outMessage;
    	}
    	catch (Exception ee) {
    		ee.printStackTrace();
    		return errorHandler(access, ee, inMessage);
    	}
    }
    catch (Exception e) {
    	e.printStackTrace(System.err);
    	myException = e;
    	return errorHandler(access, e, inMessage);
    } catch (Throwable e) {
    	e.printStackTrace(System.err);
    	return errorHandler(access, e, inMessage);
    }
    finally {
    	
    	if ( access != null && !scheduledWebservice ) {
 
    		// Remove access object from set of active webservices first.
    		synchronized ( accessSet ) {
    			accessSet.remove(access);
    		}
    		
    		// Set access to finished state.
    		access.setFinished();
    		Header h = outMessage.getHeader();
    		if (h==null) {
    			h = NavajoFactory.getInstance().createHeader(outMessage,rpcName,rpcUser,rpcPassword,-1);
    			outMessage.addHeader(h);
    		}

    		// Translate property descriptions.
    		updatePropertyDescriptions(inMessage,outMessage);
    	    access.storeStatistics(h);
    		
    	    // Call Navajoresponse event.
    	    NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(access, myException));
    	    
    		appendServerBroadCast(access, inMessage,h);
    	}
    	
    	if ( origThreadName != null ) {
    		Thread.currentThread().setName(origThreadName);
    	}
    }
  }

  private void updatePropertyDescriptions(Navajo inMessage, Navajo outMessage) {
		if (navajoConfig.getDescriptionProvider() == null) {
			//System.err.println("No description provider");
			return;
		}
		try {
			navajoConfig.getDescriptionProvider().updatePropertyDescriptions(inMessage, outMessage);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

  private void appendServerBroadCast(Access a, Navajo in, Header h) {
	  Set<BroadcastMessage> toBeRemoved = null;
	  for (Iterator<BroadcastMessage> iter = broadcastMessage.iterator(); iter.hasNext();) {

		  BroadcastMessage bm = iter.next();
		  if (bm.isExpired()) {
			  if (toBeRemoved==null) {
				  toBeRemoved = new HashSet<BroadcastMessage>();
			  }
			  toBeRemoved.add(bm);
		  }
		  if (!bm.validRecipient(a)) {
			  continue;
		  }
		  if (bm.hasBeenSent(a)) {
			  continue;
		  }
		  h.addPiggyBackData(bm.createMap());
		  bm.addSentToClientId(a);

	  }

	  if (toBeRemoved!=null) {
		  broadcastMessage.removeAll(toBeRemoved);
	  }
	  HashMap<String,String> m = new HashMap<String,String>();
	  m.put("requestRate", ""+getRequestRate());
	  m.put("serverId", ""+getServerId() + "/" + getApplicationId());
	  h.addPiggyBackData(m);
  }

/**
   * Determine if WS is reserved Navajo webservice.
   *
   * @param name
   * @return
   */
  public static final boolean isSpecialwebservice(String name) {
	  
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

  protected void finalize() {
	  instances--;
  }
  
  public static void killMe() {
	  if ( instance != null ) {
		  
		  // Kill tribe manager.
		  TribeManagerFactory.getInstance().terminate();
		  
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
		  
		  // Shutdown monitoring agent.
		  AgentFactory.getInstance().stop();
		  
		  // Finally kill myself
		  instance = null;
		  AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Navajo Dispatcher terminated.");
			 
	  }  
  }

  public String getServerId() {
	  return TslCompiler.getHostName();
  }

  public String getApplicationId() {
	  return getNavajoConfig().getInstanceName();
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
  
  public void setBroadcast(String message, int timeToLive, String recipientExpression) {
	  BroadcastMessage bm = new BroadcastMessage(message,timeToLive,recipientExpression);
	  broadcastMessage.add(bm);

  }

  public Access getAccessObject(String id) {

	  Iterator<Access> iter = accessSet.iterator();
	  boolean found = false;
	  Access a = null;
	  while (iter.hasNext() && !found) {
		  a = iter.next();
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
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

  }

  public void store() throws MappableException, UserException {
  }

  public int getPeakAccessSetSize() {
	  return peakAccessSetSize;
  }

  public void resetAccessSetPeakSize() {
	  peakAccessSetSize = 0;
  }

  public Date getStarttime() {
	  return startTime;
  }

  public static int getInstances() {
	  return instances;
  }
  
  public long getUptime() {
	  return ( System.currentTimeMillis() - startTime.getTime() );
  }

  public String getSnmpManangers() {
	  StringBuffer s = new StringBuffer();
	  for (int i = 0; i < snmpManagers.size(); i++ ) {
		  SNMPManager snmp = (SNMPManager) snmpManagers.get(i);
		  s.append(snmp.getHost() + ":" + snmp.getPort() + ":" + snmp.getSnmpVersion());
		  s.append(",");
	  }
	  String result = s.toString();
	  result = result.substring(0, result.length() - 1);
	  return result;
  }

  public void setSnmpManagers(String s) {
	  StringTokenizer st = new StringTokenizer(s, ",");
	  while ( st.hasMoreTokens() ) {
		  snmpManagers.add(new SNMPManager(st.nextToken()));
	  }
  }

  public long getRequestCount() {
	  return requestCount;
  }

  public static boolean validTimeSpecification(String dateString) {

		boolean result = false;

		try {
			if (dateString.startsWith("time:")) {
				dateString = dateString.substring(5);

				StringTokenizer tok = new StringTokenizer(dateString, "|");

				String field  = null;
				
				long   timeSpecified = 0;
				long   now           = 0;

				if (tok.hasMoreTokens()) {
					field          = tok.nextToken();
					timeSpecified += 1000000L * ( ( "*".equals( field ) ) ? 13 : Integer.parseInt( field ) );
				}
				if (tok.hasMoreTokens()) {
					field          = tok.nextToken();
					timeSpecified += 10000L * ( ( "*".equals( field ) ) ? 32 : Integer.parseInt( field ) );
				}
				if (tok.hasMoreTokens()) {
					field          = tok.nextToken();
					timeSpecified += 100L * ( ( "*".equals( field ) ) ? 25 : Integer.parseInt( field ) );
				}
				if (tok.hasMoreTokens()) {
					field          = tok.nextToken();
					timeSpecified += 1L * ( ( "*".equals( field ) ) ? 60 : Integer.parseInt( field ) );
				}
				if (tok.hasMoreTokens()) {
					field          = tok.nextToken();
				}
				if (tok.hasMoreTokens()) {
					field          = tok.nextToken();
					timeSpecified += 100000000L * ( ( "*".equals( field ) ) ? 9999 : Integer.parseInt( field ) );
				}

				Calendar current = Calendar.getInstance();
				
				now = 100000000L * current.get( Calendar.YEAR ) + 1000000L * ( current.get( Calendar.MONTH ) + 1 ) + 10000L * current.get( Calendar.DAY_OF_MONTH ) + 100L * current.get( Calendar.HOUR_OF_DAY ) + 1L * current.get( Calendar.MINUTE );
				
				result = timeSpecified > now;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}