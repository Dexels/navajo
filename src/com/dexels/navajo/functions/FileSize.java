
package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.document.types.*;

/**
 * @author Jarno Posthumus
 */
public class FileSize extends FunctionInterface {

	public FileSize() {
	}
	
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
		
		if (getOperand(0) == null) {
			return new Integer(0);
		}
		
		Binary binaryFile = (Binary)getOperand(0);
		
		return new Integer( (int) binaryFile.getLength());
    }
	
	public String usage() {
	    return "";
	}
	
	public String remarks() {
	    return "";
	}
	
	public static void main(String args[]) throws Exception {
		
		Binary b = new Binary(new java.io.FileInputStream("C:/orion.bat"));
		FileSize fsc = new FileSize();
		fsc.reset();
		fsc.insertOperand(b);
		Object o = fsc.evaluate();
		System.err.println("o = " + o);
		
    }
}
