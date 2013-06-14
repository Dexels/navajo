package com.dexels.navajo.entity;

import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operation;

public class OperationComponent implements Operation {

	private String method;
	private String service;
	private String entityName;
	private Message extraMessage;
	
	public void activateComponent(Map<String,Object> parameters) throws Exception {
		method = (String) parameters.get("operation.method");
		service = (String) parameters.get("operation.service");
		entityName = (String) parameters.get("entity.name");
		// How to fetch Message from this??
		if ( parameters.get("operation.extramessage") != null ) {
			extraMessage = (Message) parameters.get("operation.extramessage");
		}
		System.err.println("Activated operation " + method + " for entity: " + entityName);
	}
	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String getMethod() {
		return method;
	}

	@Override
	public void setService(String service) {
		this.service = service;
	}

	@Override
	public String getService() {
		return service;
	}

	@Override
	public void setEntityName(String entity) {
		this.entityName = entity;
	}

	@Override
	public String getEntityName() {
		return entityName;
	}

	@Override
	public void setExtraMessage(Message extra) {
		this.extraMessage = extra;
	}

	@Override
	public Message getExtraMessage() {
		return extraMessage;
	}

	@Override
	public Operation copy(Navajo n) {
		OperationComponent oc = new OperationComponent();
		oc.setMethod(this.method);
		oc.setService(this.service);
		oc.setEntityName(this.entityName);
		oc.setExtraMessage(this.extraMessage.copy());
		return oc;
	}

}
