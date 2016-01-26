package com.dexels.navajo.listeners.stream.core;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.events.EventFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class BaseScriptInstance {

	private final Map<String,Set<Action1<Message>>> messageRegistrations = new HashMap<>();
	private final Map<String,Set<Action1<Message>>> elementRegistrations = new HashMap<>();
	private String outputPath;

	Navajo outputAssembly = NavajoFactory.getInstance().createNavajo();

//	public void run(Observable<NavajoStreamEvent> input, Subscriber<NavajoStreamEvent> output) {
//		output.onNext(navajoStarted());
//	}
//	
	
	public abstract void init(OutputSubscriber output);
	public abstract void complete(OutputSubscriber output);
	
	public void run(NavajoStreamEvent streamEvent, OutputSubscriber output) {
		switch (streamEvent.type()) {
		case HEADER:
			init(output);
			break;
		case MESSAGE:
			Set<Action1<Message>> regs = messageRegistrations.get(streamEvent.path());
			if(regs!=null) {
				regs.forEach(e->e.call((Message)streamEvent.body()));
			}
			break;
		case ARRAY_ELEMENT:
			Set<Action1<Message>> arrayRegs = elementRegistrations.get(streamEvent.path());
			if(arrayRegs!=null) {
				arrayRegs.forEach(e->e.call((Message)streamEvent.body()));
			}
			break;
		case NAVAJO_DONE:
			complete(output);
			output.onNext(streamEvent);
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

	public void onMessage(String path, Action1<Message> cb) {
		Set<Action1<Message>> callbacks = messageRegistrations.get(path);
		if(callbacks==null) {
			callbacks = new HashSet<>();
			messageRegistrations.put(path,callbacks);
		}
		callbacks.add(cb);
	}
	
	public void onElement(String path, Action1<Message> cb) {
		Set<Action1<Message>> callbacks = messageRegistrations.get(path);
		if(callbacks==null) {
			callbacks = new HashSet<>();
			elementRegistrations.put(path,callbacks);
		}
		callbacks.add(cb);
	}


	public void updateOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	

	protected void array(String name, OutputSubscriber output, Action0 action) {
		output.onNext(EventFactory.arrayStarted(NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(),name,Message.MSG_TYPE_ARRAY),"Company" ));
		action.call();
		output.onNext(EventFactory.arrayDone(NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(),"Company",Message.MSG_TYPE_ARRAY),"Company" ));
	}
	
	protected Observable<NavajoStreamEvent> element(String name,Action1<Message> action) {
		Navajo nav = NavajoFactory.getInstance().createNavajo();
		Message element = NavajoFactory.getInstance().createMessage(nav,name,Message.MSG_TYPE_ARRAY_ELEMENT);
		action.call(element);
		return  Observable.just(EventFactory.arrayElementStarted(element, name), EventFactory.arrayElement(element, name)); //EventFactory.arrayElement(element, "Company");

	}

	protected Observable<NavajoStreamEvent> callInit(String name) {
		return Observable.<NavajoStreamEvent>create(subscriber-> {
			
		}).subscribeOn(Schedulers.io());
	}
	
	protected Message createMessage(String name) {
		return NavajoFactory.getInstance().createMessage(outputAssembly, name);
	}
	
	protected Property createProperty(String name,Object value) {
		Property prop = NavajoFactory.getInstance().createProperty(outputAssembly, name, Property.STRING_PROPERTY, "", -1, "", Property.DIR_OUT);
		prop.setAnyValue(value);
		return prop;
	}
}
