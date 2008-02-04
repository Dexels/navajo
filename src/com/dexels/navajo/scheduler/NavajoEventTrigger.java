package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.NavajoHealthCheckEvent;
import com.dexels.navajo.server.GenericThread;

public class NavajoEventTrigger extends Trigger implements NavajoListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2166990121640292643L;

	public String myDescription;
	
	public NavajoEventTrigger(String description) {
		myDescription = description;
	}
	
	@Override
	public void activateTrigger() {
		if ( myDescription.equalsIgnoreCase("healthcheckevent") ) {
			NavajoEventRegistry.getInstance().addListener(NavajoHealthCheckEvent.class, this);
		}
	}

	@Override
	public String getDescription() {
		return myDescription;
	}

	@Override
	public boolean isSingleEvent() {
		return false;
	}

	@Override
	public void removeTrigger() {
		NavajoEventRegistry.getInstance().removeListener(NavajoHealthCheckEvent.class, this);
	}

	@Override
	public void setSingleEvent(boolean b) {
		
	}

	public Navajo perform() {
		 // Spawn task asynchronously. If there is no web service to run, invoke task synchronously.
		if ( getTask().getWebservice() != null ) {
			
			GenericThread taskThread = new GenericThread("task:" + getTask().getId() ) {

				public void run() {
					try {
						worker();
					} finally {
						finishThread();
					}
				}

				public final void worker() {
					getTask().run();
				}

				@Override
				public void terminate() {
					// Nothing special.
				}
			};
			taskThread.startThread(taskThread);
		} else {
			// Invoke task synchronously to support work flow before and after task trigger synchronously.
			try {
				getTask().run();
			} catch (Throwable t2) {}
		}
		return null;
	}

	public void onNavajoEvent(NavajoEvent ne) {
		perform();
	}

}
