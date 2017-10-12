package com.dexels.navajo.document.stream;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.replication.api.ReplicationMessage;

public class DataItem {

	private final ReplicationMessage msg;
	private final byte[] data;
	private final NavajoStreamEvent streamEvent;
	
	public DataItem(ReplicationMessage msg) {
		this.msg = msg;
		this.data = null;
		this.streamEvent = null;
	}
	
	public DataItem(byte[] data) {
		this.msg = null;
		this.data = data;
		this.streamEvent = null;
	}

	public DataItem(NavajoStreamEvent event) {
		this.msg = null;
		this.data = null;
		this.streamEvent = event;
	}

	public ReplicationMessage message() {
		return this.msg;
	}

	public byte[] data() {
		return data;
	}

	public static DataItem of(ReplicationMessage repl) {
		return new DataItem(repl);
	}
	public static DataItem of(byte[] data) {
		return new DataItem(data);
	}
	
	public static DataItem of(NavajoStreamEvent event) {
		return new DataItem(event);
	}

}
