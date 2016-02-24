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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Prop.Direction;
import com.dexels.navajo.document.stream.api.Select;
import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;

public class NavajoDomStreamer {

	public NavajoDomStreamer() {
//		this.observableOutputStream.getObservable().subscribe(this);
	}
	public static Observable<NavajoStreamEvent> feed(final Navajo navajo) {
		return Observable.from(processNavajo(navajo));
	}
	
	private static List<NavajoStreamEvent> processNavajo(Navajo navajo) {
		List<NavajoStreamEvent> result = new ArrayList<>();
		Navajo output = NavajoFactory.getInstance().createNavajo();
		List<Message> all = navajo.getAllMessages();
//		subscribe.onNext( started());
		Header h = navajo.getHeader();
		if(h!=null) {
			result.add(header(h));
		} else {
			System.err.println("Unexpected case: Deal with tml without header?");
		}
		for (Message message : all) {
			emitMessage(message,result,output);
		}
		result.add(done());
//		subscribe.onNext(done());
//		subscribe.onCompleted();
		return result;
	}
	
	
	// TODO extract async and piggyback attributes
	private static NavajoStreamEvent header(Header h) {
		return Events.started(new NavajoHead(h.getRPCName(), h.getRPCUser(), h.getRPCPassword(), h.getHeaderAttributes(),Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap()));
	}
	private static void emitMessage(Message message,List<NavajoStreamEvent> list, Navajo outputNavajo) {
//		String path = getPath(message);
		String name = message.getName();
		Map<String,Object> messageAttributes = getMessageAttributes(message);
		if(message.isArrayMessage()) {
			list.add(arrayStarted(name,messageAttributes));
			Message definition = message.getDefinitionMessage();
			if(definition!=null) {
				String definitionname =name+"@definition";
				list.add(messageDefinitionStarted(definitionname));
				list.add(messageDefinition(messageProperties(definition), definitionname));
			}
			for (Message m : message.getElements()) {
				Map<String,Object> elementAttributes = getMessageAttributes(message);
				list.add(arrayElementStarted(elementAttributes));
				for (Message sm : m.getAllMessages()) {
					emitMessage(sm, list,outputNavajo);
				}				
				list.add(arrayElement(messageProperties(m),elementAttributes));
			}
			list.add(arrayDone(name));
		} else {
			list.add(messageStarted(name,messageAttributes));
			for (Message m : message.getAllMessages()) {
				emitMessage(m, list,outputNavajo);
			}
			if(message.getMode()!=null && !"".equals(message.getMode())) {
				System.err.println("MESSAGE name: "+message.getName() + " with mode: "+message.getMode());
			}
			list.add(message( messageProperties(message), name,messageAttributes));
		}

	}

//	  public static final String MSG_CONDITION = "condition";

private static Map<String, Object> getMessageAttributes(Message message) {
	Map<String,Object> attr = new HashMap<>();
	if(message.getMode()!=null && !"".equals(message.getMode())) {
		attr.put(Message.MSG_MODE, message.getMode());
	}
//	if(message.getExtends()!=null && !"".equals(message.getExtends())) {
//		attr.put(Message.MSG_EXTENDS, message.getExtends());
//	}
	if(message.getEtag()!=null && !"".equals(message.getEtag())) {
		attr.put(Message.MSG_ETAG, message.getEtag());
	}
//	if(message.getOrderBy()!=null && !"".equals(message.getOrderBy())) {
//		attr.put(Message.MSG_ORDERBY, message.getOrderBy());
//	}
//	if(message.getScope()!=null && !"".equals(message.getScope())) {
//		attr.put(Message.MSG_SCOPE, message.getScope());
//	}
//	if(message.getMethod()!=null && !"".equals(message.getMethod())) {
//		attr.put(Message.MSG_METHOD, message.getMethod());
//	}
	return attr;

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
		String type = tmlProperty.getType();
		
		List<Select> selections = selectFromTml(tmlProperty.getAllSelections());
		Object value = null;
		if(Property.BINARY_PROPERTY.equals(type)) {
			value = tmlProperty.getTypedValue();
		} else if (Property.SELECTION_PROPERTY.equals(type)) {
			value = null;
		} else {
			value = tmlProperty.getValue();
		}
//		Object value = "selection".equals(tmlProperty.getType())?null: tmlProperty.getValue();
		
		return Prop.create(tmlProperty.getName(),value,tmlProperty.getType(),selections,"in".equals(tmlProperty.getDirection())?Direction.IN:Direction.OUT, tmlProperty.getDescription(),tmlProperty.getLength(),tmlProperty.getSubType(),tmlProperty.getCardinality());
		
//		switch (type) {
//		case Property.BINARY_PROPERTY:
//			return Prop.create(attributes, (Binary)tmlProperty.getTypedValue());
//			break;
//
//		default:
//			attributes.put("value", tmlProperty.getTypedValue());
//			break;
//		}
//		return Prop.create(tmlProperty.getName(),tmlProperty.getTypedValue(),tmlProperty.getType());
	}
	
	 private static List<Select> selectFromTml(List<Selection> in) {
		 if(in==null) {
			 return Collections.emptyList();
		 }
		 List<Select> result = new ArrayList<>();
		 for (Selection selection : in) {
			result.add(Select.create(selection.getName(), selection.getValue(), selection.isSelected()));
		}
		 return result;
	 }

//	public static Msg create(Message)
	
}
