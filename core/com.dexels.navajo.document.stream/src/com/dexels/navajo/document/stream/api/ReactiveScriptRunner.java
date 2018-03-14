package com.dexels.navajo.document.stream.api;

import java.io.IOException;

import com.dexels.navajo.document.stream.ReactiveScript;

public interface ReactiveScriptRunner {
	public ReactiveScript build(String service, boolean debug) throws IOException;
	public boolean acceptsScript(String service);
	public String deployment();
}
