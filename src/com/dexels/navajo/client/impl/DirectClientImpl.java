package com.dexels.navajo.client.impl;

import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.loader.NavajoBasicClassLoader;
import com.dexels.navajo.server.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
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
public class DirectClientImpl
    implements ClientInterface {
//  private ThreadGroup myThreadGroup = new ThreadGroup("navajothreads");
  private NavajoAsyncRunner myRunner = null;
  public DirectClientImpl() {
  	this(false);
  }

  public DirectClientImpl(boolean suppressRunner) {
  	if (!suppressRunner) {
  	  	myRunner = new NavajoAsyncRunner(this);
  	    myRunner.start();
	}
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
  }

  private final Map propertyMap = new HashMap();
  private Dispatcher dispatcher;
  private ErrorResponder myErrorResponder;
  private final ArrayList myActivityListeners = new ArrayList();
  private final Map cachedServicesNameMap = new HashMap();
  private final Map serviceCache = new HashMap();
  private final Map globalMessages = new HashMap();
private String password;
private String username;


private final String mySessionToken;

//   public DirectNavajoClient(String configurationPath) throws NavajoException {
//     dispatcher = new Dispatcher(configurationPath);
//   }

  public void finalizeAsyncRunners(){
   // hiep hoi..
  }

  public void setUseAuthorization(boolean b) {
  	dispatcher.setUseAuthorisation(b);
  }
  
  public synchronized int getPending() {
    return myRunner.getPending();
  }

  public String getClientName() {
    return "direct";
  }

  public void setRetryInterval(long l) {
    // Not applicable.
  }
  
  public String getSessionToken() {
	  return mySessionToken;
  }
  public void setRetryAttempts(int i) {
    // Not applicable.
  }

  public void addCachedService(String service){
    cachedServicesNameMap.put(service, service);
  }

  public void removeCachedService(String service){
    cachedServicesNameMap.remove(service);
    serviceCache.remove(service);
  }

  public final void clearCache() {
    serviceCache.clear();
 }

 public final void clearCache(String service) {
    serviceCache.remove(service);
 }

  public final Navajo doSimpleSend(Navajo out, String server, String method,
                             String user, String password,
                             long expirationInterval) throws ClientException {
    return doSimpleSend(out, server, method, user, password, expirationInterval, false);
  }

  public final Navajo doSimpleSend(Navajo out, String server, String method, String user, String password, long expirationInterval, boolean useCompression) throws ClientException {
    fireActivityChanged(true, method);
    String cacheKey = out.persistenceKey();
    Navajo reply = (Navajo)serviceCache.get(cacheKey);
    Header outHeader = out.getHeader();
    if(reply != null && cachedServicesNameMap.get(method) != null){
        System.err.println("Returning cached WS from DirectClient");
        return reply;
      }
    try {
    	if (out.getHeader()==null) {
    	      Header header = NavajoFactory.getInstance().createHeader(out, method,
    	              user, password, expirationInterval );
    	          out.addHeader(header);
			
		} else {
//			System.err.println("Header present.");
//			try {
//				out.getHeader().write(System.err);
//			} catch (NavajoException e) {
//				e.printStackTrace();
//			}
		}
    	
    	
      NavajoConfig navajoConfig = Dispatcher.getInstance().getNavajoConfig();

// ADDED THIS STUFF:
//      System.err.println("USER: "+user);
//      System.err.println("User dir: "+System.getProperty("user.dir"));
      if (navajoConfig!=null) {
//      	System.err.println("Rootpath: "+navajoConfig.getRootPath());
      	Repository rep = navajoConfig.getRepository();
		System.err.println("Initialized repository for method: "+method+" repost. "+rep.getClass());

      	try {
			rep.initGlobals(method,user,out,null);
             this.setDocumentGlobals(out);
//      	if (rep!=null) {
//          	try {
//        		Access access = rep.authorizeUser(getUsername(), getPassword(), method, out,null);
//        	} catch (SystemException e1) {
//        		e1.printStackTrace();
//        	} catch (AuthorizationException e1) {
//        		e1.printStackTrace();
//        	}
//		}
// END OF ADD
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new ClientException(-11,1111,"Repository error. I think.");
		}
	}


    // ========= Adding globalMessages
    Iterator entries = globalMessages.entrySet().iterator();
    while(entries.hasNext()){
    	System.err.println("globalmessage");
      Map.Entry entry = (Map.Entry)entries.next();
      Message global = (Message)entry.getValue();
      try{
        out.addMessage(global);
      }catch(Exception e){
         e.printStackTrace();
         System.err.println("Could not add globals, proceeding");
      }
    }
      reply = dispatcher.handle(out);
      if (myErrorResponder != null) {
        myErrorResponder.check(reply);
      }
    }
    catch (FatalException ex) {
      ex.printStackTrace();
      fireActivityChanged(false, method);
      return null;
    }
    fireActivityChanged(false, method);
    if(cachedServicesNameMap.get(method) != null){
      serviceCache.put(cacheKey, reply);
    }
//    String source = outHeader.getRPCName();
//    System.err.println("HEADER OF INCOMING NAVAJO: ");
//    outHeader.write(System.err);
//    if (source==null) {
//    	source = method;
//	}
//    reply.getHeader().setAttribute("sourceScript",source);		
//    System.err.println("-------PRINTING REPLY HEADER --------------");
//    reply.getHeader().write(System.err);
//    System.err.println("-------END OF REPLY HEADER ----------------");
    return reply;
  }

  public void setDocumentGlobals(final Navajo doc) throws ClientException {
//      System.err.println("Setting doc. globals.");
    try {
    	Message msg = doc.getMessage(GLOBALSNAME);
    	
      Message paramMsg = null;
      if (msg!=null) {
    	  System.err.println("Found previous...");
		paramMsg = msg;
		System.err.println("Size: "+msg.getArraySize());
	} else {
		paramMsg = NavajoFactory.getInstance().createMessage(
		          doc, GLOBALSNAME);
	     doc.addMessage(paramMsg);
	}
    	  
    	  
    	  
       final Properties props = System.getProperties();
      final Set keys = props.keySet();
      final Iterator iter = keys.iterator();
      while (iter.hasNext()) {
        final String name = (String) iter.next();
//        System.err.println("Current global: "+name);
        if (name.startsWith(GLOBALSPREFIX)) {
          final String propName = name.substring(GLOBALSPREFIX.length());
          final String value = (String) props.get(name);
          final Property p = NavajoFactory.getInstance().createProperty(doc,
              propName, Property.STRING_PROPERTY,
              value, value.length(), "",
              Property.DIR_IN);
          paramMsg.addProperty(p);
        }
      }
    }
    catch (NavajoException ex) {
      throw new ClientException(99, 97, ex.toString());
    }

  }

  public void setSecure(String keystore, String storepass, boolean useSecurity) {
    // Well waddujaknow,..
  }

  public void setSecure(InputStream keystore, String storepass,
                        boolean useSecurity) {
    // Well waddujaknow,..
  }

  public void setClientProperty(String key, Object value) {
    propertyMap.put(key, value);
  }

  public Object getClientProperty(String key) {
    return propertyMap.get(key);
  }

  public void addActivityListener(ActivityListener al) {
    myActivityListeners.add(al);
  }

  public void removeActivityListener(ActivityListener al) {
    myActivityListeners.remove(al);
  }

  protected void fireActivityChanged(boolean b, String service) {
    for (int i = 0; i < myActivityListeners.size(); i++) {
      ActivityListener current = (ActivityListener) myActivityListeners.get(i);
      current.setWaiting(b, service, getActiveThreads(),getQueueSize(), 0);
    }
  }

  public final Navajo doSimpleSend(Navajo n, String service, long expirationInterval) throws ClientException {
    return doSimpleSend(n, "", service, getUsername(), getPassword(), expirationInterval, false);
  }


  public final Navajo doSimpleSend(Navajo n, String service) throws ClientException {
    return doSimpleSend(n, "", service, getUsername(), getPassword(), -1, false);
  }

  public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v) throws
      ClientException {
    Navajo result = doSimpleSend(n, method);
    checkValidation(result, v);
    return result;
  }

  public Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v, long expirationInterval) throws ClientException {
  Navajo result = doSimpleSend(n, method,expirationInterval);
  checkValidation(result, v);
  return result;
}


  private void checkValidation(Navajo result, ConditionErrorHandler v) {
    if(result != null){
      Message conditionErrors = result.getMessage("ConditionErrors");
      if (conditionErrors != null && v != null) {
        v.checkValidation(conditionErrors);
      }
    }
  }

  public Message doSimpleSend(Navajo n, String service, String messagePath) throws
      ClientException {
    return doSimpleSend(n, service).getMessage(messagePath);
  }

  public void init(URL config) throws ClientException {
    try {

      dispatcher = Dispatcher.getInstance( config,
                                  new com.dexels.navajo.server.
                                  ClassloaderInputStreamReader(), null);
      dispatcher.setUseAuthorisation(false);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
      throw new ClientException(1, 1, ex.getMessage());
    }
  }
  
  // Ummm, it is ignoring the cl parameter. Is that 'meant to be'?
  
  public void init(URL config, ClassLoader cl) throws ClientException {
      init(config,cl,  System.getProperty("user.dir"));
  }
  
  public void init(URL config, ClassLoader cl, String path) throws ClientException {
    try {

//    NavajoBasicClassLoader nbcl = new NavajoBasicClassLoader();
      dispatcher = Dispatcher.getInstance(config,new FileInputStreamReader(path),null);
//      dispatcher.setUseAuthorisation(false);
//      System.err.println("IN INIT of DCI. classloader: "+dispatcher.getNavajoConfig().getClassloader());
//      dispatcher.getNavajoConfig().setClassloader(cl);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
      throw new ClientException(1, 1, ex.getMessage());
    }
  }

  public void clearClassCache() {
      if (dispatcher!=null) {
        Dispatcher.getInstance().doClearCache();
    }
  }
  
  public void init(URL config, String path) throws ClientException {
      try {
//          dispatcher = new Dispatcher(config,new FileInputStreamReader(path),new DirectClassLoader(path,path+"/navajo-tester/auxilary/classes"));

        dispatcher = Dispatcher.getInstance(config,new FileInputStreamReader(path),null);
      }
      
      catch (NavajoException ex) {
        ex.printStackTrace();
        throw new ClientException(1, 1, ex.getMessage());
      }
    }

  
  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getServerUrl() {
    return "directclient";
  }
  public Message getGlobalMessage(String name) {
	  return (Message)globalMessages.get(name);
  }

  public void addGlobalMessage(Message m){
    globalMessages.remove(m.getName());
    globalMessages.put(m.getName(), m);
  }

  public boolean removeGlobalMessage(Message m){
    return globalMessages.remove(m.getName()) != null;
  }

  public void setUsername(String s) {
  	username = s;
  }

  public void setServerUrl(String url) {
//    throw new UnsupportedOperationException(
//        "No need to set server url in DirectClient!");
  }

  public void setPassword(String pw) {
  	password = pw;
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          String responseId) throws ClientException {
    myRunner.enqueueRequest(in, method, response, responseId);
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          ConditionErrorHandler v) throws ClientException {
    myRunner.enqueueRequest(in, method, response, v);
  }

  public void doAsyncSend(Navajo in, String method, ResponseListener response,
                          String responseId, ConditionErrorHandler v) throws
      ClientException {
    myRunner.enqueueRequest(in, method, response, responseId, v);
  }

  public LazyMessage doLazySend(Message request, String service,
                                String responseMsgName, int startIndex,
                                int endIndex, int total) {
    throw new UnsupportedOperationException(
        "Lazy message are not supported in the direct implementation!");
  }

  public LazyMessage doLazySend(Navajo request, String service,
                                String responseMsgName, int startIndex,
                                int endIndex, int total) {
    throw new UnsupportedOperationException(
        "Lazy message are not yet supported in the implementation!");
  }

  public LazyMessage doLazySend(Navajo request, String service,
                                String responseMsgName, int startIndex,
                                int endIndex, int total, ConditionErrorHandler v) {
    throw new UnsupportedOperationException(
        "Lazy message are not yet supported in the implementation!");
  }



  public Navajo createLazyNavajo(Navajo request, String service,
                                 String lazyPath, int startIndex, int endIndex) throws
      ClientException {
    throw new UnsupportedOperationException(
        "Lazy message are not supported in the direct implementation!");
  }

  public Navajo performLazyUpdate(Navajo request, int startIndex, int endIndex) throws
      ClientException {
    throw new UnsupportedOperationException(
        "Lazy message are not supported in the direct implementation!");
  }

  public Message doSimpleSend(String method, String messagePath) throws
      ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), messagePath).
        getMessage(messagePath);
  }

  public final Navajo doSimpleSend(String method, long expirationInterval) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method, expirationInterval);
  }

  public final Navajo doSimpleSend(String method) throws ClientException {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method);
  }

  public ErrorResponder getErrorHandler() {
    return myErrorResponder;
  }

  public void setErrorHandler(ErrorResponder e) {
    myErrorResponder = e;
  }

  public void displayException(Exception e) {
    if (myErrorResponder != null) {
      myErrorResponder.check(e);
    }

  }

  public int getActiveThreads(){
    return 1;
  }

  public int getQueueSize(){
    return 0;
  }

  public void doServerAsyncSend(Navajo in, String method, ServerAsyncListener listener, String clientId, int pollingInterval) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method doServerAsyncSend() not yet implemented.");
  }
  public void killServerAsyncSend(String serverId) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method killServerAsyncSend() not yet implemented.");
  }
  public void pauseServerAsyncSend(String serverId) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method pauseServerAsyncSend() not yet implemented.");
  }
  public void resumeServerAsyncSend(String serverId) throws ClientException {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method resumeServerAsyncSend() not yet implemented.");
  }
  public void deRegisterAsyncRunner(String id) {
    /**@todo Implement this com.dexels.navajo.client.ClientInterface method*/
    throw new java.lang.UnsupportedOperationException("Method resumeServerAsyncSend() not yet implemented.");
  }

  public void setCondensed(boolean b) {
      // don't do condensing...
  }
  public void addComparedServices(String serviceQuery, String serviceUpdate){
    throw new java.lang.UnsupportedOperationException("Method addComparedServices(String serviceQuery, String serviceUpdate) not yet implemented.");
  }

public void destroy() {
	// TODO Auto-generated method stub
	
}

public void setServers(String[] servers) {
	// NOT INTERESTED
}

public void addBroadcastListener(BroadcastListener al) {
	// TODO Auto-generated method stub
	
}

public void removeBroadcastListener(BroadcastListener al) {
	// TODO Auto-generated method stub
	
}

public void setKeepAlive(int millis) throws ClientException {
	// TODO Auto-generated method stub
	
}

public Navajo doSpecificSend(Navajo n, String method, int serverIndex) {
	// TODO Auto-generated method stub
	return null;
}

public int getAsyncServerIndex() {
	// TODO Auto-generated method stub
	return 0;
}

public void setLocaleCode(String locale) {
	// TODO Wha'ever
}
}
