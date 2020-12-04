/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.Calendar;
import java.util.Date;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class DateAppendClockTime extends FunctionInterface {

	@Override
	public String remarks() {
		return "Returns a date object that combines the two arguements that were provided";
	}

	@Override
	public String usage() {
		return "DateTime(Date date, ClockTime cTime)";
	}

	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

		// Arguments number check
		if (this.getOperands().size() != 2) {
			throw new TMLExpressionException(this,
					"error: arguements missing. Please provide a valid Date and a valid ClockTime object.");
		}

		Object arg0 = this.getOperand(0);
		Object arg1 = this.getOperand(1);

		// Arguments type checks
		if (!(arg0 instanceof Date)) {
			throw new TMLExpressionException(this,
					"error: arguement 0 must be a Date object. The argument you supplied is : "
							+ this.getOperand(0).getClass());
		}

		if (!(arg1 instanceof ClockTime)) {
			throw new TMLExpressionException(this,
					"error: argument 1 must be a ClockTime. The argument you supplied is : "
							+ this.getOperand(1).getClass());
		}

		Date date = (Date) arg0;
		ClockTime cTime = (ClockTime) arg1;
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		calendar.set(Calendar.SECOND, cTime.getSeconds());
		calendar.set(Calendar.MINUTE, cTime.getMinutes());
		calendar.set(Calendar.HOUR, cTime.getHours());

		return calendar.getTime();
	}
}