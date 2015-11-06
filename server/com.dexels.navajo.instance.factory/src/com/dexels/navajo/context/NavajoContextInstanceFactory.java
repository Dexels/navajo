package com.dexels.navajo.context;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoContextInstanceFactory implements NavajoServerContext {
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoContextInstanceFactory.class);

	private final boolean sharableResources = false;

	// private BundleContext bundleContext;
	private ConfigurationAdmin configAdmin;
	
	private Set<Configuration> ownedConfigurations = new HashSet<Configuration>();

//	private int modified = 0;
//	private int registered = 0;
//	private int unregistering = 0;
	// private final Map<String,Set<String>> aliasesForDataSource = new
	// HashMap<String, Set<String>>();
	private final Set<String> resourcePids = new HashSet<String>();

	public NavajoContextInstanceFactory() {
	}

	private RepositoryInstance repositoryInstance;

	public void setRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = ri;
	}

	public void clearRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = null;
	}

	public void activate(BundleContext context) throws FileNotFoundException {
			String deployment = repositoryInstance.getDeployment();
			startInstanceFactory(repositoryInstance.getRepositoryFolder(),
					deployment);

	}

	private void startInstanceFactory(File rootPath, String deployment) throws FileNotFoundException {
		File settings = new File(rootPath, "settings");
		File config = new File(rootPath, "config");
		if(!rootPath.exists()) {
			throw new FileNotFoundException("No rootFolder found: "+rootPath.getAbsolutePath());
		}
		if(!config.exists()) {
			throw new FileNotFoundException("No config folder not found: "+config.getAbsolutePath());
		}
		if(!settings.exists()) {
			throw new FileNotFoundException("No settings folder not found: "+settings.getAbsolutePath());
		}
		File globalResourceFile = new File(config, "resources.xml");
		File[] fd = settings.listFiles();
		File globalPropertyFile = new File(settings, "application.properties");
		Map<String, Object> globalProperties = readProperties(
				globalPropertyFile, deployment);
		registerGlobalConfiguration(globalProperties);
		Map<String, Set<String>> aliases = new HashMap<String, Set<String>>();
		Map<String, Message> globalResources = readResources(
				globalResourceFile, aliases, deployment);
		for (Message dataSource : globalResources.values()) {
			try {
				addDatasource(null, dataSource, aliases);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
		logger.info("Global properties: #" + globalProperties.size());
		for (File file : fd) {
			if (file.isDirectory()) {
				logger.debug("dir found: " + file.getAbsolutePath());
				String name = file.getName();
				try {
					appendInstance(name, file,
							Collections.unmodifiableMap(globalProperties),
							Collections.unmodifiableMap(globalResources),
							deployment);
				} catch (IOException e) {
					logger.error("Error appending instance: {}", name, e);
				}
			}
		}
		addWatchedFolder(config, ".*\\.cfg", "config");

		File adapters = new File(rootPath, "adapters");

		addWatchedFolder(adapters, "", "adapters");
	}

	private void addWatchedFolder(File folder, String fileFilter,
			String configName) {
		if (!folder.exists()) {
			logger.debug("Not watching folder: " + folder.getAbsolutePath()
					+ " because it does not exist");
			return;
		}
		try {
			Configuration cc = getUniqueResourceConfig(folder.getAbsolutePath());
			Dictionary<String, Object> d = new Hashtable<String, Object>();
			d.put("felix.fileinstall.dir", folder.getAbsolutePath());
			d.put("felix.fileinstall.filter", fileFilter);
			d.put("felix.fileinstall.enableConfigSave", "false");
			d.put("configName", configName);
			updateIfChanged(cc, d);
			ownedConfigurations.add(cc);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

	}

	private Map<String, Message> readResources(File resource,
			Map<String, Set<String>> aliases, String deployment) {
		Map<String, Message> result = new HashMap<String, Message>();
		FileReader fr = null;
		try {
			fr = new FileReader(resource);
			Navajo n = NavajoFactory.getInstance().createNavajo(fr);
			Message resources = n.getMessage("datasources");
			Message deployments = n.getMessage("deployments");
			Message aliasMessage = n.getMessage("alias");
			if (resources != null) {
				logger.warn("In datasource definitions, please use 'resources' instead of 'datasources' as top level message name");
			} else {
				resources = n.getMessage("resources");
			}
			// if(resources==null) {
			// logger.warn("In datasource definitions, no 'resources' found.");
			// return result;
			// }
			appendResources(aliases, result, resources, aliasMessage);
			logger.info("# of (deployment independent): resources "
					+ result.size());
			if (deployment != null && deployments != null) {
				for (Message deploymentMessage : deployments.getAllMessages()) {
					String name = deploymentMessage.getName();
					if (name.equals(deployment)) {
						Message deploymentResources = deploymentMessage
								.getMessage("resources");
						Message deploymentAliasMessage = deploymentMessage
								.getMessage("alias");
						// Map<String, Set<String>> deploymentAliases = new
						// HashMap<String, Set<String>>(aliases);
						appendResources(aliases, result, deploymentResources,
								deploymentAliasMessage);
					} else {
						logger.debug("Ignoring not-matching datasource ("
								+ name + " vs. " + deployment + ")");
					}
				}
			} else {
				logger.warn("No deployment whatsoever, ignoring all deployment specific sources");
				logger.warn("deployment: "+deployment);
				if(deployments!=null) {
					deployments.write(System.err);
				} else {
					logger.warn("No deployments message");
				}
			}

		} catch (FileNotFoundException e) {
			logger.debug("Problem reading file: {}", resource, e);
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					logger.debug("Problem closing file: {}", resource, e);
				}
			}
		}
		return result;
	}

	private void appendResources(Map<String, Set<String>> aliases,
			Map<String, Message> result, Message resources, Message aliasMessage) {
		if (aliasMessage != null) {
			List<Property> aliasProps = aliasMessage.getAllProperties();
			for (Property property : aliasProps) {
				String aliasValue = (String) property.getTypedValue();
				String name = property.getName();
				Set<String> found = aliases.get(aliasValue);
				if (found == null) {
					found = new HashSet<String>();
					aliases.put(aliasValue, found);
				}
				found.add(name);
			}
		}
		if (resources != null) {
			List<Message> sources = resources.getAllMessages();
			for (Message rsrc : sources) {
				result.put(rsrc.getName(), rsrc);
			}
		}
	}

	private Map<String, Object> readProperties(File propertyFile,
			String deployment) {
		final Map<String, Object> result = new HashMap<String, Object>();
		if (!propertyFile.exists()) {
			logger.warn("Skipping non-existant global property file: {}",
					propertyFile.getAbsolutePath());
			return result;
		}
		InputStream fi = null;
		try {
			fi = new FileInputStream(propertyFile);
			ResourceBundle rb = new PropertyResourceBundle(fi);
			for (String s : rb.keySet()) {
				if (deployment != null) {
					if (s.indexOf("/") >= 0) {
						// slash in key:
						String[] parts = s.split("/");
						if (parts[0].equals(deployment)) {
							// matched
							result.put(parts[1], rb.getObject(s));
						} else {
							// skip altogether
						}
					} else {
						result.put(s, rb.getObject(s));
					}
				} else {
					result.put(s, rb.getObject(s));
				}
			}

		} catch (IOException e) {
			logger.warn("Problem reading global property file: {}",
					propertyFile.getAbsolutePath(), e);
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException e) {
					logger.debug("Problem closing file: {}", propertyFile, e);
				}
			}
		}

		return result;
	}

	private void appendInstance(String name, File instanceFolder,
			Map<String, Object> globalProperties,
			Map<String, Message> globalResources, String deployment)
			throws IOException {
		logger.info("Instance name: " + name);
		if (skipDeployment(instanceFolder, deployment)) {
			return;
		}
		Map<String, Object> copyOfProperties = new HashMap<String, Object>(
				globalProperties);
		// copyOfProperties.putAll(globalProperties);
		// Map<String,Message> copyOfResources = new HashMap<String,
		// Message>(globalResources);
		File config = new File(instanceFolder, "config");
		File instanceProperties = new File(config, "application.properties");
		if (instanceProperties.exists()) {
			final Map<String, Object> instanceSpecific = readProperties(
					instanceProperties, deployment);
			logger.info("spec: " + instanceSpecific.size()
					+ instanceProperties.getAbsolutePath());
			copyOfProperties.putAll(instanceSpecific);
		}
		File instanceResource = new File(config, "resources.xml");
		Map<String, Set<String>> aliases = new HashMap<String, Set<String>>();
		Map<String, Message> resources = readResources(instanceResource,
				aliases, deployment);

//		registerAuthorization(name, instanceFolder);
		// copyOfResources.putAll(resources);
		// registerInstance(name);
		registerInstanceProperties(name, copyOfProperties);
		registerInstanceResources(name, resources, aliases);
		registerLocalClients(name, instanceFolder, deployment);
	}

	private boolean skipDeployment(File instanceFolder, String deployment) {
		File deploymentFile = new File(instanceFolder, "deployment.cfg");
		if (!deploymentFile.exists()) {
			return false;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(deploymentFile);
			PropertyResourceBundle prb = new PropertyResourceBundle(is);
			String deploymentList = prb.getString("deployment");
			if (deploymentList == null) {
				logger.warn(
						"Bad deployment file in {}",
						instanceFolder.getAbsolutePath()
								+ " : Key ' deployment' missing. Skipping instance.");
				return true;
			}
			String[] parts = deploymentList.split(",");
			for (String element : parts) {
				if (element.equals(deployment)) {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	private void registerLocalClients(String name, File instanceFolder,
			String deployment) {
		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("instance", name);
		File clientProperties = new File(instanceFolder, "navajoclient.cfg");
		if (!clientProperties.exists()) {
			logger.debug("Ignoring non existing navajoclient.cfg");
			return;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(clientProperties);
			PropertyResourceBundle prb = new PropertyResourceBundle(is);
			Enumeration<String> en = prb.getKeys();
			do {
				String next = en.nextElement();
				if (next.indexOf("/") != -1) {
					String[] parts = next.split("/");
					if (!parts[0].equals(deployment)) {
						continue;
					} else {
						settings.put(parts[1], prb.getObject(next));
					}
				} else {
					settings.put(next, prb.getObject(next));
				}
			} while (en.hasMoreElements());
			injectLocalClient(name, settings);
		} catch (Exception e) {
			logger.error("Error: ", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}

	}

	private void injectLocalClient(String instance, Map<String, Object> settings)
			throws IOException {
		final String filter = "(&(instance=" + instance
				+ ")(service.factoryPid=navajo.local.client))";
		Configuration cc = createOrReuse("navajo.local.client", filter);
		Dictionary<String, Object> dict = new Hashtable<String, Object>(
				settings);
		updateIfChanged(cc, dict);

	}

	private void registerAuthorization(String instance, File instanceFolder)
			throws IOException {

		File authFolder = new File(instanceFolder, "authorization");
		if (!authFolder.exists()) {
			return;
		}
		File[] impls = authFolder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory();
			}
		});
		for (File file : impls) {
			String name = file.getName();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("authorizationFolder", file.getAbsolutePath());
			map.put("multitenant", true);
			registerConfiguration(instance, map, "navajo.authorization."+name);
		}

	}

	private void registerInstanceResources(String name,
			Map<String, Message> resources, Map<String, Set<String>> aliases)
			throws IOException {
		for (Message dataSource : resources.values()) {
			addDatasource(name, dataSource, aliases);
		}
	}
	
	private void registerGlobalConfiguration(Map<String, Object> map) {

		Dictionary<String, Object> settings = new Hashtable<String, Object>();
		final String filter = "(&(instance=default) (factoryPid=" + "navajo.global.manager"
				+ "))";
		settings.put("instance", "default");
		Configuration cc;
		try {
			cc = createOrReuse("navajo.global.manager", filter);
			for (Entry<String, Object> x : map.entrySet()) {
				settings.put(x.getKey(), x.getValue());
			}
			updateIfChanged(cc, settings);
		} catch (IOException e) {
			logger.error("Error appending global config: {}",  e);
		}
		


	}

	// navajo.instance.properties
	private void registerInstanceProperties(String instance,
			Map<String, Object> map) throws IOException {
		registerConfiguration(instance, map, "navajo.global.manager");
	}

	private void registerConfiguration(String instance,
			Map<String, Object> map, String pid) throws IOException {

		Dictionary<String, Object> settings = new Hashtable<String, Object>();
		final String filter = "(&(instance=" + instance + ")(factoryPid=" + pid
				+ "))";
		Configuration cc = createOrReuse(pid, filter);
		settings.put("instance", instance);
		for (Entry<String, Object> x : map.entrySet()) {
			settings.put(x.getKey(), x.getValue());
		}
		updateIfChanged(cc, settings);
		logger.debug("Instance properties for source: {} : {}", instance,
				settings);

	}

	public void deactivate() {
		for (Configuration owned : ownedConfigurations) {
			logger.debug("Deleting configuration: "+owned.getPid());
			try {
				owned.delete();
			} catch (IOException e) {
				logger.error("Failed to delete owned (?) configuration: {}",owned.getPid(), e);
			}
		}
		logger.info("NavajoContextInstance deactivated");
	}

	private void addDatasource(String instance, Message dataSource,
			Map<String, Set<String>> aliases) throws IOException {
		String name = dataSource.getName();
		List<Property> props = dataSource.getAllProperties();
		Dictionary<String, Object> settings = new Hashtable<String, Object>();
		for (Property property : props) {
			// skip type, it is not a 'real' connection setter
			if (property.getName().equals("type")
					|| property.getName().equals("alias")) {
				continue;
			}
			// Conversion
			if (property.getName().equals("username")) {
				settings.put("user", property.getTypedValue());
			} else {
				settings.put(property.getName(), property.getTypedValue());
			}

		}
		Set<String> aliaseSet = aliases.get(name);
		if (aliaseSet != null) {
			for (String alias : aliaseSet) {
				settings.put(alias, "alias");
			}
			Vector<String> aliasVector = new Vector<String>(aliaseSet);
			aliasVector.add(name);
			settings.put("aliases", aliasVector);
		}
		settings.put("name", name);
		if (instance != null) {
			settings.put("instance", instance);
			settings.put(instance, "instance");
		}
		
		Property typeProperty = dataSource.getProperty("type");
		if(typeProperty==null) {
			throw new NullPointerException("No type property missing for instance: "+instance+" and name: "+name);
		}
		String type = (String) typeProperty.getTypedValue();

		if (configAdmin == null) {
			logger.warn("No configuration admin, assuming testing");
			return;
		}
		String uniqueId = getUniqueId(settings);
		if(uniqueId!=null) {
			settings.put("navajo.uniqueid", uniqueId);
		}
		final String filter = createFilter(instance, name, settings, type,uniqueId);
		Configuration cc = createOrReuse("navajo.resource." + type, filter);
		appendIfChanged(cc, settings);
		logger.debug("Data source settings for source: {} : {}", name, settings);

//		addResourceGroup(name, instance, type, settings);
	}

	private String createFilter(String instance, String name,
			Dictionary<String, Object> settings, String type, String uniqueId) {
		if(this.sharableResources && uniqueId!=null) {
			final String filter;
			filter = "(&(navajo.uniqueid=" + uniqueId
					+ ")(service.factoryPid=navajo.resource." + type + "))";
			return filter;
			
		} else {
			final String filter;
			if (instance == null) {
				filter = "(&(name=navajo.resource." + name
						+ ")(service.factoryPid=navajo.resource." + type + "))";
			} else {
				filter = "(&(instance=" + instance + ")(name=navajo.resource."
						+ name + ")(service.factoryPid=navajo.resource." + type
						+ "))";
			}
			return filter;
		}
	}

	/**
	 * Create a unique id based on url and username, if available
	 * @param settings
	 * @return id, or null
	 */
	private String getUniqueId(Dictionary<String, Object> settings) {
		String url = (String) settings.get("url");
		String user = (String) settings.get("user");
		if(user!=null && url!=null) {
			String unique = url+"|"+user;
			logger.info("Created uuid: "+unique);
			return unique;
		}
		return null;
	}

	private Configuration createOrReuse(String pid, final String filter)
			throws IOException {
		Configuration cc = null;
		try {
			Configuration[] c = configAdmin.listConfigurations(filter);
			if (c != null && c.length > 1) {
				logger.warn("Multiple configurations found for filter: {}",
						filter);
			}
			if (c != null && c.length > 0) {
				cc = c[0];
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error in filter: {}", filter, e);
		}
		if (cc == null) {
			cc = configAdmin.createFactoryConfiguration(pid, null);
			resourcePids.add(cc.getPid());
			ownedConfigurations.add(cc);
		}
		return cc;
	}

	private Configuration getUniqueResourceConfig(String path)
			throws IOException {
		final String factoryPid = "org.apache.felix.fileinstall";
		String filter = "(&(service.factoryPid=" + factoryPid
				+ ")(felix.fileinstall.dir=" + path + "))";
		Configuration[] cc;
		try {
			cc = configAdmin
					.listConfigurations(filter);
		} catch (InvalidSyntaxException e) {
			throw new IOException("Problem parsing filter: "+filter,e);
		}
		if (cc != null) {

			if (cc.length != 1) {
				logger.info("Odd length: " + cc.length);
			}
			return cc[0];
		} else {
			logger.info("Not found: " + path+" creating a new factory config for: "+factoryPid);
			Configuration c = configAdmin.createFactoryConfiguration(
					factoryPid, null);
			return c;
		}
	}
	
	private void updateIfChanged(Configuration c,
			Dictionary<String, Object> settings) throws IOException {
		Dictionary<String, Object> old = c.getProperties();
		if (old != null) {
			if (!old.equals(settings)) {
				c.update(settings);
			}
		} else {
			c.update(settings);
		}
	}
	
	private void appendIfChanged(Configuration c,
			Dictionary<String, Object> settings) throws IOException {
		Dictionary<String, Object> old = c.getProperties();
		if (old != null) {
			if (!old.equals(settings)) {
				Dictionary<String, Object> merged = new Hashtable<String, Object>();
				Enumeration<String> keys = old.keys();
				while (keys.hasMoreElements()) {
					String next = keys.nextElement();
					merged.put(next, old.get(next));
				}
				keys = settings.keys();
				while (keys.hasMoreElements()) {
					String next = keys.nextElement();
					merged.put(next, settings.get(next));
				}
				
				c.update(merged);
			}
		} else {
			c.update(settings);
		}
	}


//	private void addResourceGroup(String name, String instance, String type,
//			Dictionary<String, Object> settings) throws IOException {
//		// Dictionary<String,Object> settings = new Hashtable<String,Object>();
//		String pid = "navajo.resource." + type;
//		final String filter = "(&(service.factoryPid=" + pid + ")(name=" + name
//				+ ")(instance=" + instance + "))";
//		Configuration cc = createOrReuse(pid, filter);
//		settings.put("name", name);
//		settings.put("type", type);
//		cc.update(settings);
//	}

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	/**
	 * @param configAdmin
	 *            the configAdmin to remove
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}

	@Override
	public String getInstallationPath() {
		return repositoryInstance.getRepositoryFolder().getAbsolutePath();
	}

	@Override
	public String getOutputPath() {
		File outputFolder = repositoryInstance.getOutputFolder();
		if(outputFolder!=null) {
			return outputFolder.getAbsolutePath();
		}
		return getInstallationPath();
	}

	@Override
	public String getTempPath() {
		File tempFolder = repositoryInstance.getTempFolder();
		if(tempFolder!=null) {
			return tempFolder.getAbsolutePath();
		}
		return getInstallationPath();
	}

}
