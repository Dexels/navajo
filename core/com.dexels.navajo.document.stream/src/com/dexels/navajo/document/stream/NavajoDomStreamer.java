package com.dexels.navajo.document.stream;

import java.util.Collections;
import java.util.List;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Subscriber;

public class NavajoDomStreamer {

	public NavajoDomStreamer() {
//		this.observableOutputStream.getObservable().subscribe(this);
	}
	public Observable<NavajoStreamEvent> feed(final Navajo navajo) {
		return Observable.create(subscribe->{
			processNavajo(navajo,subscribe);
		});
	}
	
	public void processNavajo(Navajo navajo, Subscriber<? super NavajoStreamEvent> subscribe) {
		Navajo output = NavajoFactory.getInstance().createNavajo();
		List<Message> all = navajo.getAllMessages();
		subscribe.onNext(new NavajoStreamEvent(null, NavajoStreamEvent.NavajoEventTypes.NAVAJO_STARTED , null,Collections.emptyMap() ));
		Header h = navajo.getHeader();
		if(h!=null) {
			subscribe.onNext(new NavajoStreamEvent(null, NavajoStreamEvent.NavajoEventTypes.HEADER , h,Collections.emptyMap() ));
		}
		for (Message message : all) {
			emitMessage(message,subscribe,output);
		}
		subscribe.onNext(new NavajoStreamEvent(null, NavajoStreamEvent.NavajoEventTypes.NAVAJO_DONE , null,Collections.emptyMap() ));
		
	}
	private void emitMessage(Message message, Subscriber<? super NavajoStreamEvent> subscribe, Navajo outputNavajo) {
//		Message message = inputMessage.copy();
//		Message message = NavajoFactory.getInstance().createMessage(outputNavajo, inputMessage.getName(),inputMessage.getType());
//		List<Property> l = inputMessage.getAllProperties();
//		for (Property property : l) {
//			message.addProperty(property.copy(outputNavajo));
//		}
		String path = getPath(message);
		if(message.isArrayMessage()) {
			Message dummyArray = NavajoFactory.getInstance().createMessage(outputNavajo, message.getName(),message.getType());
			subscribe.onNext(new NavajoStreamEvent(path, NavajoStreamEvent.NavajoEventTypes.ARRAY_STARTED , dummyArray,Collections.emptyMap()));
			for (Message m : message.getElements()) {
				String elementPath = getPath(m);
				subscribe.onNext(new NavajoStreamEvent(elementPath, NavajoStreamEvent.NavajoEventTypes.ARRAY_ELEMENT_STARTED , m,Collections.emptyMap()));
				subscribe.onNext(new NavajoStreamEvent(elementPath, NavajoStreamEvent.NavajoEventTypes.ARRAY_ELEMENT , m,Collections.emptyMap()));
			}
			subscribe.onNext(new NavajoStreamEvent(path, NavajoStreamEvent.NavajoEventTypes.ARRAY_DONE , dummyArray,Collections.emptyMap()));
		} else {
			subscribe.onNext(new NavajoStreamEvent(path, NavajoStreamEvent.NavajoEventTypes.MESSAGE_STARTED , message,Collections.emptyMap()));
			for (Message m : message.getAllMessages()) {
				emitMessage(m, subscribe,outputNavajo);
			}
			subscribe.onNext(new NavajoStreamEvent(path, NavajoStreamEvent.NavajoEventTypes.MESSAGE , message,Collections.emptyMap()));
		}

	}

	private String getPath(Message message) {
		String name = message.getName();
		Message parent = message.getParentMessage();
		if(parent==null) {
			return name;
		}
//		System.err.println(">>>"+getPath(parent)+"<<<");
		String parentPath = getPath(parent);
		if("".equals(parentPath)) {
			return name;
		}
		return parentPath+"/"+name;
	}
}
