package com.dexels.navajo.entity.adapters;

import com.dexels.navajo.adapter.NavajoMap;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.ConditionErrorException;


public class EntityMap extends NavajoMap {

	private EntityManager myManager;
	private String entityName;
	private String method;
	private Entity myEntity;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		super.load(access);
		myManager = EntityManager.getInstance();
	}

	@Override
	public void setDoSend(String s, Navajo n) throws UserException, ConditionErrorException, SystemException {
		serviceCalled = false;
		serviceFinished = false;
		super.setDoSend(s, n);
	}
	
	public void setCall(boolean v) throws Exception {
		
		if ( entityName != null && method != null ) {
			Operation o = myManager.getOperation(entityName, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, this, o);
			Navajo request = prepareOutDoc();
			if ( request.getHeader() == null ) {
				Header h = NavajoFactory.getInstance().createHeader(request, myEntity.getName(), access.rpcUser, access.rpcName, -1);
				request.addHeader(h);
			}

			Navajo result = seo.perform(request);
			if ( result != null ) {
				this.serviceCalled = true;
				this.serviceFinished = true;
				this.inDoc = result;
			}
		}
		
	}
	
	@Override
	public void setPropertyName(String s) throws UserException {
		// Check if property name does not contain '/' character, i.e. it is a relative property name: append the entity name
		if ( s.indexOf("/") == -1 ) {
			s = "/" + myEntity.getMessageName() + "/" + s; 
		}
		super.setPropertyName(s);
	}
	
	@Override 
	public synchronized void waitForResult() throws UserException {
		super.waitForResult();
	}
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityname) throws UserException{
		this.entityName = entityname.replace("/", ".");;
		this.myEntity = myManager.getEntity(this.entityName);
		if (this.myEntity == null) {
		    throw new UserException(-1, "Entity " + entityName + " not found!");
		}
	}

	public String getMethod() {
		return method;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public void kill() {
	}

}
