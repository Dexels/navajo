package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/** @todo Refactor, move to NavajoSwingTipi */
public class TipiShowQuestion extends TipiAction {

	int response = 0;

	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		final String[] options = { "Ja", "Nee" };
		Operand o = getEvaluatedParameter("text", event);
		if (o == null) {
			myContext.showInternalError("showQuestion requires 'text' param");
			return;
		}
		final String title = "Vraag";
		myContext.showQuestion((String) o.value, title, options);
	}
}
