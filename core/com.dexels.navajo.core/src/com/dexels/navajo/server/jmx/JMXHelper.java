package com.dexels.navajo.server.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.monitor.GaugeMonitor;
import javax.management.monitor.Monitor;
import javax.management.remote.JMXConnector;
import javax.management.remote.rmi.RMIConnector;
import javax.management.remote.rmi.RMIServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.util.AuditLog;

public final class JMXHelper  {
		
	private JMXConnector conn;
	private MBeanServerConnection server;
	private String host = "localhost";
	private int port = 9999;
	
	private static String applicationPrefix = null;
	//public static final String SCRIPT_DOMAIN = "navajo.script:type=";
	//public static final String ADAPTER_DOMAIN = "navajo.adapter:type=";
	public static final String NAVAJO_DOMAIN = "navajo.service:type=";
	//public static final String ASYNC_DOMAIN = "navajo.async:type=";
	//public static final String TASK_DOMAIN = "navajo.task:type=";
	//public static final String EVENT_DOMAIN = "navajo.event:type=";
	public static final String MONITOR_DOMAIN = "navajo.monitor:type=";
	//public static final String QUEUED_ADAPTER_DOMAIN = "navajo.queuedadapter:type=";
	
	private static HashSet<Monitor> monitors = new HashSet<Monitor>();
	private static HashSet<ObjectName> mbeans = new HashSet<ObjectName>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(JMXHelper.class);
	
	private static Map<Monitor,List<NotificationListener>> listenerMap = new HashMap<Monitor, List<NotificationListener>>();
	private RMIServer getRMIServer(String hostName, int port) throws IOException {

		Registry registry = LocateRegistry.getRegistry(hostName, port);
		try {
			RMIServer stub = (RMIServer) registry.lookup("jmxrmi");
			return stub;
		} catch (NotBoundException nbe) {
			logger.error("Error: ", nbe);
			return null;
		}

	}

	public static void destroy() {
		
		AuditLog.log("JMX", "Deregistering JMX Beans", Level.WARNING);
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 

		Iterator<Monitor> allMonitors = monitors.iterator();
		while ( allMonitors.hasNext() ) {
			Monitor monitor = allMonitors.next();
			try {
				// doesn't do anything
				monitor.stop();
				deregisterListenersForMonitor(monitor);
			} catch (Exception e) {
				AuditLog.log("Error deregistering monitor",e, Level.SEVERE);
			}
			AuditLog.log("JMX", "Stopped JMX Monitor: " + monitor.getClass().getName(), Level.WARNING);
		}
		
		Iterator<ObjectName> allBeans = mbeans.iterator();
		while ( allBeans.hasNext() ) {
			ObjectName mbean = allBeans.next();
			try {
				mbs.unregisterMBean(mbean);
				AuditLog.log("JMX", "Deregistered JMX Bean: " + mbean.getCanonicalName());
			} catch (MBeanRegistrationException e) {
				logger.error("Error: ", e);
			} catch (InstanceNotFoundException e) {
				logger.error("Error: ", e);
			}
		}
		
		listenerMap.clear();
		mbeans.clear();
		monitors.clear();
		AuditLog.log("JMX", "ALL BEANS DEREGISTERED. BEANCOUNT: "+mbs.getMBeanCount(),Level.INFO);
	}
	
	
	public void disconnect() {
		try {
			if ( conn != null ) {
				conn.close();
			}
			server = null;
			conn = null;
			//System.err.println("Disconnected JMX.");
		} catch (IOException e) {
			AuditLog.log("Error disconnecting jmx",e, Level.SEVERE);
		}
	}
	 
	public void connect(String host, int port) throws IOException {
			this.host = host;
			this.port = port;
			connect();
			//System.err.println("Connected JMX.");
	}
	
	public void connect() throws IOException {
			conn = getServerConnection();
			conn.connect();
			server = conn.getMBeanServerConnection();
	}
	
	private JMXConnector getServerConnection() {

		try {
			RMIServer rmi = getRMIServer(this.host, this.port);
			JMXConnector conn = new RMIConnector(rmi, null);
			
			return conn;
		} catch (Exception e) {
			AuditLog.log("Error getting server connection",e, Level.SEVERE);
			return null;
		}
	}
	
	public ThreadInfo getThread(Thread t) {

		if ( server == null ) {
			return null;
		}

		ThreadInfo myThread = null;
		try {
			ThreadMXBean mxthread = 
				ManagementFactory.newPlatformMXBeanProxy(server, "java.lang:type=Threading", java.lang.management.ThreadMXBean.class);
			long [] all = mxthread.getAllThreadIds();
			long [] target = new long[1];
			for (int i = 0; i < all.length; i++) {
				ThreadInfo ti = mxthread.getThreadInfo(all[i]);
				//System.err.println("Found thread: " + ti.getThreadName());
				if ( ti.getThreadName().equals(t.getName() ) )  {
					//System.err.println("Found thread: " + t.getName());
					target[0] = all[i];
					myThread = mxthread.getThreadInfo(target )[0];
					return myThread;
				}
			}
		
		} catch (IOException e) {
			AuditLog.log( "Error getting server connection",e, Level.SEVERE);
		} 
	
		return myThread;
	}

