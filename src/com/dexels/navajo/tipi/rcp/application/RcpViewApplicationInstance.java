package com.dexels.navajo.tipi.rcp.application;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import tipi.BaseTipiApplicationInstance;
import tipi.TipiApplicationInstance;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.rcp.TipiRcpContext;

public class RcpViewApplicationInstance extends BaseTipiApplicationInstance implements TipiApplicationInstance {

	private final Composite compositeParent;
	public RcpViewApplicationInstance(Composite parent) {
		compositeParent = parent;
		try {
			setCurrentContext(createContext());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TipiContext createContext() throws IOException {
		return new TipiRcpContext(this,compositeParent, new ArrayList<TipiExtension>(), null);
	}

	@Override
	public void dispose(TipiContext t) {

	}

	@Override
	public String getDefinition() {
		return null;
	}

}
