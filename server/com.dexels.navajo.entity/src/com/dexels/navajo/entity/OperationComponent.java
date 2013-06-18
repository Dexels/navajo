package com.dexels.navajo.entity;

import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;

public class OperationComponent implements Operation {

	private String method;
	private String service;
	private String entityName;
	private Message extraMessage;
	
	public void activateComponent(Map<String,Object> parameters) throws Exception {
		method = (String) parameters.get("operation.method");
		service = (String) parameters.get("operation.service");
		entityName = (String) parameters.get("operation.entity");
		// How to fetch Message from this??
		if ( parameters.get("operation.extramessage") != null ) {
			extraMessage = (Message) parameters.get("operation.extramessage");
		}
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
		if ( extraMessage == null ) {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message m = NavajoFactory.getInstance().createMessage(n, "__OPERATION__");
			Property p = NavajoFactory.getInstance().createProperty(n, "Method", Property.STRING_PROPERTY, method, 0, "", "");
			Property p2 = NavajoFactory.getInstance().createProperty(n, "Entity", Property.STRING_PROPERTY, entityName, 0, "", "");
			m.addProperty(p);
			m.addProperty(p2);
			n.addMessage(m);
			return m;
			
		}
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
