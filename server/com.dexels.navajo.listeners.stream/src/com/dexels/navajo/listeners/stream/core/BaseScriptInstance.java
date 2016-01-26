package com.dexels.navajo.listeners.stream.core;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

public abstract class BaseScriptInstance {

	private final Map<String,Set<Action2<Message,OutputSubscriber>>> messageRegistrations = new HashMap<>();
	private final Map<String,Set<Action2<Message,OutputSubscriber>>> elementRegistrations = new HashMap<>();
	private String outputPath;

//	public void run(Observable<NavajoStreamEvent> input, Subscriber<NavajoStreamEvent> output) {
//		output.onNext(navajoStarted());
//	}
//	
	
	public abstract void init();
	public abstract void complete(OutputSubscriber output);
	
	public void run(NavajoStreamEvent streamEvent, OutputSubscriber output) {
		switch (streamEvent.type()) {
		case MESSAGE:
			Set<Action2<Message,OutputSubscriber>> regs = messageRegistrations.get(streamEvent.path());
			if(regs!=null) {
				regs.forEach(e->e.call((Message)streamEvent.body(), output));
			}
			break;
		case ARRAY_ELEMENT:
			Set<Action2<Message,OutputSubscriber>> arrayRegs = messageRegistrations.get(streamEvent.path());
			if(arrayRegs!=null) {
				arrayRegs.forEach(e->e.call((Message)streamEvent.body(), output));
			}
			break;
		case NAVAJO_DONE:
			complete(output);
			break;
		default:
			break;
		}
	}

	public Observable<NavajoStreamEvent> run(final NavajoStreamEvent streamEvents) {
		return Observable.<NavajoStreamEvent>create(f->{
			run(streamEvents,new OutputSubscriber(f));
			f.onCompleted();
		});
	}
	
	public void registerArrayStream(String path,Action1<Message> onElement, Action0 done) {
		
	}

	public void onMessage(String path, Action2<Message,OutputSubscriber> cb) {
		Set<Action2<Message,OutputSubscriber>> callbacks = messageRegistrations.get(path);
		if(callbacks==null) {
			callbacks = new HashSet<>();
			messageRegistrations.put(path,callbacks);
		}
		callbacks.add(cb);
	}
	
	public void onElement(String path, Action2<Message,OutputSubscriber> cb) {
		Set<Action2<Message,OutputSubscriber>> callbacks = messageRegistrations.get(path);
		if(callbacks==null) {
			callbacks = new HashSet<>();
			elementRegistrations.put(path,callbacks);
		}
		callbacks.add(cb);
	}



	public void emitMessage(String path, Message message) {
//		output.onNext(new NavajoStreamEvent(path, NavajoEventTypes.MESSAGE_STARTED, message, Collections.emptyMap()));
//		output.onNext(new NavajoStreamEvent(path, NavajoEventTypes.MESSAGE, message, Collections.emptyMap()));
	}
	
	public void updateOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
}
