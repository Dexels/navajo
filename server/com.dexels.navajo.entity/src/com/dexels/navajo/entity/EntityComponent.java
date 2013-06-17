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
	
<<<<<<< HEAD
	public void setDispatcher(DispatcherInterface client) {
		this.dispatcher = client;
=======
	public void setDispatcher(DispatcherInterface dispatcher) {
		this.dispatcher = dispatcher;
>>>>>>> 3e705e20d7ee1896358b84c328a2291d05cdad86
	}

	public void clearDispatcher(DispatcherInterface dispatcher) {
		this.dispatcher = null;
	}

	
	public void activateComponent(Map<String,Object> parameters) throws Exception {
		serviceName = (String) parameters.get("service.name");
		Navajo in = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(in, serviceName, "", "", -1);
		in.addHeader(h);
<<<<<<< HEAD
		Navajo result = dispatcher.handle(in,true);
=======
		Navajo result = dispatcher.handle(in, true);
>>>>>>> 3e705e20d7ee1896358b84c328a2291d05cdad86
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
