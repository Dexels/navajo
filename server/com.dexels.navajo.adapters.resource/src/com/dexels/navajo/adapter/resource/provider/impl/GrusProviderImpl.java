package com.dexels.navajo.adapter.resource.provider.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.dexels.grus.GrusConnection;
import org.dexels.grus.GrusProvider;
import org.dexels.grus.GrusProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.UserException;

public class GrusProviderImpl implements GrusProvider {

    public static final ThreadLocal<Map<DataSource, Integer>> userThreadLocal = new ThreadLocal<Map<DataSource, Integer>>();

	private final Map<String, Map<String, DataSource>> instances = new HashMap<String, Map<String, DataSource>>();
	private final Map<String, DataSource> defaultDataSources = new HashMap<String, DataSource>();
	private final Map<DataSource, Map<String, Object>> settingsMap = new HashMap<DataSource, Map<String, Object>>();

	private final Map<String, Map<String, Object>> defaultSettingsMap = new HashMap<String, Map<String, Object>>();

	private final AtomicInteger connectionCounter = new AtomicInteger();
	private final Map<Long, GrusConnection> grusIds = new HashMap<Long, GrusConnection>();

	private final static Logger logger = LoggerFactory
			.getLogger(GrusProviderImpl.class);

	@SuppressWarnings("unchecked")
	public void addDataSource(DataSource source, Map<String, Object> settings) {
		settingsMap.put(source, settings);
		List<String> instances = getInstances(settings);

		 String tenant = (String) settings.get("instance");
		String name = (String) settings.get("name");
		List<String> aliases = (List<String>) settings.get("aliases");
		logger.debug(">|>| Name: " + name + " instances: " + instances
				+ " Inst: " + tenant);
		if (tenant == null) {
			defaultSettingsMap.put(name, settings);
			defaultDataSources.put(name, source);
			if (aliases != null) {
				for (String alias : aliases) {
					defaultSettingsMap.put("navajo.resource." + alias, settings);
					defaultDataSources.put("navajo.resource." + alias, source);
					defaultSettingsMap.put(alias, settings);
					defaultDataSources.put(alias, source);
				}
			}

		} else {
			logger.debug("Adding source with name: "+name+" with instances: "+instances);
			for (String currentInstance : instances) {
				addDataSource(source, name, currentInstance);
				if (aliases != null) {
					for (String alias : aliases) {
						addDataSource(source, alias,currentInstance);
					}
				}

			}
		}
	}

	private void addDataSource(DataSource source, String name,
			String currentInstance) {
		Map<String, DataSource> instanceDataSources = getInstanceDataSources(currentInstance);
		instanceDataSources.put(name, source);
	}

	private List<String> getInstances(Map<String, Object> settings) {
		List<String> result = new ArrayList<String>();
		for (Entry<String, Object> e : settings.entrySet()) {
			if (e.getValue().equals("instance")) {
				result.add(e.getKey());
			}
		}
		return result;
	}

	private void removeInstanceDataSource(DataSource d) {
		Collection<Map<String,DataSource>> c = instances.values();
		for (Map<String, DataSource> map : c) {
			Map<String, DataSource> copy = new HashMap<String, DataSource>(map);
			Collection<Entry<String, DataSource>> cc = copy.entrySet();
			for (Entry<String,DataSource> e : cc) {
				if(e.getValue()==d) {
					map.remove(e.getKey());
				}
			}
		}
		Map<String,DataSource> defaultCopy = new HashMap<String, DataSource>(defaultDataSources);
		Collection<Entry<String, DataSource>> cc = defaultCopy.entrySet();
		for (Entry<String,DataSource> e : cc) {
			if(e.getValue()==d) {
				defaultDataSources.remove(e.getKey());
			}
		}
		settingsMap.remove(d);
	}
	
	private Map<String, DataSource> getInstanceDataSources(String instance) {
		Map<String, DataSource> instanceDataSources = instances.get(instance);
		if (instanceDataSources == null) {
			instanceDataSources = new HashMap<String, DataSource>();
			instances.put(instance, instanceDataSources);
		}
		return instanceDataSources;
	}

	@Override
	public Map<String,Object> getInstanceDataSourceSettings(String instance, String name) throws UserException {
        DataSource dataSourceInstance = null;
        dataSourceInstance = getInstanceDataSource(instance, name);

        Map<String, Object> settings = settingsMap.get(dataSourceInstance);
        if (settings == null && dataSourceInstance == null) {
            settings = defaultSettingsMap.get(name);
            if(settings==null) {
            		throw new UserException(-1, "Could not find settings for tenant-less datasource: "+name+" available (tenant-less) datasources: "+defaultSettingsMap.keySet());
            }
        }
        return settings;
	}
	
