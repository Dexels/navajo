package com.dexels.navajo.queuemanager;

import java.util.LinkedList;
import java.util.Queue;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class NavajoQueueScopeManager {
	
	private static NavajoQueueScopeManager instance = null;
	private final static String engineName = "javascript";
	private ScriptEngineManager factory = new ScriptEngineManager();

	private int MAX_UNUSED_SCOPES = 50;
	
	public synchronized static NavajoQueueScopeManager getInstance() {
		if(instance==null) {
			instance = new NavajoQueueScopeManager();
		}
		return instance;
	}
	
	private NavajoQueueScopeManager() {
		// prevent other instances.
	}
	//ScriptableObject globalScope = cx.initStandardObjects();
	private final Queue<ScriptEngine> freeScopes =  new LinkedList<ScriptEngine>();
	
	public synchronized ScriptEngine getScope() {
		long init = System.currentTimeMillis();

		ScriptEngine so = freeScopes.poll();
		if(so!=null) {
			//System.err.println("Reusing scope. Free scopes: "+freeScopes.size());
			return so;
		}
		//System.err.println("Creating scope. Free scopes: "+freeScopes.size());
        ScriptEngine engine = factory.getEngineByName(engineName);
		long started = System.currentTimeMillis();


        // basic example
		System.err.println("Engine startup: "+(started-init)+" millis.");
		
		return engine;
	}
	
	public synchronized void releaseScope(ScriptEngine scope) {
		if(freeScopes.size()>=MAX_UNUSED_SCOPES) {
			cleanScope(scope);
			return;
		}
		freeScopes.add(scope);
	}

	private void cleanScope(ScriptEngine engine) {
		// remove everything related to this run.
        engine.getBindings(ScriptContext.ENGINE_SCOPE).clear();
	}
	
	public static void clearInstance() {
		if(instance!=null) {
			instance.factory = null;
			for (ScriptEngine engine : instance.freeScopes) {
		        engine.getBindings(ScriptContext.ENGINE_SCOPE).clear();

			}
			instance = null;
		}
	}
}
