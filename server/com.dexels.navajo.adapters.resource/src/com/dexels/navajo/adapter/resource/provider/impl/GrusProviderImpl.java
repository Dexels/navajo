package com.dexels.navajo.adapter.resource.provider.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.dexels.grus.GrusConnection;

import com.dexels.navajo.adapter.resource.provider.GrusProvider;

public class GrusProviderImpl implements GrusProvider {

	private final Map<String, Map<String,DataSource>> instances = new HashMap<String, Map<String,DataSource>>();
	private final Map<DataSource,Map<String,Object>> settingsMap = new HashMap<DataSource, Map<String,Object>>();
	private final AtomicInteger connectionCounter = new AtomicInteger();
	public void addDataSource(DataSource source, Map<String,Object> settings) {
		settingsMap.put(source, settings);

		String instance = (String) settings.get("instance");
		String name = (String) settings.get("name");
		
		Map<String, DataSource> instanceDataSources = getInstanceDataSources(instance);
		instanceDataSources.put(name,source);
	}

	private Map<String, DataSource> getInstanceDataSources(String instance) {
		Map<String,DataSource> instanceDataSources = instances.get(instance);
		if(instanceDataSources==null) {
			instanceDataSources = new HashMap<String, DataSource>();
			instances.put(instance, instanceDataSources);
		}
		return instanceDataSources;
	}
	
	private DataSource getInstanceDataSource(String instance, String name) {
		return getInstanceDataSources(instance).get(name);
	}

	public void removeDataSource(DataSource source, Map<String,Object> settings) {
		settingsMap.remove(source);
		String instance = (String) settings.get("instance");
		String name = (String) settings.get("name");
		Map<String, DataSource> instanceDataSources = getInstanceDataSources(instance);
		instanceDataSources.remove(name);
	} 

	@Override
	public GrusConnection requestConnection(String instance, String name) {
		DataSource dataSourceInstance = getInstanceDataSource(instance, name);
		Map<String,Object> settings = settingsMap.get(dataSourceInstance);
		int id = connectionCounter.getAndIncrement();
		GrusConnection gc = new GrusDataSource(id, dataSourceInstance,settings);
		
		return gc;
	}
}
