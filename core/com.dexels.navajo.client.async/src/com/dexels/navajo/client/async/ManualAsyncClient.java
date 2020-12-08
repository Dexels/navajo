/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.async;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.TmlRunnable;


public interface ManualAsyncClient extends AsyncClient {

	public void callService(String url, String username,
			String password, Navajo input, String service,
			NavajoResponseHandler continuation, Integer timeout) throws IOException;

	public void callService(Access inputAccess, Navajo input,
			String service, TmlRunnable onSuccess, TmlRunnable onFail,
			NavajoResponseCallback navajoResponseCallback) throws IOException;

    public void close();

	public String getServer();

	public void setServer(String server);

	public String getUsername();

	public void setUsername(String username);

	public String getPassword();

	public void setPassword(String password);

	@Override
	public boolean useHttps();

	@Override
	public void setHttps(boolean useHttps);

    public void setCloseAfterUse(boolean closeAfterUse);

	@Override
	public void setClientCertificate(String algorithm, String type, InputStream is, char[] password) throws IOException;

}