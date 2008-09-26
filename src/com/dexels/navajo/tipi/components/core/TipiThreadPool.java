package com.dexels.navajo.tipi.components.core;

import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

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
public class TipiThreadPool {
	private int poolSize = 1;
	private final ThreadGroup myGroup = new ThreadGroup("TipiThreadGroup");
	// private final Set myThreadSet = Collections.synchronizedSet(new
	// HashSet());
	private final Map<TipiThread, Stack<TipiExecutable>> eventStackMap = new HashMap<TipiThread, Stack<TipiExecutable>>();
	private final List<TipiExecutable> myWaitingQueue = new ArrayList<TipiExecutable>();
	private final TipiContext myContext;
	private final Map<TipiExecutable, TipiEventListener> myListenerMap = Collections
			.synchronizedMap(new HashMap<TipiExecutable, TipiEventListener>());
	private List<TipiThread> myThreadCollection = Collections.synchronizedList(new ArrayList<TipiThread>());
	private final Map<TipiThread, String> threadStateMap = Collections.synchronizedMap(new TreeMap<TipiThread, String>());

	private boolean running = true;

	// for use with echo

	public TipiThreadPool(TipiContext context, int initSize) {
		this.poolSize = initSize;
		myContext = context;
		String maxThreads = context.getSystemProperty("com.dexels.navajo.tipi.maxthreads");
		// System.err.println("MAX THREADS::::::: "+maxThreads+"\n\n"+"
		// poolsize: "+initSize);

		// HACK!
		// this.poolSize = 1;

		if (maxThreads != null && !"".equals(maxThreads)) {
			int i = Integer.parseInt(maxThreads);
			// System.err.println("Using maxthread: " + i);
			this.poolSize = i;
		}
		for (int i = 0; i < this.poolSize; i++) {
			createThread("TipiThread #" + i);
		}
		// System.err.println("Available threads: "+myThreadCollection.size());
	}

	private final void createThread(String name) {
		TipiThread tt = new TipiThread(myContext, name, myGroup, this);
		// System.err.println("Creating thread: "+name);
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
		for (Iterator<TipiThread> iter = myThreadCollection.iterator(); iter.hasNext();) {
			TipiThread item = iter.next();
			// item.shutdown();
			item.interrupt();

		}

	}

	public synchronized TipiExecutable blockingGetExecutable() throws ThreadShutdownException {
		while (isRunning()) {
			TipiExecutable te = getExecutable();
			if (te == null) {
				try {
					myContext.threadEnded(Thread.currentThread());
					wait();
				} catch (InterruptedException ex) {
					// System.err.println("interrupted");
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
				exe.getEvent().performAction(exe.getEvent(), exe.getEvent(), 0);
				exe.performAction(null, null, 0);
			} catch (TipiBreakException e) {
				e.printStackTrace();
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

	public void performAction(final TipiExecutable te, final TipiEventListener listener) throws TipiException, TipiBreakException {
		myListenerMap.put(te, listener);
		myContext.enqueueExecutable(te);
	}

	public void setThreadState(final String state) {
		final Thread t = Thread.currentThread();
		if (t instanceof TipiThread) {
			TipiThread tt = (TipiThread) t;
			threadStateMap.put(tt, state);
			myContext.fireThreadStateEvent(threadStateMap, tt, state, myWaitingQueue.size());

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
			System.err.println("Dumping: " + t.getName());
			for (TipiExecutable tipiExecutable : te) {
				System.err.println("EXE: " + tipiExecutable.getEvent().getEventName());

			}
			System.err.println("End of dump: " + t.getName());
		}

	}

}
