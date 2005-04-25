
package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.document.types.*;

/**
 * @author Jarno Posthumus
 */
public class FileSizeCheck extends FunctionInterface {

	public FileSizeCheck() {
	}
	
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
		
		if (getOperand(0) == null) {
			return new Integer(0);
		}
		
		Binary binaryFile = (Binary)getOperand(0);
		byte b[] = binaryFile.getData();
		return new Integer(b.length);
    }
	
	public String usage() {
	    return "";
	}
	
	public String remarks() {
	    return "";
	}
	
	public static void main(String args[]) throws Exception {
		
		Binary b = new Binary(new java.io.FileInputStream("C:/orion.bat"));
		FileSizeCheck fsc = new FileSizeCheck();
		fsc.reset();
		fsc.insertOperand(b);
		Object o = fsc.evaluate();
		System.err.println("o = " + o);
		
    }
}
