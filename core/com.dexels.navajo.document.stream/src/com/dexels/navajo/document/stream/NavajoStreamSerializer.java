package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

import rx.Observable;

public class NavajoStreamSerializer {

	private List<String> tagStack = new ArrayList<>();
	private final Map<String,Integer> arrayCounter = new HashMap<>();
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamSerializer.class);
	private static final int INDENT = 3;

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
	
	private void processNavajoEvent(NavajoStreamEvent event,Writer w) {
		try {
			if(event.type()==NavajoEventTypes.ARRAY_STARTED) {
				System.err.println("Array started: "+event.path());
				arrayCounter.put(event.path(), 0);
			}
			Integer index = arrayCounter.get(event.path());
			if(event.type()==NavajoEventTypes.ARRAY_ELEMENT) {
				System.err.println("Array element: "+event.path()+" count: "+arrayCounter.get(event.path()));
			}

			switch (event.type()) {
			case MESSAGE_STARTED:
			case MESSAGE_DEFINITION_STARTED:
			case ARRAY_ELEMENT_STARTED:
				Message mstart = (Message) event.body();
				if(event.type()==NavajoEventTypes.ARRAY_ELEMENT_STARTED) {
					mstart.setIndex(index);
				}
				arrayCounter.put(event.path(), ++index);

				// TODO: Message should not be necessary in the body, get message name from path
				mstart.printStartTag(w, INDENT * (tagStack.size()+1),true);
				   tagStack.add(mstart.getName());
//				startPath(mstart, this.tagStack, outputStreamWriter);
				break;
			case MESSAGE:
			case MESSAGE_DEFINITION:
				Message m = (Message) event.body();
				m.printBody(w,INDENT * (tagStack.size()));
				m.printCloseTag(w, INDENT * tagStack.size());
				tagStack.remove(tagStack.size()-1);
				break;
			case ARRAY_ELEMENT:
				Message element = (Message) event.body();
				element.printBody(w,  INDENT * tagStack.size());
				tagStack.remove(tagStack.size()-1);
				element.printCloseTag(w,  INDENT * (tagStack.size()+1));
				break;
			case ARRAY_STARTED:
//				Message arr = (Message) event.getBody();
				break;
			case ARRAY_DONE:
				Message arrdone = (Message) event.body();
				arrdone.printCloseTag(w, INDENT*tagStack.size());
//				tagStack.remove(tagStack.size()-1);
				
				break;
			case HEADER:
				Header h = (Header) event.body();
				h.printElement(w, INDENT);
				break;
			case NAVAJO_STARTED:
				
				w.write("<tml>\n");
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
}
