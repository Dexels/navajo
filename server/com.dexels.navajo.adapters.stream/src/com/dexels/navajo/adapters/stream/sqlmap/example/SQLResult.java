package com.dexels.navajo.adapters.stream.sqlmap.example;

import java.sql.SQLException;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.script.api.UserException;

public class SQLResult {
	
	private final ResultSetMap parent;

	public SQLResult(ResultSetMap parent) {
		this.parent = parent;
	}
	
	public final Object getColumnValue() {
		return parent.getColumnValue();
	}

	public final String getColumnName(final Integer index) throws SQLException {
		try {
			return parent.getColumnName(index);
		} catch (UserException e) {
			throw new SQLException(e);
		}
	}

	public final Object getColumnValue(final Integer index) throws SQLException {
		try {
			return parent.getColumnValue(index);
		} catch (UserException e) {
			throw new SQLException(e);
		}
	}

	public final Object getColumnValue(final String columnName) {
		try {
			return parent.getColumnValue(columnName);
		} catch (UserException e) {
			e.printStackTrace();
			return null;
		}
	}

	public final String getType(final String columnName) throws SQLException {
		try {
			return parent.getType(columnName);
		} catch (UserException e) {
			throw new SQLException(e);
		}

	}
}
