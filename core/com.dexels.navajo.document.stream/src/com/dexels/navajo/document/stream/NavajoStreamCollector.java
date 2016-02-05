package com.dexels.navajo.document.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;

public class NavajoStreamCollector {

	private Navajo assemble = NavajoFactory.getInstance().createNavajo();
	
//	private final Map<String,Message> deferredMessages = new HashMap<>();
//	private final List<Stack<String>> deferredPaths = new ArrayList<>();
	private final Map<String,AtomicInteger> arrayCounts = new HashMap<>();
	
	private final Stack<Message> messageStack = new Stack<Message>();
	
	private Stack<String> tagStack = new Stack<>();
	
	public NavajoStreamCollector() {
	}

	public Observable<Navajo> feed(final NavajoStreamEvent streamEvents) {
		Observable<Navajo> processNavajoEvent = processNavajoEvent(streamEvents);
		return processNavajoEvent;
		
		
	}
	
	private Observable<Navajo> completeNavajo() {
//		try {
//			for (String path : deferredPaths) {
//				Msg message = deferredMessages.get(path);
//				addMessage(message, path);
//			} 
//			System.err.println("COMPLETE!");
//			return Observable.<Navajo>just(assemble);
//		} catch (Throwable e) {
//			return Observable.<Navajo>error(e);
//		}
		return Observable.<Navajo>just(assemble);

	}

	private String currentPath() {
		StringBuilder sb = new StringBuilder();
		for (String path : tagStack) {
			sb.append(path);
			sb.append('/');
		}
		int len = sb.length();
		if(sb.charAt(len-1)=='/') {
			sb.deleteCharAt(len-1);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		nsc.tagStack.push("Outer");
		nsc.tagStack.push("Inner");
	}
	@SuppressWarnings("unchecked")
	private Observable<Navajo> processNavajoEvent(NavajoStreamEvent n) {
		switch (n.type()) {
		case NAVAJO_STARTED:
			createHeader((NavajoHead)n.body());
			return Observable.<Navajo>empty();

		case MESSAGE_STARTED:
			Message prMessage = null;
			if(!messageStack.isEmpty()) {
				prMessage = messageStack.peek();
			}

			Message msg = NavajoFactory.getInstance().createMessage(assemble, n.path());
			if (prMessage == null) {
				assemble.addMessage(msg);
			} else {
				prMessage.addMessage(msg);
			}
			messageStack.push(msg);
			tagStack.push(n.path());
			return Observable.<Navajo>empty();
		case MESSAGE:
			Message msgParent = messageStack.pop();
			tagStack.pop();
//			List<Prop> msgProps = ;
			for (Prop e : (List<Prop>)n.body()) {
				msgParent.addProperty(createTmlProperty(e));
			}
			return Observable.<Navajo>empty();
		case ARRAY_STARTED:
			tagStack.push(n.path());
			String path = currentPath();
			AtomicInteger cnt = arrayCounts.get(path);
			if(cnt==null) {
				cnt = new AtomicInteger();
				arrayCounts.put(path, cnt);
			}
//			cnt.incrementAndGet();
			Message parentMessage = null;
			if(!messageStack.isEmpty()) {
				parentMessage = messageStack.peek();
			}
		
			Message arr = NavajoFactory.getInstance().createMessage(assemble, n.path(), Message.MSG_TYPE_ARRAY);
			if (parentMessage == null) {
				assemble.addMessage(arr);
			} else {
				parentMessage.addMessage(arr);
			}
			messageStack.push(arr);
			return Observable.<Navajo>empty();
		case ARRAY_DONE:
			String apath = currentPath();
			arrayCounts.remove(apath);
			this.messageStack.pop();
			return Observable.<Navajo>empty();
		case ARRAY_ELEMENT_STARTED:
			String arrayElementName = tagStack.peek();
			String  arrayPath = currentPath();
			AtomicInteger currentCount = arrayCounts.get(arrayPath);
			String ind = "@"+currentCount.getAndIncrement();
			tagStack.push(ind);
			arrayPath = currentPath();
			Message newElt = NavajoFactory.getInstance().createMessage(assemble, arrayElementName, Message.MSG_TYPE_ARRAY_ELEMENT);
			Message arrParent = messageStack.peek();
			arrParent.addElement(newElt);
			messageStack.push(newElt);
			return Observable.<Navajo>empty();
		case ARRAY_ELEMENT:
			tagStack.pop();
			Message elementParent = messageStack.pop();
			Map<String,Prop> properties = (Map<String, Prop>) n.body();
			for (Map.Entry<String,Prop> entry : properties.entrySet()) {
				elementParent.addProperty(createTmlProperty(entry.getValue()));
			}

			return Observable.<Navajo>empty();
			
		case MESSAGE_DEFINITION_STARTED:
			// TODO
			return Observable.<Navajo>empty();
		case MESSAGE_DEFINITION:
			// TODO
			//			tagStack.push(n.path());
			//			deferredMessages.get(stripIndex(n.path())).setDefinitionMessage((Message) n.body());
			return Observable.<Navajo>empty();
		case NAVAJO_DONE:
			return completeNavajo();
		default:
			return Observable.<Navajo>empty();
		}
	}
	
	private void createHeader(NavajoHead head) {
		Header h = NavajoFactory.getInstance().createHeader(assemble, head.name(), head.username(), head.password(), -1);
		assemble.addHeader(h);
	}

	private Property createTmlProperty(Prop p) {
		Property result = NavajoFactory.getInstance().createProperty(assemble, p.name(), p.type()==null?Property.STRING_PROPERTY:p.type(), null, p.length(), p.description(), p.direction());
		result.setAnyValue(p.value());
		return result;
	}
}
