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
	
	private static java.util.Random r = new java.util.Random(System.currentTimeMillis());
	
	private static String [] colors = new String[]{
		"F3AD9D", "8A5EB5", "9B8F8D", "52AA6F", "F4FA33", "8AE314", "387EEF", "A4A931", "F4CFE4", 
		"0D55CF", "D3E08C", "9A7460", "A3FA2E", "BEAE4A", "B452E4", "84F5C9", "60EDBE", "D5320A", 
		"99D3D3", "139A61", "8BA98C", "58F1FC", "423210", "1D7733", "4AAE04", "779DBB", "CD3857", 
		"DA4557", "C85AC2", "A72DB5", "A9D539", "069889", "859F8E", "9A62A5", "52E127", "B6F499", 
		"A27ACC", "C6B444", "BF9BBA", "7B2D5A", "D40F5A", "A1AA99", "B4A0BE", "86B92E", "EBD6F2", 
		"3324D2", "BE6B0C", "4E5BDE", "7EC6F9", "32A70C", "3715B5", "A9D0F2", "C7CFF5", "432FEA", 
		"FB7999", "ABE30E", "38AD6F", "958B80", "3B63CF", "3A98A3", "C66197", "75D04C", "06EF0C", 
		"CDC98F" 	
	};
	
	public RandomColor() {
	}
	
	public String remarks() {
		return "Returns a random color given some seed";
	}
	
	private char toGoogleHex(char c) {
		switch (c) {
		case '0': return 'f';
		case '1': return 'e';
		case '2': return 'd';
		case '3': return 'c';
		case '4': return 'b';
		case '5': return 'a';
		case '6': return '9';
		case '7': return '8';
		case '8': return '7';
		case '9': return '6';
		case 'a': return '5'; 
		case 'b': return '4';
		case 'c': return '3';
		case 'd': return '2';
		case 'e': return '1';
		case 'f': return '0';
		
		default: return '0';
		}
	}
	
	private String toGoogleColor(String color) {
		
		StringBuffer google = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			google.append(toGoogleHex(color.charAt(i)));
		}
		
		return google.toString();
	}
	
	public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

		int rnd = 0;
		if ( getOperands().size() > 0 ) {
			Integer seed = (Integer) getOperand(0);
			java.util.Random r2 = new java.util.Random(seed.longValue());
			rnd = Math.abs(r2.nextInt()) % 64;
		} else {
			rnd = Math.abs(r.nextInt()) % 64;
		}
		return toGoogleColor(colors[rnd]);

	}
	
	public String usage() {
		return "RandomColor(seed), returns a color string ffffff";
	}
	
	public static void main(String[] args) throws Throwable {
		
		RandomColor r = new RandomColor();
		r.reset();
		System.err.println("Aap: "+r.evaluate());
		
		r.reset();
		System.err.println("Aap: "+r.evaluate());
		
		r.reset();
		System.err.println("Aap: "+r.evaluate());
		
		r.reset();
		System.err.println("Aap: "+r.evaluate());
		
	}
}
