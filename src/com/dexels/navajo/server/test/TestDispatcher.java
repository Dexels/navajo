package com.dexels.navajo.server.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.ClientInfo;
import com.dexels.navajo.server.ConditionData;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.FatalException;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class TestDispatcher implements DispatcherInterface {

	private NavajoConfigInterface myConfig;
	
	public TestDispatcher(NavajoConfigInterface injectedNavajoConfig) {
		myConfig = injectedNavajoConfig;
	}
	
	public File createTempFile(String prefix, String suffix) throws IOException {
		return File.createTempFile(prefix, suffix);
	}

	public void init() {
	}

	public NavajoConfigInterface getNavajoConfig() {
		return myConfig;
	}

	public Navajo handle(Navajo inMessage, boolean skipAuth)
			throws FatalException {
		// TODO Auto-generated method stub
		return null;
	}

	public Navajo handle(Navajo inMessage) throws FatalException {
		// TODO Auto-generated method stub
		return null;
	}

	public Navajo handle(Navajo inMessage, Object userCertificate,
			ClientInfo clientInfo) throws FatalException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getApplicationId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setUseAuthorisation(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public Set<Access> getAccessSet() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Message[] checkConditions(ConditionData[] conditions,
			Navajo inMessage, Navajo outMessage) throws NavajoException,
			SystemException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getThreadName(Access a) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getRequestCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getUptime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isBusy() {
		// TODO Auto-generated method stub
		return false;
	}

	public void doClearCache() {
		// TODO Auto-generated method stub
		
	}

	public void doClearScriptCache() {
		// TODO Auto-generated method stub
		
	}

	public String getEdition() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRateWindowSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getRequestRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Date getStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public File getTempDir() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVendor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	public Navajo handle(Navajo inMessage, Object userCertificate)
			throws FatalException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setBroadcast(String message, int timeToLive,
			String recipientExpression) {
		// TODO Auto-generated method stub
		
	}

}