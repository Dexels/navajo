package com.dexels.navajo.entity.adapters;

import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.UserException;

public class EntityMap implements Mappable {

	private EntityManager myManager;
	
	private String entity;
	private String method;
	
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

	public Navajo getResult() throws Exception {
		if ( entity != null && method != null ) {
			Operation o =myManager.getOperation(entity, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, DispatcherFactory.getInstance());
			Navajo result = seo.perform(NavajoFactory.getInstance().createNavajo(), o);
			result.write(System.err);
		}
		return null;
	}
	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public void kill() {
	}

}
