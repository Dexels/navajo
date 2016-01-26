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
		return processNavajoEvent(streamEvents);
	}
	
	
	
	private Observable<Navajo> completeNavajo() {
		try {
			for (String path : deferredPaths) {
				Message message = deferredMessages.get(path);
//				System.err.println("Adding path: "+path);
//				message.write(System.err);
				addMessage(message, path);
			} 
			System.err.println("COMPLETE!");
			return Observable.<Navajo>just(assemble);
		} catch (Throwable e) {
			return Observable.<Navajo>error(e);
		}
	}

	private Observable<Navajo> processNavajoEvent(NavajoStreamEvent n) {
		switch (n.type()) {
		case HEADER:
			assemble.addHeader(((Header)n.body()).copy(assemble));
			return Observable.<Navajo>empty();
		case ARRAY_ELEMENT_STARTED:
			return Observable.<Navajo>empty();
		case MESSAGE_STARTED:
			deferredPaths.add(n.path());
			return Observable.<Navajo>empty();
		case MESSAGE:
			deferredMessages.put(n.path(), (Message) n.body());
			return Observable.<Navajo>empty();
		case ARRAY_STARTED:
			deferredMessages.put(n.path(), (Message) n.body());
			deferredPaths.add(n.path());
			return Observable.<Navajo>empty();
		case ARRAY_ELEMENT:
			deferredMessages.get(stripIndex(n.path())).addElement((Message) n.body());
			return Observable.<Navajo>empty();
		case MESSAGE_DEFINITION:
			deferredMessages.get(stripIndex(n.path())).setDefinitionMessage((Message) n.body());
			return Observable.<Navajo>empty();
		case NAVAJO_DONE:
			System.err.println("Keys: "+deferredMessages.keySet());
			return completeNavajo();
		default:
			return Observable.<Navajo>empty();
		}
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
