package com.dexels.navajo.document.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
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

	public Observable<Navajo> feed(final NavajoStreamEvent streamEvents) {
		return Observable.create(subscribe->{
			processNavajoEvent(streamEvents,subscribe);
		});
	}
	
	
	
	public Navajo completeNavajo(Subscriber<? super Navajo> subscribe) {
		try {
			for (String path : deferredPaths) {
				System.err.println("ADDING: "+path);
				Message message = deferredMessages.get(path);
				message.write(System.err);
				addMessage(message, path);
			} 
			assemble.write(System.err);
			subscribe.onNext(assemble);
			subscribe.onCompleted();
			System.err.println("COMPLETE!!!");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return assemble;
	}

	public Navajo processNavajoEvent(NavajoStreamEvent n, Subscriber<? super Navajo> subscribe) {
		System.err.println("INCOMING: "+n.type()+" - "+n.getPath());
		switch (n.type()) {
		case HEADER:
			assemble.addHeader(((Header)n.getBody()).copy(assemble));
			return null;
		case ARRAY_ELEMENT_STARTED:
			System.err.println("ARRAY_ELEMENT_STARTED>>>>> "+n.getPath());
			return null;
		case MESSAGE_STARTED:
			System.err.println("MESSAGE_STARTED>>>>> "+n.getPath());
			deferredPaths.add(n.getPath());
			return null;
		case MESSAGE:
			System.err.println("PUT 1>>>>> "+n.getPath());
			deferredMessages.put(n.getPath(), (Message) n.getBody());
			addChildrenToMessage(n.getPath(), (Message) n.getBody());
			((Message) n.getBody()).write(System.err);
			return null;
		case ARRAY_STARTED:
			System.err.println("PUT 2>>>>> "+n.getPath());
			deferredMessages.put(n.getPath(), (Message) n.getBody());
			deferredPaths.add(n.getPath());
			return null;
		case ARRAY_ELEMENT:
			deferredMessages.get(stripIndex(n.getPath())).addElement((Message) n.getBody());
			return null;
		case MESSAGE_DEFINITION:
			deferredMessages.get(stripIndex(n.getPath())).setDefinitionMessage((Message) n.getBody());
			return null;
		case MESSAGE_DEFINITION_STARTED:
//			deferredPaths.add(n.getPath());
			return null;


		case NAVAJO_DONE:
			return completeNavajo(subscribe);
		default:
			break;
		}
		return null;
	}

	
	private void addChildrenToMessage(String path, Message body) {
		for (String defer : deferredPaths) {
			if(defer.startsWith(path)) {
				// a descendant of the current message
				String subPath = defer.substring(path.length(), defer.length());
				if(subPath.indexOf("/")==-1) {
					// a direct descendant
					Message m = deferredMessages.get(defer);
					body.addMessage(m);
				}
			}
		}
		
	}

	private String stripIndex(String path) {
		int index = path.lastIndexOf('@');
		if(index<0) {
			return path;
		}
		return path.substring(0,index);
//		return null;
	}

	private void addMessage(Message message, String path) {
		System.err.println("PATH: "+path);
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
					System.err.println("GET 4>>>>> "+pathElements[0]);
					System.err.println("GET 4b>>>>> "+stripIndex(pathElements[0]));
					current = deferredMessages.get(stripIndex(pathElements[0]));
					assemble.addMessage(current);
				}
			} else {
				Message element = current.getMessage(pathElements[i]);
				if(element==null) {
//					System.err.println(">>>>> Getting: "+assemblePath(pathElements,i));
					System.err.println("GET 5>>>>> "+assemblePath(pathElements,i));
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


}
