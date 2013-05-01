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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;
import com.dexels.navajo.client.systeminfo.SystemInfoFactory;
import com.dexels.navajo.client.systeminfo.SystemInfoProvider;
import com.dexels.navajo.document.Guid;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.InflaterInputStream;

public class NavajoClient implements ClientInterface, Serializable {

	private static final long serialVersionUID = -7848349362973607161L;
public static final int DIRECT_PROTOCOL = 0;
  public static final int HTTP_PROTOCOL = 1;
  public static final int CONNECT_TIMEOUT = 5000;
  
//  private String host = null;
  private int loadBalancingMode = LBMODE_MANUAL;
  private String username = null;
  private String password = null;
  private boolean condensed = true;

  private String[] serverUrls;
  private double[] serverLoads;
  
  private final Random randomize = new Random(System.currentTimeMillis());

  private final static Logger logger = LoggerFactory
			.getLogger(NavajoClient.class);
	//  private NavajoPushSession pushSession = null;
  
  private int protocol = HTTP_PROTOCOL;
  private boolean useHttps = false;
//  private SSLSocketFactory sslFactory = null;
  //private String keystore, passphrase;
  private long retryInterval = 1000; // default retry interval is 500 milliseconds
  private int retryAttempts = 4; // default four retry attempts
  private int switchServerAfterRetries = 10; /** If same as retry attempts, never switch between servers, while in retry attempt. FOR NOW
  THIS IS A SAFE VALUE CAUSE INTEGRITY WORKER DOES NOT YET WORKER OVER MULTIPLE SERVER INSTANCES!!! */
  
  private int currentServerIndex;
  //private static int instances = 0;
  
  // Warning: Not thread safe!
  private final Set<Map<String,String>> piggyBackData = new HashSet<Map<String,String>>();
  private final Map<String,Long> disabledServers = new HashMap<String,Long>();


	private int globalRetryCounter = 0;
	private String localeCode = null;
	private String subLocale;
	private boolean allowCompression = true;
	private boolean forceGzip = false;
	private SystemInfoProvider systemInfoProvider;
	private SessionTokenProvider sessionTokenProvider;
	private SSLSocketFactory socketFactory;

  private static final long serverDisableTimeout = 60000;

   NavajoClient() {
   }

  /**
   * Set the username
   * @param s String
   */
  @Override
  public final void setUsername(String s) {
    username = s;
  }

  /**
   * Set the server URL
   * @param url String
   * USE SET SERVERURLS
   */
  @Override
  public final void setServerUrl(String url) {
	  serverUrls = new String[]{url};
	  setServers(serverUrls);
	  }

  /**
   * Set the password
   * @param pw String
   */
  @Override
  public final void setPassword(String pw) {
    password = pw;
  }

