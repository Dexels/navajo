package com.dexels.navajo.tipi.actions;

import java.io.IOException;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public final class TipiReboot extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2399789386165874229L;

	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		try {
			myContext.getApplicationInstance().reboot();
		} catch (IOException e) {
			throw new TipiException("Error rebooting tipi!", e);
		}
	}

}
