package com.dexels.navajo.functions;

import java.util.regex.Pattern;

import com.dexels.navajo.document.Operand;
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

	    String suspect = (String) o1;
	    String pattern = (String) o2;

	    try {
	     Pattern re = Pattern.compile(pattern);
	     boolean isMatch = re.matcher(suspect).matches();
	     if(!isMatch) {
	       return new Boolean(false);
	     } else
	       return new Boolean(true);
	   }
	   catch (Exception ree) {
	     return new Boolean(false);
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

	public static void main(String [] args) throws Exception {
		EqualsPattern ep = new EqualsPattern();
		ep.reset();
		ep.insertOperand(new String("NCX12G1"));
		ep.insertOperand(new String("[A-Z]{4}[0-9]{2}[A-Z0-9]{1}"));
		Object o = ep.evaluate();
		System.err.println("o =" + o);
	}
}
