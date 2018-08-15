package com.dexels.navajo.document.stream;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.xml.stream.XMLStreamException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Select;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.dexels.navajo.document.stream.xml.XMLEvent;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;
import io.reactivex.internal.util.BackpressureHelper;

//return new BaseFlowableOperator<Flowable<XMLEvent>, byte[]>(queueSize) {
//	
//	@Override
//	public Subscriber<? super byte[]> apply(Subscriber<? super Flowable<XMLEvent>> child) throws Exception {

public class NavajoStreamToMutableMessageStream  extends BaseFlowableOperator<Flowable<Message>, NavajoStreamEvent> implements FlowableOperator<Flowable<Message>, NavajoStreamEvent> {

	private final Stack<Message> messageStack = new Stack<Message>();
	private final Stack<String> tagStack = new Stack<>();
	private final Stack<String> matchStack = new Stack<>();
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamToMutableMessageStream.class);

	public NavajoStreamToMutableMessageStream(String path) {
		super(10);
		for (String element : path.split("/")) {
			matchStack.push(element);
		}
		
		
	}
	
	private boolean emitStack() {
		System.err.println("Checking tagstack: "+tagStack+" with: "+this.matchStack);
		return this.tagStack.equals(this.matchStack);
//		return true;
	}
	
	public Flowable<Message> processNavajoEvent(NavajoStreamEvent n)  {
		System.err.println("Processing event with type: "+n.type()+" path: "+n.path());
		switch (n.type()) {
		case NAVAJO_STARTED:
			return Flowable.empty();

		case MESSAGE_STARTED:
			Message prMessage = null;
			if(!messageStack.isEmpty()) {
				prMessage = messageStack.peek();
			}
			String mode = (String) n.attribute("mode");
			
			Message msg = NavajoFactory.getInstance().createMessage(null, n.path(),Message.MSG_TYPE_SIMPLE);
			msg.setMode(mode);
			if (prMessage == null) {
//				assemble.addMessage(msg);
//				currentMessage.set(msg);
			} else {
				prMessage.addMessage(msg);
			}
			messageStack.push(msg);
			tagStack.push(n.path());
			return Flowable.empty();
		case MESSAGE:
			Message msgParent = messageStack.pop();
			Msg mm = (Msg)n.body();
			List<Prop> msgProps = mm.properties();
			for (Prop e : msgProps) {
				msgParent.addProperty(createTmlProperty(e));
			}
//			if(msgParent==null) {
//				currentMessage.set(null);
//			}
			if(emitStack()) {
				tagStack.pop();
				return Flowable.just(msgParent);
			}
			tagStack.pop();
			return Flowable.empty();
		case ARRAY_STARTED:
			tagStack.push(n.path());
			String path = currentPath();
//			AtomicInteger cnt = arrayCounts.get(path);
//			if(cnt==null) {
//				cnt = new AtomicInteger();
//				arrayCounts.put(path, cnt);
//			}
//			cnt.incrementAndGet();
			Message parentMessage = null;
			if(!messageStack.isEmpty()) {
				parentMessage = messageStack.peek();
			}
		
			Message arr = NavajoFactory.getInstance().createMessage(null, n.path(), Message.MSG_TYPE_ARRAY);
			if (parentMessage == null) {
//				assemble.addMessage(arr);
			} else {
				parentMessage.addMessage(arr);
			}
			messageStack.push(arr);
			return Flowable.empty();
		case ARRAY_DONE:
			String apath = currentPath();
//			arrayCounts.remove(apath);
			this.messageStack.pop();
			return Flowable.empty();
		case ARRAY_ELEMENT_STARTED:
			String arrayElementName = tagStack.peek();
			String  arrayPath = currentPath();
//			AtomicInteger currentCount = arrayCounts.get(arrayPath);
//			if(currentCount!=null) {
//				String ind = "@"+currentCount.getAndIncrement();
//				tagStack.push(ind);
//			} else {
//				System.err.println("Huh?!");
//			}
			arrayPath = currentPath();
			Message newElt = NavajoFactory.getInstance().createMessage(null, arrayElementName, Message.MSG_TYPE_ARRAY_ELEMENT);
			Message arrParent = messageStack.peek();
			arrParent.addElement(newElt);
			messageStack.push(newElt);
			return Flowable.empty();
		case ARRAY_ELEMENT:
			Message elementParent = messageStack.pop();
			Msg msgElement= (Msg)n.body();
			System.err.println(">> "+msgElement.properties());
			List<Prop> elementProps = msgElement.properties();
			for (Prop e : elementProps) {
				elementParent.addProperty(createTmlProperty(e));
			}
			if(emitStack()) {
//				sub.onNext(elementParent);
//				tagStack.pop();
				return Flowable.just(elementParent);

			}
//			tagStack.pop();
			return Flowable.empty();
			
		case MESSAGE_DEFINITION_STARTED:
			// TODO
			return Flowable.empty();
		case MESSAGE_DEFINITION:
			// TODO
			//			tagStack.push(n.path());
			//			deferredMessages.get(stripIndex(n.path())).setDefinitionMessage((Message) n.body());
			return Flowable.empty();
		case NAVAJO_DONE:
			return Flowable.empty();
 			
		default:
			return Flowable.empty();
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

	private Property createTmlProperty(Prop p) {
		Property result;
		if(Property.SELECTION_PROPERTY.equals(p.type())) {
			result = NavajoFactory.getInstance().createProperty(null, p.name(), p.cardinality().orElse(null), p.description(), p.direction().orElse(null));
			for (Select s : p.selections()) {
				Selection sel = NavajoFactory.getInstance().createSelection(null, s.name(), s.value(), s.selected());
				result.addSelection(sel);
			}
		} else {
			result = NavajoFactory.getInstance().createProperty(null, p.name(), p.type()==null?Property.STRING_PROPERTY:p.type(), null, p.length(), p.description(), p.direction().orElse(null));
			if(p.value()!=null) {
				result.setAnyValue(p.value());
			}
			if(p.type()!=null) {
				result.setType(p.type());
			}
		}
		return result;
	}

	public static FlowableOperator<Flowable<Message>, NavajoStreamEvent> toMutable(String path) {
		return new NavajoStreamToMutableMessageStream(path);
	}
	
	public Subscriber<? super NavajoStreamEvent> apply(Subscriber<? super Flowable<Message>> downStream) throws Exception {
		return new Subscriber<NavajoStreamEvent>() {


//			@Override
//			public void onNext(NavajoStreamEvent event) {
//				Flowable<Message> msg = processNavajoEvent(event);
//				downStream.onNext(msg);
//				subscription.request(1);
//			}

		    @Override
		    public void onError(Throwable t) {
		        error = t;
		        done = true;
		        drain(downStream);
		    }

		    @Override
		    public void onComplete() {
//				feeder.endOfInput();
		        done = true;
		        drain(downStream);
		    }
			@Override
			public void onNext(NavajoStreamEvent event) {
				Flowable<Message> msg = processNavajoEvent(event);
				queue.offer(msg);
				drain(downStream);
			}

			@Override
			public void onSubscribe(Subscription s) {
				subscription = s;
				downStream.onSubscribe(new Subscription() {
					
					@Override
					public void request(long n) {
				        BackpressureHelper.add(requested, n);
				        drain(downStream);
					}
					
					@Override
					public void cancel() {
				        cancelled = true;
				        s.cancel();								
					}
				});
				s.request(1);
			}
		};
	}
}