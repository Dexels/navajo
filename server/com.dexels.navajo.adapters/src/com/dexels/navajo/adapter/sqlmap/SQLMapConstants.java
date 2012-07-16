package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.Repository;

/**
 * Class to hold some SQLMap constants
 * @author Erik Versteeg
 */
public class SQLMapConstants {
	public final static String ORACLEDB = "oracle";
	public final static String POSTGRESDB = "postgresql";
	public final static String MYSQLDB = "mysql";

	public static boolean isLegacyMode() {
		try {
    	    Repository r = DispatcherFactory.getInstance().getNavajoConfig().getRepository();
    		if (r != null) {
    		    r.useLegacyDateMode();
    		}
		} catch (NullPointerException e) {
		    // TODO: figure out a nice solution here
		    return true;
		}
		return true;
	}
	
}
