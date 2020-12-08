/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.connectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiHeadlessComponentImpl;

public abstract class TipiBaseConnector extends TipiHeadlessComponentImpl
		implements TipiConnector {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBaseConnector.class);
	
	private static final long serialVersionUID = 6781839214790284978L;

	@Override
	public final Navajo doTransaction() throws TipiBreakException, TipiException {
		return doTransaction(null, null, 0);
	}

	@Override
	public final Navajo doTransaction(String service) throws TipiBreakException,
			TipiException {
		return doTransaction(null, service, 0);
	}
	@Override
   public Navajo doTransaction(Navajo n, String service, Integer retries)
                   throws TipiBreakException, TipiException {
           return doTransaction(n, service, (String) null);
   }


	protected Navajo injectNavajo(String service, Navajo n)
			throws TipiBreakException {
		if (myContext == null) {
			
			logger.error("No context found while injecting: " + service);
			return null;
		} else {
			myContext.injectNavajo(service, n);
			return n;
		}
	}

	@Override
	public Object createContainer() {
		return null;
	}


}
