package com.dexels.navajo.server.enterprise.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.sharedstore.SerializationUtil;
import com.dexels.navajo.sharedstore.SharedStoreFactory;

/**
 * Wraps a Navajo object in a nice rug for cross Tribal transportation purposes
 * 
 * @author arjenschoneveld
 *
 */
public class DefaultNavajoWrap implements NavajoRug {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4689405438923422437L;

	private final String reference;
	
	public DefaultNavajoWrap(Navajo n) {
		reference = SerializationUtil.serializeNavajo(SharedStoreFactory.getInstance(), n, 
				System.currentTimeMillis() + "-" + n.hashCode() + ".xml");
	}
	
	public Navajo getNavajo() {
		return SerializationUtil.deserializeNavajo(SharedStoreFactory.getInstance(), reference);
	}
	
	@Override
	public void finalize() {
		SerializationUtil.removeNavajo(SharedStoreFactory.getInstance(), reference);
	}
}
