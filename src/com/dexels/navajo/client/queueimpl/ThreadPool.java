package com.dexels.navajo.client.queueimpl;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ThreadPool {
        /**
         *
         */
        private final int THREAD_COUNT = 1;

        private final List myWaitingQueue = Collections.synchronizedList(new ArrayList());
 //       private final AwtContext myContext;
        private final Map myListenerMap = Collections.synchronizedMap(new HashMap());
        private List myThreadCollection = Collections.synchronizedList(new ArrayList());


        public ThreadPool() {
                ThreadGroup tg = new ThreadGroup("navajo");
                  for (int i = 0; i < THREAD_COUNT; i++) {
                        PoolThread p = new PoolThread("navajoThread_"+i,tg,this);
                        myThreadCollection.add(myThreadCollection);
                        p.start();
                }
        }

        private synchronized Runnable getExecutable() {
          if (myWaitingQueue.size() == 0) {
                return null;
          }
          Runnable te = (Runnable) myWaitingQueue.get(0);
          myWaitingQueue.remove(0);
          return te;
        }

//
//        public AwtContext getContext() {
//          return myContext;
//        }
//
        public synchronized Runnable blockingGetExecutable() {
          while (true) {
                Runnable te = getExecutable();
                if (te == null) {
                  try {
//                        myContext.serverStateChanged(ServerStateListener.STATE_IDLE);

//			myContext.threadEnded(Thread.currentThread());
                        wait();
                  }
                  catch (InterruptedException ex) {
                        System.err.println("interrupted");
                  }
                }
                else {
                  return te;
                }
          }
//		return null;
        }

        public synchronized void enqueueExecutable(Runnable te) {
           myWaitingQueue.add(te);
           notify();
         }


}
