/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.script.api.UserException;

public class GrusProviderImpl implements GrusProvider {

    public static final ThreadLocal<Map<DataSource, Integer>> userThreadLocal = new ThreadLocal<>();

	private final Map<String, Map<String, DataSource>> instances = new HashMap<>();
	private final Map<String, DataSource> defaultDataSources = new HashMap<>();
	private final Map<DataSource, Map<String, Object>> settingsMap = new HashMap<>();

	private final Map<String, Map<String, Object>> defaultSettingsMap = new HashMap<>();

	private final AtomicInteger connectionCounter = new AtomicInteger();
	private final Map<Long, GrusConnection> grusIds = new HashMap<>();

	private static final Logger logger = LoggerFactory
			.getLogger(GrusProviderImpl.class);

	@SuppressWarnings("unchecked")
	public void addDataSource(DataSource source, Map<String, Object> settings) {
		settingsMap.put(source, settings);
		List<String> tenants = getInstances(settings);

		 String tenant = (String) settings.get("instance");
		String name = (String) settings.get("name");
		List<String> aliases = (List<String>) settings.get("aliases");
		logger.debug(">|>| Name: " + name + " instances: " + tenants
				+ " Inst: " + tenant);
		if (tenant == null) {
			addTenantLessDataSource(name, aliases, source, settings);

		} else {
			addTenantDataSource(name, aliases, source, tenants);
		}
	}

	private void addTenantDataSource(String name, List<String> aliases, DataSource source, List<String> tenants) {
		logger.debug("Adding source with name: {} with instances: {}",name,tenants);
		for (String currentInstance : tenants) {
			addDataSource(source, name, currentInstance);
			if (aliases != null) {
				for (String alias : aliases) {
					addDataSource(source, alias,currentInstance);
				}
			}
		}
	}

	private void addTenantLessDataSource(String name, List<String> aliases, DataSource source,
			Map<String, Object> settings) {
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
	}

	private void addDataSource(DataSource source, String name,
			String currentInstance) {
		Map<String, DataSource> instanceDataSources = getInstanceDataSources(currentInstance);
		instanceDataSources.put(name, source);
	}

	private List<String> getInstances(Map<String, Object> settings) {
		List<String> result = new ArrayList<>();
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
			Map<String, DataSource> copy = new HashMap<>(map);
			Collection<Entry<String, DataSource>> cc = copy.entrySet();
			for (Entry<String,DataSource> e : cc) {
				if(e.getValue()==d) {
					map.remove(e.getKey());
				}
			}
		}
		Map<String,DataSource> defaultCopy = new HashMap<>(defaultDataSources);
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
			instanceDataSources = new HashMap<>();
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
	        currentMap = new HashMap<>();
	        userThreadLocal.set(currentMap);
	    }
	    
	    return currentMap.containsKey(dataSourceInstance);
	}
	
	@Override
    public GrusConnection requestReuseThreadConnection(String instance, String name) {
        DataSource dataSourceInstance = getInstanceDataSource(instance, name);
        Map<DataSource, Integer> currentMap = userThreadLocal.get();
        
        if (currentMap == null) {
            currentMap = new HashMap<>();
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
            throw new UserException(-1, "Could not create datasource connection for: " + instance + " and name: "
                    + name, e);
        }
        grusIds.put((long) id, gc);

        Map<DataSource, Integer> currentMap = userThreadLocal.get();

        if (currentMap == null) {
            currentMap = new HashMap<>();
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
	
	@Override
	public String getDatabaseIdentifier(long id) throws UserException {
	    GrusConnection grusConnection = grusIds.get(id);
	    return grusConnection.getMyBroker().getDbIdentifier();
	}

    @Override
    public String getDatabaseIdentifier(String instance, String name) throws UserException {
        DataSource dataSourceInstance = null;
        dataSourceInstance = getInstanceDataSource(instance, name);

        Map<String, Object> settings = settingsMap.get(dataSourceInstance);

        if (settings == null && dataSourceInstance == null) {
            settings = defaultSettingsMap.get(name);
        }
        if(settings==null) {
            throw new UserException(-1, "Could not find settings for tenant-less datasource: "+name+" available (tenant-less) datasources: "+defaultSettingsMap.keySet());
        }
        String componentName = (String) settings.get("component.name");
        if (componentName.endsWith("oracle")) { 
            return SQLMapConstants.ORACLEDB;
        } else if (componentName.endsWith("mysql")) {
            return SQLMapConstants.MYSQLDB;
        }else if (componentName.endsWith("postgresql")) {
            return SQLMapConstants.POSTGRESDB;
        }
        return null;
    }

}
