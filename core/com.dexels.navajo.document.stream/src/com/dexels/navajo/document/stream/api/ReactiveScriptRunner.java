package com.dexels.navajo.document.stream.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.dexels.navajo.document.stream.ReactiveScript;

public interface ReactiveScriptRunner {
	public ReactiveScript build(String service, boolean debug) throws IOException;
	public boolean acceptsScript(String service);
	public Optional<String> deployment();
	public Optional<InputStream> sourceForService(String service);
	public ReactiveScript compiledScript(String service) throws IOException;
}
