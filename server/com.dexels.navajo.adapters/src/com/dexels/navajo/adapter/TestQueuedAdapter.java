package com.dexels.navajo.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;

public class TestQueuedAdapter implements Mappable, Queuable {

	private static final long serialVersionUID = 2697339185493595216L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestQueuedAdapter.class);
	
	private Access myAccess;
	public static int maxRunningInstances = -1;
	
	public void kill() {

	}

	public void load(Access access) throws MappableException, UserException {
		myAccess = access;
	}

	public void store() throws MappableException, UserException {
		try {
			RequestResponseQueueFactory.getInstance().send(this, 100);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	public Access getAccess() {
		return myAccess;
	}

	public int getMaxRetries() {
		return 0;
	}

	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	public Navajo getNavajo() {
		return null;
	}

	public Binary getRequest() {
		return null;
	}

	public Binary getResponse() {
		return null;
	}

	public int getRetries() {
		return 0;
	}

	public long getWaitUntil() {
		return 0;
	}

	public void resetRetries() {

	}

	public boolean send() {
		logger.debug("DOING SOME WORK IN TESTADAPTER (" + this.hashCode()
				+ ") ..........");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("Error: ", e);
		}
		logger.debug("....HE, HE, EINDELIJK KLAAR (" + this.hashCode() + ")");
		return true;
	}

	public void setMaxRetries(int r) {

	}

	public void setMaxRunningInstances(int maxRunningInstances) {
		setStaticMaxRunningInstances(maxRunningInstances);
	}
	private static void setStaticMaxRunningInstances(int maxRunningInstances) {
		MailMap.maxRunningInstances = maxRunningInstances;
	}

	public void setQueuedSend(boolean b) {

	}

	public void setWaitUntil(long w) {

	}

}
