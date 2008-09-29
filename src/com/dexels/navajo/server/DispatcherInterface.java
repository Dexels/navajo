package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.dexels.navajo.document.Navajo;

public interface DispatcherInterface {

	/*
	 * Methods to handle Navajo services.
	 */
	public Navajo handle(Navajo inMessage, Object userCertificate, ClientInfo clientInfo) throws FatalException;
	public Navajo handle(Navajo inMessage, Object userCertificate) throws FatalException;
	public Navajo handle(Navajo inMessage, boolean skipAuth) throws FatalException;
	public Navajo handle(Navajo inMessage) throws FatalException;
	
	/**
	 * Create a temp file with the names prefix and suffix in the designated temp path.
	 * @param prefix
	 * @param suffix
	 * @return
	 * @throws IOException
	 */
	public File createTempFile(String prefix, String suffix) throws IOException;
	
	/**
	 * Get the designated temp path.
	 * @return
	 */
	public File getTempDir();

	/**
	 * Handle to the (singleton) NavajoConfig instance.
	 * 
	 * @return
	 */
	public NavajoConfigInterface getNavajoConfig();
	
	/**
	 * Controls whether or not to authorize (only used in development phase!).
	 * 
	 * @param b
	 */
	public void setUseAuthorisation(boolean b);
	
	/**
	 * Gets a set with all the currently active web service accesses.
	 * 
	 * @return
	 */
	public Set<Access> getAccessSet();
	
	public java.util.Date getStartTime();
	public int getRateWindowSize();
	public boolean isBusy();
	public long getUptime();
	public long getRequestCount();
	public float getRequestRate();
	
	public void doClearCache();
	public void doClearScriptCache();
	
	/**
	 * Broadcast a message to a recipient.
	 * 
	 * @param message
	 * @param timeToLive
	 * @param recipientExpression
	 */
	public void setBroadcast(String message, int timeToLive, String recipientExpression);
	
	public String getApplicationId();
	public String getServerId();
	public String getThreadName(Access a);
	
	/*
	 * Product information.
	 */
	public  String getVersion();
	public  String getVendor();
	public  String getProduct();
	public  String getEdition();
}
