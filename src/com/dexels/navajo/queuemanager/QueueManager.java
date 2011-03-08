package com.dexels.navajo.queuemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.dexels.navajo.queuemanager.api.InputContext;
import com.dexels.navajo.queuemanager.api.PoolContext;
import com.dexels.navajo.queuemanager.api.PoolResponse;
import com.dexels.navajo.queuemanager.impl.BasePoolResponse;
import com.dexels.navajo.queuemanager.impl.ScriptLogger;

public class QueueManager {
	private PoolContext poolContext;
	private final Map<String,BasePoolResponse> cache = new HashMap<String,BasePoolResponse>();
	private File scriptDir = null;
	
	public File getScriptDir() {
		return scriptDir;
	}

	public void setScriptDir(File scriptDir) {
		this.scriptDir = scriptDir;
	}

	public void setPoolContext(PoolContext poolContext) {
		this.poolContext = poolContext;
	}
	
	public String resolve(InputContext in, String script) throws NavajoSchedulingException  {
		long begin = System.currentTimeMillis();
		BasePoolResponse pr = cache.get(in.getServiceName());
		if(pr==null || !pr.isValid()) {
			pr = callResolutionScript(in, script, begin);
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
			String script, long begin) throws NavajoSchedulingException {
		BasePoolResponse pc = new BasePoolResponse();
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("javascript");
		long init = System.currentTimeMillis();

		System.err.println("Engine startup: "+(init-begin)+" millis.");
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
		} catch (Exception e) {
			throw new NavajoSchedulingException(e);
		} finally {
			long res = System.currentTimeMillis() - begin;
			System.err.println("Pool selection took: "+res+" millis.");
		}
		return pc;
	}
	
}
