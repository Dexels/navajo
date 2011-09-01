package com.dexels.navajo.tipi.connectors;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;

public class TmlWriteConnector extends TipiBaseConnector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5080826465209597730L;

	// assume a load:
	public void doTransaction(Navajo input, String service)
			throws TipiBreakException, TipiException {
		throw new TipiException("Please supply a service and a destination.");
	}

	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
	}

	public String getConnectorId() {
		return "tmlwrite";
	}

	public void doTransaction(Navajo n, String service, String destination)
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
				e.printStackTrace();
			} finally {
				os.flush();
				os.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Set<String> getEntryPoints() {
		return null;
	}

	public String getDefaultEntryPoint() {
		return null;
	}

}
