package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiDebugOutput
    extends TipiAction {
  public TipiDebugOutput() {
  }

  protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
      Operand value = getEvaluatedParameter("value",event);
      System.err.println("******** DEBUG *********");
      if (value!=null) {
        System.err.println("VALUE: >"+value.value+"<");
        System.err.println("TYPE: >"+value.type+"<");
    } else {
        System.err.println("NULL EVALUATION!");
    }
      System.err.println("******** END *********");
  }
}
