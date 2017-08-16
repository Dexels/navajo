package com.dexels.navajo.adapters.stream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.dexels.grus.GrusProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.NavajoStreamOperatorsNew;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.io.NavajoReactiveOperators;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;
import com.dexels.navajo.script.api.UserException;
import com.github.davidmoten.rx.jdbc.Database;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Flowable;
import rx.Observable;

public class SQL {
	
	
	private final static Logger logger = LoggerFactory.getLogger(SQL.class);

	
	public static void main(String[] args) throws SQLException, InterruptedException, UserException {
		testBackpressure();
		if(true) {
			return;
		}
        SQL.queryToMessage("dummy", "sometenant","select * from ORGANIZATION")
        	.map(m->m.renameProperty("ORGANIZATIONID", "Id"))
        	.take(1000)
//        	.toObservable()
        	.flatMap(m->m.streamFlowable())
	    	.compose(NavajoReactiveOperators.inArray("Organization"))
	    	.compose(NavajoReactiveOperators.inNavajo("SportList","dummy","dummy"))
	    	.lift(NavajoStreamOperatorsNew.serialize())
			.lift(NavajoStreamOperatorsNew.decode("UTF-8"))
//			.compose(StringFlowable.split("\r"))
//	    	.toObservable()
//	    	.lift(NavajoStreamOperatorsNew.collect())
//	    	.doOnComplete(()->System.err.println("Done!"))
	    	.blockingForEach(oa->{
    		System.err.print(oa);
    	});       
	}
	
	public static DataSource resolveDataSource(String dataSourceName, String tenant) {
		
		if(dataSourceName.equals("dummy")) {
			MySqlDataSourceComponent dsc = new MySqlDataSourceComponent();
	        Map<String,Object> props = new HashMap<>();
	        props.put("type", "mysql");
	        props.put("name", "authentication");
	        props.put("url", "jdbc:mysql://10.0.0.1/competition");
	        props.put("user", "authentication");
	        props.put("password", "authentication");
//	        dsc.activate(props);
	        Properties p = new Properties();
	        p.putAll(props);
	        try {
				return dsc.createDataSource(p);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
	        return null;
		}
		logger.info("Resolving datasource {} for tenant {}",dataSourceName,tenant);
		DataSource source = GrusProviderFactory.getInstance().getInstanceDataSource(tenant, dataSourceName);
		return source;
	}
	
	public static void testBackpressure() {
		Database
		.fromDataSource(resolveDataSource("dummy","something"))
		.select("select * from ORGANIZATION")
		.get(SQL::resultSet)
		.map(rs->rs.columnNames().toString())
		.zipWith(Observable.interval(10, TimeUnit.MILLISECONDS), (rs,i)->{
			return rs;
		})
		.subscribe(s->System.err.println("res: "+s));
	}
	
	public static Flowable<SQLResult> query(String datasource, String tenant, String query) {
		return RxJavaInterop.toV2Flowable(Database
			.fromDataSource(resolveDataSource(datasource,tenant))
			.select(query)
			.get(SQL::resultSet));

	}

	private static Flowable<SQLResult> query(String tenant, String datasource, String query, String... params) {
		return RxJavaInterop.toV2Flowable(Database
			.fromDataSource(resolveDataSource(datasource,tenant))
			.select(query)
			.parameters((Object[])params)
			.get(SQL::resultSet));
			
	}

	public static Flowable<Msg> queryToMessage(String tenant, String datasource, String query, String... params)  {
		return query(tenant,datasource,query,params)
				.map(SQL::defaultSqlResultToMsg);
	}
	
	public static Flowable<Msg> queryToMessage(String datasource, String tenant, String query)  {
		return query(datasource,tenant,query)
				.map(SQL::defaultSqlResultToMsg);
	}

	public static Msg defaultSqlResultToMsg(SQLResult result) {
		List<Prop> props = new ArrayList<>();
		for(String column : result.columnNames()) {
			props.add(Prop.create(column, result.columnValue(column)));
		}
		return Msg.createElement(props);
	}
	
	
	
	public static SQLResult resultSet(ResultSet rs)  {
			try {
				return new SQLResult(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
}
