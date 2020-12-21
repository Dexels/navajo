/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.server.AfterWebServiceEmitter;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;

public class TestDispatcher implements DispatcherInterface {

	private NavajoConfigInterface myConfig;
	
	public TestDispatcher(NavajoConfigInterface injectedNavajoConfig) {
		myConfig = injectedNavajoConfig;
	}
	
	@Override
	public File createTempFile(String prefix, String suffix) throws IOException {
		return File.createTempFile(prefix, suffix);
	}

//	public void init() {
//	}

	@Override
	public NavajoConfigInterface getNavajoConfig() {
		return myConfig;
	}

	@Override
	public Navajo handle(Navajo inMessage, boolean skipAuth)
			throws FatalException {
		
		return null;
	}

	@Override
	public Navajo handle(Navajo inMessage) throws FatalException {
		
		return null;
	}

	@Override
	public Navajo handle(Navajo inMessage, String instance, Object userCertificate,
			ClientInfo clientInfo) throws FatalException {
		
		return null;
	}

	@Override
	public String getApplicationId() {
		
		return null;
	}
	
	@Override
	public String getApplicationGroup() {
		
		return null;
	}

	@Override
	public void setUseAuthorisation(boolean b) {
		
		
	}

	@Override
	public Set<Access> getAccessSet() {
		
		return null;
	}

	@Override
	public String getThreadName(Access a) {
		
		return null;
	}

	@Override
	public long getRequestCount() {
		
		return 0;
	}


	@Override
	public boolean isBusy() {
		
		return false;
	}

	@Override
	public void doClearCache() {
		
		
	}

	@Override
	public void doClearScriptCache() {
		
		
	}

	@Override
	public int getRateWindowSize() {
		
		return 0;
	}

	@Override
	public float getRequestRate() {
		
		return 0;
	}

	@Override
	public File getTempDir() {
		return null;
	}


	@Override
	public int getHealth(String resourceId) {
		return 0;
	}

	@Override
	public int getWaitingTime(String resourceId) {
		return 0;
	}

	@Override
	public boolean isAvailable(String resourceId) {
		return false;
	}

	@Override
	public void setHealth(String resourceId, int h) {
	}

	@Override
	public Navajo generateErrorMessage(Access access, String message, int code, int level, Throwable t) throws FatalException {
		
		return null;
	}

	@Override
	public void finalizeService(Navajo inMessage, Access access,
			String rpcName, String rpcUser,
			Throwable myException, String origThreadName,
			boolean scheduledWebservice, boolean afterWebServiceActivated,
			AfterWebServiceEmitter emit) {
		
		
	}

	@Override
	public Navajo handle(Navajo inMessage, String instance, boolean skipAuth,
			AfterWebServiceEmitter emit, ClientInfo clientInfo)
			throws FatalException {
		
		return null;
	}

	@Override
	public Navajo handleCallbackPointers(Navajo inMessage, String tenant) {
		return null;
	}

	@Override
	public Navajo handle(Navajo inMessage, String instance, boolean skipAuth)
			throws FatalException {
		return null;
	}
	
	@Override
	public void generateServerReadyEvent() {
		
	}

}