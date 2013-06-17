package com.dexels.navajo.entity;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.OperationComponent;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.server.DispatcherInterface;


public class TestOperation {

	static EntityManager manager;

	@BeforeClass
	public static void setup() {

		manager = new EntityManager();

		NavajoFactory f = NavajoFactory.getInstance();

		Navajo n = f.createNavajo();
		Message entity = f.createMessage(n, "MyEntity");
		entity.setExtends("navajo://Activity");
		n.addMessage(entity);

		Property keyProp = f.createProperty(n, "MatchId", Property.INTEGER_PROPERTY, "", 0, "", "");
		keyProp.setKey("true,auto");
		keyProp.setExtends("navajo://Activity/ActivityId");
		entity.addProperty(keyProp);

		Property keyProp2 = f.createProperty(n, "_id", Property.STRING_PROPERTY, "", 0, "", "");
		keyProp2.setKey("true,auto");
		entity.addProperty(keyProp2);

		entity.addProperty(f.createProperty(n, "CalendarDate", Property.DATE_PROPERTY, "", 0, "", ""));
		entity.addProperty(f.createProperty(n, "HomeOrganizationId", Property.STRING_PROPERTY, "", 0, "", ""));
		entity.addProperty(f.createProperty(n, "AwayOrganizationId", Property.STRING_PROPERTY, "", 0, "", ""));

		Property keyProp1_ALT = f.createProperty(n, "ExternalMatchId", Property.STRING_PROPERTY, "", 0, "", "");
		keyProp1_ALT.setKey("true,auto,id=ALT");
		entity.addProperty(keyProp1_ALT);
		Property keyProp2_ALT = f.createProperty(n, "SeasonId", Property.STRING_PROPERTY, "", 0, "", "");
		keyProp2_ALT.setKey("true,id=ALT");
		entity.addProperty(keyProp2_ALT);
		Property keyProp3_ALT = f.createProperty(n, "OrganizingDistrictId", Property.STRING_PROPERTY, "", 0, "", "");
		keyProp3_ALT.setKey("true,id=ALT");
		entity.addProperty(keyProp3_ALT);


		entity.write(System.err);

		// Create Activity entity.

		Message activity = f.createMessage(n, "Activity");
		activity.addProperty(f.createProperty(n, "ActivityType", Property.STRING_PROPERTY, "", 0, "", ""));
		Property keyPropAct = f.createProperty(n, "ActivityId", Property.INTEGER_PROPERTY, "", 0, "", "");
		keyPropAct.setKey("true,auto");
		activity.addProperty(keyPropAct);

		activity.write(System.err);

		manager.addEntity(new Entity(entity, manager));
		manager.addEntity(new Entity(activity, manager));

	}

	@Test
	public void testAddOperation() throws Exception {
		OperationComponent oc = new OperationComponent();
		oc.setEntityName("MyEntity");
		oc.setMethod("HEAD");
		oc.setService("aap/ProcessGetAap");
		manager.addOperation(oc);
		
		Operation o = manager.getOperation("MyEntity", "HEAD");
		
		Assert.assertNotNull(o);
		Assert.assertEquals("MyEntity", o.getEntityName());
		Assert.assertEquals("HEAD", o.getMethod());
		Assert.assertEquals("aap/ProcessGetAap", o.getService());
		
	}
	
	@Test
	public void testHEADOperation() throws Exception {
		
		OperationComponent oc = new OperationComponent();
		oc.setEntityName("MyEntity");
		oc.setMethod("HEAD");
		oc.setService("aap/ProcessGetAap");
		manager.addOperation(oc);
		
		ServiceEntityOperation seo = new ServiceEntityOperation(manager, (DispatcherInterface) null);
		
		Navajo result = seo.perform(null, oc);
		
		Assert.assertNotNull(result);
		
		Assert.assertNotNull(result.getMessage("MyEntity"));
		
		System.err.println("RESULT OF HEAD:");
		result.write(System.err);
		
		
	}
}
