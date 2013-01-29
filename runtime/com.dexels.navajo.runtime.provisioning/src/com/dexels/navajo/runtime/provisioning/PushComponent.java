package com.dexels.navajo.runtime.provisioning;

import java.io.IOException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.osgi.runtime.ContextIdentifier;

public class PushComponent {

	private ConfigurationAdmin myConfigurationAdmin = null;
	
	private final Set<ContextIdentifier> contextIdentifiers = new HashSet<ContextIdentifier>();

	
	private final static Logger logger = LoggerFactory
			.getLogger(PushComponent.class);
	private Configuration factoryConfiguration;

	private Dictionary properties;

	@SuppressWarnings("rawtypes")
	public void activate(ComponentContext cc) {

		try {
			long stamp = System.currentTimeMillis();
			
			properties = cc.getProperties();
			 
			logger.info("Push configuration component created.");
			String contextPath = (String) properties.get("contextPath");
			String contextName = (String) properties.get("contextName");
			boolean matches = hasContext(contextName);
			if(!matches) {
				logger.info("Skipping context: "+contextName+" it doesn't match any attached context");
				return;
			}
			logger.info("Setting up navajo context: "+contextName+" at: "+contextPath);
			try {
				setupNavajo(contextName,contextPath);
			} catch (IOException e) {
				logger.error("Setting up navajo failed: ", e);
			}
			logger.info("Activating push provisioning took: "+(System.currentTimeMillis()-stamp) +" millis. ");
		} catch (Throwable e) {
			logger.error("Error activating component",e);
		}
	}

	// Check regardless of leading slashes TODO Check for pull
	private boolean hasContext(String contextName) {

		for (ContextIdentifier c : contextIdentifiers) {
			String con = c.getContextPath();
			String ctxName = contextName;
			if(ctxName.equals(con)) {
				return true;
			}
			if(con!=null && con.startsWith("/")) {
				con = con.substring(1);
			}
			if(ctxName.startsWith("/")) {
				ctxName = ctxName.substring(1);
			}
			if(ctxName.equals(con)) {
				return true;
			}
			
		}
		return false;
	}
	
	private void injectConfig(String contextPath, String servletContextPath, String installationPath) throws IOException {
		factoryConfiguration = myConfigurationAdmin.getConfiguration("navajo.server.http.osgi",null);
		Dictionary<String, String> d = new Hashtable<String,String>();
		d.put("contextPath", contextPath);
		if(servletContextPath!=null) {
			d.put("servletContextPath", servletContextPath);
		}
		d.put("installationPath", installationPath);
		factoryConfiguration.update(d);
	}
	
	private void setupNavajo(String contextName, String contextPath) throws IOException {
		injectConfig(contextName, null, contextPath);
		

	}

	

	public void deactivate() {
		try {
			logger.info("Push provisioning deactivated");
			if(factoryConfiguration!=null) {
				try {
					factoryConfiguration.delete();
				} catch (IOException e) {
					logger.error("Error deregistering service factory: ", e);
				}
			}
		} catch (Throwable e) {
			logger.error("Error activating component",e);
		}
	}


	public void addConfigurationAdmin(ConfigurationAdmin admin) {
		this.myConfigurationAdmin = admin;
	}

	/**
	 * the configurationadmin to remove
	 * @param admin
	 */
	public void clearConfigurationAdmin(ConfigurationAdmin admin) {
		this.myConfigurationAdmin = null;
	}

	public void removeContextIdentifier(ContextIdentifier ci) {
		contextIdentifiers.remove(ci);
	}

	public void addContextIdentifier(ContextIdentifier ci) {
		contextIdentifiers.add(ci);
	}

}
