package com.dexels.navajo.entity;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;



public class TestEntityPerson extends RestTestSetup{
	NavajoFactory factory = NavajoFactory.getInstance();;
	
	@Test
	public void testGetPerson1() throws IOException, EntityException {
		Navajo n = factory.createNavajo();
		Message input = factory.createMessage(n, "Person");
		input.addProperty(factory.createProperty(n, "PersonId", Property.STRING_PROPERTY, "AA", 0, "", ""));
		
		Operation op = manager.getOperation("Person", "GET");
		ServiceEntityOperation seo = new ServiceEntityOperation(manager, DispatcherFactory.getInstance(), op);
	
		Navajo result = seo.perform(n);
		System.out.println("result");
		
	}
}
 	