package com.dexels.navajo.tipi.components.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiThreadPool implements Serializable {
	private static final long serialVersionUID = -5572531351084195337L;
	private int poolSize = 1;
	private transient  ThreadGroup myGroup = null;
	// private final Set myThreadSet = Collections.synchronizedSet(new
	// HashSet());
	private final Map<TipiThread, Stack<TipiExecutable>> eventStackMap = new HashMap<TipiThread, Stack<TipiExecutable>>();
	private final List<TipiExecutable> myWaitingQueue = new ArrayList<TipiExecutable>();
	private final TipiContext myContext;
	private final Map<TipiExecutable, TipiEventListener> myListenerMap = Collections
			.synchronizedMap(new HashMap<TipiExecutable, TipiEventListener>());
	private List<TipiThread> myThreadCollection = Collections
			.synchronizedList(new ArrayList<TipiThread>());
	private final Map<TipiThread, String> threadStateMap = Collections
			.synchronizedMap(new TreeMap<TipiThread, String>());

	private boolean running = true;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiThreadPool.class);
	
	// private Thread myShutdownThread = null;

	// for use with echo

	public TipiThreadPool(TipiContext context, int initSize) {
		if(initSize > 0) {
			myGroup = new ThreadGroup("TipiThreadGroup");
		}
		this.poolSize = initSize;
		myContext = context;
		String maxThreads = context
				.getSystemProperty("com.dexels.navajo.tipi.maxthreads");
		if (maxThreads != null && !"".equals(maxThreads)) {
			int i = Integer.parseInt(maxThreads);
			this.poolSize = i;
		}
		for (int i = 0; i < this.poolSize; i++) {
			createThread("TipiThread #" + i);
		}
	}

	private final void createThread(String name) {
		TipiThread tt = new TipiThread(myContext, name, myGroup, this);
		myThreadCollection.add(tt);
		eventStackMap.put(tt, new Stack<TipiExecutable>());
		tt.start();
	}

	public TipiExecutable getExecutable() {
		synchronized (myWaitingQueue) {
			if (myWaitingQueue.size() == 0) {
				return null;
			}

			TipiExecutable te = myWaitingQueue.get(0);
			myWaitingQueue.remove(0);
			return te;
		}
	}

	public TipiContext getContext() {
		return myContext;
	}

	private synchronized boolean isRunning() {
		return running;
	}

	private synchronized void setRunning(boolean r) {
		running = r;
	}

	public void shutdown() {
		setRunning(false);
		for (Iterator<TipiThread> iter = myThreadCollection.iterator(); iter
				.hasNext();) {
			TipiThread item = iter.next();
			// item.shutdown();
			item.interrupt();

		}

	}

	public synchronized TipiExecutable blockingGetExecutable()
			throws ThreadShutdownException {
		while (isRunning()) {
			TipiExecutable te = getExecutable();
			if (te == null) {
				try {
					myContext.threadEnded(Thread.currentThread());
					wait();
				} catch (InterruptedException ex) {
				}
			} else {
				return te;
			}
		}
		throw new ThreadShutdownException();
	}

	public TipiEventListener getEventListener(TipiExecutable te) {
		return myListenerMap.get(te);
	}

	public void removeEventListener(TipiEvent te) {
		myListenerMap.remove(te);
	}

	public void enqueueExecutable(TipiExecutable exe) throws TipiException {
		if (poolSize == 0) {
			// For echo:
			try {
				if(exe.getEvent()!=null) {
					exe.getEvent().performAction(exe.getEvent(), exe.getEvent(), 0);
				} else {
					exe.performAction(null, null, 0);
				}
				exe.performAction(null, null, 0);
			} catch (TipiBreakException e) {
				logger.error("Error: ",e);
			} catch (TipiSuspendException e) {
			}
		} else {
			synchronized (myWaitingQueue) {
				myWaitingQueue.add(exe);
			}
			awaken();
		}
	}

	private synchronized void awaken() {
		notifyAll();
	}

	public void performAction(final TipiExecutable te,
			final TipiEventListener listener) throws TipiException,
			TipiBreakException {
		myListenerMap.put(te, listener);
		myContext.enqueueExecutable(te);
	}

	public void setThreadState(final String state) {
		final Thread t = Thread.currentThread();
		if (t instanceof TipiThread) {
			TipiThread tt = (TipiThread) t;
			threadStateMap.put(tt, state);
			myContext.fireThreadStateEvent(threadStateMap, tt, state,
					myWaitingQueue.size());

		}
	}

	public Stack<TipiExecutable> getThreadStack(Thread currentThread) {
		return eventStackMap.get(currentThread);
	}

	public void pushCurrentEvent(TipiExecutable te) {
		Thread t = Thread.currentThread();
		if (!myThreadCollection.contains(t)) {
			return;
		}
		Stack<TipiExecutable> eventStack = getThreadStack(t);
		eventStack.push(te);
	}

	public void dumpEventStacks() {
		for (TipiThread t : myThreadCollection) {
			Stack<TipiExecutable> te = eventStackMap.get(t);
			logger.info("Dumping: " + t.getName());
			for (TipiExecutable tipiExecutable : te) {
				logger.info("EXE: "
						+ tipiExecutable.getEvent().getEventName());
			}
			logger.info("End of dump: " + t.getName());
		}

	}

	public void waitForAllThreads() {
		// myShutdownThread = Thread.currentThread();
		if(myGroup==null) {
			return;
		}
		while (myGroup.activeCount() > 1) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					logger.error("Error: ",e);
				}
			}
		}
	}

}
