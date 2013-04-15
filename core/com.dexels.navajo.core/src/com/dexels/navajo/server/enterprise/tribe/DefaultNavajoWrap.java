package com.dexels.navajo.server.enterprise.tribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private transient Navajo myNavajo = null;
	
	public final String reference;
	
	private final static Logger logger = LoggerFactory.getLogger(DefaultNavajoWrap.class);
	
	public DefaultNavajoWrap(Navajo n) {
		if ( n == null ) {
			logger.error("Cannot wrap null Navajo");
		}
		reference = SerializationUtil.serializeNavajo(n, System.currentTimeMillis() + "-" + n.hashCode() + ".xml");
	}
	
	public DefaultNavajoWrap(Navajo n, String id) {
		if ( n == null ) {
			logger.error("Cannot wrap null Navajo: " + id);
		}
		if ( !id.endsWith(".xml") ) {
			id = id + ".xml";
		}
		reference = SerializationUtil.serializeNavajo(n, id + ".xml");
	}
	
	public Navajo getNavajo() {
		if ( myNavajo == null ) {
			myNavajo = SerializationUtil.deserializeNavajo(reference);
			if ( myNavajo == null ) {
				logger.error("Could not de-serialize Navajo object: " + reference);
			}
		}
		return myNavajo;
	}
	
}
