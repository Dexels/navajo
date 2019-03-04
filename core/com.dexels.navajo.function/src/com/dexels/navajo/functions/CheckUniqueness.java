package com.dexels.navajo.functions;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.script.api.SystemException;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class CheckUniqueness extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory.getLogger(CheckUniqueness.class);

  @Override
public String remarks() {
    return "Checks whether properties in an array message have unique values";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    if (getOperands().size() < 2) {
      for (int i = 0; i < getOperands().size(); i++) {
        Object o = getOperands().get(i);
        logger.debug("Operand # " + i + " is: " + o.toString() + " - " +
                           o.getClass());
      }
      throw new TMLExpressionException(this,
                                       "Wrong number of arguments: " +
                                       getOperands().size());
    }
    if (! (operand(0).value instanceof String && operand(1).value instanceof String)) {
      throw new TMLExpressionException(this,
                                       "Wrong argument types: " +
                                       operand(0).value.getClass() + " and " +
                                       operand(1).value.getClass());
    }
    String messageName = getStringOperand(0);
    String propertyName = getStringOperand(1);

    String filter = null;
    if (getOperands().size() > 2) {
      filter = getStringOperand(2);
    }
    Message parent = getCurrentMessage();
    Navajo doc = getNavajo();
    boolean isUnique = true;

    HashSet<Object> values = new HashSet<Object>();
    try {
      List<Message> arrayMsg = (parent != null ? parent.getMessages(messageName) :
                            doc.getMessages(messageName));
      if (arrayMsg == null) {
        throw new TMLExpressionException(this,
            "Empty or non existing array message: " + messageName);
      }
      for (int i = 0; i < arrayMsg.size(); i++) {
        Message m = arrayMsg.get(i);
        Property p = m.getProperty(propertyName);
        boolean evaluate = (filter != null ? Condition.evaluate(filter, doc, null, m,getAccess()) : true);
        if (evaluate) {
          if (p != null) {
            Object o = null;
            if (p.getType().equals(Property.SELECTION_PROPERTY)) {
              o = p.getSelected().getValue();
            } else {
              o = p.getTypedValue();
            }
            if (values.contains(o)) {
              return Boolean.FALSE;
            } else {
              values.add(o);
            }
          }
        }
      }

    }
    catch (NavajoException ne) {
      throw new TMLExpressionException(this, ne.getMessage());
    }
    catch (SystemException se) {
      throw new TMLExpressionException(this, se.getMessage());
    }

   return (isUnique ? Boolean.TRUE : Boolean.FALSE);
  }

  @Override
public String usage() {
    return "CheckUniqueness(<Array message name>,<Property name>[,<filter>])";
  }

}