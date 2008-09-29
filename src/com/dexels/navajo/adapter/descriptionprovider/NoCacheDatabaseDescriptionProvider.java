package com.dexels.navajo.adapter.descriptionprovider;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.descriptionprovider.BaseDescriptionProvider;
import com.dexels.navajo.server.enterprise.descriptionprovider.PropertyDescription;

public class NoCacheDatabaseDescriptionProvider extends BaseDescriptionProvider {

	private static String insertPropertyDescription = "INSERT INTO propertydescription ( descriptionid, locale, name, context, objectid, objecttype, description, lastupdate, updateby) VALUES ( propertydescription_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";                      
	
	private static String selectDescription = 
		"SELECT description FROM (" +
			"SELECT name, locale||'|'||NVL(sublocale, '%')||'|'||name||'|'||NVL(objectid, '%')||'|'||NVL(context, '%') AS key,NVL(context, '0') AS context,NVL(objectid, '0') AS objectid,description " +
			"FROM propertydescription WHERE name = ? AND locale = ? AND ( sublocale = ? OR sublocale IS NULL ) AND ( context = ? OR context IS NULL ) AND " +
			"( objectid = ? OR objectid IS NULL ) ORDER BY sublocale||objectid||context DESC ) WHERE rownum = 1";
	
	
	public void updateProperty(Navajo in, Property element, String locale) {
		String subLocale = in.getHeader().getHeaderAttribute("sublocale");

		String retrievedDescription = retrieveDescription(element.getName(), locale, subLocale, in.getHeader().getRPCName(), in.getHeader().getRPCUser());
		if(retrievedDescription!=null) {
			element.setDescription(retrievedDescription);
		}
	}

	public void deletePropertyContext(String locale, String context) {
		// TODO, used for tipi codebase loading.

	}
	private final SQLMap createConnection() {
		
		SQLMap sqlMap = new SQLMap();
		// sqlMap.debug = true;
		try {
			sqlMap.load(null);
			sqlMap.setDatasource("navajostore");
		}
		catch (Exception ex) {
			ex.printStackTrace(System.err);
			return null;
		}
		
		return sqlMap;
	}
	
	public void flushCache() {
		// nada
	}

	public void flushUserCache(String user) {
		// nada
	}

	public int getCacheSize() {
		// nada
		return 0;
	}
	
	

	protected String retrieveDescription( String propertyName, String locale, String subLocale,  String context, String username) {
		SQLMap sqlMap = createConnection();
		Connection con = null;
		try {
			con = sqlMap.getConnection();
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(selectDescription);
				ps.setString(1, propertyName);
				ps.setString(2, locale);
				ps.setString(3, subLocale);
				ps.setString(4, context);
				ps.setString(5, username);
				ResultSet r = ps.executeQuery();
			       if (r.next()) {
						String desc = r.getString("description");
						System.err.println("Found description: "+desc);
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


	public void insertPropertyDescription(String name, String locale, String description, String context, String username) {
		SQLMap sqlMap = createConnection();
		Connection con = null;
		try {
			con = sqlMap.getConnection();
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
		if (con != null) {
			PreparedStatement ps = null;
			try {

//private static String insertPropertyDescription = "INSERT INTO propertydescription ( descriptionid, locale, name, webservice, objectid, objecttype, description, lastupdate, updateby VALUES ( propertydescription_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";                      
				
				
				ps = con.prepareStatement(insertPropertyDescription);
				ps.setString(1, locale);
				ps.setString(2, name);
				ps.setString(3, context);
				ps.setString(4, null);
				ps.setString(5, null);
				ps.setString(6, description);
				ps.setDate(7, new Date(System.currentTimeMillis()));
				ps.setString(8, username);
				int r = ps.executeUpdate();
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

	}

	private void deleteUnboundContext(String context) {
		
	}
	
	public void updateDescription(String locale, String name, String description, String context, String username) {
		// TODO Auto-generated method stub
		insertPropertyDescription(name, locale, description, context,username);
	}

	public void updatePropertyDescription(PropertyDescription pd) {
		// TODO Auto-generated method stub
		
	}	
	
}
