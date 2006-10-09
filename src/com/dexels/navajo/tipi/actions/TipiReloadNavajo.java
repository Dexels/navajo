package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiReloadNavajo extends TipiAction {
  public TipiReloadNavajo() {
  }
  protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
    Operand to = getEvaluatedParameter("to",event);
    Operand from  = getEvaluatedParameter("from",event);
    if (to==null || from ==null) {
      System.err.println("Null evaluation in TipiReloadNavajo");
    }
    TipiDataComponent toData = (TipiDataComponent)to.value;
    TipiDataComponent fromData = (TipiDataComponent)from.value;

    
    toData.loadData(fromData.getNearestNavajo(),myComponent.getContext(), toData.getCurrentMethod(),null);

  }

}
