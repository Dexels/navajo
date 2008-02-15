package com.dexels.navajo.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

class CacheEntry {

	public String serviceName;
	public String serviceKey;
	public String persistenceKey;
	public long expirationInterval;
	public boolean userSpecific;
	
	public CacheEntry(String service, String serviceKey, String persistenceKey, long interval, boolean userSpecific) {
		this.serviceName = service;
		this.serviceKey = serviceKey;
		this.persistenceKey = persistenceKey;
		this.expirationInterval = interval;
		this.userSpecific = userSpecific;
		AuditLog.log("CACHE", "CACHE EENTRY: serviceName="+serviceName+",serviceKey="+serviceKey+",persistenceKey="+persistenceKey+",expirationInterval="+expirationInterval);
	}
}

public class CacheController extends GenericThread implements CacheControllerMXBean  {

	private static String id = "Cache Controller";
	public static final String VERSION = "$Id$";
	private final static String CACHE_CONFIG = "cache.xml";
	
	private volatile static CacheController instance = null;
	private static Object semaphore = new Object();
	
	private volatile static HashMap<String, CacheEntry> expirations = new HashMap<String,CacheEntry>();
	
	private long configTimestamp = -1;
	
	
	public CacheController() {
		super(id);
		try {
			readConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static CacheController getInstance() {

		if (instance!=null) {
			return instance;
		}

		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			}

			instance = new CacheController();	
			
			try {
				JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			} 
			
			instance.myId = id;
			instance.setSleepTime(5000);
			instance.startThread(instance);

			AuditLog.log("Cache Controller", "Started Cache Controller Reader");
			return instance;
		}
	}

	private long getConfigTimeStamp() {
		if (  Dispatcher.getInstance() != null && Dispatcher.getInstance().getNavajoConfig() != null ) {
			java.io.File f = new java.io.File(Dispatcher.getInstance().getNavajoConfig().getConfigPath() + "/" + CACHE_CONFIG);
			if ( f != null && f.exists() ) {
				return f.lastModified();
			}
		}
		return -1;
	}
	
	private void setConfigTimeStamp() {
		configTimestamp = getConfigTimeStamp();
	}
	
	private boolean isConfigModified() {
		if ( configTimestamp != getConfigTimeStamp() ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * <message name="Cache">
	 *   <message name="Entries" type="array">
	 *      <message name="Entries"> 
	 *          <property name="Webservice" value="competition/ProcessQueryClub"/>
	 *          <property name="Timeout" value="60"/>
	 *      </message>
	 *   </message>
	 * <message>
	 */
	private void readConfig() throws Exception {


		Navajo config = Dispatcher.getInstance().getNavajoConfig().readConfig(CACHE_CONFIG);

		if ( config != null ) {
			ArrayList<Message> messages = config.getMessages("Cache/Entries");
			if ( messages != null ) {
				expirations.clear();
				for (int i = 0; i < messages.size(); i++) {
					String webservice = messages.get(i).getProperty("Webservice").getValue();
					Long expir = new Long( messages.get(i).getProperty("Timeout").getValue() );
					boolean userSpecific = (  messages.get(i).getProperty("UserCache") != null ? 
							messages.get(i).getProperty("UserCache").getValue().equals("true") : false );
					String serviceKeys = ( messages.get(i).getProperty("CacheKeys") != null ? messages.get(i).getProperty("CacheKeys").getValue() : null );
					String persistenceKeys = ( messages.get(i).getProperty("PersistenceKeys") != null ? messages.get(i).getProperty("PersistenceKeys").getValue() : null );
					CacheEntry ce = new CacheEntry(webservice, serviceKeys, persistenceKeys, expir.longValue(), userSpecific );
					expirations.put(webservice, ce);
				}
			}
		}
		
		setConfigTimeStamp();
	}
	
	private String constructPersistenceKey(Navajo in, String service) {
		
		StringBuffer result = new StringBuffer();
		
		String [] properties = expirations.get(service).persistenceKey.split(";");
		
		for ( int i = 0; i < properties.length; i++ ) {
			Property p = in.getProperty( properties[i] );
			if ( p != null ) {
				result.append(p.getValue());
			}
		}
		
		String r = result.toString();
		
		if ( r.equals("") ) {
			return null;
		} else {
			return r;
		}
	}
	
	public final String getCacheKey(final String user, final String service, final Navajo in) {
		
		String persistenceKey = null;
		
		if ( expirations.containsKey(service)  ) {
			
			if ( expirations.get(service).persistenceKey != null ) {
				persistenceKey = constructPersistenceKey(in, service);
			}
			
			if ( persistenceKey  == null ) {
				persistenceKey = in.persistenceKey();
			}
			  
			
			if ( expirations.get(service).userSpecific ) {
				return service.replace('/', '.') + "_" + user + "_" + persistenceKey;
			} else {
				return service.replace('/', '.') + "_" + persistenceKey;
			}
		} else {
			return "-1";
		}
		
	}
	
	public final long getExpirationInterval(String service) {
		if ( expirations.containsKey(service )  ) {
			return expirations.get(service).expirationInterval;
		} else {
			return -1;
		}
	}
	
	public final String getServiceKeys(String service) {
		if ( expirations.containsKey(service )  ) {
			return expirations.get(service).serviceKey;
		} else {
			return null;
		}
	}

	public void worker() 
	{ 
		if ( isConfigModified() ) {
			try {
				readConfig();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	public int cachedEntries() {
		return expirations.size();
	}

	
}
