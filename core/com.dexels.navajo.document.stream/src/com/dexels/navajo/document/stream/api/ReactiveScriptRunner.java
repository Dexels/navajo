package com.dexels.navajo.document.stream.api;

import java.io.IOException;

import com.dexels.navajo.document.stream.ReactiveScript;

public interface ReactiveScriptRunner {
	public ReactiveScript run(String service) throws IOException;
	public boolean acceptsScript(String service);

}
