package com.dexels.navajo.adapter.descriptionprovider;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.descriptionprovider.CachedDescriptionProvider;
import com.dexels.navajo.server.statistics.TodoItem;

public class DatabaseDescriptionProvider extends CachedDescriptionProvider {

	private static final String DEFAULT_LOCALE = "nl";

	private int retrieveCount = 0;
	private static String selectGeneric =
		"select description from propertydescription where locale= ? and name= ?";
	private static String selectWithService =
		"select description from propertydescription where locale= ? and name= ? and webservice = ?";
	private static String selectWithServiceAndUser =
		"select description from propertydescription where locale= ? and name= ? and webservice = ? and username = ?";
		
	
	public void updateProperty(Navajo in, Property element, String locale) {
		if (locale==null) {
			locale=DEFAULT_LOCALE;

		}
		String username = in.getHeader().getRPCUser();
		String webservice = in.getHeader().getRPCName();
		
		String desc = getDescription(locale, username, webservice, element.getName());
		if (desc!=null) {
			element.setDescription(desc);
		} 
	}
	
	private synchronized void increment() {
		retrieveCount++;
	}
	
	protected String retrieveDescription(String locale, String username, String webservice, String propertyName) {
// System.err.println("Retrieving: "+username+"|"+webservice+"|"+propertyName);
// dumpStack();
// return username+"|"+webservice+"|"+propertyName;
// increment();
		return null;
	}



	protected String retrieveDescription(String locale, String webservice, String propertyName) {
//		System.err.println("(w)Retrieving: "+propertyName);
		SQLMap sqlMap = createConnection();
		sqlMap.debug = true;
		// sqlMap.debug = true;
		Connection con = null;
		try {
			con = sqlMap.getConnection();
			System.err.println("In OracleStore, connection is " + con.hashCode() );
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
		// System.err.println("I got a connection: "+con);
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(selectWithService);
				ps.setString(1, locale);
				ps.setString(2, propertyName);
				ps.setString(3, webservice);
				ResultSet r = ps.executeQuery();
			       if (r.next()) {
						String desc = r.getString("description");
//						System.err.println("Found description: "+desc);
						return desc;
					}
				
				return null;
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
					}
				}

				if (con != null) {
					try {
						sqlMap.store();
					}
					catch (Exception ex1) {
						ex1.printStackTrace(System.err);
					}
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
	private void dumpStack() {
		System.err.println("STACK DUMP: >"+cache.size()+"< retrieves: "+retrieveCount);
	}
	
	private final SQLMap createConnection() {
		
		SQLMap sqlMap = new SQLMap();
		// sqlMap.debug = true;
		try {
			sqlMap.load(null, null, null, Dispatcher.getInstance().getNavajoConfig());
			sqlMap.setDatasource("navajostore");
		}
		catch (Exception ex) {
			ex.printStackTrace(System.err);
			return null;
		}
		
		return sqlMap;
	}
	
	protected String retrieveDescription(String locale, String propertyName) {
		System.err.println("Retrieving: "+propertyName);
		SQLMap sqlMap = createConnection();
		// sqlMap.debug = true;
		Connection con = null;
		try {
			con = sqlMap.getConnection();
			System.err.println("In OracleStore, connection is " + con.hashCode() );
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
		// System.err.println("I got a connection: "+con);
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(selectGeneric);
				ps.setString(1, locale);
				ps.setString(2, propertyName);
				ResultSet r = ps.executeQuery();
			       if (r.next()) {
//			    	   System.err.println("Query: "+selectGeneric+" p1: "+locale+" p2: "+propertyName);
			    	   String desc = r.getString("description");
//						System.err.println("Found description: "+desc);
						return desc;
			         }
				return null;
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
					}
				}

				if (con != null) {
					try {
						sqlMap.store();
					}
					catch (Exception ex1) {
						ex1.printStackTrace(System.err);
					}
				}
			}
		}
		return null;
	}
}

	
	
	

