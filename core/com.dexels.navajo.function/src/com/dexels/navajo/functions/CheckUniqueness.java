package com.dexels.navajo.functions;

import java.util.HashSet;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.SystemException;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class CheckUniqueness extends FunctionInterface {

  public String remarks() {
    return "Checks whether properties in an array message have unique values";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
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
    if (! (getOperand(0)instanceof String && getOperand(1)instanceof String)) {
      throw new TMLExpressionException(this,
                                       "Wrong argument types: " +
                                       getOperand(0).getClass() + " and " +
                                       getOperand(1).getClass());
    }
    String messageName = (String) getOperand(0);
    String propertyName = (String) getOperand(1);

    String filter = null;
    if (getOperands().size() > 2) {
      filter = (String) getOperand(2);
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
        boolean evaluate = (filter != null ? Condition.evaluate(filter, doc, null, m) : true);
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

  public String usage() {
    return "CheckUniqueness(<Array message name>,<Property name>[,<filter>])";
  }

}