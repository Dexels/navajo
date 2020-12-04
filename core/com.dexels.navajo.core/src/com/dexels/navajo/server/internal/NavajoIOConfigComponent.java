/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.internal;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.FileNavajoConfig;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoIOConfigComponent extends FileNavajoConfig implements NavajoIOConfig {

	
	private static final Logger logger = LoggerFactory
			.getLogger(NavajoIOConfigComponent.class);
	
	
	private NavajoServerContext context = null;
	private File rootPath = null;

	public void activate() {
		logger.info("NavajoIOConfigComponent activated");
	}
	
	public void deactivate() {
		logger.info("NavajoIOConfigComponent deactivated");
	}
	
	
	
	public void setServerContext(NavajoServerContext nsc) {
		this.context = nsc;
		rootPath = new File(context.getInstallationPath());
	}
	
	/**
	 * @param nsc the NavajoServerContext to remove 
	 */
	public void clearServerContext(NavajoServerContext nsc) {
		this.context = null;
	}

	@Override
	public String getConfigPath() {
		return new File(rootPath,"config").getAbsolutePath();
	}


	@Override
	public String getAdapterPath() {
		return new File(rootPath,"adapters").getAbsolutePath();
	}


	@Override
	public String getScriptPath() {
		return new File(rootPath,"scripts").getAbsolutePath();
	}

	@Override
	public String getCompiledScriptPath() {
		return new File(rootPath,"classes").getAbsolutePath();
	}

	
	@Override
	public String getRootPath() {
		return context.getInstallationPath();
	}

	@Override
	public File getContextRoot() {
		logger.warn("getContextRoot not implemented in OSGi DS implementation");
		return null;
	}

	@Override
	public String getResourcePath() {
		return new File(rootPath,"resources").getAbsolutePath();
	}

    @Override
    public String getDeployment() {
        return context.getDeployment();
    }



}
