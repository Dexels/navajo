/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.enterprise.tribe.TribeMemberInterface;

/**
 * This event is used to communicate internally that a specific tribe member has passed away.
 * NOTE: THIS EVENT IS ONLY AVAILABLE FOR THE CHIEF OF THE TRIBE, NON-CHIEF MEMBERS DO NOT RECEIVE THIS EVENT!
 * 
 * @author arjen
 *
 */
public class TribeMemberDownEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3054541910381762664L;
	
	private transient TribeMemberInterface tm;
	
	public TribeMemberDownEvent(TribeMemberInterface tm) {
		this.tm = tm;
	}

	/**
	 * Gets the TribeMember that went down.
	 * 
	 * @return
	 */
	public TribeMemberInterface getTm() {
		return tm;
	}

	@Override
	public Navajo getEventNavajo() {
		return null;
	}
	
	@Override
    public boolean isSynchronousEvent() {
        return true;
    }
}
