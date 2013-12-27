package com.dexels.navajo.mapping.compiler.meta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class is used to store database object dependencies that are hidden in adapter fields.
 * It finds database objects (stored procedures, views and tables) within a SQL statement.
 * It implements the getMultipleDependencies() method to indicate that dependencies of this type
 * are retrieved in two phases: first create an instance of SQLFieldDependency with the appropriate
 * SQLFieldDependency(...) constructor. The second phase should call getMultipleDependencies() in order
 * to retrieve the actual adapter field dependencies.
 * 
 * @author arjen
 *
 */
public class SQLFieldDependency extends AdapterFieldDependency {

	SQLFieldDependency [] sqlDepArray = null;
	
	public SQLFieldDependency(long timestamp, String adapterClass, String type, String id) {
		super(timestamp, adapterClass, type, id);
		findSQLDependencies();
	}
	
	@Override
	public Dependency [] getMultipleDependencies() {
		return sqlDepArray;
	}
	
	private void findSQLDependencies() {
		
		ArrayList<SQLFieldDependency> sqlDeps = new ArrayList<SQLFieldDependency>();
		
		// Find SP's
		String query = getEvaluatedId();
		Set<String> sps = findStoredProcs(query);
		Iterator<String> i = sps.iterator();
		while ( i.hasNext() ) {
			String spName = i.next();
			spName = spName.replace('\n', ' ').trim();
			SQLFieldDependency sqlD = new SQLFieldDependency(this.getCurrentTimeStamp(), 
					                                         this.getJavaClass(), "storedproc", spName );
			sqlDeps.add(sqlD);
		}
		
		// Find Tables/Views
		query = getEvaluatedId();
		sps = findTablesAndViews(query);
		i = sps.iterator();
		while ( i.hasNext() ) {
			String tableName = i.next();
			tableName = tableName.replace('\n', ' ').trim();
			SQLFieldDependency sqlD = new SQLFieldDependency(this.getCurrentTimeStamp(), 
					                                         this.getJavaClass(), "table", tableName );
			sqlDeps.add(sqlD);
		}
		
		sqlDepArray = new SQLFieldDependency[sqlDeps.size()];
		sqlDepArray = sqlDeps.toArray(sqlDepArray);
		
	}
	
	private final int [] findKeyword(String keyword, String query) {
		
		
		int from = query.indexOf(" " + keyword + " ");
		int length = keyword.length() + 2;
		
		return new int[]{from,length};
	}
	
	private Set<String> findTablesAndViews(String query) {
		HashSet<String> sps = new HashSet<String>();

		query = query.toLowerCase();
		
		int [] from = findKeyword("from", query);
		
		while ( from[0] != -1 ) {
			query = query.substring(from[0] + from[1]);
			//System.err.println("query = " + query);
			// Find WHERE OR EOQ (End Of Query)
			String subQuery = query;
			int bracket = query.indexOf(")");
			if ( bracket != -1 ) {
				//System.err.println("bracket = " + bracket);
				subQuery = query.substring(0, bracket);
				
			}
			int [] where = findKeyword("where", subQuery); //subQuery.indexOf(" where ");
		
			if ( where[0] != -1 ) {
				subQuery = subQuery.substring(0, where[0]);
			} 
			//System.err.println("subQuery = " + subQuery);
			// Tokenize colons
			String [] objects = subQuery.split(",");
			for ( int i = 0; i < objects.length; i++ ) {
				// Remove name qualifier.
				String qualifiedname = objects[i].trim();
				qualifiedname = ( qualifiedname.indexOf(" ") != -1 ? qualifiedname.split(" ")[0] : qualifiedname );
				qualifiedname = qualifiedname.replaceAll("\\(", "");
				qualifiedname = qualifiedname.replaceAll("\\)", "");
				qualifiedname = qualifiedname.trim();
				if ( !qualifiedname.equals("select")) {
					sps.add(qualifiedname);
				}
			}
			from = findKeyword("from", query);
			//System.err.println("from = " + from);
		}
		
		return sps;
	}
	
	private Set<String> findStoredProcs(String query) {
		HashSet<String> sps = new HashSet<String>();
		
		query = query.toLowerCase();
		
		// Find call statement.
		StringBuffer spName = new StringBuffer();
		int call = query.indexOf("call");
		while ( call != -1 ) {
			query = query.substring(call+4);
			// Find first occurence of (.
			char [] chars = query.toCharArray();
			int index = 0;
			//System.err.println(chars.length);
			while ( index < chars.length && chars[index] != '(' ) {
				if ( chars[index] != ' ' ) {
					spName.append(chars[index]);
				}
				index++;
			}
			if ( !spName.toString().equals("" ) && !spName.toString().equals("select") && !spName.toString().equals("(")) {
				
				sps.add(spName.toString());
			}
			call = query.indexOf("call");
			//System.err.println("call = " + call);
		}
		
		return sps;
	}
	
	@Override
	public String getEvaluatedId() {
		String value= getId();
		value = value.replace('\n', ' ');
		value = value.replace('\'', ' ');
		value = value.replace('?', ' ');
		return value;
	}
	
}
