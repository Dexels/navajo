package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;

public class TestQueuedAdapter implements Mappable, Queuable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2697339185493595216L;
	
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
			e.printStackTrace();
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
		System.err.println("DOING SOME WORK IN TESTADAPTER (" + this.hashCode() + ") .........." );
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("....HE, HE, EINDELIJK KLAAR (" + this.hashCode() + ")");
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
