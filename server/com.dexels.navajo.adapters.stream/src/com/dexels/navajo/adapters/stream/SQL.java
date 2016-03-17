package com.dexels.navajo.adapters.stream;

import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inArray;
import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inNavajo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.dexels.grus.GrusProviderFactory;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;
import com.dexels.navajo.script.api.UserException;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class SQL {
	public static void main(String[] args) throws SQLException, InterruptedException, UserException {
//
//        int count = SQL.query("authentication", "select * from SPORT").count().toBlocking().first();
//        System.err.println("Count: "+count);
//        
        SQL.queryToMessage("dummy", "select * from ORGANIZATION")
        	.map(m->m.renameProperty("ORGANIZATIONID", "Id"))
        	.take(10)
        	.flatMap(m->m.stream())
	    	.compose(inArray("Sport"))
	    	.compose(inNavajo("SportList","dummy","dummy"))
	    	.lift(NAVADOC.collect(Collections.emptyMap()))
	    	.doOnCompleted(()->System.err.println("Done!"))
        	.toBlocking().forEach(oa->{
    		oa.write(System.err);
    	});       
//    	.concatMap(row->Msg.createElement("Sport", msg->{
//        		msg.add(Prop.create("SportType").withValue(row.getColumnValue("SPORTTYPE")));
//    	} ))

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
		DataSource source = GrusProviderFactory.getInstance().getInstanceDataSource(tenant, dataSourceName);
		return source;
	}
	
	public static Observable<SQLResult> query(String datasource, String query) {
		return Database
			.fromDataSource(resolveDataSource(datasource,"KNVB"))
			.select(query)
			.get(SQL::resultSet);

	}

	private static Observable<SQLResult> query(String tenant, String datasource, String query, String... params) {
		return Database
			.fromDataSource(resolveDataSource(datasource,tenant))
			.select(query)
			.parameters((Object[])params)
			.get(SQL::resultSet)
			;
	}

	public static Observable<Msg> queryToMessage(String tenant, String datasource, String query, String... params)  {
		return query(tenant,datasource,query,params)
				.map(SQL::defaultSqlResultToMsg);
	}
	
	public static Observable<Msg> queryToMessage(String datasource, String query)  {
		return query(datasource,query)
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
