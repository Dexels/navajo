package com.dexels.navajo.document.stream;

import static com.dexels.navajo.document.stream.events.EventFactory.arrayDone;
import static com.dexels.navajo.document.stream.events.EventFactory.arrayElement;
import static com.dexels.navajo.document.stream.events.EventFactory.arrayElementStarted;
import static com.dexels.navajo.document.stream.events.EventFactory.arrayStarted;
import static com.dexels.navajo.document.stream.events.EventFactory.header;
import static com.dexels.navajo.document.stream.events.EventFactory.message;
import static com.dexels.navajo.document.stream.events.EventFactory.messageDefinition;
import static com.dexels.navajo.document.stream.events.EventFactory.messageDefinitionStarted;
import static com.dexels.navajo.document.stream.events.EventFactory.messageStarted;
import static com.dexels.navajo.document.stream.events.EventFactory.navajoDone;
import static com.dexels.navajo.document.stream.events.EventFactory.navajoStarted;

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
	public static Observable<NavajoStreamEvent> feed(final Navajo navajo) {
		return Observable.create(subscribe->{
			processNavajo(navajo,subscribe);
		});
	}
	
	private static void processNavajo(Navajo navajo, Subscriber<? super NavajoStreamEvent> subscribe) {
		Navajo output = NavajoFactory.getInstance().createNavajo();
		List<Message> all = navajo.getAllMessages();
		subscribe.onNext( navajoStarted());
		Header h = navajo.getHeader();
		if(h!=null) {
			subscribe.onNext(header(h));
		}
		for (Message message : all) {
			emitMessage(message,subscribe,output);
		}
		subscribe.onNext(navajoDone());
		subscribe.onCompleted();
	}
	private static void emitMessage(Message message, Subscriber<? super NavajoStreamEvent> subscribe, Navajo outputNavajo) {
		String path = getPath(message);
		if(message.isArrayMessage()) {
			Message dummyArray = NavajoFactory.getInstance().createMessage(outputNavajo, message.getName(),message.getType());
			subscribe.onNext(arrayStarted(dummyArray, path));
			Message definition = message.getDefinitionMessage();
			if(definition!=null) {
				String definitionpath =getPath(message)+"@definition";
				subscribe.onNext(messageDefinitionStarted(definition,definitionpath));
				// submessages not allowed in definition messages?
				subscribe.onNext(messageDefinition(definition, definitionpath));
				
			}
			for (Message m : message.getElements()) {
				String elementPath = getPath(m);
				subscribe.onNext(arrayElementStarted(m, elementPath));
				for (Message sm : m.getAllMessages()) {
					emitMessage(sm, subscribe,outputNavajo);
				}				
				subscribe.onNext(arrayElement(m, elementPath));
			}
			subscribe.onNext(arrayDone(dummyArray, path));
		} else {
			subscribe.onNext(messageStarted(message, path));
			for (Message m : message.getAllMessages()) {
				emitMessage(m, subscribe,outputNavajo);
			}
			subscribe.onNext(message(message, path));
		}

	}
//
	private static String getPath(Message message) {
		String path = message.getPath();
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		return path;
	}
}
