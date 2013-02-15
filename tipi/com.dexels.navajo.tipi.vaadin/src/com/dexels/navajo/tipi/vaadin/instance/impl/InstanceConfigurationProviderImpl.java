package com.dexels.navajo.tipi.vaadin.instance.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.Set;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.vaadin.instance.InstanceConfigurationProvider;

public class InstanceConfigurationProviderImpl implements
		InstanceConfigurationProvider {

	private ConfigurationAdmin configAdmin;

	private final Map<String,Configuration> resourcePids = new HashMap<String,Configuration>();

	private final Set<String> deployments = new HashSet<String>();
	private final Set<String> profiles = new HashSet<String>();
	private final Map<String,Object> global = new HashMap<String, Object>();
//	private final Map<String,Map<String,Object>> profileSettings = new HashMap<String,Map<String,Object>>();
	private final Map<String,Map<String,Map<String,Object>>> deploymentSettings = new HashMap<String,Map<String,Map<String,Object>>>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(InstanceConfigurationProviderImpl.class);

	private File rootFolder;

	private ServiceRegistration<Servlet> fileRegistration;
	
	@Override
	public Set<String> getProfiles() {
		return Collections.unmodifiableSet(profiles);
	}

	@Override
	public Set<String> getDeployments() {
		return Collections.unmodifiableSet(deployments);
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
	
	public void activate(Map<String,Object> parameters, BundleContext bundleContext) {
		deployments.clear();
		profiles.clear();
		resourcePids.clear();
//		"/Users/frank/git/memberportal/com.sportlink.memberportal.tipi"
		String root = (String) parameters.get("path");
		logger.info("========>  Activating");
		rootFolder = new File(root);
		File arguments = new File(rootFolder,"settings/arguments.properties");
		if(!arguments.exists()) {
			logger.error("No arguments found: "+arguments.getAbsolutePath());
		}
		
		try {
			emitLogbackConfiguration(root);
			emitTipiGlobal(root);

		} catch (IOException e1) {
			logger.error("Error emitting global configurations:",e1);
		}
		
		FileInputStream fis;
		try {
			File profileDir = new File(rootFolder,"settings/profiles");
			String[] files = profileDir.list();
			for (String file : files) {
				String[] parts = file.split("\\.");
				if(parts.length>1) {
					profiles.add(parts[0]);
				}
			}			
			fis = new FileInputStream(arguments);
			PropertyResourceBundle globalArguments = new PropertyResourceBundle(fis);
			Set<String> keys = globalArguments.keySet();
			for (String key : keys) {
				String[] split = key.split("/");
				if(split.length>1) {
					// deployment specific, but for all profiles
					final String deploymentName = split[0];
					final String deploymentKey = split[1];
					deployments.add(deploymentName);
					// make sure the deployment exists
					Map<String,Map<String,Object>> deploymentMap = deploymentSettings.get(deploymentName);
					if(deploymentMap==null) {
						deploymentMap = new HashMap<String,Map<String,Object>>();
						deploymentSettings.put(deploymentName, deploymentMap);
					}
					// add variable to all profiles
					for (String profile : profiles) {
						Map<String,Object> profileMap = deploymentMap.get(profile);
						if(profileMap==null) {
							profileMap = new HashMap<String, Object>();
							deploymentMap.put(profile, profileMap);
						}
						profileMap.put(deploymentKey, globalArguments.getObject(key));
					}
					
				} else {
					// global argument
					global.put(key, globalArguments.getObject(key));
				}
			}
			// loop again over all profiles:
			for (String file : files) {
				String[] parts = file.split("\\.");
				if(parts.length<2) {
					continue;
				}
				String currentProfile = parts[0];
				File profileFile = new File(profileDir,file);
				if(profileFile.isDirectory()) {
					continue;
				}
				fis = new FileInputStream(profileFile);
				PropertyResourceBundle profileArguments = new PropertyResourceBundle(fis);
				Set<String> profileKeys = profileArguments.keySet();
				for (String profileKey : profileKeys) {
					String[] profileSplit = profileKey.split("/");
					if(profileSplit.length>1) {
						String currentDeployment = profileSplit[0];
						String currentKey = profileSplit[1];
						deploymentSettings.get(currentDeployment).get(currentProfile).put(currentKey,profileArguments.getObject(profileKey));
					} else {
						// profileKey is for all deployments
						for (String currentDeployment : deployments) {
							deploymentSettings.get(currentDeployment).get(currentProfile).put(profileKey, profileArguments.getObject(profileKey));
						}
					}
				}
			}


//			registerFileServlet(rootFolder.getAbsolutePath(), bundleContext);
			emitDeployments();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
	
	private void emitLogbackConfiguration(String filePath) throws IOException {
//		emitIfChanged("navajo.logback", filter, settings)
		Dictionary<String,Object> settings = new Hashtable<String, Object>();
		settings.put("rootPath", filePath);
		settings.put("logbackPath", "settings/logback.xml");
		emitConfig("navajo.logback",settings);
	}

	private void emitTipiGlobal(String filePath) throws IOException {
//		emitIfChanged("navajo.logback", filter, settings)
		Dictionary<String,Object> settings = new Hashtable<String, Object>();
		settings.put("rootPath", filePath);
		emitConfig("tipi.instance.global",settings);
	}

	
	protected ServiceRegistration<Servlet> registerServlet(BundleContext bundleContext,
			final String alias, Servlet s) {
		Dictionary<String, Object> vaadinRegistrationSettings = new Hashtable<String, Object>();
		 vaadinRegistrationSettings.put("alias", alias);
			return bundleContext.registerService(Servlet.class, s, vaadinRegistrationSettings);
	}

	public void emitDeployments() throws IOException {
		for (String deployment : deployments) {
			for (String profile : profiles) {
				final String filter = "(&(tipi.instance.profile="+profile+")(name=tipi.instance.deployment."+deployment+"))";
				Map<String,Object> cc = deploymentSettings.get(deployment).get(profile);
				Hashtable<String, Object> o = new Hashtable<String, Object>();
				o.putAll(cc);
				o.put("tipi.instance.profile", profile);
				o.put("tipi.instance.deployment", deployment);
				o.put("tipi.instance.path", rootFolder.getAbsolutePath());
				o.putAll(global);
//				for (Entry<String,Object> e : global.entrySet()) {
//					o.put(e.getKey(), e.getValue());
//				}
				String pid = "tipi.instance";
				String implementation = (String) o.get("tipi.instance.implementation");
				if(implementation!=null) {
					pid = "tipi.instance."+implementation;
				}
				emitFactoryIfChanged(pid, filter, o);
			}
		}
	}

	
	private void emitConfig(String pid, Dictionary<String,Object> settings) throws IOException {
		Configuration config =  configAdmin.getConfiguration(pid,null);
		updateIfChanged(config, settings);
	}

	private void emitFactoryIfChanged(String pid, String filter,Dictionary<String,Object> settings) throws IOException {
		updateIfChanged(createOrReuseFactoryConfiguration(pid, filter), settings);
	}
	
	protected Configuration createOrReuseFactoryConfiguration(String pid, final String filter)
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
			resourcePids.put(cc.getPid(),cc);
		}
		return cc;
	}
	
	private void updateIfChanged(Configuration c, Dictionary<String,Object> settings) throws IOException {
		Dictionary<String,Object> old = c.getProperties();
		if(old!=null) {
			if(!old.equals(settings)) {
				c.update(settings);
			} else {
				logger.info("Ignoring equal");
			}
		} else {
			// this will make this component 'own' this configuration, unsure if this is desirable.
			resourcePids.put(c.getPid(),c);
			c.update(settings);
		}
	}
	
	
	public void deactivate() {
		logger.info(">>>>>> deactivating tipi instance provider");
		for (Entry<String,Configuration> entry : resourcePids.entrySet()) {
			try {
				entry.getValue().delete();
			} catch (IOException e) {
				logger.error("Problem deleting configuration for pid: "+entry.getKey(),e);
			}
		}
		if(fileRegistration!=null) {
			fileRegistration.unregister();
		}
		
	}
	
}
