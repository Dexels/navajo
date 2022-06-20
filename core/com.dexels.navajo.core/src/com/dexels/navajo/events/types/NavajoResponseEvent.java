/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.script.api.Access;

public class NavajoResponseEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8810423005552534253L;
	
	private Access myAccess;
	
	public NavajoResponseEvent(Access a) {
		this.myAccess = a;
	}
	
 	public Access getAccess() {
		return myAccess;
	}

	@Override
	public Navajo getEventNavajo() {
		return null;
	}
	@Override
    public boolean isSynchronousEvent() {
        return false;
    }
	
}
