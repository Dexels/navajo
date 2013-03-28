package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

/**
 * Interface for implementing topic based publish/subscribe in the Tribe.
 * 
 * @author arjenschoneveld
 *
 */
public interface TribalTopic {

	/**
	 * Register interest in this Tribal Topic.
	 * 
	 * @param tl
	 */
	public void addTopicListener(TopicListener tl);
	
	/**
	 * Deregister interest in this Tribal Topic.
	 * 
	 * @param tl
	 */
	public void removeTopicListener(TopicListener tl);
	
	/**
	 * Publish a new Message
	 * 
	 * @param s the message that will be published
	 */
	public void publish(Serializable s);
	
	/**
	 * Fetch the unique name associated with this Tribal Topic.
	 * 
	 * @return
	 */
	public String getName();
}
