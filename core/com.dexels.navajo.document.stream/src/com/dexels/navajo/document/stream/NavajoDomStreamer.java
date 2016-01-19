package com.dexels.navajo.document.stream;

import java.util.Collections;
import java.util.List;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
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
		subscribe.onCompleted();
	}
	private void emitMessage(Message message, Subscriber<? super NavajoStreamEvent> subscribe, Navajo outputNavajo) {
		String path = getPath(message);
		if(message.isArrayMessage()) {
			Message dummyArray = NavajoFactory.getInstance().createMessage(outputNavajo, message.getName(),message.getType());
			subscribe.onNext(new NavajoStreamEvent(path, NavajoStreamEvent.NavajoEventTypes.ARRAY_STARTED , dummyArray,Collections.emptyMap()));
			Message definition = message.getDefinitionMessage();
			if(definition!=null) {
				String definitionpath =getPath(message)+"@definition";
				subscribe.onNext(new NavajoStreamEvent(definitionpath, NavajoStreamEvent.NavajoEventTypes.MESSAGE_DEFINITION_STARTED , definition,Collections.emptyMap()));
				subscribe.onNext(new NavajoStreamEvent(definitionpath, NavajoStreamEvent.NavajoEventTypes.MESSAGE_DEFINITION , definition,Collections.emptyMap()));
				
			}
			for (Message m : message.getElements()) {
				String elementPath = getPath(m);
				subscribe.onNext(new NavajoStreamEvent(elementPath, NavajoStreamEvent.NavajoEventTypes.ARRAY_ELEMENT_STARTED , m,Collections.emptyMap()));
				for (Message sm : m.getAllMessages()) {
					emitMessage(sm, subscribe,outputNavajo);
				}				
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
//
	private String getPath(Message message) {
		String path = message.getPath();
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		return path;
	}
}
