package com.dexels.navajo.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.JDBCMap;
import com.dexels.navajo.adapter.SQLMap;

public class JDBCFactory {
	
	public final static Logger logger = LoggerFactory.getLogger(JDBCFactory.class);
	
	private static boolean useOSGi() {
		try {
			return navajoadapters.Version.getDefaultBundleContext()!=null;
		} catch (Throwable t) {
			logger.warn("No OSGi libraries found.");
			return false;
		}
	}
	
	public static JDBCMappable getJDBCMap() {
		if(useOSGi()) {
			return new JDBCMap();
		} else {
			return new SQLMap();
		}
	}
}
