package com.dexels.navajo.parser;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.mapping.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultExpressionEvaluator implements ExpressionEvaluator {
  public DefaultExpressionEvaluator() {
  }
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent) throws NavajoException {
    try {
      return Expression.evaluate(clause, inMessage,
                                 (MappableTreeNode) mappableTreeNode, parent);
    }
    catch (Exception ex) {
      throw NavajoFactory.getInstance().createNavajoException(ex);
    }
  }
  public Operand evaluate(String clause, Navajo inMessage) throws NavajoException {
    try {
    return Expression.evaluate(clause,inMessage);
  }
  catch (Exception ex) {
    throw NavajoFactory.getInstance().createNavajoException(ex);
  }
  }
  public Message match(String matchString, Navajo inMessage, Object mappableTreeNode, Message parent) throws NavajoException {
    try {
    return Expression.match(matchString,inMessage,(MappableTreeNode)mappableTreeNode,parent);
  }
  catch (Exception ex) {
    throw NavajoFactory.getInstance().createNavajoException(ex);
  }
  }
}
