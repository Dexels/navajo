package com.dexels.navajo.adapter.descriptionprovider;

import java.util.HashMap;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.descriptionprovider.BaseDescriptionProvider;

/**
 * This class reads the propertydescription table into a Java datastructure.
 * 
 * @author Arjen Schoneveld.
 *
 */
public class FastDescriptionProvider extends BaseDescriptionProvider {

	private String queryLocales = "SELECT DISTINCT locale FROM propertydescription WHERE name = ?";
	
	private String querySublocales = "SELECT DISTINCT NVL(sublocale, '%') FROM propertydescription WHERE name = ? AND locale = ?";
	
	private String queryUsers = "SELECT DISTINCT NVL(objectid, '%') FROM propertydescription WHERE name = ? AND locale = ? AND NVL(sublocale,'%') = ?";
	
	private String queryWebservices = "SELECT DISTINCT NVL(context, '%') FROM propertydescription WHERE name = ? " + 
									  "AND locale = ? AND NVL(sublocale,'%') = ? AND NVL(objectid, '%') = ?";
	
	private String queryDescription = "SELECT description FROM propertydescription WHERE name = ? " + 
								      "AND locale = ? AND NVL(sublocale,'%') = ? AND NVL(objectid, '%') = ? AND NVL(context,'%') = ?";
	
	private static HashMap properties = new HashMap();
	private static Object semaphore = new Object();
	
