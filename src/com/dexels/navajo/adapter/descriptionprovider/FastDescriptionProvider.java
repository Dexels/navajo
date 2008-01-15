package com.dexels.navajo.adapter.descriptionprovider;

import java.util.HashMap;
import java.util.Iterator;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.descriptionprovider.BaseDescriptionProvider;
import com.dexels.navajo.server.enterprise.descriptionprovider.PropertyDescription;

/**
 * This class reads the propertydescription table into a Java datastructure.
 * 
 * @author Arjen Schoneveld.
 *
 */
public class FastDescriptionProvider extends BaseDescriptionProvider {

	public PropertyDescription propertyDescription;
	
	private String queryLocales = "SELECT DISTINCT locale FROM propertydescription WHERE name = ?";
	
	private String querySublocales = "SELECT DISTINCT NVL(sublocale, '%') FROM propertydescription WHERE name = ? AND locale = ?";
	
	private String queryUsers = "SELECT DISTINCT NVL(objectid, '%') FROM propertydescription WHERE name = ? AND locale = ? AND NVL(sublocale,'%') = ?";
	
	private String queryContexts = "SELECT DISTINCT NVL(context, '%') FROM propertydescription WHERE name = ? " + 
									  "AND locale = ? AND NVL(sublocale,'%') = ? AND NVL(objectid, '%') = ?";
	
	private String queryDescription = "SELECT description FROM propertydescription WHERE name = ? " + 
								      "AND locale = ? AND NVL(sublocale,'%') = ? AND NVL(objectid, '%') = ? AND NVL(context,'%') = ?";
	
	private String updateDescription = "UPDATE propertydescription " + 
	   "SET description = ?, locale = ?, sublocale = ?, context = ?, object = ?, lastupdate = sysdate, updateby = ? " + 
	   "WHERE descriptionid = ?";
	
	
	private static HashMap properties = new HashMap();
	private static Object semaphore = new Object();

	private String datasource;
	private Access myAccess;
	
	public void load(Parameters parms, Navajo inMessage, Access access,	NavajoConfig config) throws MappableException, UserException {
		myAccess = access;
	}
	
	public void store() {
		
	}
	
	public void kill() {
		
	}
	
	/**
	 * clear cache for specific property (if user is null) or for specific property and user (if user is not null).
	 * 
	 * @param propertyName
	 * @param locale
	 * @param user
	 */
	private void clearCache(String propertyName, String locale, String user) {
		if ( locale == null || user == null ) {
			synchronized ( semaphore ) {
				properties.remove(propertyName);
			}
			return;
		}

		synchronized ( semaphore ) {
			HashMap locales = (HashMap) properties.get(propertyName);
			if ( locales != null ) {
				Iterator all = locales.values().iterator();
				while ( all.hasNext() ) {
					HashMap users = (HashMap) all.next();
					users.remove(users);
				}
			}
		}
	}
	
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
//						System.err.println("Found locales: " + results.length);

						if ( results.length == 0 ) {
//							System.err.println("Putting " + propertyName + " as null translation");
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
//							System.err.println("Found sublocales: " + sublocaleresults.length);

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
									sql.setQuery(queryContexts);
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
//									System.err.println("Putting for " + userString + ", " + webservices + " into users");
									users.put(userString, webservices);
								}
								//System.err.println("Putting " + subLocaleString + " into hashmap sublocales " + subLocales);
								subLocales.put(subLocaleString, users);
							}

							locales.put(localeString, subLocales);
						}
						//System.err.println("Putting " + locales + " into hashmap for " + propertyName);
						properties.put(propertyName, locales);
					}

					finally {
						try {
							sql.store();
						} catch (MappableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UserException e) {
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
//			System.err.println("Null translation found. Returning default.");
			return defaultDescription;
		}
		
		HashMap locales = (HashMap) properties.get(propertyName);

		HashMap users = null;
		// Check if there are non-generic sublocales.
		if ( locales.get(locale) == null ) {
//			System.err.println("No locales found. Returning default. ");
			return defaultDescription;
		}
		if ( ((HashMap) locales.get(locale)).size() > 1 || ((HashMap) locales.get(locale)).get("%") == null ) {
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
//			System.err.println("Users found. Returning set: "+webservices);
		}
		if ( webservices == null ) {
			webservices = (HashMap) users.get("%");
//			System.err.println("Generic services found: "+webservices);
		}
		if ( webservices == null ) {
			// Generic translation does not exist.
			return defaultDescription;
		}
		String description = null;
		// Check if there are non-generic webservices.
		if ( webservices.size() > 1 || webservices.get(webservice) != null ) {
			description = (String) webservices.get(webservice);
//			System.err.println("Webservice specific result: "+description);
		}
		if ( description == null ) {
			description = (String) webservices.get("%");
//			System.err.println("Webservice generic result: "+description);

		}
		if ( description == null ) {
			return defaultDescription;
		}
		
		return description;
	}
	
	public String getTranslation(String propertyName, String defaultDescription, String locale, String subLocale, String user, String webservice) {

		
		try {
			initializeCache(propertyName);
			String localeTranslation = getLocaleTranslation(propertyName, defaultDescription, locale, subLocale, user, webservice);
//			System.err.println("Getting description for: " + propertyName + ", locale: " + locale + ", sublocale: " + subLocale + ", user: " + user + ", webservice: " + webservice+" result: "+localeTranslation);
			return localeTranslation;

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
			
			datasource = "navajostore";
			if(getDescriptionMessage()!=null) {
				// found message, should always be the case
				Property datasourceProperty = getDescriptionMessage().getProperty("Datasource");
				if(datasourceProperty!=null) {
					datasource = datasourceProperty.getValue();
				}
			}

			sqlMap.setDatasource(datasource);
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
	}

	public void updateProperty(Navajo in, Property element, String locale) {
		String translation =  getTranslation(element.getName(), element.getDescription(), 
					locale, in.getHeader().getHeaderAttribute("sublocale"), 
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
		Iterator allProperties = properties.values().iterator();
		// Iterate over properties.
		while ( allProperties.hasNext() ) {
			Iterator allLocales = ( (HashMap) allProperties.next() ).values().iterator();
			
			while ( allLocales.hasNext()) {
				HashMap allSubLocales = (HashMap) allLocales.next();
				allSubLocales.remove(user);
			}
		}
	}

	public int getCacheSize() {
		return properties.size();
	}

	public void updateDescription(String locale, String name, String description, String context, String username) {
	}

	public void updatePropertyDescription(PropertyDescription pd) {

		SQLMap sql = createConnection();
		try {
			
			sql.setUpdate(updateDescription);
			sql.setParameter(pd.getDescription());
			sql.setParameter(pd.getLocale());
			sql.setParameter(pd.getSubLocale());
			sql.setParameter(pd.getContext());
			sql.setParameter(pd.getUsername());
			sql.setParameter(myAccess.rpcUser);
			sql.setParameter(new Integer(pd.getId()));
			sql.setDoUpdate(true);
			clearCache(pd.getName(), pd.getLocale(), pd.getUsername());
			
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}  finally {
			try {
				sql.store();
			} catch (MappableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setPropertyDescription(PropertyDescription pd) {
		updatePropertyDescription(pd);
	}
	
}
