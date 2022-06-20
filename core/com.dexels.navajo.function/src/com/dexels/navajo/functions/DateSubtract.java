/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import java.util.Calendar;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public final class DateSubtract extends FunctionInterface {

	public DateSubtract() {}

	@Override
	public String remarks() {
		return "Subtracts two date object";
	}

	@Override
	public String usage() {
		return "DateSubtract(Date1, Date2)";
	}
    @Override
	public boolean isPure() {
    		return true;
    }

	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		if (this.getOperands().size() != 2)
			throw new TMLExpressionException("DateSubtract(Date1, Date2) expected. Wrong no. of args.");

		java.util.Date date1 = (java.util.Date) this.getOperands().get(0);
		java.util.Date date2 = (java.util.Date) this.getOperands().get(1);
		if (date1==null || date2==null) {
			return null;
		}

		long diff = date1.getTime()-date2.getTime();
		int hours = (int)(diff/3600000);
		int millis = (int)(diff % 3600000);

		Calendar cc = Calendar.getInstance();
		cc.clear();
		cc.add(Calendar.HOUR_OF_DAY,hours);
		cc.add(Calendar.MILLISECOND,millis);
		return cc.getTime();
	}
}
