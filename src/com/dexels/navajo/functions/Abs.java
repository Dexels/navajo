/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author arjen
 *
 */
public class Abs extends FunctionInterface {

	public Abs() {	
//		super(new Class[][]{ {Float.class,Integer.class, null} });
//		setReturnType(new Class[]{String.class});
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Returns absolute value of a number";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		if (o == null) {
			return null;
		}
		
		if (o instanceof Float) {
			return new Float(Math.abs(((Float) o).floatValue()));
		} else if (o instanceof Double) {
			return new Double(Math.abs(((Double) o).doubleValue()));
		} else if (o instanceof Integer) {
			return new Integer(Math.abs(((Integer) o).intValue()));
		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}
	
	public static void main(String [] args) throws Exception {
	
		if ( true ) {
			Abs a = new Abs();
			a.reset();
			a.insertOperand(new Double(-30));
			Object o = a.evaluateWithTypeChecking();
		}
		
		// Timing.
		if ( false ) {
			long start = System.currentTimeMillis();
			for (int i = 0; i < 10000; i++) {
				Abs a = new Abs();
				a.reset();
				a.insertOperand(new Integer(-30));
				Object o = a.evaluateWithTypeChecking();
				//System.err.println("o = " + o);
			}
			System.err.println("With type checking, took: " +  ( System.currentTimeMillis() - start ) + " millis.");

			start = System.currentTimeMillis();
			for (int i = 0; i < 10000; i++) {
				Abs a = new Abs();
				a.reset();
				a.insertOperand(new Integer(-30));
				Object o = a.evaluate();
				//System.err.println("o = " + o);
			}
			System.err.println("Without type checking, took: " +  ( System.currentTimeMillis() - start ) + " millis.");
		}
		
	}
	

}
