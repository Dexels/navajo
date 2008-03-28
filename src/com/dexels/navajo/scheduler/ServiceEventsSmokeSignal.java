package com.dexels.navajo.scheduler;

import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;
import com.dexels.navajo.workflow.WorkFlowManager;

public class ServiceEventsSmokeSignal extends SmokeSignal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 100304624980460138L;

	public static final String ADD_WEBSERVICE = "add_webservice";
	public static final String REMOVE_WEBSERVICE = "remove_webservice";
	public static final String AFTERWEBSERVICE_EVENT = "after_webservice_event";
	
	public ServiceEventsSmokeSignal(String sender, String key, Object value) {
		super(sender, key, value);
	}

	public void processMessage() {

		// Do not process this smokesignal if I am the sender.
		if ( !iAmTheSender() ) {
			
			ListenerStore ls = ListenerStore.getInstance();
			// Determine key.
			if ( getKey().equals(ADD_WEBSERVICE)) {
				String value = (String) getValue();
				ls.addRegisteredWebservice(value);
			} else if ( getKey().equals(REMOVE_WEBSERVICE) ) {
				String value = (String) getValue();
				ls.removeRegisteredWebservice(value);
			} else if ( getKey().equals(AFTERWEBSERVICE_EVENT)) {
				AfterWebserviceTrigger awt = (AfterWebserviceTrigger) getValue();
				if ( awt.getTask().getWorkflowId() == null || WorkFlowManager.getInstance().hasWorkflowId(awt.getTask().getWorkflowId()) ) {
					awt.perform();
				}
			} 
		}

	}

}
