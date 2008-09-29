package com.dexels.navajo.adapter.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.QueuableFailureEvent;
import com.dexels.navajo.events.types.QueuableFinishedEvent;
import com.dexels.navajo.events.types.TribeMemberDownEvent;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

/**
 * This module is responsible for processing so called 'queued' adapters. A queued adapter is put
 * into a Message store instead of being processed immediately. This module consumes the queued adapters
 * in this message store. The queued adapters in the store are processed by the Navajo instance that 
 * created the adapter, however in case of 'decease' the Chief Navajo member will take over the 'running'
 * queued adapters.
 * 
 * @author arjen
 *
 */
public class RequestResponseQueue extends GenericThread implements RequestResponseQueueMXBean, RequestResponseQueueInterface, NavajoListener {

	public boolean useQueue;
	public boolean emptyQueue = false;
	public boolean doingWork = false;
	public boolean queueOnly = false;
	protected MessageStore myStore = null;
	private static volatile RequestResponseQueue instance = null;
	private static Object semaphore = new Object();
	private static String id = "Queued adapters";
	public static final String VERSION = "$Id$";
	private int MAX_THREADS = 25;
	private long SLEEPING_TIME = 60000;
	private int MAX_RETRIES = 10;
	private static HashSet<Thread> runningThreads = new HashSet<Thread>();
	private static int workerNotificationCount = 0;
	
	// Exposed fields.
	public int currentThreads = 0;
	public HashMap<Class,Integer> currentThreadsByClass = new HashMap<Class,Integer>();
	
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
			HashSet<QueuedAdapter> s = q.myStore.getQueuedAdapters();
			Iterator<QueuedAdapter> i = s.iterator();
			while ( i.hasNext() ) {
				QueuedAdapter qa = i.next();
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
		return queuedAdapters.clone();
	}
	
	public QueuedAdapter [] getQueuedAdapters() {
		HashSet<QueuedAdapter> s = instance.myStore.getQueuedAdapters();
		queuedAdapters = new QueuedAdapter[s.size()];
		queuedAdapters = s.toArray(queuedAdapters);
		return queuedAdapters.clone();

	}
		
	public QueuedAdapter[] getDeadQueue() {
		HashSet<QueuedAdapter> s = instance.myStore.getDeadQueue();
		deadQueue = new QueuedAdapter[s.size()];
		deadQueue = s.toArray(deadQueue);
		return deadQueue.clone();
	}
	
	public void setUseQueue(boolean b) {
		useQueue = b;
	}
	
	public void setQueueOnly(boolean b) {
		queueOnly = b;
	}

	/**
	 * The instantiation method to be used by the Navajo Server.
	 * 
	 * @return
	 */
	public static RequestResponseQueue getInstance() {
		if ( instance != null ) {
			return instance;
		}
		synchronized (semaphore) {
			if ( instance != null ) {
				return instance;
			} else {
				instance = getInstance(new FileStore());
				try {
					JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
				} catch (Throwable t) {
					//t.printStackTrace(System.err);
				} 
				// Register for interesting events.
				NavajoEventRegistry.getInstance().addListener(TribeMemberDownEvent.class, instance);
				
				AuditLog.log(AuditLog.AUDIT_MESSAGE_QUEUEDADAPTERS, "Started message queue process $Id$");
			
				return instance;
			}
		}
	}
	
	/**
	 * Use this method ONLY directly for testing purposes.
	 * 
	 * @param ms, specify what MesssageStore to use.
	 * @return
	 */
	protected static RequestResponseQueue getInstance(MessageStore ms) {

		instance = new RequestResponseQueue();	
		instance.myStore = ms;
		
		instance.myId = id;
		instance.setSleepTime(5000);
		instance.startThread(instance);

		return instance;

	}
	
	public void send(Queuable handler, int maxretries) throws Exception {

		RequestResponseQueue rrq = RequestResponseQueue.getInstance();
		synchronized (instance) {
			rrq.myStore.putMessage(handler, false);	
//			System.err.println("REGISTERED: " + handler.hashCode() + ", ABOUT TO NOTIFY WAITING WORKER (" + this.status + "), mt = " +
//					handler.getMaxRunningInstances() );
			workerNotificationCount++;
//			System.err.println("workerNotificationCount = " + workerNotificationCount);
			instance.notifyAll();
		}
	}
	
	private int getThreadCountForHandler(final Queuable handler) {
		synchronized (currentThreadsByClass) {
			Integer c = currentThreadsByClass.get(handler.getClass());
			if ( c == null ) {
				return 0;
			} else {
				return c.intValue();
			}
		}
	}
	
	private int increaseThreadCountForHandler(final Queuable handler) {

		synchronized (currentThreadsByClass) {
			Integer c = currentThreadsByClass.get(handler.getClass());
			if ( c == null ) {
				c = Integer.valueOf(1);
			} else {
				c = Integer.valueOf(c.intValue() + 1);
			}
			currentThreadsByClass.put(handler.getClass(), c);
			return c.intValue();
		}
	}
	
