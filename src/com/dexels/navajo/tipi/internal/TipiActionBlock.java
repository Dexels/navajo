package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiActionBlock
    implements TipiExecutable {
  protected TipiComponent myComponent = null;
//  protected Map myParams = new HashMap();
  private final ArrayList myExecutables = new ArrayList();
  private String myExpression = "";
  private String myExpressionSource = "";
  private TipiActionBlock myActionBlockParent = null;
  private TipiEvent myEvent = null;
  private boolean conditionStyle = false;
  private final TipiContext myContext;
  public TipiActionBlock(TipiContext tc) {
    myContext = tc;
  }

  public String getExpression() {
    return myExpression;
  }

  public String getExpressionSource() {
    return myExpressionSource;
  }

  public void setExpression(String ex) {
    myExpression = ex;
  }

  public void setExpressionSource(String exs) {
    myExpressionSource = exs;
  }

  public void performAction() throws TipiBreakException, TipiException {
    boolean evaluated = checkCondition();
    try {
      myContext.performedBlock(myComponent, this, myExpression, myExpressionSource, evaluated);
    }
    catch (BlockActivityException ex1) {
      System.err.println("Blocked exception");
      return;
    }
    if (!evaluated) {
      return;
    }
    for (int i = 0; i < myExecutables.size(); i++) {
      TipiExecutable current = (TipiExecutable) myExecutables.get(i);
      try {
        current.performAction();
      }
      catch (TipiBreakException ex) {
        System.err.println("Break encountered!");
        return;
      }
    }
  }

  public void loadConditionStyle(XMLElement elm, TipiComponent parent, TipiEvent event) {
    conditionStyle = true;
    myComponent = parent;
    if (elm.getName().equals("condition")) {
      Vector temp = elm.getChildren();
      for (int i = 0; i < temp.size(); i++) {
        XMLElement current = (XMLElement) temp.elementAt(i);
        if (current.getName().equals("param")) {
          String name = (String) current.getAttribute("name");
          String value = (String) current.getAttribute("value");
//           myParams.put(name, value);
          if ("tipipath".equals(name)) {
            myExpressionSource = value;
          }
          if ("expression".equals(name)) {
            myExpression = value;
          }
        }
        if ("action".equals(current.getName())) {
          try {
            TipiAction ta = myContext.instantiateTipiAction(current, parent, event);
            myExecutables.add(ta);
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      }
    }
  }

//  public boolean evaluate(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException, TipiException {
//    return evaluateCondition(context, source);
//  }
  public void setEvent(TipiEvent event) {
    myEvent = event;
  }

  public void setTipiActionBlockParent(TipiActionBlock te) {
    myActionBlockParent = te;
  }

  private boolean evaluateBlock(TipiContext context, Object source) throws TipiException {
    boolean valid = false;
    Operand o;
//    TipiPathParser pp = new TipiPathParser((TipiComponent)source, context, myExpressionSource);
//    TipiComponent sourceComponent = pp.getComponent();
//    context.setCurrentComponent( (TipiComponent) source);
    try {
      if ( (TipiComponent) source != null) {
        o = Expression.evaluate(myExpression, ( (TipiComponent) source).getNearestNavajo(), null, null, null, (TipiComponent) source);
        if (o.value.toString().equals("true")) {
          return true;
        }
      }
      else {
        o = Expression.evaluate(myExpression, null, null, null, null, (TipiComponent) source);
        if (o.value.toString().equals("true")) {
          return true;
        }
      }
    }
    catch (TMLExpressionException ex) {
      ex.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

  /**
   * @deprecated
   */
  private boolean evaluateCondition(TipiContext context, Object source) throws TipiException {
    boolean valid = false;
    Operand o;
    TipiPathParser pp = new TipiPathParser( (TipiComponent) source, context, myExpressionSource);
    TipiComponent sourceComponent = pp.getComponent();
//    context.setCurrentComponent( (TipiComponent) source);
    if (pp.getPathType() == pp.PATH_TO_TIPI) {
      if (sourceComponent != null) {
        try {
          o = Expression.evaluate(myExpression, sourceComponent.getNearestNavajo(), null, null, null, (TipiComponent) source);
          if (o.value.toString().equals("true")) {
            valid = true;
          }
        }
        catch (TMLExpressionException ex) {
          ex.printStackTrace();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      else {
        System.err.println("ERROR: --------------------------> Could not find source tipi, returning FALSE");
        System.err.println("Expression: " + myExpression);
        System.err.println("From path: " + myExpressionSource);
        valid = false;
      }
    }
    else if (pp.getPathType() == pp.PATH_TO_MESSAGE) {
      Message m = pp.getMessage();
      if (sourceComponent != null && m != null) {
        try {
          // Use a copy of the Message. ArrayMessages remain a little tricky
          Navajo n = NavajoFactory.getInstance().createNavajo();
          Message bert = m.copy(n);
          n.addMessage(bert);
          o = Expression.evaluate(myExpression, n, null, bert, null, (TipiComponent) source);
          if (o.value.toString().equals("true")) {
            valid = true;
          }
        }
        catch (TMLExpressionException ex) {
          ex.printStackTrace();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      else {
        System.err.println("ERROR: --------------------------> Could not find source tipi for MESSAGE, ignoring condition");
        valid = true;
      }
    }
    else {
      throw new TipiException("Cannot put a condition on a component or property source");
    }
    return valid;
  }

  public TipiEvent getEvent() {
    return myEvent;
  }

  public void load(XMLElement elm, TipiComponent parent, TipiEvent event) {
    conditionStyle = false;
    myComponent = parent;
    myEvent = event;
    if (elm.getName().equals("block")) {
      myExpression = (String) elm.getAttribute("expression");
      myExpressionSource = (String) elm.getAttribute("source");
      Vector temp = elm.getChildren();
      parseActions(temp);
//      for(int i=0;i<temp.size();i++){
//        XMLElement current = (XMLElement)temp.elementAt(i);
//        if(current.getName().equals("param")){
//          String name = (String)current.getAttribute("name");
//          String value = (String)current.getAttribute("value");
//           myParams.put(name, value);
//        }
//      }
    }
  }

//  public abstract boolean evaluate(Navajo n, TipiContext context, Object source, Object event) throws TipiBreakException, TipiException;
  public XMLElement store() {
    XMLElement cond = new CaseSensitiveXMLElement();
    cond.setName("block");
//    Iterator it = myParams.keySet().iterator();
    if (!myExpression.equals("")) {
      cond.setAttribute("expression", myExpression);
    }
    if (!myExpressionSource.equals("")) {
      cond.setAttribute("source", myExpressionSource);
    }
    for (int i = 0; i < myExecutables.size(); i++) {
      TipiExecutable current = (TipiExecutable) myExecutables.get(i);
      XMLElement parm = current.store();
//      parm.setName("param");
      cond.addChild(parm);
    }
    return cond;
  }

  public void appendTipiExecutable(TipiExecutable tp) {
    myExecutables.add(tp);
  }

  public boolean checkCondition() throws TipiException, TipiBreakException {
    if (myExpression == null || myExpression.equals("")) {
      return true;
    }
    if (conditionStyle) {
      return evaluateCondition(myContext, myComponent);
    }
    else {
      return evaluateBlock(myContext, myComponent);
    }
  }

  public void parseActions(Vector v) {
//    TipiActionBlock currentBlock = parentBlock;
    try {
      for (int i = 0; i < v.size(); i++) {
        XMLElement current = (XMLElement) v.elementAt(i);
        if (current.getName().equals("action")) {
//          currentBlock.parseActions(v,context,myComponent);
          TipiAction action = myContext.instantiateTipiAction(current, myComponent, myEvent);
          action.setActionBlock(this);
          appendTipiExecutable(action);
//          myActions.add(action);
        }
//        if (current.getName().equals("condition")) {
//          TipiCondition con = context.instantiateTipiCondition(current, myComponent, this);
//          parseActions(current.getChildren(), context, con);
//        }
        if (current.getName().equals("block")) {
          TipiActionBlock con = myContext.instantiateTipiActionBlock(current, myComponent, myEvent);
          con.parseActions(current.getChildren());
          con.setTipiActionBlockParent(this);
          appendTipiExecutable(con);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void appendExecutable(TipiExecutable a) {
    myExecutables.add(a);
  }

  public void removeExecutable(TipiExecutable a) {
    myExecutables.remove(a);
  }

  public void moveExecutableUp(TipiAction action) {
    int index_old = myExecutables.indexOf(action);
    if (index_old > 0) {
      myExecutables.remove(action);
      myExecutables.add(index_old - 1, action);
    }
  }

  public void moveExecutableDown(TipiAction action) {
    int index_old = myExecutables.indexOf(action);
    if (index_old < myExecutables.size() - 1) {
      myExecutables.remove(action);
      myExecutables.add(index_old + 1, action);
    }
  }

  public TipiActionBlock getActionBlockParent() {
    return myActionBlockParent;
  }

//  public TreeNode getChildAt(int parm1) {
//    return  (TreeNode)myExecutables.get(parm1);
//  }
//  public int getChildCount() {
//    return myExecutables.size();
//  }
//  public TreeNode getParent() {
//    if (myActionBlockParent!=null) {
//      return myActionBlockParent;
//    } else {
//      return myEvent;
//    }
//  }
//
//  public int getIndex(TreeNode parm1) {
//    return -1;
//  }
//  public boolean getAllowsChildren() {
//    return true;
//  }
//  public boolean isLeaf() {
//    return myExecutables.size()==0;
//  }
//  public Enumeration children() {
//    return new Vector(myExecutables).elements();
//  }
//  public void addAction(TipiAction ta) {
//    myActions.add(ta);
//  }
  public int getExecutableChildCount() {
    return myExecutables.size();
  }

  public TipiExecutable getExecutableChild(int index) {
    return (TipiExecutable) myExecutables.get(index);
  }
}
