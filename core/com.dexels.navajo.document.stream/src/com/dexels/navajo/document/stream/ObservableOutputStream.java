package com.dexels.navajo.document.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Subscriber;

public class ObservableOutputStream extends OutputStream {

	private Subscriber<? super byte[]> subscriber = null;
	private int buffersize;
	
	private final static Logger logger = LoggerFactory.getLogger(ObservableOutputStream.class);

	
	public ObservableOutputStream(Subscriber<? super byte[]> subscriber, int buffersize) {
		this.buffersize = buffersize;
		this.subscriber = subscriber;
	}

	public Subscriber<? super byte[]> getSubscriber() {
		return subscriber;
	}
	@Override
	public void write(int b) throws IOException {
		write(new byte[]{(byte) b},0,1);
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if(subscriber!=null) {
//			System.err.println("Data: "+new String(b));
			int index = 0;
			if(len<=buffersize) {
				if (b.length==len) {
					subscriber.onNext(b);
				} else {
					byte[] chu = Arrays.copyOfRange(b, off, len);
					subscriber.onNext(chu);
				}
				return;
			}
			// chunking needed:
			int chunksize = Math.min(len, buffersize);
//			System.err.println("loop start");
			while(index<len) {
				int csize = Math.min(chunksize, len-index);
				byte[] chunk = Arrays.copyOfRange(b, index, index+csize);
				subscriber.onNext(chunk);
				index+=chunksize;
			}
//			System.err.println("loop end");

		} else {
			logger.warn("Writing to Observable OutputStream without subscriber, data lost");
		}
	}
	@Override
	public void write(byte[] b) throws IOException {
		write(b,0,b.length);
	}
	@Override
	public void close() throws IOException {
		if(subscriber!=null) {
			subscriber.onNext(new byte[]{});
			subscriber.onCompleted();
		}
	}

	
}
