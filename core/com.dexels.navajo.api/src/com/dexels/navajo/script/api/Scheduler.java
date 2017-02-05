package com.dexels.navajo.script.api;


import java.io.IOException;

public interface Scheduler {
    /** Slow pool with only few threads. */
    public static final String SLOW_POOL = "slowPool";
    /** Default pool for all external requests */
    public static final String NORMAL_POOL = "externalNormalPool"; 
    /** External requests that less important. Should have little backlog */
    public static final String EXT_LOWPRIO_POOL = "externalLowPrioPool";
    /** Navajomap non-blocking but highprio calls*/
    public static final String PRIORITY_POOL = "priorityPool";
    /**  Calls made through Navajo tester */
    public static final String EXT_TESTER_POOL = "externalTesterPool";
    /**  High prio internal calls such as tasks. Should have no max backlog*/
    public static final String SYSTEM_POOL = "systemPool";
    /** Low prio internal calls (such as non-blocking navajomaps). Should have no max backlog but low prio */
    public static final String INTERNAL_LOWPRIO_POOL = "internalLowPriorityPool";
	
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
	 * TODO Huh? Why an IO exception?
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
