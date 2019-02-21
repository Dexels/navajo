package com.dexels.navajo.functions;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


public class Unicode extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		String s = getStringOperand(0);
		int i = Integer.decode(s).intValue();
		Character c = Character.valueOf((char) i);
		return ""+c;
	}

	@Override
	public String remarks() {
		return "Parses a number string (can be hex, use 0x... in that case), returns a unicode character";
	}
    @Override
	public boolean isPure() {
    		return false;
    }

	@Override
	public String usage() {
		return "Unicode(hex-string)";
	}

	public static void main(String [] args) throws Exception {
		Unicode u = new Unicode();
		u.reset();
		u.insertStringOperand("0x2f");
		
		System.err.println(u.evaluate());
	}
}
