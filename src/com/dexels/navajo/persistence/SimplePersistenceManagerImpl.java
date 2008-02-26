package com.dexels.navajo.persistence;

import com.dexels.navajo.document.Navajo;

public class SimplePersistenceManagerImpl implements PersistenceManager {

	public Persistable get(Constructor c, String key, String service, long expirationInterval, boolean persist) throws Exception {
		
		return c.construct();
	}

	public void setConfiguration(Navajo config) {
		
	}

	public Persistable get(Constructor c, String key, long expirationInterval,
			boolean persist) throws Exception {
		
		return c.construct();
	}

	public void clearCache() {
		// TODO Auto-generated method stub
		
	}

	public double getHitratio() {
		// TODO Auto-generated method stub
		return 0;
	}

}
