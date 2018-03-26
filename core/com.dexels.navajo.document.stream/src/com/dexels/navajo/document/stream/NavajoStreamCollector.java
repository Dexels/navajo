package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.api.Method;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Select;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.types.Binary;


public class NavajoStreamCollector {

	private Navajo assemble = NavajoFactory.getInstance().createNavajo();
	
	private final Map<String,AtomicInteger> arrayCounts = new HashMap<>();
	
	private final Stack<Message> messageStack = new Stack<Message>();
	private Stack<String> tagStack = new Stack<>();
	private final Map<String,Binary> pushBinaries = new HashMap<>();
	private final Map<String,Property> binaryProperties = new HashMap<>();

	private Binary currentBinary;
	private Property currentProperty;
	public NavajoStreamCollector() {
	}
	// return # of emitted items, for handling the backpressure
	public Optional<Navajo> processNavajoEvent(NavajoStreamEvent n) throws IOException {
		switch (n.type()) {
		case NAVAJO_STARTED:
			createHeader((NavajoHead)n.body());
			return Optional.empty();

		case MESSAGE_STARTED:
			Message prMessage = null;
			if(!messageStack.isEmpty()) {
				prMessage = messageStack.peek();
			}
			String mode = (String) n.attribute("mode");
			
			Message msg = NavajoFactory.getInstance().createMessage(assemble, n.path(),Message.MSG_TYPE_SIMPLE);
			msg.setMode(mode);
			if (prMessage == null) {
				assemble.addMessage(msg);
			} else {
				prMessage.addMessage(msg);
			}
			messageStack.push(msg);
			tagStack.push(n.path());
			return Optional.empty();
		case MESSAGE:
			Message msgParent = messageStack.pop();
			tagStack.pop();
			Msg mm = (Msg)n.body();
			List<Prop> msgProps = mm.properties();
			for (Prop e : msgProps) {
				msgParent.addProperty(createTmlProperty(e));
			}
//			pushBinaries
			for(Entry<String,Binary> e : pushBinaries.entrySet()) {
				msgParent.addProperty(createBinaryProperty(e.getKey(),e.getValue()));
			}
			pushBinaries.clear();
			binaryProperties.clear();
			return Optional.empty();
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
			return Optional.empty();
		case ARRAY_DONE:
			String apath = currentPath();
			arrayCounts.remove(apath);
			this.messageStack.pop();
			return Optional.empty();
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
			return Optional.empty();
		case ARRAY_ELEMENT:
			tagStack.pop();
			Message elementParent = messageStack.pop();
			Msg msgElement= (Msg)n.body();
			List<Prop> elementProps = msgElement.properties();
			for (Prop e : elementProps) {
				elementParent.addProperty(createTmlProperty(e));
			}
			for(Entry<String,Binary> e : pushBinaries.entrySet()) {
				elementParent.addProperty(createBinaryProperty(e.getKey(),e.getValue()));
			}
			pushBinaries.clear();

			return Optional.empty();
			
		case MESSAGE_DEFINITION_STARTED:
			// TODO
			return Optional.empty();
		case MESSAGE_DEFINITION:
			// TODO
			//			tagStack.push(n.path());
			//			deferredMessages.get(stripIndex(n.path())).setDefinitionMessage((Message) n.body());
			return Optional.empty();
		case NAVAJO_DONE:
			@SuppressWarnings("unchecked")
			List<Method> methodList = (List<Method>) n.body();
			methodList.stream().map(m->NavajoFactory.getInstance().createMethod(assemble, m.name, "")).forEach(e->assemble.addMethod(e));
			return Optional.of(assemble);
		case BINARY_STARTED:
			String name = n.path();
			n.attribute("direction");
			this.currentBinary = new Binary();
			this.currentBinary.startPushRead();
			this.pushBinaries.put(name, currentBinary);
			int length = (Integer) n.attribute("length",()->-1); 
			String description = (String) n.attribute("description",()->""); 
			String direction = (String) n.attribute("direction",()->"");
			String subtype = (String) n.attribute("subtype",()->"");
			this.currentProperty = NavajoFactory.getInstance().createProperty(assemble, name, Property.BINARY_PROPERTY, "", length, description, direction); 
			this.currentProperty.setSubType(subtype);
			return Optional.empty();
			
		case BINARY_CONTENT:
				if(this.currentBinary==null) {
					// whoops;
				}
				this.currentBinary.pushContent((String) n.body());
				return Optional.empty();

		case BINARY_DONE:
				this.currentBinary.finishPushContent();
				return Optional.empty();
		default:
			return Optional.empty();
		}
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
	private Property createBinaryProperty(String name, Binary value) {
		Property result = this.binaryProperties.get(name);
//		Property result = NavajoFactory.getInstance().createProperty(assemble, name, Property.BINARY_PROPERTY, null,0,"", Property.DIR_IN);
		result.setAnyValue(value);
		return result;
	}

	private void createHeader(NavajoHead head) {
		Header h = NavajoFactory.getInstance().createHeader(assemble, head.name(), head.username().orElse(null), head.password().orElse(null), -1);
		assemble.addHeader(h);
	}

	private Property createTmlProperty(Prop p) {
		Property result;
		if(Property.SELECTION_PROPERTY.equals(p.type())) {
			result = NavajoFactory.getInstance().createProperty(assemble, p.name(), p.cardinality().orElse(null), p.description(), p.direction().orElse(null));
			for (Select s : p.selections()) {
				Selection sel = NavajoFactory.getInstance().createSelection(assemble, s.name(), s.value(), s.selected());
				result.addSelection(sel);
			}
		} else {
			result = NavajoFactory.getInstance().createProperty(assemble, p.name(), p.type()==null?Property.STRING_PROPERTY:p.type(), null, p.length(), p.description(), p.direction().orElse(null));
			if(p.value()!=null) {
				result.setAnyValue(p.value());
			}
			if(p.type()!=null) {
				result.setType(p.type());
			}
		}
		return result;
	}
}
