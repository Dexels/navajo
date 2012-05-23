package com.dexels.navajo.server.scriptengine;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public interface IncludeManager {
	public void loadIncludes(ScriptEngine se, String includePath) throws ScriptException;
}
