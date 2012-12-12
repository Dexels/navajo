package com.dexels.navajo.server.internal;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.FileNavajoConfig;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoIOConfigComponent extends FileNavajoConfig implements NavajoIOConfig {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoIOConfigComponent.class);
	
	
	private NavajoServerContext context = null;
	private File rootPath = null;

	public void activate() {
		logger.info("NavajoIOConfigComponent activated");
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
	public String getClassPath() {
		return null;
	}

	@Override
	public String getAdapterPath() {
		return new File(rootPath,"adapters").getAbsolutePath();
	}

	@Override
	@Deprecated
	public File getJarFolder() {
		return null;
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
		return new File(rootPath,"resource").getAbsolutePath();
	}







}
