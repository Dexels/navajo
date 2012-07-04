package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;

public class CallService extends FunctionInterface {

	private final static Logger logger = LoggerFactory
			.getLogger(EvaluateExpression.class);
	
	@Override
	public String remarks() {
		return "Calls a Navajo Service using local Navajo Server context and available Navajo Request object.";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		Navajo input = null;
		Operand result = null;
		String serviceName = null;
		String expression = null;
		
		if ( getNavajo() == null ) {
			throw new TMLExpressionException("No Navajo Request object available.");
		}
		if ( getOperands().size() != 2 ) {
			throw new TMLExpressionException("Invalid number of parameters.");
		}
		if ( !(getOperand(0) instanceof String) ) {
			throw new TMLExpressionException("Invalid service name defined.");
		}
		if ( !(getOperand(1) instanceof String) ) {
			throw new TMLExpressionException("Invalid expression defined.");
		}
		input = getNavajo();
		serviceName = (String) getOperand(0);
		expression = (String) getOperand(1);
		

		try {
			
			Navajo response = null;
			if ( input.getNavajo(serviceName) != null ) {
				response = input.getNavajo(serviceName);
			} else {
				DispatcherInterface dispatcher = DispatcherFactory.getInstance();
				input.getHeader().setRPCName(serviceName);
				response = dispatcher.handle(input, true);
				input.addNavajo(serviceName, response);
			}
			
			result = Expression.evaluate(expression, response, null, null);
			
		}
		catch (Exception ex) {
			logger.error("Error: ", ex);
		}
		
		if ( result != null ) {
			return result.value;
		} else {
			return null;
		}
	}

}
