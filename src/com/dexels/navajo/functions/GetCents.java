package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Money;
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

public final class GetCents extends FunctionInterface {

	public static final String vcIdent = "$Id$";

	public GetCents() {
	}

	public final Object evaluate()
			throws com.dexels.navajo.parser.TMLExpressionException {

		final Object op = this.getOperands().get(0);

		if (op == null) {
			return ("");
		}

		if (!(op instanceof Money)) {
			throw new TMLExpressionException(this, "Money argument expected");
		}

		Money mo = (Money) op;
		return MoneyToCents(mo);
	}

	final String MoneyToCents(Money mo) {

		String cents;
		cents = mo.toString();
		int lastOne = cents.length();
		int beforeLastOne = cents.length() - 2;
		cents = cents.substring(beforeLastOne, lastOne);
		return cents;
	}

	public static void main(String[] args) throws Exception {

		java.util.Locale.setDefault(new java.util.Locale("nl", "NL"));
		// Tests.
		GetCents tm = new GetCents();
		tm.reset();
		tm.insertOperand(new Money(10.55));
		System.out.println("result = " + tm.evaluate());

	}

	public String usage() {
		return "GetCents(Money)";
	}

	public String remarks() {
		return "GetCents takes an amount of money and returns the cents";
	}

}