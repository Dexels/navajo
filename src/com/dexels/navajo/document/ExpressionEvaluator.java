package com.dexels.navajo.document;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ExpressionEvaluator {
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent) throws NavajoException;
  public Operand evaluate(String clause, Navajo inMessage) throws NavajoException;
  public Message match(String matchString, Navajo inMessage, Object mappableTreeNode, Message parent) throws NavajoException;
  public Map createDependencyMap(Navajo n) throws NavajoException;
  public void processRefreshQueue(Map depMap) throws NavajoException;
}
