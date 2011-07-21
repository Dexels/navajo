/**
  * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.dexels.navajo.client.push.NavajoPushSession;
import com.dexels.navajo.client.serverasync.ServerAsyncRunner;
import com.dexels.navajo.document.Guid;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;

//class MyX509TrustManager implements X509TrustManager {
//  public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//    return null;
//  }
//
//  public final void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//  }
//
//  public final void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//  }
//}

public class NavajoClient implements ClientInterface {

  public static final int DIRECT_PROTOCOL = 0;
  public static final int HTTP_PROTOCOL = 1;
  public static final int CONNECT_TIMEOUT = 5000;
  
//  private String host = null;
  private int loadBalancingMode = LBMODE_MANUAL;
  private String username = null;
  private String password = null;
  protected boolean condensed = true;

  private String[] serverUrls;
  private double[] serverLoads;
  
  private final Random randomize = new Random(System.currentTimeMillis());
  // Threadsafe collections:
  private final Map<String,Message> globalMessages = new HashMap<String,Message>();
  private final Map<String,Navajo> serviceCache = new HashMap<String,Navajo>();

  // use something serializable
  private final Object serviceCacheMutex = new LinkedList<String>();
  
  private final Set<String> cachedServiceNameMap = new HashSet<String>();
  private final Map<String,ServerAsyncRunner> asyncRunnerMap = Collections.synchronizedMap(new HashMap<String,ServerAsyncRunner>());
  private final List<ActivityListener> myActivityListeners = Collections.synchronizedList(new ArrayList<ActivityListener>());
  private final List<BroadcastListener> broadcastListeners = Collections.synchronizedList(new ArrayList<BroadcastListener>());
  
//  private NavajoPushSession pushSession = null;
  
  protected int protocol = HTTP_PROTOCOL;
  private boolean setSecure = false;
//  private SSLSocketFactory sslFactory = null;
  //private String keystore, passphrase;
  private long retryInterval = 1000; // default retry interval is 500 milliseconds
  private int retryAttempts = 4; // default four retry attempts
  private int switchServerAfterRetries = 10; /** If same as retry attempts, never switch between servers, while in retry attempt. FOR NOW
  THIS IS A SAFE VALUE CAUSE INTEGRITY WORKER DOES NOT YET WORKER OVER MULTIPLE SERVER INSTANCES!!! */
  
  private int currentServerIndex;
  private Thread keepAliveThread = null;
  //private static int instances = 0;
  
  // Warning: Not thread safe!
  private final HashMap<String,Navajo> storedNavajoComparisonMap = new HashMap<String,Navajo>();
  private final HashMap<String,String> comparedServicesQueryToUpdateMap = new HashMap<String,String>();
  private final HashMap<String,String> comparedServicesUpdateToQueryMap = new HashMap<String,String>();
  private final Set<Map<String,String>> piggyBackData = new HashSet<Map<String,String>>();
  private final String mySessionToken;
  private final Map<String,Long> disabledServers = new HashMap<String,Long>();

  private long lastActivity;
	private int keepAliveDelay;
	private int globalRetryCounter = 0;
	private String localeCode = null;
	private String subLocale;
	private boolean allowCompression = true;
