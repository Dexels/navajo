package com.dexels.navajo.client;

import com.dexels.navajo.document.Navajo;

public interface LocalClient {
	public Navajo call(Navajo n) throws Exception;
}
