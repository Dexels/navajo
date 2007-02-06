package com.dexels.navajo.server.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.rmi.RMIConnector;
import javax.management.remote.rmi.RMIServer;
import javax.script.CompiledScript;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public final class JMXHelper  {
		
	private JMXConnector conn;
	private MBeanServerConnection server;
	private String host = "localhost";
	private int port = 9999;
	
	public static String SCRIPT_DOMAIN = "com.dexels.navajo.script:type=";
	public static String ADAPTER_DOMAIN = "com.dexels.navajo.adapter:type=";
	public static String NAVAJO_DOMAIN = "com.dexels.navajo.service:type=";
	public static String ASYNC_DOMAIN = "com.dexels.navajo.async:type=";
	public static String TASK_DOMAIN = "com.dexels.navajo.task:type=";
	
	private RMIServer getRMIServer(String hostName, int port) throws IOException {

		Registry registry = LocateRegistry.getRegistry(hostName, port);
		try {
			RMIServer stub = (RMIServer) registry.lookup("jmxrmi");
			return stub;
		} catch (NotBoundException nbe) {
			nbe.printStackTrace(System.err);
			return null;
		}

	}

	public void disconnect() {
		try {
			if ( conn != null ) {
				conn.close();
			}
			server = null;
			conn = null;
			System.err.println("Disconnected JMX.");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	 
	public void connect(String host, int port) throws IOException {
			this.host = host;
			this.port = port;
			connect();
			System.err.println("Connected JMX.");
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
			e.printStackTrace(System.err);
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
				(ThreadMXBean) ManagementFactory.newPlatformMXBeanProxy(server, "java.lang:type=Threading", java.lang.management.ThreadMXBean.class);
			long [] all = mxthread.getAllThreadIds();
			long [] target = new long[1];
			for (int i = 0; i < all.length; i++) {
				ThreadInfo ti = mxthread.getThreadInfo(all[i]);
				//System.err.println("Found thread: " + ti.getThreadName());
				if ( ti.getThreadName().equals(t.getName() ) )  {
					System.err.println("Found thread: " + t.getName());
					target[0] = all[i];
					myThread = mxthread.getThreadInfo(target, true, true)[0];
					return myThread;
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		return myThread;
	}

	public final static ObjectName getObjectName(String domain, String type) {
		try {
			return new ObjectName(domain + type);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
			return  null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		} 
	}

	public final static void registerMXBean(Object o, String domain, String type) {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
		ObjectName name = getObjectName(domain, type);
		if ( name != null ) {
			try {
				mbs.registerMBean(o, name);
			} catch (InstanceAlreadyExistsException e) {
				System.err.println("WARNING: Could not register MXBean for domain " + domain + ": " + e.getMessage());
				//e.printStackTrace(System.err);
			} catch (MBeanRegistrationException e) {
				System.err.println("WARNING: Could not register MXBean for domain " + domain + ": " + e.getMessage());
				//e.printStackTrace(System.err);
			} catch (NotCompliantMBeanException e) {
				System.err.println("WARNING: Could not register MXBean for domain " + domain +  ": " + e.getMessage());
				//e.printStackTrace(System.err);
			} 
		}
	}

	public final static void deregisterMXBean(String domain, String type) throws Throwable {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
		ObjectName name = getObjectName(domain, type);
		if ( name != null ) {
			mbs.unregisterMBean(name);
		}
	}
	
}
