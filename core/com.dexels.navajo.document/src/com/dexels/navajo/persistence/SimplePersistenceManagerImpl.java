package com.dexels.navajo.persistence;

import com.dexels.navajo.document.Navajo;

public class SimplePersistenceManagerImpl implements PersistenceManager {

	@Override
	public Persistable get(Constructor c, String key, String service, long expirationInterval, boolean persist) throws Exception {
		
		return c.construct();
	}

	@Override
	public void setConfiguration(Navajo config) {
		
	}

	@Override
	public Persistable get(Constructor c, String key, long expirationInterval,
			boolean persist) throws Exception {
		
		return c.construct();
	}

	@Override
	public void clearCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHitratio() {
		// TODO Auto-generated method stub
		return 0;
	}

}
