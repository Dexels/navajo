package com.dexels.navajo.server.enterprise.tribe;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.dexels.navajo.document.Navajo;

public class DummyTribeManager implements TribeManagerInterface {

	public void terminate() {
	}

	public Navajo forward(Navajo in) throws Exception {
		throw new NotImplementedException();
	}

	public void broadcast(Navajo in) throws Exception {
		throw new NotImplementedException();
	}

}
