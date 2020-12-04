/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;

public class CreateExpressionUrl extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CreateExpressionUrl.class);
	
	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() < 2) {
			throw new TMLExpressionException("CreateEchoUrl needs two parameters");
		}
		Object oo = operand(0).value;
		if(!(oo instanceof VaadinTipiContext)) {
			throw new TMLExpressionException("CreateEchoUrl: param one should be an VaadinTipiContext");
		}
		String mime = null;
		if(getOperands().size()>2) {
			mime = getStringOperand(2);
		}
		VaadinTipiContext ee = (VaadinTipiContext)operand(0).value;
		String expression = (String)getStringOperand(1);
		String result = ee.createExpressionUrl(expression,mime);
		logger.info("Expression result: "+result);
		return result;
	}

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public String usage() {
		return(" CreateEchoUrl(context, object) returns a string pointing to the object.\nContext will usually be {context:/} ");
	}

}
