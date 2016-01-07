package com.dexels.navajo.document.stream.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.observables.ConnectableObservable;

public class ObservableOutputStream extends OutputStream {

	private final ConnectableObservable<byte[]> observable;
	private Subscriber<? super byte[]> subscriber = null;
	
	public ObservableOutputStream() {
		this.observable = Observable.<byte[]>create(new OnSubscribe<byte[]>(){

			@Override
			public void call(Subscriber<? super byte[]> s) {
				subscriber = s;
			}}).publish();
	}
	
	public ConnectableObservable<byte[]> getObservable() {
		return observable;
	}

	public Subscriber<? super byte[]> getSubscriber() {
		return subscriber;
	}
	@Override
	public void write(int b) throws IOException {
		if(subscriber!=null) {
			subscriber.onNext(new byte[]{(byte) b});
		}

	}
	@Override
	public void write(byte[] b) throws IOException {
		if(subscriber!=null) {
			subscriber.onNext(b);
		}
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if(subscriber!=null) {
			subscriber.onNext(Arrays.copyOfRange(b, off, off+len));
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
