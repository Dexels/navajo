package com.dexels.navajo.scheduler;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.scheduler.triggers.AfterWebserviceTrigger;
import com.dexels.navajo.scheduler.triggers.IllegalTrigger;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.TestNavajoConfig;
import com.dexels.navajo.server.enterprise.scheduler.TaskInterface;

public class TaskTest extends TestCase implements NavajoListener, TaskListener  {

	private boolean received = false;
	private boolean receivedAfterTask = false;
	private boolean receivedBeforeTask = false;
	private String sourceService = "navajo_ping";
	private String sourceTaskId;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
		receivedAfterTask = receivedBeforeTask = received = false;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTask() {
		Task t = new Task();
		Assert.assertNull(t.getTrigger());
	}

	@Test
	public void testTaskStringStringStringAccessStringNavajo() {
		// Case I
		boolean failure = false;
		try {
			Task t = new Task("webservice", null, "password", null, "", null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case II
		failure = false;
		try {
			Task t = new Task("webservice", "username", null, null, "", null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case III
		failure = false;
		try {
			Task t = new Task("webservice", null, null, null, "", null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case IV: Illegal trigger.
		failure = false;
		try {
			Task t = new Task(null, null, null, null, "", null);
		} catch (IllegalTrigger e) {
			failure = true;
		} catch (IllegalTask e) {
			//failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case V: Null trigger.
		failure = false;
		try {
			Task t = new Task(null, null, null, null, null, null);
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			failure = true;
		} catch (IllegalTask e) {
			//failure = true;
		}
		Assert.assertTrue(failure);
		
		// Case VI: Valid trigger.
		failure = true;
		try {
			Task t = new Task(null, null, null, null, "navajo:aap", null);
			failure = false;
		} catch (IllegalTrigger e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalTask e) {
			failure = true;
		}
		Assert.assertFalse(failure);
	}

	@Test
	public void testGetTrigger() throws Exception {
		Task t = new Task(null, null, null, null, "navajo:aap", null);
		Assert.assertNotNull(t.getTrigger());
		Assert.assertEquals(t.getTrigger().getClass(), AfterWebserviceTrigger.class);
	}

	@Test
	public void testSetTrigger() throws Exception {
		Task t = new Task(null, null, null, null, "navajo:aap", null);
		t.setTrigger("navajo:noot");
		Assert.assertEquals(t.getTriggerDescription(), "navajo:noot");
	}

	@Test
	public void testGetId() throws Exception  {
		Task t = new Task(null, null, null, null, "navajo:aap", null);
		t.setId("12345");
		Assert.assertEquals("12345", t.getId());
	}

	@Test
	public void testGetWebservice() throws Exception {
		Task t = new Task("webservice", "username", "password", null, "navajo:aap", null);
		Assert.assertEquals("webservice", t.getWebservice());
	}

	@Test
	public void testGetClientId() throws Exception  {
		Task t = new Task("webservice", "username", "password", null, "navajo:aap", null);
		t.setClientId("memyselfandi");
		Assert.assertEquals("memyselfandi", t.getClientId());
	}

	@Test
	public void testSetRemove() throws Exception {
		Task t = new Task("webservice", "username", "password", null, "navajo:aap", null);
		t.getTrigger().activateTrigger();
		Assert.assertTrue(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("aap"));
		t.setRemove(true, false);
		Assert.assertFalse(WebserviceListenerRegistry.getInstance().isRegisteredWebservice("aap"));
	}
	
	@Test
	public void testSetNavajo() throws Exception {
		Task t = new Task("webservice", "username", "password", null, "navajo:aap", null);
		t.setKeepRequestResponse(false);
		t.setClientId(null);
		t.setTaskDescription(null);
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(n, "noot", "testuser", "testpassword", -1);
		h.setHeaderAttribute("description", "mydescription");
		h.setHeaderAttribute("clientid", "myclientid");
		h.setHeaderAttribute("keeprequestresponse", "true");
		n.addHeader(h);
		t.setNavajo(n);
		Assert.assertEquals("mydescription", t.getTaskDescription());
		Assert.assertEquals("myclientid", t.getClientId());
		Assert.assertTrue(t.isKeepRequestResponse());
		
		Assert.assertEquals("noot", t.getWebservice());
		Assert.assertEquals("testuser",t.getUsername());
		Assert.assertEquals("testpassword", t.getPassword());
		
		Assert.assertNotNull(t.getNavajo());
		Assert.assertEquals(n.getHeader().getRPCName(), t.getNavajo().getHeader().getRPCName());
		// Check whether Navajo is cloned.
		Assert.assertNotSame(n.hashCode(), t.getNavajo().hashCode());
	}

	@Test
	public void testSetRequest() throws Exception {
		Task t = new Task("webservice", "username", "password", null, "navajo:aap", null);
		t.setKeepRequestResponse(false);
		t.setClientId(null);
		t.setTaskDescription(null);
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(n, "noot", "testuser", "testpassword", -1);
		Message m = NavajoFactory.getInstance().createMessage(n, "Apenoot");
		n.addMessage(m);
		t.setRequest(n);
		Assert.assertNotNull(t.getNavajo());
		Assert.assertNotNull(t.getNavajo().getMessage("Apenoot"));
		// Check whether Navajo is cloned.
		Assert.assertNotSame(n.hashCode(), t.getNavajo().hashCode());
	}

	@Test
	public void testRunWithWebserviceWithEmptyRequest() throws Exception {
		Task t = new Task("navajo_ping", "username", "password", null, "navajo:aap", null);
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		Assert.assertFalse(received);
		t.run();
		Assert.assertTrue(received);
		Navajo response = t.getResponse();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getMessage("ping"));
	}
	
	@Test
	public void testRunWithWebserviceWithFullRequestNavajo() throws Exception {
		Task t = new Task(null, null, null, null, "navajo:aap", null);
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(n, "navajo_ping", "testuser", "testpassword", -1);
		n.addHeader(h);
		// By setting request, the orginal service to be called SHOULD BE OVERWRITTEN.
		t.setNavajo(n);
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		Assert.assertFalse(received);
		t.run();
		Assert.assertTrue(received);
		Navajo response = t.getResponse();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getMessage("ping"));
	}
	
	@Test
	public void testRunWithWebserviceWithRequest() throws Exception {
		Task t = new Task("navajo_hello", "username", "password", null, "navajo:aap", null);
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(n, "navajo_test", "testuser", "testpassword", -1);
		Message m = NavajoFactory.getInstance().createMessage(n, "from");
		n.addMessage(m);
		Property p = NavajoFactory.getInstance().createProperty(n, "name", Property.STRING_PROPERTY, "harry", -1, "", Property.DIR_OUT);
		m.addProperty(p);
		n.addHeader(h);
		// By setting request, the orginal service to be called SHOULD NOT BE OVERWRITTEN.
		t.setRequest(n);
		
		sourceService = "navajo_hello";
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		Assert.assertFalse(received);
		t.run();
		Assert.assertTrue(received);
		Navajo response = t.getResponse();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getMessage("hello"));
		Assert.assertEquals("harry", response.getProperty("hello/world").getValue());
	}
	
	@Test
	public void testRunWithoutWebservice() throws Exception {
		Task t = new Task("navajo_ping", "username", "password", null, "navajo:aap", null);
		String taskid = "mytask-1";
		t.setId(taskid);
		sourceTaskId = taskid;
		TaskRunner.getInstance().addTaskListener(this);
		
		Assert.assertFalse(receivedAfterTask);
		Assert.assertFalse(receivedBeforeTask);
		t.run();
		Assert.assertTrue(receivedAfterTask);
		Assert.assertTrue(receivedBeforeTask);
	}

	@Test
	public void testGetTriggerDescription() throws Exception {
		Task t = new Task("navajo_ping", "username", "password", null, "navajo:aap", null);
		Assert.assertEquals("navajo:aap", t.getTriggerDescription());
	}

	@Test
	public void testSetProxy() throws Exception {
		Task t = new Task("navajo_test", "username", "password", null, "beforenavajo:navajo_ping", null);
		String taskid = "mytask-1";
		t.setId(taskid);
		sourceTaskId = taskid;
		t.setProxy(true);
		sourceService = "navajo_test";
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, this);
		Assert.assertFalse(received);
		t.run();
		Assert.assertTrue(received);
		Navajo response = t.getResponse();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getMessage("test"));
	}
	
	@Test
	public void testGetInstance() {
		TaskInterface ti = new Task().getInstance();
		Assert.assertNotNull(ti);
	}

	@Test
	public void testNeedsPersistence() throws Exception {
		Task t = new Task("navajo_test", "username", "password", null, "beforenavajo:navajo_ping", null);
		// Default behaviour:
		Assert.assertTrue(t.needsPersistence());
		t.setWorkflowDefinition("aap");
		t.setWorkflowId("323232");
		// Default behaviour for workflow tasks:
		Assert.assertFalse(t.needsPersistence());
		
	}

	@Test
	public void testSetPersisted() {
		Task t = new Task();
		t.setPersisted(true);
		Assert.assertTrue(t.isPersisted());
		t.setPersisted(false);
		Assert.assertFalse(t.isPersisted());
	}

	/*
	 * LISTENER METHODS:
	 */
	public void onNavajoEvent(NavajoEvent ne) {
		NavajoResponseEvent nre = (NavajoResponseEvent) ne;
		if ( nre.getAccess().getRpcName().equals(sourceService) ) {
			received = true;
		}
	}

	public void afterTask(Task t, Navajo response) {
		if ( t.getId().equals(sourceTaskId) ) {
			receivedAfterTask = true;
		}
	}

	public boolean beforeTask(Task t, Navajo request) {
		if ( t.getId().equals(sourceTaskId) ) {
			receivedBeforeTask = true;
		}
		return true;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
