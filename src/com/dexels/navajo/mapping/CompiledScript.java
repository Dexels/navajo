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

public abstract class CompiledScript {

  protected NavajoClassLoader classLoader;
  private final HashMap functions = new HashMap();

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