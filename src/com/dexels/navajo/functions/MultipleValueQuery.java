package com.dexels.navajo.functions;

import java.util.ArrayList;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.parser.TMLExpressionException;

public final class MultipleValueQuery extends SingleValueQuery {

	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {


		SQLMap sql = evaluateQuery();

		ArrayList result = new ArrayList();
		try {
			ResultSetMap [] resultSet = sql.getResultSet();
			if (resultSet.length > 0) {
				for (int i = 0; i < resultSet.length; i++ ) {
					result.add(resultSet[i].getColumnValue(new Integer(0)));
				}
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

	public String remarks() {
		return "A query can be supplied to this function that executes a SQL parameter with a given number of parameters, result is an ArrayList";
	}

	public String usage() {
		return "MultipleValueQuery('query', ?, ?, ...)";
	}

}