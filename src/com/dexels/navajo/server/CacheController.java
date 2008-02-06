package com.dexels.navajo.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class CacheController extends GenericThread  {

	private static String id = "Cache Controller";
	public static final String VERSION = "$Id$";
	private final static String CACHE_CONFIG = "cache.xml";
	
	private volatile static CacheController instance = null;
	private static Object semaphore = new Object();
	
	private volatile static HashMap<String, Long> expirations = new HashMap<String,Long>();
	
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
			ArrayList<Message> messages = config.getMessages("Cache/Entries");
			if ( messages != null ) {
				expirations.clear();
				for (int i = 0; i < messages.size(); i++) {
					String webservice = messages.get(i).getProperty("Webservice").getValue();
					Long expir = new Long( messages.get(i).getProperty("Timeout").getValue() );
					System.err.println("PUTTING " + webservice + " IN CACHECONTROL WITH TIMEOUT " + expir);
					expirations.put(webservice, expir);
				}
			}
			setConfigTimeStamp();
		
	}
	
	public final long getExpirationInterval(String service) {
		if ( expirations.containsKey(service )  ) {
			return expirations.get(service).longValue();
		} else {
			return -1;
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
}
