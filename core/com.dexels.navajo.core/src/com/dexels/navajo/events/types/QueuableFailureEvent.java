/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.server.enterprise.queue.Queuable;

public class QueuableFailureEvent implements NavajoEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8856473507658376031L;

	private Queuable myQueable;
	
	public QueuableFailureEvent(Queuable q) {
		myQueable = q;
	}
	
	@Override
	public Navajo getEventNavajo() {
		return null;
	}

	public Queuable getMyQueable() {
		return myQueable;
	}

	@Override
    public boolean isSynchronousEvent() {
        return false;
    }
}
