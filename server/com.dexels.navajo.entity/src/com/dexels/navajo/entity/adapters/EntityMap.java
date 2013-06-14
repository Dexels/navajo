package com.dexels.navajo.entity.adapters;

import java.util.Set;

import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class EntityMap implements Mappable {

	private EntityManager myManager;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		myManager = EntityManager.getInstance();
	}

	@Override
	public void store() throws MappableException, UserException {
		Set<String> allEntities = myManager.getRegisteredEntities();
		for ( String e : allEntities ) {
			System.err.println("ENTITY: " + e);
		}
	}

	@Override
	public void kill() {
	}

}
