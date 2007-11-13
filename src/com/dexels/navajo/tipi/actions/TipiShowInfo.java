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
public class TipiShowInfo extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		// String txt = getParameter("text").getValue();
		final Operand op = getEvaluatedParameter("text", event);
		Object oo = op.value;
		String result = "";
		if(oo==null) {
			result = "Null value!";
		} else {
			result = oo.toString();
		}
		final String txt = (result).replaceAll("\n", " ");
		myContext.showInfo(txt, "Info");
	}
}
