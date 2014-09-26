package com.dexels.navajo.entity.adapters;

import com.dexels.navajo.adapter.NavajoMap;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.entity.util.EntityHelper;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.ConditionErrorException;


public class EntityMap extends NavajoMap {

	private EntityManager myManager;
	private String entity;
	private String method;
	private Entity myEntity;
	
	private boolean templateMerged = false;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		super.load(access);
		myManager = EntityManager.getInstance();
	}

	@Override
	public void setDoSend(String s, Navajo n) throws UserException, ConditionErrorException, SystemException {
		templateMerged = false;
		serviceCalled = false;
		serviceFinished = false;
		super.setDoSend(s, n);
	}
	
	public void setCall(boolean v) throws Exception {
		
		if ( entity != null && method != null ) {
			Operation o = myManager.getOperation(entity, method);
			ServiceEntityOperation seo = new ServiceEntityOperation(myManager, this, o);
			myEntity = seo.getMyEntity();
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
			s = "/" + entity + "/" + s; 
		}
		super.setPropertyName(s);
	}
	
	@Override 
	public synchronized void waitForResult() throws UserException {
		if (! templateMerged ) {
			super.waitForResult();
			if ( inDoc.getMessage(myEntity.getName()) != null ) {
				EntityHelper.mergeWithEntityTemplate(inDoc.getMessage(myEntity.getName()), myEntity.getMessage());
				templateMerged = true;
			}
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

	@Override
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Override
	public void kill() {
	}

}
