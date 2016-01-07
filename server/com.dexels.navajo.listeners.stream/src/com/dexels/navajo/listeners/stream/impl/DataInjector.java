package com.dexels.navajo.listeners.stream.impl;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

public class DataInjector implements OnSubscribe<byte[]> {

	private Subscriber<? super byte[]> subscriber;
	private final ServletInputStream servletInputStream;
	public DataInjector(ServletInputStream sis) {
		this.servletInputStream = sis;
	}
	
	
	@Override
	public void call(Subscriber<? super byte[]> subscriber) {
			this.subscriber = subscriber;
		Observer o;
		this.servletInputStream.setReadListener(new ReadListener(){

			@Override
			public void onAllDataRead() throws IOException {
				subscriber.onCompleted();
			}

			@Override
			public void onDataAvailable() throws IOException {
		        int len = -1;
		        byte b[] = new byte[1024];
		        while (servletInputStream.isReady() && (len = servletInputStream.read(b)) != -1) {
		        	subscriber.onNext(Arrays.copyOfRange(b, 0, len));
		        }
		    }

			@Override
			public void onError(Throwable ex) {
				subscriber.onError(ex);
			}});

	}

}
