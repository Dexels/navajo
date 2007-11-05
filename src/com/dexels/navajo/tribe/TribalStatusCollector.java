package com.dexels.navajo.tribe;

import java.util.Iterator;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class TribalStatusCollector extends GenericThread {

	private static String id = "Tribal Status Collector";
	private static Object semaphore = new Object();
	
	private static volatile TribalStatusCollector instance = null;
	
	public TribalStatusCollector() {
		super(id);
	}
	
	public static TribalStatusCollector getInstance() {

		if (instance!=null) {
			return instance;
		}

		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			}

			instance = new TribalStatusCollector();	
			try {
				JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			} 
			instance.myId = id;
			instance.setSleepTime(5000);
			instance.startThread(instance);

			AuditLog.log("Adapter Message Queue", "Started message queue process $Id$");
			return instance;
		}
	}
	
	public void worker() {
		ClusterState cs = TribeManager.getInstance().getClusterState();
		Iterator<TribeMember> members = cs.clusterMembers.iterator();
		while ( members.hasNext() ) {
			TribeMember tm = members.next();
			PingAnswer pa = (PingAnswer) TribeManager.getInstance().askSomebody(new PingRequest(), tm.getAddress());
			tm.setStatus(pa);
		}
	}
}
