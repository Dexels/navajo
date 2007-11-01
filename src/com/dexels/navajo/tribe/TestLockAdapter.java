package com.dexels.navajo.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class TestLockAdapter implements Mappable {

	public String parent;
	public String name;
	public String operation; // lock, release.
	
	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		
	
		
	}

	public void store() throws MappableException, UserException {
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperation(String operation) {
		this.operation = operation;
		if ( operation.equals("lock")) {
			TribeManager tm = TribeManager.getInstance();
			System.err.println("The chief: " + tm.getChiefName());
			System.err.println("GOT INSTANCE: " + tm);
			GetLockRequest glr = new GetLockRequest(parent, name, SharedStoreInterface.READ_WRITE_LOCK, false);
			Answer a = tm.askChief(glr);
			System.err.println("LOCK: Got answer: " + a.acknowledged());
		} else if ( operation.equals("release")) {
			TribeManager tm = TribeManager.getInstance();
			System.err.println("The chief: " + tm.getChiefName());
			System.err.println("GOT INSTANCE: " + tm);
			RemoveLockRequest glr = new RemoveLockRequest(parent, name);
			Answer a = tm.askChief(glr);
			System.err.println("RELEASE: Got answer: " + a.acknowledged());
		}
	}

}
