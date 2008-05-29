package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;

public class TestQueuedAdapter implements Mappable, Queuable {

	private Access myAccess;
	
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		myAccess = access;
	}

	public void store() throws MappableException, UserException {
		try {
			RequestResponseQueueFactory.getInstance().send(this, 100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Access getAccess() {
		return myAccess;
	}

	public int getMaxRetries() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxRunningInstances() {
		return 1;
	}

	public Navajo getNavajo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Binary getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	public Binary getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRetries() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getWaitUntil() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void resetRetries() {
		// TODO Auto-generated method stub

	}

	public boolean send() {
		System.err.println("DOING SOME WORK IN TESTADAPTER (" + this.hashCode() + ") .........." );
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("....HE, HE, EINDELIJK KLAAR (" + this.hashCode() + ")");
		return true;
	}

	public void setMaxRetries(int r) {
		// TODO Auto-generated method stub

	}

	public void setMaxRunningInstances(int maxRunningInstances) {
		// TODO Auto-generated method stub

	}

	public void setQueuedSend(boolean b) {
		// TODO Auto-generated method stub

	}

	public void setWaitUntil(long w) {
		// TODO Auto-generated method stub

	}

}
