package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.echoimpl.*;
import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.internal.*;
import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoShowInfo
    extends TipiAction {
  public TipiEchoShowInfo() {
  }

  protected void execute(TipiEvent e) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
    Operand text = getEvaluatedParameter("text", e);
    TipiScreen s = (TipiScreen) myContext.getDefaultTopLevel();
    Window w = (Window) s.getTopLevel();

    TipiOptionPane.showInfo(w, (String) text.value, "Info:", "Close");

  }

}
