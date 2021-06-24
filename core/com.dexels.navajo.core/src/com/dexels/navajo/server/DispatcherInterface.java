/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.server.resource.ResourceManager;

public interface DispatcherInterface extends ResourceManager {

	public void generateServerReadyEvent();
	
	/*
	 * Methods to handle Navajo services.
	 */
	public Navajo handle(Navajo inMessage, String instance, Object userCertificate, ClientInfo clientInfo) throws FatalException;
	public Navajo handle(Navajo inMessage, String instance, boolean skipAuth, AfterWebServiceEmitter emit, ClientInfo clientInfo) throws FatalException;
	public Navajo handle(Navajo inMessage, boolean skipAuth) throws FatalException;
	public Navajo handle(Navajo inMessage) throws FatalException;
	public Navajo handle(Navajo inMessage, String instance, boolean skipAuth) throws FatalException;
	
	public void finalizeService(Navajo inMessage, Access access, String rpcName, String rpcUser,		Throwable myException, String origThreadName, boolean scheduledWebservice, boolean afterWebServiceActivated, AfterWebServiceEmitter emit);

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
	
	public int getRateWindowSize();
	public boolean isBusy();
	public long getRequestCount();
	public float getRequestRate();
	
	public void doClearCache();
	public void doClearScriptCache();
	

	public String getApplicationId();
	public String getApplicationGroup();

	public String getThreadName(Access a);

	
	  public  Navajo generateErrorMessage(Access access, String message, int code, int level, Throwable t) throws FatalException;
	
	  
	  public Navajo handleCallbackPointers(Navajo inMessage, String tenant);

}
