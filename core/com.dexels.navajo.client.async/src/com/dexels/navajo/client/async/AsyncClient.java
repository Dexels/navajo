package com.dexels.navajo.client.async;

import java.io.IOException;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Navajo;

public interface AsyncClient {
	
	public String getName();
	public void callService(Navajo input, String service,
			NavajoResponseHandler continuation) throws IOException;
	public void setName(String name);
	public Navajo callService(Navajo input, String service) throws IOException;


}
