package com.dexels.navajo.client.context;

import java.util.Map;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public interface ClientContext {

	public void reset();

	public void callService(String service, String tenant) throws ClientException;

	public Map<String, Navajo> getNavajos();

	public String getServiceName(Navajo n);

	public void callService(String service, String tenant, String username, String password, Navajo input)
			throws ClientException;

	public boolean hasNavajo(String name);

	public Navajo getNavajo(String name);

	public void setUsername(String username);

	public void setPassword(String password);

	public Property parsePropertyPath(String path);
	
}