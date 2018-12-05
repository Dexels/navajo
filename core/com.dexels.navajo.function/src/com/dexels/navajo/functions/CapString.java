package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

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
 * @author unascribed
 * @version $Id$
 */

public final class CapString extends FunctionInterface {

	public static final String vcIdent = "$Id$";

	public CapString() {
	}
    @Override
	public boolean isPure() {
    		return false;
    }

	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this, "Invalid function call");
		}
		Object op = getOperand(0);
		Object size = getOperand(1);
		if (!(op instanceof String)) {
			throw new TMLExpressionException(this, "String argument expected");
		}
		if (!(size instanceof Integer)) {
			throw new TMLExpressionException(this, "Integer argument expected");
		}

		if (op == null || ((String)op).length() == 0) {
			return "";
		} else {
			String s = (String) op;
			int l = (Integer)size;
			if (s.length() > l) {
				return s.substring(0, l);
			} else {
				return s;
			}
		}

	}

	@Override
	public String usage() {
		return "CapString(string, size)";
	}

	@Override
	public String remarks() {
		return "CapString a string";
	}

	public static void main(String[] args) throws TMLExpressionException {
		CapString t = new CapString();
		t.reset();
		t.insertOperand("012345678901234567890");
		t.insertOperand(6);
		String res = (String) t.evaluate();
		System.err.println(">" + res + "<");
	}
}
