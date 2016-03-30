package com.dexels.navajo.adapters.swift;

import com.dexels.navajo.resource.swift.OpenstackStorageFactory;
import com.dexels.navajo.resource.swift.OpenstackStore;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class Swift implements Mappable {

	private String name = null;
	private String tenant = null;
	@Override
	public void load(Access access) throws MappableException, UserException {
		tenant = access.getTenant();
	}

	@Override
	public void store() throws MappableException, UserException {
		OpenstackStore os = OpenstackStorageFactory.getInstance().getOpenstackStore(name, tenant);


	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

}
