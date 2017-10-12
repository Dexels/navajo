package com.dexels.navajo.document.stream;

import java.util.List;

import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.replication.api.ReplicationMessage;

public class DataItem {

	private final ReplicationMessage msg;
	private final byte[] data;
	private final NavajoStreamEvent streamEvent;
	private final List<ReplicationMessage> msgList;
	
	public DataItem(ReplicationMessage msg) {
		this.msg = msg;
		this.data = null;
		this.streamEvent = null;
		this.msgList = null;
	}
	
	public DataItem(byte[] data) {
		this.msg = null;
		this.data = data;
		this.streamEvent = null;
		this.msgList = null;
	}

	public DataItem(NavajoStreamEvent event) {
		this.msg = null;
		this.data = null;
		this.streamEvent = event;
		this.msgList = null;
	}
	
	public DataItem(List<ReplicationMessage> msgList) {
		this.msg = null;
		this.data = null;
		this.streamEvent = null;
		this.msgList = msgList;
	}
	

	public ReplicationMessage message() {
		return this.msg;
	}

	public byte[] data() {
		return data;
	}

	public List<ReplicationMessage> msgList() {
		return msgList;
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

	public static DataItem of(List<ReplicationMessage> msgList) {
		return new DataItem(msgList);
	}
}
