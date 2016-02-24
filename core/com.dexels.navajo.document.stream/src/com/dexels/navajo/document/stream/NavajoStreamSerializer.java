package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;

public class NavajoStreamSerializer {

	private int tagDepth = 0;
	private Stack<String> messageNameStack = new Stack<>();
	private Stack<Boolean> messageIgnoreStack = new Stack<>();
	private final Map<String,Integer> arrayCounter = new HashMap<>();
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamSerializer.class);
	public static final int INDENT = 3;

	public NavajoStreamSerializer() {
	}
	
	public Observable<byte[]> feed(final NavajoStreamEvent streamEvents) {
		return Observable.create(subscribe->{
			Writer w = new Writer(){
				@Override
				public void write(char[] cbuf, int off, int len) throws IOException {
					if(len>0) {
						String fragment = new String(Arrays.copyOfRange(cbuf, off, off+len));
						subscribe.onNext( fragment.getBytes(StandardCharsets.UTF_8));
					} else {
					}
				}

				@Override
				public void flush() throws IOException {}

				@Override
				public void close() throws IOException {}
			};
			processNavajoEvent(streamEvents,w);
			subscribe.onCompleted();
		});
	}
	
	@SuppressWarnings("unchecked")
	private void processNavajoEvent(NavajoStreamEvent event,Writer w) {
		try {
			String name = event.path();
			switch (event.type()) {
				case MESSAGE_STARTED:
					messageIgnoreStack.push("ignore".equals(event.attribute("mode")));
					if(!messageIgnoreStack.contains(true)) {
						printStartTag(w, INDENT * (tagDepth+1),true,"message",createMessageAttributes("name=\""+name+"\"",event.attributes()));
					}
					tagDepth++;
					messageNameStack.push(name);
					break;
				case MESSAGE_DEFINITION_STARTED:
					messageIgnoreStack.push("ignore".equals(event.attribute("mode")));
					if(!messageIgnoreStack.contains(true)) {
						printStartTag(w, INDENT * (tagDepth+1),true,"message",createMessageAttributes("name=\""+name+"\" type=\"definition\"",event.attributes()));
					}
					tagDepth++;
					messageNameStack.push(name+"@definition");
					break;
				case ARRAY_ELEMENT_STARTED:
					messageIgnoreStack.push("ignore".equals(event.attribute("mode")));
					String arrayName = messageNameStack.peek();
					String pth = currentPath();
					Integer index = arrayCounter.get(pth);
	
					if(index==null) {
						System.err.println("no current index for path: "+pth);
					}
					if(!"ignore".equals(event.attribute("mode"))) {
						printStartTag(w, INDENT * (tagDepth+1),true,"message",createMessageAttributes("name=\""+arrayName+"\" index=\""+index+"\" type=\"array_element\"",event.attributes()));
						tagDepth++;
					}
					arrayCounter.put(pth, ++index);
					messageNameStack.push("@"+index);
	//				startPath(mstart, this.tagStack, outputStreamWriter);
					break;
				case MESSAGE:
				case MESSAGE_DEFINITION:
				case ARRAY_ELEMENT:
//					Message m = (Message) event.body();
					messageNameStack.pop();

					List<Prop> properties = (List<Prop>)event.body();
					if(!messageIgnoreStack.contains(true)) {
						for (Prop prop : properties) {
							prop.write(w,INDENT * (tagDepth+1));
						}
						printEndTag(w, INDENT * tagDepth, "message");
					}
					
					//					printStartTag(w, INDENT * (tagStack.size()+1),true,"message",new String[]{"name="+name,"type=\"definition\""});

//					m.printBody(w,INDENT * (tagStack.size()));
//					m.printCloseTag(w, INDENT * tagStack.size());
					messageIgnoreStack.pop();

					tagDepth--;
					break;
				case ARRAY_STARTED:
					messageIgnoreStack.push("ignore".equals(event.attribute("mode")));

	//				Message arr = (Message) event.getBody();
					if(!messageIgnoreStack.contains(true)) {
						printStartTag(w, INDENT * (tagDepth+1),true,"message","name=\""+name+"\" type=\"array\"");
					}
					messageNameStack.push(name);
					tagDepth++;

					arrayCounter.put(currentPath(), 0);
					break;
				case ARRAY_DONE:
					if(!messageIgnoreStack.contains(true)) {
						printEndTag(w, INDENT*tagDepth, "message");
					}
//					printCloseTag(w, INDENT*tagStack.size());
					arrayCounter.remove(currentPath());
					tagDepth--;
					messageIgnoreStack.pop();
					messageNameStack.pop();
					break;
				case NAVAJO_STARTED:
					w.write("<tml>\n");
					NavajoHead head = (NavajoHead) event.body();
//					head.printElement(w,INDENT);
					head.print(w, INDENT);
//					Header h = NavajoFactory.getInstance().createHeader(null, head.name(), head.username(), head.password(), -1);
//					h.printElement(w, INDENT);
					break;				
				case NAVAJO_DONE:
					w.write("</tml>\n");
					break;
	
				default:
					break;
			}
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	private String createMessageAttributes(String initial, Map<String, Object> attributes) {
		StringBuilder sb = new StringBuilder(initial);
		String mode = (String) attributes.get(Message.MSG_MODE);
		String etag = (String) attributes.get(Message.MSG_ETAG);
		if(mode!=null) {
			sb.append(" mode=\""+mode+"\"");
		}
		if(etag!=null) {
			sb.append(" etag=\""+etag+"\"");
		}
		return sb.toString();
	}

	private String currentPath() {
		int i = 0;
		StringWriter sw = new StringWriter();
		for (String element : messageNameStack) {
			sw.write(element);
			if(i!=messageNameStack.size()-1) {
				sw.write("/");
			}
			i++;
		}
		return sw.toString();
	}
	
	private void printStartTag(final Writer sw, int indent,boolean forceDualTags,String tag,  String attributes) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 sw.write("<");
		 sw.write(tag);
		 sw.write(" ");
//		 for (String attribute : attributes) {
			sw.write(attributes);
//		}
		sw.write(">\n");
	}
	
	private void printEndTag(final Writer sw, int indent,String tag) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 sw.write("</");
		 sw.write(tag);
		 sw.write(">\n");
	}
}
