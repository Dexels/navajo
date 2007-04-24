package com.dexels.navajo.adapter.queue;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class QueuedAdapter extends Thread implements Mappable {

	public int runningTime;
	public String adapterName;
	
	private long startTime;
	private Queable handler;
	
	public QueuedAdapter(Queable h) {
		startTime = System.currentTimeMillis();
		handler = h;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public int getRunningTime() {
		return (int) ( System.currentTimeMillis() - startTime );
	}
	
	public String getAdapterName() {
		return handler.getClass().getName();
	}
	public Queable getAdapter() {
		return handler;
	}

	public void kill() {
		// TODO Auto-generated method stub
		
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}
}
