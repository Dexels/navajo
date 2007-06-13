package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.GenericThread;

/**
 * Implements an 'after' task occured trigger.
 * aftertask:<task id>
 * 
 * @author arjen
 *
 */
public class AfterTaskTrigger extends Trigger implements TaskListener {

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
		System.err.println("In after task trigger of " + t.getId() + ", checking if I am " + myDescription);
		// Is my parents' task-trigger single event? If it is so am I.
		setSingleEvent(t.getTrigger().isSingleEvent());
		if ( myDescription.equals(t.getId() ) ){
			spawnTask(request);
		}
	}

	public void beforeTask(Task t) {
		if ( myDescription.equals(t.getId() ) ){

		}
	}

}
