package com.dexels.navajo.queuemanager.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.queuemanager.NavajoSchedulingException;
import com.dexels.navajo.queuemanager.QueueManager;
import com.dexels.navajo.queuemanager.api.InputContext;
import com.dexels.navajo.queuemanager.api.QueueContext;
import com.dexels.navajo.queuemanager.api.QueueResponse;
import com.dexels.navajo.queuemanager.impl.BaseQueueResponse;
import com.dexels.navajo.queuemanager.impl.ScriptLogger;
import com.dexels.navajo.queuemanager.internal.NavajoQueueScopeManager;
import com.dexels.navajo.server.NavajoIOConfig;

public class QueueManagerImpl implements QueueManager {
	private QueueContext queueContext;
	private final Map<String,BaseQueueResponse> cache = new HashMap<String,BaseQueueResponse>();
	private File scriptDir = null;
	private NavajoIOConfig navajoIOConfig;
	
	private final static Logger logger = LoggerFactory
			.getLogger(QueueManagerImpl.class);
	
	public void activate() {
		logger.info("Activating Queuemanager");
	}
	
	public void deactivate() {
		logger.info("Deactivating Queuemanager");
	}

	public void setNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
		this.navajoIOConfig = navajoIOConfig;
	}

	public void clearNavajoIOConfig(NavajoIOConfig navajoIOConfig) {
		this.navajoIOConfig = null;
	}

	
	public File getScriptDir() {
		if(this.navajoIOConfig!=null) {
			return new File(navajoIOConfig.getConfigPath());
		}
		return scriptDir;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.queuemanager.IQueueManager#setScriptDir(java.io.File)
	 */
	@Override
	public void setScriptDir(File scriptDir) {
		this.scriptDir = scriptDir;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.queuemanager.IQueueManager#setQueueContext(com.dexels.navajo.queuemanager.api.QueueContext)
	 */
	@Override
	public void setQueueContext(QueueContext queueContext) {
		this.queueContext = queueContext;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.queuemanager.IQueueManager#flushCache()
	 */
	@Override
	public void flushCache() {
		cache.clear();
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.queuemanager.IQueueManager#flushCache(java.lang.String)
	 */
	@Override
	public void flushCache(String service) {
		cache.remove(service);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.queuemanager.IQueueManager#resolve(com.dexels.navajo.queuemanager.api.InputContext, java.lang.String)
	 */
	@Override
	public String resolve(InputContext in, String script) throws NavajoSchedulingException  {
//		long begin = System.currentTimeMillis();
		BaseQueueResponse pr = cache.get(in.getServiceName());
		if(pr==null || !pr.isValid()) {
			pr = callResolutionScript(in, script);
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
			String script) throws NavajoSchedulingException {
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
        	if(getScriptDir()==null) {
        		scriptFile = new File(script);
        	} else {
        		scriptFile = new File(getScriptDir(),script);
        	}
			FileReader fr = new FileReader(scriptFile);
			// not interested in the result
			engine.eval(fr);
			//System.err.println("Result: "+result);
			fr.close();
			
		} catch (IOException e) {
			throw new NavajoSchedulingException(NavajoSchedulingException.SCRIPT_PROBLEM,"Error reading script",e);
		} catch (ScriptException e) {
			throw new NavajoSchedulingException(NavajoSchedulingException.SCRIPT_PROBLEM,"Error executing script",e);
		} finally {
			//long res = System.currentTimeMillis() - begin;
			//System.err.println("Queue selection took: "+res+" millis.");
			NavajoQueueScopeManager.getInstance().releaseScope(engine);
		}
		return pc;
	}
	
}
