package com.dexels.navajo.mapping.compiler.meta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.meta.MapMetaDataListenerFactory;

public class MapMetaDataFactory {
	
	private static MapMetaData instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MapMetaDataFactory.class);
	
	private MapMetaDataFactory() {
	}

	public static synchronized MapMetaData getInstance() throws Exception {
		if ( instance != null ) {
			return instance;
		} else {
			logger.warn("No MapMetaData has been loaded, this is probably bad.");
		}
		return null;
	}
	
	public static synchronized void setInstance(MapMetaData newInstance) {
		instance = newInstance;
		MapMetaDataListenerFactory.setInstance(newInstance);
	}
}


