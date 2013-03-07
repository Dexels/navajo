package com.dexels.navajo.sharedstore.map;

import java.io.Serializable;

import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;

public class TribalMapSignal extends SmokeSignal {

	public final static String PUT       = "put";
	public final static String CLEAR     = "clear";
	public final static String REMOVE    = "remove";
	public final static String CREATEMAP = "createmap";
	public final static String DELETEMAP = "deletemap";
	
	public TribalMapSignal(String sender, String key, Serializable value) {
		super(sender, key, value);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5427768817319357560L;

	@Override
	public void processMessage() {
		
		if ( iAmTheSender() ) {
			return;
		}
		
		if ( key.equals(CREATEMAP) ) {
			SharedTribalMap stm = new SharedTribalMap((String) value);
			SharedTribalMap.registerMapLocal(stm);
		} else if ( key.equals(DELETEMAP) ) {
			SharedTribalMap.deregisterMapLocal((String) value);
		} else if ( key.equals(PUT) ) {
			SharedTribalElement ste = (SharedTribalElement) value;
			SharedTribalMap stm = SharedTribalMap.getMap(ste.getId());
			if ( stm != null ) {
				stm.putLocal(ste.getKey(), ste.getValue());
				// TODO: make operation tribal safe (complete handshake) by sending message to originating host.
			}
		} else if ( key.equals(REMOVE)) {
			SharedTribalElement ste = (SharedTribalElement) value;
			SharedTribalMap stm = SharedTribalMap.getMap(ste.getId());
			if ( stm != null ) {
				stm.removeLocal(ste.getKey());
			}
		} else if ( key.equals(CLEAR)) {
			SharedTribalElement ste = (SharedTribalElement) value;
			SharedTribalMap stm = SharedTribalMap.getMap(ste.getId());
			if ( stm != null ) {
				stm.clearLocal();
			}
		}
	}
	
}
