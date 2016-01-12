package com.dexels.navajo.document.stream;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class NavajoStreamSerializer implements Observer<byte[]> {

	private Subscriber<? super byte[]> bytesink;

	private ObservableOutputStream observableOutputStream;
	
	private final static Logger logger = LoggerFactory.getLogger(NavajoStreamSerializer.class);

	public NavajoStreamSerializer() {
		this.observableOutputStream = new ObservableOutputStream(20);
//		this.observableOutputStream.getObservable().subscribe(this);
	}
	public Observable<byte[]> feed(NavajoStreamEvent streamEvents) {
		processNavajoEvent(streamEvents);
		return observableOutputStream.getObservable();
	}
	
	public void processNavajoEvent(NavajoStreamEvent event) {
		try {
			switch (event.type()) {
			case MESSAGE:
				Message m = (Message) event.getBody();
				String msgPath = event.getPath();
				gotoPath(msgPath);
				m.write(observableOutputStream);
				break;
			case ARRAY_ELEMENT:
				Message element = (Message) event.getBody();
				element.write(observableOutputStream);
				break;
			case ARRAY_START:
				Message arr = (Message) event.getBody();
				String path = event.getPath();
				gotoPath(path);
				arr.write(observableOutputStream);
				break;
			case ARRAY_DONE:
				String donepath = event.getPath();
				endPath(donepath);
				break;
			case HEADER:
				// TODO
				break;
			case NAVAJO_DONE:
				endPath("/");
				break;

			default:
				break;
			}
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	private void endPath(String donepath) throws IOException {
		observableOutputStream.write(("Ending: "+donepath).getBytes());
	}
	private void gotoPath(String msgPath) throws IOException {
		observableOutputStream.write(("Starting: "+msgPath).getBytes());
	}
	@Override
	public void onCompleted() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNext(byte[] arg0) {
//		this.observableOutputStream.getObservable().
	}

}
