package com.dexels.navajo.document.stream;

import java.util.List;


import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public class DataItem {

	private final ImmutableMessage msg;
	private final ImmutableMessage stateMsg;
	private final byte[] data;
	private final List<ImmutableMessage> messageList;
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
		MSGLIST,
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
		this.messageList = null;
	}
	
	private DataItem(ImmutableMessage msg) {
		this.msg = msg;
		this.data = null;
		this.streamEvent = null;
		this.msgList = null;
		this.type = Type.MESSAGE;
		this.stateMsg = null;
		this.eventStream = null;
		this.messageList = null;
	}
	
	private DataItem(ImmutableMessage msg, ImmutableMessage stateMessage) {
		this.msg = msg;
		this.data = null;
		this.streamEvent = null;
		this.msgList = null;
		this.stateMsg = stateMessage;
		this.type = Type.MESSAGE;
		this.eventStream = null;
		this.messageList = null;
	}

	private DataItem(byte[] data) {
		this.msg = null;
		this.data = data;
		this.streamEvent = null;
		this.msgList = null;
		this.type = Type.DATA;
		this.stateMsg = null;
		this.eventStream = null;
		this.messageList = null;
	}
	
	private DataItem(byte[] data, ImmutableMessage stateMessage) {
		this.msg = null;
		this.data = data;
		this.streamEvent = null;
		this.msgList = null;
		this.type = Type.DATA;
		this.stateMsg = stateMessage;
		this.eventStream = null;
		this.messageList = null;
	}

	private DataItem(NavajoStreamEvent event) {
		this.msg = null;
		this.data = null;
		this.streamEvent = event;
		this.msgList = null;
		this.type = Type.EVENT;
		this.stateMsg = null;
		this.eventStream = null;
		this.messageList = null;
	}
	

	private DataItem(List<ImmutableMessage> list) {
		this.msg = null;
		this.data = null;
		this.streamEvent = null;
		this.msgList = null;
		this.type = Type.MSGLIST;
		this.stateMsg = null;
		this.eventStream = null;
		this.messageList = list;
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
		this.messageList = null;
	}
	
	public DataItem(NavajoStreamEvent streamEvent, ImmutableMessage state) {
		this.msg = null;
		this.data = null;
		this.streamEvent = streamEvent;
		this.msgList = null;
		this.type = Type.EVENT;
		this.stateMsg = state;
		this.eventStream = null;
		this.messageList = null;
	}

	public DataItem(Flowable<ImmutableMessage> msgList, ImmutableMessage state) {
		this.msg = null;
		this.data = null;
		this.streamEvent = null;
		this.msgList = msgList;
		this.type = Type.MSGLIST;
		this.stateMsg = state;
		this.eventStream = null;
		this.messageList = null;
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
	
	public List<ImmutableMessage> messageList() {
		if(this.messageList==null) {
			NullPointerException nullPointerException = new NullPointerException("DataItem without messageList, can't request event of dataitem of type: "+this.type);
			nullPointerException.printStackTrace();
			throw nullPointerException;
		}
		return this.messageList;
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


	// TODO support all types?
	public DataItem withStateMessage(ImmutableMessage state) {
		switch (type) {
		case DATA:
			return new DataItem(this.data,state);
		case ANY:
			throw new UnsupportedOperationException();
		case EMPTY:
			throw new UnsupportedOperationException();
		case EVENT:
			return new DataItem(this.streamEvent,state);
		case EVENTSTREAM:
			return new DataItem(this.data,state);
		case MESSAGE:
			return new DataItem(this.msg, state);
		case MSGLIST:
			return new DataItem(this.msgList, state);
		case MSGSTREAM:
			return new DataItem(this.messageStream(), state);
		case SINGLEMESSAGE:
			return new DataItem(this.msg, state);
		default:
			break;
		}
		throw new UnsupportedOperationException();
	}

	public static DataItem of(ImmutableMessage repl) {
		if(repl==null) {
			throw new NullPointerException("Can't instantiate data item with null message");
		}
		return new DataItem(repl);
	}
	
	public static DataItem of(List<ImmutableMessage> msgList) {
		if(msgList==null) {
			throw new NullPointerException("Can't instantiate data item with null messageList");
		}
		return new DataItem(msgList);
	}

	public static DataItem of(ImmutableMessage repl, ImmutableMessage stateMessage) {
		if(repl==null) {
			throw new NullPointerException("Can't instantiate data item with null message");
		}
		return new DataItem(repl,stateMessage);
	}

	
	public static DataItem of(byte[] data) {
		if(data==null) {
			throw new NullPointerException("Can't instantiate data item with null data");
		}
		return new DataItem(data);
	}
	
	public static DataItem of(NavajoStreamEvent event) {
		if(event==null) {
			throw new NullPointerException("Can't instantiate data item with null event");
		}
		return new DataItem(event);
	}

	public static DataItem ofEventStream(Flowable<NavajoStreamEvent> eventStream) {
		if(eventStream==null) {
			throw new NullPointerException("Can't instantiate data item with null eventStream");
		}
		return new DataItem(null,eventStream);
	}

	public static DataItem of(Flowable<ImmutableMessage> msgList) {
		if(msgList==null) {
			throw new NullPointerException("Can't instantiate data item with null msgList");
		}
		return new DataItem(msgList,(Flowable<NavajoStreamEvent>)null);
	}
//	private DataItem(Flowable<ImmutableMessage> msgList, Flowable<NavajoStreamEvent> event) {
	
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
