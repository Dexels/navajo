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
import java.util.logging.Level;

import navajo.Version;

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
import com.dexels.navajo.events.types.ChangeNotificationEvent;
import com.dexels.navajo.events.types.NavajoExceptionEvent;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.events.types.ServerTooBusyEvent;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.jmx.SNMPManager;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.Lock;
import com.dexels.navajo.lockguard.LockDefinition;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.lockguard.LocksExceeded;

import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.RemoteAsyncAnswer;
import com.dexels.navajo.mapping.RemoteAsyncRequest;
import com.dexels.navajo.mapping.compiler.TslCompiler;

/**
 * This class implements the general Navajo Dispatcher.
 * This class handles authorisation/authentication/logging/error handling/business rule validation and
 * finally dispatching to the proper dispatcher class.
 */

public final class Dispatcher implements Mappable, DispatcherMXBean, DispatcherInterface {

  protected static int instances = 0;
  
  /**
   * Fields accessable by webservices
   */
  public Access [] users;
  public static final String FILE_VERSION = "$Id$";
  public static final String vendor  = "Dexels BV";
  public static final String product = "Navajo Service Delivery Platform";
  public static final String version = Version.getDescription();
  public volatile static String edition;
  
  public boolean enabled = false;
  public boolean disabled = true;
  public boolean shutdown = false;
  
  static {
	  try {
			Class.forName("com.dexels.navajo.tribe.TribeManager");
			edition = "Enterprise";
		} catch (Throwable e) {
			edition = "Standard";
		}
  }
 
  /**
   * Unique dispatcher instance.
   */
  //private static volatile Dispatcher instance = null;
  private static boolean servicesBeingStarted = false;
  private static boolean servicesStarted = false;
  
  protected  boolean matchCN = false;
  public final Set<Access> accessSet = new HashSet<Access>();

  public  boolean useAuthorisation = true;
  private  final String defaultDispatcher = "com.dexels.navajo.server.GenericHandler";
  private static final String defaultNavajoDispatcher = "com.dexels.navajo.server.MaintainanceHandler";
  public static java.util.Date startTime = new java.util.Date();

  public  long requestCount = 0;
  private final NavajoConfigInterface navajoConfig;
 
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
   
  public Dispatcher(NavajoConfigInterface nc) {
	  navajoConfig = nc;
  }
  
  /**
   * Constructor for usage in web service scripts.
   */
  public Dispatcher() {
	  navajoConfig = null;
  }
   
  private final void startUpServices() {

	  // Bootstrap event registry.
	  NavajoEventRegistry.getInstance();
	  
	  // Bootstrap async store.
	  navajoConfig.getAsyncStore();
	  
	  // Startup statistics runnner.
	  navajoConfig.startStatisticsRunner();
	  
	  // Startup Jabber.
	  navajoConfig.startJabber();
	  
	  // Startup task runner.
	  navajoConfig.startTaskRunner();
	  
	  // Startup queued adapter.
	  RequestResponseQueueFactory.getInstance();
	  
	  // Bootstrap lock manager.
	  navajoConfig.getLockManager();
	  
	  // Bootstrap integrity worker.
	  navajoConfig.getIntegrityWorker();
	  
	  // Startup tribal status collector.
	  TribeManagerFactory.startStatusCollector();

  }
    
  protected final void init() {
	  
	  // Init tribe, if initializing it returns null, should not matter, let it do its thing.
	  if ( !servicesBeingStarted && !servicesStarted ) {
		  servicesBeingStarted = true;
		  if (TribeManagerFactory.getInstance() != null) {
			  // After tribe exists, start other service (there are dependencies on existence of tribe!).
			  startUpServices();
		  }
		  servicesStarted = true;
		  servicesBeingStarted = false;
	  }
	  
	  // Enable all incoming traffic.
	  enabled = true;
	  disabled = false;

  }

