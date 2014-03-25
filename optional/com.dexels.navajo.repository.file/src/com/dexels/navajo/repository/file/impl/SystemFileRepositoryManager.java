package com.dexels.navajo.repository.file.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemFileRepositoryManager {
	
	private ConfigurationAdmin configAdmin;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SystemFileRepositoryManager.class);
	
	
	public void activate(Map<String,Object> configuration) throws IOException {
		String path = System.getProperty("file.repository.path");
		String type = System.getProperty("file.repository.type");
		String deployment = System.getProperty("file.repository.deployment");
		if(path==null) {
			throw new IOException("No 'file.repository.path' set, so no file repositories will be injected.");
		}
		if(type==null) {
			throw new IOException("No 'file.repository.type' set, so no file repositories will be injected.");
		}
		injectConfig(path,type,deployment);
	}
	
	private void injectConfig(String path, String type, String deployment) throws IOException {
		String repositoryName = "system.managed.repository";
		File resolvedPath = new File(path);
		if(!resolvedPath.exists()) {
			throw new FileNotFoundException("Injected path: "+path+" is not found. Not injecting configuration");
		}
		Configuration c = createOrReuse("navajo.repository."+type, "(repository.name=system.managed.repository)");
		Dictionary<String,Object> properties = new Hashtable<String,Object>();
		// I think this one can be removed:
		properties.put("repository.type", type);
		properties.put("type", type);
		properties.put("repository.name", repositoryName);
		properties.put("repository.folder", path);
		properties.put("repo", "file");
		if(deployment!=null) {
			properties.put("repository.deployment", deployment);
		}
		c.update(properties);	
	}

	protected Configuration createOrReuse(String pid, final String filter)
			throws IOException {
		Configuration cc = null;
		try {
			Configuration[] c = configAdmin.listConfigurations(filter);
			if(c!=null && c.length>1) {
				logger.warn("Multiple configurations found for filter: {}", filter);
			}
			if(c!=null && c.length>0) {
				cc = c[0];
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error in filter: {}",filter,e);
		}
		if(cc==null) {
			cc = configAdmin.createFactoryConfiguration(pid,null);
//			resourcePids.add(cc.getPid());
		}
		return cc;
	}
	
	
	public void deactivate() {
		
		
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
