package com.dexels.navajo.tipi.actions;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiShowQuestion
    extends TipiAction {
  public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
    Object[] options = {
        "Ja", "Nee"};
    String txt = getParameter("text").getValue();
    Operand o = null;
    try {
      o = evaluate(txt,event);
    }
    catch (Exception ex) {
//      System.err.println("Error evaluating[" + txt + "] inserting as plain text only");
      ex.printStackTrace();
    }
    int response = JOptionPane.showOptionDialog( (Component) myContext.getTopLevel(), o.value, "Vraag", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    if (response != 0) {
      throw new TipiBreakException();
    }
  }
}
