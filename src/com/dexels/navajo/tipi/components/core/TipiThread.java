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
public class TipiThread extends Thread implements Comparable<TipiThread> {
	public static final String IDLE = "idle";
	public static final String BUSY = "busy";
	public static final String WAITING = "waiting";
	private final TipiThreadPool myPool;
	private final String myName;
	private final TipiContext myContext;

	public TipiThread(TipiContext context, String name, ThreadGroup group, TipiThreadPool tp) {
		super(group, name);
		setDaemon(true);
		myName = name;
		myPool = tp;
		myContext = context;
	}

	public void setThreadState(String state) {
		myPool.setThreadState(state);
	}

	public void run() {
		while (true) {
			try {
				try {
					while (true) {
						myPool.setThreadState(TipiThread.IDLE);
						TipiExecutable te = myPool.blockingGetExecutable();

//						final long t = System.currentTimeMillis();
						myPool.setThreadState(TipiThread.BUSY);
						TipiExecutable parentEvent = null;
						final Stack<TipiExecutable> s = myPool.getThreadStack(this);
						if (s != null && !s.isEmpty()) {
							parentEvent = s.peek();
						}
						myPool.pushCurrentEvent(te);
						myContext.debugLog("event", "Thread: " + myName + " got an executable. Performing now");
						try {
							myPool.getContext().threadStarted(Thread.currentThread());
							te.performAction(te.getEvent(), parentEvent, 0);
						} catch (Throwable ex) {
							if (!(ex instanceof TipiBreakException)) {
								ex.printStackTrace();
								te.dumpStack("Problem: " + ex.getMessage());
								System.err.println("Exception caught: "+ ex.getMessage());
							}
						} finally {
							TipiEventListener tel = myPool.getEventListener(te);
//							long t2 = System.currentTimeMillis();
							if (tel != null) {
								tel.eventFinished(te, null);
							}
							if (te.getComponent() != null) {
								te.getComponent().eventFinished(te, te);
							}
							myPool.removeEventListener(te.getEvent());
							
							if(te instanceof TipiEvent) {
								TipiEvent tev = (TipiEvent)te;
								if(tev.getAfterEvent()!=null) {
									tev.getAfterEvent().run();
								}
							}
						}
						Stack<TipiExecutable> ss = myPool.getThreadStack(this);
						ss.clear();
					}
				} finally {
					myPool.getContext().threadEnded(Thread.currentThread());

				}
			} catch (ThreadShutdownException t) {
				shutdown();
				return;
			} catch (Throwable t) {
				System.err.println("Caught uncaught exception in thread:");
				t.printStackTrace();
				// System.err.println("Reviving dying thread...");
			}
		}
	}

	private void shutdown() {
			synchronized (myPool) {
				myPool.notify();
			}
	}

	public int compareTo(TipiThread o) {
		return getName().compareTo(o.getName());
	}
}
