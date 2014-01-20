package com.dexels.navajo.resource.jdbc.oracle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

	class PooledConnection1
	{
	  public static void main (String args [])
	       throws SQLException
	  {

	    // Create a OracleConnectionPoolDataSource instance
//	    final OracleConnectionPoolDataSource ocpds =
//	                               new OracleConnectionPoolDataSource();

	    final OracleWrapped ow = new OracleWrapped();
	    Map<String,Object> settings = new HashMap<String,Object>();
	    settings.put("url", "jdbc:oracle:thin:@testserver:1521:SLTEST01");
	    settings.put("user", "username");
	    settings.put("password", "****");
	    settings.put(DataSourceFactory.JDBC_MAX_POOL_SIZE, 10);
	    ow.activate(settings);
	    // Set connection parameters
//	    ocpds.setURL("jdbc:oracle:thin:@10.0.0.1:1521:aardnoot");
//	    ocpds.setUser("knvbkern");
//	    ocpds.setPassword("knvb");
	    long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(5);
	    for (int i=0; i<10; i++) {
	        Runnable worker = new Runnable(){

				@Override
				public void run() {
				    for (int i=0; i<10; i++) {
				    	try {
							testConnection(ow);
						} catch (SQLException e) {
							e.printStackTrace();
						}
				    }
				}};
	        executor.execute(worker);
		}
        executor.shutdown();
    while (!executor.isTerminated()) {
    }
    System.out.println("Finished all threads");
    System.err.println("Took: "+(System.currentTimeMillis()-start));
	}

	private static void testConnection(DataSource pc)
			throws SQLException {
		// Create a pooled connection

	    // Get a Logical connection
	    Connection conn = pc.getConnection();

	    // Create a Statement
	    Statement stmt = conn.createStatement ();

	    // Select the ENAME column from the EMP table
	    ResultSet rset = stmt.executeQuery ("select * from sport");

	    // Iterate through the result and print the employee names
	    while (rset.next ()) {
		      System.out.println (rset.getString (1));
	    }

	    // Close the RseultSet
	    rset.close();
	    rset = null;

	    // Close the Statement
	    stmt.close();
	    stmt = null;

	    // Close the logical connection
	    conn.close();
//	    conn = null;

	    // Close the pooled connection
//	    pc = null;
	}
	}
