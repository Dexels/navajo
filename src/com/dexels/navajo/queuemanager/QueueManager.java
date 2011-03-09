package com.dexels.navajo.queuemanager;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;

import com.dexels.navajo.queuemanager.api.InputContext;
import com.dexels.navajo.queuemanager.api.PoolContext;
import com.dexels.navajo.queuemanager.api.PoolResponse;
import com.dexels.navajo.queuemanager.impl.BasePoolResponse;
import com.dexels.navajo.queuemanager.impl.ScriptLogger;

public class QueueManager {
	private PoolContext poolContext;
	private final Map<String,BasePoolResponse> cache = new HashMap<String,BasePoolResponse>();
	private File scriptDir = null;
	
	private static QueueManager instance = null;

	public synchronized static QueueManager getInstance() {
		if(instance==null) {
			instance = new QueueManager();
		}
		return instance;
	}
	
	public static void clearInstance() {
		if(instance!=null) {
			instance.cache.clear();
			instance = null;
		}
	}
	
	public File getScriptDir() {
		return scriptDir;
	}

	public void setScriptDir(File scriptDir) {
		this.scriptDir = scriptDir;
	}

	public void setPoolContext(PoolContext poolContext) {
		this.poolContext = poolContext;
	}
	
	public void flushCache() {
		cache.clear();
	}
	
	public void flushCache(String service) {
		cache.remove(service);
	}

	public String resolve(InputContext in, String script, String engineName) throws NavajoSchedulingException  {
		long begin = System.currentTimeMillis();
		BasePoolResponse pr = cache.get(in.getServiceName());
		if(pr==null || !pr.isValid()) {
			pr = callResolutionScript(in, script, begin,engineName);
			cache.put(in.getServiceName(), pr);
		} else {
			System.err.println("Returning cached response");			
		}
        if(!PoolResponse.ACCEPT.equals(pr.getResponse())) {
        	throw new NavajoSchedulingException("Scheduling refused!");
        }
        return pr.getPoolName();
	}

	private BasePoolResponse callResolutionScript(InputContext in,
			String script, long begin, String engineName) throws NavajoSchedulingException {
		BasePoolResponse pc = new BasePoolResponse();
        ScriptEngine engine = NavajoQueueScopeManager.getInstance().getScope();
		long init = System.currentTimeMillis();

        engine.put("inputContext", in);
        engine.put("log", new ScriptLogger());
        engine.put("poolContext", poolContext);
        engine.put("response", pc);
        // basic example
        try {
        	File scriptFile = null;
        	if(scriptDir==null) {
        		scriptFile = new File(script);
        	} else {
        		scriptFile = new File(scriptDir,script);
        	}
			FileReader fr = new FileReader(scriptFile);
			engine.eval(fr);
			fr.close();
			NavajoQueueScopeManager.getInstance().releaseScope(engine);
		} catch (Exception e) {
			throw new NavajoSchedulingException(e);
		} finally {
			long res = System.currentTimeMillis() - begin;
			System.err.println("Pool selection took: "+res+" millis.");
		}
		return pc;
	}
	
}
