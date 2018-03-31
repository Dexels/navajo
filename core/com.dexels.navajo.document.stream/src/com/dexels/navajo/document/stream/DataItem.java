package com.dexels.navajo.document.stream;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public class DataItem {

	private final ImmutableMessage msg;
	private final ImmutableMessage stateMsg;
	private final byte[] data;
	private final NavajoStreamEvent streamEvent;
	private final Flowable<NavajoStreamEvent> eventStream;
	private final Flowable<ImmutableMessage> msgList;
	public final Type type;
	public enum Type {
		MESSAGE,
		SINGLEMESSAGE,
		EVENT,
		EVENTSTREAM,
		DATA,
		MSGSTREAM,
		EMPTY,
		ANY
	}
	
	private DataItem() {
		this.msg = null;
		this.data = null;
		this.streamEvent = null;
		this.msgList = null;
		this.type = Type.EMPTY;
		this.stateMsg = null;
		this.eventStream = null;
	}
	
	private DataItem(ImmutableMessage msg) {
		this.msg = msg;
		this.data = null;
		this.streamEvent = null;
		this.msgList = null;
		this.type = Type.MESSAGE;
		this.stateMsg = null;
		this.eventStream = null;
	}
	
	private DataItem(ImmutableMessage msg, ImmutableMessage stateMessage) {
		this.msg = msg;
		this.data = null;
		this.streamEvent = null;
		this.msgList = null;
		this.stateMsg = stateMessage;
		this.type = Type.MESSAGE;
		this.eventStream = null;
	}

	private DataItem(byte[] data) {
		this.msg = null;
		this.data = data;
		this.streamEvent = null;
		this.msgList = null;
		this.type = Type.DATA;
		this.stateMsg = null;
		this.eventStream = null;
	}

	private DataItem(NavajoStreamEvent event) {
		this.msg = null;
		this.data = null;
		this.streamEvent = event;
		this.msgList = null;
		this.type = Type.EVENT;
		this.stateMsg = null;
		this.eventStream = null;
	}
	
	// Ugly but can not make two identical constructors
	private DataItem(Flowable<ImmutableMessage> msgList, Flowable<NavajoStreamEvent> event) {
		this.msg = null;
		this.data = null;
		this.streamEvent = null;
		this.msgList = msgList;
		this.type = msgList == null? Type.EVENTSTREAM : Type.MSGSTREAM;
		this.stateMsg = null;
		this.eventStream = event;
		if(msgList!=null && event!=null) {
			throw new IllegalArgumentException("Can not both supply a msgList and an event stream");
		}
	}
	
	public Flowable<ImmutableMessage> messageStream() {
		if(this.msgList==null) {
			throw new NullPointerException("DataItem without messagestream, can't request messagestream of dataitem of type: "+this.type);
		}
		return this.msgList;
	}
	
	public Flowable<NavajoStreamEvent> eventStream() {
		if(this.eventStream==null) {
			throw new NullPointerException("DataItem without event stream, can't request eventstream of dataitem of type: "+this.type);
		}
		return this.eventStream;
	}

	public ImmutableMessage stateMessage() {
		return this.stateMsg == null ? ImmutableFactory.empty() : this.stateMsg;
	}
	public ImmutableMessage message() {
		if(this.msg==null) {
			throw new NullPointerException("DataItem without message, can't request message of dataitem of type: "+this.type);
		}
		return this.msg;
	}

	public NavajoStreamEvent event() {
		if(this.streamEvent==null) {
			NullPointerException nullPointerException = new NullPointerException("DataItem without streamEvent, can't request event of dataitem of type: "+this.type);
			nullPointerException.printStackTrace();
			throw nullPointerException;
		}

		return this.streamEvent;
	}
	
	public byte[] data() {
		if(this.data==null) {
			throw new NullPointerException("DataItem without data, can't request data of dataitem of type: "+this.type+" item: "+toString());
		}
		return data;
	}

	public Flowable<ImmutableMessage> msgList() {
		if(this.msgList==null) {
			throw new NullPointerException("DataItem without msgList, can't request msgList of dataitem of type: "+this.type+" item: "+toString());
		}
		return msgList;
	}

	public static DataItem empty() {
		return new DataItem();
	}

	public static DataItem of(ImmutableMessage repl) {
		return new DataItem(repl);
	}

	public static DataItem of(ImmutableMessage repl, ImmutableMessage stateMessage) {
		return new DataItem(repl,stateMessage);
	}

	
	public static DataItem of(byte[] data) {
		return new DataItem(data);
	}
	
	public static DataItem of(NavajoStreamEvent event) {
		return new DataItem(event);
	}

	public static DataItem ofEventStream(Flowable<NavajoStreamEvent> eventStream) {
		return new DataItem(null,eventStream);
	}

	public static DataItem of(Flowable<ImmutableMessage> msgList) {
		return new DataItem(msgList,null);
	}
	
	public String toString() {
		if(streamEvent!=null) {
			return "event: "+streamEvent;
		} else if(data!=null) {
			return "data: "+new String(data);
		} else if(msgList!=null) {
			return "msglist: "+msgList;		
		} else if(msg!=null){
			return msg.toFlatString(ImmutableFactory.getInstance());
		} else {
			return "dunno";
		}
	}
	
	public static Type parseType(String typeString) {
		switch (typeString) {
			case "msg":
			case "message":
				return Type.MESSAGE;
			case "singlemessage":
				return Type.SINGLEMESSAGE;
			case "event":
				return Type.EVENT;
			case "eventstream":
				return Type.EVENTSTREAM;
			case "msgstream":
				return Type.MSGSTREAM;
			case "binary":
				return Type.DATA;
			case "empty":
				return Type.EMPTY;
		default:
			throw new IllegalArgumentException("Can not parse typestring: "+typeString);
		}
	}
}
