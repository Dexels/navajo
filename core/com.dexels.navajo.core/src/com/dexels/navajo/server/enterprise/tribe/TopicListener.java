package com.dexels.navajo.server.enterprise.tribe;

/**
 * Interface for a Topic Listener
 * 
 * @author arjenschoneveld
 *
 */
public interface TopicListener {

	/**
	 * Method that is automatically called in case of a Topic Event.
	 * Make sure that the work done in this method does not take too long since
	 * it could block the Topic Thread.
	 * 
	 * @param event
	 */
	public void onTopic(TopicEvent event);
	
}
