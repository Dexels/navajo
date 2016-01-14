package com.dexels.navajo.listeners.stream;

import java.io.IOException;
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

import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;
import com.dexels.navajo.listeners.stream.impl.NavajoOutputStreamSubscriber;
import com.dexels.navajo.listeners.stream.impl.ObservableAuthenticationHandler;
import com.dexels.navajo.listeners.stream.script.ScriptExample;

import rx.Observable;
import rx.Subscriber;

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
		ObservableAuthenticationHandler authenticationHandler = new ObservableAuthenticationHandler();

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
		
		NavajoOutputStreamSubscriber noss = new NavajoOutputStreamSubscriber(output);		
		
//		ObservableOutputStream stream = new ObservableOutputStream();
		
		Observable.<byte[]>create(subscribe->{
			ObservableOutputStream stream = new ObservableOutputStream(subscribe,BUFFER_SIZE);

			input.setReadListener(new ReadListener() {
				
				@Override
				public void onError(Throwable arg0) {
					logger.error("Error: ", input);
					
				}
				
				@Override
				public void onDataAvailable() throws IOException {
					int len = -1;
			        byte b[] = new byte[50];
			        while (input.isReady() && (len = input.read(b)) != -1) {
			        	stream.write(b,0,len);
			        }				
			        stream.flush();
				}
				
				@Override
				public void onAllDataRead() throws IOException {
					input.close();
					output.write("<tml/>".getBytes());
					output.close();
					async.complete();
				}
			});
		})
//		stream.getObservable()
			.flatMap(bytes -> xmlParser.feed(bytes))
//			.doOnNext(x->System.err.println(x))
			.flatMap(xmlEvents -> navajoParser.feed(xmlEvents))
			.lift(authenticationHandler)
			.subscribe(resolveScript(authenticationHandler.getScriptName(),noss) );
	
		
		


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


//	<T> Observable<T> fromIterable(final Iterable<T> iterable) {
//		  return Observable.create(new OnSubscribe<T>() {
//		    @Override
//		    public void call(Subscriber<? super T> subscriber) {
//		      try {
//		        Iterator<T> iterator = iterable.iterator(); // (1)
//		        while (iterator.hasNext()) { // (2)
//		          subscriber.onNext(iterator.next());
//		        }
//		        subscriber.onCompleted(); // (3)
//		      }
//		      catch (Exception e) {
//		        subscriber.onError(e); // (4)
//		      }
//		    }
//		  });
//		}
}
