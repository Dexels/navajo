package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.components.echoimpl.*;
import nextapp.echo.*;
import com.dexels.navajo.tipi.components.echoimpl.echo.*;
import com.dexels.navajo.document.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoShowQuestion extends TipiAction {
  public TipiEchoShowQuestion() {
  }
  protected void execute(TipiEvent te) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
    Operand text = getEvaluatedParameter("text",te);
    TipiScreen s = (TipiScreen)myContext.getDefaultTopLevel();
    Window w = (Window)s.getTopLevel();
    AbstractPane old = w.getContent();
    TipiOptionPane.showQuestion(w,(String)text.value,"Info:","Yes","No");
  }

}
