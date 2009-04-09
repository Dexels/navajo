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
			SQLFieldDependency sqlD = new SQLFieldDependency(this.getCurrentTimeStamp(), this.getJavaClass(), "storedproc", i.next());
			sqlDeps.add(sqlD);
		}
		
		// Find Tables/Views
		query = getEvaluatedId();
		sps = findTablesAndViews(query);
		i = sps.iterator();
		while ( i.hasNext() ) {
			SQLFieldDependency sqlD = new SQLFieldDependency(this.getCurrentTimeStamp(), this.getJavaClass(), "table", i.next());
			sqlDeps.add(sqlD);
		}
		
		sqlDepArray = new SQLFieldDependency[sqlDeps.size()];
		sqlDepArray = (SQLFieldDependency []) sqlDeps.toArray(sqlDepArray);
		
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
	
	public String getEvaluatedId() {
		String value= getId();
		value = value.replace('\n', ' ');
		value = value.replace('\'', ' ');
		value = value.replace('?', ' ');
		return value;
		
	}
	
	public static void main(String [] args) throws Exception {
		String example = "Call sp_processinsertperson( " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                        "?, ?, ?, ?, ?, ?, ?)";
		
		String example2 = "SELECT " + 
		                  " (SELECT SUM(noot) FROM mies )" +
		                  " , ( SELECT COUNT(*) " +
		                  ",  (SELECT codedesc FROM vw_code_obj, (SELECT kokosnoot FROM\noerwoud\noer\nWHERE\ncodeid = vw_matches.status AND typeofcode = 'MATCH_STATUS' AND language = get_default_language()) AS statusdesc," + 
                          "FROM   organizationperson um " + 
                          "WHERE  um.personid      = vw_person.personid " +
                          "AND    um.roleid        = 'UNIONMEMBER' " + 
                          "AND    TRUNC( SYSDATE ) BETWEEN TRUNC( um.relationstart ) AND TRUNC( um.relationend ) " +
                          ") AS isunionmember " +
                       ",( SELECT COUNT(*) " +
                          "FROM   organizationpersonhistory hm " + 
                          "WHERE  hm.personid = vw_person.personid " +
                          "AND    hm.roleid   = 'UNIONMEMBER' " +
                        ") AS isoldunionmember " +
                        " , CASE WHEN vmc.status = 'INVALIDATED' THEN 'INVALIDATED' " +
              "WHEN vmc.status IN ('CANCELED','DRAFT') THEN 'CANCELED' " +
             "WHEN vmc.value IN ('NEW', 'SCHEDULED', 'DRAFT') THEN 'INSERTED' " +
             "WHEN ( ( SELECT COUNT(DISTINCT key) FROM changelogdetail v WHERE v.changelogid = vmc.changelogid AND v.processed = '0' AND v.key NOT LIKE 'NEW_%' ) > 1 AND vmc.status NOT IN ('INVALIDATED','CANCELED'))" +
                                             "THEN 'MULTIPLE_CHANGES'" +
             "ELSE vmc.key " +
		                  "FROM\n" + "vw_person, aap, vw_person, kibbeling\n" + "WHERE\n" +  "aap.id = vw_person.id";

		String example3 = "SingleValueQuery( 'sportlinkkernel: SELECT COUNT(*) FROM vw_player WHERE personid = ? AND organizationid = ? AND sportid = ?'," +
                           "[/@PersonId], [/@OrganizationId], [/@SportId]) &gt; 0";
		
		SQLFieldDependency s = new SQLFieldDependency(-1, "SQLMap", "sql", example3);
		
		System.err.println(s.hasMultipleDependencies());
		AdapterFieldDependency [] deps = (AdapterFieldDependency []) s.getMultipleDependencies();
		for ( int i = 0; i < deps.length; i++ ) {
			System.err.println(deps[i].getType() + "->" + deps[i].getId());
		}
		
		
		
	}

}
