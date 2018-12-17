package com.dexels.navajo.tipi.components.core.adapter;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class FunctionTest {

	public static void main(String[] arr)  {
	}

	@SuppressWarnings("unchecked")
	private FunctionInterface instantiateFunction(String name)
			throws TMLExpressionException {
		Class<FunctionInterface> cc;
		try {
			cc = (Class<FunctionInterface>) Class.forName(name);

			FunctionInterface fi = cc.newInstance();
			fi.reset();
			return fi;
		} catch (Exception e) {
			throw new TMLExpressionException(
					"Error instantiating function object: " + name, e);
		}
	}

	/**
	 * @return
	 * @throws TMLExpressionException
	 */
	public String randomColor() throws TMLExpressionException {
		FunctionInterface fi = instantiateFunction("com.dexels.navajo.functions.RandomColor");
		return (String) fi.evaluate();
	}

	public String randomColor(Integer seed) throws TMLExpressionException {
		FunctionInterface fi = instantiateFunction("com.dexels.navajo.functions.RandomColor");
		fi.insertIntegerOperand(seed);
		return (String) fi.evaluate();
	}

}
