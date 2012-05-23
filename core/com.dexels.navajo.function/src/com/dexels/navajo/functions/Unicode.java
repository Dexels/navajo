package com.dexels.navajo.functions;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


public class Unicode extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			String s = (String)o;
			int i = Integer.decode(s).intValue();
			Character c = new Character((char) i);
			return ""+c;
		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}

	@Override
	public String remarks() {
		return "Parses a number string (can be hex, use 0x... in that case), returns a unicode character";
	}

	@Override
	public String usage() {
		return "Unicode(hex-string)";
	}

	public static void main(String [] args) throws Exception {
		Unicode u = new Unicode();
		u.reset();
		u.insertOperand("0x2f");
		
		System.err.println(u.evaluate());
	}
}
