package com.dexels.navajo.tipi.actions;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiShowInfo
    extends TipiAction {
  public void execute() throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    String txt = getParameter("text").getValue();
    Operand o = null;
    try {
      o = evaluate(txt);
    }
    catch (Exception ex) {
      System.err.println("Error evaluating[" + txt + "] inserting as plain text only");
      ex.printStackTrace();
    }
    JOptionPane.showMessageDialog( (Component) myContext.getTopLevel(), o.value, "Info", JOptionPane.PLAIN_MESSAGE);
  }
}