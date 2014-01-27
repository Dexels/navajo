package com.dexels.navajo.resource.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.api.NavajoServerContext;

public class ResourceManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);
	private ConfigurationAdmin configAdmin;

	private final Map<String,Configuration> resourcePids = new HashMap<String,Configuration>();
	private NavajoServerContext navajoServerContext;

	private BundleContext bundleContext = null;
	
	public void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		setupResources();
		setupTesterUser();

	}
	
	public void deactivate() {
		unloadDataSources();
		try {
			Configuration config = configAdmin.getConfiguration("com.dexels.navajo.localclient",null);
			if(config!=null) {
				config.delete();
				logger.info("Removed local client registration");
			}
		} catch (IOException e) {
			logger.error("Error deactivating config: ", e);
		}
	}

	public void unloadDataSources() {
		for (Entry<String,Configuration> entry : resourcePids.entrySet()) {
			try {
				entry.getValue().delete();
			} catch (IOException e) {
				logger.error("Error deregistering configuration: "+entry.getKey());
			}
		}
	}


	public void loadResourceTml(InputStream is) {
		Navajo n = NavajoFactory.getInstance().createNavajo(is);
		loadResourceTml(n);
	}

	private void loadResourceTml(Navajo n) {
		Message datasources = n.getMessage("datasources");
		if(datasources!=null) {
			logger.warn("In datasource definitions, please use 'resources' instead of 'datasources' as top level tag");
		} else {
			datasources = n.getMessage("resources");
		}
		if(datasources==null) {
			logger.warn("Datasources message is missing.");
		} else {
			List<Message> ds = datasources.getAllMessages();
			for (Message dataSource : ds) {
				try {
					addDatasource(dataSource);
				} catch (Exception e) {
					logger.error("Unable to load data source.",e);
				}
			}
			
		}
	}

	private void addDatasource(Message dataSource) throws Exception {
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
		String type = (String)dataSource.getProperty("type").getTypedValue();
		final String factoryPid = "navajo.resource."+type;
		final String filter = "(name="+name+")";
//		Configuration cc = configAdmin.createFactoryConfiguration(factoryPid,null);
//		resourcePids.put(cc.getPid(),cc);
//		cc.update(settings);
		emitFactoryIfChanged(factoryPid, filter, settings);
		logger.info("Data source settings for source: {} : {}",name,settings);
		
//		ResourceReference rr = new ResourceReference(name,type ,settings);
//		addResourceReference(rr);
	}

//	private void emitConfig(String pid, Dictionary<String,Object> settings) throws IOException {
//		Configuration config =  configAdmin.getConfiguration(pid,null);
//		updateIfChanged(config, settings);
//	}

	private void emitFactoryIfChanged(String factoryPid, String filter,Dictionary<String,Object> settings) throws IOException {
		updateIfChanged(createOrReuseFactoryConfiguration(factoryPid, filter), settings);
	}
	
	protected Configuration createOrReuseFactoryConfiguration(String factoryPid, final String filter)
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
			cc = configAdmin.createFactoryConfiguration(factoryPid,null);
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


	private ServiceReference<Object> getResourceReference(String shortName) throws InvalidSyntaxException {
		Collection<ServiceReference<Object>> dlist = bundleContext.getServiceReferences(Object.class,"(name="+shortName+")");
		if(dlist.size()!=1) {
			logger.info("Matched: {} datasources.",dlist.size());
		}
		if(dlist.isEmpty()) {
			logger.error("Can not find datasource: {}",shortName);
		}
		ServiceReference<Object> dref = dlist.iterator().next();
		return dref;
	}

	public Object getResourceSource(String shortName) throws InvalidSyntaxException {
		ServiceReference<Object> ss = getResourceReference(shortName);
		return bundleContext.getService(ss);
	}

	
	public void setNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = navajoServerContext;
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

	public void activateManager() {
		setupResources();
		setupTesterUser();
	}



	
	public void setupResources() {
		FileInputStream fis = null;
		try {
			logger.info("Looking for datasources in: "+navajoServerContext.getInstallationPath());
			File install = new File(navajoServerContext.getInstallationPath(),"config/datasources.xml");
			if(!install.exists()) {
				logger.warn("Datasources file: "+install.getAbsolutePath()+" does not exist, not injecting explicit datasources");
				return;
			}
			fis = new FileInputStream(install);
			loadResourceTml(fis);
			fis.close();
		} catch (IOException e) {
			logger.error("Error reading datasources file: ", e);
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Error closing datasources file: ", e);
				}
			}
		}
	}

	private void setupTesterUser() {
		FileInputStream fis = null;
		File install = new File(navajoServerContext.getInstallationPath(),"config/client.properties");
		try {
			fis = new FileInputStream(install);
			ResourceBundle b = new PropertyResourceBundle(fis);
			if(! b.containsKey("username")) {
				logger.error("No username found in client.properties");
			}
			
			processClientBundle(b);
			fis.close();
		} catch (IOException e) {
			logger.error("Error reading client.properies file. "+install.getAbsolutePath());
		} finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Error closing client.properties file. ");
				}
			}
			
		}
	}
	
	private void processClientBundle(ResourceBundle b) {
		try {
			Configuration config = configAdmin.getConfiguration("com.dexels.navajo.localclient",null);
			Dictionary<String,String> dt = new Hashtable<String,String>();
			dt.put("user", b.getString("username"));
			dt.put("password", b.getString("password"));
			config.update(dt);
		} catch (IOException e) {
			logger.error("Adding configuration for client.properties: ", e);
		}
		try {
			Configuration config = configAdmin.getConfiguration("com.dexels.navajo.localclient.legacy",null);
			Dictionary<String,String> dt = new Hashtable<String,String>();
			dt.put("user", b.getString("username"));
			dt.put("password", b.getString("password"));
			config.update(dt);
		} catch (IOException e) {
			logger.error("Adding configuration for client.properties: ", e);
		}
		
		
	}


	/**
	 * The navajoServerContext to remove
	 * @param navajoServerContext
	 */
	public void removeNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = null;
	}



}
