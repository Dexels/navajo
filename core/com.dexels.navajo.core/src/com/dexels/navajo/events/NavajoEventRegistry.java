package com.dexels.navajo.events;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationListener;

import navajocore.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.events.types.ChangeNotificationEvent;
import com.dexels.navajo.events.types.LevelEvent;
import com.dexels.navajo.server.enterprise.scheduler.tribe.NavajoEventProxyInterface;
import com.dexels.navajo.server.jmx.JMXHelper;

/**
 * A very simple event registry class.
 * 
 * @author arjen
 *
 */
public class NavajoEventRegistry extends NotificationBroadcasterSupport implements NavajoEventRegistryMXBean, NotificationListener {

	private volatile static NavajoEventRegistry instance = null;
	
	private final Map<Class<? extends NavajoEvent>, Set<NavajoListener>> registry = 
															new ConcurrentHashMap<Class<? extends NavajoEvent>, Set<NavajoListener>>();
	
	/**
	 * Map contains JMX monitored eventtypes.
	 */
	private final Set<String> monitoredEvents = Collections.newSetFromMap( new ConcurrentHashMap<String,Boolean>() );
	private final Map<String,HashSet<String>> monitorLeveledEvents = new ConcurrentHashMap<String,HashSet<String>>();
	
	private final static Object semaphore = new Object();
	public static long notificationSequence = 0;
	
