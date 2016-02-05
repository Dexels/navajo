package com.dexels.navajo.document.stream;

import static com.dexels.navajo.document.stream.events.Events.arrayDone;
import static com.dexels.navajo.document.stream.events.Events.arrayElement;
import static com.dexels.navajo.document.stream.events.Events.arrayElementStarted;
import static com.dexels.navajo.document.stream.events.Events.arrayStarted;
import static com.dexels.navajo.document.stream.events.Events.done;
import static com.dexels.navajo.document.stream.events.Events.message;
import static com.dexels.navajo.document.stream.events.Events.messageDefinition;
import static com.dexels.navajo.document.stream.events.Events.messageDefinitionStarted;
import static com.dexels.navajo.document.stream.events.Events.messageStarted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.Events;
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
//		subscribe.onNext( started());
		Header h = navajo.getHeader();
		if(h!=null) {
			subscribe.onNext(header(h));
		} else {
			System.err.println("Unexpected case: Deal with tml without header?");
		}
		for (Message message : all) {
			emitMessage(message,subscribe,output);
		}
		subscribe.onNext(done());
		subscribe.onCompleted();
	}
	
	
	private static NavajoStreamEvent header(Header h) {
		return Events.started(new NavajoHead(h.getRPCName(), h.getRPCUser(), h.getRPCPassword(), h.getHeaderAttributes(),Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap()));
	}
	private static void emitMessage(Message message, Subscriber<? super NavajoStreamEvent> subscribe, Navajo outputNavajo) {
//		String path = getPath(message);
		String name = message.getName();
		if(message.isArrayMessage()) {
			subscribe.onNext(arrayStarted(name));
			Message definition = message.getDefinitionMessage();
			if(definition!=null) {
				String definitionname =name+"@definition";
				subscribe.onNext(messageDefinitionStarted(definitionname));
				subscribe.onNext(messageDefinition(messageProperties(definition), definitionname));
			}
			for (Message m : message.getElements()) {
				subscribe.onNext(arrayElementStarted(name));
				for (Message sm : m.getAllMessages()) {
					emitMessage(sm, subscribe,outputNavajo);
				}				
				subscribe.onNext(arrayElement(messageProperties(m), name));
			}
			subscribe.onNext(arrayDone(name));
		} else {
			subscribe.onNext(messageStarted(name));
			for (Message m : message.getAllMessages()) {
				emitMessage(m, subscribe,outputNavajo);
			}
			subscribe.onNext(message( messageProperties(message), name));
		}

	}

private static List<Prop> messageProperties(Message msg) {
	List<Property> all = msg.getAllProperties();
	final List<Prop> result = new ArrayList<>();
	for (Property property : all) {
		result.add(create(property));
	}
	return Collections.unmodifiableList(result);
	}

	private static Prop create(Property tmlProperty) {
		return Prop.create(tmlProperty.getName(),tmlProperty.getTypedValue(),tmlProperty.getType());
	}

//	public static Msg create(Message)
	
}
