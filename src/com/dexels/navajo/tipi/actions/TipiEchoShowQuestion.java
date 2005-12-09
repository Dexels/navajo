package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiOptionPane;
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

public class TipiEchoShowQuestion extends TipiAction {
	public TipiEchoShowQuestion() {
	}

	protected void execute(TipiEvent e)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		Operand text = getEvaluatedParameter("text", e);
		// TipiScreen s = (TipiScreen) myContext.getDefaultTopLevel();
		// Window w = (Window) s.getTopLevel();
		// ContentPane old = w.getContent();
		TipiOptionPane.showQuestion(myContext, (String) text.value, "Info:",
				"Yes", "No");
	}

}
