package com.dexels.navajo.adapter.functions;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.jdbc.JDBCFactory;
import com.dexels.navajo.jdbc.JDBCMappable;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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
    private final static Logger logger = LoggerFactory.getLogger(SingleValueQuery.class);

  public static final String DATASOURCEDELIMITER = ":";
  public static final String USERDELIMITER = "@";
  private String dbIdentifier = null;

  public String getDbIdentifier() { return this.dbIdentifier; }
  public void setDbIdentifier(String dbIdentifier) {
      this.dbIdentifier = dbIdentifier;
  }

  public SingleValueQuery() {
	  super();
  }
  
  protected final JDBCMappable evaluateQuery() throws com.dexels.navajo.parser.TMLExpressionException {
	  String query = "";
	  JDBCMappable sql = null;

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
	  
	  String datasource = "";
	  String user = "";

	  try {
		  sql = JDBCFactory.getJDBCMap(getAccess());
		  if (query.indexOf(DATASOURCEDELIMITER) != -1) {
			  // Contains datasource specification
			  datasource = query.substring(0, query.indexOf(DATASOURCEDELIMITER));
			  query = query.substring(query.indexOf(DATASOURCEDELIMITER)+1);

			  if ( datasource.indexOf(USERDELIMITER) != -1 ) {
				  user = datasource.substring(0, datasource.indexOf(USERDELIMITER));
				  datasource = datasource.substring(datasource.indexOf(USERDELIMITER)+1);
			  }

			  if ( datasource != null) {
			      if (datasource.trim().equals("")) {
			          logger.warn("Ignoring empty datasource - using default!");
			      } else {
		              sql.setDatasource(datasource);
			      }
			  }
			  if ( user != null) { 
			      if (user.trim().equals("")) {
                      logger.warn("Ignoring empty user - using default!");
                  } else {
                      sql.setUsername(user);
                  }
			  }

		  }
		  if (transactionContext != -1) {
			  //logger.debug("SINGLEVALUEQUERY: USING TRANSACTIONCONTEXT: " + transactionContext);
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
		  throw new TMLExpressionException(this, "Fatal error: " + e.getMessage() + ", query = " + query,e);
	  } 

	  return sql;
  }
  
  @Override
public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
	  JDBCMappable sql = evaluateQuery();
	  setDbIdentifier(sql.getDbIdentifier());
	  Object result = null;
	  try {
		  
		  if (sql.getRowCount() > 0) {
			  result = sql.getColumnValue(new Integer(0));
		  } else {
		  }
	  } catch (Exception e) {
		  sql.kill();
		  throw new TMLExpressionException(this, "Fatal error: " + e.getMessage() + ", query = " + sql.getQuery(),e);
	  } finally {
		  try {
			  sql.store();
		  } catch (Exception e1) {
			  throw new TMLExpressionException(this, "Fatal error: " + e1.getMessage() + ", query = " + sql.getQuery(),e1);
		  }
		  //System.out.println("SingleValueQuery(), result = " + result);
	  }

	  return result;

  }
  
  @Override
public String usage() {
    return "SingleValueQuery('query', ?, ?, ...)";
  }
  @Override
public String remarks() {
    return "A query can be supplied to this function that executes a SQL parameter with a given number of parameters";
  }
  
  // ----------------------------------------------------------- private methods

}

