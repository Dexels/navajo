package com.dexels.navajo.server.test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.AfterWebServiceEmitter;
import com.dexels.navajo.server.ConditionData;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.NavajoConfigInterface;

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
		
		return null;
	}

	public Navajo handle(Navajo inMessage) throws FatalException {
		
		return null;
	}

	public Navajo handle(Navajo inMessage, Object userCertificate,
			ClientInfo clientInfo) throws FatalException {
		
		return null;
	}

	public String getApplicationId() {
		
		return null;
	}

	public void setUseAuthorisation(boolean b) {
		
		
	}

	public Set<Access> getAccessSet() {
		
		return null;
	}

	public String getServerId() {
		
		return null;
	}

	public Message[] checkConditions(ConditionData[] conditions,
			Navajo inMessage, Navajo outMessage) throws NavajoException {
		
		return null;
	}

	public String getThreadName(Access a) {
		
		return null;
	}

	public long getRequestCount() {
		
		return 0;
	}

	public long getUptime() {
		
		return 0;
	}

	public boolean isBusy() {
		
		return false;
	}

	public void doClearCache() {
		
		
	}

	public void doClearScriptCache() {
		
		
	}

	public String getEdition() {
		
		return null;
	}

	public String getProduct() {
		
		return null;
	}

	public int getRateWindowSize() {
		
		return 0;
	}

	public float getRequestRate() {
		
		return 0;
	}

	public Date getStartTime() {
		
		return null;
	}

	public File getTempDir() {
		
		return null;
	}

	public String getVendor() {
		
		return null;
	}

	public String getVersion() {
		
		return null;
	}

	public Navajo handle(Navajo inMessage, Object userCertificate) {
		
		return null;
	}

	public void setBroadcast(String message, int timeToLive,
			String recipientExpression) {
		
		
	}

	public Navajo removeInternalMessages(Navajo doc) {
		return doc;
	}

	public int getHealth(String resourceId) {
		
		return 0;
	}

	public int getWaitingTime(String resourceId) {
		
		return 0;
	}

	public boolean isAvailable(String resourceId) {
		
		return false;
	}

	public void setHealth(String resourceId, int h) {
		
		
	}

	public Navajo handle(Navajo inMessage, TmlRunnable initialRunnable, Object userCertificate) {
		return handle(inMessage,userCertificate);
	}

	public void finalizeService(Navajo inMessage, Access access, Navajo outMessage, String rpcName, String rpcUser,
			Throwable myException, String origThreadName, boolean scheduledWebservice, boolean afterWebServiceActivated) {
		
		
	}

	public Navajo generateErrorMessage(Access access, String message, int code, int level, Throwable t) throws FatalException {
		
		return null;
	}

	@Override
	public Navajo handle(Navajo inMessage, boolean skipAuth,
			AfterWebServiceEmitter emit) throws FatalException {
		return handle(inMessage,null);
	}

	@Override
	public void finalizeService(Navajo inMessage, Access access,
			Navajo outMessage, String rpcName, String rpcUser,
			Throwable myException, String origThreadName,
			boolean scheduledWebservice, boolean afterWebServiceActivated,
			AfterWebServiceEmitter emit) {
		
		
	}

	@Override
	public Navajo handle(Navajo inMessage, TmlRunnable initialRunnable,
			Object userCertificate, ClientInfo clientInfo)
			throws FatalException {
		return handle(inMessage,userCertificate);
	}

	@Override
	public void shutdown() {
		
		
	}

	@Override
	public Navajo handle(Navajo inMessage, boolean skipAuth,
			AfterWebServiceEmitter emit, ClientInfo clientInfo)
			throws FatalException {
		
		return null;
	}

	@Override
	public void setBundleCreator(BundleCreator bc) {
		
		
	}

	@Override
	public BundleCreator getBundleCreator() {
		
		return null;
	}

}