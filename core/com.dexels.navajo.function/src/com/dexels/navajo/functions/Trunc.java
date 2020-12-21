/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.text.ParseException;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class Trunc extends FunctionInterface {

	@Override
	public String remarks() {
		return "Given a date, a date object without time is returned";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		java.util.Date date = getDateOperand(0);
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException ex) {
			throw new TMLExpressionException(this, "Invalid date format: " + date + " for given date pattern: " + "yyyy-MM-dd");
		}
		return date;
	}

	@Override
	public boolean isPure() {
    		return false;
    }

	@Override
	public String usage() {
	    return "Trunc(Date)";
	}
	
	public static void main(String [] args) throws Exception {
		Trunc t = new Trunc();
		t.reset();
		java.util.Date date = new java.util.Date();
		System.err.println(date);
		t.insertDateOperand(date);
		System.err.println(t.evaluate());
	  }
}
