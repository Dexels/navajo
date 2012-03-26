package com.dexels.navajo.server.listener.internal;

import com.dexels.navajo.client.LocalClient;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.DispatcherInterface;

public class LocalClientDispatcherWrapper implements LocalClient {

	private final DispatcherInterface dispatcherInterface;

	public LocalClientDispatcherWrapper(DispatcherInterface di) {
		this.dispatcherInterface = di;
	}
	@Override
	public Navajo call(Navajo n) throws Exception {
		n.write(System.err);
		return dispatcherInterface.handle(n);
	}


}

