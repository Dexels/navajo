package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

/**
 * Interface for a TopicEvent
 * 
 * @author arjenschoneveld
 *
 */
public interface TopicEvent extends Serializable {

	/**
	 * Get the message associated with this Topic Event.
	 * 
	 * @return
	 */
	public Object getMessage();
	
	/**
	 * Gets the message source.
	 * 
	 * @return
	 */
	public Object getSource();
}
