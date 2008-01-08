package com.dexels.navajo.tribe;

import java.io.Serializable;

import com.dexels.navajo.scheduler.BeforeWebserviceTrigger;
import com.dexels.navajo.scheduler.ListenerStore;
import com.dexels.navajo.scheduler.WebserviceTrigger;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.workflow.WorkFlowManager;

public class SmokeSignal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1159623466118822005L;
	
	public String sender;
	public String object;
	public String key;
	public Object value;
	
	/**
	 * Objects and their keys (attributes).
	 * Objects basically defines a processing domain. Each processing domain uses keys for specific processing instructions.
	 */
	public static final String OBJECT_LISTENERS = "listeners";
	public static final String KEY_LISTENERS_ADD_WEBSERVICE = "add_webservice";
	public static final String KEY_LISTENERS_REMOVE_WEBSERVICE = "remove_webservice";
	public static final String KEY_LISTENERS_AFTERWEBSERVICE_EVENT = "after_webservice_event";
	public static final String KEY_LISTENERS_BEFOREWEBSERVICE_EVENT = "after_webservice_event";
	
	public static final String OBJECT_MEMBERSHIP = "membership";
	public static final String KEY_INTRODUCTION = "introduction";
	public static final String KEY_REMOVAL = "removal";
	
	public SmokeSignal(String sender, String object, String key, Object value) {
		this.object = object;
		this.key = key;
		this.value = value;
		this.sender = sender;
	}

	public String getObject() {
		return object;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}
	
	public String getSender() {
		return sender;
	}
	
	/**
	 * Process the message.
	 * NOTE: A SmokeSignal message may or may not, depending on its semantics, be processed on the original sender
	 * of the broadcast message.
	 * 
	 * @param m
	 */
	public void processMessage() {
		
		// Do not process message for object listeners where sender equals myself.
		if ( getObject().equals(OBJECT_LISTENERS) && !sender.equals(Dispatcher.getInstance().getNavajoConfig().getInstanceName())) {
			
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "SmokeSignal: PROCESS MESSAGE (" + getObject() + "/" + getKey() + "/" + getValue() + ")");
			
			ListenerStore ls = ListenerStore.getInstance();
			// Determine key.
			if ( getKey().equals(KEY_LISTENERS_ADD_WEBSERVICE)) {
				String value = (String) getValue();
				ls.addRegisteredWebservice(value);
			} else if ( getKey().equals(KEY_LISTENERS_REMOVE_WEBSERVICE) ) {
				String value = (String) getValue();
				ls.removeRegisteredWebservice(value);
			} else if ( getKey().equals(KEY_LISTENERS_AFTERWEBSERVICE_EVENT)) {
				WebserviceTrigger awt = (WebserviceTrigger) getValue();
				if ( awt.getTask().getWorkflowId() == null || WorkFlowManager.getInstance().hasWorkflowId(awt.getTask().getWorkflowId()) ) {
					awt.perform();
				}
			} else if ( getKey().equals(KEY_LISTENERS_BEFOREWEBSERVICE_EVENT)) {
				BeforeWebserviceTrigger bwt = (BeforeWebserviceTrigger) getValue();
				if ( bwt.getTask().getWorkflowId() == null || WorkFlowManager.getInstance().hasWorkflowId(bwt.getTask().getWorkflowId()) ) {
					bwt.perform();
				}
			} 
		} else if ( getObject().equals(OBJECT_MEMBERSHIP) ) {
			
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "SmokeSignal: PROCESS MESSAGE (" + getObject() + "/" + getKey() + "/" + getValue() + ")");
			
			if ( getKey().equals(KEY_INTRODUCTION)  ) {
				TribeMember tm = (TribeMember) getValue();
				TribeManager.getInstance().addTribeMember(tm);
			}
		}
			
	}
	
}
