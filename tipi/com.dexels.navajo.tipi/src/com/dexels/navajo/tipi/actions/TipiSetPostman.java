/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoClient;
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
	private static final long serialVersionUID = 649367657900764240L;

	private final static Logger logger = LoggerFactory
		.getLogger(TipiSetPostman.class);

	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// TODO Add support for multi-servers
		final Operand serv = getEvaluatedParameter("server", event);
		final Operand user = getEvaluatedParameter("username", event);
		final Operand pass = getEvaluatedParameter("password", event);
		final Operand token = getEvaluatedParameter("token", event);
		
		final Operand app = getEvaluatedParameter("application", event);
		final Operand org = getEvaluatedParameter("organization", event);
		// NavajoClientFactory.resetClient();
		// NavajoClientFactory.createDefaultClient();
		myContext.getClient().setServerUrl("" + serv.value);
		
		if (token != null && token.value != null) {
		   // myContext.getClient().setHeader("Authorization", "Bearer " + token.value);
		    myContext.getClient().setBearerToken(token.value.toString());
		    // Clear username/password
		    myContext.getClient().setUsername("_oauth_");
            myContext.getClient().setPassword("");
		} else {
		    myContext.getClient().setUsername("" + user.value);
		    myContext.getClient().setPassword("" + pass.value);
		}
		
        logger.info("Set username {} for Client {}", user.value,myContext.getClient().hashCode());

		if (app != null && app.value != null) {
			myContext.getClient().setNavajoHeader(NavajoClient.APP_HEADER_KEY, "" + app.value);
		}
		if (org != null && org.value != null) {
			myContext.getClient().setNavajoHeader(NavajoClient.ORG_HEADER_KEY, "" + org.value);
        }
		
		 
		logger.info("Created new client pointing to: " + serv.value
				+ " using username: " + user.value);
	}
}