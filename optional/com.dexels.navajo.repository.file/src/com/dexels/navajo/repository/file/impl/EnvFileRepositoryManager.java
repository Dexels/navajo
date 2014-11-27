package com.dexels.navajo.repository.file.impl;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvFileRepositoryManager {
	
	private ConfigurationAdmin configAdmin;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EnvFileRepositoryManager.class);

	private Configuration configuration;
	
	public void activate() throws IOException {
		Map<String,String> env = System.getenv();
		String path = env.get("FILE_REPOSITORY_PATH");
		String type = env.get("FILE_REPOSITORY_TYPE");
		String deployment = env.get("FILE_REPOSITORY_DEPLOYMENT");
		if(path==null) {
			logger.warn("No 'FILE_REPOSITORY_PATH' set, so no file repositories will be injected.");
			return;
		}
		if(type==null) {
			logger.warn("No 'FILE_REPOSITORY_TYPE' set, so no file repositories will be injected.");
			return;
		}
		if(deployment==null) {
			logger.warn("No 'FILE_REPOSITORY_DEPLOYMENT' set, so no file repositories will be injected.");
			return;
		}
		injectConfig(path,type,deployment,env);
	}
	


	private void injectConfig(String path, String type, String deployment, Map<String,String> env) throws IOException {
//		githubosgi.gitrepository
		Configuration c = createOrReuse("dexels.repository.file", "(repository.name=system.managed.repository)");
		Dictionary<String,Object> properties = new Hashtable<String,Object>();
		
		properties.put("repository.type", type);
		properties.put("name", "system.managed.repository");
		properties.put("repository.folder", path);
//		properties.put("autoRefresh", System.getProperty("git.repository.autoRefresh"));
		properties.put("repo", "git");
		if(deployment!=null) {
			properties.put("repository.deployment", deployment);
		}
		c.update(properties);	
		Configuration manager =  configAdmin.getConfiguration("navajo.repository.manager",null);
		Dictionary<String,Object> managerProperties = new Hashtable<String,Object>();
		manager.update(managerProperties);

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
			configuration = configAdmin.createFactoryConfiguration(pid,null);
		}
		return configuration;
	}
	
	
	public void deactivate() {
		try {
			configuration.delete();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
		
		
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

}
