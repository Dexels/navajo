package com.dexels.navajo.article.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ValidationStore;
import com.dexels.navajo.server.NavajoIOConfig;

public class ArticleValidationResolver implements ValidationStore {

	private final static Logger logger = LoggerFactory.getLogger(ArticleValidationResolver.class);
	private NavajoIOConfig config;
	private Properties properties;

	private final String VALIDATION_FILENAME = "validation.properties";
	
	public void activate() throws IOException {
		logger.debug("Activating article validation resolver");

		this.properties = new Properties();
		InputStream stream = config.getConfig(VALIDATION_FILENAME);
		if (stream != null)
			properties.load(stream);
	}

	public void deactivate() {
		logger.debug("Deactivating article validation resolver");
	}
	
	@Override
	public String getDescriptionById(String id) {
		if (properties != null)
			return properties.getProperty(id);
		return null;
	}
	
	public NavajoIOConfig getConfig() {
		return config;
	}
	
	public void setConfig(NavajoIOConfig ioConfig) {
		this.config = ioConfig;
	}

	public void clearConfig(NavajoIOConfig ioConfig) {
		this.config = null;
	}
}
