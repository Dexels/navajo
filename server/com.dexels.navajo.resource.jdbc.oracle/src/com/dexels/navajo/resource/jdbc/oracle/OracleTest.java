package com.dexels.navajo.resource.jdbc.oracle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.PooledConnection;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

	class PooledConnection1
	{
	  public static void main (String args [])
	       throws SQLException
	  {

	    // Create a OracleConnectionPoolDataSource instance
	    OracleConnectionPoolDataSource ocpds =
	                               new OracleConnectionPoolDataSource();

	    // Set connection parameters
	    ocpds.setURL("jdbc:oracle:thin:@10.0.0.1:1521:aardnoot");
	    ocpds.setDriverType("thin");
	    ocpds.setUser("knvbkern");
	    ocpds.setPassword("kern");

	    // Create a pooled connection
	    PooledConnection pc  = ocpds.getPooledConnection();

	    // Get a Logical connection
	    Connection conn = pc.getConnection();

	    // Create a Statement
	    Statement stmt = conn.createStatement ();

	    // Select the ENAME column from the EMP table
	    ResultSet rset = stmt.executeQuery ("select * from sport");

	    // Iterate through the result and print the employee names
	    while (rset.next ())
	      System.out.println (rset.getString (1));

	    // Close the RseultSet
	    rset.close();
	    rset = null;

	    // Close the Statement
	    stmt.close();
	    stmt = null;

	    // Close the logical connection
	    conn.close();
	    conn = null;

	    // Close the pooled connection
	    pc.close();
	    pc = null;
	  }
	}
