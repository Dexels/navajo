package com.dexels.navajo.scheduler;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.GenericThread;

/**
 * Implements an 'after' task occured trigger.
 * aftertask:<task id>
 * 
 * @author arjen
 *
 */
public class AfterTaskTrigger extends Trigger implements TaskListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1389461783321528112L;
	
	private String myDescription;
	private boolean isSingleEvent = true;
	
	public AfterTaskTrigger(String s) {
		myDescription = s;
	}
	
	public String getDescription() {
		return Trigger.AFTER_TASK_TRIGGER + ":" + myDescription;
	}

	public boolean isSingleEvent() {
		return isSingleEvent;
	}

	public void removeTrigger() {
		TaskRunner.getInstance().removeTaskListener(this);
	}

	public void setSingleEvent(boolean b) {
		isSingleEvent = b;
	}

	public void activateTrigger() {
		TaskRunner.getInstance().addTaskListener(this);
	}

	protected final void spawnTask(final Navajo request) {
		// Spawn thread.
		System.err.println("Spawning task " + getTask().getId());
		GenericThread taskThread = new GenericThread("task:" + getTask().getId()) {

			public void run() {
				try {
					worker();
				} finally {
					finishThread();
				}
			}

			public final void worker() {
				// Note; setNavajo() method overrides the webservice name(!!)
				// Store it first en set it back.
				String webservice = getTask().getWebservice();
				getTask().setNavajo(request);
				getTask().setWebservice(webservice);
				getTask().run();
			}
		};
		taskThread.startThread(taskThread);
	}
	
	public void afterTask(Task t, Navajo request) {
		
		if ( myDescription.equals(t.getId() ) ){
			System.err.println(t.getTriggerDescription() + ": in after task trigger of " + t.getId() + ", checking if I am " + myDescription);
			setSingleEvent(t.getTrigger().isSingleEvent());
			spawnTask(request);
		}
	}

	public void beforeTask(Task t) {
		if ( myDescription.equals(t.getId() ) ){

		}
	}

}
