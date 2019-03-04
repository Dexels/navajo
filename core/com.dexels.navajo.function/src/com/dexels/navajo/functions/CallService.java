package com.dexels.navajo.functions;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Expression;
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
		Operand result = null;
		String serviceName = getStringOperand(0);
		String expression = getOperands().size()==1 ? null : getStringOperand(1);
		
		if ( getNavajo() == null ) {
			throw new TMLExpressionException("No Navajo Request object available.");
		}
		if ( getOperands().size() > 2 ) {
			throw new TMLExpressionException("Invalid number of parameters.");
		}
		try {
			
			Navajo response = getNavajo().getNavajo(serviceName);
			if ( response == null ) {
				DispatcherInterface dispatcher = DispatcherFactory.getInstance();
				Navajo input = getNavajo().copy();
				input.getHeader().setRPCName(serviceName);
				response = dispatcher.handle(input, this.getAccess().getTenant(), true);
				
				getNavajo().addNavajo(serviceName, response);
			}
			
			if (expression == null)
			{
			      Binary bbb = new Binary();
			      OutputStream os = bbb.getOutputStream();
			      response.write(os);
			      os.flush();
			      os.close();
			      return bbb;
			}
			else
			{
				result = Expression.evaluate(expression, response);
			}
			
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
