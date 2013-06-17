package com.dexels.navajo.entity;

import java.util.Map;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.DispatcherInterface;


public class EntityComponent extends Entity {

	DispatcherInterface dispatcher;
	private String serviceName;
	
	public EntityComponent() {
		super();
	}
	
	public void setDispatcher(DispatcherInterface dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void clearDispatcher(DispatcherInterface dispatcher) {
		this.dispatcher = null;
	}

	
	public void activateComponent(Map<String,Object> parameters) throws Exception {
		serviceName = (String) parameters.get("service.name");
		Navajo in = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(in, serviceName, "", "", -1);
		in.addHeader(h);
		Navajo result = dispatcher.handle(in, true);
		Message l = result.getAllMessages().iterator().next();
		setMessage(l);
		activate();
	}
	
	public void deactivateComponent() throws Exception {
		deactivate();
	}
	
	public void addDependency(Entity e) throws Exception {
		addSuperEntity(e);
	}
}
