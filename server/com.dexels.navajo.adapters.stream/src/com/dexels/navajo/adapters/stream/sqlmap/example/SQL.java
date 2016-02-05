package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.events.EventFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.script.api.UserException;
import com.github.davidmoten.rx.jdbc.Database;
import com.mysql.jdbc.service.MySqlDataSourceComponent;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func1;
import rx.functions.Func2;

public class SQL {
	public static void main(String[] args) throws SQLException, InterruptedException {
//        <property name="type" value="mysql"/>
//        <property name="url" value="jdbc:mysql://10.0.0.1/authentication"/>
//        <property name="username" value="authentication"/>
//        <property name="password" value="authentication"/>
        
        DataSource dsc = dummyResolveDataSource("authentication");
        
//        ResultSet rs = dsc.getConnection().prepareStatement("select count(*) as cnt from SPORT").executeQuery();
//        rs.next();
//        long number = rs.getLong(1);
//        System.err.println("number: "+number);
////        
//        Database
//        			.fromDataSource(dsc)
//        			.select("select * from SPORT")
//        			.get)
//        			.subscribe(System.err::println);

//        System.err.println(">> "+ss);

        UserException e;
        SQL.query("authentication", "select * from SPORT", "ResultMessage", (r,m)->{
        	try {
        	} catch (Exception x) {
				x.printStackTrace();
			}
        	return true;
        });
        
        Thread.sleep(2000);
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
	
	public static Observable<ResultSetMap> query(String datasource, String query,String name,  Func2<ResultSetMap,Message,Boolean> action) {
		return Database
			.fromDataSource(dummyResolveDataSource(datasource))
			.select(query)
			.get(res->new ResultSetMap(res));

	}
	
	protected Observable<NavajoStreamEvent> emitElement(Message element, String name) {
		return Observable.<NavajoStreamEvent>just(EventFactory.arrayElementStarted(null,name ), EventFactory.arrayElement(element,name ));
	}

}
