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

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.rmi.RMIConnector;
import javax.management.remote.rmi.RMIServer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public final class JMXHelper implements Mappable {
	
	public Access [] webservices;
	
	private static JMXConnector conn;
	private static MBeanServerConnection server;
	private String host = "localhost";
	private int port = 9999;
	
	public static String SCRIPT_DOMAIN = "com.dexels.navajo.script:type=";
	public static String ADAPTER_DOMAIN = "com.dexels.navajo.adapter:type=";
	public static String NAVAJO_DOMAIN = "com.dexels.navajo.service:type=";
	public static String ASYNC_DOMAIN = "com.dexels.navajo.async:type=";
	
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
//		try {
//			if ( conn != null ) {
//				conn.close();
//			}
//			server = null;
//			webservices = null;
//			conn = null;
//			System.err.println("Disconnected JMX.");
//		} catch (IOException e) {
//			e.printStackTrace(System.err);
//		}
	}
	 
	public void connect(String host, int port) throws IOException {
	
			this.host = host;
			this.port = port;
			connect();
			System.err.println("Connected JMX.");
		
	}
	
	public void connect() throws IOException {
		if ( server == null ) {
			conn = getServerConnection();
			conn.connect();
			server = conn.getMBeanServerConnection();
		}
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
	
	private String getAccessId(String objectname) {
		StringTokenizer st = new StringTokenizer(objectname, "=");
		st.nextToken();
		String lastPart = st.nextToken();
		return lastPart.substring(0, lastPart.length());
	}
	
	public Access [] getWebservices() throws UserException {
		
		try {
			Set s = server.queryMBeans(new ObjectName(SCRIPT_DOMAIN + "*"), null);
			Iterator i = s.iterator();
			Access [] all = new Access[s.size()];
			int index = 0;
			while ( i.hasNext() ) {
				ObjectInstance oi = (ObjectInstance) i.next();
				System.err.println("Found oi " + oi);
				String accessId = getAccessId(oi.getObjectName().toString());
				System.err.println("accessId: " + accessId);
				Access a = Dispatcher.getInstance().getAccessObject(accessId);
				all[index++] = a;
				System.err.println("Found access object = " + a);
			}
			return all;
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}
	
	public ThreadInfo getThread(Thread t) {

		if ( server == null ) {
			return null;
		}
		
		try {
			getWebservices();
		} catch (UserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
	public static void main(String [] args) throws Exception {
		
		JMXHelper jmx = new JMXHelper();
		jmx.connect("ficus", 9999);
		ThreadMXBean mxthread = 
	    	(ThreadMXBean) ManagementFactory.newPlatformMXBeanProxy(jmx.server, "java.lang:type=Threading", java.lang.management.ThreadMXBean.class);
		long [] ids = mxthread.getAllThreadIds();
		System.err.println("ids = " + ids);
		
		jmx.disconnect();
	}

	public void kill() {
		disconnect();
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		try {
			connect();
		} catch (IOException e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	public void store() throws MappableException, UserException {
		disconnect();
	}
	
}
