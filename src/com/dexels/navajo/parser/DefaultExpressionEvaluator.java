package com.dexels.navajo.parser;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.mapping.*;
import java.util.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultExpressionEvaluator
    implements ExpressionEvaluator {
  public DefaultExpressionEvaluator() {
  }

  public Operand evaluate(String clause, Navajo inMessage,
                          Object mappableTreeNode, Message parent) throws
      NavajoException {
    try {
      return Expression.evaluate(clause, inMessage,
                                 (MappableTreeNode) mappableTreeNode, parent);
    }
    catch (Exception ex) {
      throw NavajoFactory.getInstance().createNavajoException(ex);
    }
  }

  public Operand evaluate(String clause, Navajo inMessage) throws
      NavajoException {
    try {
      return Expression.evaluate(clause, inMessage);
    }
    catch (Exception ex) {
      throw NavajoFactory.getInstance().createNavajoException(ex);
    }
  }

  public Message match(String matchString, Navajo inMessage,
                       Object mappableTreeNode, Message parent) throws
      NavajoException {
    try {
      return Expression.match(matchString, inMessage,
                              (MappableTreeNode) mappableTreeNode, parent);
    }
    catch (Exception ex) {
      throw NavajoFactory.getInstance().createNavajoException(ex);
    }
  }

  public void updateExpressions(Navajo n) throws NavajoException {

  }

  private List getExpressionList(Navajo n) throws NavajoException {
    List expressionList = new ArrayList();
    ArrayList a = n.getAllMessages();
    for (int i = 0; i < a.size(); i++) {
      Message current = (Message) a.get(i);
      getExpressionList(n, current, expressionList);
    }
    return expressionList;
  }

  private void getExpressionList(Navajo n, Message m, List expressionList) throws
      NavajoException {
    ArrayList a = m.getAllMessages();
    for (int i = 0; i < a.size(); i++) {
      Message current = (Message) a.get(i);
      getExpressionList(n, current, expressionList);
    }
    ArrayList b = m.getAllProperties();
    for (int i = 0; i < b.size(); i++) {
      Property current = (Property) b.get(i);
      if (current.getType().equals(Property.EXPRESSION_PROPERTY)) {
        expressionList.add(current);
      }
    }
  }

  private List getExpressionDependencies(Navajo n, Property p,
                                         List expressionList) throws
      NavajoException {
    System.err.println("Type: " + p.getType() + " value: " + p.getValue());
    if (!p.getType().equals(Property.EXPRESSION_PROPERTY)) {
      throw new UnsupportedOperationException();
    }
    String expression = p.getValue();
    return getExpressionDependencies(expression, n, p.getParentMessage(),
                                     expressionList);
  }

  private List getExpressionDependencies(String expression, Navajo n,
                                         Message parent, List expressionList) {
    if (expression==null) {
      return null;
    }
    System.err.println("Checking deps for expression: "+expression);
    int startIndex = expression.indexOf('[');
    int endIndex = expression.indexOf(']');
    if (startIndex == -1) {
      // no references to properties, so no dependencies
      return null;
    }
    if (endIndex == -1) {
      throw new IllegalArgumentException("Unbalanced brackets: " + expression);
    }
    String tml = expression.substring(startIndex + 1, endIndex);
    String rest = expression.substring(endIndex + 1);
    System.err.println("tml expression found: " + tml);
    List restList = getExpressionDependencies(rest, n, parent, expressionList);
    if (restList == null) {
      restList = new ArrayList();
    }
    Property pp = parent.getProperty(tml);
    if (pp != null && pp.getType().equals(Property.EXPRESSION_PROPERTY)) {
      restList.add(pp);
    }
    return restList;

  }

//  private boolean dependsOn(Property expressionProperty, Property target) {
//
//  }
//

  private void processRefreshQueue(List queue) throws NavajoException {
    System.err.println("# of items in queue: "+queue.size());
    for (int i = 0; i < queue.size(); i++) {
      Property current = (Property) queue.get(i);

      try {
        System.err.print("Refreshing expression. Name: "+current.getName()+" expr: "+current.getValue()+" resolved to: ");
        current.refreshExpression();
        System.err.println("VALUE: "+current.getTypedValue());
      }
      catch (NavajoException ex) {
        System.err.println("Exception while processing refresh queue. Continuing.");
      }
      System.err.println("Property: " + current.getFullPropertyName() +
                         " resolved to: " + current.getTypedValue());
    }
  }

  public void processRefreshQueue(Map depMap) throws NavajoException {
    processRefreshQueue(createUpdateQueue(depMap));
    System.err.println("processed refresh queue");
  }

  private Map getExpressionDependencyMap(Navajo n, List exprList) throws
      NavajoException {
    Map depMap = new HashMap();
    for (int i = 0; i < exprList.size(); i++) {
      Property current = (Property) exprList.get(i);
      List l = getExpressionDependencies(n, current, exprList);
      depMap.put(current, l);
    }
    return depMap;
  }

  private Map duplicateDependencyMap(Map original) {
//    Map copy = new HashMap();
//    for (Iterator iter = original.keySet().iterator(); iter.hasNext(); ) {
//      Property item = (Property)iter.next();
//      ArrayList orig = (ArrayList)original.get(item);
//      if (orig==null) {
//        copy.put(item,null);
//      } else {
//      copy.put(item,orig.clone());
//      }
//    }
//    return copy;
    return original;
  }

  public Map createDependencyMap(Navajo n) throws NavajoException {
    List l = getExpressionList(n);
    return getExpressionDependencyMap(n, l);
  }

  private List createUpdateQueue(Map mm) {

    Map dependencyMap = duplicateDependencyMap(mm);
    Set propKeys = dependencyMap.keySet();


    List queue = new ArrayList();
    while (dependencyMap.size() > 0) {
      boolean found = false;
      for (Iterator iter = propKeys.iterator(); iter.hasNext(); ) {
        Property item = (Property) iter.next();
        List deps = (List) dependencyMap.get(item);
        if (deps == null) {
          try {
            System.err.println("No deps. Adding property to queue: " +
                               item.getFullPropertyName());
          }
          catch (NavajoException ex) {
            ex.printStackTrace();
          }
          queue.add(item);
          dependencyMap.remove(item);
          found = true;
          break;
        }
        if (queue.containsAll(deps)) {
          try {
            System.err.println("Adding property to queue: " +
                               item.getFullPropertyName());
          }
          catch (NavajoException ex) {
            ex.printStackTrace();
          }
          queue.add(item);
          dependencyMap.remove(item);
          found = true;
          break;
        }
      }
      if (!found) {
        throw new RuntimeException("Cyclic expression dependency found");
      }
      System.err.println("End of for. Size: " + dependencyMap.size());
    }
    return queue;
  }

  public static void main(String[] args) throws Exception {

    System.setProperty("com.dexels.navajo.DocumentImplementation",
                       "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
    DefaultExpressionEvaluator dee = new DefaultExpressionEvaluator();
    NavajoFactory.getInstance().setExpressionEvaluator(dee);
    Navajo n = NavajoFactory.getInstance().createNavajo();
    Message aap = NavajoFactory.getInstance().createMessage(n, "aap");
    n.addMessage(aap);
    Property noot = NavajoFactory.getInstance().createProperty(n, "noot",
        Property.INTEGER_PROPERTY, "4", 0, "", Property.DIR_IN);
    Property mies = NavajoFactory.getInstance().createProperty(n, "mies",
        Property.INTEGER_PROPERTY, "3", 0, "", Property.DIR_IN);
    Property wim = NavajoFactory.getInstance().createProperty(n, "wim",
        Property.EXPRESSION_PROPERTY, "[noot]+[mies]", 0, "", Property.DIR_IN);
    Property zus = NavajoFactory.getInstance().createProperty(n, "zus",
        Property.EXPRESSION_PROPERTY, "[wim]+[noot]", 0, "", Property.DIR_IN);
    Property jet = NavajoFactory.getInstance().createProperty(n, "jet",
        Property.EXPRESSION_PROPERTY, "[zus]+[noot]", 0, "", Property.DIR_IN);
    Property teun = NavajoFactory.getInstance().createProperty(n, "teun",
        Property.EXPRESSION_PROPERTY, "[jet]+[noot]", 0, "", Property.DIR_IN);

    aap.addProperty(teun);
    aap.addProperty(zus);
    aap.addProperty(noot);
    aap.addProperty(wim);
    aap.addProperty(jet);
    aap.addProperty(mies);

//    System.err.println("Wim typed: " + wim.getTypedValue());
//    n.refreshExpression();
//    System.err.println("After refresh: " + wim.getTypedValue());
    n.write(System.err);
    List exprList = dee.getExpressionList(n);
    System.err.println("\n::: " + exprList);
    for (int i = 0; i < exprList.size(); i++) {
      Property current = (Property) exprList.get(i);
      List l = dee.getExpressionDependencies(n, current, exprList);
      System.err.println("Property: " + current.getFullPropertyName() +
                         " depends on: ");
      if (l != null) {
        for (int j = 0; j < l.size(); j++) {
          System.err.println("     Property: " +
                             ( (Property) l.get(j)).getFullPropertyName());
        }

      }
    }

    System.err.println("Creating depmap:");
    Map depMap = dee.getExpressionDependencyMap(n, exprList);

    List queue = dee.createUpdateQueue(depMap);
    System.err.println("Queue ============");
    for (int i = 0; i < queue.size(); i++) {
      Property current = (Property) queue.get(i);
      System.err.println("Item: " + current.getFullPropertyName());
    }
    System.err.println("End of queue =====");
    dee.processRefreshQueue(queue);
  }

}
