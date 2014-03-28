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

		 String instance = (String) settings.get("instance");
		String name = (String) settings.get("name");
		List<String> aliases = (List<String>) settings.get("aliases");
		logger.warn(">|>| Name: " + name + " instances: " + instances
				+ " Inst: " + instance);
		if (instance == null) {
			defaultSettingsMap.put(name, settings);
			defaultDataSources.put(name, source);
			if (aliases != null) {
				for (String alias : aliases) {
					defaultSettingsMap
							.put("navajo.resource." + alias, settings);
					defaultDataSources.put("navajo.resource." + alias, source);
				}
			}

		} else {
			System.err.println("!!! Adding source with name: "+name+" with instances: "+instances);
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

	private DataSource getInstanceDataSource(String instance, String name,
			String username) {
		if (instance != null) {
			DataSource dataSource = getInstanceDataSources(instance).get(name);
			if (dataSource != null) {
				return dataSource;
			} else {
				logger.warn("No datasource found for instance: "+instance+" name: "+name);
			}
		}
		DataSource dataSource = defaultDataSources.get("navajo.resource."
				+ name);
		if (dataSource != null) {
			return dataSource;
		}
		// I think this makes no sense
		// dataSource = getInstanceDataSources("*").get(name);
		// if(dataSource==null) {
		// logger.warn("No datasource found for instance: "+instance+" and name: "+name);
		// }
		logger.warn("No datasource found for instance: " + instance
				+ " and name: " + name);
		return null;
	}

	public void removeDataSource(DataSource source, Map<String, Object> settings) {
		
		removeInstanceDataSource(source);
		
//		List<String> instances = getInstances(settings);
//		List<String> aliases = (List<String>) settings.get("aliases");
//		String instance = (String) settings.get("instance");
//		String name = (String) settings.get("name");
//
//		if (instance == null) {
//			defaultSettingsMap.put(name, settings);
//			defaultDataSources.put(name, source);
//			if (aliases != null) {
//				for (String alias : aliases) {
//					defaultSettingsMap
//							.put("navajo.resource." + alias, settings);
//					defaultDataSources.put("navajo.resource." + alias, source);
//				}
//			}
//		}
//		
//		settingsMap.remove(source);
//
//		Map<String, DataSource> instanceDataSources = getInstanceDataSources(instance);
//		instanceDataSources.remove(name);
	}

	@Override
	public GrusConnection requestConnection(String instance, String name,
			String username) throws UserException {
		DataSource dataSourceInstance = null;
		dataSourceInstance = getInstanceDataSource(instance, name, username);

		// jdbc:oracle:thin:@odysseus:1521:SLTEST02

		Map<String, Object> settings = settingsMap.get(dataSourceInstance);
		// if(instance!=null && settings==null) {
		// logger.error("Error resolving datasource for instance: "+instance+" and name: "+name+" settings map: "+settingsMap+" defaultS");
		// throw new
		// UserException(-1,"Error resolving datasource for instance: "+instance+" and name: "+name);
		// }
		if (settings == null && dataSourceInstance == null) {
			settings = defaultSettingsMap.get(name);
			dataSourceInstance = defaultDataSources.get(name);
		}

		int id = connectionCounter.getAndIncrement();
		GrusConnection gc;
		try {
			gc = new GrusDataSource(id, dataSourceInstance, settings, this);
		} catch (Exception e) {
			throw new UserException(-1,
					"Could not create datasource connection for: " + instance
							+ " and name: " + name, e);
		}
		grusIds.put((long) id, gc);
		return gc;
	}

	@Override
	public GrusConnection requestConnection(long id) {
		return grusIds.get(id);
	}

	@Override
	public void release(GrusConnection grusDataSource) {
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
