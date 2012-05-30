package com.dexels.navajo.resource.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class ResourceManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);
	private ConfigurationAdmin configAdmin;

	private final Set<String> resourcePids = new HashSet<String>();
	
	private BundleContext bundleContext = null;
	
	public void activate(ComponentContext cc) {
		this.bundleContext = cc.getBundleContext();
	}
	
	public void deactivate() {
		for (String pid : resourcePids) {
			try {
				Configuration c = configAdmin.getConfiguration(pid);
				c.delete();
			} catch (IOException e) {
				logger.error("Error deregistering configuration: "+pid);
			}
		}
	
	}
	
	public void loadResourceTml(InputStream is) {
		Navajo n = NavajoFactory.getInstance().createNavajo(is);
		loadResourceTml(n);
	}

	private void loadResourceTml(Navajo n) {
		Message datasources = n.getMessage("datasources");
		List<Message> ds = datasources.getAllMessages();
		for (Message dataSource : ds) {
			try {
				addDatasource(dataSource);
			} catch (Exception e) {
				logger.error("Unable to load data source.",e);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addDatasource(Message dataSource) throws Exception {
		String name = dataSource.getName();
		List<Property> props = dataSource.getAllProperties();
		Dictionary settings = new Hashtable(); 
		for (Property property : props) {
			// skip type, it is not a 'real' connection setter
			if(property.getName().equals("type")) {
				continue;
			}
			settings.put(property.getName(), property.getTypedValue());
		}
		settings.put("name", "navajo.resource."+name);
		String type = (String)dataSource.getProperty("type").getTypedValue();
		Configuration cc = configAdmin.createFactoryConfiguration("navajo.resource."+type,null);
		resourcePids.add(cc.getPid());
		cc.update(settings);
		logger.info("Data source settings for source: {} : {}",name,settings);
		
//		ResourceReference rr = new ResourceReference(name,type ,settings);
//		addResourceReference(rr);
	}

	public DataSource getDataSource(String shortName) throws InvalidSyntaxException {
		ServiceReference<DataSource> ss = getDataSourceReference(shortName);
		return bundleContext.getService(ss);
	}
	public ServiceReference<DataSource> getDataSourceReference(String shortName) throws InvalidSyntaxException {
		Collection<ServiceReference<DataSource>> dlist = bundleContext.getServiceReferences(DataSource.class,"(name=navajo.resource."+shortName+")");
		if(dlist.size()!=1) {
			logger.info("Matched: {} datasources.",dlist.size());
		}
		ServiceReference<DataSource> dref = dlist.iterator().next();
		return dref;
	}

	public ServiceReference<Object> getResourceReference(String shortName) throws InvalidSyntaxException {
		Collection<ServiceReference<Object>> dlist = bundleContext.getServiceReferences(Object.class,"(name=navajo.resource."+shortName+")");
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

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}
}
