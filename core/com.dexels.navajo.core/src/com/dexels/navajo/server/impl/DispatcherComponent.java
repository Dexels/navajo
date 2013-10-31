package com.dexels.navajo.server.impl;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;

public class DispatcherComponent extends Dispatcher implements
		DispatcherInterface {

//	AuditLog auditLog = null;
	BundleContext myContext = null;
	NavajoEventRegistry myRegistry = null;
	TribeManagerInterface tribeManager = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DispatcherComponent.class);
//	private ServiceRegistration<AuditLog> auditLogSr;
	
	public void activate(BundleContext context) {
		logger.info("Activate dispatcher!");
		myContext = context;
		// Register AuditLog.
//		AuditLog al = new AuditLog(getNavajoConfig().getInstanceName(), myRegistry);
//		auditLog = al;
//		auditLogSr = myContext.registerService(AuditLog.class, al, null);
		new DispatcherFactory(this);
	}
	public void deactivate() {
		logger.debug("Deactivate dispatcher!");
		myContext = null;
//		auditLogSr.unregister();
	}

	@Override
	public void setNavajoConfig(NavajoConfigInterface nci) {
		logger.debug("setNavajoConfig dispatcher!");
		super.setNavajoConfig(nci);
	}

	public void setTribeManager(TribeManagerInterface tmi) {
		tribeManager = tmi;
	}
	
	public void clearTribeManager(TribeManagerInterface tmi) {
		tribeManager = null;
	}
	
	public void setEventRegistry(NavajoEventRegistry ner) {
		myRegistry = ner;
	}
	
	public void clearEventRegistry(NavajoEventRegistry ner) {
		myRegistry = null;
	}
}
