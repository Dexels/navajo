package com.dexels.navajo.listeners.stream.impl;

import java.io.IOException;
import java.util.Stack;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Subscriber;

public class NavajoOutputStreamSubscriber extends Subscriber<NavajoStreamEvent> {

	private final ServletOutputStream output;
	private final Stack<String> tagStack = new Stack<>();
	private final static Logger logger = LoggerFactory.getLogger(NavajoOutputStreamSubscriber.class);

	
	public NavajoOutputStreamSubscriber(ServletOutputStream sos) {
		this.output = sos;
	}
	@Override
	public void onCompleted() {
		try {
			output.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public void onError(Throwable ex) {
		ex.printStackTrace();
		try {
			output.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public void onNext(NavajoStreamEvent event) {
		try {
			switch (event.type()) {
			case MESSAGE:
				Message m = (Message) event.body();
				String msgPath = event.path();
				gotoPath(msgPath);
				m.write(output);
				break;
			case ARRAY_ELEMENT:
				Message element = (Message) event.body();
				element.write(output);
				break;
			case ARRAY_STARTED:
				Message arr = (Message) event.body();
				String path = event.path();
				gotoPath(path);
				arr.write(output);
				break;
			case ARRAY_DONE:
				String donepath = event.path();
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
		output.write(("Ending: "+donepath).getBytes());
	}
	private void gotoPath(String msgPath) throws IOException {
		output.write(("Starting: "+msgPath).getBytes());
	}

}
