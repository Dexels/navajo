package com.dexels.navajo.tipi.actions;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiOpenBrowser extends TipiAction {

	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
		// TODO Auto-generated method stub
		Operand url = getEvaluatedParameter("url", event);
		Command brc = new BrowserOpenWindowCommand((String) url.value,"",null);
        ApplicationInstance.getActive().enqueueCommand(brc);
	}

}
