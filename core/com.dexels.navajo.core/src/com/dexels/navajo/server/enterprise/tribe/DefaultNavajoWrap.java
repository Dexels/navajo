package com.dexels.navajo.server.enterprise.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.sharedstore.SerializationUtil;

/**
 * Wraps a Navajo object in a nice rug for cross Tribal transportation purposes
 * 
 * @TODO: Register DefaultNavajoWrap object with Garbage Collector to remove 'old' objects.
 * 
 * @author arjenschoneveld
 * 
 *
 */
public class DefaultNavajoWrap implements NavajoRug {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4689405438923422437L;

	public final String reference;
	
	public DefaultNavajoWrap(Navajo n) {
		reference = SerializationUtil.serializeNavajo(n, System.currentTimeMillis() + "-" + n.hashCode() + ".xml");
	}
	
	public DefaultNavajoWrap(Navajo n, String id) {
		if ( !id.endsWith(".xml") ) {
			id = id + ".xml";
		}
		reference = SerializationUtil.serializeNavajo(n, id + ".xml");
	}
	
	public Navajo getNavajo() {
		return SerializationUtil.deserializeNavajo(reference);
	}
	
}
