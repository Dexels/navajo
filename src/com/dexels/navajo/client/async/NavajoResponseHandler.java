package com.dexels.navajo.client.async;

import java.io.IOException;

import com.dexels.navajo.document.Navajo;

public interface NavajoResponseHandler {
	public void onResponse(Navajo n);
	public void onFail(Throwable t) throws IOException;

}
