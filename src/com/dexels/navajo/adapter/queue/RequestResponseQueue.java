package com.dexels.navajo.adapter.queue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.queue.Queable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

public class RequestResponseQueue extends GenericThread implements RequestResponseQueueMXBean, RequestResponseQueueInterface {

	public boolean useQueue;
	public boolean emptyQueue = false;
	public boolean doingWork = false;
	public boolean queueOnly = false;
	protected MessageStore myStore = null;
	private static volatile RequestResponseQueue instance = null;
	private static Object semaphore = new Object();
	private static String id = "Queued adapters";
	public static String VERSION = "$Id$";
	private int MAX_THREADS = 25;
	private long SLEEPING_TIME = 60000;
	private int MAX_RETRIES = 10;
	private static HashSet runningThreads = new HashSet();
	
	// Exposed fields.
	public int currentThreads = 0;
	public QueuedAdapter [] runningAdapters;
	public QueuedAdapter [] queuedAdapters;
	public QueuedAdapter [] deadQueue;
	
	public String accessId;

	public RequestResponseQueue() {
		super(id);
	}
	
	public QueuedAdapter [] getRunningAdapters() {
		synchronized (runningThreads ) {
			runningAdapters = new QueuedAdapter[runningThreads.size()];
			runningAdapters = (QueuedAdapter []) runningThreads.toArray(runningAdapters);
			return runningAdapters;
		}
	}
	
	/**
	 * Get all queued adapters of specific web service (by accessId)
	 * 
	 * @return
	 */
	public QueuedAdapter [] getQueuedAdapters(String a) {
		ArrayList<QueuedAdapter> l = new ArrayList<QueuedAdapter>();
		RequestResponseQueue q = RequestResponseQueue.getInstance();
		if ( q != null ) {
			HashSet s = q.myStore.getQueuedAdapters();
			Iterator i = s.iterator();
			while ( i.hasNext() ) {
				QueuedAdapter qa = (QueuedAdapter) i.next();
				if ( qa.handler.getAccess().accessID.equals(a) ) {
					l.add(qa);
				}
			}
		}
		if ( l.size() == 0 ) {
			queuedAdapters = null;
			return null;
		}
		queuedAdapters = new QueuedAdapter[l.size()];
		queuedAdapters = l.toArray(queuedAdapters);
		return queuedAdapters;
	}
	
	public QueuedAdapter [] getQueuedAdapters() {
		HashSet<QueuedAdapter> s = instance.myStore.getQueuedAdapters();
		queuedAdapters = new QueuedAdapter[s.size()];
		queuedAdapters = s.toArray(queuedAdapters);
		return queuedAdapters;

	}
		
	public QueuedAdapter[] getDeadQueue() {
		HashSet<QueuedAdapter> s = instance.myStore.getDeadQueue();
		deadQueue = new QueuedAdapter[s.size()];
		deadQueue = s.toArray(deadQueue);
		return deadQueue;
	}
	
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
			instance.myStore = new FileStore();
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
	
	public void send(Queable handler, int maxretries) throws Exception {
		RequestResponseQueue rrq = RequestResponseQueue.getInstance();
		handler.persistBinaries();
		rrq.myStore.putMessage(handler, false);
	}
	
	public void asyncwork(final Queable handler) {
		QueuedAdapter t = new QueuedAdapter(handler) {

			public void run() {
				currentThreads++;
				//String qid = handler.getClass().getName()+"-"+System.currentTimeMillis();
				//JMXHelper.registerMXBean(handler, JMXHelper.QUEUED_ADAPTER_DOMAIN, qid);
				try {
					doingWork = true;
					if ( handler.send() && !emptyQueue) {
						// Make sure that request payload get garbage collected by removing ref.
						handler.removeBinaries();
					} else {
						// Put stuff back in queue, unless queue is being emptied.
						System.err.println("Could not process send() method, putting queued adapter back in queue... emptyqueue: " + emptyQueue);
						if ( !emptyQueue ) {
							handler.setWaitUntil(System.currentTimeMillis() + SLEEPING_TIME);
							// Put queable in as failure if maxretries has past....
							int maxRetries = handler.getMaxRetries();
							if ( maxRetries == 0 ) {
								maxRetries = MAX_RETRIES;
							}
							myStore.putMessage(handler, ( handler.getRetries() > maxRetries ));
						}
					}
				} finally {
					doingWork = false;
					currentThreads--;
					synchronized (runningThreads ) {
						runningThreads.remove(this);
					}
				}
				System.err.println("....Finished asyncwork thread");
			}
			
		};
		synchronized (runningThreads ) {
			runningThreads.add(t);
		}
		t.start();
	}
	
	private final static synchronized void resetInstance() {
		instance = null;
	}
	
	public void terminate() {
		try {
			JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
		} catch (Throwable e) {
		}
		resetInstance();
		AuditLog.log("Adapter Queue", "Killed");
	}
	
	public void worker() {

		HashSet set = new HashSet();
		Queable handler = null;
		if ( !queueOnly) {
			// Add all work in private Set.
			try {
				myStore.rewind();
				while ( ( handler = myStore.getNext()) != null && !emptyQueue ) {
					set.add(handler);	
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace(System.err);
			}
			// Iterate over private set to do work.
			if ( !emptyQueue ) {
				Iterator iter = set.iterator();
				while ( iter.hasNext() && !emptyQueue ) {
					handler = (Queable) iter.next();
					// Only handle MAX_THREADS at a time...
					while ( currentThreads >= MAX_THREADS ) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// Spawn worker thread.
					asyncwork(handler);	
				}
			}

		}
	}

	public void emptyQueue() {
		try {
			emptyQueue = true;
			instance.myStore.emptyQueue();
			while (doingWork) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		} finally {
			emptyQueue = false;
		}
	}
	
	public int getSize() {
		return instance.myStore.getSize();
	}
	
	public int getDeadQueueSize() {
		return instance.myStore.getDeadQueue().size();	
	}

	public void setMaxThreads(int t) {
		MAX_THREADS = t;
	}

	public void setSleepingTime(long l) {
		SLEEPING_TIME = l;
	}

	public int getMaxThreads() {
		return MAX_THREADS;
	}

	public long getSleepingTime() {
		return SLEEPING_TIME;
	}
	
	public int getCurrentThreads() {
		return currentThreads;
	}

	public String getVERSION() {
		return VERSION;
	}

}
