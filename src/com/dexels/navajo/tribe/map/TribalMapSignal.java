package com.dexels.navajo.tribe.map;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;

public class TribalMapSignal extends SmokeSignal {

	public final static String PUT = "put";
	public final static String CREATEMAP = "createmap";
	public final static String DELETEMAP = "deletemap";
	
	public TribalMapSignal(String sender, String key, Object value) {
		super(sender, key, value);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5427768817319357560L;

	@Override
	public void processMessage() {
		
		System.err.println(Dispatcher.getInstance().getNavajoConfig().getInstanceName() + ": in process message");
		
		if ( iAmTheSender() ) {
			return;
		}
		
		if ( key.equals(CREATEMAP) ) {
			System.err.println("IN CREATEMAP");
			SharedTribalMap stm = new SharedTribalMap((String) value);
			SharedTribalMap.registerMapLocal(stm);
		} else if ( key.equals(DELETEMAP) ) {
			System.err.println("IN DELETEMAP");
			SharedTribalMap.deregisterMapLocal((String) value);
		} else if ( key.equals(PUT)) {
			System.err.println("IN PUT");
			SharedTribalElement ste = (SharedTribalElement) value;
			SharedTribalMap stm = SharedTribalMap.getMap(ste.getId());
			if ( stm != null ) {
				stm.putLocal(ste.getKey(), ste.getValue());
			}
		} 
	}

}
