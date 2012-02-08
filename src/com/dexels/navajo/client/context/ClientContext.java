package com.dexels.navajo.client.context;

import java.util.Map;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;

public interface ClientContext {

	public void reset();

	public void callService(String service) throws ClientException;

	public Map<String, Navajo> getNavajos();

	public String getServiceName(Navajo n);

	public void callService(String service, Navajo input)
			throws ClientException;

	public boolean hasNavajo(String name);

	public Navajo getNavajo(String name);

}