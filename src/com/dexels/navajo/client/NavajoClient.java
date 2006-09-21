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
import java.util.*;

import javax.net.ssl.*;
import javax.servlet.http.*;

import com.dexels.navajo.client.serverasync.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.BaseHeaderImpl;
import com.dexels.navajo.document.saximpl.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.client.impl.*;

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
//  private String host = null;
  private String username = null;
  private String password = null;
  protected boolean condensed = true;

  private String[] serverUrls;
  
  
  // Threadsafe collections:
  private Map globalMessages = new HashMap();
  private Map serviceCache = Collections.synchronizedMap(new HashMap());
  private Map cachedServiceNameMap = new HashMap();
  private Map asyncRunnerMap = Collections.synchronizedMap(new HashMap());
  private Map propertyMap = Collections.synchronizedMap(new HashMap());
  private List myActivityListeners = Collections.synchronizedList(new ArrayList());
  
  private final List broadcastListeners = Collections.synchronizedList(new ArrayList());
  
  private long timeStamp = 0;
  // Standard option: use HTTP protocol.
  private int protocol = HTTP_PROTOCOL;
  private boolean useLazyMessaging = true;
  private ErrorResponder myResponder;
  private boolean setSecure = false;
  private SSLSocketFactory sslFactory = null;
  private String keystore, passphrase;
  private long retryInterval = 1000; // default retry interval is 1000 milliseconds
  private int retryAttempts = 10; // default three retry attempts
  private int switchServerAfterRetries = 2;
  
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
    System.err.println("Added comparedservices: " + serviceQuery + ".." + serviceUpdate);
    comparedServicesQueryToUpdateMap.put(serviceQuery, serviceUpdate);
    comparedServicesUpdateToQueryMap.put(serviceUpdate, serviceQuery);
  }

  private final void checkForComparedServices(String queryService, Navajo n) {
    try {
      String s = (String) comparedServicesQueryToUpdateMap.get(queryService);
      if (s != null) {
        System.err.println("Storing Navajo object for service: " + queryService);
        storedNavajoComparisonMap.put(s, n.copy());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean hasComparedServiceChanged(String updateService, Navajo n) {
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
  public String getClientName() {
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

  
  
  public void finalize() {
	  
  }
  
  
  public String getSessionToken() {
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
  public String getUsername() {
    return username;
  }

  /**
   * Gets this NavajoClient object's password
   * @return String
   */
  public String getPassword() {
//    System.err.println("Getting password: "+password);
    return password;
  }

  /**
   * Gets this NavajoClient object's server URL
   * @return String
   */
  public String getServerUrl() {
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
   * @deprecated
   * USE SET SERVERURLS
   */
  public final void setServerUrl(String url) {
//    host = url;
	  System.err.println("Warning: setServerURL is deprecated!");
	  serverUrls = new String[]{url};
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
   * Add a webservice (by name) to the NavajoClient cache mechanism. This provides a way to store frequenly used webservices
   * in the NavajoClient and thus preventing a server roundtrip.
   * @param service String
   */
  public final void addCachedService(String service) {
    cachedServiceNameMap.put(service, service);
  }

  /**
   * Remove (by name) a specific webservice from the NavajoClient's cache mechanism
   * @param service String
   */
  public final void removeCachedService(String service) {
    cachedServiceNameMap.remove(service);
    serviceCache.remove(service);
  }

  /**
   * Remove all webservices from the NavajoClient's cache
   */
  public final void clearCache() {
    serviceCache.clear();
  }

  /**
   * This method removes the cached instance of the given webservice, but will continue to cache it when this
   * service is called upon again.
   * @param service String
   */
  public final void clearCache(String service) {
    serviceCache.remove(service);
  }

  /**
   * Add a global message to this NavajoClient. Global messages are messages that will ALWAYS be put in ALL requests
   * made by the NavajoClient instance. This way we can provide global information we want to use for every webservice.
   * @param m Message
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
  public boolean removeGlobalMessage(Message m) {
    return globalMessages.remove(m.getName()) != null;
  }

  /***
   * Returns a global message, addressed by name
   * @param name
   * @return
   */
  public Message getGlobalMessage(String name) {
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
   * Expirimental, not in use
   * @param u URL
   * @param n Navajo
   * @throws ClientException
   * @throws IOException
   * @return Navajo
   */
  public final Navajo doSimpleUrlSend(URL u, Navajo n) throws ClientException, IOException {
    NavajoHttpUrlConnection hhuc = (NavajoHttpUrlConnection) u.openConnection();
    return hhuc.doTransaction(n);
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
    return doSimpleSend(out, getCurrentHost(), method, username, password, expirationInterval, true);
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
    System.err.println("------------------------------------------------>>>>>> Calling latest VERSION OF setScure!?");
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
      this.passphrase = passphrase;
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

  /**
   * Do a transation with the Navajo Server (name) using
   * a Navajo Message Structure (TMS) compliant XML document.
   * @param name String
   * @param d Navajo
   * @param useCompression boolean
   */
  
  
  // TODO: Are all streams closed? I am not sure how URLConnections handle it
  private final BufferedInputStream doTransaction(String name, Navajo d, boolean useCompression) throws IOException, ClientException, NavajoException, javax.net.ssl.SSLHandshakeException {
    URL url;
    
    if (setSecure) {
      url = new URL("https://" + name);
    }
    else {
      url = new URL("http://" + name);
    }
    System.err.println("in doTransaction: opening url: " + url.toString());
    HttpURLConnection con = null;
    if (sslFactory == null) {
      con = (HttpURLConnection) url.openConnection();
      ( (HttpURLConnection) con).setRequestMethod("POST");
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

    try {
    	java.lang.reflect.Method chunked = con.getClass().getMethod("setChunkedStreamingMode", new Class[]{int.class});
    	chunked.invoke( con, new Object[]{new Integer(1000)});
    	con.setRequestProperty("Transfer-Encoding", "chunked" );
    } catch (Throwable e) {
    	//e.printStackTrace(System.err);
    	System.err.println("setChunkedStreamingMode does not exist, upgrade to java 1.5+");
    }
    
    // Verstuur bericht
    if (useCompression) {
    	con.setRequestProperty("Accept-Encoding", "gzip");
    	con.setRequestProperty("Content-Encoding", "gzip");
    	OutputStream os = null;
    	java.util.zip.GZIPOutputStream out = null;
    	try {
    		os = con.getOutputStream();
    		out = new java.util.zip.GZIPOutputStream(os);
    		d.write(out, condensed, d.getHeader().getRPCName());
    	} finally  {
//    		if ( os != null ) {
//    			try {
//					os.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//    		}
    		if ( out != null ) {
    			try {
    				out.flush();
    				out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    else {
    	OutputStream os = null;
    	try {
    		con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
    		os = con.getOutputStream();
    		d.write(os, condensed, d.getHeader().getRPCName());
    	}
    	catch (java.net.NoRouteToHostException nrthe) {
    		throw new ClientException( -1, 20, "Could not connect to URI: " + name + ", check your connection");
    	}
    	catch (java.net.SocketException se) {
    		se.printStackTrace();
    		throw new ClientException( -1, 21, "Could not connect to network, check your connection");
    	} finally {
    		if ( os != null ) {
    			os.close();
    		}
    	}
    }
    // Lees bericht
    BufferedInputStream in = null;
    if (useCompression) {
      java.util.zip.GZIPInputStream unzip = new java.util.zip.GZIPInputStream(con.getInputStream());
      in = new BufferedInputStream(unzip);
    }
    else {
      in = new BufferedInputStream(con.getInputStream());
    }
    lastActivity = System.currentTimeMillis();
    return in;
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
    return doSimpleSend(out, server, method, user, password, expirationInterval, true);
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
  public final Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval, boolean useCompression) throws ClientException {
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
        System.err.println("---------------------------------------------> Returning cached WS");
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
    header.setAttribute("clientToken", getSessionToken());
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

        BufferedInputStream in = null;
        Navajo n = null;
        try {
        	long timeStamp = System.currentTimeMillis();
        	
        	in = doTransaction(server, out, useCompression);
//            if (n == null) {
                n = NavajoFactory.getInstance().createNavajo(in);
                if (n.getHeader()!=null) {
                    n.getHeader().setAttribute("sourceScript", callingService);
                    clientTime = (System.currentTimeMillis()-timeStamp);
                    n.getHeader().setAttribute("clientTime", ""+clientTime);
                    String tot = n.getHeader().getAttribute("serverTime");
                    long totalTime = -1;
                    if (tot!=null&& !"".equals(tot)) {
                    	totalTime = Long.parseLong(tot);
                    	n.getHeader().setAttribute("transferTime",""+(clientTime-totalTime));
    				} 
                    Map headerAttributes = n.getHeader().getAttributes();
                    Map pbd = new HashMap(headerAttributes);
                    pbd.put("type","performanceStats");
                    pbd.put("service",method);
                    synchronized (piggyBackData) {
                    	piggyBackData.add(pbd);
					}
                    System.err.println(method+": totaltime = " + ( clientTime / 1000.0 )+ ", servertime = " + ( totalTime / 1000.0 )+" transfertime = "+((clientTime-totalTime)/1000)+" piggybackdata: "+piggyBackData.size()); 
				} else {
					System.err.println("Null header in input message");
				}
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
          in = retryTransaction(server, out, useCompression, retryAttempts, retryInterval, n); // lees uit resource

          if (in != null) {
            n = null;
          }
          if (n == null) {
              n = NavajoFactory.getInstance().createNavajo(in);
              System.err.println("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
              n.getHeader().setAttribute("sourceScript", callingService);
          }
        }
        catch (IOException uhe) {
        	uhe.printStackTrace();
          System.err.println("Generic IOException: "+uhe.getMessage()+". Retrying without compression...");
          n = NavajoFactory.getInstance().createNavajo();
          in = retryTransaction(server, out, false, retryAttempts, retryInterval, n); // lees uit resource

          if (in != null) {
            n = null;
          }
          if (n == null) {
              n = NavajoFactory.getInstance().createNavajo(in);
              System.err.println("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
              n.getHeader().setAttribute("sourceScript", callingService);
          }
        } finally {
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



  private void fireBroadcastEvents(Navajo n) {
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
		  for (Iterator iter = piggyBackData.iterator(); iter.hasNext();) {
			  Map element = (Map) iter.next();
			  header.addPiggyBackData(element);
		  }
		  // remove piggyback data.
		  piggyBackData.clear();
	  }
	  
  }

private final BufferedInputStream retryTransaction(String server, Navajo out, boolean useCompression, int attemptsLeft, long interval, Navajo n) throws Exception {
    BufferedInputStream in = null;
    System.err.println("------------> retrying transaction: " + server + ", attempts left: " + attemptsLeft);
    
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
    
    try {
      try {
        Thread.sleep(interval);
    } catch (InterruptedException e) {
         e.printStackTrace();
    }
      in = doTransaction(server, out, useCompression);
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
        generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
      }
      else {
        return retryTransaction(server, out, useCompression, attemptsLeft, interval, n);
      }
    }
    catch (IOException uhe) {
      if (attemptsLeft == 0) {
        generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
      }
      else {
          attemptsLeft--;
       System.err.println("---> Got a 500 server exception");
       System.err.println("Sending: ");
       out.write(System.err);
        return retryTransaction(server, out, false, attemptsLeft, interval, n);
      }
    }
    return in;
  }

  /**
   * Execute an action that is eiher defined in the action buffer
   * or is otherwise an existing action known by the Navajo server
   * (i.e. an initial action to request a service). If the action
   * is defined in the action buffer the required messages for that
   * action are assembled from the message buffer in an output
   * XML document that is sent to the Navajo server. An input XML
   * document is received from the Navajo server from which the
   * messages are appended to the message buffer and an action buffer
   * is created based upon the defined actions in the input XML
   * document. The method returns a list of received messages that
   * can be processed by the client application.
   *
   * PRE CONDITION: Either of the following situations must be valid:
   * 1. Both action and message buffer are empty and the method is
   *    known at the Navajo server but does not require any messages
   *    as parameter.
   * 2. The action buffer and the message buffer exist. The method
   *    must be defined in the action buffer. The required messages
   *    must be present in the message buffer.
   *
   * POST CONDITION: Newly received messages are appended to the
   * message buffer. If there are any new actions received a
   * clean action buffer is created.
   *
   * @deprecated
   *
   */
  protected final Navajo doMethod(String method, String user, String password, Navajo message, String server, boolean secure, String keystore, String passphrase, long expirationInterval, HttpServletRequest request, boolean stripped, boolean checkMethod, boolean useCompression) throws NavajoException, ClientException {
    int j;
    Navajo out = NavajoFactory.getInstance().createNavajo();
    if (message.getMessageBuffer() != null) {
      // Find the required messages for the given rpcName
      ArrayList req = null;
      if (checkMethod) {
          System.err.println("Checking method for required. Don't know if this is working. Beware.");
          com.dexels.navajo.document.Method dummy = message.getMethod(method);
        if (dummy != null) {
          req = dummy.getRequiredMessages();
        }
        if ( (req != null) && (req.size() > 0)) {
          for (j = 0; j < req.size(); j++) {
            if (message.getMessage( (String) req.get(j)) != null) {
              out.importMessage(message.getMessage( (String) req.get(j)));
            }
          }
        }
      }
      else {
        req = message.getAllMessages();
        for (int k = 0; k < req.size(); k++) {
          Message msg = (Message) req.get(k);
          out.importMessage(msg);
        }
      }
    }
    Navajo docIn = null;
    BufferedInputStream in = null;
    try {
      if (protocol == HTTP_PROTOCOL) {
        in = doTransaction(server, out, useCompression);
        docIn = NavajoFactory.getInstance().createNavajo(in);
      }
      else {
        throw new ClientException( -1, -1, "Unknown protocol: " + protocol);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      throw NavajoFactory.getInstance().createNavajoException("An error occured in doMethod(): " + e.getMessage());
    }
    finally {
    	if ( in != null ) {
    		try {
				in.close();
			} catch (IOException e) {
				// NOT INTERESTED.
			}
    	}
    }
    return docIn;
  }

  /**
   * See doMethod
   * @param method String
   * @param user String
   * @param password String
   * @param message Navajo
   * @param secure boolean
   * @param keystore String
   * @param passphrase String
   * @param expirationInterval long
   * @param request HttpServletRequest
   * @param useCompression boolean
   * @throws NavajoException
   * @throws ClientException
   * @return Navajo
   * @deprecated
   */
  protected final Navajo doMethod(String method, String user, String password, Navajo message, boolean secure, String keystore, String passphrase, long expirationInterval, HttpServletRequest request, boolean useCompression) throws NavajoException, ClientException {
    return doMethod(method, user, password, message, secure, keystore, passphrase, expirationInterval, request, false, useCompression);
  }

  /**
   * See doMethod
   * @param method
   * @param user
   * @param password
   * @param message
   * @param server
   * @param secure
   * @param keystore
   * @param passphrase
   * @param expirationInterval
   * @param request
   * @param useCompression
   * @return
   * @throws NavajoException
   * @throws ClientException
   *
   * @deprecated
   */
  protected final Navajo doMethod(String method, String user, String password, Navajo message, String server, boolean secure, String keystore, String passphrase, long expirationInterval, HttpServletRequest request, boolean useCompression) throws NavajoException, ClientException {
    return doMethod(method, user, password, message, server, secure, keystore, passphrase, expirationInterval, request, false, false, useCompression);
  }

  /**
   * See doMethod
   * @param method String
   * @param user String
   * @param password String
   * @param message Navajo
   * @param secure boolean
   * @param keystore String
   * @param passphrase String
   * @param expirationInterval long
   * @param request HttpServletRequest
   * @param stripped boolean
   * @param useCompression boolean
   * @throws NavajoException
   * @throws ClientException
   * @return Navajo
   */
  protected final Navajo doMethod(String method, String user, String password, Navajo message, boolean secure, String keystore, String passphrase, long expirationInterval, HttpServletRequest request, boolean stripped, boolean useCompression) throws NavajoException, ClientException {
    String server = message.getMethod(method).getServer();
    if (server.equals("")) {
      throw NavajoFactory.getInstance().createNavajoException("No server found for RPC: " + method);
    }
    if (message == null) {
      throw NavajoFactory.getInstance().createNavajoException("doMethod(): empty Navajo message");
    }
    return doMethod(method, user, password, message, server, secure, keystore, passphrase, expirationInterval, request, stripped, false, useCompression);
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
	  return System.getProperty("user.name")+"|"+(InetAddress.getLocalHost().getHostAddress())+"|"+(InetAddress.getLocalHost().getHostName())+"|"+(System.currentTimeMillis());
  }
  
  public static void main(String[] args) throws Exception {
	  System.err.println(createSessionToken());
	  System.err.println(InetAddress.getLocalHost().getHostName());
	  System.getProperties().list(System.err);
      System.setProperty(SaxTester.DOC_IMPL, SaxTester.QDSAX);
      NavajoClientFactory.getClient().setServerUrl("ficus:3000/sportlink/knvb/servlet/Postman");
      NavajoClientFactory.getClient().setUsername("ROOT");
      NavajoClientFactory.getClient().setPassword("");
      
      Navajo n = NavajoClientFactory.getClient().doSimpleSend("documents/InitUpdateDocument" );
      Property id = n.getProperty("Document/DocumentId");
      id.setValue("148040");
      Navajo m = NavajoClientFactory.getClient().doSimpleSend(n,"documents/ProcessQueryDocument" );
//      m.write(System.err);
      Binary qddata = (Binary)m.getProperty("DocumentData/Data").getTypedValue();
      System.err.println("NAVAJO SAVED: ");
      
      System.err.println("Binary size: "+qddata.getLength());
      FileOutputStream fos = new FileOutputStream("c:/testjpg.jpg");
      qddata.write(fos);
      fos.flush();
      fos.close();
 
  }

public void destroy() {
	// TODO Auto-generated method stub
	
}

public void setServers(String[] servers) {
	serverUrls = servers;
}

public String getCurrentHost() {
	if (serverUrls!=null && serverUrls.length>0) {
		return serverUrls[currentServerIndex];
	}
	return null;
}

public void switchServer(int startIndex, boolean forceChange) {
	if (serverUrls==null || serverUrls.length==0) {
		return;
	}
	if (serverUrls.length==1) {
		// Nothing to switch
		return;
	}
	
	
	if (currentServerIndex==(serverUrls.length-1)) {
		currentServerIndex = 0;
	} else {
		currentServerIndex++;
	}
	if (startIndex == currentServerIndex) {
		System.err.println("BACK AT THE ORIGINAL SERVER!!!!");
		if (!forceChange) {
			return;
		}
		throw new RuntimeException("No enabled servers left!");
	}
	String nextServer = serverUrls[currentServerIndex];
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
}