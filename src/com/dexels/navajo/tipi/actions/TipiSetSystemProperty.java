package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.parser.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSetSystemProperty extends TipiAction {
  public TipiSetSystemProperty() {
  }
  protected void execute() throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
    Operand name = getEvaluatedParameter("name");
    Operand value = getEvaluatedParameter("value");
    if (name==null || value==null) {
      return;
    }
    System.setProperty(name.value.toString(),value.value.toString());
  }

}
