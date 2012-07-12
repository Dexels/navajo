package com.dexels.navajo.adapter.sqlmap;

import java.sql.Connection;
import java.sql.SQLException;

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
		Repository r = DispatcherFactory.getInstance().getNavajoConfig().getRepository();
		return r.useLegacyDateMode();
	}
	
	public static String getDbIdentifierFromConnection(Connection con) {
		try {
//			System.out.println("********************************** Drivername: " + con.getMetaData().getDriverName());
			if (con.getMetaData().getDriverName().indexOf("Oracle") != -1) {
				return SQLMapConstants.ORACLEDB;
			} else if (con.getMetaData().getDriverName().indexOf("Postgres") != -1) {
				return SQLMapConstants.POSTGRESDB;
			} else if (con.getMetaData().getDriverName().indexOf("MySQL") != -1) {
				return SQLMapConstants.MYSQLDB;
			}
		} catch (SQLException e) {
//			System.out.println("********************************** connection error !!!!!!!!!!!!!!!");
		}
		return "";
	}
}
