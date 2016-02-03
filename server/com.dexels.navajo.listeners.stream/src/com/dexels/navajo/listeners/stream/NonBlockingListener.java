package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;
import com.dexels.navajo.listeners.stream.script.ScriptExample;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subjects.PublishSubject;

public class NonBlockingListener extends HttpServlet {

	private static final int BUFFER_SIZE = 1024;
	private static final long serialVersionUID = 8974688302015521319L;
	private final static Logger logger = LoggerFactory.getLogger(NonBlockingListener.class);

//	private Map<HttpServletRequest,Subscriber<byte[]>> subscriber;

	@SuppressWarnings("resource")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final AsyncContext async = req.startAsync();
		final ServletInputStream input = async.getRequest().getInputStream();
		final ServletOutputStream output = async.getResponse().getOutputStream();


		ObservableXmlFeeder xmlParser = new ObservableXmlFeeder();
		ObservableNavajoParser navajoParser = new ObservableNavajoParser(parseHeaders(req));
//		ObservableAuthenticationHandler authenticationHandler = new ObservableAuthenticationHandler();

		// Not really using the write listener yet, don't quite 'get' it
		output.setWriteListener(new WriteListener() {
			
			@Override
			public void onWritePossible() throws IOException {
				logger.info("write possible");
			}
			
			@Override
			public void onError(Throwable t) {
				logger.error("Error: ", t);
			}
		});
		
//		NavajoOutputStreamSubscriber noss = new NavajoOutputStreamSubscriber(output);		
		
//		ObservableOutputStream stream = new ObservableOutputStream();
		
		ConnectableObservable<NavajoStreamEvent> published = Observable.<byte[]>create(subscribe->{

			input.setReadListener(new ReadListener() {
				
				@Override
				public void onError(Throwable t) {
					logger.error("Error: ", t);
					subscribe.onError(t);
				}
				
				@Override
				public void onDataAvailable() throws IOException {
					int len = -1;
			        byte b[] = new byte[50];
			        while (input.isReady() && (len = input.read(b)) != -1) {
			        	subscribe.onNext(Arrays.copyOf(b, len));
			        }				
				}
				
				@Override
				public void onAllDataRead() throws IOException {
					input.close();
					subscribe.onCompleted();
//					output.write("<tml/>".getBytes());
//					output.close();
//					async.complete();
				}
			});
		})
			.flatMap(bytes -> xmlParser.feed(bytes))
			.flatMap(xmlEvents -> navajoParser.feed(xmlEvents))
			.publish();
			authorize(published.filter(e->e.type()==NavajoEventTypes.NAVAJO_STARTED),published,createOutput(output));
			Subscription p = published.connect();
		
		
	}

	private void authorize(Observable<NavajoStreamEvent> headerEvent, Observable<NavajoStreamEvent> publishedStream,Observer<NavajoStreamEvent> output) {
		new Authorizer(headerEvent,publishedStream,output);
	}

	
	private Subscriber<NavajoStreamEvent> resolveScript(String name, Subscriber<NavajoStreamEvent> outputSubscriber) {
		return new ScriptExample(outputSubscriber);
	}
	
private Map<String, Object> parseHeaders(HttpServletRequest req) {
	Map<String,Object> result = new HashMap<>();
	Enumeration<String> header = req.getHeaderNames();
	while(header.hasMoreElements()) {
		String key = header.nextElement();
		result.put(key, req.getHeader(key));
	}
	return result;
}


private PublishSubject<NavajoStreamEvent> createOutput(OutputStream out) {
	PublishSubject<NavajoStreamEvent> subject = PublishSubject.<NavajoStreamEvent>create();
	NavajoStreamSerializer nss = new NavajoStreamSerializer();
	subject.flatMap(nsevent->nss.feed(nsevent)).subscribe(
	b->{
		try {
			out.write(b);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}
		,t->logger.error("Error: ", t)
		,()->{
			try {
				out.flush(); 
				out.close();
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}
	);
	
	return subject;
	
}
}
