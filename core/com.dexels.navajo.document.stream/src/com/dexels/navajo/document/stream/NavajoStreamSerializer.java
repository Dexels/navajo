package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Subscriber;

public class NavajoStreamSerializer {

//	private ObservableOutputStream observableOutputStream;
	private List<String> tagStack = new ArrayList<>();
	private OutputStreamWriter outputStreamWriter;
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamSerializer.class);
	private static final int INDENT = 3;

	public NavajoStreamSerializer() {
//		this.observableOutputStream.getObservable().subscribe(this);
	}
	public Observable<byte[]> feed(final NavajoStreamEvent streamEvents) {
		return Observable.create(subscribe->{
			ObservableOutputStream observableOutputStream = new ObservableOutputStream(subscribe,10);
			this.outputStreamWriter = new OutputStreamWriter(observableOutputStream);
			processNavajoEvent(streamEvents,subscribe);
			try {
				this.outputStreamWriter.flush();
				this.outputStreamWriter.close();
			} catch (Exception e) {
				subscribe.onError(e);
			}
			subscribe.onCompleted();
		});
	}
	
	public void processNavajoEvent(NavajoStreamEvent event, Subscriber<? super byte[]> subscribe) {
		try {
			switch (event.type()) {
			case MESSAGE_STARTED:
			case MESSAGE_DEFINITION_STARTED:
				Message mstart = (Message) event.getBody();
				mstart.printStartTag(outputStreamWriter, INDENT * (tagStack.size()+1),true);
				   tagStack.add(mstart.getName());
//				startPath(mstart, this.tagStack, outputStreamWriter);
				break;
			case MESSAGE:
			case MESSAGE_DEFINITION:
				Message m = (Message) event.getBody();
				m.printBody(outputStreamWriter,INDENT * (tagStack.size()));
				m.printCloseTag(outputStreamWriter, INDENT * tagStack.size());
				tagStack.remove(tagStack.size()-1);
				m.write(System.err);
				break;
			case ARRAY_ELEMENT:
				Message element = (Message) event.getBody();
				element.printBody(outputStreamWriter,  INDENT * tagStack.size());
				tagStack.remove(tagStack.size()-1);
				element.printCloseTag(outputStreamWriter,  INDENT * (tagStack.size()+1));
				break;
			case ARRAY_STARTED:
//				Message arr = (Message) event.getBody();
				break;
			case ARRAY_DONE:
				Message arrdone = (Message) event.getBody();
				arrdone.printCloseTag(outputStreamWriter, INDENT*tagStack.size());
				tagStack.remove(tagStack.size()-1);
				
				break;
			case HEADER:
				Header h = (Header) event.getBody();
				h.printElement(outputStreamWriter, INDENT);
				break;
			case NAVAJO_STARTED:
				
				outputStreamWriter.write("<tml>\n");
				break;				
			case NAVAJO_DONE:
				outputStreamWriter.write("</tml>\n");
				break;

			default:
				break;
			}
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
}
