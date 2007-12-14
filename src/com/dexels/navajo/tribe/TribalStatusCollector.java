package com.dexels.navajo.tribe;

import java.util.HashSet;
import java.util.Iterator;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class TribalStatusCollector extends GenericThread implements TribalStatusCollectorMXBean {

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

			AuditLog.log("Tribal Status Collector", "Started message queue process $Id$");
			return instance;
		}
	}
	
	public void worker() {
		ClusterState cs = TribeManager.getInstance().getClusterState();
		HashSet<TribeMember> copyOf = new HashSet<TribeMember>(cs.clusterMembers);
		Iterator<TribeMember> members = copyOf.iterator();
		while ( members.hasNext() ) {
			TribeMember tm = members.next();
			PingAnswer pa = (PingAnswer) TribeManager.getInstance().askSomebody(new PingRequest(), tm.getAddress());
			tm.setStatus(pa);
		}
	}

	public int getClusterSize() {
		return TribeManager.getInstance().getClusterState().clusterMembers.size();
	}

	public String getChief() {
		return TribeManager.getInstance().getChiefName();
	}

	public boolean getIsChief() {
		return TribeManager.getInstance().getIsChief();
	}

	public String getMyName() {
		return TribeManager.getInstance().getMyName();
	}

	private String getClusterInfo(String field) {
		StringBuffer result = new StringBuffer();
		Iterator<TribeMember> i = TribeManager.getInstance().getClusterState().clusterMembers.iterator();
		while ( i.hasNext() ) {
			TribeMember tm = i.next();
			if ( field.equals("NAME") ) {
				result.append(tm.getMemberName() + ";");
			} else if ( field.equals("CPU") ) {
				result.append(tm.getStatus().getCpuLoad() + ";");
			} else if ( field.equals("THREADS") ) {
				result.append(tm.getStatus().getThreadCount() + ";");
			} else if ( field.equals("JOINDATE") ) {
				result.append(tm.getJoinDate() + ";");
			}
			
		}
		String s = result.toString();
		return s.substring(0, s.length() - 1);
	}
	
	public String getClusterMembers() {
		return getClusterInfo("NAME");
	}
	
	public String getClusterCPULoads() {
		return getClusterInfo("CPU");
	}
	
	public String getClusterThreadCounts() {
		return getClusterInfo("THREADS");
	}
	
	public String getClusterJoinDates() {
		return getClusterInfo("JOINDATE");
	}
}
