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
		Object oo = getOperand(0);
		if(!(oo instanceof VaadinTipiContext)) {
			throw new TMLExpressionException("CreateEchoUrl: param one should be an VaadinTipiContext");
		}
		String mime = null;
		if(getOperands().size()>2) {
			mime = (String) getOperand(2);
		}
		VaadinTipiContext ee = (VaadinTipiContext)getOperand(0);
		String expression = (String)getOperand(1);
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
