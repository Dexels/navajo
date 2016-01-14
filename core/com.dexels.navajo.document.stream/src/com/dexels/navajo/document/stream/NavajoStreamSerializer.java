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

	private ObservableOutputStream observableOutputStream;
	private List<String> tagStack = new ArrayList<>();
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamSerializer.class);
	private static final int INDENT = 3;

	public NavajoStreamSerializer() {
//		this.observableOutputStream.getObservable().subscribe(this);
	}
	public Observable<byte[]> feed(final NavajoStreamEvent streamEvents) {
		return Observable.create(subscribe->{
			this.observableOutputStream = new ObservableOutputStream(subscribe,10);
			processNavajoEvent(streamEvents,subscribe);
		});
	}
	
	public void processNavajoEvent(NavajoStreamEvent event, Subscriber<? super byte[]> subscribe) {
		try {
			
			switch (event.type()) {
			case MESSAGE_STARTED:
				Message mstart = (Message) event.getBody();
				startPath(mstart, this.tagStack, observableOutputStream);
//				tagStack.add(mstart.getName());
				break;
			case MESSAGE:
				Message m = (Message) event.getBody();
//				this.tagStack = gotoPath(event.path().subList(0, event.path().size()-1),new ArrayList<>(this.tagStack),observableOutputStream);
				Writer outputStreamWriter = new OutputStreamWriter(observableOutputStream);
//				boolean isOpen = m.printStartTag(outputStreamWriter, tagStack.size());
				m.printBody(outputStreamWriter,INDENT * tagStack.size());
				tagStack.remove(tagStack.size()-1);
//				if(!m.getAllMessages().isEmpty() || !m.getAllProperties().isEmpty()) {
					m.printCloseTag(outputStreamWriter, INDENT * tagStack.size());
//				}
				outputStreamWriter.flush();
				outputStreamWriter.close();
				break;
			case ARRAY_ELEMENT:
				Message element = (Message) event.getBody();
				Writer ow = new OutputStreamWriter(observableOutputStream);
				element.printBody(ow, INDENT * tagStack.size());
				element.printCloseTag(ow, INDENT * tagStack.size());
				ow.flush();
				ow.close();
				tagStack.remove(tagStack.size()-1);

				break;
			case ARRAY_STARTED:
				Message arr = (Message) event.getBody();
//				this.tagStack = gotoPath(event.path(),new ArrayList<>(this.tagStack),observableOutputStream);
//				arr.write(observableOutputStream);
//				Writer ow2 = new OutputStreamWriter(observableOutputStream);
//				arr.printStartTag(ow2, this.tagStack.size() * INDENT, true);
//				ow2.flush();
//				ow2.close();
				break;
			case ARRAY_DONE:
				Message arrdone = (Message) event.getBody();
				String donepath = event.getPath();
				endPath(arrdone, tagStack, observableOutputStream);
//				endArray(donepath);
				break;
			case HEADER:
				// TODO
				break;
			case NAVAJO_STARTED:
				
				observableOutputStream.write("<tml>\n".getBytes());
				break;				
			case NAVAJO_DONE:
				observableOutputStream.write("</tml>\n".getBytes());
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
	private void startPath(Message element, List<String> current, OutputStream out) throws IOException {
		Writer outputStreamWriter = new OutputStreamWriter(out);
		boolean isOpen = element.printStartTag(outputStreamWriter, INDENT*current.size(),true);
		outputStreamWriter.flush();
		outputStreamWriter.close();
	   tagStack.add(element.getName());
	}
	private void endPath(Message m, List<String> currentStack, OutputStream out) throws IOException {
		Writer outputStreamWriter = new OutputStreamWriter(out);
		m.printCloseTag(outputStreamWriter, INDENT*currentStack.size());
		currentStack.remove(currentStack.size()-1);
		outputStreamWriter.flush();
		outputStreamWriter.close();
	}


}
