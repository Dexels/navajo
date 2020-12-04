/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.echo.actions;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiOpenBrowser extends TipiAction {

	private static final long serialVersionUID = -4683456247058589772L;

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		Operand url = getEvaluatedParameter("url", event);
		Operand newWindow = getEvaluatedParameter("newWindow", event);
		boolean newWin = true;
		if(newWindow!=null && newWindow.value!=null) {
			Boolean b = (Boolean) newWindow.value;
			newWin = b.booleanValue();
		}
		if(newWin) {
			Command brc = new BrowserOpenWindowCommand((String) url.value,"","directories=no,location=yes,menubar=yes,personalbar=yes,resizable=yes,scrollbars=yes,status=yes,titlebar=yes,toolbar=yes, width=640,height=480");
	        ApplicationInstance.getActive().enqueueCommand(brc);
		} else {
			Command brc = new BrowserRedirectCommand((String) url.value);
	        ApplicationInstance.getActive().enqueueCommand(brc);
		}
	}

}
