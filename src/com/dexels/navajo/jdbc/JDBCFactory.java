package com.dexels.navajo.jdbc;

import com.dexels.navajo.adapter.JDBCMap;
import com.dexels.navajo.adapter.SQLMap;

public class JDBCFactory {
	
	private static boolean useOSGi() {
		return navajoadapters.Version.getDefaultBundleContext()!=null;
	}
	
	public static JDBCMappable getJDBCMap() {
		if(useOSGi()) {
			return new JDBCMap();
		} else {
			return new SQLMap();
		}
	}
}
