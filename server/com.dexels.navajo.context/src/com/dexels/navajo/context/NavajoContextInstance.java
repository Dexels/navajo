package com.dexels.navajo.context;

import java.io.File;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoContextInstance {
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoContextInstance.class);

	private NavajoServerContext context = null;
	private File rootPath = null;
	private File settings = null;
	private BundleContext bundleContext;

	public void activate(BundleContext context) {
		this.bundleContext = context;
		logger.info("NavajoContextInstance activated");
		settings = new File(rootPath,"settings");
		File[] fd = settings.listFiles();
		
	}

	public void deactivate() {
		logger.info("NavajoContextInstance deactivated");
	}

	public void setServerContext(NavajoServerContext nsc) {
		this.context = nsc;
		rootPath = new File(context.getInstallationPath());
	}

	/**
	 * @param nsc
	 *            the NavajoServerContext to remove
	 */
	public void clearServerContext(NavajoServerContext nsc) {
		this.context = null;
	}

}
