package com.dexels.navajo.client.async;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Navajo;

public interface AsyncClient {
	
	public String getName();
	public void callService(Navajo input, String service,
			NavajoResponseHandler continuation) throws IOException;
	public void setName(String name);
	public Navajo callService(Navajo input, String service) throws IOException;
	public void setClientCertificate(String algorithm, String type, InputStream is, char[] password) throws IOException;
	public boolean useHttps();
	public void setHttps(boolean useHttps);


}