	public final static ObjectName getObjectName(String domain, String type) {
		if ( applicationPrefix == null ) {
			synchronized ( NAVAJO_DOMAIN ) {
				DispatcherInterface instance = DispatcherFactory.getInstance();
				if(instance==null) {
					throw new RuntimeException("Navajo instance not started. Is navajo context listener valid? Check web.xml");
				}
				NavajoConfigInterface navajoConfig = instance.getNavajoConfig();
				if(navajoConfig==null) {
					throw new RuntimeException("Navajo instance not started. Is navajo context listener valid? Check web.xml");
				}
				applicationPrefix = navajoConfig.getInstanceName();
				if ( applicationPrefix  == null ) {
					applicationPrefix = "unnamedapplication";
				} 
			}
		}
		try {
			return new ObjectName(constructObjectName(domain) + type + ",instance=" + applicationPrefix);
		} catch (MalformedObjectNameException e) {
			logger.error("Error: ", e);
			return  null;
		} catch (NullPointerException e) {
			logger.error("Error: ", e);
			return null;
		} 
	}

	private static final String constructObjectName(String domain) {
		return domain;
	}
	
	private static void registerListenerForMonitor(Monitor m, NotificationListener nl) {
		List<NotificationListener> nll = listenerMap.get(m);
		if(nll==null) {
			nll = new ArrayList<NotificationListener>();
			listenerMap.put(m, nll);
		}
		nll.add(nl);
	}

	private static void deregisterListenersForMonitor(Monitor m) {
		List<NotificationListener> nll = listenerMap.get(m);
		if(nll==null) {
			System.err.println("No listeners to deregister for monitor: "+m);
			return;
		}
		System.err.println("# of Monitors to deregister: "+nll.size());
		for (NotificationListener notificationListener : nll) {
			try {
				m.removeNotificationListener(notificationListener);
			} catch (ListenerNotFoundException e) {
				logger.error("Error: ", e);
			}
		}
	}
	public final static void registerMXBean(Object o, String domain, String type) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
		ObjectName name = getObjectName(domain, type);
		if ( name != null ) {
			try {
				mbs.registerMBean(o, name);
			} catch (InstanceAlreadyExistsException e) {
				System.err.println("WARNING: InstanceAlreadyExistsException, Could not register MXBean for domain " + domain + ": " + e.getMessage());
				try {
					mbs.unregisterMBean(name);
					mbs.registerMBean(o, name);
				} catch (MBeanRegistrationException e1) {
					logger.error("Error: ", e1);
				} catch (InstanceNotFoundException e1) {
					logger.error("Error: ", e1);
				} catch (Exception e1) {
					logger.error("Error: ", e1);
				}
				//e.printStackTrace(System.err);
			} catch (MBeanRegistrationException e) {
				System.err.println("WARNING: MBeanRegistrationException, Could not register MXBean for domain " + domain + ": " + e.getMessage());
				//e.printStackTrace(System.err);
			} catch (NotCompliantMBeanException e) {
				System.err.println("WARNING: NotCompliantMBeanException, Could not register MXBean for domain " + domain +  ": " + e.getMessage());
				//e.printStackTrace(System.err);
			} 
		}
		mbeans.add(name);
	}

	public final static void deregisterMXBean(String domain, String type) {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
			ObjectName name = getObjectName(domain, type);
			if ( name != null ) {
				try {
					mbs.unregisterMBean(name);
				} finally {
					mbeans.remove(name);
				}
			}
		} catch (MBeanRegistrationException e) {
			System.err.println("Deregister of: "+domain+" : "+type+" failed ("+e.getMessage()+"). Continuing");
		} catch (InstanceNotFoundException e) {
			System.err.println("Deregister of: "+domain+" : "+type+" failed ("+e.getMessage()+"). Continuing");
		}
	}
		
	public final static GaugeMonitor addGaugeMonitor(NotificationListener listener, String domain, String type, String attributeName, Number low, Number high, long frequency) {
		// construct monitor
		
        final GaugeMonitor monitor = new GaugeMonitor();

        monitor.addObservedObject( getObjectName(domain, type) );
        monitor.setObservedAttribute(attributeName);
        monitor.setNotifyHigh(true);
        if ( low != null ) {
        	monitor.setNotifyLow (true);
        	monitor.setThresholds(high, low); 
        } else {
        	monitor.setNotifyLow(false);
        	monitor.setThresholds(high, new Integer(0)); 
        }
       
        monitor.setGranularityPeriod(frequency);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
        try {
			ObjectName objectName = getObjectName( MONITOR_DOMAIN, "GaugeMonitor-" + monitor.hashCode());
			mbs.registerMBean(monitor, objectName);
			monitor.addNotificationListener( listener, null, null);
			registerListenerForMonitor(monitor,listener);
			monitor.start();
			monitors.add(monitor);
			mbeans.add(objectName);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		
		return monitor;
	}
	
}
