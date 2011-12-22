package com.dexels.navajo.resource.jdbc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.jdbc.DataSourceFactory;

import com.dexels.navajo.resource.BaseResourceConfig;
import com.dexels.navajo.resource.ResourceConfig;
import com.dexels.navajo.resource.ResourceInstance;

public class H2ResourceConfig extends BaseResourceConfig  implements ResourceConfig {


	public H2ResourceConfig() {
	}

	@Override
	public String getType() {
		return "jdbc";
	}

	@Override
	public String getConfigName() {
		return "h2";
	}

	@Override
	public List<String> accepts() {
		return Arrays.asList(new String[]{DataSourceFactory.JDBC_URL,DataSourceFactory.JDBC_USER,DataSourceFactory.JDBC_PASSWORD,NAME});
	}

	@Override
	// maybe not user/pw?
	public List<String> requires() {
		return Arrays.asList(new String[]{DataSourceFactory.JDBC_URL,DataSourceFactory.JDBC_USER,DataSourceFactory.JDBC_PASSWORD,NAME});
	}

	@Override
	public ResourceInstance createInstance(Map<String, Object> settings)
			throws Exception {
		H2ResourceInstance h2ResourceInstance = new H2ResourceInstance();
		verifySettings(settings);
		h2ResourceInstance.instantiate(this,settings);
		return h2ResourceInstance;
	}



}
