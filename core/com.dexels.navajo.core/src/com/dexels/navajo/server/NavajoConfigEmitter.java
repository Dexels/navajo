/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.compiled.api.CachedExpressionEvaluator;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoConfigEmitter implements EventHandler {

	private static final String CONFIG_SERVER_XML = "config/server.xml";
	private static final int MAX_ACCESS_SET_SIZE = 50;
	private static final Logger logger = LoggerFactory
			.getLogger(NavajoConfigEmitter.class);

	private NavajoServerContext navajoContext;
	private ConfigurationAdmin myConfigurationAdmin = null;
	private final Set<Configuration> registeredConfigurations = new HashSet<>();
	
	public void setContext(NavajoServerContext nsc) {
		this.navajoContext = nsc;
	}

	
	/**
	 * @param nsc the context to remove 
	 */
	public void removeContext(NavajoServerContext nsc) {
		this.navajoContext = null;
	}
		

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.myConfigurationAdmin = configAdmin;
	}

	/**
	 * @param configAdmin the configAdmin to remove 
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.myConfigurationAdmin = null;
	}

	
	
	public void activate() {
		parseServerXml();
	}


	private void parseServerXml() {
		try {
			String rootPath = this.navajoContext.getInstallationPath();
			File rp = new File(rootPath);
			File serverXml = new File(rp,CONFIG_SERVER_XML);
			URL srv = serverXml.toURI().toURL();
			final String absolutePath = properDir(rp.getAbsolutePath());
			createNavajoConfigConfiguration(srv, absolutePath);
		} catch (MalformedURLException e) {
			logger.error("Parse error: ",e);
		} catch (IOException e) {
			logger.error("IO error: ",e);
		} catch (SystemException e) {
			logger.error("System error: ",e);
		} catch(Throwable e) {
			logger.error("Unexpected error activating dispatcher configuration factory: ",e);
		}
	}

	public void deactivate() {
		for (Configuration c : registeredConfigurations) {
			try {
				c.delete();
			} catch (IOException e) {
				logger.error("Problem deleting configuraiton: "+c.getPid(),e);
			}
		}
		registeredConfigurations.clear();
	}	
	
	private String getMessageValueWithDefault(String path, String defaultValue, Message body) {
		Property p = body.getProperty(path);
		if(p==null) {
			return defaultValue;
		}
		String val = (String) p.getTypedValue();
		if(val==null) {
			return defaultValue;
		}
		return val;
	}
	
	private void createNavajoConfigConfiguration(URL configurationUrl,
			String rootPath) throws IOException, SystemException {
		InputStream is = configurationUrl.openStream();
		logger.debug("Setting up configuration for rootpath: {}",rootPath);
		logger.debug("Setting up configuration url: {}",configurationUrl);
		Dictionary<String, Object> data = new Hashtable<>();
		Navajo configuration = NavajoFactory.getInstance().createNavajo(is);

		Message body = configuration.getMessage("server-configuration");
		if (body == null) {
			throw new SystemException(-1,
					"Could not read configuration file server.xml");
		}

		// Get the instance name.
		data.put("instanceName", getInstanceName(body));
		// Get the instance group.
		data.put("instanceGroup", getInstanceGroup(body));

		String configPath = properDir(rootPath + getMessageValueWithDefault("paths/configuration", "config", body));
		String adapterPath = properDir(rootPath + getMessageValueWithDefault("paths/adapters", "adapters", body));
		String scriptPath = properDir(rootPath + getMessageValueWithDefault("paths/scripts", "scripts", body));
		data.put("configPath", configPath);
		data.put("adapterPath", adapterPath);
		data.put("scriptPath", scriptPath);
		Property resourceProperty = body.getProperty("paths/resource");
		if (resourceProperty != null) {
			String resourcePath = properDir(rootPath
					+ resourceProperty.getValue());
			data.put("resourcePath", resourcePath);

		}

		String compiledScriptPath = (body.getProperty("paths/compiled-scripts") != null ? properDir(rootPath
				+ body.getProperty("paths/compiled-scripts").getValue())
				: "");
		data.put("compiledScriptPath", compiledScriptPath);

		Property descriptionProviderProperty = body
				.getProperty("description-provider/class");
		String descriptionProviderClass = null;
		if (descriptionProviderProperty != null) {
			descriptionProviderClass = descriptionProviderProperty.getValue();
			if (descriptionProviderClass != null) {
				data.put("descriptionProviderClass", descriptionProviderClass);
			}
		}

		Dictionary<String, Object> d = new Hashtable<>();
		d.put("enable", true);
		injectConfiguration("navajo.server.statistics", d);

		Property s = body.getProperty("parameters/async_timeout");
		double asyncTimeout = 3600 * 1000; // default 1 hour.
		if (s != null) {
			asyncTimeout = Float.parseFloat(s.getValue()) * 1000;
		}

		boolean enableAsync = (body.getProperty("parameters/enable_async") == null || body
				.getProperty("parameters/enable_async").getValue()
				.equals("true"));
		d = new Hashtable<>();
		d.put("enable", enableAsync);
		d.put("asyncTimeout", asyncTimeout);
		injectConfiguration("navajo.server.async", d);

		boolean enableIntegrityWorker = (body
				.getProperty("parameters/enable_integrity") == null || body
				.getProperty("parameters/enable_integrity").getValue()
				.equals("true"));

		d = new Hashtable<>();
		d.put("enable", enableIntegrityWorker);
		injectConfiguration( "navajo.server.integrity", d);

		boolean enableLockManager = (body
				.getProperty("parameters/enable_locks") == null || body
				.getProperty("parameters/enable_locks").getValue()
				.equals("true"));

		d = new Hashtable<>();
		d.put("enable", enableLockManager);
		injectConfiguration("navajo.server.lockmanager", d);

		int maxAccessSetSize = (body.getProperty("parameters/max_webservices") == null ? MAX_ACCESS_SET_SIZE
				: Integer.parseInt(body.getProperty(
						"parameters/max_webservices").getValue()));
		data.put("maxAccessSetSize", maxAccessSetSize);
		data.put("compileScripts", true);

		// Get document class implementation.
		String documentClass = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

		data.put("documentClass", documentClass);

		if (documentClass != null) {
			System.setProperty("com.dexels.navajo.DocumentImplementation",
					documentClass);
			NavajoFactory.resetImplementation();
			NavajoFactory.getInstance();
			NavajoFactory.getInstance().setExpressionEvaluator(
					new CachedExpressionEvaluator());
		}
		
		addAllProperties(body.getMessage("parameters"),data);
		updateIfChanged("navajo.server.config", data);
	}


	private String getInstanceGroup(Message body) {
		String instanceGroup = (body.getProperty("instance_group") != null ? body
				.getProperty("instance_group").getValue() : null);
		String env = System.getenv("CLUSTER");
		if(env!=null) {
			if(instanceGroup!=null) {
				logger.warn("Instance group defined in server.xml: {} but overridden by environment var CLUSTER: {}",instanceGroup,env);
			}
			return env;
		}
		if (instanceGroup == null) {
			throw new IllegalArgumentException(
					"instance_group in server.xml not found.");
		}
		return instanceGroup;
	}


	private String getInstanceName(Message body) {
		String instanceName = (body.getProperty("instance_name") != null ? body
				.getProperty("instance_name").getValue() : null);
		String env = System.getenv("INSTANCENAME");
		if(env!=null) {
			if(instanceName!=null) {
				logger.warn("Instance name defined in server.xml: {} but overridden by environment var INSTANCENAME: {}",instanceName, env);
			}
			return env;
		}
		if (instanceName == null) {
			throw new IllegalArgumentException(
					"instance_name in server.xml not found.");
		}
		return instanceName;
	}
/**
 * Dump all parameter properties into the fray:
 * @param message
 * @param data
 */
	private void addAllProperties(Message message,
			Dictionary<String, Object> data) {
	    List<Property> all = message.getAllProperties();
		for (Property property : all) {
			String name = property.getName();
			final Object old = data.get(name);
			final Object newValue = property.getTypedValue();
			if(old!=null) {
				logger.warn("Will not append property: {} (with value: {}) to configuration, as it will overwrite the present value of: {}",name,newValue,old);
			} else {
				data.put(name, newValue);
			}
		}
	}


	private void injectConfiguration( String pid,
			Dictionary<String, Object> d) throws IOException {
		Configuration c = myConfigurationAdmin.getConfiguration(pid,null);
		registeredConfigurations.add(c);
		c.update(d);
		
	}
	
	private void updateIfChanged(String pid, Dictionary<String,Object> settings) throws IOException {
		Configuration c = myConfigurationAdmin.getConfiguration(pid,null);
		Dictionary<String,Object> old = c.getProperties();
		if(old!=null) {
			if(!old.equals(settings)) {
				c.update(settings);
			} else {
				logger.info("Ignoring equal");
			}
		} else {
			logger.info("Updating config for pid: {}", c.getPid());
			c.update(settings);
		}
	}

	private static final String properDir(String in) {
		return in + (in.endsWith("/") ? "" : "/");
	}


	@Override
	public void handleEvent(Event e) {
		
		List<String> paths = new ArrayList<>();
		paths.add(CONFIG_SERVER_XML);
		if(RepositoryEventParser.touched(e,paths)) {
			parseServerXml();
		}
	}
}
