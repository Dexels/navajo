package com.dexels.navajo.functions;
import java.net.URLEncoder;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


public class URLEncode extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return URLEncoder.encode((String) o);
		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}

	@Override
	public String remarks() {
		return "Returns URL encoded string";
	}

	@Override
	public String usage() {
		return "URLEncode(s)";
	}

	public static void main(String [] args) throws Exception {
		URLEncode u = new URLEncode();
		u.reset();
		u.insertOperand("matthijs is 's werelds");
		
		System.err.println(u.evaluate());
	}
}
