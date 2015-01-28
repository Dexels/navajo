package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvAppStoreConfigurator {

	private ConfigurationAdmin configAdmin;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EnvAppStoreConfigurator.class);

	private Configuration configuration;


	
	public void activate() throws IOException {
		Map<String,String> env = System.getenv();
		String authorize = env.get("TIPI_STORE_AUTHORIZE");
		String organization = env.get("TIPI_STORE_ORGANIZATION");
		String applicationname = env.get("TIPI_STORE_APPLICATIONNAME");
		String manifestCodebase = env.get("TIPI_STORE_MANIFESTCODEBASE");
		String codebase = env.get("TIPI_STORE_CODEBASE");
		String clientid = env.get("TIPI_STORE_CLIENTID");
		String clientsecret = env.get("TIPI_STORE_CLIENTSECRET");

		if(authorize==null) {
			logger.warn("No 'TIPI_STORE_AUTHORIZE' set, so no appstore configurations will be injected.");
			return;
		}
		if(manifestCodebase==null) {
			logger.warn("No 'TIPI_STORE_MANIFESTCODEBASE' set, so no appstore configurations will be injected.");
			return;
		}
		if(codebase==null) {
			logger.warn("No 'TIPI_STORE_CODEBASE' set, so no appstore configurations will be injected.");
			return;
		}
		injectConfig(authorize,organization,applicationname,manifestCodebase,codebase,clientid,clientsecret,env);
	}
	
	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	/**
	 * @param configAdmin the configAdmin to remove 
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}



	private void injectConfig(String authorize, String organization,
			String applicationname, String manifestCodebase, String codebase,
			String clientid, String clientsecret, Map<String, String> env) throws IOException {

		Configuration c = createOrReuse("tipi.dev.appstore.manager", "(tipi.store.name=env.tipi.store)");
		Dictionary<String,Object> properties = new Hashtable<String,Object>();
		if(clientid!=null) {
			properties.put("tipi.store.clientid", clientid);
		}
		properties.put("tipi.store.name", "env.tipi.store");

		if(clientsecret!=null) {
			properties.put("tipi.store.clientsecret", clientsecret);
		}
		if(organization!=null) {
			properties.put("tipi.store.organization", organization);
		}
		if(applicationname!=null) {
			properties.put("tipi.store.applicationname", applicationname);
		}
		if(codebase!=null) {
			properties.put("tipi.store.codebase", codebase);
		}
		if(manifestCodebase!=null) {
			properties.put("tipi.store.manifestcodebase", manifestCodebase);
		}
		if(authorize!=null) {
			properties.put("authorize", authorize);
		} else {
			properties.put("authorize", "false");

		}
		c.update(properties);	
	}

	private Configuration createOrReuse(String pid, final String filter)
			throws IOException {
		configuration = null;
		try {
			Configuration[] c = configAdmin.listConfigurations(filter);
			if(c!=null && c.length>1) {
				logger.warn("Multiple configurations found for filter: {}", filter);
			}
			if(c!=null && c.length>0) {
				configuration = c[0];
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error in filter: {}",filter,e);
		}
		if(configuration==null) {
			configuration = configAdmin.getConfiguration(pid,null);
		}
		return configuration;
	}
	

	public void deactivate() {
		
	}
}
