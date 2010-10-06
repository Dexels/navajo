package com.dexels.navajo.client.logger;

import com.dexels.navajo.document.Navajo;

public interface ClientLogger {
	public void logInput(String service, Navajo in);
	public void logOutput(String service, Navajo out);
	public void logOutput(String service, Navajo in, Navajo out);
}
