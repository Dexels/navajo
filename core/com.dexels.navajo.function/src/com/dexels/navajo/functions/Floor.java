/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class Floor extends FunctionInterface {

	@Override
	public String remarks() {
		return "Return the floor value of the given number.";
	}

	@Override
	public String usage() {
		return "Floor(number), where number is of type double";
	}

	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		Object a = this.getOperands().get(0);

		try {
			Double d = (Double) a;
			double dd = d.doubleValue();
			return Math.floor(dd);
		} catch (Exception e) {
			throw new TMLExpressionException(this, "Illegal type specified in Ceil() function: " + e.getMessage());
		}
	}

	public static void main(String[] args) throws Throwable {
		double a = 1.1;
		int b = 10;
		Floor m = new Floor();
		m.reset();
		m.insertFloatOperand(Double.valueOf(a));
		System.out.println("Floor of " + a + " is " + m.evaluate());
	}

}
