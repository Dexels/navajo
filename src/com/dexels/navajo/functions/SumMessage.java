/*
 * Created on Apr 25, 2005
 *
 */
package com.dexels.navajo.functions;

import java.util.ArrayList;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.SystemException;
import com.sun.corba.se.spi.legacy.connection.*;

/**
 * @author arjen
 *
 */
public class SumMessage extends FunctionInterface {

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	public String remarks() {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#usage()
	 */
	public String usage() {
		 return "SumMessage(<Array message>,<expression>)";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	public Object evaluate() throws TMLExpressionException {
		 if (getOperands().size() < 2) {
		      for (int i = 0; i < getOperands().size(); i++) {
		        Object o = getOperands().get(i);
		        System.err.println("Operand # " + i + " is: " + o.toString() + " - " +
		                           o.getClass());
		      }
		      throw new TMLExpressionException(this,
		                                       "Wrong number of arguments: " +
		                                       getOperands().size());
		    }
		    if (! (getOperand(0) instanceof Message && getOperand(1) instanceof String)) {
		      throw new TMLExpressionException(this,
		                                       "Wrong argument types: " +
		                                       getOperand(0).getClass() + " and " +
		                                       getOperand(1).getClass());
		    }
		    Message message = (Message) getOperand(0);
		    String expression = (String) getOperand(1);
		    
		    Message parent = message;
		 
		      ArrayList arrayMsg = parent.getAllMessages();
		      if (arrayMsg == null) {
		        throw new TMLExpressionException(this,
		            "Empty or non existing array message: " + message);
		      }
		      String sumType = "int";
		      double sum = 0;
		      for (int i = 0; i < arrayMsg.size(); i++) {
		        Message m = (Message) arrayMsg.get(i);
//	        	Operand o = Expression.evaluate(expression, m.getRootDoc(), null, m);
		        Property ppp = m.getProperty(expression);
		        Object o = ppp.getTypedValue();
		        if (o == null) {
		        	continue;
		        }
	        	if (o instanceof Integer) {
	        		sum += ((Integer) o).doubleValue();
	        	} else if (o instanceof Double) {
	        		sum += ((Double) o).doubleValue();
	        	} else {
	        		throw new TMLExpressionException(this, "Incompatible type while summing: " + o);
	        	}
		      }
		      if (sumType.equals("int")) {
		        return new Integer( (int) sum);
		      }
		      else if (sumType.equals("money")) {
		        return new Money(sum);

		      }
		      else if (sumType.equals("percentage")) {
		        return new Percentage(sum);
		      }
		      else {
		        return new Double(sum);
		      }
		    
	}
		   
	

}
