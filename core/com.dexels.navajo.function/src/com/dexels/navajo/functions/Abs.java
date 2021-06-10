/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class Abs extends FunctionInterface {

	@Override
	public String remarks() {
		return "Returns absolute value of a number";
	}

	@Override
	public boolean isPure() {
		return true;
	}

	@Override
	public Object evaluate() throws TMLExpressionException {

	    Object o = operand(0).value;
		if (o == null) {
			return null;
		}

		if (o instanceof Integer) {
            return Integer.valueOf(Math.abs((Integer) o));
        } else if (o instanceof Long) {
            return Long.valueOf(Math.abs((Long) o));
        } else if (o instanceof Float) {
			return Float.valueOf(Math.abs((Float) o));
		} else if (o instanceof Double) {
			return Double.valueOf(Math.abs((Double) o));
		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}

}
