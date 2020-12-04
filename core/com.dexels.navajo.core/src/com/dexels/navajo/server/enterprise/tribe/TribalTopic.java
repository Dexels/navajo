/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	
	/**
	 * Return number of listeners interested in this Topic
	 * 
	 * @return
	 */
	public long getInterestCount();
	
}
