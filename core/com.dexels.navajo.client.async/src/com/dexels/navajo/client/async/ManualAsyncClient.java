package com.dexels.navajo.client.async;

import java.io.IOException;

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

}