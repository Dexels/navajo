package com.dexels.navajo.parser;

import com.dexels.navajo.document.*;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.Dispatcher;

import java.util.*;

import java.io.PrintWriter;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public final class DefaultExpressionEvaluator
    implements ExpressionEvaluator {

  private long lastTime = 0;

  public DefaultExpressionEvaluator() {
    lastTime = System.currentTimeMillis();
  }

  private void printStamp(String message) {
    long newTime = System.currentTimeMillis();
//    System.err.println("Event: "+message+"Diff: "+(newTime-lastTime));
    lastTime = newTime;
  }

  public final Operand evaluate(String clause, Navajo inMessage,
                                Object mappableTreeNode, Message parent) throws
      NavajoException {
//    System.err.println("Evaluating: "+clause);
    if (parent != null) {
//      System.err.println("Inmessage info: "+parent.getIndex()+" type: "+parent.getType()+" name: "+parent.getName());

    }
    try {
      return Expression.evaluate(clause, inMessage,
                                 (MappableTreeNode) mappableTreeNode, parent);
    }
    catch (Throwable ex) {
      throw NavajoFactory.getInstance().createNavajoException("Parse error: " +
          ex.getMessage() + "\n while parsing: " + clause);
    }
  }

  public final Operand evaluate(String clause, Navajo inMessage) throws
      NavajoException {
    try {
      return Expression.evaluate(clause, inMessage);
    }
    catch (Throwable ex) {

      throw NavajoFactory.getInstance().createNavajoException("Parse error: " +
          ex.getMessage() + "\n while parsing: " + clause);
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

  public final void updateExpressions(Navajo n) throws NavajoException {

  }

  private final List getExpressionList(Navajo n) throws NavajoException {
    List expressionList = new ArrayList();
    ArrayList a = n.getAllMessages();
    for (int i = 0; i < a.size(); i++) {
      Message current = (Message) a.get(i);
      getExpressionList(n, current, expressionList);
    }
    return expressionList;
  }

  private final void getExpressionList(Navajo n, Message m, List expressionList) throws
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

  private final List getExpressionDependencies(Navajo n, Property p,
                                               List expressionList) throws
      NavajoException {
//    System.err.println("Type: " + p.getType() + " value: " + p.getValue());
    if (!p.getType().equals(Property.EXPRESSION_PROPERTY)) {
      throw new UnsupportedOperationException();
    }
    String expression = p.getValue();
    return getExpressionDependencies(expression, n, p.getParentMessage());
  }

  private final List getExpressionDependencies(String expression, Navajo n,
                                               Message parent) {
    if (expression == null) {
      return null;
    }
//    System.err.println("Checking deps for expression: "+expression);
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
//    System.err.println("tml expression found: " + tml);
    List restList = getExpressionDependencies(rest, n, parent);
    if (restList == null) {
      restList = new ArrayList();
    }
    Property pp = parent.getProperty(tml);
    if (pp != null /*&& pp.getType().equals(Property.EXPRESSION_PROPERTY)*/) {
      restList.add(pp);
    }
    return restList;

  }

//  private boolean dependsOn(Property expressionProperty, Property target) {
//
//  }
//

  private final List processRefreshQueue(List queue) throws NavajoException {
    Object o = null;
    Object p = null;
    List refreshQueue = null;
    for (int i = 0; i < queue.size(); i++) {
      Property current = (Property) queue.get(i);
      if (!(current instanceof com.dexels.navajo.document.base.BasePropertyImpl)) {
		System.err.println("WTF??! "+current.getClass());
	}
      o = ( (com.dexels.navajo.document.base.BasePropertyImpl) current).
          peekEvaluatedValue();
      try {
        current.refreshExpression();
    } catch (NavajoException e) {
        System.err.println("Expression failed: "+current.getValue());

    }
      p = ( (com.dexels.navajo.document.base.BasePropertyImpl) current).
          peekEvaluatedValue();
      if (o == null && p == null) {
        continue;
      }
      if (o == null || p == null) {
        if (refreshQueue == null) {
          refreshQueue = new ArrayList();
        }
        refreshQueue.add(current);
        continue;
      }
//      if (o.equals(p)) {
//        continue;
//      } else {
      if (refreshQueue == null) {
        refreshQueue = new ArrayList();
      }
      refreshQueue.add(current);
//      }
    }
    if (refreshQueue == null) {
       refreshQueue = new ArrayList();
     }
    return refreshQueue;
  }

  public final List processRefreshQueue(Map depMap) throws NavajoException {
//    printStamp("Before processRefreshQueue: ");
    List updateQueue = createUpdateQueue(depMap);
    return processRefreshQueue(updateQueue);
//    System.err.println("processed refresh queue");
//    printStamp("After processRefreshQueue: ");
//    return updateQueue;
  }

//  public final void processRefreshQueue(Map depMap, Property p) throws
//      NavajoException {
//    printStamp("Before processRefreshQueue: ");
//    processRefreshQueue(createUpdateQueue(depMap));
//    System.err.println("processed refresh queue");
//    printStamp("After processRefreshQueue: ");
//  }

  private final Map getExpressionDependencyMap(Navajo n, List exprList) throws
      NavajoException {
    Map depMap = new HashMap();
    for (int i = 0; i < exprList.size(); i++) {
      Property current = (Property) exprList.get(i);
      List l = getExpressionDependencies(n, current, exprList);
      depMap.put(current, l);
    }
    return depMap;
  }

  private final Map duplicateDependencyMap(Map original) {
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
    printStamp("Creating depmap ");
    List l = getExpressionList(n);
    printStamp("expressionList finished ");
    Map mm = getExpressionDependencyMap(n, l);
    printStamp("createDependencyMap finished ");
    return mm;
  }

  public Map createInverseDependencyMap(Navajo n, List expressionList,
                                        Map dependencyMap) throws
      NavajoException {
    return null;
  }

  private final void getExpressionsDependingOn(Navajo n, List propertyList,
                                               Map dependencyMap,
                                               List outputList) throws
      NavajoException {
    ArrayList al = new ArrayList();
    for (int i = 0; i < propertyList.size(); i++) {
      Property current = (Property) propertyList.get(i);
      List deps = (List) dependencyMap.get(current);
      for (int j = 0; j < deps.size(); j++) {
        Property curProp = (Property) deps.get(i);

      }
      if (deps != null && deps.contains(current) &&
          !outputList.contains(current)) {
        propertyList.add(current);
      }
    }
//    return al;
  }

  private final void dumpDep(Property p, List deps, int indent,
                             Map dependencyMap, PrintWriter out) throws
      NavajoException {
    spaces(indent, out);
    if (deps == null) {
      out.println("Property: " + p.getFullPropertyName() + " <none>");
      return;
    }
    out.println("Property: " + p.getFullPropertyName() + " depends on: ");

    for (int i = 0; i < deps.size(); i++) {
//      spaces(indent+10);
      Property current = (Property) deps.get(i);
      List dd = (List) dependencyMap.get(current);
      dumpDep(current, dd, indent + 3, dependencyMap, out);
    }
  }

  private final void spaces(int l, PrintWriter out) {
    for (int i = 0; i < l; i++) {
      out.print(" ");
    }
  }

  private final List createUpdateQueue(Map mm) throws NavajoException {
    Map dependencyMap = duplicateDependencyMap(mm);
    Set propKeys = dependencyMap.keySet();
    List queue = new ArrayList();

//    PrintWriter pw = null;
//  try {
//    pw = new PrintWriter(new FileWriter("c:/depdump.txt"));
//  }
//  catch (IOException ex) {
//    ex.printStackTrace();
//  }
//
//    pw.println("Dumping depmap ==============================================");
//    for (Iterator iter = propKeys.iterator(); iter.hasNext(); ) {
//      Property item = (Property)iter.next();
//      List ll = (List)dependencyMap.get(item);
//
//      dumpDep(item,ll,0,dependencyMap, pw);
//    }
//    pw.println("End of depmap dump ==========================================");
//    pw.flush();
//    pw.close();
    
    Navajo first = null;
    while (dependencyMap.size() > 0) {
      boolean found = false;
      for (Iterator iter = propKeys.iterator(); iter.hasNext(); ) {
        Property item = (Property) iter.next();
        if (first==null) {
            first = item.getRootDoc();
        }
        if (!Property.EXPRESSION_PROPERTY.equals(item.getType())) {
          if (!queue.contains(item)) {
            queue.add(item);
          }
          dependencyMap.remove(item);
          found = true;
          break;
        }

        List deps = (List) dependencyMap.get(item);

        if (deps == null) {
          if (!queue.contains(item)) {
            queue.add(item);
          }
          dependencyMap.remove(item);
          found = true;
          break;
        }
        if (!containsExpressions(deps)) {
          if (!queue.contains(item)) {
            queue.add(item);
          }
          dependencyMap.remove(item);
          found = true;
          break;
        }

        if (queue.containsAll(deps)) {
          if (!queue.contains(item)) {
            queue.add(item);
          }
          dependencyMap.remove(item);
          found = true;
          break;
        }

        try {
          addExpressionToQueue(item, dependencyMap, queue);
          found = true;
          break;
        }
        catch (ExpressionDependencyException ex1) {
          System.err.println("Did not succeed adding. Continuing");
        }
//        for (int i = 0; i < deps.size(); i++) {
//
//        }
      }
      if (!found) {
        System.err.println("Arrrr shiver me timbers");
//        for (Iterator iter = dependencyMap.keySet().iterator(); iter.hasNext(); ) {
//          Property item = (Property) iter.next();
//            System.err.println("Depmap: Property: " + item.getFullPropertyName());
//            System.err.println("Deps: " + dependencyMap.get(item));
//        }
//        throw new RuntimeException("Cyclic expression dependency found");
      }
//      System.err.println("End of for. Size: " + dependencyMap.size());
    }
    
    // UUUUUUUUUUUGLY: MAKE SURE EVERY SINGLE ONE GETS ADDED
    if (first!=null) {
        queue.addAll(getExpressionList(first));
    }
    return queue;
  }

  private final void addExpressionToQueue(Property item, Map depMap, List queue) throws
      ExpressionDependencyException {
    List deps = (List) depMap.get(item);
    if (deps == null) {
      throw new RuntimeException("Huh?");
    }
    boolean problems = false;
    for (int i = 0; i < deps.size(); i++) {
      Property p = (Property) deps.get(i);
      List deps2 = (List) depMap.get(p);
      if (deps2 == null) {
        depMap.remove(p);
        if (!queue.contains(p)) {
          queue.add(p);
        }

        continue;
      }
      if (queue.containsAll(deps2)) {
        if (!queue.contains(p)) {
          queue.add(p);
        }
        depMap.remove(item);
        continue;
      }
      try {
        addExpressionToQueue(p, depMap, queue);
      }
      catch (ExpressionDependencyException ex) {
        problems = true;
      }
    }
    if (problems) {
      throw new ExpressionDependencyException();
    }

  }

  private final boolean containsExpressions(List expressionList) {
    for (int i = 0; i < expressionList.size(); i++) {
      Property current = (Property) expressionList.get(i);
      if (Property.EXPRESSION_PROPERTY.equals(current.getType())) {
        return true;
      }
    }
    return false;
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
        Property.EXPRESSION_PROPERTY, "ToMoney([zus]-[noot])", 0, "",
        Property.DIR_IN);
    Property teun = NavajoFactory.getInstance().createProperty(n, "teun",
        Property.EXPRESSION_PROPERTY, "[noot]", 0, "", Property.DIR_IN);

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
//    System.err.println("\n::: " + exprList);
    for (int i = 0; i < exprList.size(); i++) {
      Property current = (Property) exprList.get(i);
      List l = dee.getExpressionDependencies(n, current, exprList);
//      System.err.println("Property: " + current.getFullPropertyName() +
//                         " depends on: ");
      if (l != null) {
        for (int j = 0; j < l.size(); j++) {
//          System.err.println("     Property: " +
//                             ( (Property) l.get(j)).getFullPropertyName());
        }

      }
    }

//    System.err.println("Creating depmap:");
    Map depMap = dee.getExpressionDependencyMap(n, exprList);
    System.err.println("depMap = " + depMap);

    List queue = dee.createUpdateQueue(depMap);
//    System.err.println("Queue ============");
//    for (int i = 0; i < queue.size(); i++) {
//      Property current = (Property) queue.get(i);
//      System.err.println("Item: " + current.getFullPropertyName());
//    }
//    System.err.println("End of queue =====");
    dee.processRefreshQueue(queue);

    System.err.println("teun = " + teun.getTypedValue());
  }

  public ClassLoader getScriptClassLoader() {
	  return Dispatcher.getInstance().getNavajoConfig().getClassloader();
  }
}
