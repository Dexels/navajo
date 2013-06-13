package com.dexels.navajo.entity;

import java.util.Map;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.LocalClient;


public class EntityComponent extends Entity {

	LocalClient client;
	private String serviceName;
	
	public EntityComponent() {
		super();
	}
	
	public void setLocalClient(LocalClient client) {
		this.client = client;
	}

	public void clearLocalClient(LocalClient client) {
		this.client = null;
	}

	
	public void activateComponent(Map<String,Object> parameters) throws Exception {
		serviceName = (String) parameters.get("entity.name");
		Navajo in = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(in, serviceName, "", "", -1);
		in.addHeader(h);
		Navajo result = client.call(in);
		Message l = result.getAllMessages().iterator().next();
		setMessage(l);
		activate();
	}
	
	public void addDependency(Entity e) {
		//
	}
}
