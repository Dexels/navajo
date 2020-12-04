/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.queuemanager.internal;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.listener.http.queuemanager.api.InputContext;
import com.dexels.navajo.listener.http.queuemanager.api.NavajoSchedulingException;
import com.dexels.navajo.listener.http.queuemanager.api.QueueContext;
import com.dexels.navajo.listener.http.queuemanager.api.QueueManager;

public class NullQueueManager implements QueueManager {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NullQueueManager.class);
	
	@Override
	public void setScriptDir(File scriptDir) {
		// ignore
	}

	@Override
	public void setQueueContext(QueueContext queueContext) {
		// ignore
	}

	@Override
	public void flushCache() {
		// ignore

	}

	@Override
	public void flushCache(String service) {
		// ignore

	}

	@Override
	public String resolve(InputContext in, String script)
			throws NavajoSchedulingException {
		// ignore
		return null;
	}
	
	public void activate() {
		logger.info("Activating null queuemanager");
	}

	public void deactivate() {
		logger.info("Deactivating null queuemanager");
	}
}
