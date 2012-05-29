package com.dexels.navajo.script.api;

import com.dexels.navajo.document.Navajo;

public interface LocalClient {
	public Navajo call(Navajo n) throws FatalException;
	public Navajo generateAbortMessage(String reason) throws FatalException;
	public Navajo handleCallback(Navajo n, String callback);
	public Navajo handleInternal(Navajo in, Object cert, ClientInfo clientInfo) throws FatalException;
	public boolean isSpecialWebservice(String name);
	public String getApplicationId();
}
