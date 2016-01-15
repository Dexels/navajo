package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			subscribe.onCompleted();
		});
	}
	
	public void processNavajoEvent(NavajoStreamEvent event, Subscriber<? super byte[]> subscribe) {
		try {
			
			switch (event.type()) {
			case MESSAGE_STARTED:
				Message mstart = (Message) event.getBody();
				startPath(mstart, this.tagStack, outputStreamWriter);
//				tagStack.add(mstart.getName());
				break;
			case MESSAGE:
				Message m = (Message) event.getBody();
				m.printBody(outputStreamWriter,INDENT * tagStack.size());
				tagStack.remove(tagStack.size()-1);
					m.printCloseTag(outputStreamWriter, INDENT * tagStack.size());
				break;
			case ARRAY_ELEMENT:
				Message element = (Message) event.getBody();
				element.printBody(outputStreamWriter, INDENT * tagStack.size());
				element.printCloseTag(outputStreamWriter, INDENT * tagStack.size());
				tagStack.remove(tagStack.size()-1);

				break;
			case ARRAY_STARTED:
//				Message arr = (Message) event.getBody();
				break;
			case ARRAY_DONE:
				Message arrdone = (Message) event.getBody();
				String donepath = event.getPath();
				endPath(arrdone, tagStack, outputStreamWriter);
//				endArray(donepath);
				break;
			case HEADER:
				// TODO
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
//
//	private void startPath(String element, List<String> current, OutputStream out) throws IOException {
//		String text = "<message name=\""+element+"\" type=\"simple\"> \n";
//		out.write(text.getBytes());
//	}
//	
	private void startPath(Message element, List<String> current, Writer out) throws IOException {
		boolean isOpen = element.printStartTag(out, INDENT*current.size(),true);
	   tagStack.add(element.getName());
	}
	private void endPath(Message m, List<String> currentStack, Writer out) throws IOException {
		m.printCloseTag(out, INDENT*currentStack.size());
		currentStack.remove(currentStack.size()-1);
	}


}
