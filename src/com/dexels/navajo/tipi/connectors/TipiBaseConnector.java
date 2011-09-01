package com.dexels.navajo.tipi.connectors;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiHeadlessComponentImpl;

public abstract class TipiBaseConnector extends TipiHeadlessComponentImpl
		implements TipiConnector {

	// protected final Map<String,TipiValue> myEntrypoints = new HashMap<String,
	// TipiValue>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 6781839214790284978L;

	public final void doTransaction() throws TipiBreakException, TipiException {
		doTransaction(null, null, null);
	}

	public final void doTransaction(String service) throws TipiBreakException,
			TipiException {
		doTransaction(null, service, null);
	}

	public void doTransaction(Navajo n, String service)
			throws TipiBreakException, TipiException {
		doTransaction(n, service, null);
	}

	// public final void doTransaction(String service) throws
	// TipiBreakException, TipiException {
	// doTransaction(null,service,null);
	// }
	//

	public void injectNavajo(String service, Navajo n)
			throws TipiBreakException {
		if (myContext == null) {
			System.err.println("Injecting: " + service);
			try {
				n.write(System.err);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		} else {
			myContext.injectNavajo(service, n);
		}
	}

	public Object createContainer() {
		return null;
	}

	// public String[] getEntryPoints() {
	// return null;
	// }

}