	private void initializeCache(String propertyName) throws UserException {

		if ( properties.get(propertyName) == null ) {

			synchronized ( semaphore ) {

				if ( properties.get(propertyName) == null ) {

					SQLMap sql = createConnection();
					;
					try {
						sql.setQuery(queryLocales);
						sql.setParameter(propertyName);
						ResultSetMap [] results = sql.getResultSet();
					
						HashMap locales = new HashMap();
						// Fill locales.
						//System.err.println("Found locales: " + results.length);
						
						if ( results.length == 0 ) {
							//System.err.println("Putting " + propertyName + " as null translation");
							properties.put(propertyName, propertyName);
							return;
						}
						for (int i = 0; i < results.length; i++) {
							String localeString = (String) results[i].getColumnValue(new Integer(0));
							HashMap subLocales = new HashMap();

							sql.setQuery(querySublocales);
							sql.setParameter(propertyName);
							sql.setParameter(localeString);
							
							
							ResultSetMap [] sublocaleresults = sql.getResultSet();
							//System.err.println("Found sublocales: " + sublocaleresults.length);
							
							// Fill sublocales.
							for (int j = 0; j < sublocaleresults.length; j++) {
								String subLocaleString = (String) sublocaleresults[j].getColumnValue(new Integer(0));
								HashMap users = new HashMap();

								sql.setQuery(queryUsers);
								sql.setParameter(propertyName);
								sql.setParameter(localeString);
								sql.setParameter(subLocaleString);

								ResultSetMap [] userresults = sql.getResultSet();
								//System.err.println("Found users: " + userresults.length);
								// Fill users.
								for (int k = 0; k < userresults.length; k++) {
									String userString = (String) userresults[k].getColumnValue(new Integer(0));
									HashMap webservices = new HashMap();
									sql.setQuery(queryWebservices);
									sql.setParameter(propertyName);
									sql.setParameter(localeString);
									sql.setParameter(subLocaleString);
									sql.setParameter(userString);

									ResultSetMap [] webserviceresults = sql.getResultSet();
									//System.err.println("Found webservices: " + webserviceresults.length);
									// Fill webservices.
									for (int l = 0; l < webserviceresults.length; l++) {
										String webserviceString = (String) webserviceresults[l].getColumnValue(new Integer(0));
										sql.setQuery(queryDescription);
										sql.setParameter(propertyName);
										sql.setParameter(localeString);
										sql.setParameter(subLocaleString);
										sql.setParameter(userString);
										sql.setParameter(webserviceString);

										if ( sql.getResultSet() != null && sql.getResultSet().length > 0 ) {
											String description = (String) sql.getResultSet()[0].getColumnValue(new Integer(0));
											webservices.put(webserviceString, description);
										}

									}
									//System.err.println("Putting for " + userString + ", " + webservices + " into users");
									users.put(userString, webservices);
								}
								//System.err.println("Putting " + subLocaleString + " into hashmap sublocales " + subLocales);
								subLocales.put(subLocaleString, users);
							}

							locales.put(localeString, subLocales);
						}
						//System.err.println("Putting " + locales + " into hashmap for " + propertyName);
						properties.put(propertyName, locales);
					} finally {
						try {
							//System.err.println("Calling sqlmap store");
							sql.store();
						} catch (MappableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if ( properties.get(propertyName) == null ) {
					properties.put(propertyName, propertyName);
				}
 			}
		}
	}
	
	private final String getLocaleTranslation(String propertyName, String defaultDescription, String locale, String subLocale, String user, String webservice) {

		// Check for null translation first.
		if ( properties.get(propertyName) instanceof String ) {
			return defaultDescription;
		}
		
		HashMap locales = (HashMap) properties.get(propertyName);
		
		HashMap users = null;
		// Check if there are non-generic sublocales.
		if ( locales.get(locale) == null ) {
			return defaultDescription;
		}
		if ( ((HashMap) locales.get(locale)).size() > 1 ) {
			users = (HashMap) ((HashMap) locales.get(locale)).get(subLocale);
		}
		// Dit not find specific sublocale, use generic sublocale.
		if ( users == null ) {
			users = (HashMap) ((HashMap) locales.get(locale)).get("%");
		}
		if ( users == null ) {
			// Generic sublocale does not exist.
			return defaultDescription;
		}
		HashMap webservices = null;
		// Check if there are non-generic users.
		if ( users.size() > 1 ||  users.get(user) != null ) {
			webservices = (HashMap) users.get(user);
		}
		if ( webservices == null ) {
			webservices = (HashMap) users.get("%");
		}
		if ( webservices == null ) {
			// Generic translation does not exist.
			return defaultDescription;
		}
		String description = null;
		// Check if there are non-generic webservices.
		if ( webservices.size() > 1 || webservices.get(webservice) != null ) {
			description = (String) webservices.get(webservice);
		}
		if ( description == null ) {
			description = (String) webservices.get("%");
		}
		if ( description == null ) {
			return defaultDescription;
		}
		
		return description;
	}
	
	public String getTranslation(String propertyName, String defaultDescription, String locale, String subLocale, String user, String webservice) {

		
		try {
			//System.err.println("Getting description for: " + propertyName + ", locale: " + locale + ", sublocale: " + subLocale + ", user: " + user + ", webservice: " + webservice);
			initializeCache(propertyName);
			return getLocaleTranslation(propertyName, defaultDescription, locale, subLocale, user, webservice);

		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		// By default return defaultDescription.
		return defaultDescription;

	}
	
	private final SQLMap createConnection() {
		
		SQLMap sqlMap = new SQLMap();
		// sqlMap.debug = true;
		try {
			
			NavajoConfig nc = null;
			if ( Dispatcher.getInstance() != null ) {
				nc = Dispatcher.getInstance().getNavajoConfig();
			} else {
				nc = Dispatcher.getInstance(new java.net.URL("file:///home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/config/server.xml"), 
				  new com.dexels.navajo.server.FileInputStreamReader(), "aap").getNavajoConfig();
			}
			sqlMap.load(null, null, null, Dispatcher.getInstance().getNavajoConfig());
			sqlMap.setDatasource("navajostore");
		}
		catch (Exception ex) {
			ex.printStackTrace(System.err);
			return null;
		}
		
		return sqlMap;
	}
	
	public static void main(String [] args) {
		FastDescriptionProvider pdc = new FastDescriptionProvider();
		String value = pdc.getTranslation("kibasd", "apenoot", "nl", null, "PIET", "ProcessNoot");
		System.err.println("value = " + value);
//		value = pdc.getTranslation("BusinessRegistrationNumber", "apenoot", "nl", null, "PIET", "ProcessApenoot");
//		System.err.println("value2 = " + value);
//		value = pdc.getTranslation("BusinessRegistrationNumber", "apenoot", "nl", null, "WILLEM", "ProcessAap");
//		System.err.println("value2 = " + value);
//		value = pdc.getTranslation("BusinessRegistrationNumber", "apenoot", "nl", null, "HENK", "ProcessNoot");
//		System.err.println("value2 = " + value);
//		value = pdc.getTranslation("BusinessRegistrationNumber", "apenoot", "nl", null, "HENK", "ProcessKibbeling");
//		System.err.println("value2 = " + value);
//		long start = System.currentTimeMillis();
//		for ( int i = 0; i < 10000; i++ ) {
//			value = pdc.getTranslation("BusinessRegistrationNumber", "apenoot", "nl", "KNHB", "HENK", "ProcessKibbeling");
//		}
//		System.err.println("10000 iterations took: " + ( System.currentTimeMillis() - start ) / 10000.0 + " millis / iteration");
//		System.err.println("value2 = " + value);
	}

	public void updateProperty(Navajo in, Property element, String locale) {
		String translation = 
			getTranslation(element.getName(), element.getDescription(), 
					locale, in.getHeader().getAttribute("sublocale"), 
					in.getHeader().getRPCUser(), in.getHeader().getRPCName());
		element.setDescription(translation);
	}

	public void deletePropertyContext(String locale, String context) {
		// TODO Auto-generated method stub
		
	}

	public Message dumpCacheMessage(Navajo n) throws NavajoException {
		// TODO Auto-generated method stub
		return null;
	}

	public void flushCache() {
		synchronized ( semaphore ) {
			properties.clear();
		}
	}

	public void flushUserCache(String user) {
		// TODO Auto-generated method stub
		
	}

	public int getCacheSize() {
		return properties.size();
	}

	public void updateDescription(String locale, String name, String description, String context, String username) {
		// TODO Auto-generated method stub
		
	}
	
}
