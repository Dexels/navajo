/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.context.impl;

import java.util.Map;

import com.dexels.navajo.tipi.context.ContextInstance;

public class ContextInstanceImpl implements ContextInstance {
	private String path;
	private String profile;
	private String deployment;
	private String context;

	public void activate(Map<String,String> params) {
		this.deployment = params.get("deployment");
		this.profile = params.get("profile");
		this.path = params.get("path");
		this.context = params.get("context");
	}

	public void deactivate() {
		this.path = null;
		this.deployment = null;
		this.profile = null;
		this.context = null;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextInstance#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextInstance#getDeployment()
	 */
	@Override
	public String getDeployment() {
		return deployment;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextInstance#getProfile()
	 */
	@Override
	public String getProfile() {
		return profile;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextInstance#getContext()
	 */
	@Override
	public String getContext() {
		return context;
	}
}
