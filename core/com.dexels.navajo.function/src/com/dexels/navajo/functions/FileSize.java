
package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * @author Jarno Posthumus
 */
public class FileSize extends FunctionInterface {

	public FileSize() {
	}
	
	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		
		if (getOperand(0) == null) {
			return new Integer(0);
		}
		
		Binary binaryFile = (Binary)getOperand(0);
		
		return new Integer( (int) binaryFile.getLength());
    }
	
	@Override
	public String remarks() {
	    return "Get the filesize of supplied Binary object in bytes.";
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
