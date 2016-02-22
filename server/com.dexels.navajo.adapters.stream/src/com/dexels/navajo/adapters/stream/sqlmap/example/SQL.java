package com.dexels.navajo.adapters.stream.sqlmap.example;

import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inArray;
import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inNavajo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;
import com.dexels.navajo.script.api.UserException;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observable;

public class SQL {
	public static void main(String[] args) throws SQLException, InterruptedException, UserException {
//        SQL.query("authentication", "select * from SPORT", "ResultMessage")
//        	.concatMap(row->Msg.createElement("Sport", msg->{
//            		msg.add(Prop.create("SportType").withValue(row.getColumnValue("SPORTTYPE")));
//        	} ))
//        	
//        	.compose(inArray("Sport"))
//        	.compose(inNavajo("SportList","dummy","dummy"))
//			.lift(NAVADOC.collect(Collections.emptyMap()))
//        	.toBlocking().forEach(oa->{
//        		oa.write(System.err);
//        	});
//        Thread.sleep(2000);
        int count = SQL.query("authentication", "select * from SPORT").count().toBlocking().first();
        System.err.println("Count: "+count);
        
        SQL.queryToMessage("authentication", "select * from SPORT")
        	.map(m->m.renameProperty("SPORTID", "Id").without(new String[]{"UPDATEBY","LASTUPDATE"}))
        	.flatMap(m->m.stream())
	    	.compose(inArray("Sport"))
	    	.compose(inNavajo("SportList","dummy","dummy"))
	    	.lift(NAVADOC.collect(Collections.emptyMap()))
        	.toBlocking().forEach(oa->{
    		oa.write(System.err);
    	});       
//    	.concatMap(row->Msg.createElement("Sport", msg->{
//        		msg.add(Prop.create("SportType").withValue(row.getColumnValue("SPORTTYPE")));
//    	} ))

	}
	
	private static DataSource dummyResolveDataSource(String dataSourceName) {
        MySqlDataSourceComponent dsc = new MySqlDataSourceComponent();
        Map<String,Object> props = new HashMap<>();
        props.put("type", "mysql");
        props.put("name", "authentication");
        props.put("url", "jdbc:mysql://10.0.0.1/authentication");
        props.put("user", "authentication");
        props.put("password", "authentication");
        dsc.activate(props);
        return dsc; 
	}
	
	public static Observable<SQLResult> query(String datasource, String query) throws SQLException, UserException {
		return Database
			.fromDataSource(dummyResolveDataSource(datasource))
			.select(query)
			.get(SQL::resultSet);
	}

	public static Observable<Msg> queryToMessage(String datasource, String query) throws SQLException, UserException {
		return query(datasource,query)
				.map(SQL::defaultSqlResultToMsg);
	}

	private static Msg defaultSqlResultToMsg(SQLResult result) {
		List<Prop> props = new ArrayList<>();
		for(String column : result.columnNames()) {
			props.add(Prop.create(column, result.columnValue(column)));
		}
		return Msg.createElement(props);
	}
	
	
	
	private static SQLResult resultSet(ResultSet rs)  {
			try {
				return new SQLResult(rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
}
