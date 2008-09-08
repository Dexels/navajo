package com.dexels.navajo.adapter.queue;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.QueuableFailureEvent;
import com.dexels.navajo.events.types.QueuableFinishedEvent;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.enterprise.queue.Queuable;

import junit.framework.Assert;
import junit.framework.TestCase;

class TestQueuable implements Queuable {

	int maxRetries = 5;
	int maxRunningInstances = 1;
	int retries = 0;
	long waitUntil = 1000;
	
	boolean failure = false;
	
	String result = "NOK";
	
	public Access getAccess() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	public Navajo getNavajo() {
		return NavajoFactory.getInstance().createNavajo();
	}

	public Binary getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	public Binary getResponse() {
		return new Binary(result.getBytes());
	}

	public int getRetries() {
		return retries;
	}

	public long getWaitUntil() {
		return waitUntil;
	}

	public void resetRetries() {
		retries = 0;
	}

	public boolean send() {
		// Dummy Functionality.
		if ( !failure ) {
			result = "OK";
			System.err.println("PERFORMING QUEABLE ADAPTER");
			return true;
		} else {
			System.err.println("MIS(!!!)PERFORMING QUEABLE ADAPTER, retries " + retries);
			retries++;
			return false;
		}
	}

	public void setMaxRetries(int r) {
		maxRetries = r;
	}

	public void setMaxRunningInstances(int maxRunningInstances) {
		this.maxRunningInstances = maxRunningInstances;
	}

	public void setQueuedSend(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void setWaitUntil(long w) {
		waitUntil = w;
	}

	public boolean isFailure() {
		return failure;
	}

	public void setFailure(boolean failure) {
		this.failure = failure;
	}
	
}

public class RequestResponseQueueTest extends TestCase implements NavajoListener {

	final static RequestResponseQueue myQueue;
	
	static {
		myQueue = RequestResponseQueue.getInstance(new MemoryStore());
	}
	
	static Object semaphore = new Object();
	
	public RequestResponseQueueTest() {
		NavajoEventRegistry.getInstance().addListener(QueuableFinishedEvent.class, this);
		NavajoEventRegistry.getInstance().addListener(QueuableFailureEvent.class, this);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		myQueue.emptyQueue();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetInstance() {
		Assert.assertNotNull(myQueue);
	}
	
	public void testSend() throws Exception {
		TestQueuable q = new TestQueuable();
		Assert.assertEquals(new String(q.getResponse().getData()), "NOK");
		myQueue.send(q, 1);
		synchronized (semaphore) {
			semaphore.wait();
		}
		Assert.assertEquals(new String(q.getResponse().getData()), "OK");
	}
	
	public void testSendFailure() throws Exception {
		TestQueuable q = new TestQueuable();
		q.setFailure(true);
		q.setMaxRetries(1);
		Assert.assertEquals(new String(q.getResponse().getData()), "NOK");
		myQueue.send(q, 1);
		synchronized (semaphore) {
			semaphore.wait();
		}
		Assert.assertEquals(new String(q.getResponse().getData()), "NOK");
	}

	public void testEmptyQueue() throws Exception {
		myQueue.setSLEEPING_TIME(1000);
		TestQueuable q = new TestQueuable();
		Assert.assertEquals(new String(q.getResponse().getData()), "NOK");
		q.setFailure(true);
		q.setMaxRetries(5);
		q.setWaitUntil(2000);
		myQueue.send(q, 5);
		
		Thread.sleep(3000);
		myQueue.emptyQueue();
		Thread.sleep(2000);
		
		
		Assert.assertEquals(myQueue.getSize(), 0);
		
	}
	
	public void onNavajoEvent(NavajoEvent ne) {
		System.err.println("RECEIVED EVENT: " + ne);
		if ( ne instanceof QueuableFinishedEvent ) {
			synchronized (semaphore) {
				semaphore.notifyAll();
			}
		} else if ( ne instanceof QueuableFailureEvent ) {
			synchronized (semaphore) {
				semaphore.notifyAll();
			}
		}
	}
	
	public static void main(String [] args) throws Exception {
		RequestResponseQueueTest t = new RequestResponseQueueTest();
		
		t.testEmptyQueue();
	}
}
