/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components.calendar;

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
 */

public class MultipleDayContainer {
	private Day[] days;

	public MultipleDayContainer() {
	}

	public MultipleDayContainer(Day[] days) {
		this.days = days;
	}

	public void setDays(Day[] days) {
		this.days = days;
	}

	public Day[] getDays() {
		return days;
	}

}