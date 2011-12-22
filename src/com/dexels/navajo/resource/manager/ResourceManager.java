package com.dexels.navajo.resource.manager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.resource.ResourceConfig;
import com.dexels.navajo.resource.ResourceInstance;

public class ResourceManager {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceManager.class);
	private final Map<String,ResourceConfig> resource = new HashMap<String, ResourceConfig>();
	private final Map<String,ResourceReference> resourceReference = new HashMap<String, ResourceReference>();

	public void addResource(ResourceConfig ri) {
		System.err.println("Adding: "+ri.getClass());
		System.err.println("config: "+ri.getConfigName());
		List<String> accepts = ri.accepts();
		for (String string : accepts) {
			System.err.println("Accepts: "+string);
		}
		if(resource.containsKey(ri.getConfigName())) {
			throw new IllegalStateException("Duplicate resource config name: "+ri.getConfigName());
		}
		resource.put(ri.getConfigName(),ri);
		logger.debug("Added resource. Size now: "+resource.size());
		
		Collection<ResourceReference> refs = getReferencesForResource(ri);
		for (ResourceReference resourceReference : refs) {
			try {
				resourceReference.instantiate(ri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	public ResourceInstance getResourceInstance(String name) {
		ResourceReference rr = resourceReference.get(name);
		if(rr==null) {
			logger.info("Datasource {} not found. Available datasources: {}",name,resourceReference.keySet());
			return null;
		}
		return rr.getInstance();
	}

	public void addResourceReference(ResourceReference r) throws Exception {
		String name = r.getName();
		ResourceReference old = resourceReference.get(name);
		if(old!=null) {
			// remove old:
			removeResourceReference(old);
		}
		resourceReference.put(name, r);
		
		ResourceConfig rc = resource.get(r.getResourceConfigName());
		if(rc!=null) {
			link(r,rc);
		} else {
			logger.info("Not linking: "+r.getName()+" configname: "+r.getResourceConfigName()+" not found.");
		}
	}
	
	private void link(ResourceReference r, ResourceConfig rc) throws Exception {
		r.instantiate(rc);
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

	private void addDatasource(Message dataSource) throws Exception {
		String name = dataSource.getName();
		List<Property> props = dataSource.getAllProperties();
		Map<String, Object> settings = new HashMap<String, Object>(); 
		for (Property property : props) {
			// skip type, it is not a 'real' connection setter
			if(property.getName().equals("type")) {
				continue;
			}
			settings.put(property.getName(), property.getTypedValue());
		}
		logger.info("Data source settings for source: {} : {}",name,settings);
		ResourceReference rr = new ResourceReference(name,(String)dataSource.getProperty("type").getTypedValue() ,settings);
		addResourceReference(rr);
	}

	public void removeResourceReference(ResourceReference rr) {
		if(rr.isActive()) {
			logger.info("Removing resource: {}",rr.getName());
			rr.uninstantiate();
		}
		resourceReference.remove(rr.getName());
	}
	public void removeResource(ResourceConfig ri) {
		String configName = ri.getConfigName();
		resource.remove(configName);
		System.err.println("Removed resource. Size now: "+resource.size());
		Collection<ResourceReference> refs = getReferencesForResource(ri);
		for (ResourceReference resourceReference : refs) {
			resourceReference.uninstantiate();
		}
	}

	public Collection<ResourceReference> getReferencesForResource(ResourceConfig rc) {
		Collection<ResourceReference> result = new ArrayList<ResourceReference>();
		String rcName = rc.getConfigName();
		for (Entry<String,ResourceReference> e : resourceReference.entrySet()) {
			if(e.getValue().getResourceConfigName().equals(rcName)) {
				result.add(e.getValue());
			}
		} 
		return result;
	}
}
