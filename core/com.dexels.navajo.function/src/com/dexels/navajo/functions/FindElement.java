package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class FindElement extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory.getLogger(FindElement.class);
	
  public FindElement() {
  }

  @Override
public String remarks() {
   return "Locates a array element with a property with a certain name and value";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
	 
	Message in = (Message) getOperand(0);
	String propertyName = (String) getOperand(1);
	Object propertyValue = getOperand(2);
	
	String type = in.getType();
	if(!Message.MSG_TYPE_ARRAY.equals(type)) {
		throw new TMLExpressionException("FindMessageWithProperty only works with array messages");
	}
	for (Message element : in.getAllMessages()) {
		Property p = element.getProperty(propertyName);
		if(p!=null) {
			if(propertyValue.equals(p.getTypedValue())) {
				return element;
			}
		}
	}
	logger.debug("No property found in message. Property name: "+propertyName+" value: "+propertyValue);
	return null;
  }

  @Override
public String usage() {
     return "FindMessageWithProperty(arraymessage, propertyName, propertyValue)";
  }

  public static void main(String [] args) throws Exception {
	  Navajo n = NavajoFactory.getInstance().createNavajo();
	  Message m = NavajoFactory.getInstance().createMessage(n, "TestArray", Message.MSG_TYPE_ARRAY);
	  m.addElement(NavajoFactory.getInstance().createMessage(n, "TestArray"));
  }
}
