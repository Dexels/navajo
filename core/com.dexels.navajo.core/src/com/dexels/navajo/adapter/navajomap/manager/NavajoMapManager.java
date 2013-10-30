package com.dexels.navajo.adapter.navajomap.manager;

import java.util.HashMap;

import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.resource.ServiceAvailability;

/**
 * This singleton object is used to keep track of the health status
 * of other Navajo Servers.
 * 
 * @author arjen
 *
 */
public class NavajoMapManager implements NavajoMapManagerMBean {

	private HashMap<String, Integer> resourceHealthMap = new HashMap<String, Integer>();
	
	private static volatile NavajoMapManager instance = null;
	
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
		if ( rawId == null ) {
			throw new RuntimeException("Illegal resource specified: " + rawId);
		}
		if ( rawId.indexOf(":") != -1 ) {
			String hostId = rawId.split(":")[0];
			String portId = rawId.split(":")[1];

			return hostId + ":" + portId;
		} else if ( rawId.indexOf("/") != -1 ){
			return rawId.split("/")[0];
		} else {
			return rawId;
		}
	}
	
	/**
	 * Returns the health of a Navajo instance.
	 * resourceId is [hostidentifier]/[applicationidentifier].
	 */
	@Override
	public int getHealth(String resourceId) {
		
		String id = constructProperResourceId(resourceId);
		
		Integer health = resourceHealthMap.get(id);
		
		if ( health == null ) {
			return ServiceAvailability.STATUS_OK;
		}
		
		return health.intValue();
		
	}

	@Override
	public int getWaitingTime(String resourceId) {
		return 0;
	}

	@Override
	public boolean isAvailable(String resourceId) {
		int health = getHealth(resourceId);
		return ( health != ServiceAvailability.STATUS_DEAD );
	}

	@Override
	public void setHealth(String resourceId, int h) {
		String id = constructProperResourceId(resourceId);
		resourceHealthMap.put(id, h);
	}

}
