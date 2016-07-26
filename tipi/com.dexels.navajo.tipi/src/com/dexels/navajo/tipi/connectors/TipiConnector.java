package com.dexels.navajo.tipi.connectors;

import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;

public interface TipiConnector {

	/**
	 * Which inputless-services can be used on this connector, it can return
	 * null, in which case it is unknown. If the set contains '*' any service
	 * may be used. (e.g. filter & rss)
	 * 
	 * @return
	 */
	public Set<String> getEntryPoints();

	public String getDefaultEntryPoint();

	/**
	 * Calls the service with input navajo n. Destination may be required,
	 * optional, or ignored, depending on the connector implementation.
	 */
	public Navajo doTransaction(Navajo n, String service, String destination)
			throws TipiBreakException, TipiException;


	   /**
     * Calls the service with input navajo n, and without a destination. It may
     * be refused by connectors that require a destination
     * 
     * @param n
     * @param service
     * @throws TipiBreakException
     * @throws TipiException
     */
    public Navajo doTransaction(Navajo n, String service, Integer retries) throws TipiBreakException, TipiException;
    

	/**
	 * Calls the service without destination or input navajo, it may be refused
	 * by connectors that require input messages or destinations
	 * 
	 * @param service
	 * @throws TipiBreakException
	 * @throws TipiException
	 */
	public Navajo doTransaction(String service) throws TipiBreakException,
			TipiException;

	/**
	 * Calls the default service (Init:<connectorId>). Not all connectors
	 * support such a service.
	 * 
	 * @throws TipiBreakException
	 * @throws TipiException
	 */
	public Navajo doTransaction() throws TipiBreakException, TipiException;

	public String getConnectorId();

	public void setContext(TipiContext tipiContext);

}
