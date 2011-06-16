package com.dexels.navajo.tipi.actions;

import java.io.IOException;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;


public final class TipiReboot extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		try {
			myContext.getApplicationInstance().reboot();
		} catch (IOException e) {
			throw new TipiException("Error rebooting tipi!",e);
		}
	}

}
