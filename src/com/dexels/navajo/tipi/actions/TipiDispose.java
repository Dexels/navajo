package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiDispose extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    try {
      TipiComponent tp = (TipiComponent)evaluate(getParameter("path").getValue()).value;
      if (tp!=null) {
        System.err.println("ATTEMPTING TO DISPOSE: "+tp.getPath());
      }

//      TipiPathParser tp = new TipiPathParser( myComponent, myContext, path);
      myContext.disposeTipiComponent( (TipiComponent) (tp));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}