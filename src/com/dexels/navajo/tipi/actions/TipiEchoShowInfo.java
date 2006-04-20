package com.dexels.navajo.tipi.actions;

import nextapp.echo2.app.*;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiOptionPane;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.command.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoShowInfo extends TipiAction {
    public TipiEchoShowInfo() {
    }

    protected void execute(TipiEvent e) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
        Operand text = getEvaluatedParameter("text", e);

        // TipiOptionPane.showInfo(myContext, (String) text.value, "Info:",
        // "Close");
        ApplicationInstance.getActive().enqueueCommand(new JavaScriptEval("alert('" + text.value + "')"));

    }

}
