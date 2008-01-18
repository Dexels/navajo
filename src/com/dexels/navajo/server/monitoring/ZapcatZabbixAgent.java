package com.dexels.navajo.server.monitoring;

import org.kjkoster.zapcat.Agent;
import org.kjkoster.zapcat.zabbix.ZabbixAgent;

import com.dexels.navajo.server.enterprise.monitoring.AgentInterface;
import com.dexels.navajo.util.AuditLog;

public class ZapcatZabbixAgent implements AgentInterface {

	private static volatile ZapcatZabbixAgent instance = null;

	private static Object semaphore = new Object();

	private volatile Agent myAgent = null;
	
	public static ZapcatZabbixAgent getInstance() {
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized (semaphore) {
			instance = new ZapcatZabbixAgent();
		}
		
		return instance;
	}
	
	public void stop() {
		instance.myAgent.stop();
	}

	public String getAgentInfo() {
		return "This is a Zapcat/Zabbix agent";
	}

	public void start() {
		if ( instance.myAgent == null ) {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_MONITOR, "Started Zapcat Monitoring Agent");
			instance.myAgent = new ZabbixAgent();
			
		}
	}
	
}
