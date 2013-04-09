package com.dexels.navajo.functions;


import java.util.Calendar;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public final class DateSubstract extends FunctionInterface {

	public DateSubstract() {}

	public String remarks() {
		return "Substracts two date object";
	}

	public String usage() {
		return "DateSubstract(Date1, Date2)";
	}

	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
		if (this.getOperands().size() != 2)
			throw new TMLExpressionException("DateSubstract(Date1, Date2) expected. Wrong no. of args.");

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
