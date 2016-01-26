package com.dexels.navajo.adapters.stream.example;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.listeners.stream.core.BaseScriptInstance;
import com.dexels.navajo.listeners.stream.core.ScriptRunner;

public class TestScriptRunner extends ScriptRunner {

	@Override
	protected BaseScriptInstance resolveScript(Header h) throws Exception {
		System.err.println("Resolving script: "+h.getRPCName());
		BaseScriptInstance instance = (BaseScriptInstance) Class.forName(h.getRPCName()).newInstance();
		return instance;
	}

}
