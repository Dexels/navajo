package com.dexels.navajo.document;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Frank Lyaruu
 * @version $Id$
 */

public interface ExpressionEvaluator {
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent) throws NavajoException;
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Message currentParam, Object access) throws NavajoException;
  public Operand evaluate(String clause, Navajo inMessage) throws NavajoException;
  public Map<Property,List<Property>> createDependencyMap(Navajo n) throws NavajoException;
  public List<Property> processRefreshQueue(Map<Property,List<Property>> depMap) throws NavajoException;
}
