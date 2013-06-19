package com.dexels.navajo.entity.adapters;

import com.dexels.navajo.adapter.NavajoMap;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.UserException;

public class EntityMap extends NavajoMap {

	private EntityManager myManager;
	
	private String entity;
	private String method;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		super.load(access);
		myManager = EntityManager.getInstance();
	}

	public void setCall(boolean v) throws Exception {
		
		if ( entity != null && method != null ) {
			Operation o = myManager.getOperation(entity, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, DispatcherFactory.getInstance());
			seo.setMyEntityMap(this);
			seo.perform(this.outDoc, o);
		}
		
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
