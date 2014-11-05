package com.dexels.githubosgi.impl;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemGitRepositoryManager {
	
	private ConfigurationAdmin configAdmin;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SystemGitRepositoryManager.class);

	private Configuration configuration;
	
	
	public void activate() throws IOException {
		Map<String,String> env = System.getenv();
		String url = env.get("git.repository.url");
		String type = env.get("git.repository.type");
		String storagePath = env.get("git.repository.storage");
		String deployment = env.get("git.repository.deployment");
		if(url==null) {
			logger.warn("No 'git.repository.path' set, so no git repositories will be injected.");
			return;
		}
		if(type==null) {
			logger.warn("No 'git.repository.type' set, so no git repositories will be injected.");
			return;
		}
		if(storagePath==null) {
			logger.warn("No 'git.repository.storage' set, so no git repositories will be injected.");
			return;
		}
		injectConfig(url,type,deployment,storagePath,env);
	}
	


	private void injectConfig(String url, String type, String deployment, String storagePath, Map<String,String> env) throws IOException {
//		githubosgi.gitrepository
		Configuration c = createOrReuse("navajo.gitrepository."+type, "(repository.name=system.managed.repository)");
		Dictionary<String,Object> properties = new Hashtable<String,Object>();
		
		String branch = env.get("git.repository.branch");

		properties.put("repository.type", type);
		properties.put("branch", branch);
		properties.put("name", "system.managed.repository");
		properties.put("url", url);
//		properties.put("autoRefresh", System.getProperty("git.repository.autoRefresh"));
		String sleepTime = env.get("git.repository.sleepTime");
		if(sleepTime!=null) {
			properties.put("sleepTime", sleepTime);
		}
		
		properties.put("repo", "git");
		if(deployment!=null) {
			properties.put("repository.deployment", deployment);
		}
		c.update(properties);	
		Configuration manager =  configAdmin.getConfiguration("navajo.repository.manager",null);
		Dictionary<String,Object> managerProperties = new Hashtable<String,Object>();
		managerProperties.put("storage.path", storagePath);
		manager.update(managerProperties);

	}

	protected Configuration createOrReuse(String pid, final String filter)
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
