package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.util.HashMap;
import java.util.Stack;

public abstract class CompiledScript {

  protected NavajoClassLoader classLoader;
  private final HashMap functions = new HashMap();

  public MappableTreeNode currentMap = null;
  public final Stack treeNodeStack = new Stack();
  public Navajo outDoc = null;
  public Navajo inDoc = null;
  public Message currentOutMsg = null;
  public final Stack outMsgStack = new Stack();
  public Message currentInMsg = null;
  public Selection currentSelection = null;
  public final Stack inMsgStack = new Stack();
  public Message parmMessage = null;
  public Object sValue = null;
  public Operand op = null;
  public String type = "";
  public String subtype = "";
  public Property p = null;
  public LazyArray la = null;
  public LazyMessageImpl lm = null;
  public String fullMsgName = "";
  public boolean matchingConditions = false;
  public HashMap evaluatedAttributes = null;
  public boolean inSelectionRef = false;
  public final Stack inSelectionRefStack = new Stack();
  public int count = 1;

  public void setClassLoader(NavajoClassLoader loader) {
    this.classLoader = loader;
    //System.out.println("in setClassLoader(): " + classLoader);
  }

  public abstract void execute(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception;

  /**
   * Pool for use of Navajo functions.
   *
   * @param name
   * @return
   */
  public final Object getFunction(String name) throws Exception {
    Object f = functions.get(name);
    if (f != null)
      return f;
    f = Class.forName(name, false, classLoader).newInstance();
    functions.put(name, f);
    return f;
  }

  public void finalize() {
    //System.out.println("FINALIZE() METHOD CALL FOR CompiledScript OBJECT " + this);
    functions.clear();
  }

}