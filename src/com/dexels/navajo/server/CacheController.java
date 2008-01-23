package com.dexels.navajo.server;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.NavajoRequestEvent;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class CacheController extends GenericThread implements NavajoListener {

	private static String id = "Cache Controller";
	public static final String VERSION = "$Id$";
	private final static String CACHE_CONFIG = "cache.xml";
	
	private volatile static CacheController instance = null;
	private static Object semaphore = new Object();
	
	private long configTimestamp = -1;
	
	
	public CacheController() {
		super(id);
		readConfig();
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
	
	private void readConfig() {
		/**
		 * <message name="Cache">
		 *   <message name="Cache">
		 *      <property name="User" value="*"/>
		 *      <property name="Webservice" value="club/ProcessQueryClub"/>
		 *      <property name="Timeout" value="5000"/>
		 *   </message>
		 * </message>
		 */
	}
	
	public void worker() 
	{ 
		// implement this. 
	}

	public void invoke(NavajoEvent ne) {
		if ( ne instanceof NavajoRequestEvent ) {
			// Do stuff.
		}
	}
}
