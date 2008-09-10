/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.connectors.*;

/**
 * @author frank
 * 
 */
public class GetConnectorInit extends FunctionInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "Retrieves the init function of a connector";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		return "GetConnectorInit(TipiComponent source, String connectorId)";
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
		if (!(pp instanceof TipiComponent)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
		}
		Object o = getOperand(1);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {

		} else {
			throw new TMLExpressionException(this, "Invalid operand: " + o.getClass().getName());
		}
		TipiComponent tc = (TipiComponent) pp;
		String connectorId = (String) o;
		TipiConnector tt = tc.getContext().getConnector(connectorId);
		return tt.getDefaultEntryPoint();
	}

}
