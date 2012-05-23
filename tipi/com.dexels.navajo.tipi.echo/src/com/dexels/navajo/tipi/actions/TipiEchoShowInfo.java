package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

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

	private static final long serialVersionUID = 6946313514268068467L;

	public TipiEchoShowInfo() {
    }

    protected void execute(TipiEvent e) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
        Operand text = getEvaluatedParameter("text", e);
        myContext.showInfo(""+text.value, "Info");

    }

}
