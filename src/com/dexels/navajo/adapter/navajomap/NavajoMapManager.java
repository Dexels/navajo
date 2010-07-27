package com.dexels.navajo.adapter.navajomap;

import java.util.HashMap;

import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.resource.ResourceManager;
import com.dexels.navajo.server.resource.ServiceAvailability;

public class NavajoMapManager implements ResourceManager {

	private HashMap<String, Integer> resourceHealthMap = new HashMap<String, Integer>();
	
	private static volatile NavajoMapManager instance;
	
	private NavajoMapManager() {
		JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, "NavajoMapManager");
	}
	 
	public static NavajoMapManager getInstance() {
		if ( instance == null ) {
			instance = new NavajoMapManager();
		}
		return instance;
	}
	
	private String constructProperResourceId(String rawId) {
		// Construct property resourceId.
		if ( rawId == null || rawId.indexOf("/") == -1 ) {
			throw new RuntimeException("Illegal resource specified: " + rawId);
		}
		String hostId = rawId.split("/")[0];
		String applicationId = rawId.split("/")[1];
		
		return hostId + "/" + applicationId;
	}
	
	/**
	 * Returns the health of a Navajo instance.
	 * resourceId is [hostidentifier]/[applicationidentifier].
	 */
	public int getHealth(String resourceId) {
		
		String id = constructProperResourceId(resourceId);
		
		Integer health = resourceHealthMap.get(id);
		
		if ( health == null ) {
			return ServiceAvailability.STATUS_OK;
		}
		
		return health.intValue();
		
	}

	public int getWaitingTime(String resourceId) {
		return 0;
	}

	public boolean isAvailable(String resourceId) {
		int health = getHealth(resourceId);
		return ( health != ServiceAvailability.STATUS_DEAD );
	}

	public void setHealth(String resourceId, int h) {
		String id = constructProperResourceId(resourceId);
		resourceHealthMap.put(id, h);
	}

}
