/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

import java.io.*;
import java.net.*;
import java.security.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.*;


//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.RequestEntity;

import com.dexels.navajo.client.serverasync.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.BaseHeaderImpl;
import com.dexels.navajo.document.saximpl.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.client.impl.*;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.ZInputStream;
import com.jcraft.jzlib.ZOutputStream;
import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;

//import com.dexels.navajo.client.impl.*;

class MyX509TrustManager implements X509TrustManager {
  public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    return null;
  }

  public final void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
  }

  public final void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
  }
}

public class NavajoClient implements ClientInterface {

  public static final int DIRECT_PROTOCOL = 0;
  public static final int HTTP_PROTOCOL = 1;
  public static final int CONNECT_TIMEOUT = 3000;
  
//  private String host = null;
  private String username = null;
  private String password = null;
  protected boolean condensed = true;

  private String[] serverUrls;
  private double[] serverLoads;
  
  private final static Random randomize = new Random(System.currentTimeMillis());
  // Threadsafe collections:
  private Map globalMessages = new HashMap();
  private Map serviceCache = new HashMap();

  private Object serviceCacheMutex = new Object();
  
  private Map cachedServiceNameMap = new HashMap();
  private Map asyncRunnerMap = Collections.synchronizedMap(new HashMap());
  private Map propertyMap = Collections.synchronizedMap(new HashMap());
  private List myActivityListeners = Collections.synchronizedList(new ArrayList());
  
  private final List broadcastListeners = Collections.synchronizedList(new ArrayList());
  
  //private long timeStamp = 0;
  // Standard option: use HTTP protocol.
  protected int protocol = HTTP_PROTOCOL;
  private boolean useLazyMessaging = true;
  private ErrorResponder myResponder;
  private boolean setSecure = false;
  private SSLSocketFactory sslFactory = null;
  //private String keystore, passphrase;
  private long retryInterval = 1000; // default retry interval is 1000 milliseconds
  private int retryAttempts = 10; // default three retry attempts
  private int switchServerAfterRetries = 5; /** If same as retry attempts, never switch between servers, while in retry attempt. FOR NOW
  THIS IS A SAFE VALUE CAUSE INTEGRITY WORKER DOES NOT YET WORKER OVER MULTIPLE SERVER INSTANCES!!! */
  
  private int currentServerIndex;
  private Thread keepAliveThread = null;
  //private static int instances = 0;
  
  // Warning: Not thread safe!
  private final HashMap storedNavajoComparisonMap = new HashMap();
  private final HashMap comparedServicesQueryToUpdateMap = new HashMap();
  private final HashMap comparedServicesUpdateToQueryMap = new HashMap();

  private final Set piggyBackData = new HashSet();
  private final String mySessionToken;
  
  private final HashMap disabledServers = new HashMap();
	private long lastActivity;
	private int keepAliveDelay;
	private int globalRetryCounter = 0;
	private String localeCode = null;
	private String subLocale;
	private static boolean silent = true;
	  
