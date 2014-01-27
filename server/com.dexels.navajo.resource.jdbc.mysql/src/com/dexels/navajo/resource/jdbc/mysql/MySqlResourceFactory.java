package com.dexels.navajo.resource.jdbc.mysql;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

import com.dexels.navajo.resource.ResourceFactory;

public class MySqlResourceFactory implements ResourceFactory {

	private DataSourceFactory datasourceFactory;
	private BundleContext bundleContext;
	private final Map<String, ServiceRegistration<DataSource>> registrations = new HashMap<String, ServiceRegistration<DataSource>>();
	
	@Override
	public String getType() {
		return "mysql";
	}
	public void activate(BundleContext bundleContext) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.bundleContext = bundleContext;
        Class<?> clazz = Class.forName("com.mysql.jdbc.Driver");
        clazz.newInstance();
	}

	public void deactivate() {
		this.bundleContext = null;
	}
	@Override
	public void instantiate(String id, Map<String, Object> settings) {
		Properties props = new Properties();
		props.putAll(settings);
		try {
			DataSource src =  datasourceFactory.createDataSource(props);
			registerDataSource(id,src,settings);
		} catch (SQLException e) {
			throw new RuntimeException("Trouble instantiating datasource",e);
		}
	}

	private void registerDataSource(String id, DataSource src, Map<String, Object> settings) {
		Dictionary<String,Object> dict = new Hashtable<String, Object>();
		for (Entry<String,Object> e : settings.entrySet()) {
			dict.put(e.getKey(), e.getValue());
		}
		ServiceRegistration<DataSource> ds = bundleContext.registerService(DataSource.class, src,dict );
		registrations.put(id, ds);
	}
	@Override
	public void destroy() {
		
	}
	
	@Override
	public Class<?> getServiceType() {
		return DataSource.class;
	}
	
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.datasourceFactory = dataSourceFactory;
	}

	public void clearDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.datasourceFactory = null;
	}

}
