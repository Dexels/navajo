/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;


import java.io.IOException;

public interface Scheduler {
    /** Slow pool with only few threads. */
    public static final String SLOW_POOL = "slowPool";
    /** Default pool for all external requests */
    public static final String NORMAL_POOL = "normalPool"; 
    /** Low prio internal calls (such as non-blocking navajomaps). Should have no max backlog but low prio */
    public static final String NAVAJOMAP_LOWPRIO_POOL = "navajomapLowPriorityPool";
    /** External requests that less important. Should have low max backlog */
    public static final String EXT_LOWPRIO_POOL = "externalLowPrioPool";
    /** Navajomap non-blocking but highprio calls*/
    public static final String NAVAJOMAP_PRIORITY_POOL = "navajomapPriorityPool";
    /**  Calls made through Navajo tester */
    public static final String TESTER_POOL = "testerPool";
    /**  High prio internal calls such as tasks. Should have no max backlog*/
    public static final String TASKS_POOL = "tasksPool";
   
	
	/**
	 * Return the timeout you want to give to this specific request.
	 * This only works when you are using the NIO connector (and not the APR connector)
	 * Return 0 if you want to leave the timeout setting on its default value.
	 * @param request
	 * @return
	 */
	public int getTimeout();
	
	/**
	 * The runner object is complete, so we can schedule it now.
	 * @param myRunner
	 * @throws IOException 
	 */
	public void submit(TmlRunnable myRunner, boolean priority) throws IOException;
	
    public void submit(TmlRunnable myRunner, String queueid);


	/**
	 * Run the Runnable synchronously
	 * @param myRunner
	 */
	public void run(TmlRunnable myRunner);
	
	/**
	 * Returns a string describing the status of this scheduling implementation
	 * @return
	 */
	public String getSchedulingStatus();

	
	public void shutdownScheduler();
	
	/**
	 * To schedule 'other' runnable that need to be executed, you can use this pool.
	 * Right now, I use it for the non-blocking HTTP client.
	 * @return
	 */
	public RequestQueue getDefaultQueue();
	
	public RequestQueue getQueue(String queueid);

}
