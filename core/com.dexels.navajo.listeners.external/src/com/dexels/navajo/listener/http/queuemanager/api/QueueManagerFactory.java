/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.listener.http.queuemanager.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueManagerFactory {
	private static QueueManager instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(QueueManagerFactory.class);
	
	@SuppressWarnings("unchecked")
	public synchronized static QueueManager getInstance() {
		logger.warn("In static QueueManager getter, should only happen in non-OSGi");
		
		if(instance==null) {
			try {
				Class<? extends QueueManager> qm =(Class<? extends QueueManager>) Class.forName("com.dexels.navajo.rhino.queuemanager.QueueManagerImpl");
				instance = qm.newInstance();
			} catch (Exception e) {
				logger.error("Problem instantiating QueueManager non-OSGi:",e);
			}
		}
		return instance;
	}
	
	public synchronized static void clearInstance() {
		if(instance!=null) {
			instance.flushCache();
			instance = null;
		}
	}

}
