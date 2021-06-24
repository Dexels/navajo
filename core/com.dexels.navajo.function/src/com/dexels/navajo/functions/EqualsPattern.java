/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.regex.Pattern;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class EqualsPattern extends FunctionInterface {

	@Override
	public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

		Object o1 = getOperand(0);
		if (!(o1 instanceof String)) {
			throw new TMLExpressionException(this, "String input expected");
		}

		Object o2 = getOperand(1);
		if (!(o2 instanceof String)) {
			throw new TMLExpressionException(this, "String input expected");
		}

		try {
			return Boolean.valueOf( Pattern.compile((String) o2).matcher((String) o1).matches() );
		} catch (Exception ree) {
			return Boolean.FALSE;
		}

	}

	@Override
	public boolean isPure() {
    		return true;
    }

	@Override
	public String remarks() {
		return "Determines whether a string matches a pattern";
	}

	@Override
	public String usage() {
		return "EqualsPattern(<string>,<pattern>)";
	}

	public static void main(String[] args) throws Exception {
		EqualsPattern ep = new EqualsPattern();
		ep.reset();
		ep.insertStringOperand("NCX12G1");
		ep.insertStringOperand("[A-Z]{4}[0-9]{2}[A-Z0-9]{1}");
		Object o = ep.evaluate();
		System.err.println("o =" + o);
	}
}
