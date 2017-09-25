package com.dexels.navajo.adapters.stream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.pool.Pools;
import org.davidmoten.rx.pool.Pool;
import org.dexels.grus.GrusProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.UserException;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;

public class SQL {
	
	
	private final static Logger logger = LoggerFactory.getLogger(SQL.class);

	public static Map<String,Object> resolveDataSource(String dataSourceName, String tenant) throws UserException {
		
		if(dataSourceName.equals("dummy")) {
	        Map<String,Object> props = new HashMap<>();
	        props.put("type", "mysql");
	        props.put("name", "authentication");
	        props.put("url", "jdbc:mysql://10.0.0.1/competition");
	        props.put("user", "authentication");
	        props.put("password", "authentication");
	        return props;
		}
		logger.info("Resolving datasource {} for tenant {}",dataSourceName,tenant);
		return GrusProviderFactory.getInstance().getInstanceDataSourceSettings(tenant, dataSourceName);
	}
	
	public static Flowable<ReplicationMessage> query(String datasource, String tenant, String query) {
		Pool<Connection> pool = Pools.nonBlocking()
				.maxPoolSize(10)
				.url("jdbc:mysql://10.0.0.1:3306/competition?user=authentication&password=authentication")
				.build();
		return Database.from(pool)
			.select(query)
			.get(SQL::resultSet);
//			.zipWith(Observable.interval(100, TimeUnit.MILLISECONDS), (rs,i)->{
//				return rs;
//			})
//			.subscribe(s->System.err.println("res: "+s));
	}
	
//	public static Flowable<SQLResult> query(String datasource, String tenant, String query) {
//		return RxJavaInterop.toV2Flowable(Database
//			.fromDataSource(resolveDataSource(datasource,tenant))
//			.select(query)
//			.get(SQL::resultSet));
//
//	}
//
//	private static Flowable<SQLResult> query(String tenant, String datasource, String query, String... params) {
//		return RxJavaInterop.toV2Flowable(Database
//			.fromDataSource(resolveDataSource(datasource,tenant))
//			.select(query)
//			.parameters((Object[])params)
//			.get(SQL::resultSet));
//			
//	}

//	public static Flowable<ReplicationMessage> queryToMessage(String tenant, String datasource, String query, String... params)  {
//		return query(tenant,datasource,query,params)
//				.map(SQL::defaultSqlResultToMsg);
//	}
//	
//	public static Flowable<ReplicationMessage> queryToMessage(String tenant,String datasource,  String query)  {
//		return query(tenant,datasource,query)
//				.map(SQL::defaultSqlResultToMsg);
//	}

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
