package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.tipi.*;

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
public class TipiThread extends Thread implements Comparable<TipiThread>{
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
						myPool.setThreadState(TipiThread.BUSY);

						myContext.debugLog("event", "Thread: " + myName + " got an executable. Performing now");
						try {
							myPool.getContext().threadStarted(Thread.currentThread());
							te.performAction(te.getEvent(), null, 0);
							// te.performAction(te.getEvent());
							// System.err.println("Thread: "+myName+"
							// finished");
						} catch (Throwable ex) {
							ex.printStackTrace();
						} finally {
							TipiEventListener tel = myPool.getEventListener(te);
							if (tel != null) {
								tel.eventFinished(te, null);
							}
							if (te.getComponent() != null) {
								te.getComponent().eventFinished(te, te);
							}
							myPool.removeEventListener(te.getEvent());
						}
					}
				} finally {
					myPool.getContext().threadEnded(Thread.currentThread());
				}
			} catch (ThreadShutdownException t) {
				System.err.println("Thread received a shutdown request. Farewell..");
				return;
			} catch (Throwable t) {
				System.err.println("Caught uncaught exception in thread:");
				t.printStackTrace();
				// System.err.println("Reviving dying thread...");
			}
		}
	}

	public int compareTo(TipiThread o) {
		return getName().compareTo(o.getName());
	}
}
