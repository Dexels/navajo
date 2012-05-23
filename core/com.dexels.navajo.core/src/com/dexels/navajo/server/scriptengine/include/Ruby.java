package com.dexels.navajo.server.scriptengine.include;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.scriptengine.IncludeManager;

public class Ruby implements IncludeManager {

	public void loadIncludes(ScriptEngine se, String includePath) throws ScriptException {
		se.eval("$LOAD_PATH.push('"+includePath+"');" +
				"$LOAD_PATH.push('"+DispatcherFactory.getInstance().getNavajoConfig().getScriptPath()+"');");

	}

}