  private boolean killed = false;
  private boolean pingStarted = false;
  
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
      String s = (String) comparedServicesQueryToUpdateMap.get(queryService);
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
      Navajo orig = (Navajo) storedNavajoComparisonMap.get(updateService);
      if (orig != null) {
        Navajo clone = n.copy();
        Iterator entries = globalMessages.entrySet().iterator();
        while (entries.hasNext()) {
          Map.Entry entry = (Map.Entry) entries.next();
          Message global = (Message) entry.getValue();
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
//    host = url;
//	  System.err.println("Warning: setServerURL is deprecated!");
	  serverUrls = new String[]{url};
	  serverLoads = new double[serverUrls.length];
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
		cachedServiceNameMap.put(service, service);
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
	  return (Message)globalMessages.get(name);
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

    //System.err.println("------> Calling service: " + method);

//    try {
//      out.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
    return doSimpleSend(out, getCurrentHost(), method, username, password, expirationInterval, true, false);
  }

  /**
   * Sets whether this NavajoClient uses a secure(https) or insecure(http) connection.
   * Required is a keystore and passphrase
   * @param keystore String
   * @param passphrase String
   * @param useSecurity boolean
   * @throws ClientException
   */
  public final void setSecure(String keystore, String passphrase, boolean useSecurity) throws ClientException {
    setSecure = useSecurity;
    try {
      setSecure(new FileInputStream(new File(keystore)), passphrase, useSecurity);
    }
    catch (java.io.FileNotFoundException fnfe) {
      fnfe.printStackTrace(System.err);
      //throw new ClientException(-1, -1, fnfe.getMessage());
    }
  }

  /**
   * Sets whether this NavajoClient uses a secure(https) or insecure(http) connection.
   * Required is a keystore from an InputStream and passphrase
   * @param keystore InputStream to keystore resource.
   * @param passphrase passphrase to keystore resource.
   * @param useSecurity if true TLS security is enabled.
   * @throws ClientException
   */
  public final void setSecure(InputStream keystore, String passphrase, boolean useSecurity) throws ClientException {
    setSecure = useSecurity;
    //System.err.println("------------------------------------------------>>>>>> Calling latest VERSION OF setScure!?");
    if (sslFactory == null) {

      try {
        SSLContext ctx;
        KeyManagerFactory kmf;
        KeyStore ks;
        char[] passphraseArray = passphrase.toCharArray();
        ctx = SSLContext.getInstance("TLS");
        kmf = KeyManagerFactory.getInstance("SunX509");
        ks = KeyStore.getInstance("JKS");
        ks.load(keystore, passphraseArray);
        kmf.init(ks, passphraseArray);
        ctx.init(kmf.getKeyManagers(), new MyX509TrustManager[] {new MyX509TrustManager()}
                 , null);
        sslFactory = ctx.getSocketFactory();
      }
      catch (Exception e) {
        e.printStackTrace(System.err);
        // throw new ClientException(-1, -1, e.getMessage());
      }
      //this.passphrase = passphrase;
    }
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
    URLConnection con = null;
    if (sslFactory == null) {
      con = (HttpURLConnection) url.openConnection();
    }
    else {
      HttpsURLConnection urlcon = (HttpsURLConnection) url.openConnection();
      urlcon.setSSLSocketFactory(sslFactory);
      con = urlcon;
    }
    con.setDoOutput(true);
    con.setDoInput(true);
    con.setUseCaches(false);
    con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
    return con;
  }

  protected final void readErrorStream(final HttpURLConnection myCon) {
	  try {
		  if ( myCon != null ) {
			  int respCode = myCon.getResponseCode();
			  InputStream es = myCon.getErrorStream();
			  int ret = 0;
			  // read the response body
			  byte [] buf = new byte[32];
			  while ((ret = es.read(buf)) > 0) {
				  // Ignore errorstream.
			  }
			  // close the errorstream
			  es.close();
		  }
	  } catch (IOException ioe) {
		  ioe.printStackTrace(System.err);
	  }
  }
  
//  protected InputStream doTransaction(String name, Navajo d, boolean useCompression, HttpURLConnection myCon) throws IOException, ClientException, NavajoException, javax.net.ssl.SSLHandshakeException {
//	  HttpClient httpclient = new HttpClient();
//	  PostMethod httppost = new PostMethod("http://" + name);
//	  httppost.setContentChunked(true);
//	  httppost.setHttp11(true);
//	  httppost.setRequestHeader("Accept-Encoding", "gzip");
//	  httppost.setRequestHeader("Content-Encoding", "gzip");
//	  try {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		d.write(bos);
//		httppost.setRequestBody(new ByteArrayInputStream(bos.toByteArray()));
//	    httpclient.executeMethod(httppost);
//	    return httppost.getResponseBodyAsStream();
//	    // consume the response entity 
//	  } finally {
//		  //httppost.releaseConnection();
//	  }
//  }
  /**
   * Do a transation with the Navajo Server (name) using
   * a Navajo Message Structure (TMS) compliant XML document.
   * @param name String
   * @param d Navajo
   * @param useCompression boolean
   */
  
  protected InputStream doTransaction(String name, Navajo d, boolean useCompression, boolean forcePreparseProxy, HttpURLConnection myCon) throws IOException, ClientException, NavajoException, javax.net.ssl.SSLHandshakeException {
    URL url;
    //useCompression = false;
    
    
    if (setSecure) {
      url = new URL("https://" + name);
    }
    else {
      url = new URL("http://" + name);
    }

    HttpURLConnection con = null;
    if (sslFactory == null) {
      con = (HttpURLConnection) url.openConnection();
      ( (HttpURLConnection) con).setRequestMethod("POST");
    }
    else {
      HttpsURLConnection urlcon = (HttpsURLConnection) url.openConnection();
      urlcon.setSSLSocketFactory(sslFactory);
      con = urlcon;
      urlcon.setHostnameVerifier(new HostnameVerifier() {
    	  public boolean verify(String hostname, SSLSession session) {
    		  return true;
    	  }
      });
    }
    
    myCon = con;
   
    try {
    	java.lang.reflect.Method timeout = con.getClass().getMethod("setConnectTimeout", new Class[]{int.class});
    	timeout.invoke( con, new Object[]{new Integer(CONNECT_TIMEOUT)});
    }catch (SecurityException e) {
    }
    catch (Throwable e) {
     	System.err.println("setConnectTimeout does not exist, upgrade to java 1.5+");
    }
    
    con.setDoOutput(true);
    con.setDoInput(true);
    con.setUseCaches(false);
    //con.setRequestProperty("Connection", "keep-alive");
    con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
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
    
    // Send message
    if (useCompression) {
    	con.setRequestProperty("Accept-Encoding", "jzlib");
    	con.setRequestProperty("Content-Encoding", "jzlib");
    	
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
    }
    else {
    	BufferedWriter os = null;
    	try {
    		os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
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
    // Lees bericht
//    BufferedInputStream in = null;
//    if (useCompression) {
//      in = new BufferedInputStream(new java.util.zip.GZIPInputStream(con.getInputStream()));
//    }
//    else {
//      in = new BufferedInputStream(con.getInputStream());
//    }
    
    if ( useCompression ) {
    	return new ZInputStream(con.getInputStream());
    } else {
    	return con.getInputStream();
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
    return doSimpleSend(out, server, method, user, password, expirationInterval, true, false);
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

    if (cachedServiceNameMap.get(method) != null) {
      cacheKey = method + out.persistenceKey();
      if (serviceCache.get(cacheKey) != null) {
        //System.err.println("---------------------------------------------> Returning cached WS");
        Navajo cached = (Navajo) serviceCache.get(cacheKey);
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
    header.setHeaderAttribute("clientToken", getSessionToken());
    // ========= Adding globalMessages
    Iterator entries = globalMessages.entrySet().iterator();
    while (entries.hasNext()) {
      Map.Entry entry = (Map.Entry) entries.next();
      Message global = (Message) entry.getValue();
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
        Header h = out.getHeader();
    	 if (out.getHeader()==null) {
		} else {
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
     	 
    	 
    	 
        InputStream in = null;
        Navajo n = null;
        HttpURLConnection myCon = null;
        long timeStamp = System.currentTimeMillis();
        
        try {	
        	in = doTransaction(server, out, useCompression, allowPreparseProxy, myCon);
        	n = NavajoFactory.getInstance().createNavajo(in);
        }
        catch (javax.net.ssl.SSLException ex) {
          n = NavajoFactory.getInstance().createNavajo();
          generateConnectionError(n, 666666, "Wrong certificate or ssl connection problem: " + ex.getMessage());
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
          n = NavajoFactory.getInstance().createNavajo();
          in = retryTransaction(server, out, useCompression, allowPreparseProxy, retryAttempts, retryInterval, n); // lees uit resource

          if (in != null) {
            n = null;
          }
          if (n == null) {
              n = NavajoFactory.getInstance().createNavajo(in);
              System.err.println("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getHeaderAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
              n.getHeader().setHeaderAttribute("sourceScript", callingService);
          }
        }
        catch (IOException uhe) {
        	uhe.printStackTrace();
        	readErrorStream(myCon);
          System.err.println("Generic IOException: "+uhe.getMessage()+". Retrying without compression...");
          n = NavajoFactory.getInstance().createNavajo();
          in = retryTransaction(server, out, false, allowPreparseProxy, retryAttempts, retryInterval, n); // lees uit resource

          if (in != null) {
            n = null;
          }
          if (n == null) {
              n = NavajoFactory.getInstance().createNavajo(in);
              System.err.println("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getHeaderAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
              n.getHeader().setHeaderAttribute("sourceScript", callingService);
          }
        } catch(Throwable r) {
        	r.printStackTrace();
        }
        finally {
        	 if (n.getHeader()!=null) {
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
                 Map headerAttributes = n.getHeader().getHeaderAttributes();
                 Map pbd = new HashMap(headerAttributes);
                 pbd.put("type","performanceStats");
                 pbd.put("service",method);
                 synchronized (piggyBackData) {
                 	piggyBackData.add(pbd);
					}
//                 System.err.println(method+": totaltime = " + ( clientTime / 1000.0 )+ ", servertime = " + ( totalTime / 1000.0 )+" transfertime = "+((clientTime-totalTime)/1000)+" piggybackdata: "+piggyBackData.size()); 
				} else {
					System.err.println("Null header in input message");
				}
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (myResponder != null) {
          myResponder.check(n);
          myResponder.checkForAuthentication(n);
          myResponder.checkForAuthorization(n);
        }
        fireActivityChanged(false, method, getQueueSize(), getActiveThreads(), 0);

        if (cachedServiceNameMap.get(method) != null) {
          serviceCache.put(cacheKey, n);
        }
        checkForComparedServices(method, n);
        
        // Process broadcasts
        fireBroadcastEvents(n);
        
        // ROUND ROBIN FOR NOW:
        switchServer(currentServerIndex,false);
        
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

  protected boolean shouldOutputStreamClose() {
	  return true;
  }


  private final void fireBroadcastEvents(final Navajo n) {
	  Header h = n.getHeader();
	  
	  
	  if (h==null) {
			// no headers, don't know why. So no broadcasting.
		  return;
	  }
	  Set s = h.getPiggybackData();
	  if (s==null) {
		return;
	}
	  for (Iterator iter = s.iterator(); iter.hasNext();) {
		Map element = (Map) iter.next();
		if ("broadcast".equals(element.get("type"))) {
			String message = (String)element.get("message");
			for (int i = 0; i < broadcastListeners.size(); i++) {
				      BroadcastListener current = (BroadcastListener) broadcastListeners.get(i);
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
		  for (Iterator iter = piggyBackData.iterator(); iter.hasNext();) {
			  Map element = (Map) iter.next();
			  header.addPiggyBackData(element);
		  }
		  // remove piggyback data.
		  piggyBackData.clear();
	  }

  }

private final InputStream retryTransaction(String server, Navajo out, boolean useCompression, boolean allowPreparseProxy, int attemptsLeft, long interval, Navajo n) throws Exception {
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
    	switchServer(currentServerIndex,true);
    	server = getCurrentHost();
	}
    
    HttpURLConnection myCon = null;
    try {
    	try {
    		Thread.sleep(interval);
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	in = doTransaction(server, out, useCompression, allowPreparseProxy, myCon);
    	System.err.println("It worked!  the inputstream is: " + in);
    	return in;
    }
    catch (javax.net.ssl.SSLException ex) {
    	generateConnectionError(n, 666666, "Wrong certificate or ssl connection problem: " + ex.getMessage());
    }
    catch (java.net.UnknownHostException uhe) {
    	generateConnectionError(n, 7777777, "Unknown host: " + uhe.getMessage());
    }
    catch (java.net.NoRouteToHostException uhe) {
    	generateConnectionError(n, 55555, "No route to host: " + uhe.getMessage());
    }
    catch (java.net.SocketException uhe) {
    	attemptsLeft--;
    	if (attemptsLeft == 0) {
    		disabledServers.put(getCurrentHost(), new Long(System.currentTimeMillis()));
    		System.err.println("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    		switchServer(currentServerIndex,true);
    		generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
    	}
    	else {
    		return retryTransaction(server, out, useCompression, allowPreparseProxy, attemptsLeft, interval, n);
    	}
    }
    catch (IOException uhe) {
    	readErrorStream(myCon);
    	if (attemptsLeft == 0) {
    		disabledServers.put(getCurrentHost(), new Long(System.currentTimeMillis()));
    		System.err.println("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    		switchServer(currentServerIndex,true);
    		generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
    	}
    	else {
    		attemptsLeft--;
    		System.err.println("---> Got a 500 server exception");
    		System.err.println("Sending: ");
    		out.write(System.err);
    		return retryTransaction(server, out, false, allowPreparseProxy, attemptsLeft, interval, n);
    	}
    }
    return in;
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
   * Not supported
   * @param request Message
   * @param service String
   * @param responseMsgName String
   * @param startIndex int
   * @param endIndex int
   * @param total int
   * @return LazyMessage
   */
  public final LazyMessage doLazySend(Message request, String service, String responseMsgName, int startIndex, int endIndex, int total) {
    // is this one used?
    throw new UnsupportedOperationException("Lazy message are not yet supported in the implementation!");
  }

  /**
   * Not supported
   * @param n Navajo
   * @param service String
   * @param lazyMessageName String
   * @param startIndex int
   * @param endIndex int
   * @param total int
   * @throws ClientException
   * @return LazyMessage
   */
  public final LazyMessage doLazySend(Navajo n, String service, String lazyMessageName, int startIndex, int endIndex, int total) throws ClientException {

    n.addLazyMessagePath(lazyMessageName, startIndex, endIndex, total);
    Navajo reply = doSimpleSend(n, service);

    Message m = reply.getMessage(lazyMessageName);
    if (m == null) {
      return null;
    }
    if (!LazyMessage.class.isInstance(m)) {
      System.err.println("No lazy result returned after lazy send!");
      return (LazyMessage) m;
    }
    else {
      LazyMessage l = (LazyMessage) m;
      l.setResponseMessageName(lazyMessageName);
      l.setRequest(service, n);
      return l;
    }
  }

  /**
   * Not supported
   * @param n Navajo
   * @param service String
   * @param lazyMessageName String
   * @param startIndex int
   * @param endIndex int
   * @param total int
   * @param v ConditionErrorHandler
   * @throws ClientException
   * @return LazyMessage
   */
  public final LazyMessage doLazySend(Navajo n, String service, String lazyMessageName, int startIndex, int endIndex, int total, ConditionErrorHandler v) throws ClientException {
    n.addLazyMessagePath(lazyMessageName, startIndex, endIndex, total);
    Navajo reply = doSimpleSend(n, service, v);
    Message m = reply.getMessage(lazyMessageName);
    if (m == null) {
      return null;
    }
    if (!LazyMessage.class.isInstance(m)) {
      System.err.println("No lazy result returned after lazy send!");
      return (LazyMessage) m;
    }
    else {
      LazyMessage l = (LazyMessage) m;
      l.setResponseMessageName(lazyMessageName);
      l.setRequest(service, n);
      return l;
    }
  }

  /**
   * Not supported
   * @param request Navajo
   * @param service String
   * @param lazyPath String
   * @param startIndex int
   * @param endIndex int
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo createLazyNavajo(Navajo request, String service, String lazyPath, int startIndex, int endIndex) throws ClientException {
    return null;
  }

  /**
   * Nor supported
   * @param request Navajo
   * @param startIndex int
   * @param endIndex int
   * @throws ClientException
   * @return Navajo
   */
  public final Navajo performLazyUpdate(Navajo request, int startIndex, int endIndex) throws ClientException {
    return null;
  }

  /**
   * Not supported
   * @return boolean
   */
  public final boolean useLazyMessaging() {
    return useLazyMessaging;
  }

  /**
   * Not supported
   * @param b boolean
   */
  public final void setUseLazyMessaging(boolean b) {
    useLazyMessaging = b;
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
   * Store a NavajoClient property
   * @param key String
   * @param value Object
   */
  public final void setClientProperty(String key, Object value) {
    propertyMap.put(key, value);
  }

  /**
   * Get a NavajoClient property
   * @param key String
   * @return Object
   */
  public final Object getClientProperty(String key) {
    return propertyMap.get(key);
  }

  /**
   * Return this NavajoClient's ErrorHandler
   * @return ErrorResponder
   */
  public final ErrorResponder getErrorHandler() {
    return myResponder;
  }

  /**
   * Set the ErrorHandler
   * @param e ErrorResponder
   */
  public final void setErrorHandler(ErrorResponder e) {
    myResponder = e;
  }

  /**
   * Send the given Exception to the ErrorHandler
   * @param e Exception
   */
  public final void displayException(Exception e) {
    if (myResponder != null) {
      myResponder.check(e);
    }
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
      ActivityListener current = (ActivityListener) myActivityListeners.get(i);
      current.setWaiting(b, service, queueSize, activeThreads, millis);
    }
  }

  /**
   * Add broadcastlistener
   * @param al ActivityListener
   */
  public final void addBroadcastListener(BroadcastListener al) {
    broadcastListeners.add(al);
  }

  /**
   * Remove broadcastlistener
   * @param al ActivityListener
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
    return (ServerAsyncRunner) asyncRunnerMap.get(id);
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
      Iterator it = asyncRunnerMap.keySet().iterator();
      while (it.hasNext()) {
        String id = (String) it.next();
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

  public static String createSessionToken() throws UnknownHostException {
		String userName = null;
		try {
			userName = System.getProperty("user.name");
		} catch (SecurityException e) {
			userName = "UnknownUser";
		}

		return userName + "|" + (InetAddress.getLocalHost().getHostAddress())
				+ "|" + (InetAddress.getLocalHost().getHostName()) + "|"
				+ (System.currentTimeMillis());
	}
  
  public static void main(String[] args) throws Exception {
	  
	
	    URL  url = new URL("http://hera3.dexels.com/sportlink/knvb/servlet/Postman");
	  
	    HttpURLConnection con = null;
	   
	      con = (HttpURLConnection) url.openConnection();
	      ( (HttpURLConnection) con).setRequestMethod("POST");
	   
	   
	    try {
	    	java.lang.reflect.Method timeout = con.getClass().getMethod("setConnectTimeout", new Class[]{int.class});
	    	timeout.invoke( con, new Object[]{new Integer(CONNECT_TIMEOUT)});
	    }catch (SecurityException e) {
	    }
	    catch (Throwable e) {
	     	System.err.println("setConnectTimeout does not exist, upgrade to java 1.5+");
	    }
	    
	    con.setDoOutput(true);
	    con.setDoInput(true);
	    con.setUseCaches(false);
	    //con.setRequestProperty("Connection", "keep-alive");
	    con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");

	    try {
	    	java.lang.reflect.Method chunked = con.getClass().getMethod("setChunkedStreamingMode", new Class[]{int.class});
	    	chunked.invoke( con, new Object[]{new Integer(1024)});
	    	con.setRequestProperty("Transfer-Encoding", "chunked" );
	    } catch (SecurityException e) {
	    } catch (Throwable e) {
	     	System.err.println("setChunkedStreamingMode does not exist, upgrade to java 1.5+");
	    }
	    
	    // Send message
	   
	    	con.setRequestProperty("Accept-Encoding", "jzlib");
	    	con.setRequestProperty("Content-Encoding", "jzlib");
	    	
	    	BufferedWriter out = null;
	    	try {
	    		out = new BufferedWriter(new OutputStreamWriter(new ZOutputStream(con.getOutputStream(), JZlib.Z_BEST_SPEED), "UTF-8"));
	    		//d.write(out, false, d.getHeader().getRPCName());
	    		out.write("<ROMMEL/>");
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
 
	   
  }

public void destroy() {
	// TODO Auto-generated method stub
	
}

private final void ping() {
	// Start thread to periodically check servers.
	if ( !pingStarted) {
		new Thread() {
			public void run() {

				System.err.println("Started ping thread.");
				Navajo out = NavajoFactory.getInstance().createNavajo();
				Header outHeader = NavajoFactory.getInstance().createHeader(out, "navajo_ping", "NAVAJO", "", -1);
				out.addHeader(outHeader);
				while (!killed) {
					try {
						Thread.sleep(60000);
						System.err.println("servers: " + serverUrls.length);
						for (int i = 0; i < serverUrls.length; i++) {
							try {
								HttpURLConnection myCon = null;
								InputStream in  = doTransaction(serverUrls[i], out, false, false, myCon);
								Navajo n = NavajoFactory.getInstance().createNavajo(in);
								in.close();
								Header h = n.getHeader();
								String load =  h.getHeaderAttribute("cpuload");
								serverLoads[i] = Double.parseDouble(load);
								System.err.println(serverUrls[i] + "=" + serverLoads[i]);
							} catch (Throwable e) {
								e.printStackTrace(System.err);
							}
							
						}
					} catch (InterruptedException e) {
						e.printStackTrace(System.err);
					}
				}

			}
		}.start();
		pingStarted = true;
	}
}

public void setServers(String[] servers) {
	serverUrls = servers;
	serverLoads = new double[serverUrls.length];
	if (servers.length>0) {
		currentServerIndex = randomize.nextInt(servers.length);
		System.err.println("Starting at server # "+currentServerIndex);
	}
	ping();
}

public String getCurrentHost() {
	if (serverUrls!=null && serverUrls.length>0) {
		return serverUrls[currentServerIndex];
	}
	return null;
}

public final String getCurrentHost(int serverIndex) {
		return serverUrls[serverIndex];
}

public final void switchServer(int startIndex, boolean forceChange) {
	if (serverUrls==null || serverUrls.length==0) {
		return;
	}
	if (serverUrls.length==1) {
		// Nothing to switch
		return;
	}
	
	double minload = 1000000.0;
	int candidate = -1;
	
	for (int i = 0; i < serverUrls.length; i++) {
		if ( serverLoads[i] < minload && serverLoads[i] != -1.0 ) { // If there is really a server with a lower load, use this server as candidate.
			minload = serverLoads[i];
			candidate = i;
		}
	}
	
	if ( candidate == -1 ) {
		// Round robin...
		if (currentServerIndex==(serverUrls.length-1)) {
			currentServerIndex = 0;
		} else {
			currentServerIndex++;
		}
	} else {
		currentServerIndex = candidate;
	}
	
	System.err.println("currentServer = " + serverUrls[currentServerIndex] + " with load: " + serverLoads[currentServerIndex]);
	
	if (startIndex == currentServerIndex) {
		//System.err.println("BACK AT THE ORIGINAL SERVER!!!!");
//		if (!forceChange) {
			return;
//		}
//		throw new RuntimeException("No enabled servers left!");
	}
	String nextServer = serverUrls[currentServerIndex];
	//System.err.println("Current Server now: "+nextServer);

	if (disabledServers.containsKey(nextServer)) {
		Long timeout = (Long)disabledServers.get(nextServer);
		long t = timeout.longValue();
		if (t+serverDisableTimeout<System.currentTimeMillis()) {
			// The disabling time has passed.
			System.err.println("Reinstating server: "+ nextServer+" its timeout has passed.");
			disabledServers.remove(nextServer);
			return;
		} else {
			switchServer(startIndex,forceChange);
		}
	}
	
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
						// TODO Auto-generated catch block
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
			    return doSimpleSend(out, getCurrentHost(serverIndex), method, username, password, -1, true, true);
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
	public Navajo doScheduledSend(Navajo out, String method, String schedule) throws ClientException {
		
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
		
		h.setSchedule(triggerURL);
		
		return doSimpleSend(out, method);
	}
	
	public void finalize() {
		killed = true;
	}
	
	public void dispose() {
		killed = true;
	}
	
	

}