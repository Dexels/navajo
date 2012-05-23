package com.dexels.navajo.client.push;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;

public abstract class NavajoPushSession {

	public abstract void load(Navajo n, String agentId) throws ClientException;
}
