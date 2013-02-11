package com.dexels.navajo.expression.meta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapMetaDataListenerFactory {
	private static MapMetaDataListener instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MapMetaDataListenerFactory.class);
	
	private MapMetaDataListenerFactory() {
	}

	public static synchronized MapMetaDataListener getInstance() throws Exception {
		if ( instance != null ) {
			return instance;
		} else {
			logger.warn("No MapMetaDataListener has been loaded, this is probably bad. It is coupled to MapMetaDataFactory.");
		}
		return null;
	}
	
	public static synchronized void setInstance(MapMetaDataListener newInstance) {
		instance = newInstance;
	}
}
