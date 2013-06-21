package com.dexels.navajo.entity.impl;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.EntityOperation;
import com.dexels.navajo.entity.Key;
import com.dexels.navajo.entity.adapters.EntityMap;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.DispatcherInterface;

public class ServiceEntityOperation implements EntityOperation {

	private EntityManager manager;
	private DispatcherInterface dispatcher;
	private LocalClient client;
	private EntityMap myEntityMap;
	
	public ServiceEntityOperation() {

	}

	public ServiceEntityOperation(EntityManager m, DispatcherInterface c) {
		this.manager = m;
		this.dispatcher = c;
	}


	public ServiceEntityOperation(EntityManager m, LocalClient c) {
		this.manager = m;
		this.client = c;
	}

	public EntityMap getMyEntityMap() {
		return myEntityMap;
	}

	public void setMyEntityMap(EntityMap myEntityMap) {
		this.myEntityMap = myEntityMap;
	}

	@Override
	public Navajo perform(Navajo input, Operation o) throws EntityException {
		Entity e = manager.getEntity(o.getEntityName());
		if ( e == null ) {
			throw new EntityException("Could not find entity: " + o.getEntityName());
		}
		if(o.getMethod().equals(Operation.HEAD)) {
			Navajo out = NavajoFactory.getInstance().createNavajo();
			out.addMessage(e.getMessage().copy(out));
			return out;
		}
		if(o.getMethod().equals(Operation.GET)) {
			Message inputEntity = input.getMessage(e.getName());
			Key k = e.getKey(inputEntity.getAllProperties());
			if(k==null) {
				throw new EntityException("Input is invalid");
			}
			return callEntityService(input, o);
		}
		if(o.getMethod().equals(Operation.DELETE)) {
			Message inputEntity = input.getMessage(e.getName());
			Key k = e.getKey(inputEntity.getAllProperties());
			if(k==null) {
				throw new EntityException("Input is invalid");
			}
			return callEntityService(input, o);
		}

		if(o.getMethod().equals(Operation.PUT)) {
			boolean isOk = e.isInsertable(input);
			if(isOk) {
				return callEntityService(input, o);
			} else {
				throw new EntityException("Error calling entity service. Input incorrect. ");
			}
		}
		if(o.getMethod().equals(Operation.POST)) {
			boolean isOk = e.isUpdateable(input);
			if(isOk) {
				return callEntityService(input, o);
			} else {
				throw new EntityException("Error calling entity service. Input incorrect. ");
			}
		}		
		return null;
	}


	protected Navajo callEntityService(Navajo input, Operation o)
			throws EntityException {
		appendServiceName(input, o.getService());
		final Message extraMessage = o.getExtraMessage();
		if(extraMessage!=null) {
			input.addMessage(extraMessage);
		}
		try {
			if ( myEntityMap != null ) {
				myEntityMap.setOutDoc(input);
				try {
					myEntityMap.setDoSend(o.getService());
				} catch (Exception e) {
					throw new EntityException(e.getMessage(), e);
				} 
				return null;
			}
			if ( dispatcher != null ) {
				return dispatcher.handle(input, true);
			} else
				if ( client != null ) {
					return client.call(input);
				} else {
					throw new EntityException("No Dispatcher or LocalClient present");
				}
		} catch (FatalException e1) {
			throw new EntityException("Error calling entity service: ",e1);
		}
	}


	protected void appendServiceName(Navajo input, String rpcName) {
		Header h = input.getHeader();
		if(h==null) {
			h = NavajoFactory.getInstance().createHeader(input, rpcName, "", "", -1);
			input.addHeader(h);
		} else {
			h.setRPCName(rpcName);
		}
	}

}
