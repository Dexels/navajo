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
		
		int seedInt = seed.intValue();
		r = new java.util.Random((long) seedInt);
		
		int channel = seedInt % 3;
		int extra = seedInt % 6;
		
		char [] c = new char[6];
		for (int i = 0; i < 6; i++) {
			int rnd = Math.abs(r.nextInt()) % 15;
			c[i] = Integer.toHexString(rnd).charAt(0);
		}
		
		StringBuffer r = new StringBuffer();
		switch (channel) {
		case 0: c[0] = '0';c[1] = '0';c[extra] = '0';r.append(c);break;
		case 1: c[2] = '0';c[3] = '0';c[extra] = '0';r.append(c);break;
		case 2: c[4] = '0';c[5] = '0';c[extra] = '0';r.append(c);break;
		default: break;
		}
		return r.toString();
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
		
		r.reset();
		r.insertOperand(new Integer(190592));
		System.err.println("Aap: "+r.evaluate());
		
	}
}
