/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import java.io.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.functions.scale.*;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 *
 */
public class EmptyBinary extends FunctionInterface {
 
	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Creates an empty binary object";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "EmptyBinary()";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
	    if (getOperands().size()>0) {
            throw new TMLExpressionException(this, "No operands expected. ");
        }
	    return new Binary(new byte[0]);
	}

}
