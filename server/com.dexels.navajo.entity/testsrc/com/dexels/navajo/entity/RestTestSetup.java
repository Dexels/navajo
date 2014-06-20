package com.dexels.navajo.entity;

import org.junit.After;
import org.junit.Before;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;



public abstract class RestTestSetup {

	//static Message entity;
	protected static EntityManager manager;
	
	protected Message person1; 
	protected Message person2;
	
	@Before
	public void setup() throws Exception {
		new DispatcherFactory(new Dispatcher());
		//DispatcherFactory.getInstance().setUseAuthorisation(false);
		setupEntities();
		insertPerson1();
	}

	private void setupEntities() throws Exception {
		manager = new EntityManager();
		manager.activate();
		
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo n = f.createNavajo();
		Message entity = f.createMessage(n, "Person");
		n.addMessage(entity);
		
		Property keyProp = f.createProperty(n, "PersonId", Property.STRING_PROPERTY, "", 0, "", "");
		keyProp.setKey("true,auto");
		entity.addProperty(keyProp);
				
		entity.addProperty(f.createProperty(n, "LastName", Property.STRING_PROPERTY, "", 0, "", ""));
		entity.addProperty(f.createProperty(n, "FirstName", Property.STRING_PROPERTY, "", 0, "", ""));
		entity.addProperty(f.createProperty(n, "DateOfBirth", Property.DATE_PROPERTY, "", 0, "", ""));
		
		entity.write(System.err);
		
		// Create Activity entity.

		Entity e1 = manager.addEntity(new Entity(entity, manager));
		e1.activate();
		
		
		definePersonOperations();
		
			
		n = f.createNavajo();
		person1 = f.createMessage(n, "Person");
		person1.addProperty(f.createProperty(n, "PersonId", Property.STRING_PROPERTY, "AA", 0, "", ""));
		person1.addProperty(f.createProperty(n, "LastName", Property.STRING_PROPERTY, "Jansen", 0, "", ""));
		
		person2 = f.createMessage(n, "Person");
		person2.addProperty(f.createProperty(n, "PersonId", Property.STRING_PROPERTY, "BB", 0, "", ""));
		person2.addProperty(f.createProperty(n, "LastName", Property.STRING_PROPERTY, "Pieters", 0, "", ""));

	}

	private void definePersonOperations() {
		OperationComponent oc = new OperationComponent();
		oc.setEntityName("Person");
		oc.setMethod("PUT");
		oc.setService("mongo/ProcessInsertMongo");
		manager.addOperation(oc);
		
		oc = new OperationComponent();
		oc.setEntityName("Person");
		oc.setMethod("GET");
		oc.setService("mongo/ProcessQueryMongo");
		manager.addOperation(oc);

		oc = new OperationComponent();
		oc.setEntityName("Person");
		oc.setMethod("POST");
		oc.setService("mongo/ProcessModifyMongo");
		manager.addOperation(oc);
		
		oc = new OperationComponent();
		oc.setEntityName("Person");
		oc.setMethod("DELETE");
		oc.setService("mongo/ProcessDeleteMongo");
		manager.addOperation(oc);
	}
	
	@After
	public void tearDown()  {
		
	}

	public void insertPerson1() throws Exception {

		Operation op = manager.getOperation("Person", "GET");
		ServiceEntityOperation seo = new ServiceEntityOperation(manager,
				DispatcherFactory.getInstance(), op);

		NavajoFactory f = NavajoFactory.getInstance();
		Navajo n = f.createNavajo();
		n.addMessage(person1);

		Navajo result = seo.perform(n);

	}

}
