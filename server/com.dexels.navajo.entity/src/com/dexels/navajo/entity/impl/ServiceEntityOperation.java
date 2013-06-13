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
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class ServiceEntityOperation implements EntityOperation {

	private EntityManager manager;
	private LocalClient client;
	
	
	@Override
	public Navajo perform(Navajo input, Operation o) throws EntityException {
		Entity e = manager.getEntity(o.getEntityName());
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
			return client.call(input);
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
