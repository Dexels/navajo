package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.components.echoimpl.EchoTipiContext;

public class CreateEchoUrl extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CreateEchoUrl.class);
	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException("CreateEchoUrl needs two parameters");
		}
		Object oo = getOperand(0);
		if(!(oo instanceof EchoTipiContext)) {
			throw new TMLExpressionException("CreateEchoUrl: param one should be an EchoTipiContext");
		}
		EchoTipiContext ee = (EchoTipiContext)getOperand(0);
		String expression = (String)getOperand(1);
		String result = ee.createExpressionUrl(expression);
		logger.info("Result: "+result);
		return result;
	}

	@Override
	public String remarks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String usage() {
		return(" CreateEchoUrl(context, object) returns a string pointing to the object.\nContext will usually be {context:/} ");
	}

}
