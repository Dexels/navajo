package com.dexels.navajo.scheduler.tribe;

import java.io.Serializable;
import java.util.HashMap;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.TribeMemberDownEvent;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.tribe.TribeManager;
import com.dexels.navajo.util.Util;

/**
 * This class is used to proxy NavajoEvents on other Tribal Members. The proxy is
 * used to make sure that NavajoEvents that are registered as Triggers (NavajoEventTrigger) are
 * emitted to the interested Tribal Member.
 * 
 * @author arjen
 *
 */
public class NavajoEventProxy implements Serializable, NavajoListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1905617747534932582L;

	private final static HashMap<String, NavajoEventProxy> registeredProxies = new HashMap<String, NavajoEventProxy>();
	
	private Class<? extends NavajoEvent> proxiedEventClass;
	private Object interestedParty;
	
	private String guid;
	
	public NavajoEventProxy() {
		
	}
	/**
	 * Create a new NavajoEventProxy object
	 * 
	 * @param eventClass, the NavajoEvent class that needs to be listened to.
	 * @param interestedParty, the address of the Tribal Member that is interested in this specific event.
	 */
	public NavajoEventProxy(Class<? extends NavajoEvent> eventClass, Object interestedParty) {
		proxiedEventClass = eventClass;
		guid = Util.getRandomGuid();
		this.interestedParty = interestedParty;
		// NavajoEventProxy should be interested in TribeMemberDownEvent events for removing
		// proxied events for a deceased member.
		NavajoEventRegistry.getInstance().addListener(TribeMemberDownEvent.class, this);
	}

	public Class<? extends NavajoEvent> getProxiedEventClass() {
		return proxiedEventClass;
	}

	/**
	 * This method is used to register the registration of an event proxy on the remote tribal members.
	 * DO NOT REGISTER PROXY AS NAVAJOEVENTLISTENER, this is only to administer the fact that the proxy
	 * has been created and is active, such that it can be removed when necessary.
	 * 
	 * @param nep
	 */
	public static void addProxyLocal(NavajoEventProxy nep) {
		synchronized (registeredProxies) {
			registeredProxies.put(nep.getGuid(), nep);
		}
	}
	
	/**
	 * This method is used to register an event proxy an a remote tribal member.
	 * REGISTER PROXY AS NAVAJOEVENTLISTENER. If proxy is already present do not re-add.
	 * 
	 * @param nep
	 */
	public static void addProxyRemote(NavajoEventProxy nep) {
		synchronized (registeredProxies) {
			if ( !registeredProxies.containsKey(nep.getGuid()) ) {
				registeredProxies.put(nep.getGuid(), nep);
				NavajoEventRegistry.getInstance().addListener(nep.proxiedEventClass, nep);
			}
		}
	}
	
	/**
	 * Remove a NavajoEventProxy object.
	 * 
	 * @param nep
	 */
	private static void removeProxy(NavajoEventProxy nep) {
		synchronized (registeredProxies) {
			NavajoEventRegistry.getInstance().removeListener(nep.getProxiedEventClass(), nep);
			registeredProxies.remove(nep.getGuid());
		}
	}
	
	/**
	 * Remove a NavajoEventProxy object given its guid.
	 * 
	 * @param guid
	 */
	public static void removeProxy(String guid) {
		NavajoEventProxy nep = registeredProxies.get(guid);
		removeProxy(nep);
	}

	/**
	 * Process a NavajoServerEvent for which this NavajoListener is registered.
	 * 
	 */
	public void onNavajoEvent(NavajoEvent ne) {

		// If a TribeMemberDownEvent occurs and this proxy happens to be a proxy
		// for the deceased member, remove this proxy.
		if ( ne instanceof TribeMemberDownEvent ) {
			if ( interestedParty.equals(((TribeMemberDownEvent) ne).getTm().getAddress() ) ) {
				removeProxy(this);
			}
		} 

		// Inform interested tribal member of event occurrence by emitting same event via multicast.
		TribeManagerFactory.getInstance().multicast(new Object[]{getInterestedParty()},
				new NavajoServerEventSignal(DispatcherFactory.getInstance().getNavajoConfig().getInstanceName(),
						NavajoServerEventSignal.BROADCAST_SERVER_EVENT, ne) );
		
		
	}

	/**
	 * Returns a unique GUID associated with this NavajoEventProxy object.
	 * 
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Return the 'address' of the interested party.
	 * 
	 * @return
	 */
	public Object getInterestedParty() {
		return interestedParty;
	}
}
