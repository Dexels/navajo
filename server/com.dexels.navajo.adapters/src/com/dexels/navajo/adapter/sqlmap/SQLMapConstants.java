package com.dexels.navajo.adapter.sqlmap;

import com.dexels.navajo.server.DispatcherFactory;

/**
 * Class to hold some SQLMap constants
 * 
 * @author Erik Versteeg
 */
public class SQLMapConstants {
    public final static String ENTERPRISEDB = "enterprisedb";
    public final static String ORACLEDB = "oracle";
    public final static String POSTGRESDB = "postgresql";
    public final static String MYSQLDB = "mysql";

    public static boolean isLegacyMode() {
        try {
            return DispatcherFactory.getInstance().getNavajoConfig().useLegacyDateMode();
        } catch (NullPointerException e) {
            // TODO: figure out a nice solution here
            return true;
        }
    }

}
