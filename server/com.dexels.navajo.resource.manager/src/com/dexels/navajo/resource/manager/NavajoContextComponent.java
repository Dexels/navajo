package com.dexels.navajo.resource.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.osgi.service.cm.Configuration;
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
		setupTesterUser();
	}

	public void deactivate() {
		logger.info("Deactivating context!");
	}

	
	private void setupResources() {
		FileInputStream fis = null;
		try {
			logger.info("Looking for datasources in: "+navajoServerContext.getInstallationPath());
			File install = new File(navajoServerContext.getInstallationPath(),"config/datasources.xml");
			fis = new FileInputStream(install);
			resourceManager.loadResourceTml(fis);
			fis.close();
		} catch (IOException e) {
			logger.error("Error reading datasources file: ", e);
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Error closing datasources file: ", e);
				}
			}
		}
	}

	private void setupTesterUser() {
		FileInputStream fis = null;
		try {
			logger.debug("Looking for client.properties in: "+navajoServerContext.getInstallationPath());
			File install = new File(navajoServerContext.getInstallationPath(),"config/client.properties");
			fis = new FileInputStream(install);
			resourceManager.loadResourceTml(fis);
			ResourceBundle b = new PropertyResourceBundle(fis);
			if(! b.containsKey("username")) {
				logger.error("No username found in client.properties");
			}
			
			processClientBundle(b);
			fis.close();
		} catch (IOException e) {
			logger.error("Error reading client.properies file: ", e);
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Error closing client.properties file: ", e);
				}
			}
			
		}
	}
	
	private void processClientBundle(ResourceBundle b) {
		try {
			Configuration config = configAdmin.getConfiguration("com.dexels.navajo.localclient");
			Dictionary dt = new Hashtable<String,String>();
			for(String key : b.keySet()) {
				logger.info("Key: "+key+" value: "+b.getString(key));
			}
			
			dt.put("user", b.getString("username"));
			dt.put("password", b.getString("password"));
			config.update(dt);
			logger.info("client module registration complete.");
		} catch (IOException e) {
			logger.error("Adding configuration for client.properties: ", e);
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
