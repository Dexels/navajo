package com.dexels.navajo.adapters.stream.sqlmap.example;

import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inArray;
import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inNavajo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.script.api.UserException;
import com.github.davidmoten.rx.jdbc.Database;
import com.mysql.jdbc.service.MySqlDataSourceComponent;

import rx.Observable;

public class SQL {
	public static void main(String[] args) throws SQLException, InterruptedException, UserException {
        SQL.query("authentication", "select * from SPORT", "ResultMessage")
        	.concatMap(row->Msg.createElement("Sport", msg->{
            		msg.add(Prop.create("SportType").withValue(row.getColumnValue("SPORTTYPE")));
        	} ))
        	.compose(inArray("Sport"))
        	.compose(inNavajo("SportList","dummy","dummy"))
			.lift(NAVADOC.collect(Collections.emptyMap()))
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
	
	public static Observable<SQLResult> query(String datasource, String query,String name) throws SQLException, UserException {
		return Database
			.fromDataSource(dummyResolveDataSource(datasource))
			.select(query)
			.get(SQL::resultSet);
			
	}

	public static SQLResult resultSet(ResultSet rs) throws SQLException {
		try {
			return new SQLResult( new ResultSetMap(rs));
		} catch (UserException e) {
			throw new SQLException(e);
		}
	}
}
