package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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

public final class Trim extends FunctionInterface {

	public static final String vcIdent = "$Id$";

	public Trim() {
	}

	public final Object evaluate()
			throws com.dexels.navajo.parser.TMLExpressionException {

		final Object op = this.getOperands().get(0);

		if (op == null) {
			return ("");
		}

		if (!(op instanceof java.lang.String)) {
			throw new TMLExpressionException(this, "String argument expected");
		}

		final String s = (String) op;

		return s.trim();
	}

	public String usage() {
		return "Trim(string)";
	}

	public String remarks() {
		return "Trims a string";
	}

	public static void main(String[] args) throws TMLExpressionException {
		Trim t = new Trim();
		t.reset();
		t.insertOperand("   aaap     ");
		String res = (String) t.evaluate();
		System.err.println(">" + res + "<");
	}
} // public class Trim extends FunctionInterface

// EOF: $RCSfile$ //