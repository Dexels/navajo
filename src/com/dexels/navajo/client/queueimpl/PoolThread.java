package com.dexels.navajo.client.queueimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PoolThread extends Thread {
//	private final int THREAD_COUNT = 1;
        //	private final List myWaitingQueue = Collections.synchronizedList(new ArrayList());
//        private final AwtContext myContext;
        //	private final Map myListenerMap = Collections.synchronizedMap(new HashMap());
        //	private List myThreadCollection = Collections.synchronizedList(new ArrayList());
        private final ThreadPool myPool;
        public PoolThread(String name, ThreadGroup group, ThreadPool tp) {
                super(group, name);
//                myContext = context;
                myPool = tp;
        }
        public void run() {
                while (true) {
                        try {
                                        Runnable te = myPool.blockingGetExecutable();
//                                        myContext.serverStateChanged(ServerStateListener.STATE_CLIENT);
                                        te.run();
//                                        myContext.serverStateChanged(ServerStateListener.STATE_CLIENT);
                        } catch (Throwable t) {
                                System.err.println("Caught uncaught exception in thread:");
                                t.printStackTrace();
                                System.err.println("Reviving dying thread...");
                        }
                }
        }
}
