/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.functions;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.jdbc.JDBCFactory;
import com.dexels.navajo.jdbc.JDBCMappable;

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
    private static final Logger logger = LoggerFactory.getLogger(SingleValueQuery.class);

  static final String DATASOURCEDELIMITER = ":";
  private static final String USERDELIMITER = "@";
  private String dbIdentifier = null;

  public String getDbIdentifier() { return this.dbIdentifier; }
  public void setDbIdentifier(String dbIdentifier) {
      this.dbIdentifier = dbIdentifier;
  }

  public SingleValueQuery() {
	  super();
  }
  
  protected final JDBCMappable evaluateQuery() {
	  String query = "";
	  JDBCMappable sql = null;

	  int transactionContext = -1;

	  // String read query.
	  Object o1 = operand(0).value;
	  if (o1 instanceof Integer) {  // TransactionContext set.
		  transactionContext = ((Integer) o1).intValue();
		  Object o2 = operand(1).value;
		  if (!(o2 instanceof String))
			  throw new TMLExpressionException(this, "Invalid argument: " + o2);
		  query = (String) o2;
	  } else if (o1 instanceof String) { // No TransactionContext set.
		  query = (String) o1;
	  } else {
		  throw new TMLExpressionException(this, "Invalid argument: " + o1);
	  }



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
			  if ( user != null && !user.trim().equals("")) { 
			     sql.setUsername(user);
			  }

		  }
		  if (transactionContext != -1) {
			  sql.setTransactionContext(transactionContext);
		  }
		  sql.setQuery(query);
		  int offset = (transactionContext == -1) ? 1 : 2;
		  for (int i = 0; i < parameterCount; i++) {
			  Object o = operand(i+offset).value;
			  sql.setParameter(o);
		  }
	  } catch (Exception e) {
		  sql.kill();
		  throw new TMLExpressionException(this, "Fatal error: " + e.getMessage() + ", query = " + query,e);
	  } 

	  return sql;
  }
  
  @Override
public Object evaluate() {
	  JDBCMappable sql = evaluateQuery();
	  setDbIdentifier(sql.getDbIdentifier());
	  Object result = null;
	  try {
		  
		  if (sql.getRowCount() > 0) {
			  result = sql.getColumnValue(Integer.valueOf(0));
		  } else {
		  }
	  } catch (Exception e) {
		  sql.kill();
		  throw new TMLExpressionException(this, "Fatal error: " + e.getMessage() + ", query = " + sql.getQuery(),e);
	  } finally {
		  try {
			  sql.store();
		  } catch (Exception e1) {
			  logger.error("Fatal error: query = "+sql.getQuery(), e1);
		  }
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