//	private static boolean silent = true;
//	  
//  private boolean killed = false;
//  private boolean pingStarted = false;
//  
  // Disable for one minute. Bit short, should be maybe an hour, but better for debugging.
  private static final long serverDisableTimeout = 60000;
  
  /**
   * Initialize a NavajoClient object with an empty XML message buffer.
   */
  public NavajoClient(String dtdFile) {
	  this();
  }

  public void addComparedServices(String serviceQuery, String serviceUpdate) {
    //single query support!!
    //System.err.println("Added comparedservices: " + serviceQuery + ".." + serviceUpdate);
    comparedServicesQueryToUpdateMap.put(serviceQuery, serviceUpdate);
    comparedServicesUpdateToQueryMap.put(serviceUpdate, serviceQuery);
  }

  private final void checkForComparedServices(final String queryService, final Navajo n) {
    try {
      String s = comparedServicesQueryToUpdateMap.get(queryService);
      if (s != null) {
        //System.err.println("Storing Navajo object for service: " + queryService);
        storedNavajoComparisonMap.put(s, n.copy());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final boolean hasComparedServiceChanged(final String updateService, final Navajo n) {
    try {
      Navajo orig = storedNavajoComparisonMap.get(updateService);
      if (orig != null) {
        Navajo clone = n.copy();
        Iterator<Entry<String,Message>> entries = globalMessages.entrySet().iterator();
        while (entries.hasNext()) {
          Map.Entry<String,Message> entry = entries.next();
          Message global = entry.getValue();
          try {
            clone.removeMessage(global.getName());
          }
          catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not remove globals, proceeding");
          }
        }
        return!clone.isEqual(orig);
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Returns "http"
   * @return String
   */
  public final String getClientName() {
    return "http";
  }

  /**
   * Default constructor
   */
  public NavajoClient() {
	  String token = null;
	  try {
		token = NavajoClient.createSessionToken();
	} catch (UnknownHostException e) {
	
		e.printStackTrace();
	}
	if (token!=null) {
		mySessionToken = token;
	} else {
		mySessionToken = "OHDEAR|OHDEAR|OHDEAR|OHDEAR|OHDEAR";		
	}

	
	  //instances++;
	  //System.err.println("NavajoClient instances: " + instances);
  }

  /**
   * Construct a NavajoClient with a given protocol
   * @param protocol int
   */
  public NavajoClient(int protocol) {
	this();
    this.protocol = protocol;
  }

  
  public final String getSessionToken() {
	  if(mySessionToken==null) {
		  return "";
	  }
		  return mySessionToken;
  }
  
  /**
   * Not used
   * @param config URL
   * @throws ClientException
   */
  public final void init(URL config) throws ClientException {
    // not implemented
  }

  /**
   * Gets this NavajoClient object's username
   * @return String
   */
  public final String getUsername() {
    return username;
  }

  /**
   * Gets this NavajoClient object's password
   * @return String
   */
  public final String getPassword() {
//    System.err.println("Getting password: "+password);
    return password;
  }

  /**
   * Gets this NavajoClient object's server URL
   * @return String
   */
  public final String getServerUrl() {
    return getCurrentHost();
  }

  /**
   * Set the username
   * @param s String
   */
  public final void setUsername(String s) {
    username = s;
  }

  /**
   * Set the server URL
   * @param url String
   * USE SET SERVERURLS
   */
  public final void setServerUrl(String url) {
	  serverUrls = new String[]{url};
	  setServers(serverUrls);
	  }

  /**
   * Set the password
   * @param pw String
   */
  public final void setPassword(String pw) {
    password = pw;
  }

  /**
   * Sets the retry interval in milliseconds, this is the interval between the return of a request and the consecutive
   * retry request. Retries will be done when the target host can not be reached or returned an connection error
   * @param interval long
   */
  public final void setRetryInterval(long interval) {
    retryInterval = interval;
  }

  /**
   * Sets the number of retries the NavajoClient should perform before giving up
   * @param attempts int
   */
  public final void setRetryAttempts(int attempts) {
    retryAttempts = attempts;
  }

  /**
	 * Add a webservice (by name) to the NavajoClient cache mechanism. This
	 * provides a way to store frequenly used webservices in the NavajoClient
	 * and thus preventing a server roundtrip.
	 * 
	 * @param service
	 *            String
	 */
	public final void addCachedService(String service) {
		cachedServiceNameMap.add(service);
	}

	/**
	 * Remove (by name) a specific webservice from the NavajoClient's cache
	 * mechanism
	 * 
	 * @param service
	 *            String
	 */
	public final void removeCachedService(String service) {
		synchronized (serviceCacheMutex) {
			cachedServiceNameMap.remove(service);
			serviceCache.remove(service);
		}
	}

	/**
	 * Remove all webservices from the NavajoClient's cache
	 */
	public final void clearCache() {
		synchronized (serviceCacheMutex) {
			serviceCache.clear();
		}
	}

	/**
	 * This method removes the cached instance of the given webservice, but will
	 * continue to cache it when this service is called upon again.
	 * 
	 * @param service
	 *            String
	 */
	public final void clearCache(String service) {
		synchronized (serviceCacheMutex) {
			serviceCache.remove(service);
		}
	}

	/**
	 * Add a global message to this NavajoClient. Global messages are messages
	 * that will ALWAYS be put in ALL requests made by the NavajoClient
	 * instance. This way we can provide global information we want to use for
	 * every webservice.
	 * 
	 * @param m
	 *            Message
	 */
  public final void addGlobalMessage(Message m) {
    globalMessages.remove(m.getName());
    globalMessages.put(m.getName(), m);
  }

  /**
   * Remove a specific global message from the NavajoClient
   * @param m Message
   * @return boolean
   */
  public final boolean removeGlobalMessage(Message m) {
    return globalMessages.remove(m.getName()) != null;
  }

  /***
   * Returns a global message, addressed by name
   * @param name
   * @return
   */
  public final Message getGlobalMessage(String name) {
	  return globalMessages.get(name);
  }
  
  /**
   * Perform a synchronous webservice call
   * @param n Navajo
   * @param method String
   * @param v ConditionErrorHandler
   * @param expirationInterval long
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v, long expirationInterval) throws ClientException {
    if (v != null) {
      v.clearConditionErrors();
    }

    Navajo result = doSimpleSend(n, method, expirationInterval);
    if (v != null) {
      checkValidation(result, v);
    }
    return result;
  }

  /**
   * Perform a synchronous webservice call
   * @param out Navajo
   * @param method String
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(Navajo out, String method) throws ClientException {
    return doSimpleSend(out, method, -1);
  }



  /**
   * Perform a synchronous webservice call
   * @param out Navajo
   * @param method String
   * @param expirationInterval long
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(Navajo out, String method, long expirationInterval) throws ClientException {
    if (username == null) {
      throw new ClientException(1, 1, "No username set!");
    }
    if (password == null) {
      throw new ClientException(1, 1, "No password set!");
    }
    if (getCurrentHost() == null) {
      throw new ClientException(1, 1, "No host set!");
    }
    return doSimpleSend(out, getCurrentHost(), method, username, password, expirationInterval, allowCompression , false);
  }


  /**
   * Internal function for creating a URLConnection based on this Client's security settings
   * @param url URL
   * @throws IOException
   * @return URLConnection
   */
  public URLConnection createUrlConnection(URL url) throws IOException {
//    URL url;
//    if (setSecure) {
//      url = new URL("https://" + name);
//    }
//    else {
//      url = new URL("http://" + name);
//    }
    //System.err.println("in doTransaction: opening url: " + url.toString());
	  HttpURLConnection con = null;
      con = (HttpURLConnection) url.openConnection();
    con.setDoOutput(true);
    con.setDoInput(true);
    con.setUseCaches(false);
    con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
    return con;
  }

  private final void copyResource(OutputStream out, InputStream in){
	  BufferedInputStream bin = new BufferedInputStream(in);
	  BufferedOutputStream bout = new BufferedOutputStream(out);
	  byte[] buffer = new byte[1024];
	  int read = -1;
	  boolean ready = false;
	  while (!ready) {
		  try {
			  read = bin.read(buffer);
			  if ( read > -1 ) {
				  bout.write(buffer,0,read);
			  }
		  } catch (IOException e) {
		  }
		  if ( read <= -1) {
			  ready = true;
		  }
	  }
	  try {
		  bin.close();
		  bout.flush();
		  bout.close();
	  } catch (IOException e) {

	  }
  }
  
  protected final String readErrorStream(final HttpURLConnection myCon) {
	  try {
		  if ( myCon != null ) {
			  int respCode = myCon.getResponseCode();
			  InputStream es = myCon.getErrorStream();
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
			  copyResource(bos, es);
			  bos.close();
//			  String error = new String(bos.toByteArray());
			  System.err.println("Responsecode: " + respCode);
			  //System.err.println("Responsemessage: " + myCon.getResponseMessage());
			  //System.err.println("Got error from server: " + error);
			  // close the errorstream
			  es.close();
			  return "HTTP Status error " + respCode;
		  }
	  } catch (IOException ioe) {
		  //ioe.printStackTrace(System.err);
	  }
	  return null;
  }
  
  /**
   * Do a transation with the Navajo Server (name) using
   * a Navajo Message Structure (TMS) compliant XML document.
   * @param name String
   * @param d Navajo
   * @param useCompression boolean
   */
  
  protected Navajo doTransaction(String name, Navajo d, boolean useCompression, boolean forcePreparseProxy) throws IOException, NavajoException {
    URL url;
    //useCompression = false;
    
    
    if (setSecure) {
      url = new URL("https://" + name );
    }
    else {
      url = new URL("http://" + name );
    }
    HttpURLConnection con = null;
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setConnectTimeout(CONNECT_TIMEOUT);
    
    // Omit fancy http stuff for now
    con.setDoOutput(true);

    
    
    con.setDoInput(true);
    con.setUseCaches(false);
    con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
    appendHeaderToHttp(con,d.getHeader());    
    
    con.setRequestProperty("Connection", "Keep-Alive");

    con.setRequestProperty("Host", "localhost");
    if ( forcePreparseProxy ) {
    	con.setRequestProperty("Navajo-Preparse", "true");
    }
    try {
    	java.lang.reflect.Method chunked = con.getClass().getMethod("setChunkedStreamingMode", new Class[]{int.class});
    	chunked.invoke( con, new Object[]{new Integer(1024)});
    	con.setRequestProperty("Transfer-Encoding", "chunked" );
    } catch (SecurityException e) {
    } catch (Throwable e) {
     	System.err.println("setChunkedStreamingMode does not exist, upgrade to java 1.5+");
    }
    if (useCompression) {
    	con.setRequestProperty("Accept-Encoding", "jzlib");
    	con.setRequestProperty("Content-Encoding", "jzlib");
    	//con.connect();
    	   
    	BufferedWriter out = null;
    	try {
    		out = new BufferedWriter(new OutputStreamWriter(new ZOutputStream(con.getOutputStream(), JZlib.Z_BEST_SPEED), "UTF-8"));
    		d.write(out, condensed, d.getHeader().getRPCName());
    	} finally  {
    		if ( out != null ) {
    			try {
    				//out.flush();
    				out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    } else {
    	//con.connect();
    	con.setRequestProperty("noCompression", "true");
    	BufferedWriter os = null;
    	try {
//    		System.err.println("Using no compression!");
    		os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
//    		os.write("apenootjes");
    		d.write(os, condensed, d.getHeader().getRPCName());    	
    	} finally {
    		if ( os != null ) {
    			try {
    				os.flush();
    				os.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }

//    String contentEncoding = con.getContentEncoding();
    //System.err.println("Content encoding: "+contentEncoding);

    
    // Check for errors.
    InputStream in = null;
    Navajo n = null;
    try {
    	if ( con.getResponseCode() >= 400 ) {
    		throw new IOException(readErrorStream(con));
    	} else {
    		if ( useCompression ) {
    			in = new ZInputStream(con.getInputStream());
    		} else {
    			in = con.getInputStream();
    		}
    	}
    	if ( in != null ) {
    		n = NavajoFactory.getInstance().createNavajo(in);
    	}
    } finally {
    	if ( in != null ) {
    		in.close();
    		in = null;
    	}
    	//con.disconnect();
    }
    
	return n;
  }

  private void appendHeaderToHttp(HttpURLConnection con, Header header) {
	  con.setRequestProperty("rpcName",header.getRPCName());
	  con.setRequestProperty("rpcPass",header.getRPCPassword());
	  con.setRequestProperty("rpcUser",header.getRPCUser());
	  Map<String,String> attrs = header.getHeaderAttributes();
	  for (Entry<String,String> element : attrs.entrySet()) {
		  if(element.getValue()!=null) {
			  con.setRequestProperty(element.getKey(),element.getValue());
		  }
	}
  }

/**
   * Perform a synchronous webservice call
   * @param out Navajo
   * @param server String
   * @param method String
   * @param user String
   * @param password String
   * @param expirationInterval long
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval) throws ClientException {
    return doSimpleSend(out, server, method, user, password, expirationInterval, allowCompression, false);
  }

  //   navajo://frank:aap@192.0.0.1/InitUpdateMember

//  public final Navajo doUrlSend(Navajo out, String url) {
//    URLStreamHandler u;
//  }

  private final void generateConnectionError(Navajo n, int id, String description) {
	 
    try {
      Message conditionError = NavajoFactory.getInstance().createMessage(n, "ConditionErrors", Message.MSG_TYPE_ARRAY);
      n.addMessage(conditionError);
      Message conditionErrorElt = NavajoFactory.getInstance().createMessage(n, "ConditionErrors");
      conditionError.addMessage(conditionErrorElt);
      Property p1 = NavajoFactory.getInstance().createProperty(n, "Id", Property.INTEGER_PROPERTY, id + "", 10, "Id", Property.DIR_OUT);
      Property p2 = NavajoFactory.getInstance().createProperty(n, "Description", Property.INTEGER_PROPERTY, description, 10, "Omschrijving", Property.DIR_OUT);
      Property p3 = NavajoFactory.getInstance().createProperty(n, "FailedExpression", Property.INTEGER_PROPERTY, "", 10, "FailedExpression", Property.DIR_OUT);
      Property p4 = NavajoFactory.getInstance().createProperty(n, "EvaluatedExpression", Property.INTEGER_PROPERTY, "", 10, "EvaluatedExpression", Property.DIR_OUT);
      conditionErrorElt.addProperty(p1);
      conditionErrorElt.addProperty(p2);
      conditionErrorElt.addProperty(p3);
      conditionErrorElt.addProperty(p4);
    }
    catch (NavajoException ex) {
      ex.printStackTrace(System.err);
    }
  }

  /**
   * Perform a synchronous webservice call
   * @param out Navajo
   * @param server String
   * @param method String
   * @param user String
   * @param password String
   * @param expirationInterval long
   * @param useCompression boolean
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval, boolean useCompression, boolean allowPreparseProxy) throws ClientException {
    // NOTE: prefix persistence key with method, because same Navajo object could be used as a request
    // for multiple methods!

    // ============ compared services ===================

	  /**
	   * Make sure that same Navajo is not used simultaneously.
	   */
	  synchronized (out) {
		  if (!hasComparedServiceChanged(method, out)) {
			  try {
				  System.err.println("-------------------------------------------------->> Ignoring incoming request! <------------------------");
				  NavajoFactory nf = NavajoFactory.getInstance();
				  Navajo n = nf.createNavajo();
				  Message m = nf.createMessage(n, "Info");
				  Property p = nf.createProperty(n, "Message", "string", "Ignored unchanged input", 50, "", "out");
				  m.addProperty(p);
				  n.addMessage(m);
				  return n;
			  }
			  catch (Exception e) {
				  e.printStackTrace();
			  }
		  }

		  //====================================================

		  String cacheKey = "";

		  if (cachedServiceNameMap.contains(method)) {
			  cacheKey = method + out.persistenceKey();
			  if (serviceCache.get(cacheKey) != null) {
				  //System.err.println("---------------------------------------------> Returning cached WS");
				  Navajo cached = serviceCache.get(cacheKey);
				  return cached.copy();
			  }
		  }
		  fireActivityChanged(true, method, getQueueSize(), getActiveThreads(), 0);
		  Header header = out.getHeader();
		  String callingService = null;
		  if (header == null) {
			  header = NavajoFactory.getInstance().createHeader(out, method, user, password, expirationInterval );
			  out.addHeader(header);
		  } else {
			  callingService = header.getRPCName();
			  header.setRPCName(method);
			  header.setRPCUser(user);
			  header.setRPCPassword(password);
			  header.setExpirationInterval(expirationInterval);
		  }
		  // ALWAY SET REQUEST ID AT THIS POINT.
		  header.setRequestId( Guid.create() );
		  String sessionToken = getSessionToken();
		header.setHeaderAttribute("clientToken", sessionToken);
		  header.setHeaderAttribute("clientInfo", SystemInfo.getSystemInfo().toString());
		  // ========= Adding globalMessages
		  Iterator<Entry<String,Message>> entries = globalMessages.entrySet().iterator();
		  while (entries.hasNext()) {
			  Entry<String,Message> entry = entries.next();
			  Message global = entry.getValue();
			  try {
				  out.addMessage(global);
			  }
			  catch (Exception e) {
				  e.printStackTrace();
				  System.err.println("Could not add globals, proceeding");
			  }
		  }

		  long clientTime = 0;
		  try {

			  if (protocol == HTTP_PROTOCOL) {
				  if (out.getHeader()!=null) {
					  processPiggybackData(out.getHeader());
				  }

				  //==================================================================
				  // set the locale
				  // ==============================================
				  if (localeCode!=null) {
					  out.getHeader().setHeaderAttribute("locale", localeCode);
				  }
				  //==================================================================
				  // set the sublocale
				  // ==============================================
				  if (subLocale!=null) {
					  out.getHeader().setHeaderAttribute("sublocale", subLocale);
				  }


				  Navajo n = null;

				  long timeStamp = System.currentTimeMillis();

				  try {	
					  n = doTransaction(server, out, useCompression, allowPreparseProxy);
					  if ( n == null ) {
						  throw new Exception("Empty Navajo received");
					  }
				  }
				  catch (java.net.UnknownHostException uhe) {
					  n = NavajoFactory.getInstance().createNavajo();
					  generateConnectionError(n, 7777777, "Unknown host: " + uhe.getMessage());
				  }
				  catch (java.net.NoRouteToHostException uhe) {
					  n = NavajoFactory.getInstance().createNavajo();
					  generateConnectionError(n, 55555, "No route to host: " + uhe.getMessage());
				  }
				  catch (java.net.SocketException uhe) {
					  if ( retryAttempts <= 0 ) {
						  throw uhe;
					  }
					  Navajo n2 = NavajoFactory.getInstance().createNavajo();
					  n = retryTransaction(server, out, useCompression, allowPreparseProxy, retryAttempts, retryInterval, n2); // lees uit resource
					  if (n != null) {

						  //System.err.println("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getHeaderAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
						  if(callingService!=null) {
							  n.getHeader().setHeaderAttribute("sourceScript", callingService);							  
						  }
					  } else {
						  n = n2;
					  }
				  }
				  catch (IOException uhe) {
					  //uhe.printStackTrace();
					  //readErrorStream(myCon);
					  if ( retryAttempts <= 0 ) {
						  throw uhe;
					  }
					  System.err.println("Generic IOException: "+uhe.getMessage()+". Retrying without compression...");
					  Navajo n2 = NavajoFactory.getInstance().createNavajo();
					  n = retryTransaction(server, out, false, allowPreparseProxy, retryAttempts, retryInterval, n2); // lees uit resourc
					  if (n != null) {
						  //System.err.println("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getHeaderAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
						  if(callingService!=null) {
							  n.getHeader().setHeaderAttribute("sourceScript", callingService);
						  }
					  } else {
						  n = n2;
					  }
				  } catch(Throwable r) {
					  r.printStackTrace();
				  }
				  finally {
					  if ( n != null && n.getHeader()!=null) {
						  n.getHeader().setHeaderAttribute("sourceScript", callingService);
						  clientTime = (System.currentTimeMillis()-timeStamp);
						  n.getHeader().setHeaderAttribute("clientTime", ""+clientTime);
						  String tot = n.getHeader().getHeaderAttribute("serverTime");
						  String loadStr = n.getHeader().getHeaderAttribute("cpuload");
						  double load = -1.0;
						  if ( loadStr != null ) {
							  try {
								  load = Double.parseDouble(loadStr);
								  for (int x = 0; x < serverUrls.length; x++) {
									  if ( serverUrls[x].equals(server) ) {
										  serverLoads[x] = load;
										  x = serverUrls.length + 1;
									  }
								  }
							  } catch (Throwable t) {}
						  }
						  long totalTime = -1;
						  if (tot!=null&& !"".equals(tot)) {
							  totalTime = Long.parseLong(tot);
							  n.getHeader().setHeaderAttribute("transferTime",""+(clientTime-totalTime));
						  } 
						  Map<String,String> headerAttributes = n.getHeader().getHeaderAttributes();
						  Map<String,String> pbd = new HashMap<String,String>(headerAttributes);
						  pbd.put("type","performanceStats");
						  pbd.put("service",method);
						  synchronized (piggyBackData) {
							  piggyBackData.add(pbd);
						  }
						  //                 System.err.println(method+": totaltime = " + ( clientTime / 1000.0 )+ ", servertime = " + ( totalTime / 1000.0 )+" transfertime = "+((clientTime-totalTime)/1000)+" piggybackdata: "+piggyBackData.size()); 
					  } else {
						  System.err.println("Null header in input message");
					  }
				  }


				  fireActivityChanged(false, method, getQueueSize(), getActiveThreads(), 0);

				  if (cachedServiceNameMap.contains(method)) {
					  serviceCache.put(cacheKey, n);
				  }
				  checkForComparedServices(method, n);

				  // Process broadcasts
				  fireBroadcastEvents(n);

				  return n;
			  }
			  else {
				  throw new ClientException( -1, -1, "Unknown protocol: " + protocol);
			  }
		  }
		  catch (Exception e) {
			  e.printStackTrace();
			  fireActivityChanged(false, method, getQueueSize(), getActiveThreads(), 0);
			  throw new ClientException( -1, -1, e.getMessage());
		  }
	  }
  }

  protected boolean shouldOutputStreamClose() {
	  return true;
  }


  @Deprecated
  private final void fireBroadcastEvents(final Navajo n) {
	  
	  if (n==null) {
			// no navajo, don't know why. So no broadcasting.
		  return;
	  }

	  Header h = n.getHeader();
	  
	  
	  if (h==null) {
			// no headers, don't know why. So no broadcasting.
		  return;
	  }
	  Set<Map<String, String>> s = h.getPiggybackData();
	  if (s==null) {
		return;
	}
	  for (Iterator<Map<String, String>> iter = s.iterator(); iter.hasNext();) {
		Map<String, String> element = iter.next();
		if ("broadcast".equals(element.get("type"))) {
			String message = element.get("message");
			for (int i = 0; i < broadcastListeners.size(); i++) {
				      BroadcastListener current = broadcastListeners.get(i);
				      current.broadcast(message,element);
			   }
		}
		
	}
	
}

/**
   * Add piggyback data to header.
   * @param header
   */
  private final void processPiggybackData(Header header) {

	  synchronized (piggyBackData) {
		  // Clear previous piggyback data.
		  header.clearPiggybackData();
		  for (Iterator<Map<String,String>> iter = piggyBackData.iterator(); iter.hasNext();) {
			  Map<String,String> element = iter.next();
			  header.addPiggyBackData(element);
		  }
		  // remove piggyback data.
		  piggyBackData.clear();
	  }

  }

private final Navajo retryTransaction(String server, Navajo out, boolean useCompression, boolean allowPreparseProxy, int attemptsLeft, long interval, Navajo n) throws Exception {
	InputStream in = null;
    
    globalRetryCounter++;
    System.err.println("------------> retrying transaction: " + server + ", attempts left: " + attemptsLeft+" total retries: "+globalRetryCounter);
    
    int pastAttempts = retryAttempts-attemptsLeft;
    System.err.println("Past retries: "+pastAttempts);
    // only switch if there is more than one server
    if (pastAttempts>=(switchServerAfterRetries-1) && serverUrls.length>1) {
    	System.err.println("Did: "+pastAttempts+" retries. Switching server");
    	disabledServers.put(getCurrentHost(), new Long(System.currentTimeMillis()));
    	System.err.println("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    	switchServer(true);
    	server = getCurrentHost();
	}
    
    try {
    	try {
    		Thread.sleep(interval);
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	Navajo doc = doTransaction(server, out, useCompression, allowPreparseProxy);
    	System.err.println("It worked!  the inputstream is: " + in);
    	return doc;
    }
    catch (java.net.UnknownHostException uhe) {
    	generateConnectionError(n, 7777777, "Unknown host: " + uhe.getMessage());
    }
    catch (java.net.NoRouteToHostException uhe) {
    	generateConnectionError(n, 55555, "No route to host: " + uhe.getMessage());
    }
    catch (java.net.SocketException uhe) {
    	attemptsLeft--;
    	if (attemptsLeft <= 0) {
    		disabledServers.put(getCurrentHost(), new Long(System.currentTimeMillis()));
    		System.err.println("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    		switchServer(true);
    		generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
    	}
    	else {
    		return retryTransaction(server, out, useCompression, allowPreparseProxy, attemptsLeft, interval, n);
    	}
    }
    catch (IOException uhe) {
    	//readErrorStream(myCon);
    	System.err.println(uhe.getMessage());
    	if (attemptsLeft <= 0) {
    		disabledServers.put(getCurrentHost(), new Long(System.currentTimeMillis()));
    		System.err.println("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    		switchServer(true);
    		generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
    	}
    	else {
    		attemptsLeft--;
//    		System.err.println("Sending: ");
//    		out.write(System.err);
    		return retryTransaction(server, out, false, allowPreparseProxy, attemptsLeft, interval, n);
    	}
    }
    return null;
  }


  /**
   * Perform an asyncronous webservice call, the webservice will be started and when it's finished
   * the receive() method of the ResponseListener will be called with the result of the webservice.
   * During these operations the NavajoClient can continue to handle incoming requests
   * @param in Navajo
   * @param method String
   * @param response ResponseListener
   * @param responseId String
   * @throws ClientException
   */
  public void doAsyncSend(final Navajo in, final String method, final ResponseListener response, final String responseId) throws ClientException {
    doAsyncSend(in, method, response, responseId, null);
  }

  /**
   * Perform an asynchronous webservice call. For a brief explanation look for another implementation of this function
   * @param in Navajo
   * @param method String
   * @param response ResponseListener
   * @param v ConditionErrorHandler
   * @throws ClientException
   */
  public void doAsyncSend(final Navajo in, final String method, final ResponseListener response, final ConditionErrorHandler v) throws ClientException {
    doAsyncSend(in, method, response, "", v);
  }

  /**
   * Perform an asynchronous webservice call. For a brief explanation look for another implementation of this function
   * @param in Navajo
   * @param method String
   * @param response ResponseListener
   * @param responseId String
   * @param v ConditionErrorHandler
   * @throws ClientException
   */
  public void doAsyncSend(final Navajo in, final String method, final ResponseListener response, final String responseId, final ConditionErrorHandler v) throws ClientException {
	  
	  Thread t = new Thread(new Runnable() {
		  
		  final Navajo nc = in;
		  final ResponseListener rc = response;
		  final String mc = method;
		  final String ic = responseId;
		  
		  public final void run() {
			  
			  try {
				  final Navajo n;
				  if (v == null) {
					  n = doSimpleSend(nc, mc);
				  }
				  else {
					  n = doSimpleSend(nc, mc, v);
				  }
				  
				  if (response != null) {
					  rc.receive(n, mc, ic);
				  }
			  }
			  catch (Throwable ex) {
				  ex.printStackTrace();
				  if (rc != null) {
					  rc.setWaiting(false);
					  rc.handleException( (Exception) ex);
				  }
			  }
		  }
	  });
	
	  t.start();
  }

  /**
   * Dummy function, will return 0
   * @return int
   */
  public final int getPending() {
    System.err.println("getPending Dummy. This client has no asynchronous calls, so it will always return 0  ");
    return 0;
  }


  /**
   * Perform a synchronous webservice call
   * @param method String
   * @param messagePath String
   * @throws ClientException
   * @return Message
   */
  public final Message doSimpleSend(String method, String messagePath) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method, messagePath);
  }

  /**
   * Perform a synchronous webservice call
   * @param n Navajo
   * @param method String
   * @param messagePath String
   * @throws ClientException
   * @return Message
   */
  public final Message doSimpleSend(Navajo n, String method, String messagePath) throws ClientException {
    return doSimpleSend(n, method).getMessage(messagePath);
  }

  /**
   * Perform a synchronous webservice call
   * @param n Navajo
   * @param method String
   * @param v ConditionErrorHandler
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v) throws ClientException {
    if (v != null) {
      v.clearConditionErrors();
    }
    Navajo result = doSimpleSend(n, method);
    checkValidation(result, v);
    return result;
  }

  private final void checkValidation(Navajo result, ConditionErrorHandler v) {
    Message conditionErrors = result.getMessage("ConditionErrors");
    if (conditionErrors != null && v != null) {
      v.checkValidation(conditionErrors);
    }
  }

  /**
   * Perform a synchronous webservice call
   * @param method String
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(String method) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method);
  }

  /**
   * Perform a synchronous webservice call
   * @param method String
   * @param expirationInterval long
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo doSimpleSend(String method, long expirationInterval) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method, expirationInterval);
  }




  /**
   * Add activitylistener
   * @param al ActivityListener
   */
  public final void addActivityListener(ActivityListener al) {
    myActivityListeners.add(al);
  }

  /**
   * Remove activitylistener
   * @param al ActivityListener
   */
  public final void removeActivityListener(ActivityListener al) {
    myActivityListeners.remove(al);
  }


  
  /**
   * Fires an activitychange event to all listeners
   * @param b boolean
   * @param service String
   * @param queueSize int
   * @param activeThreads int
   * @param millis long
   */
  public void fireActivityChanged(boolean b, String service, int queueSize, int activeThreads, long millis) {
    for (int i = 0; i < myActivityListeners.size(); i++) {
      ActivityListener current = myActivityListeners.get(i);
      current.setWaiting(b, service, queueSize, activeThreads, millis);
    }
  }

  /**
   * Add broadcastlistener
   * @param al ActivityListener
   * @deprecated
   */
  public final void addBroadcastListener(BroadcastListener al) {
    broadcastListeners.add(al);
  }

  /**
   * Remove broadcastlistener
   * @param al ActivityListener
   * @deprecated
   */
  public final void removeBroadcastListener(BroadcastListener al) {
	  
	  broadcastListeners.remove(al);
  }



  
  
  /**
   * Performs an asynchronous serverside webservice call. These services will be polled by the Started ServerAsyncRunner
   * and pass the status on to the given ServerAsyncListener. This method can be used for large time consuming webservices
   * @param in Navajo
   * @param method String
   * @param listener ServerAsyncListener
   * @param clientId String
   * @param pollingInterval int
   * @throws ClientException
   */
  public final void doServerAsyncSend(Navajo in, String method, ServerAsyncListener listener, String clientId, int pollingInterval) throws ClientException {
    ServerAsyncRunner sar = new ServerAsyncRunner(this, in, method, listener, clientId, pollingInterval);
    String serverId = sar.startAsync();
    registerAsyncRunner(serverId, sar);

  }

  private final void registerAsyncRunner(String id, ServerAsyncRunner sar) {
    asyncRunnerMap.put(id, sar);
  }

  /**
   * Deregister asyncrunner
   * @param id String
   */
  public final void deRegisterAsyncRunner(String id) {
    asyncRunnerMap.remove(id);
  }

  private final ServerAsyncRunner getAsyncRunner(String id) {
    return asyncRunnerMap.get(id);
  }

  public int getQueueSize() {
    return 0;
  }

  public int getActiveThreads() {
    return -1;
  }

  /**
   * Finalize all asyncrunners
   */
  public final void finalizeAsyncRunners() {
    try {
      System.err.println("------------------------------------------>> Finalizing asyncrunners....");
      Iterator<String> it = asyncRunnerMap.keySet().iterator();
      while (it.hasNext()) {
        String id = it.next();
        ServerAsyncRunner sar = getAsyncRunner(id);
        sar.killServerAsyncSend();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Kill an asynchronous server process
   * @param serverId String
   * @throws ClientException
   */
  public final void killServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    System.err.println("Looking for asyncRunner: " + serverId);
    if (sar != null) {
      sar.killServerAsyncSend();
    }
    else {
      System.err.println("Not found!");
    }
  }

  /**
   * Pause an asynchronous server process
   * @param serverId String
   * @throws ClientException
   */
  public final void pauseServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    if (sar != null) {
      sar.resumeServerAsyncSend();
    }
  }

  /**
   * Resume an asynchronous server process
   * @param serverId String
   * @throws ClientException
   */
  public final void resumeServerAsyncSend(String serverId) throws ClientException {
    ServerAsyncRunner sar = getAsyncRunner(serverId);
    if (sar != null) {
      sar.resumeServerAsyncSend();
    }
  }

  public void setCondensed(boolean b) {
    condensed = b;
  }

  
  // Ultra defensive for app engines.
  public static String createSessionToken() throws UnknownHostException {
		String userName = null;
		try {
			userName = System.getProperty("user.name");
		} catch (SecurityException e) {
			userName = "UnknownUser";
		}

		String fabricatedToken = null;
		
		try {
			fabricatedToken = userName + "|" + (InetAddress.getLocalHost().getHostAddress())
					+ "|" + (InetAddress.getLocalHost().getHostName()) + "|"
					+ (System.currentTimeMillis());
		} catch (Throwable e) {
//			e.printStackTrace();
			System.err.println("Session failed!");
			fabricatedToken="unknown session";
		}
		return fabricatedToken;
	}
  
 

public void destroy() {
		
}



public void setServers(String[] servers) {
	serverUrls = servers;
	serverLoads = new double[serverUrls.length];
	if (servers.length>0) {
		currentServerIndex = randomize.nextInt(servers.length);
//		System.err.println("Starting at server # "+currentServerIndex);
	}
//	System.err.println("servers = " + servers[0] + ", loadBalancingMode = " + loadBalancingMode);
//	if ( loadBalancingMode != LBMODE_MANUAL ) {
//		determineServerLoadsAndSetCurrentServer(true);
//		ping();
//	}
}

public String getCurrentHost() {
	if (serverUrls!=null && serverUrls.length>0) {
		return serverUrls[currentServerIndex];
	}
	return null;
}

public void setCurrentHost(String host) {
	for (int i = 0; i < serverUrls.length; i++) {
		if ( serverUrls[i].equals(host) ) {
			currentServerIndex = i;
			System.err.println("SET CURRENT SERVER TO: " + host + "(" + currentServerIndex + ")");
			break;
		}
	}
}

public final String getCurrentHost(int serverIndex) {
		return serverUrls[serverIndex];
}

/**
 * Switch the current server.
 * If force flag is set. The server with the minimum load is ALWAYS set as the current server.
 * For LBMODE_MANUAL and LBMODE_STATIC_MINLOAD the server is ONLY switched if force flag is set.
 * For LBMODE_DYNAMIC_MINLOAD the server is ALWAYS switched.
 * @param force
 */
public final void switchServer(boolean force) {
	
	if ( loadBalancingMode == LBMODE_MANUAL ) {
		return;
	}
	
	if ( !force &&  loadBalancingMode == LBMODE_STATIC_MINLOAD ) {
		return;
	}
	
	
	if (serverUrls==null || serverUrls.length == 0 || serverUrls.length == 1) {
		return;
	}
	
	double minload = 1000000.0;
	int candidate = -1;
	
	for (int i = 0; i < serverUrls.length; i++) {
		if ( serverLoads[i] < minload && serverLoads[i] != -1.0 && !disabledServers.containsKey(serverUrls[i]) ) { // If there is really a server with a lower load, use this server as candidate.
			minload = serverLoads[i];
			candidate = i;
		}
	}
	
	if ( candidate == -1 ) {
		throw new RuntimeException("No enabled servers left!");
	} else {
		currentServerIndex = candidate;
	}
	
	System.err.println("currentServer = " + serverUrls[currentServerIndex] + " with load: " + serverLoads[currentServerIndex]);
	
}

//  public static void main(String[] args) throws Exception {
//	  System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
//		
//	  NavajoClient nc = new NavajoClient();
//	  //nc.setSecure("/home/arjen/BBKY84H.keystore", "kl1p_g31t", true);
//	  Navajo out = NavajoFactory.getInstance().createNavajo();
//	  
//	  Navajo aap = nc.doSimpleSend(out, "ficus:3000/sportlink/knvb/servlet/Postman", "InitBM", "ROOT", "", -1);
//	  //out.write(System.err);
//	  //System.err.println("RESPONSE:");
//	  //aap.write(System.err);
//	  
//	  Navajo dummy = NavajoFactory.getInstance().createNavajo();
//	  BufferedInputStream stream = nc.retryTransaction("ficus:3000/sportlink/knvb/servlet/Postman", out, false, 3, 4000, dummy);
//	  Navajo aap2 = NavajoFactory.getInstance().createNavajo(stream);
//	  //aap2.write(System.err);
//	  
//  }

	private void checkKeepalive() throws ClientException {
		if (keepAliveDelay>0) {
			if (System.currentTimeMillis()-keepAliveDelay>lastActivity) {
				doSimpleSend("navajo/InitKeepAlive");
			}
		}
	}
	/** sets the keepalive frequency. It will send a keepalive event (single threaded) after millis
	 *  milliseconds of inactivity.
	 * @throws ClientException 
	 * @throws ClientException 
	 */

	public void setKeepAlive(int millis) throws ClientException {
		if (keepAliveDelay>0 && keepAliveDelay < 5000) {
			throw new IllegalArgumentException("setKeepAlive: ");
		}
		keepAliveDelay = millis;
//		checkKeepalive();
		if (keepAliveThread==null) {
			keepAliveThread = new Thread(new Runnable(){
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					while (keepAliveDelay>0) {
						try {
							checkKeepalive();
							Thread.sleep(keepAliveDelay);
						} catch(Throwable t) {
							t.printStackTrace();
						}
					
					}
					keepAliveThread = null;
				}},"KeepAliveThread");
			keepAliveThread.setDaemon(true);
			keepAliveThread.start();
		}
		
	}

	public Navajo doSpecificSend(Navajo out, String method, int serverIndex)  throws ClientException{
			    if (username == null) {
			      throw new ClientException(1, 1, "No username set!");
			    }
			    if (password == null) {
			      throw new ClientException(1, 1, "No password set!");
			    }
			    if (getCurrentHost() == null) {
			      throw new ClientException(1, 1, "No host set!");
			    }

			    //System.err.println("------> Calling service: " + method);

//			    try {
//			      out.write(System.err);
//			    }
//			    catch (NavajoException ex) {
//			      ex.printStackTrace();
//			    }
			    return doSimpleSend(out, getCurrentHost(serverIndex), method, username, password, -1, allowCompression, true);
		}

	public int getAsyncServerIndex() {
		return currentServerIndex;
	}


	public void setLocaleCode(String locale) {
		this.localeCode  = locale;
	}

	public String getLocaleCode() {
		return this.localeCode;
	}



	public void setSubLocaleCode(String locale) {
		this.subLocale = locale;
	}

	/**
	 * Schedule a webservice @ a certain time. Note that this method does NOT return the response
	 * of the scheduled webservice. It contains a Navajo with the status of the scheduling.
	 * 
	 * @out contains the request Navajo
	 * @method defines the webservice
	 * @schedule defines a timestamp of the format: HH:mm:ss dd-MM-yyyy. If null assume immediate execution.
	 * 
	 */
	public Navajo doScheduledSend(Navajo out, String method, String schedule, String description, String clientId) throws ClientException {
		
		String triggerURL = null;
		
		if ( schedule == null ) {
			schedule = "now";
		}
		
		Header h = out.getHeader();
		if ( h == null ) {
			h = NavajoFactory.getInstance().createHeader(out, method, username, password, -1 );
			out.addHeader(h);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		if ( !schedule.equals("now") ) {
			try {
				c.setTime(sdf.parse(schedule));
				triggerURL = "time:" + (c.get(Calendar.MONTH) + 1) + "|" + c.get(Calendar.DAY_OF_MONTH) + "|" + c.get(Calendar.HOUR_OF_DAY) + "|" +
				c.get(Calendar.MINUTE) + "|*|" + c.get(Calendar.YEAR);
			} catch (ParseException e) {
				throw new ClientException(-1, -1, "Unknown schedule timestamp format: " + schedule);
			}
		} else {
			triggerURL = "time:" + schedule;
		}
		if(description != null){
			h.setHeaderAttribute("description", description);
		}
		if(clientId != null){
			h.setHeaderAttribute("clientid", clientId);
		}
		h.setHeaderAttribute("keeprequestresponse", "true");
		h.setSchedule(triggerURL);
		
		return doSimpleSend(out, method);
	}
	
	public void finalize() {
//		killed = true;
	}
	
	public void dispose() {
//		killed = true;
	}
	
	public void init(String rootPath, String serverXmlPath) throws ClientException {
	}

	public Binary getArrayMessageReport(Message m, String[] propertyNames, String[] propertyTitles, int[] columnWidths, String format) throws NavajoException {
		return getArrayMessageReport(m, propertyNames, propertyTitles, columnWidths, format, null,null);
	}
	
	public Binary getArrayMessageReport(Message m, String[] propertyNames, String[] propertyTitles, int[] columnWidths, String format, String orientation, int[] margins) throws NavajoException {
//		Message m = in.getMessage(messagePath);
		if(m==null) {
			throw NavajoFactory.getInstance().createNavajoException("Message not found. Can not run report.");
		}
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message cp = m.copy(n);
		Header h = NavajoFactory.getInstance().createHeader(n, "Irrelevant", "Irrelevant", "Irrelevant", -1);
		n.addHeader(h);
		h.setHeaderAttribute("sourceScript", "Irrelevant");
		n.addMessage(cp);
		Message repDef = NavajoFactory.getInstance().createMessage(n,"__ReportDefinition");
		n.addMessage(repDef);
		StringBuffer sz = new StringBuffer();
		for (int i = 0; i < columnWidths.length; i++) {
			if(i!=0) {
				sz.append(",");
			}
			sz.append(columnWidths[i]);
		}
		Property sizeProp = NavajoFactory.getInstance().createProperty(n, "PropertySizes",Property.STRING_PROPERTY, sz.toString(), 0, "", Property.DIR_IN);
		repDef.addProperty(sizeProp);

		sz = new StringBuffer();
		for (int i = 0; i < propertyNames.length; i++) {
			if(i!=0) {
				sz.append(",");
			}
			sz.append(propertyNames[i]);
		}
		String propertyNamesString = sz.toString();
		Property namesProp = NavajoFactory.getInstance().createProperty(n, "PropertyNames",Property.STRING_PROPERTY,propertyNamesString, 0, "", Property.DIR_IN);
		repDef.addProperty(namesProp);
		
		sz = new StringBuffer();
		if(propertyTitles!=null) {
			for (int i = 0; i < propertyTitles.length; i++) {
				if(i!=0) {
					sz.append(",");
				}
				sz.append(propertyTitles[i]);
			}
		} else {
			// If no titles supplied, use property names
			sz.append(propertyNamesString);
		}
		Property titlesProp = NavajoFactory.getInstance().createProperty(n, "PropertyTitles",Property.STRING_PROPERTY, sz.toString(), 0, "", Property.DIR_IN);
		repDef.addProperty(titlesProp);		
		
		Property messagePathProp = NavajoFactory.getInstance().createProperty(n, "MessagePath",Property.STRING_PROPERTY, cp.getName(), 0, "", Property.DIR_IN);
		repDef.addProperty(messagePathProp);
		
		Property reportFormatProp = NavajoFactory.getInstance().createProperty(n, "OutputFormat",Property.STRING_PROPERTY, format, 0, "", Property.DIR_IN);
		repDef.addProperty(reportFormatProp);

		if(margins!=null) {
			Property marginProperty = NavajoFactory.getInstance().createProperty(n, "Margin",Property.STRING_PROPERTY,  margins[0]+","+margins[1]+","+margins[2]+","+margins[3], 0,"", Property.DIR_IN);
			repDef.addProperty(marginProperty);
		}
		if(orientation!=null) {
			Property orientationProperty = NavajoFactory.getInstance().createProperty(n, "Orientation",Property.STRING_PROPERTY, orientation, 0, "", Property.DIR_IN);
			repDef.addProperty(orientationProperty);
		}

		n.getMessage("__ReportDefinition").write(System.err);
//		repDef.write(System.err);
		try {
			Navajo result = NavajoClientFactory.getClient().doSimpleSend(n, "ProcessPrintTableBirt");
			Property data = result.getProperty("/Result/Data");
			if(data==null) {
				result.write(System.err);
				throw NavajoFactory.getInstance().createNavajoException("No report property found.");
			}
			Binary b = (Binary) data.getTypedValue();
			return b;
		} catch (ClientException e) {
			throw NavajoFactory.getInstance().createNavajoException(e);
		}
	}

	public int getLoadBalancingMode() {
		return loadBalancingMode;
	}

	public void setLoadBalancingMode(int i) {
		loadBalancingMode = i;
	}
	

	public boolean attemptPushRegistration(String agentId) {
		Navajo init = NavajoFactory.getInstance().createNavajo();
		try {
			Message m = NavajoFactory.getInstance().createMessage(init, "Agent");
			init.addMessage(m);
			Property p = NavajoFactory.getInstance().createProperty(init, "ApplicationId", Property.STRING_PROPERTY, agentId, 0, "aap", Property.DIR_IN);
			m.addProperty(p);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		Navajo n = null;
		try {
			n = doSimpleSend(init,"navajo/InitClientSession");
		} catch (ClientException e) {
			return false;
		}
		if(n.getMessage("error")!=null) {
			return false;
		}
		if(n.getMessage("ConditionErrors")!=null) {
			return false;
		}
//		
//		NavajoPushSession nps 
		processPushNavajo(n,agentId);
		return true;
	}

	@SuppressWarnings("unchecked")
	private void processPushNavajo(Navajo n,String agentId) {
		try {
			n.write(System.err);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		String pushImpl = (String) n.getProperty("SessionParameters/PushbackHandler").getTypedValue();
		try {
			Class<? extends NavajoPushSession> c = (Class<? extends NavajoPushSession>) Class.forName(pushImpl);
			NavajoPushSession nps =  c.newInstance();
			nps.load(n,agentId);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading push implementation. Disabling push");
		}
		
	}
	
	public boolean isAllowCompression() {
		return allowCompression;
	}

	public void setAllowCompression(boolean allowCompression) {
		this.allowCompression = allowCompression;
	}

	public static void main(String [] args) throws Exception {
		NavajoClient nc = new NavajoClient();
//		nc.setServerUrl("penelope1.dexels.com/sportlink/knvb/servlet/Postman");
//		nc.doTransaction("penelope1.dexels.com/sportlink/knvb/servlet/Postman", NavajoFactory.getInstance().createNavajo(), false, false);
		nc.setUsername("ROOT");
		nc.setPassword("ROOT");
		nc.setServerUrl("spiritus.dexels.nl:9080/JsSportlink/Comet");
		Navajo res = nc.doSimpleSend(NavajoFactory.getInstance().createNavajo(), "tests/InitNavajoMapTest3");
		res.write(System.err);
	}
}