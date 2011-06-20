package com.dexels.navajo.queuemanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.dexels.navajo.queuemanager.api.InputContext;
import com.dexels.navajo.queuemanager.api.QueueContext;
import com.dexels.navajo.queuemanager.api.QueueResponse;
import com.dexels.navajo.queuemanager.impl.BaseQueueResponse;
import com.dexels.navajo.queuemanager.impl.ScriptLogger;

public class QueueManager {
	private QueueContext queueContext;
	private final Map<String,BaseQueueResponse> cache = new HashMap<String,BaseQueueResponse>();
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

	public void setQueueContext(QueueContext queueContext) {
		this.queueContext = queueContext;
	}
	
	public void flushCache() {
		cache.clear();
	}
	
	public void flushCache(String service) {
		cache.remove(service);
	}

	public String resolve(InputContext in, String script, String engineName) throws NavajoSchedulingException  {
		long begin = System.currentTimeMillis();
		BaseQueueResponse pr = cache.get(in.getServiceName());
		if(pr==null || !pr.isValid()) {
			pr = callResolutionScript(in, script, begin,engineName);
			cache.put(in.getServiceName(), pr);
		} else {
			//System.err.println("Returning cached response");			
		}
        if(!QueueResponse.ACCEPT.equals(pr.getResponse())) {
        	NavajoSchedulingException navajoSchedulingException = new NavajoSchedulingException(NavajoSchedulingException.REQUEST_REFUSED, "Scheduling refused!");
        	throw navajoSchedulingException;
        }
        return pr.getQueueName();
	}

	private BaseQueueResponse callResolutionScript(InputContext in,
			String script, long begin, String engineName) throws NavajoSchedulingException {
		BaseQueueResponse pc = new BaseQueueResponse();
        ScriptEngine engine = NavajoQueueScopeManager.getInstance().getScope();
//		long init = System.currentTimeMillis();

        engine.put("inputContext", in);
        engine.put("log", new ScriptLogger());
        engine.put("queueContext", queueContext);
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
			// not interested in the result
			engine.eval(fr);
			//System.err.println("Result: "+result);
			fr.close();
			
		} catch (IOException e) {
			throw new NavajoSchedulingException(NavajoSchedulingException.SCRIPT_PROBLEM,"Error reading script",e);
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new NavajoSchedulingException(NavajoSchedulingException.SCRIPT_PROBLEM,"Error executing script",e);
		} finally {
			//long res = System.currentTimeMillis() - begin;
			//System.err.println("Queue selection took: "+res+" millis.");
			NavajoQueueScopeManager.getInstance().releaseScope(engine);
		}
		return pc;
	}
	
}
