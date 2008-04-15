package com.dexels.navajo.tipi.actions;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.webcontainer.command.*;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiOpenBrowser extends TipiAction {

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		Operand url = getEvaluatedParameter("url", event);
		Operand newWindow = getEvaluatedParameter("newWindow", event);
		boolean newWin = true;
		if(newWindow!=null && newWindow.value!=null) {
			Boolean b = (Boolean) newWindow.value;
			newWin = b.booleanValue();
		}
		if(newWin) {
			Command brc = new BrowserOpenWindowCommand((String) url.value,"",null);
	        ApplicationInstance.getActive().enqueueCommand(brc);
		} else {
			Command brc = new BrowserRedirectCommand((String) url.value);
	        ApplicationInstance.getActive().enqueueCommand(brc);
		}
	}

}
