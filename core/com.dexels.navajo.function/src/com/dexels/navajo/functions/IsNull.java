package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.parser.FunctionInterface;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class IsNull extends FunctionInterface {
  public IsNull() {
  }
  public String remarks() {
    /**@todo Implement this com.dexels.navajo.parser.FunctionInterface abstract method*/
    return "Will return true if the supplied argument is null, false otherwise";
  }
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object arg = this.getOperands().get(0);
    if (arg==null) {
        return Boolean.TRUE;
    }
    if (arg instanceof NavajoType) {
        NavajoType n = (NavajoType)arg;
        return (n.isEmpty());
    }
    return Boolean.FALSE;
  }
  public String usage() {
    return "IsNull( <argument>)";
  }

}
