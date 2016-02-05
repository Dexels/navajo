package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.script.api.UserException;
import com.github.davidmoten.rx.jdbc.Database;
import com.mysql.jdbc.service.MySqlDataSourceComponent;

import rx.Observable;
import rx.functions.Func0;

public class SQL {
	public static void main(String[] args) throws SQLException, InterruptedException, UserException {
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

        NavajoStreamCollector collector = new NavajoStreamCollector();
        
        SQL.query("authentication", "select * from SPORT", "ResultMessage")
        	.flatMap(row->Msg.createElement("Sport", msg->{
        		try {
            		msg.add(Prop.create("SportType").withValue(row.getColumnValue("SPORTTYPE")));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
        	} , m->Observable.empty()))
        	.startWith(Observable.just(Events.arrayStarted("Sport")))
        	.concatWith(Observable.just(Events.arrayDone("Sport")))
        	.startWith(Observable.just(Events.started(NavajoHead.createDummy())))
        	.concatWith(Observable.just(Events.done()))
        	.flatMap(collector::feed)
        	.toBlocking().forEach(oa->{
        		oa.write(System.err);
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
	
	public static Observable<ResultSetMap> query(String datasource, String query,String name) throws SQLException, UserException {
		return Database
			.fromDataSource(dummyResolveDataSource(datasource))
			.select(query)
			.get(SQL::resultSet);

	}
	
	// TODO move this
	public static Observable<NavajoStreamEvent> array(String name, Func0<Observable<NavajoStreamEvent>> func) {
		NavajoStreamEvent startEvent = Events.arrayStarted(name );
		NavajoStreamEvent arrayDone = Events.arrayDone(name );
		return func.call().startWith(Observable.just(startEvent)).concatWith(Observable.just(arrayDone));
	}
	
	public static ResultSetMap resultSet(ResultSet rs) throws SQLException {
		try {
			return new ResultSetMap(rs);
		} catch (UserException e) {
			throw new SQLException(e);
		}
	}
//	protected Observable<NavajoStreamEvent> emitElement(Message element, String name) {
//		return Observable.<NavajoStreamEvent>just(Events.arrayElementStarted(name ), Events.arrayElement(element,name ));
//	}

}
