package com.dexels.navajo.tipi.actions;

import java.util.*;

import javax.servlet.http.Cookie;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import nextapp.echo2.webcontainer.command.BrowserSetCookieCommand;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class TipiDumpClass extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		Map m = myContext.getTipiClassDefMap();
		for (Iterator iter = m.keySet().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			
		}
		Operand nameO = getEvaluatedParameter("name", event);
		Operand valueO = getEvaluatedParameter("value", event);
		
	}

}