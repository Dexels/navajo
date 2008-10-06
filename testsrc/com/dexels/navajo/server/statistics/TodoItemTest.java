package com.dexels.navajo.server.statistics;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TodoItemTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testInsert() throws Exception {
		Access a = new Access(13456789,1,1,"aap","noot","mies","1.1.1.1", "host", false, null);
		Navajo request = NavajoFactory.getInstance().createNavajo();
		Message req = NavajoFactory.getInstance().createMessage(request, "Request");
		request.addMessage(req);
		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message res = NavajoFactory.getInstance().createMessage(request, "Response");
		response.addMessage(res);
		a.setInDoc(request);
		a.setOutputDoc(response);
		TodoItem ti = new TodoItem(a,null);
		Access a2 = ti.getAccessObject();
		Assert.assertEquals(a.getAccessID(), a2.getAccessID());
	}
	
	public void testFinalize() throws Exception {
		Access a = new Access(912345678,1,1,"aap","noot","mies","1.1.1.1", "host", false, null);
		Navajo request = NavajoFactory.getInstance().createNavajo();
		Message req = NavajoFactory.getInstance().createMessage(request, "Request");
		request.addMessage(req);
		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message res = NavajoFactory.getInstance().createMessage(request, "Response");
		response.addMessage(res);
		a.setInDoc(request);
		a.setOutputDoc(response);
		TodoItem ti = new TodoItem(a,null);
		Access a2 = ti.getAccessObject();
		Assert.assertEquals(a.getAccessID(), a2.getAccessID());
		ti.finalize();
		Access a3 = ti.getAccessObject();
		Assert.assertNull(a3);
	}

}
