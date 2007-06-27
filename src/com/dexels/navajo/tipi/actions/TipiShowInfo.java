package com.dexels.navajo.tipi.actions;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.Operand;
import java.lang.reflect.*;

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
//    String txt = getParameter("text").getValue();
    final Operand op = getEvaluatedParameter("text", event);

    final String txt = ((String)op.value).replaceAll("\n", " ");
   myContext.showInfo(txt, "Info");
  }
}
