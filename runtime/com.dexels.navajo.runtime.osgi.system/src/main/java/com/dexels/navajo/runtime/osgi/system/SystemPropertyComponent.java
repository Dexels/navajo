package com.dexels.navajo.runtime.osgi.system;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import com.dexels.navajo.osgi.runtime.ConfigurationInjectionInterface;

public class SystemPropertyComponent {

	private ConfigurationInjectionInterface configurationInterface;

	public void activate() throws IOException {
		String path = System.getProperty("tipi.context");
		String deployment = System.getProperty("deployment");
		String profile = System.getProperty("profile");
		startTipi(path, deployment, profile);
	}
	
	public void deactivate() {
		
	}
	
	public void setConfigurationInterface(ConfigurationInjectionInterface ci) {
		this.configurationInterface = ci;
	}

	/**
	 * The configurationinjectioninterface to remove
	 * @param ci
	 */
	public void clearConfigurationInterface(ConfigurationInjectionInterface ci) {
		this.configurationInterface = null;
	}

	public void startTipi(String path, String deployment, String profile) throws IOException {
		Dictionary<String,String> properties = new Hashtable<String,String>();
		properties.put("tipi.context", path);
		properties.put("deployment", deployment);
		properties.put("profile", profile);
		configurationInterface.addConfiguration("com.dexels.navajo.tipi.swing.application", properties);
	}

}
