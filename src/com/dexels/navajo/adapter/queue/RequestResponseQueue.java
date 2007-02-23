package com.dexels.navajo.adapter.queue;


import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class RequestResponseQueue extends GenericThread implements RequestResponseQueueMXBean {

	public boolean useQueue;
	public boolean queueOnly = false;
	private MessageStore myStore = new FileStore();
	private static RequestResponseQueue instance = null;
	private static Object semaphore = new Object();
	private static String id = "Queued adapters";
	
	public void setUseQueue(boolean b) {
		useQueue = b;
	}
	
	public void setQueueOnly(boolean b) {
		queueOnly = b;
	}

	public static RequestResponseQueue getInstance() {
		
		if (instance!=null) {
			return instance;
		}
		
		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			}
			
			instance = new RequestResponseQueue();	
			try {
				JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			} 
			instance.myId = id;
			instance.setSleepTime(5000);
			instance.startThread(instance);
			
			AuditLog.log("Adapter Message Queue", "Started message queue process $Id$");
			return instance;
		}
	}
	
	public static void send(Queable handler, int maxretries) throws Exception {
		
		RequestResponseQueue rrq = RequestResponseQueue.getInstance();
		rrq.myStore.putMessage(handler);
		
	}
	
	public void asyncwork(final Queable handler) {
		new Thread() {

			public void run() {
				System.err.println("Starting work....");
				String qid = handler.getClass().getName()+"-"+System.currentTimeMillis();
				JMXHelper.registerMXBean(handler, JMXHelper.QUEUED_ADAPTER_DOMAIN, qid);
				try {
					if ( handler.send() ) {
						System.err.println("Succesfully processed send() method");
						// Make sure that request payload get garbage collected by removing ref.
						Binary b = handler.getRequest();
						if ( b != null ) {
							b.removeRef();
						}
					} else {
						// Put stuff back in queue.
						System.err.println("Could not process send() method, putting queued adapter back in queue...");
						myStore.putMessage(handler);
					}
				} finally {
					try {
						JMXHelper.deregisterMXBean(JMXHelper.QUEUED_ADAPTER_DOMAIN, qid);
					} catch (Throwable e) {
					}
				}
				System.err.println("....Finished asyncwork thread");
			}
			
		}.start();
	}
	
	public void finalize() {
		try {
			JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
		} catch (Throwable e) {
		}
	}
	
	public void terminate() {
		instance = null;
		try {
			JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AuditLog.log("Adapter Queue", "Killed");
	}
	
	public void worker() {

		System.err.println("In worker!!");
		Queable handler = null;

		if ( !queueOnly) {
			while ( ( handler = myStore.getNext()) != null ) {
				asyncwork(handler);		
			}
		}
	}

	public void emptyQueue() {
		myStore.emptyQueue();
	}
	
	public int getSize() {
		return myStore.getSize();
	}
}
