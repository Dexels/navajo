/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * @author frank
 * 
 */
public class GetSource extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(GetSource.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Converts a navajo object to an xml string";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	@Override
	public String usage() {
		return "GetSource(Navajo n)";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (pp == null) {
			return null;
		}
		if (!(pp instanceof Navajo)) {
			throw new TMLExpressionException(this, "Invalid operand: "
					+ pp.getClass().getName());
		}
		Navajo n = (Navajo) pp;
		StringWriter sw = new StringWriter();
		try {
			n.write(sw);
			sw.flush();
			sw.close();
			return sw.toString();
		} catch (Exception e) {
			logger.error("Error: ",e);
			throw new TMLExpressionException("Error writing navajo: ", e);
		}
	}

}
