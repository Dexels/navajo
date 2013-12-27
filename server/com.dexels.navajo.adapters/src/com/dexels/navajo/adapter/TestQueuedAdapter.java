package com.dexels.navajo.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;

public class TestQueuedAdapter implements Mappable, Queuable {

	private static final long serialVersionUID = 2697339185493595216L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestQueuedAdapter.class);
	
	private Access myAccess;
	public static int maxRunningInstances = -1;
	
	@Override
	public void kill() {

	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		myAccess = access;
	}

	@Override
	public void store() throws MappableException, UserException {
		try {
			RequestResponseQueueFactory.getInstance().send(this, 100);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public Access getAccess() {
		return myAccess;
	}

	@Override
	public int getMaxRetries() {
		return 0;
	}

	@Override
	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	@Override
	public Navajo getNavajo() {
		return null;
	}

	@Override
	public Binary getRequest() {
		return null;
	}

	@Override
	public Binary getResponse() {
		return null;
	}

	@Override
	public int getRetries() {
		return 0;
	}

	@Override
	public long getWaitUntil() {
		return 0;
	}

	@Override
	public void resetRetries() {

	}

	@Override
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

	@Override
	public void setMaxRetries(int r) {

	}

	@Override
	public void setMaxRunningInstances(int maxRunningInstances) {
		setStaticMaxRunningInstances(maxRunningInstances);
	}
	private static void setStaticMaxRunningInstances(int maxRunningInstances) {
		MailMap.maxRunningInstances = maxRunningInstances;
	}

	@Override
	public void setQueuedSend(boolean b) {

	}

	@Override
	public void setWaitUntil(long w) {

	}

}
