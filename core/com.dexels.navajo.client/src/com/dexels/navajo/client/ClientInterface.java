package com.dexels.navajo.client;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;
import com.dexels.navajo.client.systeminfo.SystemInfoProvider;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.types.Binary;

/**
 * See NavajoClient for an explanation of all methods
 */
public interface ClientInterface {

  public static final String GLOBALSNAME = "__globals__";
  public static final String GLOBALSPREFIX = "navajo.globals.";
  
  public final static int LBMODE_MANUAL = 0;
  public final static int LBMODE_DYNAMIC_MINLOAD = 1;
  public final static int LBMODE_STATIC_MINLOAD = 2;

  public String getClientName();

//  public URLConnection createUrlConnection(URL url) throws IOException;

//  public void addComparedServices(String serviceQuery, String serviceUpdate);

  public Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval) throws ClientException;

  public Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval, boolean useCompression, boolean allowPreparseProxy) throws ClientException;

  public Navajo doSimpleSend(Navajo out, String method) throws ClientException;
  
  public Navajo doSimpleSend(Navajo out, String method, long expirationInterval) throws ClientException;

  public Message doSimpleSend(Navajo out, String method, String messagePath) throws ClientException;


  public Navajo doSimpleSend(String method) throws ClientException;

  public Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v, long expirationInterval) throws ClientException;

  public Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v) throws ClientException;

  public Navajo doScheduledSend(Navajo out, String method, String schedule, String description, String clientId) throws ClientException;

  public void init(String rootPath, String serverXmlPath) throws ClientException;

  public void setLoadBalancingMode(int i);
  
  public int getLoadBalancingMode();
  
  public String getUsername();

  public String getPassword();

  public String getServerUrl();

  public void setUsername(String s);

  public void setPassword(String pw);

  public void setServerUrl(String url);

   
  public void setRetryAttempts(int noOfAttempts);

  public void setRetryInterval(long interval);



  public int getQueueSize();

  public int getActiveThreads();




public void destroy();

public void setServers(String[] servers);

public Binary getArrayMessageReport(Message m, String[] propertyNames,String[] propertyTitles, int[] columnWidths, String format) throws NavajoException;
public Binary getArrayMessageReport(Message m, String[] propertyNames, String[] propertyTitles, int[] columnWidths, String format, String orientation, int[] margins) throws NavajoException;

	public SystemInfoProvider getSystemInfoProvider();
	public void setSystemInfoProvider(SystemInfoProvider sip);
	public SessionTokenProvider getSessionTokenProvider();
	public void setSessionTokenProvider(SessionTokenProvider stp);


public void setKeepAlive(int millis) throws ClientException;

/*
 * Do simple send to a specific server
 */
public Navajo doSpecificSend(Navajo out, String method, int serverIndex)  throws ClientException;


/*
 * sets the locale for the client, it will be appended to the header
 */
public void setLocaleCode(String locale);
public String getLocaleCode();

public void setSubLocaleCode(String locale);

public String getCurrentHost();

public void setCurrentHost(String host);

public void setAllowCompression(boolean allowCompression);

/**
 * Created to force the client to encode the request using Gzip (GAE related)
 * @param forceGzip
 */
public void setForceGzip(boolean forceGzip);

}
