package com.dexels.navajo.adapter.resource.provider.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.dexels.grus.GrusConnection;
import org.dexels.grus.GrusProvider;
import org.dexels.grus.GrusProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.UserException;

public class GrusProviderImpl implements GrusProvider {

	private final Map<String, Map<String,DataSource>> instances = new HashMap<String, Map<String,DataSource>>();
	private final Map<DataSource,Map<String,Object>> settingsMap = new HashMap<DataSource, Map<String,Object>>();
	private final AtomicInteger connectionCounter = new AtomicInteger();
	private final Map<Long,GrusConnection> grusIds = new HashMap<Long, GrusConnection>();

	
	private final static Logger logger = LoggerFactory
			.getLogger(GrusProviderImpl.class);
	
	public void addDataSource(DataSource source, Map<String,Object> settings) {
		settingsMap.put(source, settings);

		String instance = (String) settings.get("instance");
		String name = (String) settings.get("name");
		Vector<String> aliases = (Vector<String>) settings.get("aliases");
		
		Map<String, DataSource> instanceDataSources = getInstanceDataSources(instance);
		instanceDataSources.put(name,source);
		if(aliases!=null) {
			for (String alias : aliases) {
				instanceDataSources.put(alias,source);
			}
		}
	}

	private Map<String, DataSource> getInstanceDataSources(String instance) {
		Map<String,DataSource> instanceDataSources = instances.get(instance);
		if(instanceDataSources==null) {
			instanceDataSources = new HashMap<String, DataSource>();
			instances.put(instance, instanceDataSources);
		}
		return instanceDataSources;
	}
	
	private DataSource getInstanceDataSource(String instance, String name,String username) {
		DataSource dataSource = getInstanceDataSources(instance).get(name);
		if(dataSource != null) {
			return dataSource;
		}
		dataSource = getInstanceDataSources("*").get(name);
		if(dataSource==null) {
			logger.warn("No datasource found for instance: "+instance+" and name: "+name);
		}
		return dataSource;
	}

	public void removeDataSource(DataSource source, Map<String,Object> settings) {
		settingsMap.remove(source);
		String instance = (String) settings.get("instance");
		String name = (String) settings.get("name");
		Map<String, DataSource> instanceDataSources = getInstanceDataSources(instance);
		instanceDataSources.remove(name);
	} 

	@Override
	public GrusConnection requestConnection(String instance, String name, String username) throws UserException {
		if(instance==null) {
			throw new IllegalArgumentException("instance can not be null");
		}
		
//		jdbc:oracle:thin:@odysseus:1521:SLTEST02
		
		DataSource dataSourceInstance = getInstanceDataSource(instance, name,username);
		Map<String,Object> settings = settingsMap.get(dataSourceInstance);
		if(settings==null) {
			logger.error("Error resolving datasource for instance: "+instance+" and name: "+name);
			throw new UserException(-1,"Error resolving datasource for instance: "+instance+" and name: "+name);
		}
		int id = connectionCounter.getAndIncrement();
		GrusConnection gc = new GrusDataSource(id, dataSourceInstance,settings,this);
		grusIds.put((long) id,gc);
		return gc;
	}

	@Override
	public GrusConnection requestConnection(long id) {
		return grusIds.get(id);
	}

	@Override
	public void release(GrusConnection grusDataSource) {
		grusIds.remove( grusDataSource.getId());
		grusDataSource.destroy();
	}
	
	public void activate() {
		GrusProviderFactory.setInstance(this);
	}

	public void deactivate() {
		GrusProviderFactory.setInstance(null);
	}

}
