package com.dexels.navajo.client.async;

import java.io.IOException;

import org.eclipse.jetty.client.HttpClient;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;

public interface AsyncClient {

	public abstract int getActualCalls();

	public abstract void setActualCalls(int actualCalls);

	public abstract void callService(String service,
			NavajoResponseHandler continuation) throws IOException,
			NavajoException;

	public abstract void callService(Navajo input, String service,
			NavajoResponseHandler continuation) throws IOException,
			NavajoException;

	public abstract void callService(String url, String username,
			String password, Navajo input, String service,
			NavajoResponseHandler continuation) throws IOException,
			NavajoException;

	public abstract void callService(Access inputAccess, Navajo input,
			String service, TmlRunnable onSuccess, TmlRunnable onFail,
			NavajoResponseCallback navajoResponseCallback) throws IOException,
			NavajoException;

	public abstract Navajo createBlankNavajo(String service, String user,
			String password);

	public abstract HttpClient getClient();

	public abstract void setClient(HttpClient client);

	public abstract String getServer();

	public abstract void setServer(String server);

	public abstract String getUsername();

	public abstract void setUsername(String username);

	public abstract String getPassword();

	public abstract void setPassword(String password);

}