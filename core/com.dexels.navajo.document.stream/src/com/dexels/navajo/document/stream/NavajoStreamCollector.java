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
				Message message = deferredMessages.get(path);
				System.err.println("Adding path: "+path);
				message.write(System.err);
				addMessage(message, path);
			} 
			subscribe.onNext(assemble);
			subscribe.onCompleted();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return assemble;
	}

	public Navajo processNavajoEvent(NavajoStreamEvent n, Subscriber<? super Navajo> subscribe) {
		switch (n.type()) {
		case HEADER:
			assemble.addHeader(((Header)n.getBody()).copy(assemble));
			return null;
		case ARRAY_ELEMENT_STARTED:
			return null;
		case MESSAGE_STARTED:
			deferredPaths.add(n.getPath());
			return null;
		case MESSAGE:
			deferredMessages.put(n.getPath(), (Message) n.getBody());
//			addChildrenToMessage(n.getPath(), (Message) n.getBody());
			return null;
		case ARRAY_STARTED:
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
			System.err.println("Keys: "+deferredMessages.keySet());
			return completeNavajo(subscribe);
		default:
			break;
		}
		return null;
	}

	
//	private void addChildrenToMessage(String path, Message body) {
//		for (String defer : deferredPaths) {
//			if(path.equals(defer)) {
//				System.err.println("Can not add message: "+defer+" to itself");
//				return;
//			}
//			if(defer.startsWith(path)) {
//				// a descendant of the current message
//				String subPath = defer.substring(path.length(), defer.length());
//				if(subPath.indexOf("/")==-1) {
//					// a direct descendant
//					Message m = deferredMessages.get(defer);
//					if(body==m) {
//						System.err.println("EEEK! ");
//						m.write(System.err);
//					}
//					body.addMessage(m);
//				}
//			}
//		}
//		
//	}

	private String stripIndex(String path) {
		int index = path.lastIndexOf('@');
		if(index<0) {
			return path;
		}
		return path.substring(0,index);
//		return null;
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
					current = deferredMessages.get(stripIndex(pathElements[0]));
					assemble.addMessage(current);
				}
			} else {
				Message element = current.getMessage(pathElements[i]);
				if(element==null) {
					element = deferredMessages.get(assemblePath(pathElements,i));
					if(current==element) {
						System.err.println("oh dear");
						current.write(System.err);

					}
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
