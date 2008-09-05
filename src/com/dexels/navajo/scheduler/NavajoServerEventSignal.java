package com.dexels.navajo.scheduler;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;
import com.dexels.navajo.util.AuditLog;

public class NavajoServerEventSignal extends SmokeSignal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1610895338378921500L;

	public final static String ADD_SERVER_EVENTPROXY = "add";
	public final static String REMOVE_SERVER_EVENTPROXY = "remove";
	public final static String BROADCAST_SERVER_EVENT = "broadcast";
	
	public NavajoServerEventSignal(String sender, String key, Object value) {
		super(sender, key, value);
	}
	
	@Override
	public void processMessage() {
		if ( !iAmTheSender() && key.equals(BROADCAST_SERVER_EVENT)) {
			// Publish event has if it actually happened on this tribal member.
			NavajoEvent realEvent = (NavajoEvent) getValue();
			// Do not publish event to registered proxies again to prevent ping-pong(!!!)
			NavajoEventRegistry.getInstance().publishEvent( realEvent, true );
			
		}
		if ( !iAmTheSender() && key.equals(ADD_SERVER_EVENTPROXY) ) {
			//AuditLog.log("","ABOUT TO CREATE REMOTE PROXY FOR: " + ((NavajoEventProxy) getValue()).getInterestedParty() + "(" + ((NavajoEventProxy) getValue()).getGuid() + ")"  );
			NavajoEventProxy.addProxyRemote((NavajoEventProxy) getValue());
		}
		if ( iAmTheSender() && key.equals(ADD_SERVER_EVENTPROXY) ) {
			//AuditLog.log("","ABOUT TO CREATE LOCAL PROXY FOR: " + ((NavajoEventProxy) getValue()).getInterestedParty() + "(" + ((NavajoEventProxy) getValue()).getGuid() + ")"  );
			NavajoEventProxy.addProxyLocal((NavajoEventProxy) getValue());
		}
		if ( key.equals(REMOVE_SERVER_EVENTPROXY) ) {
			//AuditLog.log("","ABOUT TO REMOVE PROXY FOR GUID: " + getValue() );
			NavajoEventProxy.removeProxy( (String) getValue() );
		}
	
	}

}
