/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
