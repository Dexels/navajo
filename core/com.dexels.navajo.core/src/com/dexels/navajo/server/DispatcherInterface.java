package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.resource.ResourceManager;

public interface DispatcherInterface extends ResourceManager {

	/*
	 * Methods to handle Navajo services.
	 */
	public Navajo handle(Navajo inMessage, Object userCertificate, ClientInfo clientInfo) throws FatalException;
	public Navajo handle(Navajo inMessage, TmlRunnable initialRunnable, Object userCertificate, ClientInfo clientInfo) throws FatalException;
//	public Navajo handle(Navajo inMessage, boolean skipAuth) throws FatalException;
	public Navajo handle(Navajo inMessage, boolean skipAuth, AfterWebServiceEmitter emit) throws FatalException;
	public Navajo handle(Navajo inMessage, boolean skipAuth, AfterWebServiceEmitter emit, ClientInfo clientInfo) throws FatalException;
	public Navajo handle(Navajo inMessage, boolean skipAuth) throws FatalException;
	public Navajo handle(Navajo inMessage) throws FatalException;
	
	public void finalizeService(Navajo inMessage, Access access, Navajo outMessage, String rpcName, String rpcUser,		Throwable myException, String origThreadName, boolean scheduledWebservice, boolean afterWebServiceActivated, AfterWebServiceEmitter emit);


	/**
	 * Special method to remove internal Server messages.
	 * 
	 * @param doc
	 * @return
	 */
	public Navajo removeInternalMessages(Navajo doc);
	
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
	

	public String getApplicationId();
	public String getServerId();
	public String getThreadName(Access a);
	
	/*
	 * Product information.
	 */
//	public  String getVersion();
//	public  String getVendor();
//	public  String getProduct();
//	public  String getEdition();
	
	
	  public  Navajo generateErrorMessage(Access access, String message, int code, int level, Throwable t) throws FatalException;
	public void shutdown();
	
	//
	/**
	 * Associates a BundleCreator with this dispatcher object, might be called multiple times, might be null.
	 * @param bc
	 */
	public void setBundleCreator(BundleCreator bc);
	
	public BundleCreator getBundleCreator();
}
