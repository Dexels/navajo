package com.dexels.navajo.document;

import com.dexels.navajo.server.*;
import com.dexels.navajo.parser.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ExpressionEvaluator {
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent) throws TMLExpressionException, SystemException;
  public Operand evaluate(String clause, Navajo inMessage) throws TMLExpressionException, SystemException;
  public Message match(String matchString, Navajo inMessage, Object mappableTreeNode, Message parent) throws TMLExpressionException, SystemException;
}
