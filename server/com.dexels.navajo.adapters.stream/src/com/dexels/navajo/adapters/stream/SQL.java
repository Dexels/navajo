package com.dexels.navajo.adapters.stream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.pool.Pools;
import org.davidmoten.rx.pool.Pool;
import org.dexels.grus.GrusProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.internal.ConnectionProviderFromDataSource;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;
import oracle.jdbc.pool.OracleDataSource;

public class SQL {
	
	
	private final static Logger logger = LoggerFactory.getLogger(SQL.class);

	private static Map<DataSource,Pool<Connection>> resolvedPools = new HashMap<>();
	
	private static DataSource testDataSource = null;
	
	public static DataSource resolveDataSource(String dataSourceName, String tenant) {
		
		if(dataSourceName.equals("dummy")) {
			if(testDataSource!=null) {
				return testDataSource;
			}

		    OracleDataSource ds;
			try {
				ds = new oracle.jdbc.pool.OracleDataSource();
			    ds.setDriverType("thin");
			    ds.setServerName("localhost");
			    ds.setDatabaseName("AARDBEI");
			    ds.setPortNumber(1521);
			    ds.setUser("username");
			    ds.setPassword("password");
	        		testDataSource = ds;
				return testDataSource;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		logger.info("Resolving datasource {} for tenant {}",dataSourceName,tenant);
		DataSource source = GrusProviderFactory.getInstance().getInstanceDataSource(tenant, dataSourceName);
		return source;
	}
	
	private static Pool<Connection> getPoolForDataSource(DataSource ds) {
		Pool<Connection> connection = resolvedPools.get(ds);
		if(connection!=null) {
			return connection;
		}
		Pool<Connection> resolved = Pools.nonBlocking().maxPoolSize(20).connectionProvider(new ConnectionProviderFromDataSource(ds)).build();
		resolvedPools.put(ds, resolved);
		return resolved;
	}
	
	
	public static Flowable<ReplicationMessage> query(String datasource, String tenant, String query, Object... params) {
		DataSource ds = resolveDataSource(datasource, tenant);
		Pool<Connection> pool =  getPoolForDataSource(ds);

		return Database.from(pool)
			.select(query)
			.parameterList(Arrays.asList(params))
			.get(SQL::resultSet);
	}
	


	public static ReplicationMessage defaultSqlResultToMsg(SQLResult result) {
		return result.toMessage();
	}
	
	
	
	public static ReplicationMessage resultSet(ResultSet rs)  {
			try {
				return new SQLResult(rs).toMessage();
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
			return null;
	}
}
