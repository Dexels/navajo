package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.components.echoimpl.echo.*;
import nextapp.echo.*;
import com.dexels.navajo.tipi.components.echoimpl.*;
import com.dexels.navajo.parser.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoShowInfo extends TipiAction {
  public TipiEchoShowInfo() {
  }
  protected void execute() throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
    Operand text = getEvaluatedParameter("text");
//    dialog = new DialogPanel("A Sample Dialog");
//    dialog.setBackgroundDithered(true);
//    dialog.setLeft(50);
//    dialog.setTop(50);
    TipiOptionPane top = new TipiOptionPane();
    top.setText((String)text.value);
    // looks scary:
    TipiScreen s = (TipiScreen)myContext.getDefaultTopLevel();
    Window w = (Window)s.getTopLevel();
    AbstractPane old = w.getContent();
//    ContentPane nw = new ContentPane();
    old.add(top);
//    w.setContent(nw);
    top.setVisible(true);
    System.err.println("AAAAAAAAAAAAAAAAAAAAAAAP");
  }

}
