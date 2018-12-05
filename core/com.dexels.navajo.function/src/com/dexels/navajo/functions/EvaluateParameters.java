package com.dexels.navajo.functions;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Expression;

public class EvaluateParameters extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(EvaluateParameters.class);

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException("Wrong number of arguments");
		}

		Message currentMessage = this.getCurrentMessage();
		String expression = (String) getOperand(0);

		logger.debug("input: {} ", expression);
		String result = "";
		StringTokenizer tok = new StringTokenizer(expression, "[");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			logger.debug("token: {}", token);
			if (token.indexOf("]") > 0) {
				String property = token.substring(0, token.indexOf("]"));
				String value = property;
				logger.debug("Property: {}", property);
				if (currentMessage != null) {
					if(currentMessage.getProperty(property) != null){
						value = currentMessage.getProperty(property).getValue();
					}
				}else{
					if(inMessage.getProperty(property) != null){
						value = inMessage.getProperty(property).getValue();
					}
				}
				result += value;
				if (token.indexOf("]") < token.length() - 1) {
					result += token.substring(token.indexOf("]") + 1);
				}
			} else {
				result += token;
			}
		}
		return result;
	}

	@Override
	public String remarks() {
		return "EvaluateParameters(String)";
	}

	public static void main(String[] args) throws Exception {
//		Navajo n = NavajoFactory.getInstance().createNavajo();
//		Message noot = NavajoFactory.getInstance().createMessage(n, "Noot");
//		
//		n.addMessage(noot);
//		noot.addProperty(ai);
		
		String expression = "test [/mies/ActivityId]";
		Navajo m = NavajoFactory.getInstance().createNavajo();
		Message mies = NavajoFactory.getInstance().createMessage(m, "mies");
		Property ai = NavajoFactory.getInstance().createProperty(m,	"ActivityId", "string", "4792834", 10 , "Ac", "in");
		mies.addProperty(ai);
	    Property p = NavajoFactory.getInstance().createProperty(m,	"exp", "string", expression, 10 , "Ac", "in");
		
		m.addMessage(mies);
		mies.addProperty(p);		
		
		System.err.println(Expression.evaluate("EvaluateParameters([/mies/exp])", m).value);
		
		/*
		EvaluateParameters ce = new EvaluateParameters();
		ce.reset();
		ce.currentMessage = noot;	
		ce.insertOperand(Expression.evaluate("[/mies/exp]", m).value);
		String result = (String) ce.evaluate();
		System.err.println("result:");
		System.err.println(result);*/
	}

}
