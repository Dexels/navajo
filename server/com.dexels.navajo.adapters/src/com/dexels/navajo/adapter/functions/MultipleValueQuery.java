package com.dexels.navajo.adapter.functions;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.jdbc.JDBCMappable;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class MultipleValueQuery extends SingleValueQuery {

	
	private static final Logger logger = LoggerFactory.getLogger(MultipleValueQuery.class);

	@Override
	public final Object evaluate() {


		JDBCMappable sql = evaluateQuery();

		ArrayList result = new ArrayList();
		try {
			ResultSetMap [] resultSet = sql.getResultSet();
			if (resultSet.length > 0) {
				for (int i = 0; i < resultSet.length; i++ ) {
					result.add(resultSet[i].getColumnValue(Integer.valueOf(0)));
				}
			}
		} catch (Exception e) {
			sql.kill();
			throw new TMLExpressionException(this, "Fatal error: " + e.getMessage(),e);
		} finally {
			try {
				sql.store();
			} catch (Exception e1) {
				logger.error("Error: ", e1);
			}
		}

		return result;

	}

	@Override
	public String remarks() {
		return "A query can be supplied to this function that executes a SQL parameter with a given number of parameters, result is an ArrayList";
	}

	@Override
	public String usage() {
		return "MultipleValueQuery('query', ?, ?, ...)";
	}

}