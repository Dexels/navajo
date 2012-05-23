package com.dexels.navajo.adapter.sqlmap;

public interface ConnectionBrokerManagerMBean {

	/**
	 * Return a , separated list of defined datasource names.
	 * 
	 * @return
	 */
	public String getDefinedDatasources();
	
	/**
	 * Gets the url associated with the datasource.
	 * 
	 * @param datasource
	 * @return
	 */
	public String getDatasourceUrl(String datasource);
	
	/**
	 * Gets the username associated with the datasource.
	 * 
	 * @param datasource
	 * @return
	 */
	public String getDatasourceUsername(String datasource);
	
	/**
	 * Sets the health of ALL(!) datasources identified by given url.
	 * Note: multiple datasource with same url and different/same usernames could be defined.
	 * 
	 * @param url
	 * @param health
	 */
	public void setHealthByUrl(String url, int health);
	
	/**
	 * Returns the common(!) health of datasources identified by given url.
	 * 
	 * @param url
	 * @return
	 */
	public int getHealthByUrl(String url);
	
	/**
	 * Returns the cumulative number of active connections of all datasources identified by url.
	 * 
	 * @param url
	 * @return
	 */
	public int getActiveConnectionsByUrl(String url);
	
	/**
	 * Return the active connections by datasource.
	 * 
	 * @param datasource
	 * @return
	 */
	public int getActiveConnectionsByDatasource(String datasource);
	
	/**
	 * Return all active datasource connections.
	 * 
	 * @return
	 */
	public int getActiveConnections();
	
	/**
	 * Returns the maximum number of connections by datasource.
	 * 
	 * @param datasource
	 * @return
	 */
	public int getMaxConnectionsByDatasource(String datasource);
	
	/**
	 * Sets the maximum number of connections by datasource.
	 * 
	 * @param datasource
	 */
	public void setMaxConnectionsByDatasource(String datasource, int connections);
	
	/**
	 * Returns the number of all defined datasources.
	 * 
	 * @return
	 */
	public int getDatasourceCount();
	
}
