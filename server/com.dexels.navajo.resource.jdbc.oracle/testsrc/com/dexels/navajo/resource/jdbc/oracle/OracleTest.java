package com.dexels.navajo.resource.jdbc.oracle;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleTest {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(OracleTest.class);
	
	public static void main(String args[]) throws SQLException,
			InterruptedException, IOException {

		// Create a OracleConnectionPoolDataSource instance
		// final OracleConnectionPoolDataSource ocpds =
		// new OracleConnectionPoolDataSource();

		final OracleWrapped ow = new OracleWrapped();
		Map<String, Object> settings = new HashMap<String, Object>();
		settings.put("url", "jdbc:oracle:thin:@10.0.0.1:1521:datavase");
		settings.put("user", "user");
		settings.put("password", "pass");
		settings.put("MaxLimit", "20");
		settings.put("MinLimit", "1");
		settings.put("InitialLimit", "1");
		settings.put("ConnectionWaitTimeout", "60");
		// settings.put("ValidateConnection", "true");
		// settings.put("maxPoolSize", "100");
		// settings.put(DataSourceFactory.JDBC_MAX_POOL_SIZE, 1);
		ow.activate(settings);
		Thread.sleep(2000);
		// Set connection parameters
		FileWriter found = new FileWriter("foundids.txt");
		long start = System.currentTimeMillis();
		Connection conn = ow.getConnection();
		AtomicInteger count = new AtomicInteger(0);
		Files.lines(Paths.get("list.txt")).forEach(e->{
			System.err.println("> "+e);
			doQuery(conn,e,count.getAndIncrement(),found);
		});
		found.close();
		logger.info("Finished all threads");
		logger.info("Took: " + (System.currentTimeMillis() - start));
	}

	private static void doQuery(Connection conn, String e, int count, FileWriter found) {
		String[] parts = e.split(",");
		try {
			if(count % 100 == 0) {
				System.err.println("Progress: "+count);
			}
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select count(*) from whatever");
			rset.next();
			int rows = rset.getInt(1);
			if(rows<1) {
				found.write(parts[0]+"<$>"+parts[1]+"<$>"+parts[2]+"\n");
				System.err.println("Missing row detected: "+e);
				found.flush();
			}
			stmt.close();
			rset.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}


}