	@Override
	public DataSource getInstanceDataSource(String instance, String name) {
		if (instance != null) {
			DataSource dataSource = getInstanceDataSources(instance).get(name);
			if (dataSource != null) {
				return dataSource;
			}
		}
		final String fullName = name.startsWith("navajo.resource.")?name:"navajo.resource."+name;
		DataSource dataSource = defaultDataSources.get(fullName);
		if (dataSource != null) {
			return dataSource;
		}
		dataSource = defaultDataSources.get(name);
		if (dataSource != null) {
			return dataSource;
		}
		if (instance != null)
		{
			logger.warn("No datasource found for instance: " + instance
					+ " and name: " + name+ " datasource keys (tenant-specific): "+getInstanceDataSources(instance).keySet() + " datasource keys (tenant-less): "+defaultDataSources.keySet(), new Exception());
		}
		else
		{
			logger.warn("No datasource found for instance: " + instance
					+ " and name: " + name+ " datasource keys: "+defaultDataSources.keySet(), new Exception());
		}
		return null;
	}

	public void removeDataSource(DataSource source, Map<String, Object> settings) {
		removeInstanceDataSource(source);
	}
	
	@Override
	public boolean threadContainsConnection(String instance, String name) {
	    DataSource dataSourceInstance = getInstanceDataSource(instance, name);
	    Map<DataSource, Integer> currentMap = userThreadLocal.get();
	    
	    if (currentMap == null) {
	        currentMap = new HashMap<DataSource, Integer>();
	        userThreadLocal.set(currentMap);
	    }
	    
	    if (currentMap.containsKey(dataSourceInstance)) {
	        return true;
	    }
	    return false;
	}
	
	@Override
    public GrusConnection requestReuseThreadConnection(String instance, String name) {
        DataSource dataSourceInstance = getInstanceDataSource(instance, name);
        Map<DataSource, Integer> currentMap = userThreadLocal.get();
        
        if (currentMap == null) {
            currentMap = new HashMap<DataSource, Integer>();
        }
        
        if (!currentMap.containsKey(dataSourceInstance)) {
            throw new RuntimeException("Unable to find existing connection!");
        }
        return requestConnection(currentMap.get(dataSourceInstance));
    }

	@Override
    public GrusConnection requestConnection(String instance, String name) throws UserException {
        DataSource dataSourceInstance = null;
        dataSourceInstance = getInstanceDataSource(instance, name);

        Map<String, Object> settings = settingsMap.get(dataSourceInstance);

        if (settings == null && dataSourceInstance == null) {
            settings = defaultSettingsMap.get(name);
            if(settings==null) {
            	throw new UserException(-1, "Could not find settings for tenant-less datasource: "+name+" available (tenant-less) datasources: "+defaultSettingsMap.keySet());
            }
            dataSourceInstance = defaultDataSources.get(name);
        }

        int id = connectionCounter.getAndIncrement();
        GrusConnection gc;
        try {
            gc = new GrusDataSource(id, dataSourceInstance, settings, this);
        } catch (Exception e) {
            logger.error("Exception in creating datasource connection for: {} name: {}: {}", instance, name, e);
            throw new UserException(-1, "Could not create datasource connection for: " + instance + " and name: "
                    + name, e);
        }
        grusIds.put((long) id, gc);

        Map<DataSource, Integer> currentMap = userThreadLocal.get();

        if (currentMap == null) {
            currentMap = new HashMap<DataSource, Integer>();
        }
        currentMap.put(dataSourceInstance, id);
        userThreadLocal.set(currentMap);
        return gc;
    }

    
  
    
	@Override
	public GrusConnection requestConnection(long id) {
		return grusIds.get(id);
	}

	@Override
	public void release(GrusConnection grusDataSource) {
	    
        Map<DataSource, Integer> currentMap = userThreadLocal.get();
        
        if (currentMap != null) {
            currentMap.remove(grusDataSource.getDatasource());
            if (currentMap.size() == 0) {
                currentMap = null; // clear map to allow GC
            }
            userThreadLocal.set(currentMap);
        }
        

		grusIds.remove(grusDataSource.getId());
		grusDataSource.destroy();
		
		
	}

	public void activate() {
		GrusProviderFactory.setInstance(this);		
	}

	public void deactivate() {
		GrusProviderFactory.setInstance(null);
	}

}
