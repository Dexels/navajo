package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
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
public class TipiSetPostman extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		// TODO Add support for multi-servers
		final Operand serv = getEvaluatedParameter("server", event);
		final Operand user = getEvaluatedParameter("username", event);
		final Operand pass = getEvaluatedParameter("password", event);
		NavajoClientFactory.resetClient();
		NavajoClientFactory.createDefaultClient();
		NavajoClientFactory.getClient().setServerUrl("" + serv.value);
		NavajoClientFactory.getClient().setUsername("" + user.value);
		NavajoClientFactory.getClient().setPassword("" + pass.value);
		System.err.println("Created new client pointing to: " + serv.value + " using username: " + user.value);
	}
}