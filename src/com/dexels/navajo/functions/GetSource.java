/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;

/**
 * @author frank
 * 
 */
public class GetSource extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Converts a navajo object to an xml string";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetSource(Navajo n)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (pp == null) {
			return null;
		}
		if (!(pp instanceof Navajo)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
		}
		Navajo n = (Navajo) pp;
		StringWriter sw = new StringWriter();
		try {
			n.write(sw);
			sw.flush();
			sw.close();
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TMLExpressionException("Error writing navajo: ", e);
		}
	}

}
