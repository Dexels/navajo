package com.dexels.navajo.tipi.actions;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/** @todo Refactor, move to NavajoSwingTipi */
public class TipiShowInfo
    extends TipiAction {
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    String txt = getParameter("text").getValue();
    Operand o = null;
    try {
      o = evaluate(txt,event);
    }
    catch (Exception ex) {
      System.err.println("Error evaluating[" + txt + "] inserting as plain text only");
      ex.printStackTrace();
    }
    JOptionPane.showMessageDialog( (Component) myContext.getTopLevel(), o.value, "Info", JOptionPane.PLAIN_MESSAGE);
  }
}
