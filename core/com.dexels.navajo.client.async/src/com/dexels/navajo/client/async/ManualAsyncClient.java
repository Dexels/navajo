package com.dexels.navajo.client.async;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;

public interface ManualAsyncClient extends AsyncClient {

	public void callService(String url, String username,
			String password, Navajo input, String service,
			NavajoResponseHandler continuation) throws IOException;

	public void callService(Access inputAccess, Navajo input,
			String service, TmlRunnable onSuccess, TmlRunnable onFail,
			NavajoResponseCallback navajoResponseCallback) throws IOException,
			NavajoException;

	public String getServer();

	public void setServer(String server);

	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	public void close();
	
	@Override
	public boolean useHttps();
	@Override
	public void setHttps(boolean useHttps);

	/**
	 * set the SSL socket factory to use whenever an HTTPS call is made.
	 * @param algorithm, the algorithm to use, for example: SunX509
	 * @param type Type of the keystore, for example PKCS12 or JKS
	 * @param source InputStream of the client certificate, supply null to reset the socketfactory to default
	 * @param password the keystore password
	 */

	@Override
	public void setClientCertificate(String algorithm, String type, InputStream is, char[] password) throws IOException;


}