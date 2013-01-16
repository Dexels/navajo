package com.dexels.navajo.rhino;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class NavajoScopeManager {

	private static NavajoScopeManager instance = null;

	private int MAX_UNUSED_SCOPES = 50;

	public synchronized static NavajoScopeManager getInstance() {
		if (instance == null) {
			instance = new NavajoScopeManager();
		}
		return instance;
	}

	private NavajoScopeManager() {
		// prevent other instances.
	}

	// ScriptableObject globalScope = cx.initStandardObjects();
	private final Queue<Scriptable> freeScopes = new LinkedList<Scriptable>();

	public synchronized Scriptable getScope() {
		Context cx = Context.getCurrentContext();
		if (cx == null) {
			throw new UnsupportedOperationException(
					"Can not create a scope without attached context");
		}
		Scriptable so = freeScopes.poll();
		if (so != null) {
			return so;
		}
		ScriptableObject globalScope = cx.initStandardObjects();
		return globalScope;
	}

	public synchronized void releaseScope(Scriptable scope) {
		if (freeScopes.size() >= MAX_UNUSED_SCOPES) {
//			cleanScope(scope);
			return;
		}
		freeScopes.add(scope);
	}

	public void runScript(String fileName, Reader r, Map<String, Object> parameters) throws IOException {
		Context cx = Context.getCurrentContext();
		if(cx==null) {
			cx = Context.enter();
		}
		ScriptableObject globalScope = (ScriptableObject) NavajoScopeManager.getInstance().getScope();
		for (Entry<String,Object> e : parameters.entrySet()) {
			ScriptableObject.putProperty(globalScope, e.getKey(),Context.javaToJS(e.getValue(), globalScope));
		}
		cx.evaluateReader(globalScope, r, fileName, 1, null);
		Context.exit();
		Set<String> keys = parameters.keySet();
		for (String e : keys) {
			ScriptableObject.putProperty(globalScope, e,null);
		}

		NavajoScopeManager.getInstance().releaseScope(globalScope);
	}
//	private void cleanScope(Scriptable scope) {
//	}
}
