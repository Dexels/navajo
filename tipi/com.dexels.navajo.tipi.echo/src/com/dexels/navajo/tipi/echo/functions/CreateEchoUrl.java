package com.dexels.navajo.tipi.echo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.tipi.components.echoimpl.EchoTipiContext;

public class CreateEchoUrl extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CreateEchoUrl.class);
	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException("CreateEchoUrl needs two parameters");
		}
		Object oo = operand(0).value;
		if(!(oo instanceof EchoTipiContext)) {
			throw new TMLExpressionException("CreateEchoUrl: param one should be an EchoTipiContext");
		}
		EchoTipiContext ee = (EchoTipiContext)oo;
		String expression = getStringOperand(1) ;
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
