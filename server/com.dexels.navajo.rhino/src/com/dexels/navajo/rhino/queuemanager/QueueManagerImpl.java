package com.dexels.navajo.rhino.queuemanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import navajorhino.Version;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.listener.http.queuemanager.api.InputContext;
import com.dexels.navajo.listener.http.queuemanager.api.NavajoSchedulingException;
import com.dexels.navajo.listener.http.queuemanager.api.QueueContext;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManager;
import com.dexels.navajo.listener.http.queuemanager.api.QueueResponse;
import com.dexels.navajo.rhino.NavajoScopeManager;
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
		boolean osgi = Version.isOSGi();
		if(pr==null || !pr.isValid()) {
			if (osgi) {
				pr = callRhinoResolutionScript(in, script);
			} else {
				pr = callResolutionScript(in, script);
			}
			if(pr==null) {
				return null;
			}
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
//		long init = System.currentTimeMillis();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("inputContext", in);
		params.put("log", new ScriptLogger());
		params.put("queueContext", queueContext);
		params.put("response", pc);
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
			NavajoScopeManager.getInstance().runScript(scriptFile.getName(), fr, params);
			//System.err.println("Result: "+result);
			fr.close();
			
		} catch (IOException e) {
			throw new NavajoSchedulingException(NavajoSchedulingException.SCRIPT_PROBLEM,"Error reading script",e);
		}
		return pc;
	}
	
	private BaseQueueResponse callRhinoResolutionScript(InputContext in,
			String script) throws NavajoSchedulingException {
		BaseQueueResponse pc = new BaseQueueResponse();
//		long init = System.currentTimeMillis();
		Scriptable globalScope = null;
    	File scriptFile = null;
    	Context.enter();
    	if(getScriptDir()==null) {
    		scriptFile = new File(script);
    	} else {
    		scriptFile = new File(getScriptDir(),script);
    	}
    	if(!scriptFile.exists()) {
    		logger.debug("Can not resolve resolution script.");
    		return null;
    	}
    	FileReader fr = null;
        try {
    		fr = new FileReader(scriptFile);
		globalScope = NavajoScopeManager.getInstance()
				.getScope();

//		Scriptable scr = NavajoScopeManager.getInstance().getScope();
		final Map<String,Object> params = new HashMap<String, Object>();
		params.put("inputContext", in);
		params.put("log", new ScriptLogger());
		params.put("queueContext", queueContext);
		params.put("response", pc);
        // basic example
		
		
		NavajoScopeManager.getInstance().runScript(scriptFile.getName(), fr, params);
		fr.close();
		
		
		} catch (IOException e) {
			throw new NavajoSchedulingException(NavajoSchedulingException.SCRIPT_PROBLEM,"Error reading script",e);
		} finally {
			//long res = System.currentTimeMillis() - begin;
			//System.err.println("Queue selection took: "+res+" millis.");
//			NavajoQueueScopeManager.getInstance().releaseScope(engine);
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(globalScope!=null) {
				
				NavajoScopeManager.getInstance().releaseScope(globalScope);
			}
		}
		return pc;
	}
	
}
