package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.adapter.SQLMap;
import java.util.*;

/**
 * <p>Title: <h3>SingleValueQuery</h3><br></p>
 * <p>Description: Web Services for the SportLink Project<br>
 * SingleValueQuery allows the simple use of an SQL query which must
 * return one and only one value. <br></p>
 * <p>Copyright: Copyright (c) 2002 - 2003<br></p>
 * <p>Company: Dexels B.V.<br></p>
 * @author Arjen Schoneveld aschoneveld@dexels.com
 * @version $Id$
 */

public class SingleValueQuery extends FunctionInterface {

  public static final String DATASOURCEDELIMITER = ":";
  public static final String USERDELIMITER = "@";

  protected final SQLMap evaluateQuery() throws com.dexels.navajo.parser.TMLExpressionException {

	  String query = "";
	  SQLMap sql = null;

	  int transactionContext = -1;

	  // String read query.
	  Object o1 = getOperand(0);
	  query = "";
	  if (o1 instanceof Integer) {  // TransactionContext set.
		  transactionContext = ((Integer) o1).intValue();
		  Object o2 = getOperand(1);
		  if (!(o2 instanceof String))
			  throw new TMLExpressionException(this, "Invalid argument: " + o2);
		  query = (String) o2;
	  } else if (o1 instanceof String) { // No TransactionContext set.
		  query = (String) o1;
	  } else
		  throw new TMLExpressionException(this, "Invalid argument: " + o1);


	  //if ((query.indexOf("COUNT") == -1) && (query.indexOf("count") == -1))
	  //  throw new TMLExpressionException(this, "Only queries with count constructs supported " + o1);

	  StringTokenizer tokens = new StringTokenizer(query, "?");
	  int parameterCount = tokens.countTokens() - 1;
	  if (query.endsWith("?"))
		  parameterCount++;

	  sql = new SQLMap();
	  String datasource = "";
	  String user = "";

	  //sql.setDebug(true);
	  try {
		  sql.load(null);
		  if (query.indexOf(DATASOURCEDELIMITER) != -1) {
			  // Contains datasource specification
			  datasource = query.substring(0, query.indexOf(DATASOURCEDELIMITER));
			  query = query.substring(query.indexOf(DATASOURCEDELIMITER)+1);

			  if ( datasource.indexOf(USERDELIMITER) != -1 ) {
				  user = datasource.substring(0, datasource.indexOf(USERDELIMITER));
				  datasource = datasource.substring(datasource.indexOf(USERDELIMITER)+1);
			  }

			  if ( datasource != null && datasource.length() > 0 ) {
				  sql.setDatasource(datasource);
			  }
			  if ( user != null && user.length() > 0 ) {
				  sql.setUsername(user);
			  }

		  }
		  if (transactionContext != -1) {
			  //System.out.println("SINGLEVALUEQUERY: USING TRANSACTIONCONTEXT: " + transactionContext);
			  sql.setTransactionContext(transactionContext);
		  }
		  sql.setQuery(query);
		  int offset = (transactionContext == -1) ? 1 : 2;
		  for (int i = 0; i < parameterCount; i++) {
			  Object o = getOperand(i+offset);
			  sql.setParameter(o);
		  }
	  } catch (Exception e) {
		  sql.kill();
		  e.printStackTrace();
		  throw new TMLExpressionException(this, "Fatal error: " + e.getMessage());
	  } 

	  return sql;
  }
  
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {


	  SQLMap sql = evaluateQuery();

	  Object result = null;
	  try {
		  if (sql.getRowCount() > 0) {
			  result = sql.getColumnValue(new Integer(0));
		  } else {
			  //System.err.println("NO RECORDS FOUND");
		  }
	  } catch (Exception e) {
		  sql.kill();
		  e.printStackTrace();
		  throw new TMLExpressionException(this, "Fatal error: " + e.getMessage());
	  } finally {
		  try {
			  sql.store();
		  } catch (Exception e1) {
			  e1.printStackTrace();
			  throw new TMLExpressionException(this, "Fatal error: " + e1.getMessage());
		  }
		  //System.out.println("SingleValueQuery(), result = " + result);
	  }

	  return result;

  }
  
  public String usage() {
    return "SingleValueQuery('query', ?, ?, ...)";
  }
  public String remarks() {
    return "A query can be supplied to this function that executes a SQL parameter with a given number of parameters";
  }

  // ----------------------------------------------------------- private methods


} // public class SingleValueQuery extends FunctionInterface

// EOF: $RCSfile$ //

