
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
			return Integer.valueOf(0);
		}
		
		Binary binaryFile = (Binary)getOperand(0);
		
		return Integer.valueOf( (int) binaryFile.getLength());
    }
	
	@Override
	public String remarks() {
	    return "Get the filesize of supplied Binary object in bytes.";
	}
}
