package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class RandomColor extends FunctionInterface {
	
	private static  java.util.Random r; 
	
	public RandomColor() {
	}
	
	public String remarks() {
		return "Returns a random color given some seed";
	}
	
	public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

		ArrayList operands = this.getOperands();
		Integer seed = (Integer) this.getOperand(0);
		
		r = new java.util.Random((long) seed.intValue());
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int rnd = Math.abs(r.nextInt()) % 15;
			result.append(Integer.toHexString(rnd));
		}
		return result.toString();
	}
	
	public String usage() {
		return "RandomColor(seed), returns a color string ffffff";
	}
	
	public static void main(String[] args) throws Throwable {
		
		RandomColor r = new RandomColor();
		r.reset();
		r.insertOperand(new Integer(190588));
		System.err.println("Aap: "+r.evaluate());
		
		r.reset();
		r.insertOperand(new Integer(190591));
		System.err.println("Aap: "+r.evaluate());
		
		r.reset();
		r.insertOperand(new Integer(190590));
		System.err.println("Aap: "+r.evaluate());
		
	}
}