  /**
   * Sets the number of retries the NavajoClient should perform before giving up
   * @param attempts int
   */
  public final void setRetryAttempts(int attempts) {
    retryAttempts = attempts;
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
  @Override
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
  @Override
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
  private final Navajo doSimpleSend(Navajo out, String method, long expirationInterval) throws ClientException {
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
  
  private final String readErrorStream(final HttpURLConnection myCon) {
	  try {
		  if ( myCon != null ) {
			  int respCode = myCon.getResponseCode();
			  InputStream es = myCon.getErrorStream();
			  ByteArrayOutputStream bos = new ByteArrayOutputStream();
			  copyResource(bos, es);
			  bos.close();
//			  String error = new String(bos.toByteArray());
			  logger.info("Responsecode: " + respCode);
			  //logger.info("Responsemessage: " + myCon.getResponseMessage());
			  //logger.info("Got error from server: " + error);
			  // close the errorstream
			  es.close();
			  return "HTTP Status error " + respCode;
		  }
	  } catch (IOException ioe) {
		  logger.error("Error: "+ioe);
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
  
  @SuppressWarnings("resource")
private Navajo doTransaction(String name, Navajo d, boolean useCompression, boolean forcePreparseProxy) throws IOException, NavajoException {
    URL url;
    //useCompression = false;
    
    
    if (useHttps) {
      url = new URL("https://" + name );
    }
    else {
      url = new URL("http://" + name );
    }
    HttpURLConnection con = null;
      con = (HttpURLConnection) url.openConnection();
      if(useHttps) {
    	  HttpsURLConnection httpsCon = (HttpsURLConnection) con;
    	  if(socketFactory!=null) {
    		  httpsCon.setSSLSocketFactory(socketFactory);
    	  }
      }
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
    	// skip it for GAE
    	if(!forceGzip) {
        	chunked.invoke( con, new Object[]{new Integer(1024)});
        	con.setRequestProperty("Transfer-Encoding", "chunked" );
    	}
    } catch (SecurityException e) {
    } catch (Throwable e) {
    	logger.error("Error: ", e);
    	logger.warn("setChunkedStreamingMode does not exist, upgrade to java 1.5+");
    }
    if (useCompression) {
    	if(forceGzip) {
        	con.setRequestProperty("Content-Encoding", "gzip");
        	con.setRequestProperty("Accept-Encoding", "gzip");
    	} else {
        	con.setRequestProperty("Content-Encoding", "jzlib");
        	con.setRequestProperty("Accept-Encoding", "jzlib");
    	}
    	//con.connect();
    	   
    	BufferedWriter out = null;
    	try {
    		if (forceGzip) {
        		out = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(con.getOutputStream()), "UTF-8"));
			} else {
	    		out = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(con.getOutputStream()), "UTF-8"));
			}
    		d.write(out, condensed, d.getHeader().getRPCName());
    	} finally  {
    		if ( out != null ) {
    			try {
    				//out.flush();
    				out.close();
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
    		}
    	}
    } else {
    	//con.connect();
    	con.setRequestProperty("noCompression", "true");
    	BufferedWriter os = null;
    	try {
//    		logger.info("Using no compression!");
    		
      		os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
//    		os.write("apenootjes");
    		
    		
    		d.write(os, condensed, d.getHeader().getRPCName());    	
    	} finally {
    		if ( os != null ) {
    			try {
    				os.flush();
    				os.close();
    			} catch (IOException e) {
    				logger.error("Error: ", e);
    			}
    		}
    	}
    }

//    String contentEncoding = con.getContentEncoding();
    //logger.info("Content encoding: "+contentEncoding);

    
    // Check for errors.
    InputStream in = null;
    Navajo n = null;
    try {
    	if ( con.getResponseCode() >= 400 ) {
    		throw new IOException(readErrorStream(con));
    	} else {
    		if ( useCompression ) {
    			if (forceGzip) {
        			in = new GZIPInputStream(con.getInputStream());
				} else {
	    			in = new InflaterInputStream(con.getInputStream());
				}
    			
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

  public boolean useHttps() {
	return useHttps;
}

public void setHttps(boolean useHttps) {
	this.useHttps = useHttps;
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
  @Override
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
    	logger.error("Error: ", ex);
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
  @Override
  public final Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval, boolean useCompression, boolean allowPreparseProxy) throws ClientException {
    // NOTE: prefix persistence key with method, because same Navajo object could be used as a request
    // for multiple methods!

    // ============ compared services ===================

	  /**
	   * Make sure that same Navajo is not used simultaneously.
	   */
	  synchronized (out) {

		  //====================================================


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
		  if ( header.getRequestId() != null && header.getRequestId().equals("42") ) {
			  System.err.println("ENCOUNTERED TEST!!!");
		  } else {
			  header.setRequestId( Guid.create() );
		  }
		  String sessionToken = getSessionTokenProvider().getSessionToken();
		header.setHeaderAttribute("clientToken", sessionToken);
		  header.setHeaderAttribute("clientInfo", getSystemInfoProvider().toString());
		  // ========= Adding globalMessages

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
//					  logger.info("SENT TO SERVER: ");
//					  out.write(System.err);

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

						  //logger.info("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getHeaderAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
						  if(callingService!=null) {
							  n.getHeader().setHeaderAttribute("sourceScript", callingService);							  
						  }
					  } else {
						  n = n2;
					  }
				  }
				  catch (IOException uhe) {
					  if ( retryAttempts <= 0 ) {
						  throw uhe;
					  }
					  logger.info("Generic IOException: "+uhe.getMessage()+". Retrying without compression...");
					  Navajo n2 = NavajoFactory.getInstance().createNavajo();
					  n = retryTransaction(server, out, false, allowPreparseProxy, retryAttempts, retryInterval, n2); // lees uit resourc
					  if (n != null) {
						  //logger.info("METHOD: "+method+" sourcehead: "+callingService+" sourceSource: "+out.getHeader().getHeaderAttribute("sourceScript")+" outRPCName: "+n.getHeader().getRPCName());
						  if(callingService!=null) {
							  n.getHeader().setHeaderAttribute("sourceScript", callingService);
						  }
					  } else {
						  n = n2;
					  }
				  } catch(Throwable r) {
					  logger.error("Error: ", r);
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
						  //                 logger.info(method+": totaltime = " + ( clientTime / 1000.0 )+ ", servertime = " + ( totalTime / 1000.0 )+" transfertime = "+((clientTime-totalTime)/1000)+" piggybackdata: "+piggyBackData.size()); 
					  } else {
						  logger.info("Null header in input message");
					  }
				  }
				  return n;
			  }
			  else {
				  throw new ClientException( -1, -1, "Unknown protocol: " + protocol);
			  }
		  }
		  catch (Exception e) {
			  logger.error("Error: ", e);
			  throw new ClientException( -1, -1, e.getMessage());
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
    logger.info("------------> retrying transaction: " + server + ", attempts left: " + attemptsLeft+" total retries: "+globalRetryCounter);
    
    int pastAttempts = retryAttempts-attemptsLeft;
    logger.info("Past retries: "+pastAttempts);
    // only switch if there is more than one server
    if (pastAttempts>=(switchServerAfterRetries-1) && serverUrls.length>1) {
    	logger.info("Did: "+pastAttempts+" retries. Switching server");
    	disabledServers.put(getCurrentHost(), new Long(System.currentTimeMillis()));
    	logger.info("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    	switchServer(true);
    	server = getCurrentHost();
	}
    
    try {
    	try {
    		Thread.sleep(interval);
    	} catch (InterruptedException e) {
    		logger.error("Error: ", e);
    	}
    	Navajo doc = doTransaction(server, out, useCompression, allowPreparseProxy);
    	logger.info("It worked!  the inputstream is: " + in);
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
    		logger.info("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    		switchServer(true);
    		generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
    	}
    	else {
    		return retryTransaction(server, out, useCompression, allowPreparseProxy, attemptsLeft, interval, n);
    	}
    }
    catch (IOException uhe) {
    	logger.info(uhe.getMessage());
    	if (attemptsLeft <= 0) {
    		disabledServers.put(getCurrentHost(), new Long(System.currentTimeMillis()));
    		logger.info("Disabled server: "+getCurrentHost()+" for "+serverDisableTimeout+" millis." );
    		switchServer(true);
    		generateConnectionError(n, 4444, "Could not connect to server (network problem?) " + uhe.getMessage());
    	}
    	else {
    		attemptsLeft--;
    		return retryTransaction(server, out, false, allowPreparseProxy, attemptsLeft, interval, n);
    	}
    }
    return null;
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
  @Override
  public final Navajo doSimpleSend(String method) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method);
  }

  @Override
public void setServers(String[] servers) {
	serverUrls = servers;
	serverLoads = new double[serverUrls.length];
	if (servers.length>0) {
		currentServerIndex = randomize.nextInt(servers.length);
	}
//	logger.info("servers = " + servers[0] + ", loadBalancingMode = " + loadBalancingMode);
//	if ( loadBalancingMode != LBMODE_MANUAL ) {
//		determineServerLoadsAndSetCurrentServer(true);
//		ping();
//	}
}

  @Override
public String getCurrentHost() {
	if (serverUrls!=null && serverUrls.length>0) {
		return serverUrls[currentServerIndex];
	}
	return null;
}

  @Override
  /**
   * I think only used in testing
   */
public void setCurrentHost(String host) {
	for (int i = 0; i < serverUrls.length; i++) {
		if ( serverUrls[i].equals(host) ) {
			currentServerIndex = i;
			logger.info("SET CURRENT SERVER TO: " + host + "(" + currentServerIndex + ")");
			break;
		}
	}
}

/**
 * Switch the current server.
 * If force flag is set. The server with the minimum load is ALWAYS set as the current server.
 * For LBMODE_MANUAL and LBMODE_STATIC_MINLOAD the server is ONLY switched if force flag is set.
 * For LBMODE_DYNAMIC_MINLOAD the server is ALWAYS switched.
 * @param force
 */
private final void switchServer(boolean force) {
	
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
	
	logger.info("currentServer = " + serverUrls[currentServerIndex] + " with load: " + serverLoads[currentServerIndex]);
	
}

	@Override
	public void setLocaleCode(String locale) {
		this.localeCode  = locale;
	}

	@Override
	public String getLocaleCode() {
		return this.localeCode;
	}

	@Override
	public void setSubLocaleCode(String locale) {
		this.subLocale = locale;
	}

	@Override
	public String getSubLocaleCode() {
		return this.subLocale;
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

	@Override
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

	@Override
	public void setAllowCompression(boolean allowCompression) {
		this.allowCompression = allowCompression;
	}

	@Override
	public void setForceGzip(boolean forceGzip) {
		this.forceGzip = forceGzip;
	}

	@Override
	public SystemInfoProvider getSystemInfoProvider() {
		if(this.systemInfoProvider==null) {
			return SystemInfoFactory.getSystemInfo();
		}
		return systemInfoProvider;
	}
	
	@Override
	public void setSystemInfoProvider(SystemInfoProvider sip) {
		this.systemInfoProvider = sip;
	}	
	
	@Override
	public SessionTokenProvider getSessionTokenProvider() {
		if(sessionTokenProvider==null) {
			return SessionTokenFactory.getSessionTokenProvider();
		}
		return this.sessionTokenProvider;
	}
	
	@Override
	public void setSessionTokenProvider(SessionTokenProvider stp) {
		this.sessionTokenProvider = stp;
	}

	/**
	 * set the SSL socket factory to use whenever an HTTPS call is made.
	 * @param algorithm, the algorithm to use, for example: SunX509
	 * @param type Type of the keystore, for example PKCS12 or JKS
	 * @param source InputStream of the client certificate, supply null to reset the socketfactory to default
	 * @param password the keystore password
	 */
	@Override
	public void setClientCertificate(String algorithm, String keyStoreType, InputStream source,
			char[] password) throws IOException {
		if(source==null) {
			this.socketFactory = null;
			return;
		}
		SSLContext context;
		try {
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(algorithm);
			  KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			  try {
				keyStore.load(source, password);
			} finally {
				source.close();
			}
			keyManagerFactory.init(keyStore, password);
			context = SSLContext.getInstance("TLS");
			context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
			this.socketFactory = context.getSocketFactory();
			return;
		} catch (UnrecoverableKeyException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (KeyManagementException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (NoSuchAlgorithmException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (KeyStoreException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (CertificateException e) {
			throw new IOException("Error loading certificate: ",e);
		}
	}


}