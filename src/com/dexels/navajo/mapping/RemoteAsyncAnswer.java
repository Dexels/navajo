package com.dexels.navajo.mapping;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.tribe.TribeManager;

public class RemoteAsyncAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 489178532753193613L;

	
	private Object ownerOfRef = null;
	private Object hostNameOwnerOfRef = null;
	private boolean acknowledged = false;
	
	public RemoteAsyncAnswer(RemoteAsyncRequest q) {
		super(q);
		RemoteAsyncRequest rasr = (RemoteAsyncRequest) getMyRequest();
		String ref = rasr.getRef();
		if (AsyncStore.getInstance().getInstance(ref) != null ) {
			acknowledged = true;
			ownerOfRef = TribeManagerFactory.getInstance().getMyMembership().getAddress();
			hostNameOwnerOfRef = TribeManagerFactory.getInstance().getMyMembership().getMemberName();
		}
	}

	@Override
	public boolean acknowledged() {
		return acknowledged;
	}

	public Object getOwnerOfRef() {
		return ownerOfRef;
	}

	public Object getHostNameOwnerOfRef() {
		return hostNameOwnerOfRef;
	}

}