  public Access [] getUsers() {
	  Set<Access> all = new HashSet<Access>(com.dexels.navajo.server.DispatcherFactory.getInstance().getAccessSet());
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

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#getRequestRate()
   */
  public  float getRequestRate() {
    if(rateWindow[0] > 0){
      float time = (float)(rateWindow[rateWindowSize - 1] - rateWindow[0]) / (float)1000.0;
      float avg = (float) rateWindowSize/time;
      return avg;
    }
    return 0.0f;
  }

  /**
   * Clears all instantiated Navajo Classloaders to support a reload of Navajo Adapters.
   */
  public synchronized  final void doClearCache() {
    navajoConfig.doClearCache();
    GenericHandler.doClearCache();
    System.runFinalization();
    //System.gc();
  }

  /**
   * Clears only the script Navajo classloaders, and leaves the jar cache alone
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

  /*
   * Get the (singleton) NavajoConfig object reference.
   *
   * @return
   */
  public  final NavajoConfigInterface getNavajoConfig() {
    return navajoConfig;
  }

  /*
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

  /*
   * Get a reference to the Navajo repository object.
   *
   * @return
   */
  public  final Repository getRepository() {
    return navajoConfig.getRepository();
  }

  /*
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
	  LockManager lm = navajoConfig.getLockManager();
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
		
		  // If recompile is needed ALWAYS set expirationInterval to -1.
		  long expirationInterval = GenericHandler.needsRecompile(access) ? -1 : CacheController.getInstance().getExpirationInterval(access.rpcName);
		  
		  // Remove password from in to create password independend persistenceKey.
		  in.getHeader().setRPCPassword("");
		
		  out = (Navajo) navajoConfig.getPersistenceManager().get(sh,  
				  CacheController.getInstance().getCacheKey( access.rpcUser, access.rpcName, in), 
				  access.rpcName,
				  expirationInterval, 
				  (expirationInterval != -1) );
		  
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
  @Deprecated
  private final void addParameters(Navajo doc, Parameters parms) throws
  NavajoException {

	  Message msg = doc.getMessage("__parms__");
	  if (msg == null) {
		  msg = NavajoFactory.getInstance().createMessage(doc, "__parms__");
		  doc.addMessage(msg);
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
   * Handle a webservice (without ClientInfo object given).
   *
   * @param inMessage
   * @param userCertificate
   * @return
   * @throws FatalException
   */
  public final Navajo handle(Navajo inMessage, Object userCertificate) throws
      FatalException {
    return processNavajo(inMessage, userCertificate, null, false);
  }

  /**
   * Handle a webservice (without ClientInfo and certificate).
   *
   * @param inMessage
   * @return
   * @throws FatalException
   */
  public final Navajo handle(Navajo inMessage, boolean skipAuth) throws FatalException {
    return processNavajo(inMessage, null, null, skipAuth);
  }
  
  public final Navajo handle(Navajo inMessage) throws FatalException {
	  return processNavajo(inMessage, null, null, false);
  }

  public String getThreadName(Access a) {
	  return getApplicationId() + "/" + a.accessID;
  }
  
  public final boolean isBusy() {
	  return  ( accessSet.size() > navajoConfig.getMaxAccessSetSize() );
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
  
  /**
   * Entry point for HTTP Servlet Listener.
   * 
   * @param inMessage, the Navajo request message 
   * @param userCertificate, optionally a certificate
   * @param clientInfo, a client info structure
   * @return
   * @throws FatalException
   */
  public final Navajo handle(Navajo inMessage, Object userCertificate, ClientInfo clientInfo) throws FatalException {
	  // Maybe use event to trigger handle event.... such that NavajoRequest events can be proxied/intercepted by
	  // other classes.
	  return processNavajo(inMessage, userCertificate, clientInfo, false);
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
private final Navajo processNavajo(Navajo inMessage, Object userCertificate, ClientInfo clientInfo, boolean skipAuth) throws
      FatalException {
	
    Access access = null;
    Navajo outMessage = null;
    String rpcName = "";
    String rpcUser = "";
    String rpcPassword = "";
   
    Throwable myException = null;
    String origThreadName = null;
    boolean scheduledWebservice = false;
    
    int accessSetSize = accessSet.size();
    setRequestRate(clientInfo, accessSetSize);
     
    // Check whether service is  disabled (FORCED). Only accept special web services.
    if ( disabled && !isSpecialwebservice(inMessage.getHeader().getRPCName()) ) {
    	throw new FatalException("500");
    }
    
    // Check whether server is too busy...
    if ( isBusy() && !isSpecialwebservice(inMessage.getHeader().getRPCName()) && !inMessage.getHeader().hasCallBackPointers() ) {
    	try {
    		Navajo result = TribeManagerFactory.getInstance().forward(inMessage);
    		return result;
    	} catch (Exception e) {
    		// Server too busy!
    		NavajoEventRegistry.getInstance().publishEvent(new ServerTooBusyEvent());
    		System.err.println(">> SERVER TOO BUSY!!!!!");
    		throw new FatalException("500.13");
    	}
    }
    
    // Check whether server is disabled...
    if ( !enabled && !inMessage.getHeader().hasCallBackPointers() && !isSpecialwebservice(inMessage.getHeader().getRPCName())) {
    	System.err.println("DISPATCHER DISABLED, TRY OTHER NAVAJO INSTANCE/SE");
    	throw new FatalException("500.14");
    }
    
    // Check whether unkown callbackpointers are present that need to be handled by another instance.
    if ( inMessage.getHeader().hasCallBackPointers() ) {
    	String [] allRefs = inMessage.getHeader().getCallBackPointers();
    	if ( AsyncStore.getInstance().getInstance(allRefs[0]) == null ) {
    		RemoteAsyncRequest rasr = new RemoteAsyncRequest(allRefs[0]);
    		RemoteAsyncAnswer rasa = (RemoteAsyncAnswer) TribeManagerFactory.getInstance().askAnybody(rasr);
    		if ( rasa != null ) {
    			System.err.println("ASYNC OWNER: " + rasa.getOwnerOfRef() + "(" + rasa.getHostNameOwnerOfRef() + ")" + " FOR REF " + allRefs[0]);
    			try {
    				Navajo result = TribeManagerFactory.getInstance().forward(inMessage, rasa.getOwnerOfRef());
    				return result;
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}

    		} else {
    			System.err.println("DID NOT FIND ANY OWNERS OF ASYNCREF...");
    		}
    	} else {
    		System.err.println("FOUND ASYNCREF: " + allRefs[0] + " ON MYSELF!!");
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
          outMessage = generateAuthorizationErrorMessage(access, ex, rpcName);
          AuditLog.log(AuditLog.AUDIT_MESSAGE_AUTHORISATION, "(service=" + rpcName + ", user=" + rpcUser + ", message=" + ex.getMessage(), Level.WARNING);
          return outMessage;
        }
        catch (SystemException se) {
          outMessage = generateErrorMessage(access, se.getMessage(),
                                            SystemException.NOT_AUTHORISED, 1,
                                            new Exception("NOT AUTHORISED"));
          AuditLog.log(AuditLog.AUDIT_MESSAGE_AUTHORISATION, "(service=" + rpcName + ", user=" + rpcUser + ", message=" + se.getMessage(), Level.WARNING);
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
        		if (rpcName.startsWith(MaintainanceRequest.METHOD_NAVAJO))  
        	    {
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
    		//if (!isSpecialwebservice(rpcName)  ) {
    			// Register webservice call to WebserviceListener if it was not a scheduled webservice.
    			WebserviceListenerFactory.getInstance().afterWebservice(rpcName, access);
    		//}
        }
        
        return outMessage;
      }
    }
    catch (AuthorizationException aee) {
    	outMessage = generateAuthorizationErrorMessage(access, aee, rpcName);
    	AuditLog.log(AuditLog.AUDIT_MESSAGE_AUTHORISATION, "(service=" + rpcName + ", user=" + rpcUser + ", message=" + aee.getMessage(), Level.WARNING);
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
    	myException = e;
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
    	    
    	    // Publish exception event if exception occurred.
    	    if ( myException != null ) {
    	    	NavajoEventRegistry.getInstance().publishEvent(new NavajoExceptionEvent(rpcName, access.getAccessID(), rpcUser, myException));
    	    }
    	    
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
	  if ( DispatcherFactory.getInstance() != null ) {
		  
		  // 1. Kill all supporting threads.
		  GenericThread.killAllThreads();
		  
		  // 2. Clear all classloaders.
		  GenericHandler.doClearCache();
		  
	      // 3. Shutdown monitoring agent.
		  AgentFactory.getInstance().stop();
		  
		  // 4. Kill tribe manager.
		  TribeManagerFactory.getInstance().terminate();
		  
		  // 5. Finally kill myself
		  //instance = null;
		  try {
			  JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, "Dispatcher");
		  } catch (Throwable e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
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
	  return DispatcherFactory.getInstance().getAccessSet().size();
  }

  public void kill() {
  }

  public void load(Access access) throws MappableException, UserException {

  }

  public void store() throws MappableException, UserException {
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#getPeakAccessSetSize()
   */
  public int getPeakAccessSetSize() {
	  return peakAccessSetSize;
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#resetAccessSetPeakSize()
   */
  public void resetAccessSetPeakSize() {
	  peakAccessSetSize = 0;
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#getStarttime()
   */
  public Date getStarttime() {
	  return startTime;
  }

  public static int getInstances() {
	  return instances;
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#getUptime()
   */
  public long getUptime() {
	  return ( System.currentTimeMillis() - startTime.getTime() );
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#getSnmpManangers()
   */
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

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#setSnmpManagers(java.lang.String)
   */
  public void setSnmpManagers(String s) {
	  StringTokenizer st = new StringTokenizer(s, ",");
	  while ( st.hasMoreTokens() ) {
		  snmpManagers.add(new SNMPManager(st.nextToken()));
	  }
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#getRequestCount()
   */
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

  public  String getVersion() {
	  return version;
  }

  public  String getVendor() {
	  return vendor;
  }

  public  String getProduct() {
	  return product;
  }

  public  String getEdition() {
	  return edition;
  }

  public boolean getEnabled() {
	  return ((Dispatcher) DispatcherFactory.getInstance()).enabled;
  }

  public void setEnabled(boolean enabled) {
	  ((Dispatcher) DispatcherFactory.getInstance()).enabled = enabled;
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#disableDispatcher()
   */
  public void disableDispatcher() {
	  
	  ChangeNotificationEvent cne = 
		  new ChangeNotificationEvent(AuditLog.AUDIT_MESSAGE_DISPATCHER,
				  "Dispatcher disabled",
			      "enabled", "boolean", 
			      Boolean.valueOf(((Dispatcher) DispatcherFactory.getInstance()).enabled), Boolean.valueOf(false));
	  
	  NavajoEventRegistry.getInstance().publishEvent(cne);
	  
	  setEnabled(false);
  }

  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#enableDispatcher()
   */
  public void enableDispatcher() {
	  
	  ChangeNotificationEvent cne = 
		  new ChangeNotificationEvent(AuditLog.AUDIT_MESSAGE_DISPATCHER,
				  "Dispatcher enabled",
			      "enabled", "boolean", 
			      Boolean.valueOf(((Dispatcher) DispatcherFactory.getInstance()).enabled), Boolean.valueOf(true));
	  
	  NavajoEventRegistry.getInstance().publishEvent(cne);
	  
	  setEnabled(true);
  }

  /**
   * Use this method to FORCE a server to be disabled. Do NOT ACCEPT ANY requests anymore even
   * for async web service polling.
   * 
   * @param b
   */
  public void setDisabled(boolean b) {
	 
	  ChangeNotificationEvent cne = 
		  new ChangeNotificationEvent(AuditLog.AUDIT_MESSAGE_DISPATCHER,
				  (b ? "Dispatcher unset disabled" : "Dispatcher set disabled"),
			      "disabled", "boolean", 
			      Boolean.valueOf(((Dispatcher) DispatcherFactory.getInstance()).disabled), Boolean.valueOf(b));
	  
	  NavajoEventRegistry.getInstance().publishEvent(cne);
	  
	  ((Dispatcher) DispatcherFactory.getInstance()).disabled = b;
  }
  
  public void setShutdown(boolean b) {
	  if ( b ) {
		  shutdown();
	  }
  }
  
  /*
   * (non-Javadoc)
   * @see com.dexels.navajo.server.DispatcherMXBean#shutdown()
   */
  public void shutdown() {
	  disableDispatcher();
	  // Wait for all synchronous web service tasks to finish...
	  boolean finished = false;
	  while (!finished) {
		  int size = 0;
		  Iterator<Access> iter = ((Dispatcher) DispatcherFactory.getInstance()).accessSet.iterator();
		  while ( iter.hasNext() ) {
			  Access a = iter.next();
			  // Do not count maintenance services...
			  if ( !isSpecialwebservice(a.rpcName) ) {
				  size++;
			  }
		  }
		  AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Shutdown in progress, active services: " + size);
		  if ( size == 0 ) {
			  finished = true;
		  } else {
			  try {
				  Thread.sleep(2000);
			  } catch (InterruptedException e) {
			  }
		  }
	  }
	  // Wait for all asynchronous web service tasks to finish...
	  finished = false;
	  while (!finished) {
		  int size = com.dexels.navajo.mapping.AsyncStore.getInstance().objectStore.size();
		  AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Shutdown in progress, active async services: " + size);
		  if ( size ==  0 ) {
			  finished = true;
		  } else {
			  try {
				  Thread.sleep(2000);
			  } catch (InterruptedException e) {
			  }
		  }
	  }
	  Dispatcher.killMe();
	  
  }

  public Set<Access> getAccessSet() {
	  return accessSet;
  }

  public java.util.Date getStartTime() {
	  return startTime;
  }

  public int getRateWindowSize() {
	  return rateWindowSize;
  }
}