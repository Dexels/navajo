package com.dexels.navajo.functions;

import java.util.regex.Pattern;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class EqualsPattern extends FunctionInterface {

	@Override
	public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

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
		ep.insertOperand("NCX12G1");
		ep.insertOperand("[A-Z]{4}[0-9]{2}[A-Z0-9]{1}");
		Object o = ep.evaluate();
		System.err.println("o =" + o);
	}
}
