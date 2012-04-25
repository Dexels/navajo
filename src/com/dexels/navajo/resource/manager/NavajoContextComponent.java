package com.dexels.navajo.resource.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoContextComponent {
	private NavajoServerContext navajoServerContext;
	private ResourceManager resourceManager;
	private ConfigurationAdmin configAdmin;
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoContextComponent.class);
	
	public void setNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = navajoServerContext;
	}
	

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}

	public void activate() {
		logger.info("Activating context!");
		resourceManager.setConfigAdmin(configAdmin);
		setupResources();
	}

	private void setupResources() {
		FileInputStream fis;
		try {
			logger.info("Looking for datasources in: "+navajoServerContext.getInstallationPath());
			File install = new File(navajoServerContext.getInstallationPath(),"config/datasources.xml");
			fis = new FileInputStream(install);
			resourceManager.loadResourceTml(fis);
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = null;
	}

	public void setResourceManager(ResourceManager r) {
		this.resourceManager = r;
	}

	public void removeResourceManager(ResourceManager r) {
		this.resourceManager = null;
	}

}
