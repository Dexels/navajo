package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.subjects.PublishSubject;

public class NonBlockingListener extends HttpServlet {

	private static final long serialVersionUID = 8974688302015521319L;

//	private Map<HttpServletRequest,Subscriber<byte[]>> subscriber;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext async = req.startAsync();
//		ServletOutputStream out = response.getOutputStream();
//		new OnSubscribe();
		final ServletInputStream input = req.getInputStream();
		final PublishSubject<byte[]> subject = PublishSubject.<byte[]>create();

//		ConnectableObservable<NavajoStreamEvent> b = NavajoStream.streamNavajo(subject);
//		b.connect();
//		NavajoStreamCollector nsc = new NavajoStreamCollector(b);
		
		input.setReadListener(new ReadListener() {
			
			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDataAvailable() throws IOException {
		        int len = -1;
		        byte b[] = new byte[1024];
		        while (input.isReady() && (len = input.read(b)) != -1) {
		        	subject.onNext(Arrays.copyOfRange(b, 0, len));
		        }				
			}
			
			@Override
			public void onAllDataRead() throws IOException {
				// TODO Auto-generated method stub
				
			}
		});
		subject.publish();
//		final Observable<byte[]> b = Observable.<byte[]>create( di);		
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
	
	
    private static Observable<byte[]> fromServletStream(final ServletInputStream input) {
        return Observable.<byte[]>create(new OnSubscribe<byte[]>() {

			@Override
			public void call(Subscriber<? super byte[]> subscriber) {
				input.setReadListener(new ReadListener() {
					
					@Override
					public void onError(Throwable ex) {
						subscriber.onError(ex);
					}
					
					@Override
					public void onDataAvailable() throws IOException {
				        int len = -1;
				        byte b[] = new byte[1024];
				        while (input.isReady() && (len = input.read(b)) != -1) {
				        	//subject.onNext(Arrays.copyOfRange(b, 0, len));
				        	subscriber.onNext(Arrays.copyOfRange(b, 0, len));
				        }				

					}
					
					@Override
					public void onAllDataRead() throws IOException {
						subscriber.onCompleted();
					}
				});
				
			}});
    }

}
