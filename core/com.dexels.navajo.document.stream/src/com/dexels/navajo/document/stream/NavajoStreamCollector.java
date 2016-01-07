package com.dexels.navajo.document.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.events.ArrayElement;
import com.dexels.navajo.document.stream.events.ArrayStart;
import com.dexels.navajo.document.stream.events.HeaderEvent;
import com.dexels.navajo.document.stream.events.MessageEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Subscriber;

public class NavajoStreamCollector {

	private Navajo assemble = NavajoFactory.getInstance().createNavajo();
	
	private final Map<String,Message> deferredMessages = new HashMap<>();
	private final List<String> deferredPaths = new ArrayList<>();

	protected Subscriber<? super Navajo> currentSubscriber;
	
	public NavajoStreamCollector() {
	}

	public Navajo completeNavajo() {
		try {
			for (String path : deferredPaths) {
				addMessage(deferredMessages.get(path), path);
			} 
			assemble.write(System.err);
			currentSubscriber.onNext(assemble);
			currentSubscriber.onCompleted();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return assemble;
	}

	public Navajo processNavajoEvent(NavajoStreamEvent n) {
		switch (n.type()) {
		case HEADER:
			assemble.addHeader(((HeaderEvent) (n)).getHeader().copy(assemble));
			return null;
		case MESSAGE:
			MessageEvent m = (MessageEvent)n;
			deferredMessages.put(m.getPath(), m.getMessage());
			deferredPaths.add(m.getPath());
			return null;
		case ARRAY_START:
			ArrayStart as = (ArrayStart)n;
			deferredMessages.put(as.getPath(), as.getMessage());
			deferredPaths.add(as.getPath());
			return null;
		case ARRAY_ELEMENT:
			ArrayElement e = (ArrayElement)n;
			deferredMessages.get(e.getPath()).addElement(e.getMessage());
			return null;
		case NAVAJO_DONE:
			return completeNavajo();
		default:
			break;
		}
		return null;
	}

	
	private void addMessage(Message message, String path) {
		
		String[] pathElements = path.split("/");
		if(pathElements.length==1) {
			assemble.addMessage(message.copy(assemble));
			return;
		}
		Message current = null;
		for (int i = 0; i < pathElements.length; i++) {
			if(i==0) {
				current = assemble.getMessage(pathElements[0]);
				if(current==null) {
					current = deferredMessages.get(pathElements[0]);
					assemble.addMessage(current);
				}
			} else {
				Message element = current.getMessage(pathElements[i]);
				if(element==null) {
					element = deferredMessages.get(assemblePath(pathElements,i));
					current.addMessage(element);
				}
				current = element;
			}
		}
	}
	private String assemblePath(String[] pathElements, int count) {
		StringBuilder result = new StringBuilder();
		for (int j = 0; j < pathElements.length; j++) {
			if(j!=0) {
				result.append("/");
			}
			result.append(pathElements[j]);
			if(count==j) {
				break;
			}
		}
		return result.toString();
	}
	
	public Observable<Navajo> feed(NavajoStreamEvent streamEvents) {
			Observable<Navajo> currentObservable = Observable.<Navajo>create(subscriber->{
				NavajoStreamCollector.this.currentSubscriber = subscriber;
				final Navajo result = processNavajoEvent(streamEvents);
				if(result!=null) {
					subscriber.onNext(result);
				}
			});
			return currentObservable;
	}

}
