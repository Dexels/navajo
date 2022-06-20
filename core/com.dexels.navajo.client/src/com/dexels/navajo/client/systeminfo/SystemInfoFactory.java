/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.systeminfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemInfoFactory  {

	private static SystemInfoProvider instance = null;
	
	private static final Logger logger = LoggerFactory
			.getLogger(SystemInfoFactory.class);
	
	private SystemInfoFactory() {
		
	}
	
	
	public static synchronized SystemInfoProvider getSystemInfo() {

		if ( instance == null ) {
			try {
				instance = new DefaultSystemInfo();
				instance.init();
			} catch (Throwable t) { // Could not get runtime
				instance = new DefaultSystemInfo(-1, t.getMessage());
			}
		}

		return instance;
	}

	public static synchronized void setSystemInfoProvider(SystemInfoProvider provider) {
		instance = provider;
	}
	
	public static void main(String [] args) {
		SystemInfoProvider info = SystemInfoFactory.getSystemInfo();
		logger.info("{}",info);
	}
	
	public static void clearInstance() {
		instance = null;
	}
}
