package com.dexels.navajo.client.async.legacy;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;

public interface AsyncRegistry {

	/**
	 * Deregister asyncrunner
	 * @param id String
	 */
	public void deRegisterAsyncRunner(String id);

	/**
	 * Performs an asynchronous serverside webservice call. These services will be polled by the Started ServerAsyncRunner
	 * and pass the status on to the given ServerAsyncListener. This method can be used for large time consuming webservices
	 * @param in Navajo
	 * @param method String
	 * @param listener ServerAsyncListener
	 * @param clientId String
	 * @param pollingInterval int
	 * @throws ClientException
	 */
	public void doServerAsyncSend(Navajo in, String method,
			ServerAsyncListener listener, String clientId, int pollingInterval)
			throws ClientException;

}