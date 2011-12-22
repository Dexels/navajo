package com.dexels.navajo.resource.jdbc.mysql;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.jdbc.DataSourceFactory;

import com.dexels.navajo.resource.BaseResourceConfig;
import com.dexels.navajo.resource.ResourceConfig;
import com.dexels.navajo.resource.ResourceInstance;

public class MySqlResourceConfig extends BaseResourceConfig  implements ResourceConfig {

	public static final String URL = "URL";
	public static final String USERNAME = "USERNAME";
		public static final String PASSWORD = "PASSWORD";

	public MySqlResourceConfig() {
	}

	@Override
	public String getType() {
		return "jdbc";
	}

	@Override
	public String getConfigName() {
		return "mysql";
	}

	@Override
	public List<String> accepts() {
		return Arrays.asList(new String[]{DataSourceFactory.JDBC_URL,DataSourceFactory.JDBC_USER,DataSourceFactory.JDBC_PASSWORD,DataSourceFactory.JDBC_PASSWORD});
	}

	@Override
	// maybe not user/pw?
	public List<String> requires() {
		return Arrays.asList(new String[]{DataSourceFactory.JDBC_URL,DataSourceFactory.JDBC_USER,DataSourceFactory.JDBC_PASSWORD});
	}

	@Override
	public ResourceInstance createInstance(Map<String, Object> settings)
			throws Exception {
		MySqlResourceInstance h2ResourceInstance = new MySqlResourceInstance();
		verifySettings(settings);
		h2ResourceInstance.instantiate(this,settings);
		return h2ResourceInstance;
	}



}
