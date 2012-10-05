package com.dexels.navajo.queuemanager.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavajoQueueScopeManager {

	private static NavajoQueueScopeManager instance = null;
	private final static String engineName = "javascript";
	private ScriptEngineManager factory = new ScriptEngineManager();

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoQueueScopeManager.class);

	private int MAX_UNUSED_SCOPES = 50;

	public synchronized static NavajoQueueScopeManager getInstance() {
		if (instance == null) {
			instance = new NavajoQueueScopeManager();
		}
		return instance;
	}

	private NavajoQueueScopeManager() {
		// prevent other instances.
	}

	// ScriptableObject globalScope = cx.initStandardObjects();
	private final Queue<ScriptEngine> freeScopes = new LinkedList<ScriptEngine>();

	public synchronized ScriptEngine getScope() {
		long init = System.currentTimeMillis();

		ScriptEngine so = freeScopes.poll();
		if (so != null) {
			// System.err.println("Reusing scope. Free scopes: "+freeScopes.size());
			return so;
		}
		// System.err.println("Creating scope. Free scopes: "+freeScopes.size());
		ScriptEngine engine = factory.getEngineByName(engineName);
		long started = System.currentTimeMillis();

		// basic example
		logger.debug("Engine startup: " + (started - init) + " millis.");
		if (engine == null) {
			List<ScriptEngineFactory> fact = factory.getEngineFactories();
			for (ScriptEngineFactory scriptEngineFactory : fact) {
				logger.warn("Factory: " + scriptEngineFactory.getEngineName());
			}
			logger.error("Fail: engine didn't start!");
		}
		return engine;
	}

	public synchronized void releaseScope(ScriptEngine scope) {
		if (freeScopes.size() >= MAX_UNUSED_SCOPES) {
			cleanScope(scope);
			return;
		}
		freeScopes.add(scope);
	}

	private void cleanScope(ScriptEngine engine) {
		// remove everything related to this run.
		engine.getBindings(ScriptContext.ENGINE_SCOPE).clear();
	}

	public static synchronized void clearInstance() {
		if (instance != null) {
			instance.factory = null;
			for (ScriptEngine engine : instance.freeScopes) {
				engine.getBindings(ScriptContext.ENGINE_SCOPE).clear();

			}
			instance = null;
		}
	}

}
