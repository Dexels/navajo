package com.dexels.navajo.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationListener;
import javax.management.monitor.MonitorNotification;

import com.dexels.navajo.events.types.TribeMemberDownEvent;
import com.dexels.navajo.scheduler.NavajoEventProxy;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;

/**
 * A very simple event registry class.
 * 
 * @author arjen
 *
 */
public class NavajoEventRegistry extends NotificationBroadcasterSupport implements NavajoEventRegistryMXBean, NotificationListener {

	private volatile static NavajoEventRegistry instance = null;
	
	private final HashMap<Class<? extends NavajoEvent>, HashSet<NavajoListener>> registry = 
															new HashMap<Class<? extends NavajoEvent>, HashSet<NavajoListener>>();
	
	/**
	 * Map contains JMX monitored eventtypes.
	 */
	private final HashSet<String> monitoredEvents = new HashSet<String>();
	
	private static Object semaphore = new Object();
	
	public static void clearInstance() {
		instance = null;
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
			synchronized (semaphore ) {
				
				if ( instance != null ) {
					return instance;
				}
				
				instance = new NavajoEventRegistry();
				try {
					JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, "Navajo Event Registry");
				} catch (Throwable t) {
					//t.printStackTrace(System.err);
				} 
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
			
			HashSet<NavajoListener> registered = registry.get(type);
			if ( registered == null ) {
				registered = new HashSet<NavajoListener>();
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
			HashSet<NavajoListener> registered = registry.get(type);
			registered.remove(l);
		}
	}

	private void publishMonitoredEvent(final NavajoEvent ne) {

		if ( monitoredEvents.contains( ne.getClass().getName() )) {
			
			Notification n = 
				new Notification(ne.getClass().getName(), "Navajo Event Registry", GenericThread.notificationSequence++,
								 System.currentTimeMillis(), ne.toString());

			sendNotification(n); 
		}
	}
	
	/**
	 * Publish an event and handle the listeners asynchronously
	 * 
	 * @param ne
	 * @param ignoreProxyListeners, if set to true, listeners of class NavajoEventProxy are ignored to prevent event ping-pong.
	 */
	public void publishAsynchronousEvent(final NavajoEvent ne, boolean ignoreProxyListeners) {

		//System.err.println("Asynchronous Event Triggered: " + ne.getClass());
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
					t.printStackTrace(System.err);
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

		//System.err.println("Synchronous Event Triggered: " + ne.getClass());
		publishMonitoredEvent(ne);
		
		Set<NavajoListener> copy = getInterestedParties(ne);
		if ( copy != null )  {
			Iterator<NavajoListener> i = copy.iterator();
			while ( i.hasNext() ) {
				try {
					NavajoListener nl = i.next();
					if ( !ignoreProxyListeners || !( nl instanceof NavajoEventProxy ))
					nl.onNavajoEvent(ne);
				} catch (Throwable t) {
					t.printStackTrace(System.err);
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
		synchronized (semaphore) {
			HashSet<NavajoListener> copy = null; 
			HashSet<NavajoListener> registered = registry.get(ne.getClass());
			if ( registered != null ) {
				copy = new HashSet<NavajoListener>();
				copy.addAll(registered);
				return copy;
			} else {
				return null;
			}
		}
	}

	/**
	 * Checks whether a certain event is monitored.
	 * @param type, the NavajoEvent class to which NavajoListener instances are attached.
	 * @param ignoreEventProxy, if set to true NavajoEventProxy instances are ignored as 'real' listeners.
	 * @return
	 */
	public boolean isMonitoredEvent(Class<? extends NavajoEvent> type, boolean ignoreEventProxy) {
		Iterator iter = registry.keySet().iterator();
		while ( iter.hasNext() ) {
			Class s = (Class) iter.next();
			if ( s.equals(type) ) {
				HashSet listeners = registry.get(s);
				Iterator all = listeners.iterator();
				while ( all.hasNext() ) {
					NavajoListener nl = (NavajoListener) all.next();
					if ( !ignoreEventProxy || !( nl instanceof NavajoEventProxy ) ) {
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

		if (notification instanceof MonitorNotification) { 
			
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getType() );
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getMessage() );
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getSequenceNumber() );
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getTimeStamp() );
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + notification.getSource() );		
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + ((MonitorNotification) notification).getObservedObject() );
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + ((MonitorNotification) notification).getObservedAttribute() );
			System.err.println(">>>>>>>>>>>> RECEIVED NOTIFICATION: " + ((MonitorNotification) notification).getTrigger() );
			
		}

	}

	public int getNumberOfRegisteredListeners() {
		return registry.size();
	}
	
	public static void main(String [] args) {
		NavajoEventRegistry n = NavajoEventRegistry.getInstance();
		n.addListener(NavajoEvent.class, new NavajoEventProxy());
		n.addListener(NavajoEvent.class, null);
		System.err.println(">>>> " + n.isMonitoredEvent(NavajoEvent.class, true));
		System.err.println(">>>> " + n.isMonitoredEvent(TribeMemberDownEvent.class, true));
	}
}
