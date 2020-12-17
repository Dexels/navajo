/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.queuemanager.internal;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.listener.http.queuemanager.api.InputContext;
import com.dexels.navajo.listener.http.queuemanager.api.NavajoSchedulingException;
import com.dexels.navajo.listener.http.queuemanager.api.QueueContext;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManager;

public class BasicQueueManager  implements QueueManager {
    private static final Logger logger = LoggerFactory.getLogger(BasicQueueManager.class);
    private Map<String, Object> settings;

    public void activate(Map<String,Object> settings) {
        logger.info("Activating BasicQueueManager");
        modified(settings);
    }
    
    public void modified(Map<String, Object> settings) {
        this.settings = settings;
        
    }

    public void deactivate() {
        logger.info("Deactivating BasicQueueManager");
        settings = null;
    }

    
    
    @Override
    public void setScriptDir(File scriptDir) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setQueueContext(QueueContext queueContext) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void flushCache() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void flushCache(String service) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String resolve(InputContext in, String resolvescript) throws NavajoSchedulingException {
        String serviceName = in.getServiceName();
        if(serviceName==null) {
        	return null;
        }
		return (String) settings.get(serviceName);
        
    }
}
