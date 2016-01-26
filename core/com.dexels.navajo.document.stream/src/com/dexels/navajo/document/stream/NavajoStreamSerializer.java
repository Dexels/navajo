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
			if(event.type()==NavajoEventTypes.ARRAY_ELEMENT) {
				System.err.println("Array element: "+event.path()+" count: "+arrayCounter.get(event.path()));
			}

			switch (event.type()) {
			case MESSAGE_STARTED:
			case MESSAGE_DEFINITION_STARTED:
				Message mstart = (Message) event.body();
				mstart.printStartTag(w, INDENT * (tagStack.size()+1),true);
				tagStack.add(mstart.getName());
				
				break;
			case ARRAY_ELEMENT_STARTED:
				Message elementstart = (Message) event.body();
				String pth = arrayPath(event.path());
				Integer index = arrayCounter.get(pth);

				if(index==null) {
					System.err.println("no current index for path: "+pth);
				}
				if(event.type()==NavajoEventTypes.ARRAY_ELEMENT_STARTED) {
					elementstart.setIndex(index);
				}
				arrayCounter.put(pth, ++index);

				// TODO: Message should not be necessary in the body, get message name from path
				elementstart.printStartTag(w, INDENT * (tagStack.size()+1),true);
				   tagStack.add(elementstart.getName());
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

	private String arrayPath(String path) {
		int atIndex = path.lastIndexOf('@');
		if(atIndex<0) {
			logger.warn("Array element path without '@': "+path);
			return path;
		}
		return path.substring(0, atIndex);
	}
}
