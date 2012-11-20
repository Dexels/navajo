package com.dexels.navajo.runtime.provisioning.internal;

import java.io.IOException;
import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.osgi.runtime.ConfigurationInjectionInterface;

public class ConfigurationReceiver implements ConfigurationInjectionInterface {

	private ConfigurationAdmin configurationAdmin;
	private boolean active = false;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ConfigurationReceiver.class);
	
	
	@Override
	public void addConfiguration(String name, Dictionary data) throws IOException {
//		Configuration c = 
		if(!active) {
			logger.error("Can not add configuration: "+name+": Not activated");
			return;
		}
		Configuration c = configurationAdmin.getConfiguration(name,null);
		c.update(data);
	}
	
	@Override
	public String addFactoryConfiguration(String name, Dictionary data) throws IOException {
//		Configuration c = 
		if(!active) {
			logger.error("Can not add configuration: "+name+": Not activated");
			return null;
		}
		Configuration c = configurationAdmin.createFactoryConfiguration(name,null);
		c.update(data);
		return c.getPid();
	}

	@Override
	public void removeConfigutation(String name) throws IOException {
		Configuration c = configurationAdmin.getConfiguration(name);
		logger.info("Deleting config: "+c.getPid());
		c.delete();
	}

	public void setConfigurationAdmin(ConfigurationAdmin ca) {
		this.configurationAdmin = ca;
	}

	/**
	 * the configurationadmin to remove
	 * @param ca
	 */
	public void clearConfigurationAdmin(ConfigurationAdmin ca) {
		this.configurationAdmin = null;
	}

	public void activate() {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}
}
