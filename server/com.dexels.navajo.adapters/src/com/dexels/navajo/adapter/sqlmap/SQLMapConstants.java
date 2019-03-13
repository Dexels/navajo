package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.server.DispatcherFactory;

/**
 * Class to hold some SQLMap constants
 * 
 * @author Erik Versteeg
 */
public class SQLMapConstants {
    public static final String ENTERPRISEDB = "enterprisedb";
    public static final String ORACLEDB = "oracle";
    public static final String POSTGRESDB = "postgresql";
    public static final String MYSQLDB = "mysql";

    public static boolean isLegacyMode() {
        try {
            return DispatcherFactory.getInstance().getNavajoConfig().useLegacyDateMode();
        } catch (NullPointerException e) {
            return true;
        }
    }

}
