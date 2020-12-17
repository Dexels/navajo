/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapters.stream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.davidmoten.rx.jdbc.Database;
import org.dexels.grus.GrusProvider;
import org.dexels.grus.GrusProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;
import com.dexels.navajo.runtime.config.TestConfig;

import io.reactivex.Flowable;
import oracle.jdbc.pool.OracleDataSource;

public class SQL {
	
	private SQL() {
		// no instances
	}
	
	private static final Logger logger = LoggerFactory.getLogger(SQL.class);

	private static DataSource testDataSource = null;

	
	public static Optional<DataSource> resolveDataSource(String dataSourceName, String tenant) {
		
		if(dataSourceName.equals("dummy")) {
			if(testDataSource!=null) {
				return Optional.of(testDataSource);
			}

		    OracleDataSource ds;
			try {
				ds = new oracle.jdbc.pool.OracleDataSource();
			    ds.setURL(TestConfig.ORACLE_DUMMY_URL.getValue());
	        		testDataSource = ds;
				return Optional.of(testDataSource);
			} catch (SQLException e1) {
				logger.error("Error: ", e1);
			}
		}
		
		if(dataSourceName.equals("dummymysql")) {
			MySqlDataSourceComponent dsc = new MySqlDataSourceComponent();
	        Map<String,Object> props = new HashMap<>();
	        props.put("type", "mysql");
	        props.put("name", "authentication");
	        props.put("url", "jdbc:mysql://localhost/competition");
	        props.put("user", "username");
	        props.put("password", "password");
	        Properties p = new Properties();
	        p.putAll(props);
	        try {
	        		testDataSource = dsc.createDataSource(p);
				return Optional.of(testDataSource);
			} catch (SQLException e) {
				logger.error("Error: ", e);
			} 
	        return Optional.empty();
		}
		GrusProvider instance = GrusProviderFactory.getInstance();
		DataSource source = instance.getInstanceDataSource(tenant, dataSourceName);
		return Optional.ofNullable(source);
	}
	
	public static Flowable<ImmutableMessage> query(String datasource, String tenant, String query, Operand... params) {
		Optional<DataSource> ds = resolveDataSource(datasource, tenant);
		if(!ds.isPresent()) {
			return Flowable.error(new NullPointerException("Datasource missing for datasource: "+datasource+" with tenant: "+tenant));
		}
		List<Object> valueList = Arrays.asList(params).stream().map(e->e.value).collect(Collectors.toList());
		return Database.fromBlocking(ds.get())
			.select(query)
			.parameters(valueList)
			.get(SQL::resultSet);
	}
	
	public static Flowable<ImmutableMessage> update(String datasource, String tenant, String query, Operand... params) {
		Optional<DataSource> ds = resolveDataSource(datasource, tenant);
		if(!ds.isPresent()) {
			return Flowable.error(new NullPointerException("Datasource missing for datasource: "+datasource+" with tenant: "+tenant));
		}
		List<Object> valueList = Arrays.asList(params).stream().map(e->e.value).collect(Collectors.toList());
		return Database.fromBlocking(ds.get())
			.update(query)
			.parameters(valueList)
			.counts()
			.map(count->ImmutableFactory.empty().with("count", count, Property.INTEGER_PROPERTY));
	}
	
	private static ImmutableMessage toLowerCaseKeys(ImmutableMessage m) {
		Set<String> names = m.columnNames();
		Map<String,Object> values = new HashMap<>();
		Map<String,String> types = new HashMap<>();
		names.forEach(e->{
			values.put(e.toLowerCase(), m.value(e).orElse(null));
			types.put(e.toLowerCase(), m.columnType(e));
		});
		return ImmutableFactory.create(values, types, Collections.emptyMap(), Collections.emptyMap());
	}

	public static ImmutableMessage defaultSqlResultToMsg(SQLResult result) {
		return result.toMessage();
	}
	
	
	
	public static ImmutableMessage resultSet(ResultSet rs)  {
			try {
				return toLowerCaseKeys(new SQLResult(rs)
						.toMessage());
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
			return null;
	}
}
