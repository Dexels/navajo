package com.dexels.navajo.tipi.components.swingimpl.actions;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.document.*;
import java.awt.print.*;
import com.dexels.navajo.tipi.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiCreatePrintJob extends TipiAction {
  public TipiCreatePrintJob() {
  }
  protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
    /**@todo Implement this com.dexels.navajo.tipi.internal.TipiAction abstract method*/
    Operand globalvalue = getEvaluatedParameter("value",event);
    PrinterJob jb = PrinterJob.getPrinterJob();
    TipiReference tr = (TipiReference)globalvalue.value;
    if (jb.printDialog()) {
      tr.setValue(super.getParameter("value").getValue(),new Operand(jb,"object",null),myComponent);
    } else {
      System.err.println("Breaking on printjob!");
      throw new TipiBreakException();
    }

  }

}
