/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.event.InputEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 * @deprecated
 */

public class CalendarEvent {
	CalendarTable mySource;
	InputEvent myEvent;

	public CalendarEvent(CalendarTable t, InputEvent e) {
		mySource = t;
		myEvent = e;
	}

	public CalendarEvent() {

	}

	public InputEvent getEvent() {
		return myEvent;
	}

	public CalendarTable getSource() {
		return mySource;
	}

}