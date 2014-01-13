package com.dexels.navajo.document;

import java.util.*;

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
  public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Message currentParam) throws NavajoException;
  public Operand evaluate(String clause, Navajo inMessage) throws NavajoException;
  public Message match(String matchString, Navajo inMessage, Object mappableTreeNode, Message parent) throws NavajoException;
  public Map<Property,List<Property>> createDependencyMap(Navajo n) throws NavajoException;
  public List<Property> processRefreshQueue(Map<Property,List<Property>> depMap) throws NavajoException;
  public ClassLoader getScriptClassLoader();
  public Comparator<Message> getComparator(String compareFunction);
}
