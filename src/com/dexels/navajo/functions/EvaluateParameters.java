package com.dexels.navajo.functions;

import java.util.StringTokenizer;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class EvaluateParameters extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException("Wrong number of arguments");
		}

		Message currentMessage = this.getCurrentMessage();
		String expression = (String) getOperand(0);

		String result = "";
		StringTokenizer tok = new StringTokenizer(expression, "[");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			System.err.println("token: " + token);
			if (token.indexOf("]") > 0) {
				String property = token.substring(0, token.indexOf("]"));
				String value = property;
				System.err.println("Property: " + property);
				if (currentMessage != null) {
					if(currentMessage.getProperty(property) != null){
						value = currentMessage.getProperty(property).getValue();
					}
				}
				result += value;
				if (token.indexOf("]") < token.length() - 1) {
					result += token.substring(token.indexOf("]") + 1);
				}
			} else {
				result += token;
			}
		}
		return result;
	}

	@Override
	public String remarks() {
		return "EvaluateParameters(String)";
	}

	public static void main(String[] args) throws Exception {
		String expression = "Hallo [ActivityId] Hoe is het nou? ' met [Lastname]";
		EvaluateParameters ce = new EvaluateParameters();
		ce.reset();
		ce.insertOperand(expression);
		String result = (String) ce.evaluate();
		System.err.println("result:");
		System.err.println(result);
	}

}
