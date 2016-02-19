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
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Select;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Subscriber;

public class NavajoStreamCollector {

	private Navajo assemble = NavajoFactory.getInstance().createNavajo();
	
//	private final Map<String,Message> deferredMessages = new HashMap<>();
//	private final List<Stack<String>> deferredPaths = new ArrayList<>();
	private final Map<String,AtomicInteger> arrayCounts = new HashMap<>();
	
	private final Stack<Message> messageStack = new Stack<Message>();
	
	private Stack<String> tagStack = new Stack<>();
	
	public NavajoStreamCollector() {
	}

	// TODO use just the operators
	
	public Observable<Navajo> feed(final NavajoStreamEvent streamEvents) {
		Observable<Navajo> processNavajoEvent = processNavajoEvent(streamEvents,null);
		return processNavajoEvent;
		
		
	}

	public void feed(final NavajoStreamEvent streamEvents, Subscriber<? super Navajo> subscriber) {
		processNavajoEvent(streamEvents,subscriber);
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
	private Observable<Navajo> processNavajoEvent(NavajoStreamEvent n, Subscriber<? super Navajo> subscriber) {
		switch (n.type()) {
		case NAVAJO_STARTED:
			createHeader((NavajoHead)n.body());
			return Observable.<Navajo>empty();

		case MESSAGE_STARTED:
			Message prMessage = null;
			if(!messageStack.isEmpty()) {
				prMessage = messageStack.peek();
			}
			String mode = (String) n.attribute("mode");
			
			Message msg = NavajoFactory.getInstance().createMessage(assemble, n.path(),mode);
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
			List<Prop> properties = (List<Prop>) n.body();
			for (Prop entry : properties) {
				elementParent.addProperty(createTmlProperty(entry));
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
			if(subscriber!=null) {
				subscriber.onNext(assemble);
			}
			return Observable.<Navajo>just(assemble);

		default:
			return Observable.<Navajo>empty();
		}
	}
	
	private void createHeader(NavajoHead head) {
		Header h = NavajoFactory.getInstance().createHeader(assemble, head.name(), head.username(), head.password(), -1);
		assemble.addHeader(h);
	}

	private Property createTmlProperty(Prop p) {
		Property result;
		if(Property.SELECTION_PROPERTY.equals(p.type())) {
			result = NavajoFactory.getInstance().createProperty(assemble, p.name(), p.cardinality(), p.description(), p.direction());
			for (Select s : p.selections()) {
				Selection sel = NavajoFactory.getInstance().createSelection(assemble, s.name(), s.value(), s.selected());
				result.addSelection(sel);
			}
		} else {
			result = NavajoFactory.getInstance().createProperty(assemble, p.name(), p.type()==null?Property.STRING_PROPERTY:p.type(), null, p.length(), p.description(), p.direction());
			result.setAnyValue(p.value());
			if(p.type()!=null) {
				result.setType(p.type());
			}
		}
		return result;
	}
}
