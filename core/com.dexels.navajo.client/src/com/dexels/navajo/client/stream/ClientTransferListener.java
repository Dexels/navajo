package com.dexels.navajo.client.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTransferListener implements TransferDataListener {

	private static TransferDataListener instance;
	private long total = 0;
	private int connections = 0;
	private long duration = 0;
	private int failed = 0;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ClientTransferListener.class);
	
	public static TransferDataListener getInstance() {
		if(instance!=null) {
			return instance;
		}
		instance = new ClientTransferListener();
		return instance;
	}
	
	@Override
	public void transferCompleted(String label, long bytes, long duration) {
		total+=bytes;
		this.connections++;
		this.duration+= duration;
		report();
	}

	@Override
	public void transferFailed(String label, long duration) {
		this.connections++;
		this.failed  ++;
		this.duration+= duration;
		report();
	}
	
	private void report() {
		logger.debug("Loader: client network: "+total+" connections: "+connections+" failed: "+failed+" duration: "+duration+" millis since start: ");
	}
}