	private final static String id = "Navajo Event Registry";
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoEventRegistry.class);
	
	public static void clearInstance() {
		instance = null;
	}
	
	public void shutdown() {
		monitoredEvents.clear();
		monitorLeveledEvents.clear();
		clearInstance();
	}
	
	/**
	 * Return a (singleton) instance of the NavajoEventRegistry.
	 * 
	 * @return
	 */
	public static NavajoEventRegistry getInstance() {
		if ( instance != null ) {
			return instance;
		} else {
			
			if ( !Version.osgiActive() ) {
				synchronized (semaphore ) {

					if ( instance == null ) {
						instance = new NavajoEventRegistry();
						try {
							JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
						} catch (Throwable t) {
							//t.printStackTrace(System.err);
						} 
					}
				}
			} else {
				logger.warn("No NavajoEventRegistry instance found");
			}
			return instance;
		}
	}
	
	/**
	 * Express an interest in a certain type of event. The interested party should implement the NavajoListener interface
	 * 
	 * @param type
	 * @param l
	 */
	public void addListener(Class<? extends NavajoEvent> type, NavajoListener l) {
		
		synchronized (semaphore) {
			
			Set<NavajoListener> registered = registry.get(type);
			if ( registered == null ) {
				registered = Collections.newSetFromMap( new ConcurrentHashMap<NavajoListener,Boolean>() );
				registry.put(type, registered);
			}
			registered.add(l);
		}
	}
	
	/**
	 * Deregister a previously registered interest in an event.
	 * 
	 * @param type
	 * @param l
	 */
	public void removeListener(Class<? extends NavajoEvent> type, NavajoListener l) {
		synchronized (semaphore) {
			Set<NavajoListener> registered = registry.get(type);
			registered.remove(l);
		}
	}

	private void publishMonitoredEvent(final NavajoEvent ne) {

		if ( ne instanceof ChangeNotificationEvent ) {
			ChangeNotificationEvent cne = (ChangeNotificationEvent) ne;
			sendNotification(cne.getJMXNotification());
		}

		boolean notificationsend = false;
		
		if ( ne instanceof LevelEvent ) {

			HashSet<String> levelEvents = monitorLeveledEvents.get( ((LevelEvent) ne).getLevel().getName() );
			if ( levelEvents != null && levelEvents.contains( ne.getClass().getName() )) {
				
				Notification n = 
					new Notification(ne.getClass().getName(), "Navajo Event Registry", notificationSequence++,
							System.currentTimeMillis(), ne.toString());
				notificationsend = true;
				sendNotification(n); 
			}
			
		} 
		
		if ( !notificationsend && monitoredEvents.contains( ne.getClass().getName() )) {

			Notification n = 
				new Notification(ne.getClass().getName(), "Navajo Event Registry", notificationSequence++,
						System.currentTimeMillis(), ne.toString());

			sendNotification(n); 
		}
		
	}
	
	/**
	 * Publish an event and handle the listeners asynchronously
	 * 
	 * @param ne
	 * @param ignoreProxyListeners, if set to true, listeners of class NavajoEventProxy are ignored to prevent event ping-pong.
	 * 
	 */
	public void publishAsynchronousEvent(final NavajoEvent ne, boolean ignoreProxyListeners) {
		// TODO ignoreProxyListeners is actually not used. Is that correct?
		//logger.info("Asynchronous Event Triggered: " + ne.getClass());
		publishMonitoredEvent(ne);
		
		Set<NavajoListener> copy = getInterestedParties(ne);
		if ( copy != null ) {
			Iterator<NavajoListener> i = copy.iterator();
			while ( i.hasNext() ) {
				final NavajoListener nl = i.next();
				try {
					new Thread() {
						public void run() {
							nl.onNavajoEvent(ne);
						}
					}.start();
				} catch (Throwable t) {
					logger.error("Error: ", t);
				}
			}
		}
	}

	/**
	 * Publish an event and handle the listeners asynchronously
	 * 
	 * @param ne
	 */
	public void publishAsynchronousEvent(final NavajoEvent ne) {
		publishAsynchronousEvent(ne, false);
	}
	
	/**
	 * Publish an event and handle the listeners synchronously
	 * 
	 * @param ne
	 * @param ignoreProxyListeners, if set to true, listeners of class NavajoEventProxy are ignored to prevent event ping-pong.
	 */
	public void publishEvent(NavajoEvent ne, boolean ignoreProxyListeners) {

		//logger.info("Synchronous Event Triggered: " + ne.getClass());
		publishMonitoredEvent(ne);
		
		Set<NavajoListener> copy = getInterestedParties(ne);
		if ( copy != null )  {
			Iterator<NavajoListener> i = copy.iterator();
			while ( i.hasNext() ) {
				try {
					NavajoListener nl = i.next();
					if ( ignoreProxyListeners && ( nl instanceof NavajoEventProxyInterface )) {
						
					} else {
						nl.onNavajoEvent(ne);
					}
				} catch (Throwable t) {
					logger.error("Error: ", t);
				}
			}
		}
	}
	
	/**
	 * Publish an event and handle the listeners synchronously
	 * 
	 * @param ne
	 */
	public void publishEvent(NavajoEvent ne) {
		publishEvent(ne, false);
	}
	
	private final Set<NavajoListener> getInterestedParties(NavajoEvent ne) {		
		return registry.get(ne.getClass());
	}

	/**
	 * Checks whether a certain event is monitored.
	 * @param type, the NavajoEvent class to which NavajoListener instances are attached.
	 * @param ignoreEventProxy, if set to true NavajoEventProxy instances are ignored as 'real' listeners.
	 * @return
	 */
	public boolean isMonitoredEvent(Class<? extends NavajoEvent> type, boolean ignoreEventProxy) {
		Iterator<Class<? extends NavajoEvent>> iter = registry.keySet().iterator();
		while ( iter.hasNext() ) {
			Class<? extends NavajoEvent> s = iter.next();
			if ( s.equals(type) ) {
				Set<NavajoListener> listeners = registry.get(s);
				Iterator<NavajoListener> all = listeners.iterator();
				while ( all.hasNext() ) {
					NavajoListener nl = all.next();
					if ( !ignoreEventProxy || !( nl instanceof NavajoEventProxyInterface ) ) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void addMonitoredEvent(String type) {
		monitoredEvents.add(type);
	}
	
	public void addMonitoredEvent(String type, String level) {
		synchronized ( monitorLeveledEvents ) {
			HashSet<String> levelSet = monitorLeveledEvents.get(level);
			if ( levelSet == null ) {
				levelSet = new HashSet<String>();
				monitorLeveledEvents.put(level, levelSet);
			}
			levelSet.add(type);
		}
	}

	public void removeMonitoredEvent(String type) {
		monitoredEvents.remove(type);
	}

	public String getMonitoredEvents() {
		return monitoredEvents.toString();
	}

	/** 
	 * Handles a JMX notification.
	 */
	public void handleNotification(Notification notification, Object handback) {

		logger.info(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getType() );
		logger.info(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getMessage() );
		logger.info(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getSequenceNumber() );
		logger.info(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getTimeStamp() );
		logger.info(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getSource() );		

	}

	public MBeanNotificationInfo[] getNotificationInfo() { 
		String[] types = new String[] { 
				AttributeChangeNotification.ATTRIBUTE_CHANGE
		}; 
		String name = AttributeChangeNotification.class.getName(); 
		String description = "An attribute of this MBean has changed"; 
		MBeanNotificationInfo info = 
			new MBeanNotificationInfo(types, name, description); 
		
		return new MBeanNotificationInfo[] {info}; 
	} 
	 
	public int getNumberOfRegisteredListeners() {
		return registry.size();
	}
	
	public void activate() {
		instance = this;
		try {
			JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
		} catch (Throwable e) {
			logger.error("Caught Error: ", e);
		}
	}
	
	public void deactivate() {
		shutdown();
	}
	

}
