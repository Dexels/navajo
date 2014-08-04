package com.dexels.navajo.entity;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.ComponentException;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;


public class EntityComponent extends Entity {

	public EntityComponent(Message msg, EntityManager m) {
		super(msg, m);
	}

	private String serviceName;
	

	public void activateComponent(Map<String, Object> parameters) throws ComponentException {
		serviceName = (String) parameters.get("service.name");
		Navajo in = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(in, serviceName, "", "", -1);
		in.addHeader(h);
		try {
			Navajo result = myClient.call(in);
			if (result.getMessage((String) parameters.get("entity.name")) == null) {
				throw new Exception("unable to find entity in provided script!");
			}

			Message l = result.getAllMessages().iterator().next();
			setMessage(l);

			em.addEntity(this);

			// Add HEAD operation.
			Operation head = new OperationComponent();
			head.setEntityName(getName());
			head.setMethod("HEAD");
			if (em == null) {
				return;
			}
			em.addOperation(head);
			// Add operations defined in entity.
			List<Operation> allOps = result.getAllOperations();
			for (Operation o : allOps) {
				if (o.getEntityName() == null || o.getEntityName().equals("")) {
					o.setEntityName(getName());
				}
				em.addOperation(o);
			}
		} catch (Exception e) {
			throw new ComponentException(e);
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

