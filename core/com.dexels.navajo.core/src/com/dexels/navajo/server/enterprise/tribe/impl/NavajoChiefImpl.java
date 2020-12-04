/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.tribe.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.enterprise.tribe.NavajoChief;

public class NavajoChiefImpl implements NavajoChief {

	/**
	 * This 'token' service is meant to only be available to the chief.
	 * It requires configuration, which will be emitted by the clustering implementation if this member is indeed the chief
	 */
	private static final Logger logger = LoggerFactory.getLogger(NavajoChiefImpl.class);

	
	public void activate(Map<String,Object> settings) {
		logger.info("Activating Chief service. Today's chief is supplied by: "+settings.get("cluster.implementation"));
	}
	
	public void deactivate() {
		logger.info("Deactivating Chief service");
	}
}
