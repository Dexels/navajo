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
		GenericThread taskThread = new GenericThread("task:" + getTask().getId()) {

			public void terminate () {
				// Nothing special.
			}
			
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
			setSingleEvent(t.getTrigger().isSingleEvent());
			// Clone myself before calling spawnTask...
			AfterTaskTrigger att = (AfterTaskTrigger) this.clone();
			att.spawnTask(request);
		}
	}

	public boolean beforeTask(Task t) {
		if ( myDescription.equals(t.getId() ) ){

		}
		return true;
	}

	public Navajo perform() {
		// TODO Auto-generated method stub
		return null;
	}

}
