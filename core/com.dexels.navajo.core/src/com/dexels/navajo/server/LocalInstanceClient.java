/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.FatalException;

public class LocalInstanceClient extends LocalClientDispatcherWrapper {
	
	private static final Logger logger = LoggerFactory
			.getLogger(LocalInstanceClient.class);
	private String instance = null;
	
	@Override
	public void activate(Map<String,String> properties) {
		try {
			user = properties.get("user");
			pass = properties.get("password");
			instance = properties.get("instance");
		} catch (Throwable e) {
			logger.error("Activation failed");
		}
	}
	

	/**
	 * ignore supplied instance, need to remove this one
	 */
	@Override
	public Navajo call(String instance, Navajo n) throws FatalException {
		if(n.getHeader().getRPCUser()==null || n.getHeader().getRPCUser().equals("")) {
			n.getHeader().setRPCUser(user);
		}
		if(n.getHeader().getRPCPassword()==null || n.getHeader().getRPCPassword().equals("")) {
			n.getHeader().setRPCPassword(pass);
		}
		return super.call(this.instance, n);
	}
	
	@Override
	public Navajo call(Navajo n) throws FatalException {
		if(n.getHeader().getRPCUser()==null || n.getHeader().getRPCUser().equals("")) {
			n.getHeader().setRPCUser(user);
		}
		if(n.getHeader().getRPCPassword()==null || n.getHeader().getRPCPassword().equals("")) {
			n.getHeader().setRPCPassword(pass);
		}
		return super.call(this.instance, n);		
	}
}
