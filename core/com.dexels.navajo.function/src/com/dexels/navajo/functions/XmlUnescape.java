/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
/**
 * @author arjen
 *
 */
public class XmlUnescape extends FunctionInterface {

	  public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Unescapes the following characters in the characters: &'<>\", using xml escape codes";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return XMLutils.XMLUnescape((String) o);
		}  else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
	}


	
}
