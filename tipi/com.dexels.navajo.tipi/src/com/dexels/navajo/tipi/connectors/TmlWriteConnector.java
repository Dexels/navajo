/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.connectors;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;

public class TmlWriteConnector extends TipiBaseConnector {

	private static final long serialVersionUID = -5080826465209597730L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TmlWriteConnector.class);
	// assume a load:
	@Override
	public Navajo doTransaction(Navajo input, String service, Integer retries)
			throws TipiBreakException, TipiException {
		throw new TipiException("Please supply a service and a destination.");
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
	}

	@Override
	public String getConnectorId() {
		return "tmlwrite";
	}

	@Override
	public Navajo doTransaction(Navajo n, String service, String destination)
			throws TipiBreakException, TipiException {
		if (n == null || destination == null) {
			throw new TipiException(
					"Please specify a destination and a navajo for saving!");
		}
		try {
			OutputStream os = myContext.getGenericResourceLoader()
					.writeResource(destination);

			try {
				n.write(os);
			} catch (NavajoException e) {
				logger.error("Error: ",e);
			} finally {
				os.flush();
				os.close();
			}
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
		return null;
	}

	@Override
	public Set<String> getEntryPoints() {
		return null;
	}

	@Override
	public String getDefaultEntryPoint() {
		return null;
	}

}
