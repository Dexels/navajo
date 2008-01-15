package com.dexels.navajo.tribe;

import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;
import com.dexels.navajo.util.AuditLog;

public class MembershipSmokeSignal extends SmokeSignal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4808643364226896337L;

	public static final String INTRODUCTION = "introduction";
	public static final String REMOVAL = "removal";
	
	public MembershipSmokeSignal(String sender, String key, Object value) {
		super(sender, key, value);
	}

	public void processMessage() {

		AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "MembershipSmokeSignal: PROCESS MESSAGE (" + getKey() + "/" + getValue() + ")");

		if ( getKey().equals(INTRODUCTION)  ) {
			TribeMember tm = (TribeMember) getValue();
			TribeManager.getInstance().addTribeMember(tm);
		}
	}

}
