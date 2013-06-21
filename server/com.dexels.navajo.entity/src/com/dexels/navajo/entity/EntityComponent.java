package com.dexels.navajo.entity;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
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
		// Add HEAD operation.
		Operation head = new OperationComponent();
		head.setEntityName(getName());
		head.setMethod("HEAD");
		em.addOperation(head);
		// Add operations defined in entity.
		List<Operation> allOps = result.getAllOperations();
		for ( Operation o : allOps ) {
			em.addOperation(o);
		}
	}
	
	public void deactivateComponent() throws Exception {
		deactivate();
	}
	
	public void addDependency(Entity e) throws Exception {
		addSuperEntity(e);
	}
	
	public static void main(String [] args) {
		
		String aap = "<tml><message name=\"noot\"></message></tml>";
		Navajo m = NavajoFactory.getInstance().createNavajo(new StringReader(aap));
		
		m.write(System.err);
		
	}
}
