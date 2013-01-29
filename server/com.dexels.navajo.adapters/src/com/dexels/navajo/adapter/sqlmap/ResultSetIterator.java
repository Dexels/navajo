package com.dexels.navajo.adapter.sqlmap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.SQLMap;

public class ResultSetIterator implements Iterator<ResultSetMap> {
	
	private ResultSet rs = null;
	private ResultSetMetaData meta = null;
	private int columns;
	
	Logger logger = LoggerFactory.getLogger(ResultSetIterator.class);
	
	public ResultSetIterator(ResultSet rs, ResultSetMetaData meta, int columns) {
		this.rs = rs;
		this.meta = meta;
		this.columns = columns;
	}

	@Override
	public boolean hasNext() {
		try {
			return rs.next();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	public ResultSetMap next() {
		try {
			return SQLMap.getResultSetMap(meta, columns, rs);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public void remove() {
		
	}
	
	
	public void close() {
		try {
			rs.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
}
