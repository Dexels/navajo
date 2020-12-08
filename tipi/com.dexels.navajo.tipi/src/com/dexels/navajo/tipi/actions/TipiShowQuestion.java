/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = -6263991376950108031L;
	int response = 0;

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
	    
	    String yesOption = (String) myContext.getLookupParser().lookup("Yes");
	    String NoOption = (String)  myContext.getLookupParser().lookup("No");
	    
		final String[] options = { yesOption, NoOption };
		Operand o = getEvaluatedParameter("text", event);
		if (o == null) {
			myContext.showInternalError("showQuestion requires 'text' param");
			return;
		}
		String title = (String) getEvaluatedParameterValue("title", event);
		if (title == null) {
			title = "Vraag";
		}
		try
		{
			myContext.showQuestion((String) o.value, title, options, event.getComponent());
		}
		catch(TipiBreakException tbe)
		{
			performTipiEvent("onNo", null, true);
			throw tbe;
		}
		// cannot be inside the try, otherwise a TBE inside the onYes event results in the onNo also being executed
		performTipiEvent("onYes", null, true);
	}
}
