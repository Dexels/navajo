package com.dexels.navajo.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoContextInstanceFactory implements NavajoServerContext {
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoContextInstanceFactory.class);

	private File rootPath = null;
	private File settings = null;
//	private BundleContext bundleContext;
	private ConfigurationAdmin configAdmin;

	private final Set<String> resourcePids = new HashSet<String>();


	public void activate(Map<String,Object> properties) {
//		this.bundleContext = context;
		logger.info("NavajoContextInstance activated");
		String installPath = (String) properties.get("installationPath");
		rootPath = new File(installPath);
		settings = new File(rootPath,"settings");
		File config = new File(rootPath,"config");
		File globalResourceFile = new File(config,"resources.xml");
		File[] fd = settings.listFiles();
		File globalPropertyFile = new File(settings,"application.properties");
		Map<String,Object> globalProperties = readProperties(globalPropertyFile);
		Map<String,Message> globalResources = readResources(globalResourceFile);
		for (Message dataSource : globalResources.values()) {
			try {
				addDatasource("*",dataSource);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
		logger.info("Global properties: #"+globalProperties.size());
		for (File file : fd) {
			if(file.isDirectory()) {
				System.err.println("dir found!");
				String name = file.getName();
				try {
					appendInstance(name, file,Collections.unmodifiableMap(globalProperties),Collections.unmodifiableMap(globalResources));
				} catch (IOException e) {
					logger.error("Error appending instance: {}",name,e);
				}
			}
		}
	}
	
	private Map<String,Message> readResources(File resource) {
		Map<String,Message> result = new HashMap<String, Message>();
		FileReader fr = null;
		try {
			fr = new FileReader(resource);
			Navajo n = NavajoFactory.getInstance().createNavajo(fr);
			Message m = n.getMessage("datasources");
			if(m!=null) {
				logger.warn("In datasource definitions, please use 'resources' instead of 'datasources' as top level tag");
			} else {
				m = n.getMessage("resources");
			}
			if(m==null) {
				logger.warn("In datasource definitions, no 'resources' found.");
				return result;
			}
			List<Message> sources = m.getAllMessages();
			for (Message rsrc : sources) {
				result.put(rsrc.getName(), rsrc);
			}
			logger.info("# of resources: "+result.size());
		} catch (FileNotFoundException e) {
			logger.debug("Problem reading file: {}",resource,e);
		} finally {
			if(fr!=null) {
				try {
					fr.close();
				} catch (IOException e) {
					logger.debug("Problem closing file: {}",resource,e);
				}
			}
		}
		return result;
	}

	private Map<String,Object> readProperties(File propertyFile) {
		final Map<String,Object> result = new HashMap<String, Object>();
		if(!propertyFile.exists()) {
			logger.warn("Skipping non-existant global property file: {}",propertyFile.getAbsolutePath());
			return result;
		}
		InputStream fi = null;
		try {
			fi = new FileInputStream(propertyFile);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			for (String s : rb.keySet()) {
				result.put(s, rb.getObject(s));
			}
		} catch (IOException e) {
			logger.warn("Problem reading global property file: {}",propertyFile.getAbsolutePath(), e);
		} finally {
			if(fi!=null) {
				try {
					fi.close();
				} catch (IOException e) {
					logger.debug("Problem closing file: {}",propertyFile,e);
				}
			}
		}
		
		return result;
	}

	private void appendInstance(String name, File instanceFolder, Map<String, Object> globalProperties, Map<String, Message> globalResources) throws IOException {
		logger.info ("Name: "+name);
		Map<String, Object> copyOfProperties = new HashMap<String, Object>(globalProperties);
//		copyOfProperties.putAll(globalProperties);
//		Map<String,Message> copyOfResources = new HashMap<String, Message>(globalResources);
		File config = new File(instanceFolder,"config");
		File instanceProperties = new File(config,"application.properties");
		if(instanceProperties.exists()) {
			final Map<String, Object> instanceSpecific = readProperties(instanceProperties);
			logger.info("spec: "+instanceSpecific.size()+instanceProperties.getAbsolutePath());
			copyOfProperties.putAll(instanceSpecific);
		}
		File instanceResource = new File(config,"resources.xml");
		Map<String,Message> resources = readResources(instanceResource);
//		copyOfResources.putAll(resources);
//		registerInstance(name);
		registerInstanceProperties(name,copyOfProperties);
		registerInstanceResources(name,resources);
	}

	private void registerInstanceResources(String name,Map<String, Message> resources) throws IOException {
		for (Message dataSource : resources.values()) {
			addDatasource(name,dataSource);
		}
	}

	private void registerInstanceProperties(String instance, Map<String, Object> map) throws IOException {
		logger.info("# of properties in {}: "+map.size(),instance);
		final String totalFilter = "(service.factoryPid=navajo.instance.properties)";
		Configuration[] list;
//		Constants.SERVICE_PID
		try {
			list = configAdmin.listConfigurations(totalFilter);
			logger.info("Total # of instance properties: "+(list!=null?list.length:0));
		} catch (InvalidSyntaxException e1) {
			logger.error("Error: ", e1);
		}
		Dictionary<String,Object> settings = new Hashtable<String,Object>(); 
		final String filter = "(&(instance="+instance+")(factoryPid=navajo.instance.properties))";
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
			cc = configAdmin.createFactoryConfiguration("navajo.instance.properties",null);
			resourcePids.add(cc.getPid());
		}
		settings.put("instance", instance);
		for (Entry<String,Object> x : map.entrySet()) {
			settings.put(x.getKey(), x.getValue());
		}
		cc.update(settings);
		logger.debug("Instance properties for source: {} : {}",instance,settings);

	}

	public void deactivate() {
		logger.info("NavajoContextInstance deactivated");
	}

//	private void registerInstance(String instance) throws IOException {
//		logger.info("Registering instance: {}",instance);
//		Dictionary<String,Object> settings = new Hashtable<String,Object>(); 
//		settings.put("instance", instance);
//		final String filter = "(instance="+instance+")";
//		Configuration cc = null;
//		try {
//			Configuration[] c = configAdmin.listConfigurations(filter);
//			if(c!=null && c.length>1) {
//				logger.warn("Multiple configurations found for filter: {}", filter);
//			}
//			if(c!=null && c.length>0) {
//				cc = c[0];
//			}
//		} catch (InvalidSyntaxException e) {
//			logger.error("Error in filter: {}",filter,e);
//		}
//		if(cc==null) {
//			cc = configAdmin.createFactoryConfiguration("navajo.instance",null);
//			resourcePids.add(cc.getPid());
//		}
//		cc.update(settings);
//		logger.debug("Instance settings for source: {} : {}",instance,settings);
//
//	}
	
	private void addDatasource(String instance,Message dataSource) throws IOException {
		String name = dataSource.getName();
		List<Property> props = dataSource.getAllProperties();
		Dictionary<String,Object> settings = new Hashtable<String,Object>(); 
		for (Property property : props) {
			// skip type, it is not a 'real' connection setter
			if(property.getName().equals("type")) {
				continue;
			}
			settings.put(property.getName(), property.getTypedValue());
		}
		settings.put("name", "navajo.resource."+name);
		settings.put("instance", instance);
		String type = (String)dataSource.getProperty("type").getTypedValue();
		
		if(configAdmin==null) {
			logger.debug("No configuration admin, assuming testing");
			return;
		}
//		configAdmin.getConfiguration(arg0)
//		Configuration cc = configAdmin.getConfiguration("navajo.resource."+instance+"."+name,null);
		final String filter = "(&(instance="+instance+")(name=navajo.resource."+name+"))";
		Configuration cc = createOrReuse("navajo.resource."+type, filter);
		cc.update(settings);
		logger.debug("Data source settings for source: {} : {}",name,settings);
		
		addResourceGroup(name,instance,type);
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
			resourcePids.add(cc.getPid());
		}
		return cc;
	}
	

	private void addResourceGroup(String name, String instance, String type) throws IOException {
		Dictionary<String,Object> settings = new Hashtable<String,Object>(); 
		final String filter = "(&(service.factoryPid=navajo.resourcegroup)(name="+name+"))";
		Configuration cc = createOrReuse("navajo.resourcegroup", filter);
		settings.put("name", name);
		settings.put("type", type);
		cc.update(settings);
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

	@Override
	public String getInstallationPath() {
		return rootPath.getAbsolutePath();
	}

	
}
