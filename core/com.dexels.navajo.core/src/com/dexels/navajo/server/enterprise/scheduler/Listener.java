/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.scheduler;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;

/**
 * The Listener interface.
 * 
 * @author arjen
 *
 */
public interface Listener extends Serializable {
	
	public String getListenerId();

	/**
	 * Perform the task that is associated with this listener.
	 * 
	 * @param a
	 * @return
	 */
	public Navajo perform();
	
	public boolean isWorkflowTrigger();
	
	/**
	 * Get owner host.
	 * 
	 * @return
	 */
	public String getOwnerHost();
	
	public void setOwnerHost(String s);
	
	/**
	 * Defines whether this listener is only used in 'local', i.e. in-memory, mode. 
	 * 
	 * @return
	 */
	public boolean isLocal();
	
	public void setLocal();
	
	
}
