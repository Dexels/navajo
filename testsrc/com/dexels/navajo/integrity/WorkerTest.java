package com.dexels.navajo.integrity;

import java.util.Random;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.TestDispatcher;
import com.dexels.navajo.server.TestNavajoConfig;

import junit.framework.Assert;
import junit.framework.TestCase;

public class WorkerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
		Worker.getInstance().terminate();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGetInstance() {
		Worker w = Worker.getInstance();
		Assert.assertNotNull(w);
	}
	
	private Navajo createNiceNavajo(String requestId) {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "aap", "noot", "mies", -1);
		if ( requestId != null ) {
			h.setRequestId(requestId);
		}
		doc.addHeader(h);
		return doc;
	}
	
	public void testWriteFile() {
		Worker w = Worker.getInstance();
		Navajo doc = createNiceNavajo("345");
		w.writeFile("myId", doc);
		Assert.assertEquals(w.getFileCount(), 1);
		w.kill();
	}
	
	public void testSetResponse() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(2000);
		
		Navajo request = createNiceNavajo("345");
		
		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(response, "Result");
		response.addMessage(m);
		
		w.setResponse(request, response);
		
		Assert.assertTrue(w.workList.containsKey("345"));
		Navajo test = w.workList.get("345");
		Assert.assertEquals(test, response);
		Assert.assertEquals(w.getNotWrittenSize(), 1);
		// Wait for worker to kick in...
		Thread.sleep(3000);
		
		Assert.assertTrue(w.integrityCache.containsKey("345"));
		
		Assert.assertFalse(w.workList.containsKey("345"));
		Assert.assertEquals(w.getNotWrittenSize(), 0);
		
	}
	
	public void testSetResponseWithoutRequestId() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(2000);
		
		Navajo request = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(request, "aap", "noot", "mies", -1);
		request.addHeader(h);
		
		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(response, "Result");
		response.addMessage(m);
		
		w.setResponse(request, response);
		
		Assert.assertFalse(w.workList.containsKey("345"));
		Navajo test = w.workList.get("345");
		Assert.assertNull(test);
		
	}
	
	private void simulateIntegrityViolation(Worker w, boolean longWait) throws Exception {
		
		String rand = new Random(System.currentTimeMillis()).nextInt()+"";
		
		Navajo request = createNiceNavajo(rand);
		
		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(response, "Result");
		response.addMessage(m);
		
		w.setResponse(request, response);
		
		final Access a = new Access(1,1,1,"","","","","",false,null);
		if ( longWait ) {
			a.isFinished = false;
			new Thread() {
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					a.isFinished = true;
				}
			}.start();
		}
		
		Navajo n = w.getResponse(a, createNiceNavajo(rand));
		Assert.assertNotNull(n);
		Assert.assertNotNull(n.getMessage("Result"));
	}
	
	public void testGetResponse() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(500);
		simulateIntegrityViolation(w, false);
	}
	
	public void testViolationCount() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(500);
		simulateIntegrityViolation(w, false);
		Assert.assertEquals(w.getViolationCount(), 1);
		simulateIntegrityViolation(w, false);
		Assert.assertEquals(w.getViolationCount(), 2);
	}
	
	public void testWaitedForRunningRequest() throws Exception {
		
		Worker w = Worker.getInstance();
		w.setSleepTime(500);
		String rand = new Random(System.currentTimeMillis()).nextInt()+"";
		
		final Access a = new Access(1,1,1,"","","","","",false,null);
		a.isFinished = false;
		
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				a.isFinished = true;
			}
		}.start();
		
		Assert.assertFalse(w.waitedForRunningRequest(a, createNiceNavajo(rand), rand));
		Assert.assertTrue(w.waitedForRunningRequest(a, createNiceNavajo(rand), rand));
		// Sleep longer than 2000 millis.
		Thread.sleep(2200);
		Assert.assertFalse(w.waitedForRunningRequest(a, createNiceNavajo(rand), rand));
		
	}
	
	public void testGetResponseWithLongRunningRequest() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(500);
		simulateIntegrityViolation(w, true);
	}
	
	public void testGetResponseWithDifferentRequestId() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(500);
		
		String rand = new Random(System.currentTimeMillis()).nextInt()+"";

		Navajo request = createNiceNavajo(rand);

		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(response, "Result");
		response.addMessage(m);

		w.setResponse(request, response);
		
		final Access a = new Access(1,1,1,"","","","","",false,null);
		Navajo n = w.getResponse(a, createNiceNavajo(rand+"2"));
		Assert.assertNull(n);
		
	}
	
	public void testClearCache() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(500);
		
		String rand = new Random(System.currentTimeMillis()).nextInt()+"";
		
		Navajo request = createNiceNavajo(rand);

		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(response, "Result");
		response.addMessage(m);

		w.setResponse(request, response);
		w.clearCache();
		
		Assert.assertEquals(0, w.getFileCount());
		Assert.assertEquals(0, w.getCacheSize());
		
	}
	
	public void testTerminate() throws Exception {
		Worker w = Worker.getInstance();
		w.setSleepTime(500);
		
		String rand = new Random(System.currentTimeMillis()).nextInt()+"";
		
		Navajo request = createNiceNavajo(rand);

		Navajo response = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(response, "Result");
		response.addMessage(m);

		w.setResponse(request, response);
		w.terminate();
		
		Assert.assertEquals(0, w.getFileCount());
		Assert.assertEquals(0, w.getCacheSize());
		Assert.assertEquals(0, w.getWorkSize());
		Assert.assertEquals(0, w.notWrittenSize);
		
	}

}
