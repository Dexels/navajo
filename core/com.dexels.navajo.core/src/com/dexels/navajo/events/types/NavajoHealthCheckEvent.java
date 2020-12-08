/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.events.types;

import java.util.logging.Level;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;


public class NavajoHealthCheckEvent implements NavajoEvent, LevelEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917699466935141060L;
	
	private String message;
	private Level level;
	
	public NavajoHealthCheckEvent(Level level, String msg) {
		message = msg;
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Navajo getEventNavajo() {
		return null;
	}

	@Override
	public Level getLevel() {
		return level;
	}
	@Override
    public boolean isSynchronousEvent() {
        return false;
    }
	
}
