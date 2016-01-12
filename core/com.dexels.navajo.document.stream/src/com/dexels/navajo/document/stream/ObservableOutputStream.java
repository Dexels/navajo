package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;
import rx.Subscriber;

public class ObservableOutputStream extends OutputStream {

	private final Observable<byte[]> observable;
	private Subscriber<? super byte[]> subscriber = null;
	
	private final static Logger logger = LoggerFactory.getLogger(ObservableOutputStream.class);

	
	public ObservableOutputStream() {
		this.observable = Observable.<byte[]>create(s->{
				System.err.println("obs created");
				subscriber = s;
			});
	}
	
	public Observable<byte[]> getObservable() {
		return observable;
	}

	public Subscriber<? super byte[]> getSubscriber() {
		return subscriber;
	}
	@Override
	public void write(int b) throws IOException {
		write(new byte[]{(byte) b});
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		write(Arrays.copyOfRange(b, off, off+len));
	}
	@Override
	public void write(byte[] b) throws IOException {
		if(subscriber!=null) {
//			System.err.println("Data: "+new String(b));
			subscriber.onNext(b);
		} else {
			logger.warn("Writing to Observable OutputStream without subscriber, data lost");
		}
	}
	@Override
	public void close() throws IOException {
		if(subscriber!=null) {
			subscriber.onNext(new byte[]{});
			subscriber.onCompleted();
		}
	}

	
}
