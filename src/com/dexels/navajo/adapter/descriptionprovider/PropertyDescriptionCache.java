package com.dexels.navajo.adapter.descriptionprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.UserException;

/**
 * This class reads the propertydescription table into a Java datastructure.
 * 
 * @author Arjen Schoneveld.
 *
 */
public class PropertyDescriptionCache {

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

					try {
						sql.setQuery(queryLocales);
						sql.setParameter(propertyName);
						ResultSetMap [] results = sql.getResultSet();
						HashMap locales = new HashMap();
						// Fill locales.
						for (int i = 0; i < results.length; i++) {
							String localeString = (String) results[i].columnValue;
							HashMap subLocales = new HashMap();

							sql.setQuery(querySublocales);
							sql.setParameter(propertyName);
							sql.setParameter(localeString);

							ResultSetMap [] sublocaleresults = sql.getResultSet();
							// Fill sublocales.
							for (int j = 0; j < sublocaleresults.length; j++) {
								String subLocaleString = (String) sublocaleresults[j].columnValue;
								HashMap users = new HashMap();

								sql.setQuery(queryUsers);
								sql.setParameter(propertyName);
								sql.setParameter(localeString);
								sql.setParameter(subLocaleString);

								ResultSetMap [] userresults = sql.getResultSet();
								// Fill users.
								for (int k = 0; k < userresults.length; k++) {
									String userString = (String) userresults[k].columnValue;
									HashMap webservices = new HashMap();
									sql.setQuery(queryWebservices);
									sql.setParameter(propertyName);
									sql.setParameter(localeString);
									sql.setParameter(subLocaleString);
									sql.setParameter(userString);

									ResultSetMap [] webserviceresults = sql.getResultSet();
									// Fill webservices.
									for (int l = 0; l < webserviceresults.length; l++) {
										String webserviceString = (String) webserviceresults[l].columnValue;
										sql.setQuery(queryDescription);
										sql.setParameter(propertyName);
										sql.setParameter(localeString);
										sql.setParameter(subLocaleString);
										sql.setParameter(userString);
										sql.setParameter(webserviceString);

										if ( sql.getResultSet() != null && sql.getResultSet().length > 0 ) {
											String description = (String) sql.getResultSet()[0].columnValue;
											webservices.put(webserviceString, description);
										}

									}

									users.put(userString, webservices);
								}

								subLocales.put(subLocaleString, users);
							}

							locales.put(localeString, subLocales);
						}
						properties.put(propertyName, locales);
					} finally {
						try {
							sql.store();
						} catch (MappableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	private String getLocaleTranslation(String propertyName, String locale, String subLocale, String user, String webservice) {

		HashMap locales = (HashMap) properties.get(propertyName);
		HashMap users = null;
		// Check if there are non-generic sublocales.
		if ( ((HashMap) locales.get(locale)).size() > 1 ) {
			users = (HashMap) ((HashMap) locales.get(locale)).get(subLocale);
		}
		// Dit not find specific sublocale, use generic sublocale.
		if ( users == null ) {
			users = (HashMap) ((HashMap) locales.get(locale)).get("%");
		}
		HashMap webservices = null;
		// Check if there are non-generic users.
		if ( users.size() > 1 ) {
			webservices = (HashMap) users.get(user);
		}
		if ( webservices == null ) {
			webservices = (HashMap) users.get("%");
		}
		String description = null;
		// Check if there are non-generic webservices.
		if ( webservices.size() > 1 ) {
			description = (String) webservices.get(webservice);
		}
		if ( description == null ) {
			description = (String) webservices.get("%");
		}
		
		return description;
	}
	
	public String getTranslation(String propertyName, String locale, String subLocale, String user, String webservice) {

		
		try {
		
			initializeCache(propertyName);
			return getLocaleTranslation(propertyName, locale, subLocale, user, webservice);

		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		// By default return propertyName.
		return propertyName;

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
	
}