	private int decreaseThreadCountForHandler(final Queuable handler) {
		synchronized (currentThreadsByClass) {
			Integer c = currentThreadsByClass.get(handler.getClass());
			if ( c == null ) {
				return 0;
			} 
			c = Integer.valueOf(c.intValue() - 1);
			if ( c.intValue() <= 0 ) {
				currentThreadsByClass.remove(handler.getClass());
			} else {
				currentThreadsByClass.put(handler.getClass(), c);
			}
			return c.intValue();
		}
	}
	
	private final void asyncwork(final Queuable handler) {
		
		increaseThreadCountForHandler(handler);
		QueuedAdapter t = new QueuedAdapter(handler) {

			public void run() {
				currentThreads++;
				
				//String qid = handler.getClass().getName()+"-"+System.currentTimeMillis();
				//JMXHelper.registerMXBean(handler, JMXHelper.QUEUED_ADAPTER_DOMAIN, qid);
				try {
					doingWork = true;
					if ( handler.send() && !emptyQueue) {
						NavajoEventRegistry.getInstance().publishAsynchronousEvent(new QueuableFinishedEvent(handler));
					} else {
						// Put stuff back in queue, unless queue is being emptied.
						//System.err.println("Could not process send() method, putting queued adapter back in queue... emptyqueue: " + emptyQueue);
						
						AuditLog.log(AuditLog.AUDIT_MESSAGE_QUEUEDADAPTERS, 
								QueuedAdapter.generateLogMessage(handler, "Could not process send() method, putting queued adapter back in queue") );
						if ( !emptyQueue ) {
							handler.setWaitUntil(System.currentTimeMillis() + SLEEPING_TIME );
							// Put queable in as failure if maxretries has past....
							int maxRetries = handler.getMaxRetries();
							if ( maxRetries == 0 ) {
								maxRetries = MAX_RETRIES;
							}
							if ( handler.getRetries() > maxRetries ) {
								sendHealthCheck(handler.getRetries(), maxRetries, Level.SEVERE, "Could not process queued adapter, service = " + 
									( handler.getAccess() != null ?	handler.getAccess().getRpcName() : "" ) + ", adapter = " + handler.getClass().getName());
							}
							myStore.putMessage(handler, ( handler.getRetries() > maxRetries ));
						}
						NavajoEventRegistry.getInstance().publishAsynchronousEvent(new QueuableFailureEvent(handler));
					}
				} finally {
					doingWork = false;
					currentThreads--;
					decreaseThreadCountForHandler(handler);
					synchronized (runningThreads ) {
						runningThreads.remove(this);
					}
					synchronized (instance) {
						//System.err.println("NOTIFYING WAITING WORKER....");
						instance.notifyAll();
					}
				}
				//System.err.println("....Finished asyncwork thread");
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
		AuditLog.log(AuditLog.AUDIT_MESSAGE_QUEUEDADAPTERS, "Killed");
	}
	
	public void worker() {
		
		do { 
			HashSet<Queuable> set = new HashSet<Queuable>();
			Queuable handler = null;

			if ( !queueOnly) {

				// Add all work in private Set.
				try {
					myStore.rewind();
					while ( ( handler = myStore.getNext()) != null && !emptyQueue ) {
						set.add(handler);	
					}
					if ( set.size() == 0 ) {
						workerNotificationCount = 0;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					AuditLog.log(AuditLog.AUDIT_MESSAGE_QUEUEDADAPTERS, 
							QueuedAdapter.generateLogMessage(handler, e1.getMessage() ), Level.SEVERE );
					e1.printStackTrace(System.err);
				}
				// Iterate over private set to do work.
				if ( !emptyQueue ) {
					Iterator<Queuable> iter = set.iterator();
					
					while ( iter.hasNext() && !emptyQueue ) {
						handler = iter.next();
						// Only handle MAX_THREADS at a time...
						while ( currentThreads >= MAX_THREADS ) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
							}
						}
						// Spawn worker thread, check limit on thread count for specific handler(!)
						if ( handler.getMaxRunningInstances() == -1 || getThreadCountForHandler(handler) < handler.getMaxRunningInstances() ) {
							asyncwork(handler);
						} else {
							// Put back in store(!)
							myStore.putMessage(handler, false);
						}
						workerNotificationCount--;
					}
				} else {
					workerNotificationCount = 0;
				}
			} else {
				workerNotificationCount = 0;
			}
		} while ( workerNotificationCount > 0);
		workerNotificationCount = 0;
	}

	public void inactive() {
		
		synchronized (instance) {
			
			if ( workerNotificationCount > 0 ) {
				return;
			}
			workerNotificationCount = 0;
			try {
				//System.err.println("RequestResponseQueue becoming inactive....workerNotificationCount " + workerNotificationCount);
				instance.wait(SLEEPING_TIME);
			} catch (Throwable e) {
				
			}
		}
		//System.err.println("WOKE UP!!!");
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

	public MessageStore getMyStore() {
		return myStore;
	}

	/**
	 * This method is called by Navajo Event Mechanism.
	 * 
	 */
	public void onNavajoEvent(NavajoEvent ne) {
		
		//System.err.println("In RequestResponseQueue, event arrived: " + ne.getClass() );
		if ( ne instanceof TribeMemberDownEvent ) {
			instance.getMyStore().takeOverPersistedAdapters( ((TribeMemberDownEvent) ne).getTm().getMemberName() );
		}
		
	}

}
